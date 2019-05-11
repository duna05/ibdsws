/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbBeneficiariosPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbBeneficiariosPj;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author robinson.rodriguez
 */
public interface IbBeneficiariosPjDAO {

    /**
     * Metodo buscar el detalle de pagos especiales Oracle 11
     *
     * @param idBeneficiario id del beneficiario
     * @param estatusCarga estatus de la carga
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE bancario
     * @return
     */
    public List<IbBeneficiariosPjDTO> listarIbBeneficiariosPjDTO(Long idBeneficiario, Short estatusCarga, String idCanal, String codigoCanal);

    /**
     * Metodo buscar el detalle de pagos especiales Oracle 11
     *
     * @param idEmpresa id de la Empresa
     * @param estatusBeneficiario estatus de el beneficiario
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE bancario
     * @return
     */
    public List<IbBeneficiariosPjDTO> listarIbBeneficiariosPorEmpresaPjDTO(String idEmpresa, Short estatusBeneficiario, String idCanal, String codigoCanal);

    /**
     * Metodo buscar el detalle de pagos especiales Oracle 11
     *
     * @param idCtaEmpresa indica la empresa propietaria de la cuenta
     * @param idCtaDelSur indicad si la cuenta es de DelSur o de otro banco
     * @param idCtaPro indica se la cuenta es propia o de un tercero
     * @param estatusCta indica inactividad o activida de la cuenta
     * @param estatusBeneficiario estatus de el beneficiario
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE bancario
     * @return una lista de IbBeneficiariosPjDTO
     */
    public List<IbBeneficiariosPjDTO> listaIbBeneficiariosPorCtaEmpresaPjDTO(BigDecimal idCtaEmpresa, BigDecimal idCtaDelSur, BigDecimal idCtaPro, Short estatusCta, Short estatusBeneficiario, String idCanal, String codigoCanal);

    /**
     * Metodo para insertar una nueva cabecera de proceso de carga de pagos
     * especiales Oracle 11
     *
     * @param ibBeneficiariosPj objeto completo de IbBeneficiariosPjDTO
     * @param codigoCanal String del canal a utilizar
     * @return UtilDTO
     */
    public UtilDTO insertarIbBeneficiariosPj(IbBeneficiariosPj ibBeneficiariosPj, String codigoCanal);

    /**
     * Metodo para modificar una nueva cabecera de proceso de carga de pagos
     * especiales Oracle 11
     *
     * @param idBeneficiario de tipo Long, identificador del beneficiario a
     * cambiar de estatus
     * @param ibEstatusPagosPjDTO objeto completo de tipo IbEstatusPagosPjDTO
     * que establecera el nuevo estatus del pago
     * @param codigoCanal String
     * @return UtilDTO
     */
    public UtilDTO modificarEstatusIbBeneficiariosPj(Long idBeneficiario, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, String codigoCanal);

    /**
     * Metodo para modificar Estatus por Empresa inhabilitar los beneficiarios
     * especiales Oracle 11
     *
     * @param idEmpresa de tipo String, identificador de la empresa
     * @param idbCargaPagosCorpDetPjDTO
     * @param idBeneficiario de tipo Long, identificador del beneficiario a
     * cambiar de estatus
     * @param estatusBeneficiario estatus de el beneficiario
     * @param estatusAutorizacion IbEstatusPagosPj objeto completo de tipo
     * IbEstatusPagosPj que establecera el nuevo estatus
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE bancario
     * @return IbBeneficiariosPjDTO
     */
    public UtilDTO modificarEstatusIbBeneficiariosPorEmpresaPjDTO(String idEmpresa, Long idbCargaPagosCorpDetPjDTO, Long idBeneficiario, Short estatusBeneficiario, Short estatusAutorizacion, String idCanal, String codigoCanal);

    /**
     *
     * @param nroCuenta
     * @param identBeneficiario
     * @param codigoCanal
     * @return
     */
    public UtilDTO validarCtaAsociarBeneficiario(String nroCuenta, String identBeneficiario, String codigoCanal);

    /**
     *
     * @param idBeneficiario
     * @param idEmpresa
     * @param nroCtaBeneficiario
     * @param codigoUsuarioCarga
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    public UtilDTO insertarIbCuentasPorBeneficiariosPjDTO(Long idBeneficiario, String idEmpresa, String nroCtaBeneficiario, String codigoUsuarioCarga, String idCanal, String codigoCanal);

    /**
     * @param idEmpresa
     * @param ibBeneficiariosPj
     * @param codigoCanal
     * @return
     */
    public IbBeneficiariosPjDTO consultarBeneficiariosPorEmpresaPj(String idEmpresa, Long ibBeneficiariosPj, String estatusCta, String codigoCanal);

    /**
     * @param idEmpresa
     * @param ibBeneficiariosPj
     * @param codigoCanal
     * @return
     */
    public IbBeneficiariosPjDTO consultarBeneficiariosPorNroIdentificacionClientePj(BigDecimal idEmpresa, String nroIdentificacionCliente, String codigoCanal);

    /**
     * Lista de beneficiarios cuenta autoriza pj
     *
     * @param idEmpresa
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param StatusAConsultar
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    public IbBeneficiariosPjDTO listarIbBeneficiariosCtaAutorizarPj(BigDecimal idEmpresa, Long idUsuarioAurorizado, Long idServicio, String StatusAConsultar, String idCanal, String codigoCanal);
}
