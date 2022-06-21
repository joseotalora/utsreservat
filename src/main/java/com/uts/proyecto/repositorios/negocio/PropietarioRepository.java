/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.repositorios.negocio;

import com.uts.proyecto.modelos.negocio.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author otalo
 */
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {
    
    @Query("SELECT u FROM Propietario u WHERE u.email = :email")
    Propietario findOneByEmail(@Param("email") String email);
}
