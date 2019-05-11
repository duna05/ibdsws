/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbErroresNominaPjDAO;
import com.bds.ws.dto.IbErroresNominaPjDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbErroresNominaPjFacade;
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
@Named("IbErroresNominaPjDAO")
@Stateless
public class IbErroresNominaPjDaoImpl extends BDSUtil implements IbErroresNominaPjDAO{
    
     /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbErroresNominaPjDaoImpl.class.getName());
    
    
    @EJB
    private IbErroresNominaPjFacade ibErroresNominaPjFacade;

    @Override
    public UtilDTO insertarIbErroresNominaPj(IbErroresNominaPjDTO ibErroresNominaPjDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UtilDTO consultarIbErroresNominaPjById(BigDecimal idError) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UtilDTO consultarIbErroresNominaPjByPago(BigDecimal idPago) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
