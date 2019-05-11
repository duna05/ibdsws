/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.validator;

import com.bds.ws.daoimpl.IbCargaPagosEspPjDAOImpl;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.exception.IbErroresCargaPjException;
import com.bds.ws.model.IbCargaPagosEspDetPj;
import com.bds.ws.model.IbEstatusPagosPj;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author robinson.rodriguez
 */
public class ValidatorCargaPagosEspeciales extends Validator {

    private final int LONGITUD_DETALLE = 160;
    private final int LONGITUD_DETALLE_RIF_INDICE = 1;
    private final int LONGITUD_DETALLE_RIF_NUMERADOR = 14;
    private final int LONGITUD_CUENTA_ACREDITAR = 20;
    private final int LONGITUD_MONTO_ACREDITAR = 15;
    private final int LONGITUD_REFERENCIA = 15;
    private final int LONGITUD_CORREO = 51;
    private final int LONGITUD_NOMBRE = 44;

    public ValidatorCargaPagosEspeciales(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
        this.longitudDetalleFONZ03 = LONGITUD_DETALLE;
    }

    @Override
    public Object leerDetalle(Map<String, Object> param) throws Exception {

        IbCargaPagosEspDetPj ibCargaPagosEspDetPj = new IbCargaPagosEspDetPj();

        String aux = "";
        String indiceRif = "";
        int pos = 0;

        linea = (String) param.get("linea");
        nroLinea = (Integer) param.get("nroLinea");

        try {

            //Longitud del detalle
            if (linea.length() != LONGITUD_DETALLE) {
                String err = mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_LINEA_DETALLE_TAMANO);
                String replaceAll = err.replaceAll("<debe>", String.valueOf(LONGITUD_DETALLE))
                        .replaceAll("<posee>", String.valueOf(linea.trim().length()));
                throw new Exception(replaceAll);
            }

            //Indice Rif           
            aux = linea.substring(pos, pos += LONGITUD_DETALLE_RIF_INDICE);
            if (!super.validaIndiceRif(aux.trim())) {
                throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_INDICE_RIF_INVALIDO));
            }
            indiceRif = aux.trim();

            //Numerador Rif
            aux = linea.substring(pos, pos += LONGITUD_DETALLE_RIF_NUMERADOR);
            if (!super.validaSoloNumeros(aux.trim())) {
                String errIndRif = mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_NUMERADOR_RIF_INVALIDO);
                String replaceAllIndRif = errIndRif.replaceAll("<haber>", aux.trim());
                throw new Exception(replaceAllIndRif);
            }
            //Realizamos esta operacion para eliminar los ceros que lleva el numero del rif a la izquierda
            Long auxInt = Long.parseLong(aux.trim());
            aux = String.valueOf(auxInt);
            ibCargaPagosEspDetPj.setNroIdentificacionCliente(indiceRif + aux.trim());

            //Número Cuenta acreditar            
            aux = linea.substring(pos, pos += LONGITUD_CUENTA_ACREDITAR);
            if (!super.validaSoloNumeros(aux.trim())) {
                throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_CUENTA_SOLO_NUMERO));
            }
            ibCargaPagosEspDetPj.setNroCuentaBeneficiario(aux.trim());

            //Monto            
            aux = linea.substring(pos, pos += LONGITUD_MONTO_ACREDITAR);
            if (!super.validaSoloNumeros(aux.trim())) {
                throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_MONTO_NUMEROS));
            }

            ibCargaPagosEspDetPj.setMontoPago((new BigDecimal(aux.trim())).divide(new BigDecimal("100")));

            //Referencia           
            aux = linea.substring(pos, pos += LONGITUD_REFERENCIA);
            if (!super.validaAlfaNumerico(aux.trim())) {
                throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_ALFANUMERICO));
            }

            ibCargaPagosEspDetPj.setReferenciaPago(aux.trim());

            //Correo            
            aux = linea.substring(pos, pos += LONGITUD_CORREO);
            if (!super.validaEmail(aux.trim())) {
                throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_CORREO));
            }

            ibCargaPagosEspDetPj.setEmailBeneficiario(aux.trim());

            //Nombre del cliente            
            aux = linea.substring(pos, pos += LONGITUD_NOMBRE);
            if (!super.validaAlfaNumerico(aux.trim())) {
                throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_ALFANUMERICO));
            }

            ibCargaPagosEspDetPj.setNombreBeneficiario(aux.trim());

            ibCargaPagosEspDetPj.setNroLineaArchivo(nroLinea);

            ibCargaPagosEspDetPj.setEstatus(new IbEstatusPagosPj(BigDecimal.ZERO));

            ibCargaPagosEspDetPj.setFechaHoraCarga(new Date());

            return ibCargaPagosEspDetPj;

        } catch (java.lang.StringIndexOutOfBoundsException strEx) {

            throw new Exception("Linea detalle muy corta, tamaño=" + linea.length());

        } catch (Exception e) {

            throw new Exception("Falla en el formato, linea (" + nroLinea + "),  " + e.getMessage());

        }

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
