package com.mx.Producto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mx.Producto.Entidad.Producto;
import com.mx.Producto.Repositorio.ProductoRepositorio;

@Service // Indica que esta clase es un servicio de Spring
public class ProductoService {
	
	@Autowired // Inyecta el repositorio de productos
	private ProductoRepositorio dao;
	
	//Metodo de listar todos los productos
	public List<Producto> listar(){
		return dao.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
	}
	
	//Metodo para guardar un producto
	public void guardar(Producto producto) {
		dao.save(producto);
	}
	
	//Metodo para editar un producto
	public void editar(Producto producto) {
		dao.save(producto);
	}
	
	//Metodo para eliminar un producto por id
	public void eliminar(int id) {
		dao.deleteById(id);
	}
	
	//Metodo para buscar un producto por id
	public Producto buscar(int id) {
		return dao.findById(id).orElse(null);
	}
	
	//Metodo para validar si exite un producto por su nombre
	public boolean existeProducto(String nombre) {
		return dao.existsByNombreAllIgnoringCase(nombre);
	}
	
	//Metodo para listar productos por departamentoId
	public List<Producto> listarPorDepartamentoId(int depaId){
		return dao.findByDepaId(depaId);
	}
	
	//Metodo para listar productos por tiendaId
	public List<Producto> listarPorTiendaId(int tiendaId){
		return dao.findByTiendaId(tiendaId);
	}
	
	

}
