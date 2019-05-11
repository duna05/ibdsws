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
public class ValidatorCargaFideicomisoFonz04Excel extends ValidatorCargaFideicomisoFonz04 {

    public ValidatorCargaFideicomisoFonz04Excel(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
    }

    @Override
    public IbFideicomisoDetPj leerDetalle(Row row, Integer nroLinea) {
        IbFideicomisoDetPj ibFideicomisoDetPj = new IbFideicomisoDetPj();
        String aux;
        this.nroLinea = nroLinea;
        this.linea    = "";

        try {
            //CODIGO EMPRESA
            aux = obtenerValorCelda(row.getCell(0));
            validarCodigoEmpresa(aux);
            linea += String.format("%" + FONZ04_LON_CODIGO_EMPRESA + "s", aux);

            //CODIGO PLAN
            aux = obtenerValorCelda(row.getCell(1));
            validarCodigoPlan(aux);
            linea += String.format("%" + FONZ04_LON_CODIGO_PLAN + "s", aux);

            //TIPO DE PLAN
            aux = obtenerValorCelda(row.getCell(2));
            validarTipoPlan(aux);
            linea += String.format("%" + FONZ04_LON_TIPO_PLAN + "s", aux);

            //TIPO PLAN SAFE
            aux = obtenerValorCelda(row.getCell(3));
            validarTipoPlanSAFE(aux);
            linea += String.format("%" + FONZ04_LON_TIPO_PLAN_SAFE + "s", aux);

            //TIPO NACIONALIDAD
            aux = obtenerValorCelda(row.getCell(4));
            validarTipoNacionalidad(aux, ibFideicomisoDetPj);
            linea += String.format("%-" + FONZ04_LON_TIPO_NACIONALIDAD + "s", aux);

            //NRO IDENTIFICACION
            aux = obtenerValorCelda(row.getCell(5));
            validarNroIdentificacion(aux, ibFideicomisoDetPj);
            linea += String.format("%" + FONZ04_LON_NRO_IDENTIFICACION + "s", aux).replace(" ", "0");

            //consecutivo
            //aux = obtenerValorCelda(row.getCell(6));
            linea += String.format("%-" + FONZ04_LON_SIN_USO + "s", "   ");

            //SEXO
            aux = obtenerValorCelda(row.getCell(7));
            validarSexo(aux);
            linea += String.format("%-" + FONZ04_LON_SEXO_BENEFICIARIO + "s", aux);

            //PRIMER NOMBRE BENEFICIARIO
            aux = obtenerValorCelda(row.getCell(8));
            validarPrimerNombreBeneficiario(aux, ibFideicomisoDetPj);
            linea += String.format("%-" + FONZ04_LON_1NOMBRE_BENEFICIARIO + "s", aux);

            //PRIMER APELLIDO BENEFICIO
            aux = obtenerValorCelda(row.getCell(9));
            validarPrimerApellidoBeneficiario(aux, ibFideicomisoDetPj);
            linea += String.format("%-" + FONZ04_LON_1APELLIDO_BENEFICIARIO + "s", aux);

            //SEGUNDO NOMBRE BENEFICIARIO
            aux = obtenerValorCelda(row.getCell(10));
            validarSegundoNombreBeneficiario(aux, ibFideicomisoDetPj);
            linea += String.format("%-" + FONZ04_LON_2NOMBRE_BENEFICIARIO + "s", aux);

            //SEGUNDO APELLIDO BENEFICIARIO
            aux = obtenerValorCelda(row.getCell(11));
            validarSegundoApellidoBeneficiario(aux, ibFideicomisoDetPj);
            linea += String.format("%-" + FONZ04_LON_2APELLIDO_BENEFICIARIO + "s", aux);

            //ESTADO CIVIL
            aux = obtenerValorCelda(row.getCell(12));
            validarEstadoCivil(aux);
            linea += String.format("%-" + FONZ04_LON_EDO_CIVIL_BENEFICIARIO + "s", aux);

            //APELLIDO DE CASADA
            aux = obtenerValorCelda(row.getCell(13));
            validarApellidoCasada(aux);
            linea += String.format("%-" + FONZ04_LON_APELLIDO_CASADA_BENEFICIARIO + "s", aux);

            //FECHA NACIMIENTO
            aux = obtenerValorCelda(row.getCell(14));
            validarFechaNacimientoBeneficiario(aux);
            linea += String.format("%-" + FONZ04_LON_FECHA_NAC_BENEFICIARIO + "s", aux);

            //LUGAR NACIMIENTO
            aux = obtenerValorCelda(row.getCell(15));
            validarLugarNacimiento(aux);
            linea += String.format("%-" + FONZ04_LON_LUGAR_NAC_BENEFICIARIO + "s", aux);

            //ESTAUS NACIONALIZACION
            aux = obtenerValorCelda(row.getCell(16));
            validarEstatusNacionalizacion(aux);
            linea += String.format("%-" + FONZ04_LON_ESTATUS_NACIONALIZACION + "s", aux);

            //PROFESION
            aux = obtenerValorCelda(row.getCell(17));
            validarProfesion(aux);
            linea += String.format("%-" + FONZ04_LON_PROFESION_BENEFICIARIO + "s", aux);

            //URBANIZACION
            aux = obtenerValorCelda(row.getCell(18));
            validarUrbanizacion(aux);
            linea += String.format("%-" + FONZ04_LON_URBANIZACION_BENEFICIARIO + "s", aux);

            //AVENIDA
            aux = obtenerValorCelda(row.getCell(19));
            validarAvenida(aux);
            linea += String.format("%-" + FONZ04_LON_CALLE_O_AVENIDA_BENEFICIARIO + "s", aux);

            //MANZANA
            aux = obtenerValorCelda(row.getCell(20));
            validarManzana(aux);
            linea += String.format("%-" + FONZ04_LON_MANZANA_O_PISO_BENEFICIARIO + "s", aux);

            //EDIFICIO
            aux = obtenerValorCelda(row.getCell(21));
            validarEdificio(aux);
            linea += String.format("%-" + FONZ04_LON_EDIFICIO_BENEFICIARIO + "s", aux);

            //CODIGO DE AREA
            aux = obtenerValorCelda(row.getCell(22));
            validarCodigoAreaTelf(aux);
            linea += String.format("%-" + FONZ04_LON_COD_AREA_TELEFONO_BENEFICIARIO + "s", aux);

            //TELEFONO
            aux = obtenerValorCelda(row.getCell(23));
            validarTelf(aux);
            linea += String.format("%-" + FONZ04_LON_TELEFONO_BENEFICIARIO + "s", aux);

            //FECHA INGRESO
            aux = obtenerValorCelda(row.getCell(24));
            validarFechaIngreso(aux);
            linea += String.format("%-" + FONZ04_LON_FECHA_INGRESO_BENEFICIARIO + "s", aux);

            //NUMERO FICHA
            aux = obtenerValorCelda(row.getCell(25));
            validarNumeroFicha(aux);
            linea += String.format("%-" + FONZ04_LON_NUMERO_FICHA + "s", aux);

            //UBICACION ADMINISTRATIVA
            aux = obtenerValorCelda(row.getCell(26));
            validarUbicacionAdministrativa(aux);
            linea += String.format("%-" + FONZ04_UBICACION_ADMINISTRATIVA + "s", aux);

            //CAPITALIZA INTERESES
            aux = obtenerValorCelda(row.getCell(27));
            validarCapitalizaIntereses(aux);
            linea += String.format("%-" + FONZ04_CAPITALIZA_INTERESES + "s", aux);

            //CARGO OCUPADO
            aux = obtenerValorCelda(row.getCell(28));
            validarCargoOcupado(aux);
            linea += String.format("%-" + FONZ04_CARGO_OCUPADO + "s", aux);

            //SUELDO
            aux = obtenerValorCelda(row.getCell(29));
            validarSueldo(aux);
            linea += String.format("%-" + FONZ04_SUELDO_BENEFICIARIO + "s", aux);

            //CENTRO DE COSTO
            aux = obtenerValorCelda(row.getCell(30));
            validarCentroCosto(aux);
            linea += String.format("%-" + FONZ04_CENTRO_COSTOS_BENEFICIARIO + "s", aux);

            //NRO. CUENTA
            aux = obtenerValorCelda(row.getCell(31));
            validarNroCuenta(aux, ibFideicomisoDetPj);
            linea += String.format("%" + FONZ04_LON_NUMERO_CUENTA + "s", aux).replace(" ", "0");

            //MONTO ABONAR
            aux = obtenerValorCelda(row.getCell(32));
            validarMontoAbonar(aux, ibFideicomisoDetPj);
            linea += String.format("%" + FONZ04_LON_MONTO + "s", aux).replace(" ", "0");

            //CODIGO MOVIMIENTO
            aux = obtenerValorCelda(row.getCell(33));
            validarCodigoMovimiento(aux, ibFideicomisoDetPj);
            linea += String.format("%-" + FONZ04_LON_TIPO_MOVIMIENTO + "s", aux);

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
