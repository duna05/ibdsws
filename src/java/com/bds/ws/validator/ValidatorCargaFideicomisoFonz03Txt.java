/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.validator;

import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.exception.IbErroresCargaPjException;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.model.IbFideicomisoDetPj;
import com.bds.ws.model.IbUsuariosPj;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author roger.muñoz
 */
public class ValidatorCargaFideicomisoFonz03Txt extends ValidatorCargaFideicomisoFonz03 {
    
    public ValidatorCargaFideicomisoFonz03Txt(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
    }

    /**
     * *
     * Lee las lineas 1..N
     *
     * @param linea
     * @return
     * @throws IbErroresCargaPjException
     * @throws Exception
     */
    @Override
    public IbFideicomisoDetPj leerDetalle(Map<String, Object> param) throws Exception {
        IbFideicomisoDetPj ibFideicomisoDetPj = new IbFideicomisoDetPj();
        String aux = "";
        int pos = 0;
        linea = ((String) param.get("linea")).trim();
        nroLinea = (Integer) param.get("nroLinea");
        this.setExtension((String) param.get("extension"));
        long codigoClienteAbanks = (Long) param.get("codigoClienteAbanks");

        //Código Empresa
        aux = linea.substring(pos, pos += this.FONZ03_LON_CODIGO_EMPRESA);
        validarCodigoEmpresa(aux);

        //Código Plan
        aux = linea.substring(pos, pos += this.FONZ03_LON_CODIGO_PLAN);
        validarCodigoPlan(aux);

        //Tipo de Nacionalidad
        aux = linea.substring(pos, pos += this.FONZ03_LON_TIPO_NACIONALIDAD);
        validarTipoNacionalidad(aux, ibFideicomisoDetPj);

        //Nro Identificacion
        aux = linea.substring(pos, pos += this.FONZ03_LON_NRO_IDENTIFICACION);
        validarNroIdentificacion(aux, ibFideicomisoDetPj);

        //consecutivo
        aux = linea.substring(pos, pos += this.FONZ03_LON_SIN_USO);

        //Nro Cuenta
        aux = linea.substring(pos, pos += this.FONZ03_LON_NUMERO_CUENTA);
        validarNroCuenta(aux, ibFideicomisoDetPj);

        //Monto abonar
        aux = linea.substring(pos, pos += this.FONZ03_LON_MONTO_ABONAR);
        validarMontoAbonar(aux, ibFideicomisoDetPj);

        //Codigo transaccion
        aux = linea.substring(pos, pos += this.FONZ03_LON_CODIGO_TRANSACCION);
        validarCodigoTransaccion(aux, ibFideicomisoDetPj);

        //usuario
        ibFideicomisoDetPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaArchivoDto.getCodigoUsuario()));

        //Estatus
        ibFideicomisoDetPj.setEstatus(new IbEstatusPagosPj(BigDecimal.ZERO));

        //FechaHoraCarga
        ibFideicomisoDetPj.setFechaHoraCarga(new Date());

        //linea 
        ibFideicomisoDetPj.setLineaTxtXls(linea);

        return ibFideicomisoDetPj;
    }

    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public Object leerCabecera(Map<String, Object> param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
