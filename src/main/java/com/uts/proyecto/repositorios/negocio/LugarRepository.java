/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.repositorios.negocio;

import com.uts.proyecto.modelos.negocio.Lugar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author otalo
 */
public interface LugarRepository extends JpaRepository<Lugar, Long> {

    @Query("FROM Lugar lg where lg.propietario.id = :id")
    List<Lugar> findByIdPropietario(@Param("id") Long id) throws Exception;
    
    @Query("FROM Lugar lg where lg.categoriaServicio.id = :id")
    List<Lugar> findByIdCategoria(@Param("id") Long id) throws Exception;
}
