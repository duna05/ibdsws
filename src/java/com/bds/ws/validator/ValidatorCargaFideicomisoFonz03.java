/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.validator;

import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.model.IbFideicomisoDetPj;
import java.math.BigDecimal;

/**
 *
 * @author luis.perez
 */
public class ValidatorCargaFideicomisoFonz03 extends ValidatorCargaFideicomiso {
    
    protected final static String ERR_KEY_LINEA_TAMANO = "pjw.fideicomisoCargaMasiva.error.linea_tamano";
    protected final static String ERR_KEY_CODIGO_EMPRESA = "pjw.fideicomisoCargaMasiva.error.codigo_empresa";
    protected final static String ERR_KEY_CODIGO_EMPRESA_INCORRECTO = "pjw.fideicomisoCargaMasiva.error.codigo_empresa_incorrecto";
    protected final static String ERR_KEY_TIPO_NACIONALIDAD = "pjw.fideicomisoCargaMasiva.error.tipo_nacionalidad";
    protected final static String ERR_KEY_NRO_IDENTIFICACION = "pjw.fideicomisoCargaMasiva.error.nro_identificacion";
    protected final static String ERR_KEY_NRO_CUENTA = "pjw.fideicomisoCargaMasiva.error.nro_cuenta";
    protected final static String ERR_KEY_MONTO_ABONAR = "pjw.fideicomisoCargaMasiva.error.monto_abonar";
    protected final static String ERR_KEY_TIPO_TRANSACCION = "pjw.fideicomisoCargaMasiva.error.codigo_transaccion";
    
    protected final int FONZ03_LON_CODIGO_EMPRESA = 4;
    protected final int FONZ03_LON_CODIGO_PLAN = 4;
    protected final int FONZ03_LON_TIPO_NACIONALIDAD = 1;
    protected final int FONZ03_LON_NRO_IDENTIFICACION = 10;
    protected final int FONZ03_LON_SIN_USO = 3;
    protected final int FONZ03_LON_NUMERO_CUENTA = 10;
    protected final int FONZ03_LON_MONTO_ABONAR = 10;
    protected final int FONZ03_LON_CODIGO_TRANSACCION = 4;
    protected final String VACIO = "";

    public ValidatorCargaFideicomisoFonz03(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
    }

    protected void validarCodigoEmpresa(String aux) {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_CODIGO_EMPRESA));
        }
        //if (Long.valueOf(aux).longValue() != codigoClienteAbanks) {
        //TODO: temporalmente, mientras se verifica con tra que se valida, no puede ser contra abank pues este tiene mas de 4 digitos!                
        //addError(linea, nroLinea, getMapErrorres().get(IbFideicomisoPjDAOImpl.ERR_KEY_CODIGO_EMPRESA_INCORRECTO));
        //}
    }

    protected void validarTipoNacionalidad(String aux, IbFideicomisoDetPj ibFideicomisoDetPj) {
        if (!super.validaSolaUnaDeEstasLetras(aux, "VE")) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TIPO_NACIONALIDAD));
        }
        ibFideicomisoDetPj.setNroIdentifiBeneficiario(aux);
    }

    protected void validarNroIdentificacion(String aux, IbFideicomisoDetPj ibFideicomisoDetPj) {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_NRO_IDENTIFICACION));
        }
        ibFideicomisoDetPj.setNroIdentifiBeneficiario(ibFideicomisoDetPj.getNroIdentifiBeneficiario() + aux);
    }

    //consecutivo
    //aux  = linea.substring(pos, pos += this.FONZ03_LON_SIN_USO);
    protected void validarNroCuenta(String aux, IbFideicomisoDetPj ibFideicomisoDetPj) {
        if (aux.trim().compareTo(VACIO) != 0) {
            if (!super.validaSoloNumeros(aux)) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_NRO_CUENTA));
            }
            ibFideicomisoDetPj.setNroCuentaBeneficiario(aux);
        }
    }

    protected void validarMontoAbonar(String aux, IbFideicomisoDetPj ibFideicomisoDetPj) {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_MONTO_ABONAR));
        } else {
            ibFideicomisoDetPj.setMontoPago((new BigDecimal(aux)).divide(new BigDecimal(100)));
        }
    }

    protected void validarCodigoTransaccion(String aux, IbFideicomisoDetPj ibFideicomisoDetPj) {
        if (!(aux.equals("0001"))) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TIPO_TRANSACCION));
        }
        ibFideicomisoDetPj.setCodigoTransaccion(new Short(aux));
    }
}
