package com.mx.Veterinaria.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mx.Veterinaria.Dtos.MascotaDto;
import com.mx.Veterinaria.Dtos.ResponsableDto;
import com.mx.Veterinaria.Entity.Veterinaria;
import com.mx.Veterinaria.FeingConfig.MascotaFeignClient;
import com.mx.Veterinaria.FeingConfig.ResponsableFeignClient;
import com.mx.Veterinaria.Repository.VeterinariRepository;

@Service
public class VeterinariaService {
	
	@Autowired //inyectamos la dependencias 
	private VeterinariRepository dao;
	
	//Metodo para listar
	public List<Veterinaria> listar(){
		return dao.findAll(Sort.by(Sort.Direction.ASC,"idVeterinaria"));
	}
		
	//Metodo para guardar
	public void guardar(Veterinaria veterinaria) {
		dao.save(veterinaria);
	}
		
	//Metodo para editar
	public void editar(Veterinaria veterinaria) {
		dao.save(veterinaria);
	}
		
	//Metodo para buscar por ID
	public Veterinaria buscarId(Integer idVeterinaria) {
		return dao.findById(idVeterinaria).orElse(null);
	}
		
	//Metodo para eliminar
	public void eliminar(Integer idVeterinaria) {
		 dao.deleteById(idVeterinaria);
	}
		
	//------------------------------------------------------------------------------------------
		
	//Metodo para buscar por nombre
	public Veterinaria buscar(String nombre) {
		return dao.findByNombre(nombre);
	}
		
	//Metodo para validar telefono
	public boolean existeTelefono(Long telefono) {
		return dao.existsByTelefono(telefono);
	}
		
	//Metodo para validar si existe ya el nombre
	public boolean existeNombre(String nombre) {
		return dao.existsByNombreAllIgnoringCase(nombre);
	}
		
	//Metodo para validar si existe ya la direccion
	public boolean existeDireccion(String direccion) {
		return dao.existsByDireccionAllIgnoringCase(direccion);
	}
	
//----------------------------------------------------------------------------------------------
	//METODO DE OTRO MICROSERVICIO USANDO FEIGN CLIENT
	
	@Autowired //Inyeccion de Dependencias
	private MascotaFeignClient mascoFC;
	
	@Autowired //Inyeccion de Dependencias
	private ResponsableFeignClient respFC;
	
	//Metodo para listar mascotas por Veterinaria
	public List<MascotaDto> listarMXVeterinaria(int veterinariaId){
		return mascoFC.listarMPorVeterinaria(veterinariaId);
	}
	
	//Metodo para listar Responsables por Veterinaria
	public List<ResponsableDto> listarRXVeterinaria(int veterinariaId){
		return respFC.listarRPorVeterinaria(veterinariaId);
	}
}
