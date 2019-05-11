/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.validator;

import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.model.IbFideicomisoDetPj;
import com.bds.ws.model.IbUsuariosPj;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author luis.perez
 */
public class ValidatorCargaFideicomisoFonz03Excel extends ValidatorCargaFideicomisoFonz03 {
    
    public ValidatorCargaFideicomisoFonz03Excel(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
    }

    @Override
    public IbFideicomisoDetPj leerDetalle(Row row, Integer nroLinea) {
        IbFideicomisoDetPj ibFideicomisoDetPj = new IbFideicomisoDetPj();
        String aux;
        this.nroLinea = nroLinea;
        this.linea    = "";
        
        try {
            //Código Empresa
            aux = obtenerValorCelda(row.getCell(0));
            validarCodigoEmpresa(aux);
            linea = String.format("%" + FONZ03_LON_CODIGO_EMPRESA + "s", aux);

            //Código Plan
            aux = obtenerValorCelda(row.getCell(1));
            validarCodigoPlan(aux);
            linea += String.format("%" + FONZ03_LON_CODIGO_PLAN + "s", aux);

            //Tipo de Nacionalidad 
            aux = obtenerValorCelda(row.getCell(2));
            String tipoNacionalidad = aux.substring(0, 1);
            validarTipoNacionalidad(tipoNacionalidad, ibFideicomisoDetPj);
            linea += String.format("%" + FONZ03_LON_TIPO_NACIONALIDAD + "s", tipoNacionalidad);

            //Nro Identificacion
            String nroIdentificacion = aux.substring(1, aux.length());
            validarNroIdentificacion(nroIdentificacion, ibFideicomisoDetPj);
            linea += String.format("%" + FONZ03_LON_NRO_IDENTIFICACION + "s", nroIdentificacion);

            //CONSECUTIVO NO SE USA
            aux = obtenerValorCelda(row.getCell(3));
            linea += String.format("%" + FONZ03_LON_SIN_USO + "s", aux);

            //Nro Cuenta
            aux = obtenerValorCelda(row.getCell(4));
            validarNroCuenta(aux, ibFideicomisoDetPj);
            linea += String.format("%" + FONZ03_LON_NUMERO_CUENTA + "s", aux);

            //Monto abonar
            aux = obtenerValorCelda(row.getCell(5));
            validarMontoAbonar(aux, ibFideicomisoDetPj);
            linea += String.format("%" + FONZ03_LON_MONTO_ABONAR + "s", aux);

            //Transaccion
            aux = obtenerValorCelda(row.getCell(6));
            validarCodigoTransaccion(aux, ibFideicomisoDetPj);
            linea += String.format("%" + FONZ03_LON_CODIGO_TRANSACCION + "s", aux);

            //usuario
            ibFideicomisoDetPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaArchivoDto.getCodigoUsuario()));
            //Estatus
            ibFideicomisoDetPj.setEstatus(new IbEstatusPagosPj(BigDecimal.ZERO));
            //FechaHoraCarga
            ibFideicomisoDetPj.setFechaHoraCarga(new Date());
            //linea 
            ibFideicomisoDetPj.setLineaTxtXls(linea);
        } catch (Exception e) {
            addError(linea, nroLinea, e.getMessage());
        } finally {
            modificarLineaError(linea, nroLinea);
        }
        return ibFideicomisoDetPj;
    }
}
