/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.modelos.negocio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author otalo
 */
@Entity
@Table(name = "LUGAR")
public class Lugar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLugar")
    private Long id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "latitud")
    private String latitude;

    @Column(name = "longitud")
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "id")
    private Propietario propietario;

    @Column(name = "precio")
    private String price;
    
    @Column(name = "capacidad")
    private String capacidad;

    @ManyToOne
    @JoinColumn(name = "idCategoriaEstado")
    private CategoriaServicio categoriaServicio;

    @Transient
    private List<Imagenes> Imagenes = new ArrayList<>();
    
    @Transient
    private List<Reserva> reservas = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "lugar", fetch = FetchType.EAGER)
    @OrderBy
    private Set<MedidasLugar> medidasLugar = new HashSet<>();

    @Transient
    private List<MedidasProteccion> medidasProteccionChildren = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public CategoriaServicio getCategoriaServicio() {
        return categoriaServicio;
    }

    public void setCategoriaServicio(CategoriaServicio categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    public List<Imagenes> getImagenes() {
        return Imagenes;
    }

    public void setImagenes(List<Imagenes> Imagenes) {
        this.Imagenes = Imagenes;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    } 

    public Set<MedidasLugar> getMedidasLugar() {
        return medidasLugar;
    }

    public void setMedidasLugar(Set<MedidasLugar> medidasLugar) {
        this.medidasLugar = medidasLugar;
    }

    public List<MedidasProteccion> getMedidasProteccionChildren() {
        return medidasProteccionChildren;
    }

    public void setMedidasProteccionChildren(List<MedidasProteccion> medidasProteccionChildren) {
        this.medidasProteccionChildren = medidasProteccionChildren;
    }    
    
}
