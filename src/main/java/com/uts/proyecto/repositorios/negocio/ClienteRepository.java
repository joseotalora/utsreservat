/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.repositorios.negocio;

import com.uts.proyecto.modelos.negocio.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author otalo
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
