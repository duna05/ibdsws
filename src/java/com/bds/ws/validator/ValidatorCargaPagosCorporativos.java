/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.validator;

import com.bds.ws.daoimpl.IbCargaPagosCorpPjDAOImpl;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.exception.IbErroresCargaPjException;
import com.bds.ws.model.IbCargaPagosCorpDetPj;
import java.math.BigDecimal;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author robinson.rodriguez
 */
public class ValidatorCargaPagosCorporativos extends Validator {

    //Formato fecha
    private final String FORMATO_FECHA_PAGO = "dd/MM/yyyy";
    private String fntOrigen = "";

    //Variables declaradas para la validación del archivo de longitud 248.
    private final int LONGITUD_DETALLE_248 = 248;
    private final int LONGITUD_DETALLE_TIPO_NACIONALIDAD_248 = 1;
    private final int LONGITUD_DETALLE_NUMERO_IDENTIFICACION_248 = 12;
    private final int LONGITUD_DETALLE_NUMERO_CUENTA_DEBITO_248 = 12;
    private final int LONGITUD_DETALLE_NUMERO_CUENTA_ABONO_248 = 20;
    private final int LONGITUD_DETALLE_FECHA_PROCESO_ABONO_248 = 10;
    private final int LONGITUD_DETALLE_REFERENCIA_PAGO_248 = 15;
    private final int LONGITUD_DETALLE_MONTO_PAGO_248 = 18;
    private final int LONGITUD_DETALLE_MOTIVO_PAGO_248 = 160;

    //Variables declaradas para la validación del archivo de longitud 387.
    private final int LONGITUD_DETALLE_387 = 387;
    private final int LONGITUD_DETALLE_NUMERO_IDENTIFICACION_387 = 13;
    private final int LONGITUD_DETALLE_NUMERO_CUENTA_DEBITO_387 = 12;
    private final int LONGITUD_DETALLE_ESPACIOS_BLANCOS_387 = 1;
    private final int LONGITUD_DETALLE_FECHA_PROCESO_ABONO_387 = 10;
    private final int LONGITUD_DETALLE_MONTO_PAGO_387 = 18;
    private final int LONGITUD_DETALLE_REFERENCIA_PAGO_387 = 15;
    private final int LONGITUD_DETALLE_MOTIVO_PAGO_387 = 160;

    public ValidatorCargaPagosCorporativos(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
    }

    @Override
    public Object leerCabecera(Map<String, Object> param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object leerDetalle(Map<String, Object> param) throws Exception {
        IbCargaPagosCorpDetPj ibCargaPagosCorpDetPj = new IbCargaPagosCorpDetPj();

        linea = (String) param.get("linea");
        nroLinea = (Integer) param.get("nroLinea");

        switch (linea.length()) {
            case 248:
                ibCargaPagosCorpDetPj = this.detalleValidator248(linea, nroLinea, LONGITUD_DETALLE_248);
                break;
            case 387:
                ibCargaPagosCorpDetPj = this.detalleValidator387(linea, nroLinea, LONGITUD_DETALLE_387);
                break;
            default:
                this.errorLinea(linea.length(), IbCargaPagosCorpPjDAOImpl.ERR_KEY_LINEA_DETALLE_TAMANO);
                break;
        }

        return ibCargaPagosCorpDetPj;

    }

    @Override
    public Object leerDetalle(Row row, Integer nroLinea) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public IbCargaPagosCorpDetPj detalleValidator248(String linea, int nroLinea, int longitud) throws Exception, StringIndexOutOfBoundsException {
        IbCargaPagosCorpDetPj ibCargaPagosCorpDetPj = new IbCargaPagosCorpDetPj();

        String aux = "";
        String indiceRif = "";
        int pos = 0;

        //Indice Rif           
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_TIPO_NACIONALIDAD_248);
        if (!super.validaIndiceRif(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_INDICE_RIF_INVALIDO));
        }

        indiceRif = aux.trim();

        //Numerador Rif
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_NUMERO_IDENTIFICACION_248);
        if (!super.validaSoloNumeros(aux.trim())) {
            String errIndRif = mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_NUMERADOR_RIF_INVALIDO);
            String replaceAllIndRif = errIndRif.replaceAll("<haber>", aux.trim());
            throw new Exception(replaceAllIndRif);
        }
        //Realizamos esta operacion para eliminar los ceros que lleva el numero del rif a la izquierda
        Long auxInt = Long.parseLong(aux.trim());
        aux = String.valueOf(auxInt);
        ibCargaPagosCorpDetPj.setNroIdentificacionCliente(indiceRif + aux.trim());

        //Cuenta debito
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_NUMERO_CUENTA_DEBITO_248);
        if (!super.validaSoloNumeros(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_CUENTA_SOLO_NUMERO));
        }

        //Debe ir la cuenta debito
        //Por validar
        //Cuenta beneficiario
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_NUMERO_CUENTA_ABONO_248);
        if (!super.validaSoloNumeros(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_CUENTA_SOLO_NUMERO));
        }

        ibCargaPagosCorpDetPj.setNroCuentaBeneficiario(aux.trim());

        //Fecha de carga
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_FECHA_PROCESO_ABONO_248);

        if (!super.validaFecha(aux, FORMATO_FECHA_PAGO)) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_FORMATO_FECHA_PAGO));
        }

//        ibCargaPagosCorpDetPj.setFechaHoraCarga((new SimpleDateFormat(FORMATO_FECHA_PAGO)).parse(aux));

        //Referencia           
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_REFERENCIA_PAGO_248);
        if (!super.validaAlfaNumerico(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_ALFANUMERICO));
        }

        ibCargaPagosCorpDetPj.setReferenciaPago(aux.trim());

        //Monto
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_MONTO_PAGO_248);
        if (!super.validaSoloNumeros(aux)) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_MONTO_NUMEROS));
        }

        ibCargaPagosCorpDetPj.setMontoPago((new BigDecimal(aux)).divide(new BigDecimal("100")));

        //Motivo_Pago            
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_MOTIVO_PAGO_248).trim();
        if (!super.validaAlfanumericoPuntoComa(aux)) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_MOTIVO_PAGO));
        }

        ibCargaPagosCorpDetPj.setMotivoDePago(aux.trim());

        fntOrigen = "248";

        //Indico el origen de la cuenta
        this.setFntOrigen(fntOrigen);

        return ibCargaPagosCorpDetPj;
    }

    public IbCargaPagosCorpDetPj detalleValidator387(String linea, int nroLinea, int longitud) throws Exception, StringIndexOutOfBoundsException {
        IbCargaPagosCorpDetPj ibCargaPagosCorpDetPj = new IbCargaPagosCorpDetPj();

        String aux = "";
        int pos = 0;

        //Numerador Rif
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_NUMERO_IDENTIFICACION_387);
        if (!super.validaSoloNumeros(aux.trim())) {
            String errIndRif = mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_NUMERADOR_RIF_INVALIDO);
            String replaceAllIndRif = errIndRif.replaceAll("<haber>", aux.trim());
            throw new Exception(replaceAllIndRif);
        }
        //Realizamos esta operacion para eliminar los ceros que lleva el numero del rif a la izquierda
        Long auxInt = Long.parseLong(aux.trim());
        aux = String.valueOf(auxInt);
        ibCargaPagosCorpDetPj.setNroIdentificacionCliente(aux.trim());

        //Cuenta beneficiario
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_NUMERO_CUENTA_DEBITO_387);
        if (!super.validaSoloNumeros(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_CUENTA_SOLO_NUMERO));
        }

        ibCargaPagosCorpDetPj.setNroCuentaBeneficiario(aux.trim());

        aux = linea.substring(pos, pos += LONGITUD_DETALLE_ESPACIOS_BLANCOS_387);

        //Fecha de carga
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_FECHA_PROCESO_ABONO_387);
        if (!super.validaFecha(aux, FORMATO_FECHA_PAGO)) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_FORMATO_FECHA_PAGO));
        }

//        ibCargaPagosCorpDetPj.setFechaHoraCarga((new SimpleDateFormat(FORMATO_FECHA_PAGO)).parse(aux));

        //Monto
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_MONTO_PAGO_387);
        if (!super.validaSoloNumeros(aux)) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_MONTO_NUMEROS));
        }

        ibCargaPagosCorpDetPj.setMontoPago((new BigDecimal(aux)).divide(new BigDecimal("100")));

        //Referencia           
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_REFERENCIA_PAGO_387);
        if (!super.validaAlfaNumerico(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_ALFANUMERICO));
        }

        ibCargaPagosCorpDetPj.setReferenciaPago(aux.trim());

        //Motivo_Pago            
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_MOTIVO_PAGO_387).trim();
        if (!super.validaAlfanumericoPuntoComa(aux)) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_MOTIVO_PAGO));
        }

        fntOrigen = "387";

        //Indico el origen de la cuenta
        this.setFntOrigen(fntOrigen);

        ibCargaPagosCorpDetPj.setMotivoDePago(aux.trim());

        return ibCargaPagosCorpDetPj;
    }

    public String getFntOrigen() {
        return fntOrigen;
    }

    public void setFntOrigen(String fntOrigen) {
        this.fntOrigen = fntOrigen;
    }

}
