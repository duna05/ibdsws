/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dto.RecaudacionPjDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.bds.ws.dao.RecaudacionPjDAO;
import java.util.List;

/**
 *
 * @author maria.fernandez
 */

@WebService(serviceName = "RecaudacionWs")
public class RecaudacionWs {

    @EJB
    private RecaudacionPjDAO recaudacionDAO;

    /**
     * retorna los datos basicos de la recaudacion mediante el numero de la agencia, la fecha de inicio, fecha fin y el canal
     *
     * @param numeroConvenio String numero de convenio
     * @param fechaDesde String fecha inicio filtro
     * @param fechaHasta String fecha fin filtro
     * @param canal String canal por el cual se realiza la consulta
     * @return UtioDTO si la respuesta es exitosa el nombre de la variable dentro del mapa es listarecaudacion
     */
    @WebMethod(operationName = "detalleRecaudacion")
    public List<RecaudacionPjDTO> detalleRecaudacion(@WebParam(name = "numeroConvenio") String numeroConvenio, @WebParam(name = "fechaDesde") String fechaDesde, @WebParam(name = "fechaHasta") String fechaHasta, @WebParam(name = "canal") String canal) {
       return recaudacionDAO.obtenerDetalleRecaudacion(numeroConvenio, fechaDesde, fechaHasta, canal);
    }

    
     @WebMethod(operationName = "nuevoRecaudacion")
    public RecaudacionPjDTO nuevoRecaudacion(@WebParam(name = "numeroConvenio") String numeroConvenio, @WebParam(name = "fechaDesde") String fechaDesde, @WebParam(name = "fechaHasta") String fechaHasta, @WebParam(name = "canal") String canal) {
       return new RecaudacionPjDTO();
    }
    
   
}
