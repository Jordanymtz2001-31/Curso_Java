package com.mx.Tienda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.mx.Tienda.Entity.Tienda;
import com.mx.Tienda.Entity.Etipos;
import com.mx.Tienda.Repository.TiendaRepository;
import com.mx.Tienda.Service.TiendaService;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application.properties") //Indicamos la ubicacion del archivo de propiedades
@Transactional //Nos ayuda a hacer rollback despues de cada prueba
public class TestIntegration {
	
	//Inyectamos los componentes necesarios para las pruebas de integracion
	
	@Autowired
	private TiendaService service;
	
	@Autowired
	private TiendaRepository dao;
	
	@BeforeEach //Antes de cada prueba
	void setUp() {
		dao.deleteAll(); //Eliminamos todos los registros antes de cada prueba para no g
	}
	
	//TEST de guardar tienda
	void guardarTiendaTest() throws Exception  {
		//Instanciamos una tienda
		Tienda tienda = new Tienda("T1","Tienda A", "Calle 123", Etipos.SUPERMERCADO, "Ciudad X");
		
		//Guardamos la tienda en la base de datos
		service.guardar(tienda);
		
		//Validad si se guardo correctamente, verificamos si la tienda existe en la base de datos
		//por el id se asigna automaticamente
		assertTrue(tienda.getIdTienda() > 0, "La tienda no se guardo correctamente");
		
		//Buscar la tienda guardado en la base de datos
		Tienda guardado = dao.findById(tienda.getIdTienda()).orElseThrow(()-> new AssertionError("La tienda no se encontro en la base de datos"));
		
		//Verificar que los datos del cliente guardado sean correctos
		assertNotNull(guardado);
		assertEquals("T1", guardado.getCodigo());
		assertEquals("Tienda A", guardado.getNombre());
		assertEquals("Calle 123", guardado.getDireccion());
		assertEquals(Etipos.SUPERMERCADO, guardado.getTipo());
		assertEquals("Ciudad X", guardado.getCiudad());
		System.out.println("Cliente guardado correctamente: " + guardado);
	
		
	}
	

}
