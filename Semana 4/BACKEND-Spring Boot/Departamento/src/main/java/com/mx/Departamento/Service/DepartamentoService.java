package com.mx.Departamento.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mx.Departamento.Dto.EmpleadoDto;
import com.mx.Departamento.Dto.ProductoDto;
import com.mx.Departamento.Entidad.Departamento;
import com.mx.Departamento.Repositorio.DepartamentoRepo;

@Service // Indica que esta clase es un servicio de Spring
public class DepartamentoService {
	
	@Autowired // Inyecta el repositorio de Departamento
	private DepartamentoRepo dao;
	
	//Metodo para guardar departamento
	public void guardar(Departamento departamento) {
		dao.save(departamento);
	}
	
	//Metodo para listar departamentos
	public List<Departamento> listar(){
		return dao.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
	}
	
	//Metodo para editar departamento
	public void editar(Departamento departamento) {
		dao.save(departamento);
	}
	
	//Metodo para eliminar departamento
	public void eliminar(int idDepartamento) {
		dao.deleteById(idDepartamento);
	}
	
	//Metodo para buscar departamento
	public Departamento buscar(int idDepartamento) {
		return dao.findById(idDepartamento).orElse(null);
	}
	
	//Metodo para lista de departamentos por tienda
	public List<Departamento> listarPorTienda(int tiendaId){
		return dao.findByTiendaId(tiendaId);
	}
	
	//Consumir listar empleados de otro microservicio usando RestTemplate
	@Autowired
	private RestTemplate restTemplate;
	
	//Metodo para listar empleados por departamento
	public List<EmpleadoDto> listarEmpleado(int departamentoId){
		@SuppressWarnings("unchecked")
		
		//Depende del Id del empleaso se llama (todos los atributos de empelado) se pasa a EmpleadoDto
		List<EmpleadoDto> emps = restTemplate.getForObject("http://localhost:8001/empleados/listDepa/"+ departamentoId, List.class);
		return emps;
	}
	
	//Metodo para listar productos por departamento
	public List<ProductoDto> listarProducto(int departamentoId){
		@SuppressWarnings("unchecked")
		
		//Depende del Id del empleaso se llama (todos los atributos de empelado) se guarda a EmpleadoDto
		List<ProductoDto> prods = restTemplate.getForObject("http://localhost:8002/productos/departamento/"+ departamentoId, List.class);
		return prods;
		
	}

}
