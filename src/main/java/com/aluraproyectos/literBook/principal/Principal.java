package com.aluraproyectos.literBook.principal;

import com.aluraproyectos.literBook.model.*;
import com.aluraproyectos.literBook.repository.AutorRepository;
import com.aluraproyectos.literBook.repository.LibroRepository;
import com.aluraproyectos.literBook.service.ConsumoApi;
import com.aluraproyectos.literBook.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {


    private final String URL_BASE = "https://gutendex.com/books/";
    ConsumoApi consumoApi = new ConsumoApi();
    Scanner scanner = new Scanner(System.in);
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Autor> autores;
    private List<Libro> libros;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros almacenados
                    3 - Listar autores almacenados
                    4- Listar autores vivos por determinado año
                                        
                    0 - Salir del programa
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPortitulo();
                    break;
                case 2:
                    listarLibroRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivorApartirDeAño();
                    break;
                default:
                    System.out.println("Opcion incorrecta");
            }
        }
    }

    public Datos obtenerDatosLibro(){
        System.out.println("Por favor digite el nombre del libro que desea buscar");
        var titulo = scanner.nextLine();
        var json = consumoApi.consumirDatos(URL_BASE + "?search=" + titulo.replace(" ", "+"));
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        return datos;
    }

    public Libro AlmacenarLibro(DatosLibro datosLibro, Autor autor){
        Libro libro = new Libro(datosLibro, autor);
        return libroRepository.save(libro);

    }

    public void buscarLibroPortitulo(){
       Datos datos = obtenerDatosLibro();
       if(!datos.resultados().isEmpty()){
           DatosLibro datosLibro = datos.resultados().get(0);
           System.out.println("Bandera: " + datosLibro );
           DatosAutor datosAutor = datosLibro.autor().get(0);
           System.out.println("Bandera: " + datosAutor);
           Libro libroBuscado = libroRepository.findByTituloIgnoreCase(datosLibro.titulo());

           if(libroBuscado != null){
               System.out.println(libroBuscado);
               System.out.println("El libro buscado ya se encuentra registrado en la base de datos, no se puede volver a registrar");
           }else{
               Autor autorBuscado = autorRepository.findByNombreIgnoreCase(datosAutor.nombre());
               if(autorBuscado == null){
                   Autor autor = new Autor(datosAutor);
                   autorRepository.save(autor);
                   Libro libro = AlmacenarLibro(datosLibro, autor);
                   System.out.println(libro);
               }
           }
       }else{
           System.out.println("El libro buscado no encontrado");
       }
    }

    public void listarLibroRegistrados(){
        libros = libroRepository.findAll();
        if(!libros.isEmpty()){
            libros.forEach(System.out::println);
        }else {
            System.out.println("Ningun libro almacenado en la base de datos, por favor volver a buscar");
        }
    }
    public void listarAutoresRegistrados(){
        autores = autorRepository.findAll();
        if(!autores.isEmpty()){
            autores.forEach(System.out::println);
        }else{
            System.out.println("Ningun autor encontrado en la base de datos, por favor volver a buscar");
        }
    }
    public void listarAutoresVivorApartirDeAño(){
        System.out.println("Por favor ingrese el año");
        var año = scanner.nextInt();
        List<Autor> autoresBuscados = autorRepository.autorVivoEnDerminadoaño(año);
        if(!autoresBuscados.isEmpty()){
            autoresBuscados.forEach(System.out::println);
        }else{
            System.out.println("No hay autores encontrados para ese año");
        }
    }

}
