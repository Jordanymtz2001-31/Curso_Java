package com.mx.Book.Dominio;

public class Book {
	private Integer id;
	private String titulo;
	private String autor;
	private Double precio;
	
	
	
	// Constructor
	public Book() {
		super();
	}
	
	public Book(Integer id, String titulo, String autor, Double precio) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.precio = precio;
	}
	
	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", precio=" + precio + "]";
	}
	
	
	

}
