package com.aluraproyectos.literBook.repository;

import com.aluraproyectos.literBook.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository <Libro, Long> {

    Libro findByTituloIgnoreCase(String titulo);

}
