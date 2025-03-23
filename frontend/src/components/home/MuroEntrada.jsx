import React, { useContext, useState, useEffect } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import "../../Style/MuroEntrada.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import PublicationCard from "./PublicationCard";
import Profile from "./Profile";

function MuroEntrada() {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();
  const [publications, setPublications] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [showProfile, setShowProfile] = useState(false);
  const [formData, setFormData] = useState({
    titulo: "",
    texto: "",
    imagenBase64: "",
  });

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const config = {
        headers: { Authorization: `Bearer ${user.token}` },
      };
      formData.fechaPublicacion = new Date().toISOString().split("T")[0];
      await axios.post(
        "http://localhost:8080/publications/savePublication/" + user.id,
        formData,
        config
      );
      setShowForm(false);
      setFormData({ titulo: "", texto: "", imagenBase64: "" });
      fetchPublications();
    } catch (error) {
      console.error("Error al publicar:", error);
    }
  };

  const fetchPublications = async () => {
    try {
      if (!user.token) {
        console.log("No se ha encontrado un token de autenticación");
        return;
      }
      const config = {
        headers: { Authorization: `Bearer ${user.token}` },
      };
      const response = await axios.get(
        "http://localhost:8080/publications/getPublications",
        config
      );
      setPublications(response.data);
    } catch (error) {
      console.error("Error al obtener las publicaciones:", error);
    }
  };
  

  useEffect(() => {
    fetchPublications();
  }, [user]);

  return (
    <div className="user-entrada-container1">
      {/* Header */}
      <header className="user-entrada-header">
        <div className="header-content">
          <div className="user-info">
            <p>{user ? `${user.name} (${user.email})` : "Usuario no autenticado"}</p>
            <center>
              <button className="publish-button" onClick={() => setShowForm(!showForm)}>
                {showForm ? "Volver" : "Publicar"}
              </button>
            </center>
          </div>
          <div className="avatar-container">
            <img
              src="/avatar.png"
              alt="Avatar"
              id="Avatar"
              className="avatar"
              onClick={() => setShowProfile(true)}
              style={{ cursor: "pointer" }}
            />
            <button className="logout-button" onClick={handleLogout}>
              Cerrar sesión
            </button>
          </div>
          <div className="image-logo">
            <img src="/periferia.png" alt="periferia" className="periferia" />
          </div>
        </div>
      </header>

      {/* Contenido principal */}
      <div className="user-entrada-body">
        {showProfile ? (
          <div>
           <Profile user={user} boton={<button className="back-button" onClick={() => setShowProfile(false)}>Publicaciones</button>} />

            <div class="profile-button-container">
				<button class="profile-button" onclick="mostrarPublicaciones()">
					Ver Perfil y Publicaciones
				</button>
			</div>
          </div>
        ) : showForm ? (
          <div className="publication-form-container elegant-form">
            <form onSubmit={handleSubmit} className="publication-form">
              <h2 className="form-title">Crear Publicación</h2>
              <input
                type="text"
                name="titulo"
                placeholder="Título"
                value={formData.titulo}
                onChange={handleInputChange}
                required
                className="form-input"
              />
              <textarea
                name="texto"
                placeholder="Texto"
                value={formData.texto}
                onChange={handleInputChange}
                required
                className="form-textarea"
              />
              <input
                type="file"
                accept="image/*"
                onChange={(e) => {
                  const file = e.target.files[0];
                  if (file) {
                    const reader = new FileReader();
                    reader.onloadend = () => {
                      setFormData({
                        ...formData,
                        imagenBase64: reader.result.split(",")[1],
                      });
                    };
                    reader.readAsDataURL(file);
                  }
                }}
                className="form-input"
              />

              <button type="submit" className="form-button">
                Publicar
              </button>
            </form>
          </div>
        ) : (
          <div className="user-entrada-contenedor">
            {publications.length > 0 ? (
              publications.map((publication) => (
                <PublicationCard key={publication.id} publication={publication} refreshPublications={fetchPublications} />
              ))
            ) : (
              <p>No hay publicaciones disponibles.</p>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default MuroEntrada;
