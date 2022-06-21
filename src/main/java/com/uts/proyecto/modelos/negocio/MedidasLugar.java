/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.modelos.negocio;

import java.io.Serializable;
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
@Table(name = "MEDIDASBIOSEGURIDADLUGAR")
public class MedidasLugar implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMedidasbiseguirdadLugar")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idLugar")
    private Lugar lugar;

    @ManyToOne
    @JoinColumn(name = "idMedidas")
    private MedidasProteccion medidasProteccion;

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

    public MedidasProteccion getMedidasProteccion() {
        return medidasProteccion;
    }

    public void setMedidasProteccion(MedidasProteccion medidasProteccion) {
        this.medidasProteccion = medidasProteccion;
    }    
    
}
