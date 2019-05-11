/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteRefPersonalesUpdateDAO;
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
@Named("FichaClienteRefPersonalesUpdateDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FichaClienteRefPersonalesUpdateDAOImpl extends DAOUtil implements FichaClienteRefPersonalesUpdateDAO {
    private static final Logger logger = Logger.getLogger(FichaClienteRefPersonalesUpdateDAOImpl.class.getName());
    
    @Override
    public UtilDTO actualizarRefPersonales(String iCodigoCliente, String codTipoIdentif, String nroIdentif, String nombreIn, String primerApellido, String segundoApellido, String codReferenciaPersonal, String telefonoIn, String telefono2In) {
        RespuestaDTO respuesta = new RespuestaDTO();       
        UtilDTO utilDTO = new UtilDTO();       
        try {           
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("jdbc/temp");
            this.conn = ds.getConnection();
            CallableStatement cst = conn.prepareCall("{call FICHA_CLIENTE_TEMP.OBT_DATOS_PERSONALES_CLI_IN (?,?,?,?,?,?,?,?,?)}");
            cst.setInt(1, Integer.parseInt(iCodigoCliente));  
            cst.setString(2, codTipoIdentif);
            cst.setString(3, nroIdentif);
            cst.setString(4, nombreIn);
            cst.setString(5, primerApellido);
            cst.setString(6, segundoApellido);
            cst.setInt(7, Integer.parseInt(codReferenciaPersonal));
            cst.setString(8, telefonoIn);
            cst.setString(9, telefono2In);
            cst.execute();
            this.conn.close();
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO AL ACTUALIZAR LAS REFERENCIAS PERSONALES ")                   
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
