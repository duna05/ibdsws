/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.validator;

import com.bds.ws.daoimpl.AfiliacionesDomiciliacionesPjDAOImpl;
import com.bds.ws.dto.AfiliacionesCargaDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.exception.IbErroresCargaPjException;
import static com.bds.ws.validator.ValidatorCargaDomiciliaciones.ERR_KEY_CODIGO_ORDENANTE_TOYOTA;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Jose.Perez
 */
public class ValidatorCargaAfiliaciones extends Validator {

    protected final static String ERR_KEY_LINEA_DETALLE_TAMANO = "pjw.cargaMasDomiciliaciones.error.archivo.longitudArchivo";

    private final int LONGITUD_DETALLE = 117;
    private final int LONGITUD_DETALLE_VALIDA = 30;
    private final int LONGITUD_DETALLE_CODIGO_ORDENANTE = 4;
    private final int LONGITUD_DETALLE_CLAVE_ORDENANTE = 15;
    private final int LONGITUD_DETALLE_BANCO_DESTINO = 4;
    private final int LONGITUD_DETALLE_TIPO_OPERACION = 1;
    private final int LONGITUD_DETALLE_TIPO_PAGADOR = 1;
    protected final int LONGITUD_DETALLE_TIPO_PAGADOR_DETALLE = 1;
    private final int LONGITUD_DETALLE_IDENTIFICADOR_PAGADOR = 11;
    private final int LONGITUD_DETALLE_CUENTA_PAGADOR = 20;
    private final int LONGITUD_DETALLE_NOMBRE_PAGADOR = 30;
    private final int LONGITUD_DETALLE_REFERENCIA_CONTRATO = 30;

    public ValidatorCargaAfiliaciones(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
    }

    @Override
    public Object leerDetalle(Map<String, Object> param) throws Exception {

        AfiliacionesCargaDTO afiliacionesCargaDTO = new AfiliacionesCargaDTO();

        String resultadoValidatorTipoPagador = "";
        String aux = "";
        String codigoOrdenante = "";
        String nombreArchivo = "";
        String rifEmpresa = "";
        int pos = 0;
        linea = ((String) param.get("linea")).trim();
        nroLinea = (Integer) param.get("nroLinea");
        nombreArchivo = (String) param.get("fileName");
        rifEmpresa = (String) param.get("rifEmpresa");
        this.setExtension((String) param.get("extension"));

        //Longitud del detalle        
        if (linea.length() < (LONGITUD_DETALLE - LONGITUD_DETALLE_VALIDA) || linea.length() > LONGITUD_DETALLE) {
            String err = mapErrorres.get(ERR_KEY_LINEA_DETALLE_TAMANO);
            String replaceAll = err.replaceAll("<debe>", String.valueOf(LONGITUD_DETALLE))
                    .replaceAll("<posee>", String.valueOf(linea.trim().length()));
            throw new Exception(replaceAll);
        }

        //Codigo Ordenante
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_CODIGO_ORDENANTE);
        codigoOrdenante = aux;
        if (rifEmpresa.equals(AfiliacionesDomiciliacionesPjDAOImpl.RIF_TOYOTA)) {
            if (!codigoOrdenante.equals(AfiliacionesDomiciliacionesPjDAOImpl.CODIGO_ORDENANTE_TOYOTA)) {
                String err = mapErrorres.get(ERR_KEY_CODIGO_ORDENANTE_TOYOTA);
                String mensajeError = err.replaceAll("@codigoOrdenante", String.valueOf(codigoOrdenante));
                //addError(linea, nroLinea, mensajeError);
                throw new IbErroresCargaPjException(mensajeError);
            } else {
                if (!super.validaSoloNumeros(codigoOrdenante)) {
                    throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_CODIGO_ORDENANTE));
                }
            }
        } else {
            if (!super.validaSoloNumeros(codigoOrdenante)) {
                throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_CODIGO_ORDENANTE));
            }
        }

        //Clave Ordenante
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_CLAVE_ORDENANTE);
        if (!super.validaAlfaNumerico(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_CLAVE_ORDENANTE));
        }

        //Banco destino
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_BANCO_DESTINO);
        if (!super.validaSoloNumeros(aux.trim())) {
            throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_BANCO_DESTINO));
        }

        //Tipo Operacion  
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_TIPO_OPERACION);
        if (!super.validaSolaUnaDeEstasLetras(aux, "AD")) {
            throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_TIPO_OPERACION_AFILIACION));
        }

        //Tipo Pagador          
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_TIPO_PAGADOR);
        if (!super.validaSolaUnaDeEstasLetras(aux, "NRJG")) {
            throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_TIPO_PAGADOR));
        }

        //Valor de tipo pagador para validar el detalle del tipo pagador
        resultadoValidatorTipoPagador = aux;

        //Detalle tipo pagador
        if (resultadoValidatorTipoPagador.contains("N")) {
            aux = linea.substring(pos, pos += LONGITUD_DETALLE_TIPO_PAGADOR_DETALLE);
            if (!super.validaSolaUnaDeEstasLetras(aux, "VEP")) {
                throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_TIPO_PAGADOR_DETALLE));
            }
        } else if (resultadoValidatorTipoPagador.contains("R")) {
            aux = linea.substring(pos, pos += LONGITUD_DETALLE_TIPO_PAGADOR_DETALLE);
            if (!super.validaSolaUnaDeEstasLetras(aux, "VE")) {
                throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_TIPO_PAGADOR_DETALLE));
            }
        } else if (resultadoValidatorTipoPagador.contains("J")) {
            aux = linea.substring(pos, pos += LONGITUD_DETALLE_TIPO_PAGADOR_DETALLE);
            if (!super.validaSolaUnaDeEstasLetras(aux, "J")) {
                throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_TIPO_PAGADOR_DETALLE));
            }
        } else if (resultadoValidatorTipoPagador.contains("G")) {
            aux = linea.substring(pos, pos += LONGITUD_DETALLE_TIPO_PAGADOR_DETALLE);
            if (!super.validaSolaUnaDeEstasLetras(aux, "G")) {
                throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_TIPO_PAGADOR_DETALLE));
            }
        }

        //Tipo pagador
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_IDENTIFICADOR_PAGADOR);
        if (!super.validaSoloNumeros(aux)) {
            throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_IDENTIFICADOR_PAGADOR));
        }

        //Cuenta Pagador    
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_CUENTA_PAGADOR);
        if (!super.validaSoloNumeros(aux)) {
            throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_LINEA_CUENTA_PAGADOR));
        }

        //Nombre Pagador
        aux = linea.substring(pos, pos += LONGITUD_DETALLE_NOMBRE_PAGADOR);
        if (!super.validaAlfanumericoPuntoComa(aux)) {
            throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_NOMBRE_PAGADOR));
        }

        //Referencia Contrato
        aux = linea.substring(pos, linea.length() - 1);
        if (!super.validaAlfaNumerico(aux)) {
            throw new IbErroresCargaPjException(mapErrorres.get(AfiliacionesDomiciliacionesPjDAOImpl.ERR_KEY_CONTRATO));
        }

        afiliacionesCargaDTO.setLineaTxt(linea);

        afiliacionesCargaDTO.setNumeroLinea(nroLinea);

        afiliacionesCargaDTO.setNombreArchivo(nombreArchivo);

        afiliacionesCargaDTO.setCodigoOrdenante(codigoOrdenante);

        return afiliacionesCargaDTO;
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
