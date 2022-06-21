/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.repositorios.negocio;

import com.uts.proyecto.modelos.negocio.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author otalo
 */
public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {
    ResetToken findByToken(String token);
    
    /*@Modifying
    @Query("delete from ResetToken ur where ur.user.id = :userId")
    public void eliminarToken(@Param("userId") Long userId);*/
    
    @Transactional
    @Modifying
    @Query(value="DELETE FROM SINRESETTOKEN WHERE IDPROPIETARIO = :id", nativeQuery=true)
    public void eliminarToken(@Param("id") Long id);
}
