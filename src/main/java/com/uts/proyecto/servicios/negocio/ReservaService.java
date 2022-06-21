/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.servicios.negocio;

import com.uts.proyecto.modelos.negocio.Cliente;
import com.uts.proyecto.modelos.negocio.Reserva;
import com.uts.proyecto.repositorios.negocio.ClienteRepository;
import com.uts.proyecto.repositorios.negocio.LugarRepository;
import com.uts.proyecto.repositorios.negocio.ReservaRepository;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author otalo
 */
@Service
@Transactional
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LugarRepository lugarRepository;

    public Reserva create(Reserva reserva) throws Exception {
        Cliente cliente = new Cliente();
        cliente.setName(reserva.getCliente().getName().toUpperCase());
        cliente.setEmail(reserva.getCliente().getEmail().toLowerCase());
        cliente.setPhone(reserva.getCliente().getPhone());
        Cliente savecliente = clienteRepository.saveAndFlush(cliente);
        reserva.setStatus('P');
        reserva.setCliente(savecliente);
        reserva.setCapacidad(reserva.getCapacidad());
        Reserva saveReserva = reservaRepository.saveAndFlush(reserva);
        return saveReserva;
    }

    public Reserva update(Long idReserva, Reserva reserva) throws Exception {

        Optional<Reserva> foundReserva = reservaRepository.findById(idReserva);
        if (foundReserva == null) {
            return null;
        }

        Reserva updatedReserva = foundReserva.get();

        Cliente cliente = new Cliente();
        cliente.setName(reserva.getCliente().getName().toUpperCase());
        cliente.setEmail(reserva.getCliente().getEmail().toLowerCase());
        cliente.setPhone(reserva.getCliente().getPhone());
        Cliente savecliente = clienteRepository.saveAndFlush(cliente);
        updatedReserva.setCliente(savecliente);
        updatedReserva.setStatus('P');
        updatedReserva.setCapacidad(reserva.getCapacidad());
        Reserva saveReserva = reservaRepository.saveAndFlush(updatedReserva);
        return saveReserva;
    }

    public Reserva findById(Long idReserva) throws Exception {

        Optional<Reserva> reserva = reservaRepository.findById(idReserva);
        if (reserva == null) {
            return null;
        }
        return reserva.get();
    }

    public Long delete(Long idReserva) throws Exception {
        reservaRepository.deleteById(idReserva);
        return 0L;
    }

    public Reserva validarReserva(Long idReserva, Character estado) throws Exception {
        Optional<Reserva> reserva = reservaRepository.findById(idReserva);

        if (reserva.get().getStatus() =='R') {
            reserva.get().setStatus('R');
            Reserva saveReserva = reservaRepository.saveAndFlush(reserva.get());
            return saveReserva;
        } else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaIni = dateFormat.format(reserva.get().getStartDate());
            String fechaFin = dateFormat.format(reserva.get().getEndDate());
            Calendar c = Calendar.getInstance();
            c.setTime(reserva.get().getEndDate());
            c.add(Calendar.DATE, 1);
            Date currentDatePlusOne = c.getTime();
            String fechaFinF = dateFormat.format(currentDatePlusOne);
            List<Reserva> reservas = reservaRepository.validarResrva(reserva.get().getLugar().getId(), reserva.get().getStartDate(), reserva.get().getEndDate(), currentDatePlusOne);

            if (reservas.size() == 0) {
                reserva.get().setStatus('A');
                Reserva saveReserva = reservaRepository.saveAndFlush(reserva.get());
                return saveReserva;
            } else {
                reserva.get().setStatus('R');
                Reserva saveReserva = reservaRepository.saveAndFlush(reserva.get());
                return saveReserva;
            }
        }

    }
}
