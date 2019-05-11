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

/**
 *
 * @author jose.perez
 */
public class ValidatorCargaDomiciliacionesLong399Txt extends ValidatorCargaDomiciliaciones {

    public ValidatorCargaDomiciliacionesLong399Txt(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
    }

    //Mensajes de errores archivo longitud 399
    protected final static String ERR_KEY_REFERENCIA_PRESTAMO = "pjw.cargaMasDomiciliaciones.error.archivo.referenciaPrestamo";
    protected final static String ERR_KEY_MONTO_CAPITAL = "pjw.cargaMasDomiciliaciones.error.archivo.montoCapital";
    protected final static String ERR_KEY_MONTO_INTERESES = "pjw.cargaMasDomiciliaciones.error.archivo.montoIntereses";
    protected final static String ERR_KEY_MONTO_MORA = "pjw.cargaMasDomiciliaciones.error.archivo.montoMora";
    protected final static String ERR_KEY_MONTO_COMISION = "pjw.cargaMasDomiciliaciones.error.archivo.montoComision";
    protected final static String ERR_KEY_OTROS_SALDOS = "pjw.cargaMasDomiciliaciones.error.archivo.otrosSaldos";
    protected final static String ERR_KEY_REFERENCIA_SEGURO = "pjw.cargaMasDomiciliaciones.error.archivo.referenciaSeguro";
    protected final static String ERR_KEY_AMORTIZACION_CAPITAL = "pjw.cargaMasDomiciliaciones.error.archivo.amortizacionCapita";
    private final int LONGITUD_CLIENTE_ESPECIAL = 399;

    /**
     * *
     * Lee las lineas 1..N
     *
     * @param param
     * @return
     * @throws IbErroresCargaPjException
     * @throws Exception
     */
    @Override
    public DomiciliacionesCargaDTO leerDetalle(Map<String, Object> param) throws Exception {

        DomiciliacionesCargaDTO domiciliacionesCargaDTO = new DomiciliacionesCargaDTO();

        String aux = "";
        String nombreArchivo = "";
        int pos = 0;
        String rifEmpresa = "";
        String codigoOrdenante = "";
        String formatoFecha = "ddMMyyyy";
        linea = ((String) param.get("linea")).trim();
        nroLinea = (Integer) param.get("nroLinea");
        nombreArchivo = (String) param.get("fileName");
        rifEmpresa = (String) param.get("rifEmpresa");
        this.setExtension((String) param.get("extension"));

        if (linea.length() != LONGITUD_CLIENTE_ESPECIAL) {
            String err = mapErrorres.get(ValidatorCargaDomiciliaciones.ERR_KEY_LINEA_DETALLE_TAMANO);
            String mensajeError = err.replaceAll("<debe>", String.valueOf(LONGITUD_CLIENTE_ESPECIAL))
                    .replaceAll("<posee>", String.valueOf(linea.trim().length()));
            addError(linea, nroLinea, mensajeError);
            throw new IbErroresCargaPjException(mensajeError);
        }

        //Codigo Ordenante
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_CODIGO_ORDENANTE);
        codigoOrdenante = aux;
        if(rifEmpresa.equals(ValidatorCargaDomiciliaciones.RIF_TOYOTA)){
            if(codigoOrdenante.equals(ValidatorCargaDomiciliaciones.CODIGO_ORDENANTE_TOYOTA)){
                validarCodigoOrdenante(codigoOrdenante, domiciliacionesCargaDTO);
            }else{
                String err = mapErrorres.get(ValidatorCargaDomiciliaciones.ERR_KEY_CODIGO_ORDENANTE_TOYOTA);
                String mensajeError = err.replaceAll("@codigoOrdenante", String.valueOf(codigoOrdenante));
                addError(linea, nroLinea, mensajeError);                
                throw new IbErroresCargaPjException(ERR_KEY_CODIGO_ORDENANTE_TOYOTA);                              
            }
        }

        //Clave Ordenante
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_CLAVE_ORDENANTE);
        validarClaveOrdenante(aux);

        //Tipo de Operacion
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_TIPO_OPERACION);
        validarTipoOperacion(aux);

        //Subtipo domiciliacion
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_SUBTIPO_DOMICILIACION);
        validarSubTipoDomiciliacion(aux);

        //Cuenta pagador
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_CUENTA_PAGADOR);
        validarCuentaPagador(aux);

        //Monto
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_MONTO);
        validarMonto(aux);

        ///Tipo pagador
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_TIPO_PAGADOR);
        validarTipoPagador(aux);

        //Detalle tipo pagador
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_TIPO_PAGADOR_DETALLE);
        validarTipoPagadorDetalle(aux);

        //Identificador pagador
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_IDENTIFICADOR_PAGADOR);
        validaSoloNumerosYEspaciosEnBlanco(aux);

        //Nombre pagador
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_NOMBRE_PAGADOR);
        validarNombrePagador(aux);

        //Referencia prestamo
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_REFERENCIA_PRESTAMO);
        validarReferenciaPrestamo(aux);

        //Vencimiento fecha factura
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_VENCIMIENTO_FECHA_FACTURA);
        validaFecha(aux, formatoFecha);

        //Monto capital
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_MONTO_CAPITAL);
        validarMontoCapital(aux);

        //Monto intereses
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_MONTO_INTERESES);
        validarMontoIntereses(aux);

        //Monto Mora
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_MONTO_MORA);
        validarMontoMora(aux);

        //Monto comision
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_MONTO_COMISION);
        validarMontoComision(aux);

        //Otros saldos
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_OTROS_SALDOS);
        validarMontoOtrosSaldos(aux);

        //Referencia seguros
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_REFERENCIA_SEGURO);
        validarReferenciaSeguro(aux);

        //Vencimiento fecha factura S
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_VENCIMIENTO_FECHA_FACTURA);
        validaFecha(aux, formatoFecha);

        //Monto capital S
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_MONTO_CAPITAL_S);
        validarMontoCapital(aux);

        //Monto intereses S
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_MONTO_INTERESES_S);
        validarMontoIntereses(aux);

        //Monto mora S
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_MONTO_MORA_S);
        validarMontoMora(aux);

        //Monto comision S
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_MONTO_COMISION_S);
        validarMontoComision(aux);

        //Monto otros saldos S
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_OTROS_SALDOS_S);
        validarMontoOtrosSaldos(aux);

        //Amortizacion capital S
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_AMORTIZACION_CAPITAL);
        validarAmortizacionCapital(aux);

        //Amortizacion capital S
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_AMORTIZACION_CAPITAL_S);
        validarAmortizacionCapital(aux);

        //Referencia contrato
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_REFERENCIA_CONTRATO);
        validarReferenciaContrato(aux);

        //Numero Factura
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_NUMERO_FACTURA);
        validarNumeroFactura(aux);

        //Fecha factura
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_FECHA_FACTURA);
        validaFecha(aux, formatoFecha);

        domiciliacionesCargaDTO.setLineaTxt(linea);

        domiciliacionesCargaDTO.setNumeroLinea(nroLinea);

        domiciliacionesCargaDTO.setNombreArchivo(nombreArchivo);

        domiciliacionesCargaDTO.setCodigoOrdenante(codigoOrdenante);

        domiciliacionesCargaDTO.setTipoTxt(ValidatorCargaDomiciliacionesLong399Txt.AMORTIZACIONES);

        return domiciliacionesCargaDTO;
    }

    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
}
