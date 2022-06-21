/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.controlador.negocio;

import com.uts.proyecto.JwtTokenUtil;
import com.uts.proyecto.modelos.negocio.Propietario;
import com.uts.proyecto.servicios.negocio.PropietarioService;
import com.uts.proyecto.utils.Util;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author otalo
 */
@RestController
@RequestMapping(Util.WEB_SERVER_BASE_API_URL + "/propietario")
public class PropietarioController {

    @Autowired
    private PropietarioService propietarioService;
    
  

    @GetMapping("/{idPropietario}")
    public Propietario findById(@PathVariable Long idPropietario, HttpServletResponse response) {

        Propietario propietario;
        try {
            propietario = propietarioService.findById(idPropietario);
            if (propietario == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return propietario;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    

    @PostMapping
    public Propietario createCategoriaServicio(@RequestBody Propietario propietario, HttpServletResponse response) {
        try {
            Propietario savePropietario = propietarioService.create(propietario);
            if (savePropietario == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_CREATED);
            return savePropietario;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            return null;
        }
    }

    @PutMapping("/{idPropietario}")
    @Transactional
    public Propietario updateStatus(@PathVariable Long idPropietario, @RequestBody Propietario propietario, HttpServletResponse response) {
        try {
            Propietario foundPropietario = propietarioService.update(idPropietario, propietario);
            if (foundPropietario == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return foundPropietario;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            return null;
        }
    }
}
