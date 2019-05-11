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
public class ValidatorCargaFideicomisoFonz04Txt extends ValidatorCargaFideicomisoFonz04 {
    
    public ValidatorCargaFideicomisoFonz04Txt(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
        this.longitudDetalleFONZ04 = LONGITUD;
    }
        
    /***
     * Lee las lineas 1..N 
     * @return
     * @throws IbErroresCargaPjException
     * @throws Exception 
     */
    @Override
    public IbFideicomisoDetPj leerDetalle(Map<String, Object> param ) throws Exception {
        IbFideicomisoDetPj ibFideicomisoDetPj =  new IbFideicomisoDetPj();
        String aux = "";
        pos=0;
        
        linea=((String)param.get("linea")).trim();
        nroLinea=(Integer)param.get("nroLinea");
        
        this.setExtension(  (String)param.get("extension") ) ;
        //long codigoClienteAbanks = (Long)param.get("codigoClienteAbanks");

        //Código Empresa
        aux=extract(this.FONZ04_LON_CODIGO_EMPRESA);
        validarCodigoEmpresa(aux);

        //Código Plan
        aux=extract(this.FONZ04_LON_CODIGO_PLAN);
        validarCodigoPlan(aux);

        //Tipo Plan
        aux=extract(this.FONZ04_LON_TIPO_PLAN);
        validarTipoPlan(aux);
        
        //Tipo Plan SAFE
        aux=extract(this.FONZ04_LON_TIPO_PLAN_SAFE);
        validarTipoPlanSAFE(aux);

        //Tipo de Nacionalidad
        aux=extract(this.FONZ04_LON_TIPO_NACIONALIDAD);
        validarTipoNacionalidad(aux, ibFideicomisoDetPj);

        //Nro Identificacion
        aux=extract(this.FONZ04_LON_NRO_IDENTIFICACION);
        validarNroIdentificacion(aux, ibFideicomisoDetPj);

        //consecutivo
        aux=extract(this.FONZ04_LON_SIN_USO);

        //Sexo
        aux=extract(this.FONZ04_LON_SEXO_BENEFICIARIO);
        validarSexo(aux);
        

        //Primer Nombre Beneficiario
        aux=extract(this.FONZ04_LON_1NOMBRE_BENEFICIARIO).trim();
        validarPrimerNombreBeneficiario(aux, ibFideicomisoDetPj);

        //Primer Apellido Beneficiario
        aux=extract(this.FONZ04_LON_1APELLIDO_BENEFICIARIO).trim();
        validarPrimerApellidoBeneficiario(aux, ibFideicomisoDetPj);

        //Segundo Nombre Beneficiario
        aux=extract(this.FONZ04_LON_2NOMBRE_BENEFICIARIO).trim();
        validarSegundoNombreBeneficiario(aux, ibFideicomisoDetPj);

        //Segundo Apellido Beneficiario
        aux=extract(this.FONZ04_LON_2APELLIDO_BENEFICIARIO).trim();
        validarSegundoApellidoBeneficiario(aux, ibFideicomisoDetPj);
        
        //Estado Civil
        aux=extract(this.FONZ04_LON_EDO_CIVIL_BENEFICIARIO);
        validarEstadoCivil(aux);

        //Apellido Casada Beneficiario
        aux=extract(this.FONZ04_LON_APELLIDO_CASADA_BENEFICIARIO).trim();
        validarApellidoCasada(aux);
                
        //Fecha Nacimiento Beneficiario
        aux=extract(this.FONZ04_LON_FECHA_NAC_BENEFICIARIO);        
        validarFechaNacimientoBeneficiario(aux);

        //Lugar de Nacimiento Beneficiario
        aux=extract(this.FONZ04_LON_LUGAR_NAC_BENEFICIARIO).trim();
        validarLugarNacimiento(aux);

        //Estatus Nacionalizacion
        aux=extract(this.FONZ04_LON_ESTATUS_NACIONALIZACION);
        validarEstatusNacionalizacion(aux);

        //Profesion Beneficiario
        aux=extract(this.FONZ04_LON_PROFESION_BENEFICIARIO);
        validarProfesion(aux);
        
        //Urbanizacion Beneficiario
        aux=extract(this.FONZ04_LON_URBANIZACION_BENEFICIARIO);
        validarUrbanizacion(aux);

       //Calle o Avenida
        aux=extract(this.FONZ04_LON_CALLE_O_AVENIDA_BENEFICIARIO);
        validarAvenida(aux);

       //Manzana o Piso
        aux=extract(this.FONZ04_LON_MANZANA_O_PISO_BENEFICIARIO);
        validarManzana(aux);

       //edificio del beneficiario
        aux=extract(this.FONZ04_LON_EDIFICIO_BENEFICIARIO);
        validarEdificio(aux);
               
        //código de área del teléfono del beneficiario
        aux=extract(this.FONZ04_LON_COD_AREA_TELEFONO_BENEFICIARIO);
        validarCodigoAreaTelf(aux);
        
        //teléfono del beneficiario
        aux=extract(this.FONZ04_LON_TELEFONO_BENEFICIARIO);
        validarTelf(aux);

        //Fecha de ingreso del beneficiario
        aux=extract(this.FONZ04_LON_FECHA_INGRESO_BENEFICIARIO);
        validarFechaIngreso(aux);
        
        //campo número de ficha
        aux=extract(this.FONZ04_LON_NUMERO_FICHA);
        validarNumeroFicha(aux);
         
        //ubicación administrativa
        aux=extract(this.FONZ04_UBICACION_ADMINISTRATIVA);
        validarUbicacionAdministrativa(aux);

        //capitaliza intereses
        aux=extract(this.FONZ04_CAPITALIZA_INTERESES);
        validarCapitalizaIntereses(aux);
        
        //cargo ocupado
        aux=extract(this.FONZ04_CARGO_OCUPADO);
        validarCargoOcupado(aux);

        //sueldo del beneficiario
        aux=extract(this.FONZ04_SUELDO_BENEFICIARIO);
        validarSueldo(aux);
        
        //centro de costo del beneficiario
        aux=extract(this.FONZ04_CENTRO_COSTOS_BENEFICIARIO);
        validarCentroCosto(aux);
                            
        //Nro Cuenta
        aux=extract(this.FONZ04_LON_NUMERO_CUENTA);
        validarNroCuenta(aux, ibFideicomisoDetPj);
        
        //Monto abonar
        aux=extract(this.FONZ04_LON_MONTO);
        validarMontoAbonar(aux, ibFideicomisoDetPj);

        //Codigo MOVIMIENTO
        aux=extract(this.FONZ04_LON_TIPO_MOVIMIENTO);
        validarCodigoMovimiento(aux, ibFideicomisoDetPj);

        //usuario
        ibFideicomisoDetPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaArchivoDto.getCodigoUsuario()));

        //Estatus
        ibFideicomisoDetPj.setEstatus(new IbEstatusPagosPj(BigDecimal.ZERO));

        //FechaHoraCarga
        ibFideicomisoDetPj.setFechaHoraCarga(new Date());
        
        //linea 
        ibFideicomisoDetPj.setLineaTxtXls(linea.trim());                
        
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
