/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.controlador.negocio;

import com.uts.proyecto.modelos.negocio.MedidasProteccion;
import com.uts.proyecto.servicios.negocio.MedidasProteccionService;
import com.uts.proyecto.utils.Util;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author otalo
 */
@RestController
@RequestMapping(Util.WEB_SERVER_BASE_API_URL + "/medidasproteccion")
public class MedidasProteccionController {

    @Autowired
    private MedidasProteccionService medidasProteccionService;

    @GetMapping()
    public List<MedidasProteccion> findByAll(HttpServletResponse response) {

        List<MedidasProteccion> medidasProteccion = null;
        try {
            medidasProteccion = medidasProteccionService.findAll();
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        return medidasProteccion;
    }

    @GetMapping("/{idMedidasProteccion}")
    public MedidasProteccion findById(@PathVariable Long idMedidasProteccion, HttpServletResponse response) {

        MedidasProteccion medidasProteccion;
        try {
            medidasProteccion = medidasProteccionService.findById(idMedidasProteccion);
            if (medidasProteccionService == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return medidasProteccion;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @PostMapping
    public MedidasProteccion createMedidasProteccion(@RequestBody MedidasProteccion medidasProteccion, HttpServletResponse response) {
        try {
            MedidasProteccion saveMedidasProteccion = medidasProteccionService.create(medidasProteccion);
            if (saveMedidasProteccion == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }

            response.setStatus(HttpServletResponse.SC_CREATED);
            return saveMedidasProteccion;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @PutMapping("/{idMedidasProteccion}")
    @Transactional
    public MedidasProteccion updateStatus(@PathVariable Long idMedidasProteccion, @RequestBody MedidasProteccion medidasProteccion, HttpServletResponse response) {
        try {
            MedidasProteccion foundMedidasProteccion = medidasProteccionService.update(idMedidasProteccion, medidasProteccion);
            if (foundMedidasProteccion == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return foundMedidasProteccion;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @DeleteMapping("{idMedidasProteccion}")
    @Transactional
    public void deleteMedidasProteccion(@PathVariable Long idMedidasProteccion, HttpServletResponse response) {

        try {
            Long resp = medidasProteccionService.delete(idMedidasProteccion);
            if (resp != 1) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
