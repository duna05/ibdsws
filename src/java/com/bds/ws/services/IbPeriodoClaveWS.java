/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbPeriodoClaveDAO;
import com.bds.ws.dto.IbPeriodoClaveDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase de servicios web para periodos de clave
 * @author juan.faneite
 */
@WebService(serviceName = "IbPeriodoClaveWS")
public class IbPeriodoClaveWS {
    @EJB
    private IbPeriodoClaveDAO ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    /**
     * Metodo que obtiene el listado de periodos de clave
     * @param nombreCanal nombre del canal
     * @return IbPeriodoClaveDTO listado de periodos de clave
     */
    @WebMethod(operationName = "listaPeriodoClave")
    public IbPeriodoClaveDTO listaPeriodoClave(@WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.listaPeriodoClave(nombreCanal);
    }
    
}
