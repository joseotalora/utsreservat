/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.servicios.negocio;

import com.uts.proyecto.modelos.negocio.Imagenes;
import com.uts.proyecto.modelos.negocio.Lugar;
import com.uts.proyecto.modelos.negocio.MedidasLugar;
import com.uts.proyecto.modelos.negocio.MedidasProteccion;
import com.uts.proyecto.modelos.negocio.Reserva;
import com.uts.proyecto.repositorios.negocio.ImagenesRepository;
import com.uts.proyecto.repositorios.negocio.LugarRepository;
import com.uts.proyecto.repositorios.negocio.MedidasLugarRepository;
import com.uts.proyecto.repositorios.negocio.ReservaRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author otalo
 */
@Service
@Transactional
public class LugarService {

    @Autowired
    private LugarRepository lugarRepository;

    @Autowired
    private ImagenesRepository imagenesRepository;

    @Autowired
    private MedidasLugarRepository medidasLugarRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Lugar> findAll() throws Exception {

        List<Lugar> categoriaServicio = lugarRepository.findAll();
        if (categoriaServicio == null) {
            return null;
        }
        categoriaServicio.sort(Comparator.comparing(m -> m.getName()));
        return categoriaServicio;

    }

    public List<Lugar> findByPropietario(Long idPersona) throws Exception {

        List<Lugar> lugar = lugarRepository.findByIdPropietario(idPersona);

        lugar.forEach(cnsmr -> {
            Lugar lg = new Lugar();
            lg.setName(cnsmr.getName());
            lg.setDescription(cnsmr.getDescription().toUpperCase());
            lg.setLatitude(cnsmr.getLatitude());
            lg.setLongitude(cnsmr.getLongitude());
            lg.setPrice(cnsmr.getPrice());
            lg.setCapacidad(cnsmr.getCapacidad());
            try {
                List<Imagenes> img = imagenesRepository.findByIdLugar(cnsmr.getId());
                img.forEach(cnsmrs -> {
                    Imagenes imgs = new Imagenes();
                    imgs.setId(cnsmrs.getId());
                    imgs.setName(cnsmrs.getName());
                    lg.getImagenes().add(imgs);
                });
            } catch (Exception ex) {
                Logger.getLogger(LugarService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        if (lugar == null) {
            return null;
        }

        lugar.sort(Comparator.comparing(m -> m.getName()));
        return lugar;

    }

    public List<Lugar> findByCategoria(Long idCategoria) throws Exception {

        List<Lugar> lugar = lugarRepository.findByIdCategoria(idCategoria);

        lugar.forEach(cnsmr -> {
            Lugar lg = new Lugar();
            lg.setName(cnsmr.getName());
            lg.setDescription(cnsmr.getDescription().toUpperCase());
            lg.setLatitude(cnsmr.getLatitude());
            lg.setLongitude(cnsmr.getLongitude());
            lg.setPrice(cnsmr.getPrice());
            lg.setCapacidad(cnsmr.getCapacidad());
            try {
                List<Imagenes> img = imagenesRepository.findByIdLugar(cnsmr.getId());
                img.forEach(cnsmrs -> {
                    Imagenes imgs = new Imagenes();
                    imgs.setId(cnsmrs.getId());
                    imgs.setName(cnsmrs.getName());
                    lg.getImagenes().add(imgs);
                });
            } catch (Exception ex) {
                Logger.getLogger(LugarService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        if (lugar == null) {
            return null;
        }

        lugar.sort(Comparator.comparing(m -> m.getName()));
        return lugar;

    }

    public Lugar findById(Long idLugar) throws Exception {

        Optional<Lugar> lugar = lugarRepository.findById(idLugar);
        List<Imagenes> imagenes = imagenesRepository.findByIdLugar(idLugar);
        if (imagenes != null) {
            imagenes.forEach(cnsmr -> {
                Imagenes img = new Imagenes();
                img.setId(cnsmr.getId());
                img.setName(cnsmr.getName());
                lugar.get().getImagenes().add(img);
            });
        }
        
        lugar.get().getMedidasLugar().forEach(cnsmr ->{
            MedidasProteccion medidasProteccion = new MedidasProteccion();
            medidasProteccion.setId(cnsmr.getMedidasProteccion().getId());
            medidasProteccion.setMedida(cnsmr.getMedidasProteccion().getMedida());
            lugar.get().getMedidasProteccionChildren().add(medidasProteccion);
        });
        if (lugar == null) {
            return null;
        }
        return lugar.get();
    }

    public Lugar findByReservas(Long idLugar) throws Exception {

        Optional<Lugar> lugar = lugarRepository.findById(idLugar);
        List<Reserva> reserva = reservaRepository.findByReservasLugar(idLugar);
        if (reserva != null) {
            reserva.forEach(cnsmr -> {
                Reserva re = new Reserva();
                re.setId(cnsmr.getId());
                re.setStartDate(cnsmr.getStartDate());
                re.setEndDate(cnsmr.getEndDate());
                re.setTimeReservedStart(cnsmr.getTimeReservedStart());
                re.setTimeReservedEnd(cnsmr.getTimeReservedEnd());
                re.setCliente(cnsmr.getCliente());
                re.setStatus(cnsmr.getStatus());
                re.setCapacidad(cnsmr.getCapacidad());
                lugar.get().getReservas().add(re);
            });
        }
        if (lugar == null) {
            return null;
        }
        return lugar.get();
    }

    public Lugar create(Lugar lugar) throws Exception {
        lugar.setName(lugar.getName().toUpperCase());
        lugar.setDescription(lugar.getDescription().toUpperCase());
        lugar.setLatitude(lugar.getLatitude());
        lugar.setLongitude(lugar.getLongitude());
        lugar.setPrice(lugar.getPrice());
        lugar.setCapacidad(lugar.getCapacidad());        
        Lugar saveLugar = lugarRepository.saveAndFlush(lugar);
        
        lugar.getMedidasProteccionChildren().forEach(item -> {
            MedidasLugar medidasLugar = new MedidasLugar();
            medidasLugar.setLugar(saveLugar);
            medidasLugar.setMedidasProteccion(item);
            medidasLugarRepository.saveAndFlush(medidasLugar);
        });
        return saveLugar;
    }

    public Lugar update(Long idLugar, Lugar lugar) throws Exception {

        Optional<Lugar> foundLugar = lugarRepository.findById(idLugar);
        if (foundLugar == null) {
            return null;
        }

        Lugar updatedcaLugar = foundLugar.get();
        updatedcaLugar.setName(lugar.getName().toUpperCase());
        updatedcaLugar.setDescription(lugar.getDescription().toUpperCase());
        updatedcaLugar.setLatitude(lugar.getLatitude());
        updatedcaLugar.setLongitude(lugar.getLongitude());
        updatedcaLugar.setPrice(lugar.getPrice());
        updatedcaLugar.setCategoriaServicio(lugar.getCategoriaServicio());
        updatedcaLugar.setPropietario(lugar.getPropietario());
        updatedcaLugar.setCapacidad(lugar.getCapacidad());
        Lugar saveLugar = lugarRepository.saveAndFlush(updatedcaLugar);

        medidasLugarRepository.deleteLugarMedida(saveLugar.getId());
        lugar.getMedidasProteccionChildren().forEach(item -> {
            MedidasLugar medidasLugar = new MedidasLugar();
            medidasLugar.setLugar(saveLugar);
            medidasLugar.setMedidasProteccion(item);
            medidasLugarRepository.saveAndFlush(medidasLugar);
        });
        
        return saveLugar;
    }

    public Long delete(Long idLugar) throws Exception {
        List<Imagenes> img = imagenesRepository.findByIdLugar(idLugar);
        List<Reserva> re = reservaRepository.findByReservasLugar(idLugar);
        img.forEach(cnsmrs -> {
            imagenesRepository.deleteById(cnsmrs.getId());
        });

        re.forEach(cnsmr -> {
            reservaRepository.deleteById(cnsmr.getId());
        });
        medidasLugarRepository.deleteLugarMedida(idLugar);
        imagenesRepository.eliminarImagenes(idLugar);
        lugarRepository.deleteById(idLugar);
        return 0L;
    }

    public Long deleteImg(Long idImg) throws Exception {
        Optional<Imagenes> img = imagenesRepository.findById(idImg);
        //firebaseFileService.delete(img.get().getName());          
        imagenesRepository.deleteById(img.get().getId());
        return 0L;
    }
}
