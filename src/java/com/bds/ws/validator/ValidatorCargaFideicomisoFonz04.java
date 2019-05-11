/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.validator;

import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.model.IbFideicomisoDetPj;
import com.bds.ws.util.BDSUtil;
import java.math.BigDecimal;

/**
 *
 * @author luis.perez
 */
public class ValidatorCargaFideicomisoFonz04 extends ValidatorCargaFideicomiso {
    
    protected final static String ERR_KEY_TIPO_PLAN = "pjw.fideicomisoCargaMasiva4.error.tipo_plan";
    protected final static String ERR_KEY_TIPO_PLAN_SAFE = "pjw.fideicomisoCargaMasiva4.error.tipo_plan_safe";
    protected final static String ERR_KEY_SEXO_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.sexo_beneficiario";
    protected final static String ERR_KEY_1NOMBRE_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.primerNombre_beneficiario";
    protected final static String ERR_KEY_1APELLIDO_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.primerApellid_beneficiario";
    protected final static String ERR_KEY_2NOMBRE_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.segundNombre_beneficiario";
    protected final static String ERR_KEY_2APELLIDO_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.segundApellid_beneficiario";
    protected final static String ERR_KEY_EDO_CIVIL = "pjw.fideicomisoCargaMasiva4.error.estado_civil_beneficiario";
    protected final static String ERR_KEY_APELLIDO_CASADA_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.apellido_casada";
    protected final static String ERR_KEY_FECHA_NAC_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.fechaNac_beneficiario";
    protected final static String ERR_KEY_LUGAR_NAC_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.lugarNac_beneficiario";
    protected final static String ERR_KEY_ESTATUS_NACIONALIZACION = "pjw.fideicomisoCargaMasiva4.error.estatus_nacionalizacion";
    protected final static String ERR_KEY_PROFESION_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.profesion_beneficiario";
    protected final static String ERR_KEY_URBANIZACION_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.urbanizacion_beneficiario";
    protected final static String ERR_KEY_CALLE_O_AVENIDA = "pjw.fideicomisoCargaMasiva4.error.calleAvenida_beneficiario";
    protected final static String ERR_KEY_MANZANA_O_PISO_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.manzanaPiso_beneficiario";
    protected final static String ERR_KEY_EDIFICIO_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.edificio_beneficiario";
    protected final static String ERR_KEY_COD_AREA_TELEFONO_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.areaTelefono_beneficiario";
    protected final static String ERR_KEY_TELEFONO_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.telefono_beneficiario";
    protected final static String ERR_KEY_FECHA_INGRESO_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.fechaIngreso_beneficiario";
    protected final static String ERR_KEY_NUMERO_FICHA = "pjw.fideicomisoCargaMasiva4.error.numero_ficha";
    protected final static String ERR_KEY_UBICACION_ADMINISTRATIVA = "pjw.fideicomisoCargaMasiva4.error.ubicacion_administrativa";
    protected final static String ERR_KEY_CAPITALIZA_INTERESES = "pjw.fideicomisoCargaMasiva4.error.capitaliza_intereses";
    protected final static String ERR_KEY_CARGO_OCUPADO = "pjw.fideicomisoCargaMasiva4.error.cargo_ocuopado";
    protected final static String ERR_KEY_SUELDO_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.sueldo_beneficiario";
    protected final static String ERR_KEY_CENTRO_COSTOS_BENEFICIARIO = "pjw.fideicomisoCargaMasiva4.error.centro_costos_beneficiario";
    protected final static String ERR_KEY_TIPO_MOVIMIENTO = "pjw.fideicomisoCargaMasiva4.error.tipo_movimiento";
    protected final static String PARAM_KEY_PLAN_SAFE = "pjw.fideicomisoCargaMasiva4.plan_safe";
    protected final static String ERR_KEY_MONTO_ABONAR_04 = "pjw.fideicomisoCargaMasiva4.error.monto_abonar";
    protected final static String ERR_KEY_NRO_CUENTA = "pjw.fideicomisoCargaMasiva4.error.nro_cuenta";
    protected final static String ERR_KEY_NRO_IDENTIFICACION = "pjw.fideicomisoCargaMasiva4.error.nro_identificacion";
    protected final static String ERR_KEY_TIPO_NACIONALIDAD = "pjw.fideicomisoCargaMasiva4.error.tipo_nacionalidad";
    protected final static String ERR_KEY_CODIGO_EMPRESA = "pjw.fideicomisoCargaMasiva4.error.codigo_empresa";
    
    protected final String FORMATO_FECHA_NAC = "ddMMyyyy";
    protected final String FORMATO_FECHA_INGRESO = "ddMMyyyy";

    protected final int LONGITUD = 46;
    protected final int FONZ04_LON_CODIGO_EMPRESA = 4;
    protected final int FONZ04_LON_CODIGO_PLAN = 4;
    protected final int FONZ04_LON_TIPO_PLAN = 2;
    protected final int FONZ04_LON_TIPO_PLAN_SAFE = 4;
    protected final int FONZ04_LON_TIPO_NACIONALIDAD = 1;
    protected final int FONZ04_LON_NRO_IDENTIFICACION = 10;
    protected final int FONZ04_LON_SIN_USO = 3;
    protected final int FONZ04_LON_SEXO_BENEFICIARIO = 1;

    protected final int FONZ04_LON_1NOMBRE_BENEFICIARIO = 15;
    protected final int FONZ04_LON_2NOMBRE_BENEFICIARIO = 15;
    protected final int FONZ04_LON_1APELLIDO_BENEFICIARIO = 15;
    protected final int FONZ04_LON_2APELLIDO_BENEFICIARIO = 15;

    protected final int FONZ04_LON_EDO_CIVIL_BENEFICIARIO = 1;

    protected final int FONZ04_LON_APELLIDO_CASADA_BENEFICIARIO = 15;
    protected final int FONZ04_LON_FECHA_NAC_BENEFICIARIO = 8;
    protected final int FONZ04_LON_LUGAR_NAC_BENEFICIARIO = 45;
    protected final int FONZ04_LON_ESTATUS_NACIONALIZACION = 1;
    protected final int FONZ04_LON_PROFESION_BENEFICIARIO = 17;
    protected final int FONZ04_LON_URBANIZACION_BENEFICIARIO = 25;
    protected final int FONZ04_LON_CALLE_O_AVENIDA_BENEFICIARIO = 20;
    protected final int FONZ04_LON_MANZANA_O_PISO_BENEFICIARIO = 5;
    protected final int FONZ04_LON_EDIFICIO_BENEFICIARIO = 25;

    protected final int FONZ04_LON_COD_AREA_TELEFONO_BENEFICIARIO = 3;
    protected final int FONZ04_LON_TELEFONO_BENEFICIARIO = 8;
    protected final int FONZ04_LON_FECHA_INGRESO_BENEFICIARIO = 8;

    protected final int FONZ04_LON_NUMERO_FICHA = 10;
    protected final int FONZ04_UBICACION_ADMINISTRATIVA = 10;
    protected final int FONZ04_CAPITALIZA_INTERESES = 1;
    protected final int FONZ04_CARGO_OCUPADO = 37;
    protected final int FONZ04_SUELDO_BENEFICIARIO = 10;
    protected final int FONZ04_CENTRO_COSTOS_BENEFICIARIO = 10;

    protected final int FONZ04_LON_NUMERO_CUENTA = 10;
    protected final int FONZ04_LON_MONTO = 10;
    protected final int FONZ04_LON_TIPO_MOVIMIENTO = 1;

    protected final String FONZ04_SAFE = "9999";

    protected final String VACIO = "";

    public ValidatorCargaFideicomisoFonz04(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
    }

    protected void validarCodigoEmpresa(String aux) {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_CODIGO_EMPRESA));
        }
        //if (Long.valueOf(aux).longValue() != codigoClienteAbanks) {
        //TODO: temporalmente, mientras se verifica con tra que se valida, no puede ser contra abank pues este tiene mas de 4 digitos!                
        //addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_CODIGO_EMPRESA_INCORRECTO));
        //}
    }

    protected void validarTipoPlan(String aux) {
        if (!super.validaSoloNumeros(aux)
                && !(aux.equals("01") || aux.equals("02") || aux.equals("03"))) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TIPO_PLAN));
        }
    }

    protected void validarTipoPlanSAFE(String aux) {
        if (!super.validaSoloNumeros(aux)
                && !(aux.equals(FONZ04_SAFE))) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TIPO_PLAN_SAFE));
        }
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

    protected void validarSexo(String aux) {
        if (!super.validaSolaUnaDeEstasLetras(aux, "FM")) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_SEXO_BENEFICIARIO));
        }
    }

    protected void validarPrimerNombreBeneficiario(String aux, IbFideicomisoDetPj ibFideicomisoDetPj) {
        if (!super.validaAlfabetico(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_1NOMBRE_BENEFICIARIO));
        }
        ibFideicomisoDetPj.setNombreBeneficiario(aux.trim());
    }

    protected void validarPrimerApellidoBeneficiario(String aux, IbFideicomisoDetPj ibFideicomisoDetPj) {
        if (!super.validaAlfabetico(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_1APELLIDO_BENEFICIARIO));
        }
        ibFideicomisoDetPj.setNombreBeneficiario(ibFideicomisoDetPj.getNombreBeneficiario().concat(" ").concat(aux.trim()));
    }

    protected void validarSegundoNombreBeneficiario(String aux, IbFideicomisoDetPj ibFideicomisoDetPj) {
        if (!super.validaAlfabetico(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_2NOMBRE_BENEFICIARIO));
        }
        ibFideicomisoDetPj.setNombreBeneficiario(ibFideicomisoDetPj.getNombreBeneficiario().concat(" ").concat(aux.trim()));
    }

    protected void validarSegundoApellidoBeneficiario(String aux, IbFideicomisoDetPj ibFideicomisoDetPj) {
        if (aux.trim()
                .compareTo(VACIO) != 0) {
            if (!super.validaAlfabetico(aux)) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_2APELLIDO_BENEFICIARIO));
            } else {
                ibFideicomisoDetPj.setNombreBeneficiario(ibFideicomisoDetPj.getNombreBeneficiario().concat(" ").concat(aux.trim()));
            }
        }
    }

    protected void validarEstadoCivil(String aux) {
        if (!super.validaSolaUnaDeEstasLetras(aux,
                "SCDV")) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_EDO_CIVIL));
        }
    }

    protected void validarApellidoCasada(String aux) {
        if (aux.trim().compareTo(VACIO) != 0) {
            if (!super.validaAlfabetico(aux.trim())) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_APELLIDO_CASADA_BENEFICIARIO));
            }
        }
    }

    protected void validarFechaNacimientoBeneficiario(String aux) {
        try {
            if ((!super.validaSoloNumeros(aux))
                    || (!super.validaFecha(aux, FORMATO_FECHA_NAC))
                    || (BDSUtil.formatearFechaStringADate(aux, FORMATO_FECHA_NAC).compareTo(this.ahora()) == 1)) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_FECHA_NAC_BENEFICIARIO));
            }
        } catch (Exception e) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_FECHA_NAC_BENEFICIARIO));
        }
    }

    protected void validarLugarNacimiento(String aux) {
        if (!super.validaAlfabetico(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_LUGAR_NAC_BENEFICIARIO));
        }
    }

    protected void validarEstatusNacionalizacion(String aux) {
        if (!super.validaSolaUnaDeEstasLetras(aux, "SN")) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_ESTATUS_NACIONALIZACION));
        }
    }

    protected void validarProfesion(String aux) {
        if (!super.validaSoloLetras(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_PROFESION_BENEFICIARIO));
        }
    }

    protected void validarUrbanizacion(String aux) {
        if (aux.trim()
                .compareTo(VACIO) != 0) {
            if (!super.validaAlfaNumerico(aux)) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_URBANIZACION_BENEFICIARIO));
            }
        }
    }

    protected void validarAvenida(String aux) {
        if (aux.trim()
                .compareTo(VACIO) != 0) {
            if (!super.validaAlfaNumerico(aux)) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_CALLE_O_AVENIDA));
            }
        }
    }

    protected void validarManzana(String aux) {
        if (aux.trim()
                .compareTo(VACIO) != 0) {
            if (!super.validaAlfaNumerico(aux)) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_MANZANA_O_PISO_BENEFICIARIO));
            }
        }
    }

    protected void validarEdificio(String aux) {
        //NO SE VALIDA ESTE CAMPO PORQUE PUEDE VENIR VACIO
        //edificio del beneficiario
        //if(aux.compareTo(VACIO)!=0)
        //if(!super.validaAlfaNumerico(aux)){
        //addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_EDIFICIO_BENEFICIARIO));
        //}
    }

    protected void validarCodigoAreaTelf(String aux) {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_COD_AREA_TELEFONO_BENEFICIARIO));
        }
    }

    protected void validarTelf(String aux) {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TELEFONO_BENEFICIARIO));
        }
    }

    protected void validarFechaIngreso(String aux) {
        if (!super.validaFecha(aux, FORMATO_FECHA_INGRESO)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_FECHA_INGRESO_BENEFICIARIO));
        }
    }

    protected void validarNumeroFicha(String aux) {
        if (aux.trim()
                .compareTo(VACIO) != 0) {
            if (!super.validaAlfaNumerico(aux)) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_NUMERO_FICHA));
            }
        }
    }

    protected void validarUbicacionAdministrativa(String aux) {
        if (aux.trim()
                .compareTo(VACIO) != 0) {
            if (!super.validaSoloLetras(aux)) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_UBICACION_ADMINISTRATIVA));
            }
        }
    }

    protected void validarCapitalizaIntereses(String aux) {
        if (!super.validaSolaUnaDeEstasLetras(aux, "SN")) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_CAPITALIZA_INTERESES));
        }
    }

    protected void validarCargoOcupado(String aux) {
        if (!super.validaSoloLetras(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_CARGO_OCUPADO));
        }
    }

    protected void validarSueldo(String aux) {
        if (aux.trim().compareTo(VACIO) != 0) {
            if (!super.validaSoloNumeros(aux)) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_SUELDO_BENEFICIARIO));
            }
        }
    }

    protected void validarCentroCosto(String aux) {
        if (aux.trim().compareTo(VACIO) != 0) {
            if (!super.validaSoloNumeros(aux)) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_CENTRO_COSTOS_BENEFICIARIO));
            }
        }
    }

    protected void validarNroCuenta(String aux, IbFideicomisoDetPj ibFideicomisoDetPj) {
        if (aux.trim().compareTo(VACIO) != 0) {
            if (!super.validaSoloNumeros(aux)) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_NRO_CUENTA));
            } else {
                ibFideicomisoDetPj.setNroCuentaBeneficiario(aux);
            }
        }
    }

    protected void validarMontoAbonar(String aux, IbFideicomisoDetPj ibFideicomisoDetPj) {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_MONTO_ABONAR_04));
        } else {
            ibFideicomisoDetPj.setMontoPago((new BigDecimal(aux)).divide(new BigDecimal(100)));
        }
    }

    protected void validarCodigoMovimiento(String aux, IbFideicomisoDetPj ibFideicomisoDetPj) {
        if (!super.validaSolaUnaDeEstasLetras(aux, "12")) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TIPO_MOVIMIENTO));
        } else {
            ibFideicomisoDetPj.setCodigoTransaccion(new Short(aux));
        }
    }
}
