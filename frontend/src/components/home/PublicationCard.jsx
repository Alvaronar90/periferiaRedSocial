import React from "react";
import "../../Style/StylePublicationCard.css"

const PublicationCard = ({ publication }) => {
  return (
    <div className="publication-item">
      {/* Encabezado */}
      <div className="publication-header">
        <div className="user-info">
          <strong>{publication.usuario.name}</strong>
          <span className="publication-date">{publication.fechaPublicacion}</span>
        </div>
      </div>

      {/* Contenido */}
      <div className="publication-content">
        {/* Sección de Likes */}
        <div className="likes-section">
          <span>👍</span>
          <span>0</span>
        </div>

        {/* Imagen o cuadro vacío */}
        <div className="image-container">
          {publication.imagenBase64 ? (
            <img src={`data:image/png;base64,${publication.imagenBase64}`} alt="Publicación" />
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
