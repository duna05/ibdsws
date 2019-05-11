/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.DivisasDAO;
import com.bds.ws.dto.SicadIDTO;
import com.bds.ws.dto.SicadIIDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author juan.faneite
 */
@WebService(serviceName = "DivisasWS")
public class DivisasWS {

    @EJB
    private DivisasDAO divisasDAO;

    @WebMethod(operationName = "consultaAdjSicadI")
    public SicadIDTO consultaAdjSicadI(@WebParam(name = "iIdentificacion") String iIdentificacion, @WebParam(name = "canal") String canal) {
        return divisasDAO.consultaAdjSicadI(iIdentificacion, canal);
    }

    @WebMethod(operationName = "consultaAdjSicadII")
    public SicadIIDTO consultaAdjSicadII(@WebParam(name = "iIdentificacion") String iIdentificacion, @WebParam(name = "canal") String canal) {
        return divisasDAO.consultaAdjSicadII(iIdentificacion, canal);
    }

}
