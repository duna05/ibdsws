/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbBeneficiariosPjDAO;
import com.bds.ws.dao.IbCuetasPorBeneficiariosPjDAO;
import com.bds.ws.dto.IbBeneficiariosPjDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbBeneficiariosPj;
import com.bds.ws.model.IbCtasXBeneficiariosPj;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author robinson.rodriguez
 */
@WebService(serviceName = "IbBeneficiariosPjWs")
public class IbBeneficiariosPjWs {

    @EJB
    private IbBeneficiariosPjDAO ibBeneficiariosPjDAO;
    @EJB
    IbCuetasPorBeneficiariosPjDAO ibCuetasPorBeneficiariosPjDAO;

    /**
     * Metodo buscar el detalle de pagos especiales Oracle 11
     *
     * @param idBeneficiario id del beneficiario a buscar
     * @param estatusCarga
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @return IbRegistrosMasivosPjDTO
     */
    @WebMethod(operationName = "listarIbBeneficiariosPjDTO")
    public List<IbBeneficiariosPjDTO> listarIbBeneficiariosPjDTO(
            @WebParam(name = "idBeneficiario") Long idBeneficiario,
            @WebParam(name = "estatusCarga") Short estatusCarga,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibBeneficiariosPjDAO.listarIbBeneficiariosPjDTO(idBeneficiario, estatusCarga, idCanal, codigoCanal);
    }

    /**
     * Metodo buscar los beneficiarios asociados a una empresa en particular en
     * Oracle 11
     *
     * @param idEmpresa es el codigo de identificacion de la empresa propietaria
     * de la cta.
     * @param idBeneficiario id del beneficiario a buscar
     * @param estatusCarga estatus de carga de la cuentas asociada
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @return IbBeneficiariosPjDTO
     */
    @WebMethod(operationName = "listarIbBeneficiariosPorEmpresaPjDTO")
    public List<IbBeneficiariosPjDTO> listarIbBeneficiariosPorEmpresaPjDTO(@WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "estatusCarga") Short estatusCarga,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibBeneficiariosPjDAO.listarIbBeneficiariosPorEmpresaPjDTO(idEmpresa, estatusCarga, idCanal, codigoCanal);
    }

    /**
     * Metodo buscar los beneficiarios asociados a una empresa en particular en
     * Oracle 11
     *
     * @param idCtaEmpresa es el codigo de identificacion de la empresa
     * propietaria de la cta.
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @param estatusCta el estatus de actividad o inactividad de la cuenta de
     * la asociada
     * @param estatusBeneficiario determica que usuario esta activo o inactivo
     * @param idCtaDelSur indica si la cuenta es del Banco de sur o de otro
     * banco
     * @param idCtaPro indica si la cuenta es propia o de un tersero
     * @return IbBeneficiariosPjDTO
     */
    @WebMethod(operationName = "listaIbBeneficiariosPorCtaEmpresaPjDTO")
    public List<IbBeneficiariosPjDTO> consultarIbBeneficiariosCtaEmpresaPjDTO(
            @WebParam(name = "idCtaEmpresa") BigDecimal idCtaEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal,
            @WebParam(name = "estatusCarga") Short estatusCta,
            @WebParam(name = "estatusBeneficiario") Short estatusBeneficiario,
            @WebParam(name = "idCtaDelSur") BigDecimal idCtaDelSur,
            @WebParam(name = "idCtaPro") BigDecimal idCtaPro) {
        return ibBeneficiariosPjDAO.listaIbBeneficiariosPorCtaEmpresaPjDTO(idCtaEmpresa, idCtaDelSur, idCtaPro, estatusCta, estatusBeneficiario, idCanal, codigoCanal);

    }

    /**
     * Metodo buscar los beneficiarios asociados a una empresa en particular en
     * Oracle 11
     *
     * @param idCtaEmpresa es el codigo de identificacion de la empresa
     * propietaria de la cta.
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @param estatusCta el estatus de actividad o inactividad de la cuenta de
     * la asociada
     * @param idCtaDelSur indica si la cuenta es del Banco de sur o de otro
     * banco
     * @param idCtaPro indica si la cuenta es propia o de un tersero
     * @return IbBeneficiariosPjDTO
     */
    @WebMethod(operationName = "consultarIbBeneficiariosPorCtasPjDTO")
    public IbBeneficiariosPjDTO consultarIbBeneficiariosPorCtasPjDTO(
            @WebParam(name = "idCtaEmpresa") BigDecimal idCtaEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal,
            @WebParam(name = "estatusCarga") Short estatusCta,
            @WebParam(name = "idCtaDelSur") BigDecimal idCtaDelSur,
            @WebParam(name = "idCtaPro") BigDecimal idCtaPro) {
        return ibCuetasPorBeneficiariosPjDAO.listaIbBeneficiariosPorCtaEmpresaPjDTO(idCtaEmpresa, idCtaDelSur, idCtaPro, estatusCta, idCanal, codigoCanal);
    }

    /**
     * Metodo buscar los beneficiarios asociados a una empresa en particular en
     * Oracle 11
     *
     * @param beneficiario Objeto tipo beneficiario
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE banario
     * @param estatusCta el estatus de actividad o inactividad de la cuenta de
     * la asociada
     * @param idCtaDelSur indica si la cuenta es del Banco de sur o de otro
     * banco
     * @param idCtaPro indica si la cuenta es propia o de un tersero
     * @return IbBeneficiariosPjDTO
     */
    @WebMethod(operationName = "consultarIbCuentasPorBeneficiariosPjDTO")
    public IbBeneficiariosPjDTO consultarIbCuentasPorBeneficiariosPjDTO(
            @WebParam(name = "beneficiario") IbBeneficiariosPj beneficiario,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal,
            @WebParam(name = "estatusCarga") Short estatusCta,
            @WebParam(name = "idCtaDelSur") BigDecimal idCtaDelSur,
            @WebParam(name = "idCtaPro") BigDecimal idCtaPro) {
        return ibCuetasPorBeneficiariosPjDAO.listaIbCuentasPorBeneficiariosPjDTO(beneficiario, idCtaDelSur, idCtaPro, estatusCta, idCanal, codigoCanal);

    }

    /**
     * Metodo para validar que el numero de cuenta introducido exista y
     * pertenesca al benefeciario asignado
     *
     * @param nroCuenta String snumero de cuenta a validar
     * @param identBeneficiario String identificacion del benefeciario
     * @param codigoCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no
     * y contiene el dato de respuesta en el map con key perteneseCta, es una
     * tipo String
     */
    @WebMethod(operationName = "validarCtaAsociarBeneficiario")
    public UtilDTO validarCtaAsociarBeneficiario(
            @WebParam(name = "nroCuenta") String nroCuenta,
            @WebParam(name = "identBeneficiario") String identBeneficiario,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return this.ibBeneficiariosPjDAO.validarCtaAsociarBeneficiario(nroCuenta, identBeneficiario, codigoCanal);
    }

    /**
     *
     * @param idBeneficiario
     * @param idCtaEmpresa
     * @param idCanal
     * @param codigoCanal
     * @param idEmpresa
     * @return
     */
    @WebMethod(operationName = "insertarIbCuentasPorBeneficiariosPjDTO")
    public UtilDTO insertarIbCuentasPorBeneficiariosPjDTO(
            @WebParam(name = "idBeneficiario") Long idBeneficiario,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "nroCtaBeneficiario") String nroCtaBeneficiario,
            @WebParam(name = "codigoUsuarioCarga") String codigoUsuarioCarga,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibBeneficiariosPjDAO.insertarIbCuentasPorBeneficiariosPjDTO(idBeneficiario, idEmpresa, nroCtaBeneficiario, codigoUsuarioCarga, idCanal, codigoCanal);
    }

    @WebMethod(operationName = "ibCtasXBeneficiariosPjPrueba")
    public IbCtasXBeneficiariosPj ibCtasXBeneficiariosPjPrueba() {
        return new IbCtasXBeneficiariosPj();
    }

    /**
     *
     * @param idEmpresa
     * @param ibBeneficiariosPj
     * @param estatusCta
     * @param codigoCanal
     * @return
     */
    @WebMethod(operationName = "consultarBeneficiariosPorEmpresaPj")
    public IbBeneficiariosPjDTO ibBeneficiariosPorEmpresaPj(
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "ibBeneficiariosPj") Long ibBeneficiariosPj,
            @WebParam(name = "estatusCta") String estatusCta,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibBeneficiariosPjDAO.consultarBeneficiariosPorEmpresaPj(idEmpresa, ibBeneficiariosPj, estatusCta, codigoCanal);
    }

    /**
     *
     * @param nroIdentificacionCliente
     * @param codigoCanal
     * @return BigDecimal idEmpresa,
     */
    @WebMethod(operationName = "ibBeneficiariosPorNroIdentificacionClientePj")
    public IbBeneficiariosPjDTO ibBeneficiariosPorNroIdentificacionClientePj(
            @WebParam(name = "idEmpresa") BigDecimal idEmpresa,
            @WebParam(name = "nroIdentificacionCliente") String nroIdentificacionCliente,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibBeneficiariosPjDAO.consultarBeneficiariosPorNroIdentificacionClientePj(idEmpresa, nroIdentificacionCliente, codigoCanal);
    }

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
    @WebMethod(operationName = "listarIbBeneficiariosCtaAutorizarPj")
    public IbBeneficiariosPjDTO listarIbBeneficiariosCtaAutorizarPj(
            @WebParam(name = "idEmpresa") BigDecimal idEmpresa,
            @WebParam(name = "idUsuarioAurorizado") Long idUsuarioAurorizado,
            @WebParam(name = "idServicio") Long idServicio,
            @WebParam(name = "StatusAConsultar") String StatusAConsultar,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibBeneficiariosPjDAO.listarIbBeneficiariosCtaAutorizarPj(idEmpresa, idUsuarioAurorizado, idServicio, StatusAConsultar, idCanal, codigoCanal);

    }
}
