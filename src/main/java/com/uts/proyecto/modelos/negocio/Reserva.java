/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.modelos.negocio;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author otalo
 */
@Entity
@Table(name = "RESERVA")
public class Reserva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReserva")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idLugar")
    private Lugar lugar;

    @Column(name = "fechaInicial")
    private Date startDate;

    @Column(name = "fechaFinal")
    private Date endDate;

    @Column(name = "horaInicial")    
    private String timeReservedStart;

    @Column(name = "horaFinal")   
    private String timeReservedEnd;

    @Column(name = "capacidad")   
    private String capacidad;
    
    @Column(name = "estado")
    private Character status;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTimeReservedStart() {
        return timeReservedStart;
    }

    public void setTimeReservedStart(String timeReservedStart) {
        this.timeReservedStart = timeReservedStart;
    }

    public String getTimeReservedEnd() {
        return timeReservedEnd;
    }

    public void setTimeReservedEnd(String timeReservedEnd) {
        this.timeReservedEnd = timeReservedEnd;
    }   

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }   

}
