/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbCtasXBeneficiariosPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author ledwin.belen
 */
public interface IbCtasXBeneficiariosPjDAO {

    /**
     *
     * @param idEmpresa
     * @param idBeneficiario
     * @param codigoCanal
     * @return
     */
    public IbCtasXBeneficiariosPjDTO listarCtasBeneficiarioXEmpresa(BigDecimal idEmpresa, Long idBeneficiario, String codigoCanal);

    /**
     *
     * @param idEmpresa
     * @param idBeneficiario
     * @param codigoCanal
     * @return
     */
    public UtilDTO eliminarCtasBeneficiarioXEmpresa(BigDecimal idEmpresa, Long idBeneficiario, String codigoCanal);

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
    public IbCtasXBeneficiariosPjDTO listarCtasBeneficiarioXEmpresaAutoriza(BigDecimal idEmpresa, Long idUsuarioAurorizado, Long idServicio, Long idBeneficiario, String idCanal, String codigoCanal);

}
