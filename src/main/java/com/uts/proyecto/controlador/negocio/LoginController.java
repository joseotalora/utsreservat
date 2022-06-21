/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.controlador.negocio;

import com.uts.proyecto.modelos.negocio.Propietario;
import com.uts.proyecto.modelos.negocio.ResetToken;
import com.uts.proyecto.repositorios.negocio.PropietarioRepository;
import com.uts.proyecto.repositorios.negocio.ResetTokenRepository;
import com.uts.proyecto.servicios.negocio.PropietarioService;
import static com.uts.proyecto.utils.AuthUtil.generateSha512;
import com.uts.proyecto.utils.EmailService;
import com.uts.proyecto.utils.Mail;
import com.uts.proyecto.utils.Util;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author otalo
 */
@RestController
@CrossOrigin
@RequestMapping(Util.WEB_SERVER_BASE_API_URL + "/login")
public class LoginController {

    @Autowired
    private ResetTokenRepository resetTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PropietarioRepository propietarioRepository;

    @Autowired
    private PropietarioService propietarioService;

    @PostMapping(value = "/enviarCorreo", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Integer> enviarCorreo(@RequestBody String correo) {
        int rpta = 0;
        try {
            Propietario pro = propietarioRepository.findOneByEmail(correo);
            if (pro != null && pro.getId() > 0) {

                ResetToken token = new ResetToken();
                token.setToken(UUID.randomUUID().toString());
                token.setPropietario(pro);
                token.setExpiracion(10);
                resetTokenRepository.save(token);

                int min = 1;
                int max = 1000;              
              
                int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
                
                pro.setPassword(Integer.toString(random_int));
                Propietario updatePropietario = propietarioService.update(pro.getId(), pro);

                Mail mail = new Mail();
                mail.setFrom("otakora9110@gmail.com");
                mail.setTo(pro.getEmail());
                mail.setSubject("RESTABLECER CONTRASEÑA - APP UTS RESERVAT");

                Map<String, Object> model = new HashMap<>();               
                model.put("user", token.getPropietario().getNombres());
                model.put("pass", Integer.toString(random_int));
                mail.setModel(model);
                emailService.sendEmail(mail);
                rpta = 1;
            }
        } catch (Exception e) {
            return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
    }

    @GetMapping(value = "/restablecer/verificar/{token}")
    public ResponseEntity<Integer> restablecerClave(@PathVariable("token") String token) {
        int rpta = 0;
        try {
            if (token != null && !token.isEmpty()) {
                ResetToken rt = resetTokenRepository.findByToken(token);
                if (rt != null && rt.getId() > 0) {
                    if (!rt.isExpirado()) {
                        rpta = 1;
                    }
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
    }

    @PostMapping(value = "/restablecer/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> restablecerClave(@PathVariable("token") String token, @RequestBody String clave) {
        int rpta = 0;
        try {
            ResetToken rt = resetTokenRepository.findByToken(token);

            Optional<Propietario> foundPropietario = propietarioRepository.findById(rt.getPropietario().getId());
            foundPropietario.get().setPassword(generateSha512(clave.toUpperCase()));
            Propietario updateUser = propietarioRepository.saveAndFlush(foundPropietario.get());
            resetTokenRepository.eliminarToken(rt.getPropietario().getId());
            if (updateUser != null) {
                rpta = 1;
                //resetTokenRepository.eliminarToken(foundUser.get().getId());
            }

        } catch (Exception e) {
            return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
    }
}
