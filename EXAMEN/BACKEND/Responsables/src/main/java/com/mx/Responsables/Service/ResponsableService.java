package com.mx.Responsables.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mx.Responsables.Dto.MascotaDto;
import com.mx.Responsables.Entity.Responsable;
import com.mx.Responsables.Repository.ResponsableRepository;

@Service //Indicamos que esta clase es un servicio
public class ResponsableService {
	
	@Autowired //Inyectamos la dependencia del Repositorio
	private ResponsableRepository dao;
	
	//Metodo para listar
	public List<Responsable> listar(){
		return dao.findAll(Sort.by(Sort.Direction.ASC,"idResponsable"));
	}
	
	//Metodo para guardar
	public void guardar(Responsable responsable) {
		dao.save(responsable);
	}
	
	//Metodo para editar
	public void editar(Responsable responsable) {
		dao.save(responsable);
	}
	
	//Metodo para buscar por ID
	public Responsable buscarId(Integer idResponsable) {
		return dao.findById(idResponsable).orElse(null);
	}
	
	//Metodo para eliminar
	public void eliminar(Integer idResponsable) {
		 dao.deleteById(idResponsable);
	}
	
	//------------------------------------------------------------------------------------------
	
	//Metodo para buscar por nombre
	public Responsable buscar(String nombre) {
		return dao.findByNombre(nombre);
	}
	
	//Metodo para validar telefono
	public boolean existeTelefono(Long contacto) {
		return dao.existsByContacto(contacto);
	}
	
	//Metodo para validar si existe ya el nombre
	public boolean existeNombre(String nombre) {
		return dao.existsByNombreAllIgnoringCase(nombre);
	}
	
	//Listar responsable por IDVeterinaria
	public List<Responsable> listarXVeterinaria(int veterinariaId){
		return dao.findByVeterinariaId(veterinariaId);
	}
	
	//METODO QUE CONSUMIRAN A OTRO MICROSERVICIO CON RestTemplate------------------------------------------------------
	@Autowired
	private RestTemplate restTemplate;
	
	//Metodo para listar mascotas por responsable
	public List<MascotaDto> listarMascotas(int responsableId){
		@SuppressWarnings("unchecked")
		
		//Creamos una lista para guardar las mascotas asociadas a un responsable específico
		List<MascotaDto> mascotas = restTemplate.getForObject("http://localhost:8002/mascota/responsable/"+ responsableId, List.class);
			return mascotas;
	}

}
