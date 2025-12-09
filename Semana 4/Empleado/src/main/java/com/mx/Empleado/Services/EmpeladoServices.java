package com.mx.Empleado.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mx.Empleado.Entidad.Empelado;
import com.mx.Empleado.Repositorio.EmpleadoRepositorio;

@Service // Indica que esta clase es un servicio de Spring
public class EmpeladoServices {
	
	@Autowired // Inyecta el repositorio de empleados
	private EmpleadoRepositorio dao;
	
	
	//Metodo para guardar un empleado
	public void guadar(Empelado empleado) {
		dao.save(empleado);
	}
	
	//Metodo para editar un empleado
	public void editar(Empelado empleado) {
		dao.save(empleado);
	}
	
	//Metodo para eliminar un empleado por id
	public void eliminar(int id) {
		dao.deleteById(id);
	}
	
	//Metodo para buscar un empleado por id
	public Empelado buscar(int id) {
		return dao.findById(id).orElse(null);
	}
	
	//Metodo para listar todos los empleados ordenados por nombre ascendente
	public List<Empelado> listar(){
		return dao.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
	}

	//Metodo para validar si exite un empleado por su nombre, apellidoP y apellidoM
	public boolean existeEmpleado(String nombre, String apellidoP, String apellidoM) {
		return dao.existsByNombreAndApellidoPAndApellidoMAllIgnoringCase(nombre, apellidoP, apellidoM);
	}
	
	//Metodo para listar empleados por departamentoId
	public List<Empelado> listarPorDepartamentoId(int departamentoId){
		return dao.findByDepartamentoId(departamentoId);
	}
	
	//Metodo para listar empleados por tiendaId
	public List<Empelado> listarPorTiendaId(int tiendaId){
		return dao.findByTiendaId(tiendaId);
	}
	
	//Metodo para validar si existe el numero de telefono
	public boolean existeTelefono(Long telefono) {
		return dao.existsByTelefono(telefono);
	}

}
