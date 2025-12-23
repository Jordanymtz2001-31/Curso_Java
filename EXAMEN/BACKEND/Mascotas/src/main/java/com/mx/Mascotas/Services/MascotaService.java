package com.mx.Mascotas.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mx.Mascotas.Entity.Mascota;
import com.mx.Mascotas.Repository.MascotaRepository;

@Service //Indica que esta clase es un servicio 
public class MascotaService {
	
	@Autowired //Inyectamos la dependencia del Repository
	private MascotaRepository dao;
	
	//Metodo para listar todas las mascotas
	public List<Mascota> listar(){
		return dao.findAll(Sort.by(Sort.Direction.ASC, "idMascota"));
	}
	
	//Metodo para editar
	public void editar(Mascota mascota) {
		dao.save(mascota);
	}
	
	//Metodo para guardar
	public void guardar(Mascota mascota) {
		dao.save(mascota);
	}
	
	//Metodo para eliminar
	public void eliminar(int idMascota) {
		dao.deleteById(idMascota);
	}
	
	//Metodo para buscar por ID
	public Mascota buscarId(int idMascota) {
		return dao.findById(idMascota).orElse(null);
	}
	
	
	//METODOS PERSONALIZADOS
	
	//Metodo para validar si existe la mascota
	public boolean existeMascota(String nombre) {
		return dao.existsByNombreAllIgnoringCase(nombre);
	}
	
	//Metodo para buscar
	public Mascota buscar(String nombre) {
		return dao.findByNombre(nombre);
	}
	
	//Metodo para listar por Id de Cliente
	public List<Mascota> listarIDCliente(int clienteId){
		return dao.findByClienteId(clienteId);
	}
	
	//Metodo para listar por Id de Veterinaria
	public List<Mascota> listarIDVeterinaria(int veterinariaId){
		return dao.findByveterinariaId(veterinariaId);
	}
	
	//Metodo para listar por Id de Responsable
	public List<Mascota> listarIDResponsable(int responsableId){
		return dao.findByresponsableId(responsableId);
	}
	
}
