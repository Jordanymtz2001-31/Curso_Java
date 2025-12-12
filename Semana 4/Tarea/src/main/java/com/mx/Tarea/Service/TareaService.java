package com.mx.Tarea.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mx.Tarea.Entity.Eestado;
import com.mx.Tarea.Entity.Tareas;
import com.mx.Tarea.Repository.TareaRepository;

@Service //Indica que es un servicio de negocio
public class TareaService {
	
	@Autowired //Inyecta el repositorio de tareas
	private TareaRepository dao;
	
	//Metodo para listar todas las tareas
	public List<Tareas> listarTareas() {
		return dao.findAll(Sort.by(Sort.Direction.ASC, "fechaVencimiento"));
		}
	
	//Metodo para guardar una tarea y validar que la fecha de vencimiento no sea anterior a la fecha de creacion
	public void guardarTarea(Tareas tarea) {
		if(tarea.getFechaVencimiento().isBefore(tarea.getFechaCreacion())) {
			throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior a la fecha de creacion");
		}
		dao.save(tarea);
	}
	
	//Metodo para editar una tarea
	public void editarTarea(Tareas tarea) {
		if(tarea.getFechaVencimiento().isBefore(tarea.getFechaCreacion())) {
			throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior a la fecha de creacion");
		}
		dao.save(tarea);
	}
	
	//Metodo para eliminar una tarea por id
	public void eliminarTarea(int id) {
		dao.deleteById(id);
	}
	
	//Metodo para buscar una tarea por id
	public Tareas buscar(int id) {
		return dao.findById(id).orElse(null);
	}
	
	//Metodo para cambiar el estado 
	public Tareas cambiarEstado(int id, String nuevoEstado) {
		//Verificamos que el estado sea valido
		if (!nuevoEstado.equals("PENDIENTE") && !nuevoEstado.equals("EN_PROGRESO") && !nuevoEstado.equals("COMPLETADA")) {
			throw new IllegalArgumentException("Estado no valido" + nuevoEstado);
		}
		
		//Buscamos la tarea por id para cambiar su estado
		Tareas tarea = buscar(id);
		
		if (tarea == null) {
			throw new IllegalArgumentException("Tarea no encontrada con id: " + id);
		}else {
			//Cambiamos el estado de la tarea
			tarea.setEstado(Eestado.valueOf(nuevoEstado));
			//Guardamos la tarea con el nuevo estado
			dao.save(tarea);
			return tarea; //Al final regresamos la tarea con el nuevo estado
		}
		
	}
	


}
