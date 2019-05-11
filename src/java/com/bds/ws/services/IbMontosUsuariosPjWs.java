/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbMontosUsuariosPjDAO;
import com.bds.ws.dto.IbMontosUsuariosPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "IbMontosUsuariosPjWs")
public class IbMontosUsuariosPjWs {
    @EJB
    IbMontosUsuariosPjDAO ibMontosUsuariosPjDAO;
    /**
     * Metodo que inserta las reglas de aprobacion por moto BD
     * Oracle 11++
     *
     *
     * @param idUsuario codigo del usuario en IB
     * @param idEmpresa codigo de la empresa en IB
     * @param montoHasta monto final permitido para la regla
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @WebMethod(operationName = "insertarReglasAprobacionPj")
    public UtilDTO insertarReglasAprobacionPj(@WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "montoHasta") BigDecimal montoHasta,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibMontosUsuariosPjDAO.insertarReglasAprobacionPj(idUsuario, idEmpresa, montoHasta, idCanal, codigoCanal);
    }
    
    /**
     * Metodo que modifica las reglas de aprobacion por moto BD Oracle 11++
     *
     *
     * @param idUsuario codigo del usuario en IB
     * @param idEmpresa codigo de la empresa en IB
     * @param montoHasta monto final permitido para la regla
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @WebMethod(operationName = "modificarReglasAprobacionPj")
    public IbMontosUsuariosPjDTO modificarReglasAprobacionPj(@WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "montoHasta") BigDecimal montoHasta,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibMontosUsuariosPjDAO.modificarReglasAprobacionPj(idUsuario, idEmpresa, montoHasta, idCanal, codigoCanal);
    }
}
