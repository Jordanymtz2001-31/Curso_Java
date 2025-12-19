package com.mx.Cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.mx.Cliente.Entidad.Cliente;
import com.mx.Cliente.Repositorio.ClienteRepository;
import com.mx.Cliente.Service.ClienteImplementacion;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application.properties") //Indicamos la ubicacion del archivo de propiedades
@Transactional //Nos ayuda a hacer rollback despues de cada prueba
public class TestIntegracion {
	
	@Autowired //Inyectamos el servicio
	private ClienteImplementacion service;
	
	@Autowired //Inyectamos el repositorio
	private ClienteRepository dao;
	
	@BeforeEach //Antes de cada prueba
	void setUp() {
		dao.deleteAll(); //Eliminamos todos los registros antes de cada prueba
	}
	
	
	/*
	 * //TESTS Guardar cliente
	 * 
	 * @Test void guardarClienteTest() {
	 * 
	 * //Instancia de cliente Cliente cliente = new Cliente("juan","martinez",
	 * "jimenez", 25, 2331086955L, 1);
	 * 
	 * //Si ocurre guardar en la base de datos service.guardar(cliente);
	 * 
	 * //Validad si se guardo correctamente, verificamos si el cliente existe en la
	 * base de datos //por el id se asigna automaticamente
	 * assertTrue(cliente.getId() > 0, "El cliente no se guardo correctamente");
	 * 
	 * //Buscar el cliente guardado en la base de datos Cliente guardado =
	 * dao.findById(cliente.getId()).orElseThrow(()-> new
	 * AssertionError("El cliente no se encontro en la base de datos"));
	 * 
	 * //Verificar que los datos del cliente guardado sean correctos
	 * assertNotNull(guardado); assertEquals("juan", guardado.getNombre());
	 * assertEquals("martinez", guardado.getApellidoP()); assertEquals("jimenez",
	 * guardado.getApellidoM()); assertEquals(25, guardado.getEdad());
	 * assertEquals(2331086955L, guardado.getTelefono()); assertEquals(1,
	 * guardado.getTiendaId());
	 * System.out.println("Cliente guardado correctamente: " + guardado); }
	 * 
	 * //TEST buscar por Id
	 * 
	 * @Test void buscarClientePorIdTest() { //Instancia de cliente Cliente cliente
	 * = new Cliente("pedro","lopez", "gomez", 30, 2331086956L, 2);
	 * 
	 * //Guardar el cliente en la base de datos service.guardar(cliente);
	 * 
	 * int idGuardado = cliente.getId();
	 * 
	 * Optional<Cliente> encontrado = dao.findById(idGuardado);
	 * 
	 * assertTrue(encontrado.isPresent(), "Deberia encontrar Por ID");
	 * assertEquals("pedro", encontrado.get().getNombre());
	 * System.out.println(encontrado); }
	 * 
	 * //Si existe
	 * 
	 * @Test void testBuscarPorIdNoExiste() { Optional<Cliente> noEncontrado =
	 * dao.findById(360);
	 * 
	 * assertFalse(noEncontrado.isPresent(), "no deberia encontrar el cliente");
	 * System.out.print(noEncontrado); }
	 * 
	 * //TEST de Listar
	 * 
	 * @Test void listarClientesTest() { service.guardar(new Cliente("Ana",
	 * "Matinez", "Gonzalez", 22, 2331286944L, 1)); service.guardar(new
	 * Cliente("Juan", "Matinez", "Gonzalez", 25, 4331286944L, 1));
	 * service.guardar(new Cliente("Maria", "Matinez", "Gonzalez", 12, 8331286944L,
	 * 1));
	 * 
	 * //Creamos una lista para almacenar los clientes List<Cliente> clients =
	 * dao.findAll();
	 * 
	 * assertEquals(3, clients.size(), "Dberian existir 3 clientes");
	 * assertTrue(clients.stream().anyMatch(c -> "Ana".equals(c.getNombre())));
	 * assertTrue(clients.stream().anyMatch(c -> "Juan".equals(c.getNombre())));
	 * assertTrue(clients.stream().anyMatch(c -> "Maria".equals(c.getNombre())));
	 * 
	 * System.out.print(clients);
	 * 
	 * }
	 * 
	 * //TEST de editar
	 * 
	 * @Test void editarClienteTest() { Cliente cli = new Cliente("Diana",
	 * "Martinez", "Perez", 36, 2331045699L, 1); service.editar(cli); int encontrado
	 * = cli.getId();
	 * 
	 * cli.setNombre("Yami"); cli.setApellidoP("Hernandez"); service.editar(cli);
	 * 
	 * Cliente actualizado = dao.findById(encontrado).orElseThrow(()-> new
	 * AssertionError("El cliente deberia existeir despues del actualizar"));
	 * 
	 * //Verificamos que los datos que no se modificaron siguen siendo los mismos
	 * assertEquals("Perez", actualizado.getApellidoM()); assertEquals(2331045699L,
	 * actualizado.getTelefono()); System.out.print(actualizado); }
	 * 
	 * //TEST para eliminar cliente
	 * 
	 * @Test void eliminarClientesTest() { //Instanciamos para crear un objeto
	 * Cliente cli = new Cliente("Diana", "Martinez", "Perez", 36, 2331045699L, 1);
	 * 
	 * //Guadamos para depues liminarlos service.guardar(cli);
	 * 
	 * //Nuscamos por ID int encontrarId = cli.getId();
	 * 
	 * assertTrue(dao.findById(encontrarId).isPresent(),
	 * "El id deberia existir antes de eliminar"); System.out.print(cli);
	 * 
	 * //Ahora lo eliminamos service.eliminar(cli.getId());
	 * 
	 * Optional<Cliente> eliminado = dao.findById(encontrarId);
	 * assertFalse(eliminado.isPresent()
	 * ,"El id no deberia de existir despues de eliminar");
	 * System.out.print("Despues de eliminar" + eliminado);
	 * 
	 * 
	 * }
	 * 
	 * //Validar que si se esta haciendo un Rollback
	 * 
	 * @Test void pruebaTransacionalTest() { long before = dao.count();
	 * 
	 * Cliente test = new Cliente("Test", "Transacional", "Trans", 36, 2331045699L,
	 * 1);
	 * 
	 * service.guardar(test); long after = dao.count();
	 * 
	 * assertEquals(before + 1, after,
	 * "Deberia de existir un clientes mas antes de un rockball");
	 * 
	 * }
	 * 
	 * //Verificar que todos los registros que creo que contienen un id diferente
	 * 
	 * @Test void testIntegrationCliente() { Cliente cl = new Cliente("Ana",
	 * "Matinez", "Gonzalez", 22, 2331286944L, 1); Cliente cl1 = new Cliente("Juan",
	 * "Matinez", "Gonzalez", 25, 4331286944L, 1); Cliente cl2 = new
	 * Cliente("Maria", "Matinez", "Gonzalez", 12, 8331286944L, 1);
	 * 
	 * service.guardar(cl); service.guardar(cl1); service.guardar(cl2);
	 * 
	 * List<Cliente> lis = dao.findAll();
	 * 
	 * assertEquals(3, lis.size(), "Deberian se der 3");
	 * 
	 * long idsDifrentes = lis.stream().map(Cliente::getId).distinct().count();
	 * assertEquals(3, idsDifrentes, "Todos deberian de tener IDs diferentes"); }
	 */

}
