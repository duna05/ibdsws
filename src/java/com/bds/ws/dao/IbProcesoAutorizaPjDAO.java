/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbAutorizaPj;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author christian.gutierrez
 */
public interface IbProcesoAutorizaPjDAO {
    
     /**
     * Metodo que se utiliza para actualizar los estatus de cada uno de los elementos : IbCargaPagosCorpDetPj
     * Oracle 11
     
     * @param listAutoriza: Determinada por una lista de ids a los cuales se les va cambiar el estatus si se cumplen los requisitos establecidos 
     * @param idUsuario: Indica quien es el usuario que esta realizando el cambio
     * @param cantidadAprovadores: proporciona cuantos individuos deben autorizar una transacción
     * @param idCanal:  
     * @param codigoCanal : 
     * @return UtilDTO
     */
    public UtilDTO autorizaNominaPj(List<IbAutorizaPj> ibAutorizaPjsList , Long idUsuario,Integer cantidadAprovadores,BigDecimal idPago, String idCanal, String codigoCanal);
     /**
     * Metodo que se utiliza para actualizar los estatus de cada uno de los elementos : IbCargaPagosCorpDetPj
     * Oracle 11
     
     * @param listAutoriza: Determinada por una lista de ids a los cuales se les va cambiar el estatus si se cumplen los requisitos establecidos 
     * @param idUsuario: Indica quien es el usuario que esta realizando el cambio
     * @param cantidadAprovadores: proporciona cuantos individuos deben autorizar una transacción
     * @param idCanal:  
     * @param codigoCanal : 
     * @return UtilDTO
     */
    public UtilDTO autorizaPagosPj(List<IbAutorizaPj> ibAutorizaPjsList, Long idUsuario, Integer cantidadAprovadores, BigDecimal idPago, String idCanal, String codigoCanal);
    /**
     * Metodo que se utiliza para actualizar los estatus de cada uno de los elementos : ibBeneficiariosPj
     * Oracle 11
     
     * @param listAutoriza: Determinada por una lista de ids a los cuales se les va cambiar el estatus si se cumplen los requisitos establecidos 
     * @param idUsuario: Indica quien es el usuario que esta realizando el cambio
     * @param cantidadAprovadores: proporciona cuantos individuos deben autorizar una transacción
     * @param idCanal:  
     * @param codigoCanal : 
     * @return UtilDTO
     */
    public UtilDTO autorizaBeneficiariosPj(List<IbAutorizaPj> ibAutorizaPjsList, Long idUsuario, Integer cantidadAprovadores, BigDecimal idPago, String idCanal, String codigoCanal);
    
    
    /**
     * Metodo que se utiliza para actualizar los estatus de cada uno de los elementos : ibBeneficiariosPj
     * Oracle 11
     
     * @param listAutoriza: Determinada por una lista de ids a los cuales se les va cambiar el estatus si se cumplen los requisitos establecidos 
     * @param idUsuario: Indica quien es el usuario que esta realizando el cambio
     * @param cantidadAprovadores: proporciona cuantos individuos deben autorizar una transacción
     * @param idCanal:  
     * @param codigoCanal : 
     * @return UtilDTO
     */
    public UtilDTO autorizaPagosEspecialesPj(List<IbAutorizaPj> ibAutorizaPjsList, Long idUsuario, Integer cantidadAprovadores, BigDecimal idPago, String idCanal, String codigoCanal);
    
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
    public UtilDTO autorizaFideicomisoPj(List<IbAutorizaPj> ibAutorizaPjsList, Long idUsuario, Integer cantidadAprovadores, BigDecimal idPago, String idCanal, String codigoCanal);

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
    public UtilDTO autorizaCtasXBeneficiarioPj(List<IbAutorizaPj> ibAutorizaPjsList, Long idUsuario, Integer cantidadAprovadores, BigDecimal idPago, String idCanal, String codigoCanal);
    
    
}
