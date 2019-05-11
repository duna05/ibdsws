/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;
import com.bds.ws.dao.ClasificacionDAO;
import com.bds.ws.dao.SubClasificacionDAO;
import com.bds.ws.dao.SubSubClasificacionDAO;
import com.bds.ws.dao.SubtipoClienteDAO;
import com.bds.ws.dto.ClasificacionDTO;
import com.bds.ws.dto.SubClasificacionDTO;
import com.bds.ws.dto.SubSubClasificacionDTO;
import com.bds.ws.dto.SubtipoClienteDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author humberto.rojas
 */
@WebService(serviceName = "SubSubClasificacionWs")
public class SubSubClasificacionWs {
     @EJB
    private SubSubClasificacionDAO subsubclasificacionDAO;
     
     
    @WebMethod(operationName = "consultarSubSubClasificacion")
    public SubSubClasificacionDTO consultarSubSubClasificacion() {
        return subsubclasificacionDAO.consultarSubSubClasificacion();
    }
}
