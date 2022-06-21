/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.controlador.negocio;

import com.uts.proyecto.modelos.negocio.Reserva;
import com.uts.proyecto.servicios.negocio.ReservaService;
import com.uts.proyecto.utils.Util;
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
@RequestMapping(Util.WEB_SERVER_BASE_API_URL + "/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/{idReserva}")
    public Reserva findById(@PathVariable Long idReserva, HttpServletResponse response) {

        Reserva reserva;
        try {
            reserva = reservaService.findById(idReserva);
            if (reservaService == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return reserva;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @PostMapping
    public Reserva createReserva(@RequestBody Reserva lugar, HttpServletResponse response) {
        try {
            Reserva saveReserva = reservaService.create(lugar);
            if (saveReserva == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }

            response.setStatus(HttpServletResponse.SC_CREATED);
            return saveReserva;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @PutMapping("/{idReserva}")
    @Transactional
    public Reserva updateStatus(@PathVariable Long idReserva, @RequestBody Reserva reserva, HttpServletResponse response) {
        try {
            Reserva foundlugar = reservaService.update(idReserva, reserva);
            if (foundlugar == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return foundlugar;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @DeleteMapping("{idReserva}")
    @Transactional
    public void deleteLugar(@PathVariable Long idReserva, HttpServletResponse response) {

        try {
            Long resp = reservaService.delete(idReserva);
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

    @PostMapping("validarReserva/{idReserva}/{estado}")
    public Reserva validarReserva(@PathVariable Long idReserva, @PathVariable Character estado, HttpServletResponse response) {
        try {
            Reserva saveReserva = reservaService.validarReserva(idReserva, estado);
            if (saveReserva == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }

            response.setStatus(HttpServletResponse.SC_CREATED);
            return saveReserva;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
