import React, { useContext, useState, useEffect } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import "../../Style/MuroEntrada.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import PublicationCard from "./PublicationCard";

function MuroEntrada() {
	const { user, logout, token } = useContext(AuthContext);
	const navigate = useNavigate();
	const handleLogout = () => {
		logout(); // Llama a la función de cierre de sesión
		navigate("/"); // Redirige a la pantalla de inicio
	};
	const fetchPublications = async () => {
		try {
			// Asegúrate de que el token esté presente
			if (!user.token) {
				console.log("No se ha encontrado un token de autenticación");
				return;
			}

			// Configura el encabezado Authorization con el token
			const config = {
				headers: {
					Authorization: `Bearer ${user.token}`,
				},
			};

			// Realiza la solicitud a la API
			const response = await axios.get(
				"http://localhost:8080/publications/getPublications",
				config
			);
			setPublications(response.data);
			console.log(response.data);
		} catch (error) {
			console.error("Error al obtener las publicaciones:", error);
		}
	};
	useEffect(() => {
		fetchPublications(); // Llama la función al cargar la página
	}, [user]);
	const [publications, setPublications] = useState([]);
	return (
		<div className="user-entrada-container">
			{/* Header con la información del usuario */}
			<header className="user-entrada-header">
				<div className="header-content">
					<div className="user-info">
						<p>
							{user ? `${user.name} (${user.email})` : "Usuario no autenticado"}
						</p>
					</div>
					<div className="avatar-container">
						<img
							src="/avatar.png" // Ruta directa al archivo en la carpeta public
							alt="Avatar"
							className="avatar"
						/>
						<button className="logout-button" onClick={handleLogout}>
							Cerrar sesión
						</button>
					</div>
					<div className="image-logo">
						<img
							src="/periferia.png" // Ruta directa al archivo en la carpeta public
							alt="periferia"
							className="periferia"
						/>
					</div>
				</div>
			</header>

			{/* Cuerpo con el contenedor con sombra y fondo gris claro */}
			<div className="user-entrada-body">
				<div className="user-entrada-contenedor">
					
						{publications.length > 0 ? (
							publications.map((publication) => (
								<PublicationCard
									key={publication.id}
									publication={publication}
								/>
							))
						) : (
							<p>No hay publicaciones disponibles.</p>
						)}
					
				</div>
			</div>
		</div>
	);
}

export default MuroEntrada;
