package com.aluraproyectos.literBook.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    // Esta anotación indica que el atributo 'Id' será la clave primaria de la entidad
    @Id
    // Esta anotación especifica que el valor del 'Id' será generado automáticamente por la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private int descargas;
    //cascade: indica que si hubo un cambio en episodios se debe reflejar en episodios(Indica cambios en cascada)
    @ManyToOne()
    private Autor autor;

    private Libro(){}
    public Libro(DatosLibro datosLibro, Autor autor){
        this.titulo = datosLibro.titulo();
        this.descargas = datosLibro.descargas();
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libro{" +
                ", titulo='" + titulo + '\'' +
                ", descargas=" + descargas +
                '}';
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
