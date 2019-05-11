/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.FichaClienteRefPersonalesUpdateDAO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author audra.zapata
 */
@WebService(serviceName = "FichaClienteRefPersonalesUpdateWs")
public class FichaClienteRefPersonalesUpdateWs {
    @EJB
    private FichaClienteRefPersonalesUpdateDAO fichaClienteRefPersonalesUpdateDAO;
    
    @WebMethod(operationName = "actualizarRefPersonales")
    public UtilDTO actualizarRefPersonales(@WebParam(name = "codigoCliente") String codigoCliente,@WebParam(name = "codTipoIdentif") String codTipoIdentif,@WebParam(name = "nroIdentif") String nroIdentif,@WebParam(name = "nombreIn") String nombreIn,@WebParam(name = "primerApellido") String primerApellido,@WebParam(name = "segundoApellido") String segundoApellido,@WebParam(name = "codReferenciaPersonal") String codReferenciaPersonal,@WebParam(name = "telefonoIn") String telefonoIn,@WebParam(name = "telefono2In") String telefono2In) {
        return fichaClienteRefPersonalesUpdateDAO.actualizarRefPersonales(codigoCliente, codTipoIdentif, nroIdentif, nombreIn, primerApellido, segundoApellido, codReferenciaPersonal, telefonoIn, telefono2In);
    }
}
