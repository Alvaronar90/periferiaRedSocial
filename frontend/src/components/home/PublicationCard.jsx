import React, { useContext } from "react";
import "../../Style/StylePublicationCard.css";
import { AuthContext } from "../../contexts/AuthContext";
import axios from "axios";


const PublicationCard = ({ publication,refreshPublications }) => {
    const { user } = useContext(AuthContext);
  
	const registerLIke = async (idP) => {
		//e.preventDefault();
		try {
			const config = {
				headers: { Authorization: `Bearer ${user.token}` },
			};
			var formData={}
      formData.idPublication=idP;
      formData.idUser=user.id;
      formData.fechaPublicacion = new Date().toISOString().split("T")[0];
			console.log(formData);
			const response = await axios.post(
				"http://localhost:8080/likes/savelike",
				formData,
				config
			);
      refreshPublications();
			//fetchPublications(); // Refresca publicaciones
		} catch (error) {
			console.error("Error al publicar:", error);
		}
	};


 
	return (
		<div className="publication-item">
			{/* Encabezado */}
			<div className="publication-header">
				<div className="user-info">
					<strong>{publication.usuario.name}</strong>
					<span className="publication-date">
						{publication.fechaPublicacion}
					</span>
				</div>
			</div>

			{/* Contenido */}
			<div className="publication-content">
				{/* Sección de Likes */}
				<button className="likes-section" onClick={()=>registerLIke(publication.id)}>
					<span>👍</span>
					<span>{publication.likes}</span>
				</button>

				{/* Imagen o cuadro vacío */}
				<div className="image-container">
					{publication.imagenBase64 ? (
						<img
							src={`data:image/png;base64,${publication.imagenBase64}`}
							alt="Publicación"
						/>
					) : (
						<span>Sin imagen</span>
					)}
				</div>

				{/* Texto */}
				<div className="text-container">
					<h3>{publication.titulo}</h3>
					<p>{publication.texto}</p>
				</div>
			</div>
		</div>
	);
};

export default PublicationCard;
