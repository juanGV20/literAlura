package com.aluraproyectos.literBook.service;

public interface IConvierteDatos {
    //interfaz generica para convertir datos en formato Json a un objeto
    <T> T obtenerDatos(String json, Class <T> clase);
}
