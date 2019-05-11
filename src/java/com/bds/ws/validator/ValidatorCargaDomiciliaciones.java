/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.validator;

import com.bds.ws.dto.DomiciliacionesCargaDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.exception.IbErroresCargaPjException;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Jose.Perez
 */
public class ValidatorCargaDomiciliaciones extends Validator {

    public ValidatorCargaDomiciliaciones(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
        pos = 0;
    }

    public final static String ERR_KEY_CODIGO_ORDENANTE = "pjw.cargaMasDomiciliaciones.error.archivo.codigoOrdenante";
    public final static String ERR_KEY_CODIGO_ORDENANTE_TOYOTA = "pjw.cargaMasDomiciliaciones.error.archivo.codigoOrdToyota";
    public final static String ERR_KEY_CLAVE_ORDENANTE = "pjw.cargaMasDomiciliaciones.error.archivo.claveOrdenante";
    public final static String ERR_KEY_TIPO_OPERACION = "pjw.cargaMasDomiciliaciones.error.archivo.tipoOperacion";
    public final static String ERR_KEY_SUBTIPO_DOMICILIACION = "pjw.cargaMasDomiciliaciones.error.archivo.tipoDomiciliacion";
    public final static String ERR_KEY_CUENTA_PAGADOR = "pjw.cargaMasDomiciliaciones.error.archivo.cuentaPagador";
    public final static String ERR_KEY_MONTO = "pjw.cargaMasDomiciliaciones.error.archivo.numeroMonto";
    public final static String ERR_KEY_TIPO_PAGADOR = "pjw.cargaMasDomiciliaciones.error.archivo.tipoPagador";
    public final static String ERR_KEY_TIPO_PAGADOR_DETALLE = "pjw.cargaMasDomiciliaciones.error.archivo.tipoPagadorDetalle";
    public final static String ERR_KEY_IDENTIFICADOR_PAGADOR = "pjw.cargaMasDomiciliaciones.error.archivo.identificaPagador";
    public final static String ERR_KEY_NOMBRE_PAGADOR = "pjw.cargaMasDomiciliaciones.error.archivo.nombrePagador";
    public final static String ERR_KEY_REFERENCIA_CONTRATO = "pjw.cargaMasDomiciliaciones.error.archivo.referenciaContrato";
    public final static String ERR_KEY_NUMERO_FACTURA = "pjw.cargaMasDomiciliaciones.error.archivo.numeroFactura";
    public final static String ERR_KEY_FECHA_FACTURA = "pjw.cargaMasDomiciliaciones.error.archivo.fecha";
    public final static String ERR_KEY_VENCIMIENTO_FECHA_FACTURA = "pjw.cargaMasDomiciliaciones.error.archivo.fecha";
    public final static String ERR_KEY_FORMATO_CUENTA_INVALIDO = "pjw.cargaMasDomiciliaciones.error.archivo.errorBancoDestino";
    public final static String ERR_KEY_REGISTRO_DUPLICADO = "pjw.cargaMasDomiciliaciones.error.archivo.registroDuplicado";

    public final static String ERR_KEY_LINEA_DETALLE_TAMANO = "pjw.cargaMasDomiciliaciones.error.archivo.longitudArchivo";

    protected final int LONGITUD_DETALLE_CODIGO_ORDENANTE = 4;
    protected final int LONGITUD_DETALLE_CLAVE_ORDENANTE = 15;
    protected final int LONGITUD_DETALLE_TIPO_OPERACION = 3;
    protected final int LONGITUD_DETALLE_SUBTIPO_DOMICILIACION = 3;
    protected final int LONGITUD_DETALLE_CUENTA_PAGADOR = 20;
    protected final int LONGITUD_DETALLE_MONTO = 15;
    protected final int LONGITUD_DETALLE_TIPO_PAGADOR = 1;
    protected final int LONGITUD_DETALLE_TIPO_PAGADOR_DETALLE = 1;
    protected final int LONGITUD_DETALLE_IDENTIFICADOR_PAGADOR = 11;
    protected final int LONGITUD_DETALLE_NOMBRE_PAGADOR = 30;
    protected final int LONGITUD_DETALLE_REFERENCIA_CONTRATO = 30;
    protected final int LONGITUD_DETALLE_NUMERO_FACTURA = 30;
    protected final int LONGITUD_DETALLE_FECHA_FACTURA = 8;
    protected final int LONGITUD_DETALLE_VENCIMIENTO_FECHA_FACTURA = 8;
    protected final String VACIO = "";

    //Datos adicionales para carga archivo longitud 399
    protected final int LONGITUD_DETALLE_REFERENCIA_PRESTAMO = 8;
    protected final int LONGITUD_DETALLE_MONTO_CAPITAL = 16;
    protected final int LONGITUD_DETALLE_MONTO_INTERESES = 16;
    protected final int LONGITUD_DETALLE_MONTO_MORA = 16;
    protected final int LONGITUD_DETALLE_MONTO_COMISION = 16;
    protected final int LONGITUD_DETALLE_OTROS_SALDOS = 16;
    protected final int LONGITUD_DETALLE_REFERENCIA_SEGURO = 12;
    protected final int LONGITUD_DETALLE_MONTO_CAPITAL_S = 16;
    protected final int LONGITUD_DETALLE_MONTO_INTERESES_S = 16;
    protected final int LONGITUD_DETALLE_MONTO_MORA_S = 16;
    protected final int LONGITUD_DETALLE_MONTO_COMISION_S = 16;
    protected final int LONGITUD_DETALLE_OTROS_SALDOS_S = 16;
    protected final int LONGITUD_DETALLE_AMORTIZACION_CAPITAL = 16;
    protected final int LONGITUD_DETALLE_AMORTIZACION_CAPITAL_S = 16;
    
    //Rif Toyota y codigo ordenante toyota.
    public final static String RIF_TOYOTA = "308554839";
    public final static String CODIGO_ORDENANTE_TOYOTA = "0001";

    private DomiciliacionesCargaDTO domiciliacionesCargaDTO;
    private ValidatorCargaDomiciliacionesLong399Txt validatorCargaDomiciliacionesLong399Txt;
    String resultadoValidatorTipoPagador = "";

    public final static String DOMICILIACION_NORMAL = "C";
    public final static String AMORTIZACIONES = "M";
    public final String FORMATO_FECHA = "ddMMyyyy";

    protected void validarCodigoOrdenante(String aux, DomiciliacionesCargaDTO domiciliacionesCargaDTO) throws IbErroresCargaPjException {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_CODIGO_ORDENANTE));
            throw new IbErroresCargaPjException(ERR_KEY_CODIGO_ORDENANTE);
        } else {
            domiciliacionesCargaDTO.setCodigoOrdenante(aux);
        }
    }

    protected void validarClaveOrdenante(String aux) throws IbErroresCargaPjException {
        if (!super.validaAlfaNumerico(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_CLAVE_ORDENANTE));
            throw new IbErroresCargaPjException(ERR_KEY_CLAVE_ORDENANTE);
        }
    }

    protected void validarTipoOperacion(String aux) throws IbErroresCargaPjException {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TIPO_OPERACION));
            throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_TIPO_OPERACION));
        }
    }

    protected void validarSubTipoDomiciliacion(String aux) throws IbErroresCargaPjException {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_SUBTIPO_DOMICILIACION));
            throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_SUBTIPO_DOMICILIACION));
        }
    }

    protected void validarCuentaPagador(String aux) throws IbErroresCargaPjException {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_CUENTA_PAGADOR));
            throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_CUENTA_PAGADOR));
        }
    }

    protected void validarMonto(String aux) throws IbErroresCargaPjException {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_MONTO));
            throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_MONTO));
        }
    }

    protected void validarTipoPagador(String aux) throws IbErroresCargaPjException {
        resultadoValidatorTipoPagador = aux;
        if (!super.validaSolaUnaDeEstasLetras(resultadoValidatorTipoPagador, "NRJG")) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TIPO_PAGADOR));
            throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_TIPO_PAGADOR));
        }
    }

    protected void validarTipoPagadorDetalle(String aux) throws IbErroresCargaPjException {
        if (resultadoValidatorTipoPagador.contains("N")) {
            if (!super.validaSolaUnaDeEstasLetras(aux, "VEP")) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TIPO_PAGADOR_DETALLE));
                throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_TIPO_PAGADOR_DETALLE));
            }
        } else if (resultadoValidatorTipoPagador.contains("R")) {
            if (!super.validaSolaUnaDeEstasLetras(aux, "VE")) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TIPO_PAGADOR_DETALLE));
                throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_TIPO_PAGADOR_DETALLE));
            }
        } else if (resultadoValidatorTipoPagador.contains("J")) {
            if (!super.validaSolaUnaDeEstasLetras(aux, "J")) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TIPO_PAGADOR_DETALLE));
                throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_TIPO_PAGADOR_DETALLE));
            }
        } else if (resultadoValidatorTipoPagador.contains("G")) {
            if (!super.validaSolaUnaDeEstasLetras(aux, "G")) {
                addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TIPO_PAGADOR_DETALLE));
                throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_TIPO_PAGADOR_DETALLE));
            }
        } else {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_TIPO_PAGADOR_DETALLE));
            throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_TIPO_PAGADOR_DETALLE));
        }
    }

    protected void validarIdentificadorPagador(String aux) throws IbErroresCargaPjException {
        if (!super.validaSoloNumerosYEspaciosEnBlanco(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_IDENTIFICADOR_PAGADOR));
            throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_IDENTIFICADOR_PAGADOR));
        }
    }

    protected void validarNombrePagador(String aux) throws IbErroresCargaPjException {
        if (!super.validaAlfanumericoPuntoComa(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_NOMBRE_PAGADOR));
            throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_NOMBRE_PAGADOR));
        }

    }

    protected void validarReferenciaContrato(String aux) throws IbErroresCargaPjException {
        if (!super.validaAlfanumericoConGuion(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_REFERENCIA_CONTRATO));
            throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_REFERENCIA_CONTRATO));
        }
    }

    protected void validarNumeroFactura(String aux) throws IbErroresCargaPjException {
        if (!super.validaAlfaNumerico(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_NUMERO_FACTURA));
            throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_NUMERO_FACTURA));
        }
    }

    protected void fechaFactura(String aux) throws IbErroresCargaPjException {
        if (!super.validaFecha(aux, FORMATO_FECHA) && !super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_FECHA_FACTURA));
            throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_FECHA_FACTURA));
        }
    }

    protected void vencimientoFechaFactura(String aux) throws IbErroresCargaPjException {
        if (!super.validaFecha(aux, FORMATO_FECHA) && !super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_VENCIMIENTO_FECHA_FACTURA));
            throw new IbErroresCargaPjException(mapErrorres.get(ERR_KEY_VENCIMIENTO_FECHA_FACTURA));
        }
    }

    protected void validarReferenciaPrestamo(String aux) throws IbErroresCargaPjException {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_REFERENCIA_PRESTAMO));
            throw new IbErroresCargaPjException(getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_REFERENCIA_PRESTAMO));
        }
    }

    protected void validarMontoCapital(String aux) throws IbErroresCargaPjException {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_MONTO_CAPITAL));
            throw new IbErroresCargaPjException(getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_MONTO_CAPITAL));
        }
    }

    protected void validarMontoIntereses(String aux) throws IbErroresCargaPjException {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_MONTO_INTERESES));
            throw new IbErroresCargaPjException(getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_MONTO_INTERESES));
        }
    }

    protected void validarMontoMora(String aux) throws IbErroresCargaPjException {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_MONTO_MORA));
            throw new IbErroresCargaPjException(getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_MONTO_MORA));
        }
    }

    protected void validarMontoComision(String aux) throws IbErroresCargaPjException {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_MONTO_COMISION));
            throw new IbErroresCargaPjException(getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_MONTO_COMISION));
        }
    }

    protected void validarMontoOtrosSaldos(String aux) throws IbErroresCargaPjException {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_OTROS_SALDOS));
            throw new IbErroresCargaPjException(getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_OTROS_SALDOS));
        }
    }

    protected void validarReferenciaSeguro(String aux) throws IbErroresCargaPjException {
        if (!super.validaAlfaNumerico(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_REFERENCIA_SEGURO));
            throw new IbErroresCargaPjException(getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_REFERENCIA_SEGURO));
        }
    }

    protected void validarAmortizacionCapital(String aux) throws IbErroresCargaPjException {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_AMORTIZACION_CAPITAL));
            throw new IbErroresCargaPjException(getMapErrorres().get(ValidatorCargaDomiciliacionesLong399Txt.ERR_KEY_AMORTIZACION_CAPITAL));
        }
    }

    public void setIbErroresCargaPjDTOLista(DomiciliacionesCargaDTO domiciliacionesCargaDTO) {
        this.domiciliacionesCargaDTO = domiciliacionesCargaDTO;
    }

    @Override
    public Object leerDetalle(Map<String, Object> param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object leerCabecera(Map<String, Object> param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object leerDetalle(Row row, Integer nroLinea) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
