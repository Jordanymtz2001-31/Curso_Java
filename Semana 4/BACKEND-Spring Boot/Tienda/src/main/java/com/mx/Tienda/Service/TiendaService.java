package com.mx.Tienda.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.mx.Tienda.Dtos.ClienteDto;
import com.mx.Tienda.Dtos.DepartamentoDto;
import com.mx.Tienda.Dtos.EmpleadoDto;
import com.mx.Tienda.Dtos.ProductoDto;
import com.mx.Tienda.Dtos.ProveedorDto;
import com.mx.Tienda.Entity.Tienda;
import com.mx.Tienda.FeingConfig.ClientesFeignClient;
import com.mx.Tienda.FeingConfig.DepartamentoFeingClient;
import com.mx.Tienda.FeingConfig.EmpleadoFeingClient;
import com.mx.Tienda.FeingConfig.ProductoFeingClient;
import com.mx.Tienda.FeingConfig.ProveedorFeingClient;
import com.mx.Tienda.Repository.TiendaRepository;

@Service //Indica que esta clase es un servicio 
public class TiendaService {
	
	@Autowired //Inyeccion de dependencias
	private TiendaRepository dao;
	
	//Metodo para listar todas las tiendas
	public List<Tienda> listarTiendas() {
		return dao.findAll(Sort.by(Sort.Direction.ASC, "idTienda"));
	}
	
	//Metdo para editar una tienda
	public void editarTienda(Tienda tienda) {
		dao.save(tienda);
	}
	
	//Metodo para guardar una tienda
	public void guardar(Tienda tienda) throws Exception {
		
		validarDatos(tienda); //Llama al metodo para validar los datos
		try {
			dao.save(tienda);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException(mensajeError(e));
		}
	}
	
	//Metodo para eliminar una tienda
	public void eliminarTienda(int idTienda) {
		dao.deleteById(idTienda);
	}
	
	//Metodo para buscar una tienda por id
	public Tienda buscarTienda(int idTienda) {
		return dao.findById(idTienda).orElse(null);
	}
	
	//---------------------------------------------------------------------------------------------
	
	//Metodo para buscar una tienda por nombre
	public Tienda buscarTiendaPorNombre(String nombre) {
		return dao.findByNombre(nombre); //Llama al metodo del repositorio buscar por nombre
	}
	
	//Metodo para buscar tiendas por ciudad
	public List<Tienda> buscarPorCiudad(String ciudad) {
		return dao.findByCiudadIgnoreCase(ciudad); //Llama al metodo del repositorio buscar por ciudad
	}
	
	//Metodo para validar si existe el codigo de la tienda
	public boolean existeCodigo(String codigo) {
		return dao.existsByCodigoIgnoreCase(codigo); //Llama al metodo del repositorio existe por codigo
	}
	
	//Metodo para validar si existe el nombre de la tienda
	public boolean existeNombre(String nombre) {
		return dao.existsByNombreIgnoreCase(nombre); //Llama al metodo del repositorio existe por nombre
	}
	
	//Metodo para validar si existe la direccion de la tienda
	public boolean existeDireccion(String direccion) {
		return dao.existsByDireccionIgnoreCase(direccion); //Llama al metodo del repositorio existe por direccion
	}
	
	//Metodo para validar todas las validaciones
	public void validarDatos(Tienda tienda) throws IllegalArgumentException  {
		if (existeCodigo(tienda.getCodigo())) {
			throw new IllegalArgumentException ("El codigo de la tienda ya existe");
		}
		if (existeNombre(tienda.getNombre())) {
			throw new IllegalArgumentException ("El nombre de la tienda ya existe");
		}
		if (existeDireccion(tienda.getDireccion())) {
			throw new IllegalArgumentException ("La direccion de la tienda ya existe");
		}
	}
	
	private String mensajeError(DataIntegrityViolationException e) {
		if(e.getCause() == null) {
			return "Error al registrar la tienda";
		}
		
		String mensaje = e.getRootCause().getMessage().toLowerCase();
		
		//Comparamos los valores del usuario con los que estan en la base de datos
		if(mensaje.contains("nombre") || mensaje.contains("nombre")) {
			return "El nombre de la tienda ya existe";
		}else if(mensaje.contains("codigo") || mensaje.contains("codigo")) {
			return "El codigo de la tienda ya existe";
		}else if(mensaje.contains("direccion") || mensaje.contains("direccion")) {
			return "La direccion de la tienda ya existe";
		}
		
		return "Los datos contienen informacion duplicada o invalida";
	}
	
	
//----------------------------------------------------------------------------------------------
	//METODO DE OTRO MICROSERVICIO USANDO FEIGN CLIENT
	
	
	
	@Autowired //Inyeccion de dependencias
	private DepartamentoFeingClient depaFC;
	
	@Autowired //Inyeccion de dependencias
	private EmpleadoFeingClient empleadoFC;
	
	@Autowired //Inyeccion de dependencias
	private ProveedorFeingClient proveedorFC;
	
	@Autowired //Inyeccion de dependencias
	private ClientesFeignClient clienteFC;
	
	@Autowired //Inyeccion de dependencias
	private ProductoFeingClient productoFC;
	
	//Metodo para listar Departamentos por tienda
	public List<DepartamentoDto> listarDepartamenXTienda(int tiendaId){
		return depaFC.listarPorTienda(tiendaId);
	}
	 
	//Metodo para listar Empleados por tienda
	public List<EmpleadoDto> listarEmpleadosXTienda(int tiendaId){
		return empleadoFC.ListarPorTiendaId(tiendaId);
	}
	
	//Metodo para listar Proveedores por tienda
	public List<ProveedorDto> listarProveedoresXTienda(int tiendaId){
		return proveedorFC.listarPorTienda(tiendaId);
	}
	
	//Metodo para listar Clientes por tienda
	public List<ClienteDto> listarClientesXTienda(int tiendaId){
		return clienteFC.buscarXTienda(tiendaId);
	}
	
	//Metodo para listar Productos por tienda
	public List<ProductoDto> listarProductosXTienda(int tiendaId){
		return productoFC.ListarProductosPorTienda(tiendaId);
	}
	
	//Metodo para mostrar la informacion de todo en consulta
		
	public Map<String, Object> mostrarInfo(int tiendaId) {
	    Map<String, Object> resul = new HashMap<>();
	    
	    //Primero validar si existe la tienda
	    Tienda tienda = dao.findById(tiendaId).orElse(null);
	    
	    if (tienda == null) {
	        resul.put("Error", "La tienda no existe");
	    }else {
	    	resul.put("Tienda", tienda);
	    	
	    	try {
	    		//Validar si existen departamentos
		    	List<DepartamentoDto> departamentos = listarDepartamenXTienda(tiendaId);
		    	if (departamentos.isEmpty()) {
		    		resul.put("Departamentos", "No hay departamentos en la tienda con ID: " + tiendaId);
		    	}else {
		    		resul.put("Departamentos", departamentos);
		    	}
	    	} catch (Exception e) {
	    		resul.put("Error", "Ocurrio un error al obtener los departamentos: " + e.getMessage());
	    	}
	    	
	    	try {
	    		//Validar si existen empleados
		    	List<EmpleadoDto> empleados = listarEmpleadosXTienda(tiendaId);
		    	if (empleados.isEmpty()) {
		    		resul.put("Empleados", "No hay empleados en la tienda con ID: " + tiendaId);
		    	}else {
		    		resul.put("Empleados", empleados);
		    	}
	    	} catch (Exception e) {
	    		resul.put("Error", "Ocurrio un error al obtener los departamentos: " + e.getMessage());
	    	}
	    	
	    	try {
	    		//Validar si existen proveedores
		    	List<ProveedorDto> proveedores = listarProveedoresXTienda(tiendaId);
		    	if (proveedores.isEmpty()) {
		    		resul.put("Proveedores", "No hay proveedores en la tienda con ID: " + tiendaId);
		    	}else {
		    		resul.put("Proveedores", proveedores);
		    	}
	    	} catch (Exception e) {
	    		resul.put("Error", "Ocurrio un error al obtener los departamentos: " + e.getMessage());
	    	}
	    	
	    	try {
	    		//Validar si existen clientes
		    	List<ClienteDto> clientes = listarClientesXTienda(tiendaId);
		    	if (clientes.isEmpty()) {
		    		resul.put("Clientes", "No hay clientes en la tienda con ID: " + tiendaId);
		    	}else {
		    		resul.put("Clientes", clientes);
		    	}
	    	} catch (Exception e) {
	    		resul.put("Error", "Ocurrio un error al obtener los departamentos: " + e.getMessage());
	    	}
	    	
	    	try {
	    		//Validar si existen productos
		    	List<ProductoDto> productos = listarProductosXTienda(tiendaId);
		    	if (productos.isEmpty()) {
		    		resul.put("Productos", "No hay productos en la tienda con ID: " + tiendaId);
		    	}else {
		    		resul.put("Productos", productos);
		    	}
	    	} catch (Exception e) {
	    		resul.put("Error", "Ocurrio un error al obtener los departamentos: " + e.getMessage());
	    	}
	    }
		return resul;
	}
	
	

}
















