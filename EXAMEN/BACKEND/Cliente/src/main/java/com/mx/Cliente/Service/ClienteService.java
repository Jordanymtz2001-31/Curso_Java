package com.mx.Cliente.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mx.Cliente.Dto.MascotaDto;
import com.mx.Cliente.Entity.Cliente;
import com.mx.Cliente.Repository.ClienteRepository;

@Service //Indicamos que esta clase es un servicio
public class ClienteService {
	
	@Autowired
	private ClienteRepository dao;
	
	//Metodo para listar
		public List<Cliente> listar(){
			return dao.findAll(Sort.by(Sort.Direction.ASC,"idCliente"));
		}
		
		//Metodo para guardar
		public void guardar(Cliente cliente) {
			dao.save(cliente);
		}
		
		//Metodo para editar
		public void editar(Cliente cliente) {
			dao.save(cliente);
		}
		
		//Metodo para buscar por ID
		public Cliente buscarId(Integer idCliente) {
			return dao.findById(idCliente).orElse(null);
		}
		
		//Metodo para eliminar
		public void eliminar(Integer idCliente) {
			 dao.deleteById(idCliente);
		}
		
		//METODOS PERSONALIZADOS------------------------------------------------------------------------------------------
		
		//Metodo para buscar por nombre
		public Cliente buscar(String nombre) {
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
		
		//Metodo para validar si existe ya el nombre
		public boolean existeDireccion(String direccion) {
			return dao.existsByDireccionAllIgnoringCase(direccion);
		}
		
		//METODO QUE CONSUMIRAN A OTRO MICROSERVICIO CON RestTemplate------------------------------------------------------
		@Autowired
		private RestTemplate restTemplate;
		
		//Metodo para listar mascotas por cliente
		public List<MascotaDto> listarMascotas(int clienteId){
			@SuppressWarnings("unchecked")
			
			List<MascotaDto> mascotas = restTemplate.getForObject("http://localhost:8002/mascota/cliente/"+ clienteId, List.class);
			return mascotas;
		}


}
