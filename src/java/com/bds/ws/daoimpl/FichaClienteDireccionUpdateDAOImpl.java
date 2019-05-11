/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteDireccionUpdateDAO;
import com.bds.ws.dao.NotificationServiceType;

import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;


/**
 *
 * @author Accusys Technology
 */
@Named("FichaClienteDireccionUpdateDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FichaClienteDireccionUpdateDAOImpl extends DAOUtil implements FichaClienteDireccionUpdateDAO{
    
    private static final Logger logger = Logger.getLogger(FichaClienteDireccionUpdateDAOImpl.class.getName());

    @Override
    public void insertUpdateFichaClienteDireccion(String iCodigoCliente, String tipoVivienda) {
        
        this.conectarJNDI(JNDI_ORACLE_9, null);
        try{
           
            this.generarQuery("FICHA_CLIENTE", "OBT_DIR_FICHA_CLI_IN", 2, null);
            
            statement.setInt(1, Integer.parseInt(iCodigoCliente));  
            statement.setString(2, tipoVivienda);
 
            this.ejecuto = statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FichaClienteDireccionUpdateDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            
            this.cerrarConexion(null);
        }
    }
    
    
    
}
