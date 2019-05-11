/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.P2PDAO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author cesar.mujica
 */
@WebService(serviceName = "P2PWS")
public class P2PWS {
    @EJB
    private P2PDAO ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "realizarPagoP2PTercerosDelsur")
    public UtilDTO realizarPagoP2PTercerosDelsur(@WebParam(name = "codCanalP2P") String codCanalP2P, @WebParam(name = "nroEmisor") String nroEmisor, @WebParam(name = "nroBeneficiario") String nroBeneficiario, @WebParam(name = "identificacionBeneficiario") String identificacionBeneficiario, @WebParam(name = "monto") String monto, @WebParam(name = "conceptoPago") String conceptoPago, @WebParam(name = "identificacionPagador") String identificacionPagador,@WebParam(name = "url") String url, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.realizarPagoP2PTercerosDelsur(codCanalP2P, nroEmisor, nroBeneficiario, identificacionBeneficiario, monto, conceptoPago, identificacionPagador, url, idCanal, nombreCanal);
    }                                             

    @WebMethod(operationName = "realizarPagoP2PTercerosOtrosBancos")
    public UtilDTO realizarPagoP2PTercerosOtrosBancos(@WebParam(name = "codCanalP2P") String codCanalP2P, @WebParam(name = "nroEmisor") String nroEmisor, @WebParam(name = "nroBeneficiario") String nroBeneficiario, @WebParam(name = "identificacionBeneficiario") String identificacionBeneficiario, @WebParam(name = "monto") String monto, @WebParam(name = "conceptoPago") String conceptoPago, @WebParam(name = "identificacionPagador") String identificacionPagador, @WebParam(name = "codBanco") String codBanco, @WebParam(name = "url") String url, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.realizarPagoP2PTercerosOtrosBancos(codCanalP2P, nroEmisor, nroBeneficiario, identificacionBeneficiario, monto, conceptoPago, codBanco, identificacionPagador,  url, idCanal, nombreCanal);
    }
    
    /**
     * Método para registrar el beneficiario p2p
     * @param codCanalP2P
     * @param nroEmisor
     * @param nroBeneficiario
     * @param identificacionBeneficiario
     * @param monto
     * @param conceptoPago
     * @param identificacionPagador
     * @param codBanco
     * @param url
     * @param frecuente
     * @param alias
     * @param idUsuarioP2P
     * @param tipoDocumento
     * @param idCanal
     * @param nombreCanal
     * @return 
     */
    @WebMethod(operationName = "registrarBeneficiarioP2P")
    public UtilDTO registrarBeneficiarioP2P(@WebParam(name = "codCanalP2P") String codCanalP2P, @WebParam(name = "nroEmisor") String nroEmisor, @WebParam(name = "nroBeneficiario") String nroBeneficiario, @WebParam(name = "identificacionBeneficiario") String identificacionBeneficiario, @WebParam(name = "monto") String monto, @WebParam(name = "conceptoPago") String conceptoPago, @WebParam(name = "identificacionPagador") String identificacionPagador, @WebParam(name = "codBanco") String codBanco, @WebParam(name = "url") String url, @WebParam(name = "frecuente") boolean frecuente, @WebParam(name = "alias") String alias, @WebParam(name = "id") BigDecimal idUsuarioP2P, @WebParam(name = "tipoDocumento") String tipoDocumento, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.registrarBeneficiarioP2P(codCanalP2P, nroEmisor, nroBeneficiario, identificacionBeneficiario, monto, conceptoPago, codBanco, identificacionPagador,  url, frecuente, alias, idUsuarioP2P, tipoDocumento, idCanal, nombreCanal);
    }
    
}
