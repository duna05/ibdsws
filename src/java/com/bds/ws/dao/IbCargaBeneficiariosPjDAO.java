/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbBeneficiariosPjDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaBeneficiarioManualDTO;
import com.bds.ws.dto.IbCargaBeneficiarioManualModificarDTO;
import com.bds.ws.dto.IbCargaBeneficiariosPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author robinson.rodriguez
 */
public interface IbCargaBeneficiariosPjDAO {

    /**
     * Metodo para listar las cabeceras del proceso de carga de pagos especiales
     * Oracle 11
     *
     * @param idEmpresa id de la empresa
     * @param estatusCarga estatus en el que se encuentran los registros
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return IbCargaBeneficiariosPjDTO
     */
    public IbCargaBeneficiariosPjDTO listarIbCargaBeneficiariosPj(BigDecimal idEmpresa, Short estatusCarga, String idCanal, String codigoCanal);

    /**
     * Metodo para insertar una nueva cabecera de proceso de carga de pagos
     * especiales Oracle 11
     *
     * @param IbCargaBeneficiariosPjDTO objeto completo de
     * IbCargaBeneficiariosPjDTO
     * @param CargaBeneficiariosDetPjDTO IbBeneficiariosPjDTO
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String del canal a utilizar
     * @return UtilDTO
     */
    public UtilDTO insertarIbCargaBeneficiariosPj(IbCargaBeneficiariosPjDTO IbCargaBeneficiariosPjDTO, IbBeneficiariosPjDTO CargaBeneficiariosDetPjDTO, String idCanal, String codigoCanal);

    /**
     * Metodo para modificar una nueva cabecera de proceso de carga de pagos
     * especiales Oracle 11
     *
     * @param idCargaBeneficiario id del beneficiario
     * @param ibEstatusPagosPjDTO objeto completo de tipo IbEstatusPagosPjDTO
     * que establecera el nuevo estatus del pago
     * @param idEmpresa BigDecimal id de la empresa de sesion
     * @param idCanal String id del canal a utilizar
     * @param codigoCanal String
     * @return UtilDTO
     */
    public UtilDTO modificarEstatusIbCargaBeneficiariosPj(Long idCargaBeneficiario, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, BigDecimal idEmpresa, String idCanal, String codigoCanal);

    /**
     * Metodo para leer de la interfaz el archivo de carga de pagos especiales
     * Oracle 11
     *
     * @param ibCargaArchivoDTO el cual posee codigoUsuario cdClienteAbanks :
     * codigo abanks filename nombre del archivo de nomina enviado data data en
     * bytes del archivo idCanal String id del canal a utilizar codigoCanal
     * String del canal a utilizar
     * @return UtilDTO
     */
    public UtilDTO procesarArchivoIbCargaBeneficiariosPj(IbCargaArchivoDTO ibCargaArchivoDTO);

    /**
     * Metodo eliminar las cabeceras en estatus cero Oracle 11
     *
     * @param idCargaBeneficiario
     * @param codigoCanal
     * @return UtilDTO
     */
    public UtilDTO eliminarIbCargaBeneficiariosPjEstatusCero(Long idCargaBeneficiario, String codigoCanal);

    /**
     * Metodo eliminar las cabeceras en estatus cero Oracle 11
     *
     * @param ibCargaBeneficiarioManualDTO estructura la cual contiene los datos
     * a ser registrados de forma manual del beneficiario.
     * @return UtilDTO
     */
    public UtilDTO procesarRegistroManualIbCargaBeneficiariosPj(IbCargaBeneficiarioManualDTO ibCargaBeneficiarioManualDTO);


    /**
     * Metodo eliminar las cabeceras en estatus cero Oracle 11
     *
     * @param ibCargaBeneficiarioManualModificarDTO estructura la cual contiene
     * los datos a ser modificados de forma manual del beneficiario.
     * @return UtilDTO
     * @param codigoCanal
     */
    public UtilDTO procesarRegistroManualModificarIbCargaBeneficiariosPj(IbCargaBeneficiarioManualModificarDTO ibCargaBeneficiarioManualModificarDTO, String canal, String codigoCanal); 

    
    /**
     * 
     * @param idEmpresa
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param idCanal
     * @param codigoCanal
     * @return 
     */
    public IbCargaBeneficiariosPjDTO listarIbCargaBeneficiariosAutorizarPj(BigDecimal idEmpresa, Long idUsuarioAurorizado,Long idServicio, String StatusAConsultar, String idCanal, String codigoCanal);

}
