/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbEstatusPagosPjDAO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbEstatusPagosPjFacade;
import com.bds.ws.util.BDSUtil;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.vasquez
 */
@Named("IbEstatusPagosPjDAO")
@Stateless
public class IbEstatusPagosPjDaoImpl extends BDSUtil implements IbEstatusPagosPjDAO{

     /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbEstatusPagosPjDaoImpl.class.getName());
            
            
    @EJB
    private IbEstatusPagosPjFacade ibEstatusPagosPjFacade;
    
    @Override
    public UtilDTO insertarIbEstatusPagosPj(IbEstatusPagosPjDTO ibEstatusPagosPj) {
       return new UtilDTO();
    }

    @Override
    public UtilDTO insertarIbEstatusPagosPj(String nombre, String descripcion) {
        return new UtilDTO();
    }

    @Override
    public UtilDTO modificarIbEstatusPagosPj(IbEstatusPagosPjDTO ibEstatusPagosPj) {
        return new UtilDTO();
    }

    @Override
    public UtilDTO consultarIbEstatusPagosPjById(BigDecimal idEstatus) {
        
        
        return new UtilDTO();
    }
    
}
