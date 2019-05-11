/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.ConvenioPjDAO;
import com.bds.ws.dto.ConvenioPjDTO;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author maria.fernandez
 */
@WebService(serviceName = "ConvenioWs")
public class ConvenioWs {

    @EJB
    private ConvenioPjDAO convenioDAO;

    /**
     * retorna el numero de convenio del cliente mediante el codigo del cliente y el canal
     *
     * @param ivCliente String codigo del cliente
     * @param canal String canal por el cual se realiza la consulta
     * @return UtilDTO si la respuesta es exitosa el nombre de la variable dentro del mapa es listaconvenio
     */
    @WebMethod(operationName = "detalleConvenio")
    public List<ConvenioPjDTO> detalleConvenio(@WebParam(name = "ivCliente") String ivCliente, @WebParam(name = "canal") String canal) {
       return convenioDAO.obtenerDetalleConvenio(ivCliente, canal);
    }

    
    @WebMethod(operationName = "nuevoConvenio")
    public ConvenioPjDTO nuevoConvenio(@WebParam(name = "ivCliente") String ivCliente, @WebParam(name = "canal") String canal) {
       return new ConvenioPjDTO();
    }
    
   
}
