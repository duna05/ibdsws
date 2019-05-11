/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.validator;

import com.bds.ws.dao.FideicomisoPjDAO;
import com.bds.ws.daoimpl.FideicomisoPjDAOImpl;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.enumerated.FideicomisoEnum;
import com.bds.ws.exception.IbErroresCargaPjException;
import com.bds.ws.model.IbFideicomisoDetPj;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author roger.muñoz
 */
public class ValidatorCargaFideicomiso extends Validator{
    public final static String ERR_KEY_PLAN_NO_PERTENECE_EMPRESA_03 = "pjw.fideicomisoCargaMasiva.error.plan.no.pertenece.empresa";
    public final static String ERR_KEY_PLAN_NO_PERTENECE_EMPRESA_04 = "pjw.fideicomisoCargaMasiva4.error.plan.no.pertenece.empresa";
    public final static String ERR_KEY_PLAN_ESTAUS_INACTIVO_03 = "pjw.fideicomisoCargaMasiva.error.estatus_plan_inactivo";
    public final static String ERR_KEY_PLAN_ESTAUS_INACTIVO_04 = "pjw.fideicomisoCargaMasiva4.error.estatus_plan_inactivo";
    public final static String ERR_KEY_CODIGO_PLAN = "pjw.fideicomisoCargaMasiva.error.codigo_plan";
    
    private FideicomisoPjDAO fideicomisoPjDAO = new FideicomisoPjDAOImpl();
    
    public ValidatorCargaFideicomiso(IbCargaArchivoDTO ibCargaArchivoDto) {        
        super(ibCargaArchivoDto);        
        pos=0;
    }
        
    /***
     * Lee las lineas 1..N 
     * @param linea
     * @return
     * @throws IbErroresCargaPjException
     * @throws Exception 
     */
    @Override
    public IbFideicomisoDetPj leerDetalle(Map<String, Object> param ) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }  
    
    @Override
    public IbFideicomisoDetPj leerDetalle(Row row, Integer nroLinea) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object leerCabecera(Map<String, Object> param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    protected void validarCodigoPlan(String aux) {
        if (!super.validaSoloNumeros(aux)) {
            addError(linea, nroLinea, getMapErrorres().get(ERR_KEY_CODIGO_PLAN));
        }else {
            //SI EL CODIGO DE PLAN NO PERTENE A LA EMPRESA EN VSUAF
            if(!fideicomisoPjDAO.validarCodigoPlanEmpresa(ibCargaArchivoDto.getRifEmpresa(), aux, ibCargaArchivoDto.getIdCanal(), ibCargaArchivoDto.getCodigoCanal())){                
                //FONZ03
                if(ibCargaArchivoDto.getEstructura().equals(FideicomisoEnum.MOVIMIENTO.getDescripcion())){
                    addError(linea, nroLinea, ERR_KEY_PLAN_NO_PERTENECE_EMPRESA_03, (new Parametro()).add("$plan", aux));
                //FONZ04
                }else {
                    addError(linea, nroLinea, ERR_KEY_PLAN_NO_PERTENECE_EMPRESA_04, (new Parametro()).add("$plan", aux));
                }
            }
            
            //SI EL CODIGO DE PLAN NO ESTA ACTIVO EN VSUAF
            if (!fideicomisoPjDAO.validarEstatusPlan(aux, ibCargaArchivoDto.getIdCanal(), ibCargaArchivoDto.getCodigoCanal())) {
                //FONZ03
                if (ibCargaArchivoDto.getEstructura().equals(FideicomisoEnum.MOVIMIENTO.getDescripcion())) {
                    addError(linea, nroLinea, ERR_KEY_PLAN_ESTAUS_INACTIVO_03, (new Parametro()).add("$plan", aux));
                //FONZ04
                } else {
                    addError(linea, nroLinea, ERR_KEY_PLAN_ESTAUS_INACTIVO_04, (new Parametro()).add("$plan", aux));
                }
            }
        }
    }
}
