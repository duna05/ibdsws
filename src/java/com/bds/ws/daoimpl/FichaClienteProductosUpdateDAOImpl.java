
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteProductosUpdateDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.util.DAOUtil;
import java.sql.CallableStatement;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author audra.zapata
 */
@Named("FichaClienteProductosUpdateDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FichaClienteProductosUpdateDAOImpl extends DAOUtil implements FichaClienteProductosUpdateDAO {
     private static final Logger logger = Logger.getLogger(FichaClienteProductosUpdateDAOImpl.class.getName());
    
     
    @Override
    public UtilDTO actualizarClienteProductos(String iCodigoCliente, String motivoSolicitud) {
        RespuestaDTO respuesta = new RespuestaDTO();       
        UtilDTO utilDTO = new UtilDTO();       
        try {           
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("jdbc/temp");
            this.conn = ds.getConnection();
            CallableStatement cst = conn.prepareCall("{call FICHA_CLIENTE_TEMP.OBT_DATOS_PRODUCTOS_CLI_IN (?,?)}");
            cst.setInt(1, Integer.parseInt(iCodigoCliente));
            cst.setString(2, motivoSolicitud);
            cst.execute();
            this.conn.close();
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarClienteExcluido: ")                   
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
