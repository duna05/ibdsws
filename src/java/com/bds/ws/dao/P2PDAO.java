/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author cesar.mujica
 */
public interface P2PDAO {

    /**
     * invoca al WS de pagos P2P en DelSur.
     *
     * @param codCanalP2P
     * @param nroEmisor
     * @param idCanal String
     * @param nroBeneficiario
     * @param monto
     * @param conceptoPago
     * @param identificacionPagador
     * @param url
     * @param identificacionBeneficiario
     * @param nombreCanal String
     * @return RespuestaDTO
     */
    public UtilDTO realizarPagoP2PTercerosDelsur(String codCanalP2P, String nroEmisor, String nroBeneficiario, String identificacionBeneficiario, String monto, String conceptoPago, String identificacionPagador, String url, String idCanal, String nombreCanal);

    /**
     * invoca al WS de pagos P2P en Otros Bancos.
     *
     * @param codCanalP2P
     * @param nroEmisor
     * @param idCanal String
     * @param nroBeneficiario
     * @param monto
     * @param conceptoPago
     * @param codBanco
     * @param identificacionPagador
     * @param url
     * @param identificacionBeneficiario
     * @param nombreCanal String
     * @return RespuestaDTO
     */
    public UtilDTO realizarPagoP2PTercerosOtrosBancos (String codCanalP2P, String nroEmisor, String nroBeneficiario, String identificacionBeneficiario, String monto, String conceptoPago, String codBanco, String identificacionPagador, String url, String idCanal, String nombreCanal);
   
    /**
     * Método para registrar el beneficiario p2p
     * @param codCanalP2P
     * @param nroEmisor
     * @param nroBeneficiario
     * @param identificacionBeneficiario
     * @param monto
     * @param conceptoPago
     * @param codBanco
     * @param identificacionPagador
     * @param url
     * @param frecuente
     * @param alias
     * @param idUsuarioP2P
     * @param tipoDocumento
     * @param idCanal
     * @param nombreCanal
     * @return 
     */
    public UtilDTO registrarBeneficiarioP2P (String codCanalP2P, String nroEmisor, String nroBeneficiario, String identificacionBeneficiario, String monto, String conceptoPago, String codBanco, String identificacionPagador, String url, boolean frecuente, String alias, BigDecimal idUsuarioP2P, String tipoDocumento, String idCanal, String nombreCanal);

    }
