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
public class ValidatorCargaDomiciliacionesLong179Txt extends ValidatorCargaDomiciliaciones {

    public ValidatorCargaDomiciliacionesLong179Txt(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
    }
    private final int LONGITUD_CLIENTE_NORMAL = 179;

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
        String codigoOrdenante = "";
        String rifEmpresa = "";
        int pos = 0;
        String formatoFecha = "ddMMyyyy";
        linea = ((String) param.get("linea")).trim();
        nroLinea = (Integer) param.get("nroLinea");
        nombreArchivo = (String) param.get("fileName");
        rifEmpresa = (String) param.get("rifEmpresa");
        this.setExtension((String) param.get("extension"));

        if (linea.length() != LONGITUD_CLIENTE_NORMAL) {
            String err = mapErrorres.get(ValidatorCargaDomiciliaciones.ERR_KEY_LINEA_DETALLE_TAMANO);
            String mensajeError = err.replaceAll("<debe>", String.valueOf(LONGITUD_CLIENTE_NORMAL))
                    .replaceAll("<posee>", String.valueOf(linea.trim().length()));
            addError(linea, nroLinea, mensajeError);
            throw new IbErroresCargaPjException(mensajeError);
        }

        //Codigo Ordenante
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_CODIGO_ORDENANTE);
        codigoOrdenante = aux;
        if (rifEmpresa.equals(ValidatorCargaDomiciliaciones.RIF_TOYOTA)) {
            if (codigoOrdenante.equals(ValidatorCargaDomiciliaciones.CODIGO_ORDENANTE_TOYOTA)) {
                validarCodigoOrdenante(codigoOrdenante, domiciliacionesCargaDTO);
            } else {
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

        //Tipo pagador
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_TIPO_PAGADOR);
        validarTipoPagador(aux);

        //Detalle tipo pagador
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_TIPO_PAGADOR_DETALLE);
        validarTipoPagadorDetalle(aux);

        //Identificador pagador
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_IDENTIFICADOR_PAGADOR);
        validarIdentificadorPagador(aux);

        //Nombre pagador
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_NOMBRE_PAGADOR);
        validarNombrePagador(aux);

        //Referencia contrato
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_REFERENCIA_CONTRATO);
        validaAlfanumericoConGuion(aux);

        //Numero Factura
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_NUMERO_FACTURA);
        validarNumeroFactura(aux);

        //Fecha factura
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_FECHA_FACTURA);
        validaFecha(aux, formatoFecha);

        //Vencimiento fecha factura
        aux = linea.substring(pos, pos += this.LONGITUD_DETALLE_VENCIMIENTO_FECHA_FACTURA);
        validaFecha(aux, formatoFecha);

        domiciliacionesCargaDTO.setLineaTxt(linea);

        domiciliacionesCargaDTO.setNumeroLinea(nroLinea);

        domiciliacionesCargaDTO.setNombreArchivo(nombreArchivo);

        domiciliacionesCargaDTO.setCodigoOrdenante(codigoOrdenante);

        domiciliacionesCargaDTO.setTipoTxt(ValidatorCargaDomiciliacionesLong179Txt.DOMICILIACION_NORMAL);

        return domiciliacionesCargaDTO;
    }

    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
}
