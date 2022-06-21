/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.repositorios.negocio;

import com.uts.proyecto.modelos.negocio.MedidasLugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author otalo
 */
public interface MedidasLugarRepository extends JpaRepository<MedidasLugar, Long> {
   
    @Modifying
    @Query("delete from  MedidasLugar ur where ur.lugar.id = :Id")
    public void deleteLugarMedida(@Param("Id") Long Id);
}
