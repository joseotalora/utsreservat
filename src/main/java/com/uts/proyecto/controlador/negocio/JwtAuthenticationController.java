/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.controlador.negocio;

import com.uts.proyecto.JwtTokenUtil;
import com.uts.proyecto.modelos.negocio.JwtResponse;
import com.uts.proyecto.modelos.negocio.Propietario;
import com.uts.proyecto.repositorios.negocio.PropietarioRepository;
import com.uts.proyecto.servicios.negocio.JwtRequest;
import com.uts.proyecto.servicios.negocio.JwtUserDetailsService;
import com.uts.proyecto.utils.AuthUtil;
import com.uts.proyecto.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author otalo
 */
@RestController
@CrossOrigin
@RequestMapping(Util.WEB_SERVER_BASE_API_URL)
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PropietarioRepository propietarioRepository;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticationRequest.setEmail(authenticationRequest.getEmail().toLowerCase());
        authenticationRequest.setPassword(authenticationRequest.getPassword());

        Propietario foundPropietario = propietarioRepository.findOneByEmail(authenticationRequest.getEmail());

        if (foundPropietario != null) {
            if (foundPropietario.getPassword().equals(AuthUtil.generateSha512(authenticationRequest.getPassword()))) {
                authenticate("javainuse", "password");
                //authenticationRequest.setUsername("javainuse");
                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(authenticationRequest.getEmail());
                final String token = jwtTokenUtil.generateToken(userDetails);
                return ResponseEntity.ok(new JwtResponse(token));
            } else {
                return (ResponseEntity<?>) ResponseEntity.notFound();
            }
        } else {
            return (ResponseEntity<?>) ResponseEntity.notFound();
        }

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
