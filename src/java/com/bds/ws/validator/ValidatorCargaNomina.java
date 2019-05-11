/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.validator;

import com.bds.ws.daoimpl.IbCargaNominaPjDaoImpl;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaNominaPjDTO;
import com.bds.ws.exception.IbErroresCargaPjException;
import com.bds.ws.model.IbCargaNominaDetallePj;
import com.bds.ws.model.IbCargaNominaPj;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.model.IbUsuariosPj;
import com.bds.ws.util.BDSUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author roger.muñoz
 */
public class ValidatorCargaNomina extends Validator{

    private final String FORMATO_FECHA_PAGO = "dd/MM/yyyy HH:mm:ss";

    private final int LONGITUD_CABECERA = 158;
    private final int LONGITUD_DETALLE  =  73;
    
    private final int LONGITUD_CABECERA_CONVENIO        =   8;
    private final int LONGITUD_CABECERA_FECHA_CARGA     =  19;
    private final int LONGITUD_CABECERA_CUENTA_DEBITAR  =  12;
    private final int LONGITUD_CABECERA_MONTO_DEBITAR   =  16;
    private final int LONGITUD_CABECERA_REFERENCIA      =  15;
    private final int LONGITUD_CABECERA_MOTIVO_PAGO     =  80;
    private final int LONGITUD_CABECERA_TOTAL_REGISTROS =   6;
    private final int LONGITUD_CABECERA_CONFIDENCIAL    =   1;
    
    private final String COD_FORMA_PAGO                 =  "D";
    
    
    private final int LONGITUD_DETALLE_CODIGO_EMPRESA   =   4;
    private final int LONGITUD_DETALLE_CEDULA           =  10;
    private final int LONGITUD_CUENTA_ACREDITAR         =  10;
    private final int LONGITUD_MONTO_ACREDITAR          =  10;
    private final int LONGITUD_TIPO_TRANSACCION         =   1;
    private final int LONGITUD_CONVENIO                 =   8;
    
    private final String COD_TIPO_TRANSACCION           =  "C";

    
    public ValidatorCargaNomina(IbCargaArchivoDTO ibCargaArchivoDto) {
        super(ibCargaArchivoDto);
        this.longitudCabecera = LONGITUD_CABECERA;
        this.longitudDetalleFONZ03 = LONGITUD_DETALLE;        
    }
        
    /*
    	Por cada línea de la cabecera debe validarse:
        Para Número de convenio: solo números.
	Para el campo fecha de pago: debe estar conformado por la siguiente secuencia DD/MM/YYYY. Debe validarse que exista el separador / entre cada tipo de unidad de tiempo. La fecha debe ser posterior a la fecha actual. Adicional la hora debe estar en formato militar
	Para número cuenta del cliente: solo números.
	Para monto del pago: solo números. Los últimos 2 caracteres corresponden a decimales.
	Para forma de pago: solo debe aceptar el valor D.. . No agregar este campo en base de datos, pero se debe contemplar en la estructura del archivo..
	Para Referencia de pago: debe aceptarse solo valores alfabéticos sin caracteres especiales. Si lo trae se toma
	Para Motivo del pago: debe aceptarse solo valores alfabéticos sin caracteres especiales.
	Para total de registros: solo números. Debe coincidir con la cantidad de registros provistos en el archivo.
	Para confidencialidad de los pagos: solo permita el valor S o N. Solo 1 valor. Nos e tomara para validar
    */
    @Override
    public IbCargaNominaPjDTO leerCabecera(Map<String,Object> param) throws Exception {
        
        String linea = (String)param.get("linea");
        String aux = "";

        int pos=0;
        
        try{    
            IbCargaNominaPj ibCargaNominaPj = new IbCargaNominaPj();  
            
            ibCargaNominaPj.setCantidadCreditosAplicar(BigDecimal.ZERO);
            ibCargaNominaPj.setMontoTotalAplicar(BigDecimal.ZERO);            
                        
            //campos no contenidos en el archivo            
            ibCargaNominaPj.setCantidadCreditosRechazados(BigDecimal.ZERO);            
            ibCargaNominaPj.setCantidadCreditosAplicados(BigDecimal.ZERO);            
            ibCargaNominaPj.setCantidadAprobadoresServicio(BigDecimal.ONE);
            ibCargaNominaPj.setSecuenciaCumplida(BigDecimal.ZERO);
            ibCargaNominaPj.setFechaHoraCarga( new Date() );            
            ibCargaNominaPj.setEstatus(new IbEstatusPagosPj(BigDecimal.ZERO));
            ibCargaNominaPj.setTipoCargaNomina(BigDecimal.ONE);
            ibCargaNominaPj.setNombreArchivo(ibCargaArchivoDto.getFileName());
        

            if(linea.trim().length()!=LONGITUD_CABECERA){
                String err = mapErrorres.get(IbCargaNominaPjDaoImpl.ERR_KEY_LINEA_CABECERA_TAMANO);
                err= err.replace("$debe", String.valueOf(LONGITUD_CABECERA));
                err= err.replace("$posee", String.valueOf(linea.length()));                
                
                throw  new Exception(err);
            }   
            //Nro_Convenio
            aux=linea.substring(pos, pos+=LONGITUD_CABECERA_CONVENIO);

            if(!super.validaSoloNumeros(aux)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_NUMERO_CONVENIO_NUMEROS);
            }
            ibCargaNominaPj.setNroConvenio(new BigDecimal(aux));
            
            //fecha de carga
            //formato
            aux=linea.substring(pos, pos+=LONGITUD_CABECERA_FECHA_CARGA);
            
            if(!super.validaFecha(aux, FORMATO_FECHA_PAGO)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_FORMATO_FECHA_PAGO);
            }
            ibCargaNominaPj.setFechaHoraPago((new SimpleDateFormat(FORMATO_FECHA_PAGO)).parse(aux));                
            
            
            //posterior
            Date hoy       =  BDSUtil.formatearFechaDateADate(
                                new Date(), 
                                this.FORMATO_FECHA_PAGO);
            
            Date fechaPago =  BDSUtil.formatearFechaDateADate(
                                ibCargaNominaPj.getFechaHoraPago(), 
                                this.FORMATO_FECHA_PAGO);
            
            if(!hoy.before(fechaPago)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_FECHA_PAGO_FUTURA);
            }
            
            //Nro_Cuenta_Debitar            
            aux=linea.substring(pos, pos+=LONGITUD_CABECERA_CUENTA_DEBITAR);
            if(!super.validaSoloNumeros(aux)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_CUENTA_CLIENTE_NUMEROS);
            }
            ibCargaNominaPj.setNroCuentaDebito(aux);            

            
            //Monto
            aux=linea.substring(pos, pos+=LONGITUD_CABECERA_MONTO_DEBITAR);
            if(!super.validaSoloNumeros(aux)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_MONTO_NUMEROS);
            }
            ibCargaNominaPj.setMontoTotalAplicar( (new BigDecimal(aux)).divide(new BigDecimal("100")) );            

            //Forma_de_Pago                        
            aux=linea.substring(pos, pos+=1);
            if(!aux.equals(COD_FORMA_PAGO)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_FORMA_PAGO_D);
            }

            //Referencia_Pago            
            aux=linea.substring(pos, pos+=LONGITUD_CABECERA_REFERENCIA);          
            
            //Motivo_Pago            
            aux=linea.substring(pos, pos+=LONGITUD_CABECERA_MOTIVO_PAGO).trim();            
            if(!super.validaAlfaNumerico(aux)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_MOTIVO_PAGO);
            }
            ibCargaNominaPj.setMotivoDePago(aux);                        

            //Total Registros
            aux=linea.substring(pos, pos+=LONGITUD_CABECERA_TOTAL_REGISTROS);            
            if(!super.validaSoloNumeros(aux)){
                System.out.println("aux="+aux);
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_TOTAL_REGISTROS);
            }
            ibCargaNominaPj.setCantidadCreditosAplicar(new BigDecimal(aux));

            //Confidencial
            aux=linea.substring(pos, pos+=LONGITUD_CABECERA_CONFIDENCIAL);
            if(!super.validaSolaUnaDeEstasLetras(aux,"SN")){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_CONFIDENCIAL);
            } 
            
            ibCargaNominaPj.setIdPago(null);
            ibCargaNominaPj.setCodigoClienteAbanks(ibCargaArchivoDto.getCdClienteAbanks()); 
            ibCargaNominaPj.setCodigoUsuarioCarga( new IbUsuariosPj(ibCargaArchivoDto.getCodigoUsuario()));
            
            IbCargaNominaPjDTO  ibCargaNominaPjDTO = new IbCargaNominaPjDTO();
            ibCargaNominaPjDTO.setIbCargaNominaPj(ibCargaNominaPj);                        
            
            return ibCargaNominaPjDTO;
            
        }catch(java.lang.StringIndexOutOfBoundsException strEx){
            String err = mapErrorres.get(IbCargaNominaPjDaoImpl.ERR_KEY_LINEA_CABECERA_TAMANO);
            err= err.replaceAll("$debe", String.valueOf(linea.length()));
            err= err.replaceAll("$posee", String.valueOf(LONGITUD_CABECERA));                
            throw  new Exception("falla en el formato de la cabecera,  motivo= "+err);
        }catch(IbErroresCargaPjException epj){            
            throw new Exception("falla en el formato de la cabecera,  motivo= "+mapErrorres.get(epj.getMessage()));
        }catch(Exception e){
            throw new Exception("falla en el formato de la cabecera,  motivo= "+e.getMessage());            
        }
       
    }    
    
/*    			
CABECERA 
    
Campo                       Tipo Campo	Tamaño
------------------ ------------ ------  --------------------------------------------------------  
Nro_Convenio                Numérico	 8
Fecha_Pago / Hora de Pago   Date	19
Nro_Cuenta_Debitar          Numérico	12
Monto                       Numérico    18 (16,2)
Forma_de_Pago               Caracter	 1
Referencia_Pago             Numérico	15
Motivo_Pago                 Caracter	80
Cant_Registros              Numérico	 6
Confidencial                Caracter	 1
        
DETALLE	
			
Campo               Tipo Campo	Tamaño	Descripción
------------------ ------------ ------  --------------------------------------------------------  
Código Empresa      Numérico         4	Código asignado a la empresa por el banco.
Número Cédula       Caracter        10	Número de cédula del titular de la cuenta
Número Cuenta       Numérico        10	Número de cuenta del beneficiario
Monto               Numérico        10	8 Enteros y 2 decimales
Tipo Transacción    Caracter         1	Valor: C->Crédito 
Codigo Convenio     Numérico         8	Número del convenio asignado por el banco
Nombre              Caracter        30	Nombre del titular de la cuenta.
------------------------------------------------------------------------------------------------    			
    Nota :			
    La información en los campos carácter se debe completar con espacios en blanco a la izquierda			
    La información en los campos numéricos se debe completar con ceros a la izquierda			
------------------------------------------------------------------------------------------------    

    Ejemplo:
    **************************************************************************************************************************************************************
    0001001701/03/2017 08:00:000038380001010000000081862817D000000000000000                                                                  PAGO DE NOMINA000002N
    0291001616294900380355490044954959C00010017            RIVAS BAENA JHONNY
    0291001552255010384155580036907858C00010017MOROCOIMA MONTANO EDUARDO DAMI
    **************************************************************************************************************************************************************/            

    /***
     * Lee las lineas 2..N correspondientes a los detalles
     * @param linea
     * @return
     * @throws IbErroresCargaPjException
     * @throws Exception 
     */
    @Override
    public Object leerDetalle(Map<String, Object> param ) throws Exception {
        IbCargaNominaDetallePj ibCargaNominaDetallePj = new IbCargaNominaDetallePj();  

        String aux = "";
        int pos=0;
        linea=(String)param.get("linea");
        nroLinea=(Integer)param.get("nroLinea");
        
        try{    
            
            if(linea.trim().length()!= LONGITUD_DETALLE){
                String err = mapErrorres.get(IbCargaNominaPjDaoImpl.ERR_KEY_LINEA_DETALLE_TAMANO);
                err = err.replace("$debe", String.valueOf(LONGITUD_DETALLE));
                err = err.replace("$posee", String.valueOf(linea.trim().length()));
                throw new Exception(err);
            }   
                        
            //Código Empresa            
            aux=linea.substring(pos, pos+=LONGITUD_DETALLE_CODIGO_EMPRESA);
            if(!super.validaSoloNumeros(aux)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_CODIGO_EMPRESA);
            }
            
            //Número Cédula            
            aux=linea.substring(pos, pos+=LONGITUD_DETALLE_CEDULA);
            if(!super.validaSoloNumeros(aux)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_CEDULA_SOLO_NUMERO);
            }
            ibCargaNominaDetallePj.setNroIdentificacionCliente(aux);
            
            //Número Cuenta acreditar            
            aux=linea.substring(pos, pos+=LONGITUD_CUENTA_ACREDITAR);
            if(!super.validaSoloNumeros(aux)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_CUENTA_SOLO_NUMERO);
            }
            ibCargaNominaDetallePj.setNroCuentaBeneficiario(aux);
            
            //Monto            
            aux=linea.substring(pos, pos+=LONGITUD_MONTO_ACREDITAR);
            if(!super.validaSoloNumeros(aux)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_MONTO_SOLO_NUMERO);
            }
            ibCargaNominaDetallePj.setMontoPago( (new BigDecimal(aux)).divide(new BigDecimal("100")) );

            //Tipo Transacción            
            aux=linea.substring(pos, pos+=LONGITUD_TIPO_TRANSACCION);
            if(!super.validaSolaUnaDeEstasLetras(aux,COD_TIPO_TRANSACCION)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_TRANSACCION_C);
            }
            
            //Codigo Convenio            
            aux=linea.substring(pos, pos+=LONGITUD_CONVENIO);
            
            //nombre_beneficiario
            aux=linea.substring(pos).trim();
            if(!super.validaAlfaNumerico(aux)){
                throw new IbErroresCargaPjException(IbCargaNominaPjDaoImpl.ERR_KEY_BENEFICIARIO_ALFANUMERICO);
            }

            ibCargaNominaDetallePj.setNombreBeneficiario(aux);
                        
            ibCargaNominaDetallePj.setNroLineaArchivo(new BigDecimal(nroLinea));
            ibCargaNominaDetallePj.setCodigoClienteAbanks(this.ibCargaArchivoDto.getCdClienteAbanks());
            ibCargaNominaDetallePj.setFechaHoraCarga((Date)param.get("fechaHoraCarga"));
            ibCargaNominaDetallePj.setEstatus(new IbEstatusPagosPj(BigDecimal.ZERO));
            ibCargaNominaDetallePj.setIdPagoDetalle(BigDecimal.ZERO);
            
            return ibCargaNominaDetallePj;
            
        }catch(java.lang.StringIndexOutOfBoundsException strEx){
            throw new Exception("Linea de cabecera muy corta, tamaño="+linea.length());
        }catch(IbErroresCargaPjException epj){            
            throw new Exception("falla en el formato linea "+nroLinea+",  motivo= "+mapErrorres.get(epj.getMessage()));
        }catch(Exception e){
            throw  new Exception("falla en el formato, linea ("+nroLinea+"),  "+e.getMessage());
        }
    }    

    @Override
    public Object leerDetalle(Row row, Integer nroLinea) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
