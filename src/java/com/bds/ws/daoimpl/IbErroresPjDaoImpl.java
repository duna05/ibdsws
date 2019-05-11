/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbErroresPjDAO;
import com.bds.ws.dto.IbErroresPjDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbErroresPjFacade;
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
@Named("IbErroresPjDAO")
@Stateless
public class IbErroresPjDaoImpl extends BDSUtil implements IbErroresPjDAO{

     /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbErroresPjDaoImpl.class.getName());
    
    
    @EJB
    private IbErroresPjFacade ibErroresPjFacade;
    
    @Override
    public UtilDTO insertarIbErroresCargaPj(IbErroresPjDTO ibErroresCargaPjDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UtilDTO consultarIbErroresCargaPjById(BigDecimal idError) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
