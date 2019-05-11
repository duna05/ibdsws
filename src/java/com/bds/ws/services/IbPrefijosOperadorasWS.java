/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbPrefijosOperadorasDAO;
import com.bds.ws.dto.IbPrefijosOperadorasDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase de servicios web para prefijos de operadoras
 * @author juan.faneite
 */
@WebService(serviceName = "IbPrefijosOperadorasWS")
public class IbPrefijosOperadorasWS {
    @EJB
    private IbPrefijosOperadorasDAO ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    /**
     * Metodo para obtener el listado de prefijos de operadoras
     * @param nombreCanal nombre del canal
     * @return IbPrefijosOperadorasDTO listado de prefijos de operadoras
     */
    @WebMethod(operationName = "listaPrefijosOperadoras")
    public IbPrefijosOperadorasDTO listaPrefijosOperadoras(@WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.listaPrefijosOperadoras(nombreCanal);
    }
    
}
