/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteRefComercialesUpdateDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;


/**
 *
 * @author audra.zapata
 */
@Named("FichaClienteRefComercialesUpdateDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FichaClienteRefComercialesUpdateDAOImpl extends DAOUtil implements FichaClienteRefComercialesUpdateDAO {
  private static final Logger logger = Logger.getLogger(FichaClienteRefComercialesUpdateDAOImpl.class.getName());
    
    
    @Override
    public UtilDTO actualizarRefComerciales(String iCodigoCliente) {
         
          RespuestaDTO respuesta = new RespuestaDTO();       
        UtilDTO utilDTO = new UtilDTO();       
        try {           
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("jdbc/temp");
            this.conn = ds.getConnection();
            //### ### colocar el nombre del procedimiento correcto
            CallableStatement cst = conn.prepareCall("{call FICHA_CLIENTE_TEMP.OBT_DATOS_COMERCIALES_CLI_IN (?)}");
            cst.setInt(1, Integer.parseInt(iCodigoCliente));      
            cst.execute();
            this.conn.close();
            utilDTO.setRespuesta(respuesta);
        } catch(Exception e) {
           logger.error( new StringBuilder("ERROR DAO EN validarClienteExcluido: ")
                    //.append("CTA-").append(codCliente)
                    //.append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            utilDTO.setRespuesta(respuesta);
        } finally {          
             this.cerrarConexion(null);
        }
        return utilDTO;
    }   
    
}
