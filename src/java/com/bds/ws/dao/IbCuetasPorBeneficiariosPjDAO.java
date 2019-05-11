/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbBeneficiariosPjDTO;
import com.bds.ws.model.IbBeneficiariosPj;
import java.math.BigDecimal;

/**
 *
 * @author robinson.rodriguez
 */
public interface IbCuetasPorBeneficiariosPjDAO {
   
   /**
     * Metodo que lista una serie de beneficiario en base a unos parametros indicados en  especiales Oracle 11
     *
    
     * 
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @param estatusCta el estatus de actividad o inactividad de la cuenta de la asociada
     * @param idCtaDelSur indica si la cuenta es del Banco de sur o de otro banco 
     * @param idCtaPro indica si la cuenta es propia o de un tersero 
     * @return IbBeneficiariosPjDTO
     */
    
    public IbBeneficiariosPjDTO listaIbBeneficiariosPorCtaEmpresaPjDTO(BigDecimal idCtaEmpresa,BigDecimal idCtaDelSur,BigDecimal idCtaPro, Short estatusCta, String idCanal,String codigoCanal);
    
    /**
     * Metodo que lista una serie de beneficiario en base a unos parametros indicados en  especiales Oracle 11
     *
    
     * 
     * @param beneficiario Objeto te tipo beneficiario 
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @param estatusCta el estatus de actividad o inactividad de la cuenta de la asociada
     * @param idCtaDelSur indica si la cuenta es del Banco de sur o de otro banco 
     * @param idCtaPro indica si la cuenta es propia o de un tersero 
     * @return IbBeneficiariosPjDTO
     */
    
    public IbBeneficiariosPjDTO listaIbCuentasPorBeneficiariosPjDTO(IbBeneficiariosPj beneficiario, BigDecimal idCtaDelSur, BigDecimal idCtaPro, Short estatusCta, String idCanal, String codigoCanal);
     
}
