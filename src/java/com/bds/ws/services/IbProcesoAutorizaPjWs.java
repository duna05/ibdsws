/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbProcesoAutorizaPjDAO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbAutorizaPj;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author christian.gutierrez
 */
@WebService(serviceName = "IbProcesoAutorizaPjWs")
public class IbProcesoAutorizaPjWs {

    @EJB
    private IbProcesoAutorizaPjDAO ibProcesoAutorizaPjDAO;

    /**
     * Metodo que se utiliza para actualizar los estatus de cada uno de los
     * elementos : IbCargaPagosCorpDetPj Oracle 11
     *
     * @param listAutoriza: Determinada por una lista de ids a los cuales se les
     * va cambiar el estatus si se cumplen los requisitos establecidos
     * @param idUsuario: Indica quien es el usuario que esta realizando el
     * cambio
     * @param cantidadAprovadores: proporciona cuantos individuos deben
     * autorizar una transacción
     * @param idCanal:
     * @param codigoCanal :
     * @return UtilDTO
     */
    @WebMethod(operationName = "autorizaNominaPj")
    public UtilDTO autorizaNominaPj(@WebParam(name = "listAutoriza") List<IbAutorizaPj> ibAutorizaPjsList,
             @WebParam(name = "idUsuario") Long idUsuario,
             @WebParam(name = "cantidadAprovadores") Integer cantidadAprovadores,
             @WebParam(name = "idPago") BigDecimal idPago,
             @WebParam(name = "idCanal") String idCanal,
             @WebParam(name = "codigoCanal") String codigoCanal) {
        return this.ibProcesoAutorizaPjDAO.autorizaNominaPj(ibAutorizaPjsList, idUsuario, cantidadAprovadores, idPago, idCanal, codigoCanal);
    }

    /**
     * Metodo que se utiliza para actualizar los estatus de cada uno de los
     * elementos : IbCargaPagosCorpDetPj Oracle 11
     *
     * @param listAutoriza: Determinada por una lista de ids a los cuales se les
     * va cambiar el estatus si se cumplen los requisitos establecidos
     * @param idUsuario: Indica quien es el usuario que esta realizando el
     * cambio
     * @param cantidadAprovadores: proporciona cuantos individuos deben
     * autorizar una transacción
     * @param idCanal:
     * @param codigoCanal :
     * @return UtilDTO
     */
    @WebMethod(operationName = "autorizaPagosPj")
    public UtilDTO autorizaPagosPj(@WebParam(name = "listAutoriza") List<IbAutorizaPj> ibAutorizaPjsList,
             @WebParam(name = "idUsuario") Long idUsuario,
             @WebParam(name = "cantidadAprovadores") Integer cantidadAprovadores,
             @WebParam(name = "idPago") BigDecimal idPago,
             @WebParam(name = "idCanal") String idCanal,
             @WebParam(name = "codigoCanal") String codigoCanal) {
        return this.ibProcesoAutorizaPjDAO.autorizaPagosPj(ibAutorizaPjsList, idUsuario, cantidadAprovadores, idPago, idCanal, codigoCanal);
    }

    /**
     * Metodo que se utiliza para actualizar los estatus de cada uno de los
     * elementos : ibBeneficiariosPj Oracle 11
     *
     * @param listAutoriza: Determinada por una lista de ids a los cuales se les
     * va cambiar el estatus si se cumplen los requisitos establecidos
     * @param idUsuario: Indica quien es el usuario que esta realizando el
     * cambio
     * @param cantidadAprovadores: proporciona cuantos individuos deben
     * autorizar una transacción
     * @param idCanal:
     * @param codigoCanal :
     * @return UtilDTO
     */
    @WebMethod(operationName = "autorizaBeneficiariosPj")
    public UtilDTO autorizaBeneficiariosPj(@WebParam(name = "listAutoriza") List<IbAutorizaPj> ibAutorizaPjsList,
             @WebParam(name = "idUsuario") Long idUsuario,
             @WebParam(name = "cantidadAprovadores") Integer cantidadAprovadores,
             @WebParam(name = "idPago") BigDecimal idPago,
             @WebParam(name = "idCanal") String idCanal,
             @WebParam(name = "codigoCanal") String codigoCanal) {
        return this.ibProcesoAutorizaPjDAO.autorizaBeneficiariosPj(ibAutorizaPjsList, idUsuario, cantidadAprovadores, idPago, idCanal, codigoCanal);
    }

    /**
     * Metodo que se utiliza para actualizar los estatus de cada uno de los
     * elementos : ibPagosEspecialesDetPj Oracle 11
     *
     * @param listAutoriza: Determinada por una lista de ids a los cuales se les
     * va cambiar el estatus si se cumplen los requisitos establecidos
     * @param idUsuario: Indica quien es el usuario que esta realizando el
     * cambio
     * @param cantidadAprovadores: proporciona cuantos individuos deben
     * autorizar una transacción
     * @param idCanal:
     * @param codigoCanal :
     * @return UtilDTO
     */
    @WebMethod(operationName = "autorizaPagosEspecialesPj")
    public UtilDTO autorizaPagosEspecialesPj(@WebParam(name = "listAutoriza") List<IbAutorizaPj> ibAutorizaPjsList,
             @WebParam(name = "idUsuario") Long idUsuario,
             @WebParam(name = "cantidadAprovadores") Integer cantidadAprovadores,
             @WebParam(name = "idPago") BigDecimal idPago,
             @WebParam(name = "idCanal") String idCanal,
             @WebParam(name = "codigoCanal") String codigoCanal) {
        return this.ibProcesoAutorizaPjDAO.autorizaPagosEspecialesPj(ibAutorizaPjsList, idUsuario, cantidadAprovadores, idPago, idCanal, codigoCanal);
    }

    /**
     *
     * @param ibAutorizaPjsList
     * @param idUsuario
     * @param cantidadAprovadores
     * @param idPago
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @WebMethod(operationName = "autorizaFideicomisoPj")
    public UtilDTO autorizaFideicomisoPj(@WebParam(name = "listAutoriza") List<IbAutorizaPj> ibAutorizaPjsList,
             @WebParam(name = "idUsuario") Long idUsuario,
             @WebParam(name = "cantidadAprovadores") Integer cantidadAprovadores,
             @WebParam(name = "idPago") BigDecimal idPago,
             @WebParam(name = "idCanal") String idCanal,
             @WebParam(name = "codigoCanal") String codigoCanal) {
        return this.ibProcesoAutorizaPjDAO.autorizaFideicomisoPj(ibAutorizaPjsList, idUsuario, cantidadAprovadores, idPago, idCanal, codigoCanal);
    }

    /**
     * Proceso de Autorizacion de las cuentas asociadas a los beneficiarios.
     * @param ibAutorizaPjsList
     * @param idUsuario
     * @param cantidadAprovadores
     * @param idPago
     * @param idCanal
     * @param codigoCanal
     * @return 
     */
    @WebMethod(operationName = "autorizaCtasXBeneficiarioPj")
    public UtilDTO autorizaCtasXBeneficiarioPj(@WebParam(name = "listAutoriza") List<IbAutorizaPj> ibAutorizaPjsList,
             @WebParam(name = "idUsuario") Long idUsuario,
             @WebParam(name = "cantidadAprovadores") Integer cantidadAprovadores,
             @WebParam(name = "idPago") BigDecimal idPago,
             @WebParam(name = "idCanal") String idCanal,
             @WebParam(name = "codigoCanal") String codigoCanal) {
        return this.ibProcesoAutorizaPjDAO.autorizaCtasXBeneficiarioPj(ibAutorizaPjsList, idUsuario, cantidadAprovadores, idPago, idCanal, codigoCanal);
    }

}
