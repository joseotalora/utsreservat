/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.repositorios.negocio;

import com.uts.proyecto.modelos.negocio.Reserva;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author otalo
 */
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("FROM Reserva re where re.lugar.id = :id")
    List<Reserva> findByReservasLugar(@Param("id") Long id) throws Exception;
    
    @Query(value="SELECT * from reserva where estado = 'A' AND id_lugar = :id AND FECHA_INICIAL BETWEEN :Fechaini AND :Fechafin AND FECHA_FINAL BETWEEN :Fechafin AND :FechafinF", nativeQuery=true)
    List<Reserva> validarResrva(@Param("id") Long id, @Param("Fechaini") Date Fechaini, @Param("Fechafin") Date Fechafin, @Param("FechafinF") Date FechafinF );
    
    
}
