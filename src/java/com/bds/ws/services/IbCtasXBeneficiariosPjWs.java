/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbCtasXBeneficiariosPjDAO;
import com.bds.ws.dto.IbCtasXBeneficiariosPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author ledwin.belen
 */
@WebService(serviceName = "IbCtasXBeneficiariosPjWs")
public class IbCtasXBeneficiariosPjWs {

    @EJB
    private IbCtasXBeneficiariosPjDAO ibCtasXBeneficiariosPjDAO;

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "listarCtasBeneficiarioXEmpresa")
    public IbCtasXBeneficiariosPjDTO listarCtasBeneficiarioXEmpresa(
            @WebParam(name = "idBeneficiario") Long idBeneficiario,
            @WebParam(name = "idEmpresa") BigDecimal idEmpresa,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibCtasXBeneficiariosPjDAO.listarCtasBeneficiarioXEmpresa(idEmpresa, idBeneficiario, codigoCanal);
    }

    @WebMethod(operationName = "eliminarCtasBeneficiarioXEmpresa")
    public UtilDTO eliminarCtasBeneficiarioXEmpresa(
            @WebParam(name = "idBeneficiario") Long idBeneficiario,
            @WebParam(name = "idEmpresa") BigDecimal idEmpresa,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibCtasXBeneficiariosPjDAO.eliminarCtasBeneficiarioXEmpresa(idEmpresa, idBeneficiario, codigoCanal);
    }

    /**
     * Lista de cuenta beneficiarios por estatus de autorizacion
     *
     * @param idEmpresa
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param idBeneficiario
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @WebMethod(operationName = "listarCtasBeneficiarioXEmpresaAutoriza")
    public IbCtasXBeneficiariosPjDTO listarCtasBeneficiarioXEmpresaAutoriza(
            @WebParam(name = "idEmpresa") BigDecimal idEmpresa,
            @WebParam(name = "idUsuarioAurorizado") Long idUsuarioAurorizado,
            @WebParam(name = "idServicio") Long idServicio,
            @WebParam(name = "idBeneficiario") Long idBeneficiario,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibCtasXBeneficiariosPjDAO.listarCtasBeneficiarioXEmpresaAutoriza(idEmpresa, idUsuarioAurorizado, idServicio, idBeneficiario, idCanal, codigoCanal);
    }
}
