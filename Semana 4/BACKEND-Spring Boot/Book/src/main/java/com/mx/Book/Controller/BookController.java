package com.mx.Book.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Book.Dominio.Book;

@RestController //Indica que esta clase es un controlador REST
@CrossOrigin(origins = "*") //Permite solicitudes CORS desde cualquier origen
@RequestMapping("/books") //Mapea las solicitudes HTTP a /books
public class BookController {
	
	//Creamos una lista estática de libros como datos de ejemplo
	private static List<Book> listaBooks = new ArrayList<>();
	
	static {
		listaBooks.add(new Book(1, "El principito", "Antoine de Saint-Exupéry", 15.5));
		listaBooks.add(new Book(2, "Cien años de soledad", "Gabriel García Márquez", 25.0));
	}
	
	//Metodo para listar todos los libros
	@GetMapping("/listar") 
	public List<Book> listarBooks() {
		return listaBooks;
	}
	
	//Metodo para buscar un libro por su ID
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarBookPorId(@PathVariable Integer id) {
		//Iteramos sobre la lista para encontrar el libro con el ID proporcionado
		for (Book book : listaBooks) {
			if (book.getId().equals(id)) { //Compara el ID del libro con el ID proporcionado
				return ResponseEntity.ok(book); //Retorna el libro si se encuentra
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Libro no encontrado\"}");
	}

}
