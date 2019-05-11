/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.validator;

import com.bds.ws.daoimpl.IbCargaBeneficiariosPjDAOImpl;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaBeneficiariosPjDTO;
import com.bds.ws.exception.IbErroresCargaPjException;
import com.bds.ws.model.IbBeneficiariosPj;
import com.bds.ws.model.IbEstatusPagosPj;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author robinson.rodriguez
 */
public class ValidatorCargaBeneficiarios extends Validator {

    //Variables declaradas para la validación del archivo de longitud 133.
    private final int LONGITUD_DETALLE_133 = 133;
    private final int LONGITUD_DETALLE_TIPO_NACIONALIDAD_133 = 1;
    private final int LONGITUD_DETALLE_NUMERO_IDENTIFICACION_133 = 12;
    private final int LONGITUD_DETALLE_NOMBRE_BENEFICIARIO_133 = 50;
    private final int LONGITUD_DETALLE_CUENTA_133 = 20;
    private final int LONGITUD_DETALLE_EMAIL_133 = 50;

    private String ctaBeneficiario133 = "";
    private String fntOrigen = "";

    //Variables declaradas para la validación del archivo de longitud 398.
    private final int LONGITUD_DETALLE_398 = 398;
    private final int LONGITUD_DETALLE_TIPO_NACIONALIDAD_398 = 1;
    private final int LONGITUD_DETALLE_NUMERO_IDENTIFICACION_398 = 12;
    private final int LONGITUD_DETALLE_REFERENCIA_398 = 10;
    private final int LONGITUD_DETALLE_NOMBRE_BENEFICIARIO_398 = 50;
    private final int LONGITUD_DETALLE_ESPACIOS_BLANCOS_2_398 = 187;
    private final int LONGITUD_DETALLE_EMAIL_398 = 50;
    private final int LONGITUD_DETALLE_ESPACIOS_BLANCOS_3_398 = 56;
    private final int LONGITUD_DETALLE_CUENTA_DEL_SUR_398 = 12;
    private final int LONGITUD_DETALLE_CUENTA_OTROS_BANCOS_398 = 20;

    private String ctaBeneficiarioDelSur398 = "";
    private String ctaBeneficiarioOtrosBancos398 = "";

    public ValidatorCargaBeneficiarios(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
    }

    @Override
    public IbCargaBeneficiariosPjDTO leerCabecera(Map<String, Object> param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object leerDetalle(Map<String, Object> param) throws Exception {
        IbBeneficiariosPj ibBeneficiariosPj = new IbBeneficiariosPj();

        linea = (String) param.get("linea");
        nroLinea = (Integer) param.get("nroLinea");

        switch (linea.length()) {
            case 133:
                ibBeneficiariosPj = this.detalleValidator133(linea, nroLinea, LONGITUD_DETALLE_133);
                break;
            case 398:
                ibBeneficiariosPj = this.detalleValidator398(linea, nroLinea, LONGITUD_DETALLE_398);
                break;
            default:
                this.errorLinea(linea.length());
                break;
        }

        return ibBeneficiariosPj;

    }

    public IbBeneficiariosPj detalleValidator133(String linea, int nroLinea, int longitud) throws Exception, StringIndexOutOfBoundsException {

        IbBeneficiariosPj ibBeneficiariosPj = new IbBeneficiariosPj();

        String aux = "";
        String indiceRif = "";
        int pos = 0;

        //Indice Rif           
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_TIPO_NACIONALIDAD_133);
        if (!super.validaIndiceRif(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_INDICE_RIF_INVALIDO));
        }
        indiceRif = aux.trim();

        //Numerador Rif
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_NUMERO_IDENTIFICACION_133);
        if (!super.validaSoloNumeros(aux.trim())) {
            String errIndRif = mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_NUMERADOR_RIF_INVALIDO);
            String replaceAllIndRif = errIndRif.replaceAll("<haber>", aux.trim());
            throw new Exception(replaceAllIndRif);
        }
        //Realizamos esta operacion para eliminar los ceros que lleva el numero del rif a la izquierda
        Long auxInt = Long.parseLong(aux.trim());
        aux = String.valueOf(auxInt);
        ibBeneficiariosPj.setNroIdentificacionCliente(indiceRif + aux.trim());

        //Asigno el numero de cliente a la referencia
        ibBeneficiariosPj.setReferenciaBeneficiario(ibBeneficiariosPj.getNroIdentificacionCliente());

        //Nombre del cliente            
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_NOMBRE_BENEFICIARIO_133);
        if (!super.validaAlfaNumerico(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_ALFANUMERICO));
        }

        ibBeneficiariosPj.setNombreBeneficiario(aux.trim());

        //Correo            
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_EMAIL_133);
        if (!super.validaEmail(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_CORREO));
        }

        ibBeneficiariosPj.setEmailBeneficiario(aux.trim());

        //Número Cuenta            
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_CUENTA_133);
        if (!super.validaSoloNumeros(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_CUENTA_SOLO_NUMERO));
        }

        this.setCtaBeneficiario133(aux.trim());

        fntOrigen = "133";

        //Indico el origen de la cuenta
        this.setFntOrigen(fntOrigen);

        ibBeneficiariosPj.setNroLineaArchivo(nroLinea);

        ibBeneficiariosPj.setEstatusAutorizacion(new IbEstatusPagosPj(BigDecimal.ZERO));

        ibBeneficiariosPj.setFechaHoraCarga(new Date());

        return ibBeneficiariosPj;

    }

    public IbBeneficiariosPj detalleValidator398(String linea, int nroLinea, int longitud) throws Exception, StringIndexOutOfBoundsException {

        IbBeneficiariosPj ibBeneficiariosPj = new IbBeneficiariosPj();

        String aux = "";
        String indiceRif = "";
        int pos = 0;

        //Indice Rif           
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_TIPO_NACIONALIDAD_398);
        if (!super.validaIndiceRif(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_INDICE_RIF_INVALIDO));
        }
        indiceRif = aux.trim();

        //Numerador Rif
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_NUMERO_IDENTIFICACION_398);
        if (!super.validaSoloNumeros(aux.trim())) {
            String errIndRif = mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_NUMERADOR_RIF_INVALIDO);
            String replaceAllIndRif = errIndRif.replaceAll("<haber>", aux.trim());
            throw new Exception(replaceAllIndRif);
        }
        //Realizamos esta operacion para eliminar los ceros que lleva el numero del rif a la izquierda
        Long auxInt = Long.parseLong(aux.trim());
        aux = String.valueOf(auxInt);
        ibBeneficiariosPj.setNroIdentificacionCliente(indiceRif + aux.trim());

        //Referencia
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_REFERENCIA_398);
        if (!super.validaAlfaNumerico(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_REFERENCIA));
        }
        ibBeneficiariosPj.setReferenciaBeneficiario(aux.trim());

        //Nombre del cliente            
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_NOMBRE_BENEFICIARIO_398);
        if (!super.validaAlfaNumerico(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_ALFANUMERICO));
        }

        ibBeneficiariosPj.setNombreBeneficiario(aux.trim());

        aux = linea.substring(pos, pos += LONGITUD_DETALLE_ESPACIOS_BLANCOS_2_398);

        //Correo            
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_EMAIL_398);
        if (!super.validaEmail(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_CORREO));
        }

        ibBeneficiariosPj.setEmailBeneficiario(aux.trim());

        aux = linea.substring(pos, pos += LONGITUD_DETALLE_ESPACIOS_BLANCOS_3_398);

        //Número Cuenta del sur           
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_CUENTA_DEL_SUR_398);
        if (!super.validaSoloNumeros(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_CUENTA_SOLO_NUMERO));
        }

        this.setCtaBeneficiarioDelSur398(aux.trim());

        //Número Cuenta otros bancos           
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_CUENTA_OTROS_BANCOS_398);
        if (!super.validaSoloNumeros(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_CUENTA_SOLO_NUMERO));
        }

        this.setCtaBeneficiarioOtrosBancos398(aux.trim());

        fntOrigen = "398";

        //Indico el origen de la cuenta
        this.setFntOrigen(fntOrigen);

        ibBeneficiariosPj.setNroLineaArchivo(nroLinea);

        ibBeneficiariosPj.setEstatusAutorizacion(new IbEstatusPagosPj(BigDecimal.ZERO));

        ibBeneficiariosPj.setFechaHoraCarga(new Date());

        return ibBeneficiariosPj;

    }

    public void errorLinea(int longitud) throws Exception {
        String err = mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_LINEA_DETALLE_TAMANO);
        String replaceAll = err.replaceAll("<posee>", String.valueOf(linea.trim().length()));
        throw new Exception(replaceAll);
    }

    public String getCtaBeneficiario133() {
        return ctaBeneficiario133;
    }

    public void setCtaBeneficiario133(String ctaBeneficiario133) {
        this.ctaBeneficiario133 = ctaBeneficiario133;
    }

    public String getCtaBeneficiarioDelSur398() {
        return ctaBeneficiarioDelSur398;
    }

    public void setCtaBeneficiarioDelSur398(String ctaBeneficiarioDelSur398) {
        this.ctaBeneficiarioDelSur398 = ctaBeneficiarioDelSur398;
    }

    public String getCtaBeneficiarioOtrosBancos398() {
        return ctaBeneficiarioOtrosBancos398;
    }

    public void setCtaBeneficiarioOtrosBancos398(String ctaBeneficiarioOtrosBancos398) {
        this.ctaBeneficiarioOtrosBancos398 = ctaBeneficiarioOtrosBancos398;
    }

    public String getFntOrigen() {
        return fntOrigen;
    }

    public void setFntOrigen(String fntOrigen) {
        this.fntOrigen = fntOrigen;
    }

    @Override
    public Object leerDetalle(Row row, Integer nroLinea) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
