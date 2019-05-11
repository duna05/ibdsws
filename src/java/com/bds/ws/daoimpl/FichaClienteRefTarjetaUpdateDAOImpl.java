/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteRefTarjetaUpdateDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.sql.CallableStatement;
import java.util.Date;
import org.apache.log4j.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author audra.zapata
 */
@Named("FichaClienteRefTarjetaUpdateDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FichaClienteRefTarjetaUpdateDAOImpl  extends DAOUtil implements FichaClienteRefTarjetaUpdateDAO{
private static final Logger logger = Logger.getLogger(FichaClienteRefTarjetaUpdateDAOImpl.class.getName());
    @Override
    public UtilDTO actualizarRefTarjeta(String iCodigoCliente) {
         RespuestaDTO respuesta = new RespuestaDTO();       
        UtilDTO utilDTO = new UtilDTO();       
        try {           
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("jdbc/temp");
            this.conn = ds.getConnection();
            CallableStatement cst = conn.prepareCall("{call FICHA_CLIENTE_TEMP.OBT_DATOS_TARJETAS_CLI_IN (?)}");
            cst.setInt(1, Integer.parseInt(iCodigoCliente));           
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
