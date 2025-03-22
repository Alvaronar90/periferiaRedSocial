package com.example.periferia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "publicaciones")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private long usuarioId;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false,insertable = false, updatable = false)
    private User usuario;

    @Column(nullable = false, length = 1000)
    private String titulo;

    @Column(nullable = false)
    private String texto;

    @Column(name = "imagen_base64")
    private String imagenBase64;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es-CO", timezone = "America/Bogota")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_publicacion", nullable = false, updatable = false)
    private Date fechaPublicacion;

    public Publication() {
    }

    public Publication(Long id, Date fechaPublicacion, String imagenBase64, String titulo, User usuario, String texto) {
        this.id = id;
        this.fechaPublicacion = fechaPublicacion;
        this.imagenBase64 = imagenBase64;
        this.titulo = titulo;
        this.usuario = usuario;
        this.texto = texto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }
}

