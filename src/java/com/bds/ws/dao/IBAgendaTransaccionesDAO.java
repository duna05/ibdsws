/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IBAgendaPNDTO;
import com.bds.ws.dto.IBAgendaTransaccionDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author cesar.mujica
 */
public interface IBAgendaTransaccionesDAO {
    
    /**
     * Metodo se encarga de almacenar la cabecera de una transaccion agendada
     * @param agenda Objeto con los datos de la cabecera de transaccion a agendar
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO agregarCabeceraAgenda( IBAgendaTransaccionDTO agenda, String nombreCanal);
    
     /**
     * Metodo se encarga de almacenar el detalle de una transaccion agendada
     * @param agenda Objeto con los datos de la cabecera de transaccion a agendar
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO agregarDetalleAgenda(IBAgendaTransaccionDTO agenda, String nombreCanal);
    
    /**
     * Metodo se encarga de obtener el id de la cabecera de una transaccion agendada
     *
     * @param agenda Objeto con los datos de la cabecera de transaccion a
     * agendar
     * @param nombreCanal nombre del canal
     * @return UtilDTO
     */
    public UtilDTO consultarIdCabeceraAgenda(IBAgendaTransaccionDTO agenda, String nombreCanal);
    
    /**
     * Metodo se encarga de obtener el id de la cabecera de una transaccion agendada por el usuario, id de transacción
     *
     * @param idUsuario
     * @param tipo
     * @param idTransaccion
     * @param nombreCanal nombre del canal
     * @param idCanal
     * @return UtilDTO
     */
    public IBAgendaPNDTO consultarIdCabeceraAgendaPP(BigDecimal idUsuario, char tipo, int idTransaccion, String nombreCanal, String idCanal);
    
    /**
     * Metodo se encarga de obtener el id de la cabecera de una transaccion agendada por el usuario, id de transacción
     *
     * @param idAgenda
     * @param idUsuario
     * @param nombreCanal nombre del canal
     * @param idCanal
     * @return UtilDTO
     */
    public RespuestaDTO eliminarAgendaProgramada(BigDecimal idAgenda, BigDecimal idUsuario, String nombreCanal, String idCanal);
    
}
