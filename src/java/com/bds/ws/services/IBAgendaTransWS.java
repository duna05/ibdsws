/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IBAgendaTransaccionesDAO;
import com.bds.ws.dto.IBAgendaPNDTO;
import com.bds.ws.dto.IBAgendaTransaccionDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author cesar.mujica
 */
@WebService(serviceName = "IBAgendaTransWS")
public class IBAgendaTransWS {
    @EJB
    private IBAgendaTransaccionesDAO ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

     /**
     * Metodo se encarga de almacenar la cabecera de una transaccion agendada
     * @param idUsuario identificador del usuario
     * @param ctaDestino cuenta destino 
     * @param ctaOrigen cuenta origen de los fondos
     * @param diaFrecuencia dia de la frecuencia (depende del tipo de frecuencia)
     * @param monto monto de la transaccion
     * @param fechaLimite fecha tope de la agenda
     * @param frecuencia frecuencia con la que se realiza la transaccion (1 semanal, 2 quincenal o 3 mensual)
     * @param tipo tipo de la agenda
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "agregarCabeceraAgenda")
    public RespuestaDTO agregarCabeceraAgenda(@WebParam(name = "idUsuario") BigDecimal idUsuario,
        @WebParam(name = "ctaDestino") String ctaDestino,
        @WebParam(name = "ctaOrigen") String ctaOrigen,
        @WebParam(name = "diaFrecuencia") int diaFrecuencia,
        @WebParam(name = "monto") BigDecimal monto,
        @WebParam(name = "fechaLimite") String fechaLimite,
        @WebParam(name = "frecuencia") char frecuencia,
        @WebParam(name = "tipo") char tipo, @WebParam(name = "nombreCanal") String nombreCanal) {
        
        IBAgendaTransaccionDTO agenda = new IBAgendaTransaccionDTO();
        
        agenda.setIdUsuario(idUsuario);
        agenda.setCuentaDestino(ctaDestino);
        agenda.setCuentaOrigen(ctaOrigen);
        agenda.setDiaFrecuencia(diaFrecuencia);
        agenda.setMonto(monto);
        agenda.setFechalimite(fechaLimite);
        agenda.setFrecuencia(frecuencia);
        agenda.setTipo(tipo);
        
        return ejbRef.agregarCabeceraAgenda(agenda, nombreCanal);
    }

     /**
     * Metodo se encarga de almacenar el detalle de una transaccion agendada
     * @param idUsuario identificador del usuario
     * @param ctaDestino cuenta destino 
     * @param ctaOrigen cuenta origen de los fondos
     * @param diaFrecuencia dia de la frecuencia (depende del tipo de frecuencia)
     * @param monto monto de la transaccion
     * @param fechaLimite fecha tope de la agenda
     * @param frecuencia frecuencia con la que se realiza la transaccion (1 semanal, 2 quincenal o 3 mensual)
     * @param descripcion descripcion de la operacion
     * @param tipoDoc tipo de doc del beneficiario
     * @param documento numero de documento del beneficiario
     * @param email correo electronico del beneficiario
     * @param idAgenda identificador de la agenda padre
     * @param idTransaccion identificador del tipo de transaccion segun el core bancario Oracle9
     * @param nombre nombre del beneficiario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "agregarDetalleAgenda")
    public RespuestaDTO agregarDetalleAgenda(@WebParam(name = "idUsuario") BigDecimal idUsuario,
        @WebParam(name = "ctaDestino") String ctaDestino,
        @WebParam(name = "ctaOrigen") String ctaOrigen,
        @WebParam(name = "diaFrecuencia") int diaFrecuencia,
        @WebParam(name = "monto") BigDecimal monto,
        @WebParam(name = "fechaLimite") String fechaLimite,
        @WebParam(name = "frecuencia") char frecuencia, 
        @WebParam(name = "descripcion") String descripcion,
        @WebParam(name = "tipoDoc") char tipoDoc, 
        @WebParam(name = "documento") String documento,
        @WebParam(name = "email") String email,
        @WebParam(name = "idAgenda") BigDecimal idAgenda,
        @WebParam(name = "idTransaccion") int idTransaccion,
        @WebParam(name = "nombre") String nombre,         
        @WebParam(name = "nombreCanal") String nombreCanal) {
        
        IBAgendaTransaccionDTO agenda = new IBAgendaTransaccionDTO();
        
        agenda.setIdUsuario(idUsuario);
        agenda.setCuentaDestino(ctaDestino);
        agenda.setCuentaOrigen(ctaOrigen);
        agenda.setDiaFrecuencia(diaFrecuencia);
        agenda.setMonto(monto);
        agenda.setFechalimite(fechaLimite);
        agenda.setFrecuencia(frecuencia);
        agenda.setDescripcion(descripcion);
        agenda.setDocumento(documento);
        agenda.setTipoDoc(tipoDoc);
        agenda.setEmail(email);
        agenda.setIdAgenda(idAgenda);
        agenda.setIdTransaccion(idTransaccion);
        agenda.setNombre(nombre);        
        
        return ejbRef.agregarDetalleAgenda(agenda, nombreCanal);
    }
    
    /**
     * Metodo se encarga de obtener el id de la cabecera de una transaccion agendada
     *
     * @param idUsuario identificador del usuario
     * @param ctaDestino cuenta destino 
     * @param ctaOrigen cuenta origen de los fondos
     * @param diaFrecuencia dia de la frecuencia (depende del tipo de frecuencia)
     * @param monto monto de la transaccion
     * @param fechaLimite fecha tope de la agenda
     * @param frecuencia frecuencia con la que se realiza la transaccion (1 semanal, 2 quincenal o 3 mensual)
     * @param tipo tipo de la agenda
     * @param nombreCanal nombre del canal
     * @return UtilDTO
     */
    @WebMethod(operationName = "consultarIdCabeceraAgenda")
    public UtilDTO consultarIdCabeceraAgenda(@WebParam(name = "idUsuario") BigDecimal idUsuario,
        @WebParam(name = "ctaDestino") String ctaDestino,
        @WebParam(name = "ctaOrigen") String ctaOrigen,
        @WebParam(name = "diaFrecuencia") int diaFrecuencia,
        @WebParam(name = "monto") BigDecimal monto,
        @WebParam(name = "fechaLimite") String fechaLimite,
        @WebParam(name = "frecuencia") char frecuencia,
        @WebParam(name = "tipo") char tipo, @WebParam(name = "nombreCanal") String nombreCanal) { 
        
        IBAgendaTransaccionDTO agenda = new IBAgendaTransaccionDTO();
        
        agenda.setIdUsuario(idUsuario);
        agenda.setCuentaDestino(ctaDestino);
        agenda.setCuentaOrigen(ctaOrigen);
        agenda.setDiaFrecuencia(diaFrecuencia);
        agenda.setMonto(monto);
        agenda.setFechalimite(fechaLimite);
        agenda.setFrecuencia(frecuencia);
        agenda.setTipo(tipo);
        
        return ejbRef.consultarIdCabeceraAgenda(agenda, nombreCanal);
    }
    
    /**
     * Metodo se encarga de obtener el id de la cabecera de una transaccion agendada por usuario, id de transacción
     *
     * @param idUsuario identificador del usuario
     * @param tipo tipo de la agenda
     * @param nombreCanal nombre del canal
     * @return UtilDTO
     */
    @WebMethod(operationName = "consultarIdCabeceraAgendaPP")
    public IBAgendaPNDTO consultarIdCabeceraAgendaPP(@WebParam(name = "idUsuario") BigDecimal idUsuario,@WebParam(name = "tipo") char tipo,@WebParam(name = "idTransaccion") int idTransaccion, @WebParam(name = "nombreCanal") String nombreCanal, @WebParam(name = "idCanal") String idCanal) {  
        return ejbRef.consultarIdCabeceraAgendaPP(idUsuario,tipo,idTransaccion, nombreCanal, idCanal);
    }
    
    
     /**
     * Metodo para eliminar Pago Programado, Agenda
     *
     * @param idAgenda
     * @param idUsuario
     * @param nombreCanal String ID del canal
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "eliminarAgendaProgramada")
    public RespuestaDTO eliminarAgendaProgramada(@WebParam(name = "idAgenda")BigDecimal idAgenda, @WebParam(name = "idUsuario")BigDecimal idUsuario, @WebParam(name = "nombreCanal")String nombreCanal, @WebParam(name = "idCanal") String idCanal){
        return ejbRef.eliminarAgendaProgramada(idAgenda, idUsuario, nombreCanal, idCanal);
    } 
    
}
