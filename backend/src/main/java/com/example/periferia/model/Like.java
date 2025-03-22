package com.example.periferia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "publication_id")
    Integer idPublication;
    @ManyToOne
    @JoinColumn(name = "publication_id", nullable = false,insertable = false, updatable = false)
    private Publication publication;
    @Column(name = "user_id")
    Integer idUser;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,insertable = false, updatable = false)
    private User usuario;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es-CO", timezone = "America/Bogota")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_publicacion", nullable = false, updatable = false)
    private Date fechaPublicacion;

    public Like() {

    }

    public Like(Long id, Integer idPublication, Integer idUser, Date fechaPublicacion) {
        this.id = id;
        this.idPublication = idPublication;
        this.idUser = idUser;
        this.fechaPublicacion = fechaPublicacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(Integer idPublication) {
        this.idPublication = idPublication;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}
