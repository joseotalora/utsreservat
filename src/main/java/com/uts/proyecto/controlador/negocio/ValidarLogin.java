/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.controlador.negocio;

import com.uts.proyecto.JwtTokenUtil;
import com.uts.proyecto.modelos.negocio.Propietario;
import com.uts.proyecto.repositorios.negocio.PropietarioRepository;
import com.uts.proyecto.servicios.negocio.PropietarioService;
import com.uts.proyecto.utils.Util;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author otalo
 */
@RestController
@RequestMapping(Util.WEB_SERVER_BASE_API_URL + "/validarToken")
public class ValidarLogin {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PropietarioRepository propietarioRepository;

    @GetMapping("infoPropietario")
    public Propietario findByToken(HttpServletResponse response, @RequestHeader("authorization") String authHeader) {

        Propietario propietario;
        String email = null;
        if (authHeader != null) {
            int can = authHeader.length();
            String toke = authHeader.substring(7, can);
            email = jwtTokenUtil.getUsernameFromToken(toke);
        }
        try {
            propietario = propietarioRepository.findOneByEmail(email);
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
}
