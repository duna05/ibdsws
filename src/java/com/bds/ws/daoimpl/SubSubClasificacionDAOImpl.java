/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;


import com.bds.ws.dao.ClasificacionDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.NotificationServiceType.ServiceType;
import com.bds.ws.dao.SubClasificacionDAO;
import com.bds.ws.dao.SubSubClasificacionDAO;
import com.bds.ws.dto.ClasificacionDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.SubClasificacionDTO;
import com.bds.ws.dto.SubSubClasificacionDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;
import java.util.ArrayList;

/**
 *
 * @author humberto.rojas
 */
@Named("SubSubClasificacionDAO")
@Stateless
@NotificationServiceType(ServiceType.SERVICES)
public class SubSubClasificacionDAOImpl extends DAOUtil implements SubSubClasificacionDAO {
    
     private static final Logger logger = Logger.getLogger(SubSubClasificacionDAOImpl.class.getName());

    @Override
    public SubSubClasificacionDTO consultarSubSubClasificacion() {     
     SubSubClasificacionDTO subsubclasificacion = null;
     SubSubClasificacionDTO subsubclasificacionSalida = new SubSubClasificacionDTO();
     RespuestaDTO respuesta = new RespuestaDTO();     
     List<SubSubClasificacionDTO> subsubclasificacionList = new ArrayList<SubSubClasificacionDTO>();
     try {
           respuesta = this.conectarJNDI(JNDI_ORACLE_9, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("CATALOGOS_FICHA_CLIENTE", "SUBSUBCLASIFICACION", 1, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }            
            statement.registerOutParameter(1, OracleTypes.CURSOR);            
            this.ejecuto = statement.execute();
            

                    objResultSet = (ResultSet) statement.getObject(1);
                    while (objResultSet.next()) {
                        subsubclasificacion = new SubSubClasificacionDTO();
                                               subsubclasificacion.setCodigoSubSubClasificacion(objResultSet.getBigDecimal("CODIGO_SUB_SUB_CLASIFICACION"));
                       				subsubclasificacion.setDescripcion(objResultSet.getString("DESCRIPCION"));						
                    subsubclasificacionList.add(subsubclasificacion);
                    }
              
            subsubclasificacionSalida.setIbSubSubClasificacionList(subsubclasificacionList);            
            subsubclasificacionSalida.setRespuesta(respuesta); 
           
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN : ")
                    //.append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            
        } finally {
            
            this.cerrarConexion(null);
        }
        return subsubclasificacionSalida;
        
        }
}
