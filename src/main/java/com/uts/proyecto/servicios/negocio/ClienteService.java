/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.servicios.negocio;

import com.uts.proyecto.modelos.negocio.Cliente;
import com.uts.proyecto.repositorios.negocio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author otalo
 */
@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente create(Cliente cliente) throws Exception {
        cliente.setName(cliente.getName().toUpperCase());
        cliente.setEmail(cliente.getEmail().toLowerCase());
        Cliente saveCliente = clienteRepository.saveAndFlush(cliente);
        return saveCliente;
    }
}
