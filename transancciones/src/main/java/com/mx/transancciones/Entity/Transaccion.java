package com.mx.transancciones.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data //Para contruir los getters, setter,contrsucotres y demas
@Table(name = "transaccion") //Indica el que es una tabla de base de datos
@Entity //Indica que es una entidad de JPA
public class Transaccion {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operacion;
    private String importe;
    private String cliente;
    private String referencia;
    private String estatus;   // "Aprobada" o "Cancelada"

    public Transaccion() {}   // necesario para JPA

}
