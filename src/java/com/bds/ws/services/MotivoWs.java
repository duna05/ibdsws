/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.MotivoDAO;
import javax.jws.WebService;
import com.bds.ws.dto.MotivoDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


/**
 *
 * @author humberto.rojas
 */
@WebService(serviceName = "MotivoWs")
public class MotivoWs {
    @EJB
    private MotivoDAO motivoDAO;
     
     
    @WebMethod(operationName = "consultarMotivo")
    public MotivoDTO consultarMotivo() {
        return motivoDAO.consultarMotivo();
    }
    
}
