/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.repositorios.negocio;

import com.uts.proyecto.modelos.negocio.Imagenes;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author otalo
 */
public interface ImagenesRepository extends JpaRepository<Imagenes, Long> {
    
    @Query("FROM Imagenes im where im.lugar.id = :id")
    List<Imagenes> findByIdLugar(@Param("id") Long id) throws Exception;
    
    @Transactional
    @Modifying
    @Query(value="DELETE FROM Imagenes WHERE id = :id", nativeQuery=true)
    public void eliminarImagenes(@Param("id") Long id);
}
