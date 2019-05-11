/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.NotificationServiceType.ServiceType;
import com.bds.ws.dao.SubtipoClienteDAO;
import com.bds.ws.dto.FichaClienteDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.SubtipoClienteDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;
import java.util.ArrayList;

/**
 *
 * @author humberto.rojas
 */
@Named("SubtipoClienteDAO")
@Stateless
@NotificationServiceType(ServiceType.SERVICES)
public class SubtipoClienteDAOImpl extends DAOUtil implements SubtipoClienteDAO {
    
     private static final Logger logger = Logger.getLogger(FichaClienteDAOImpl.class.getName());

    @Override
    public SubtipoClienteDTO consultarSubtipoCliente() {
     //SubtipoClienteDTO subtipocliente = new SubtipoClienteDTO();
     SubtipoClienteDTO subtipocliente = null;
     SubtipoClienteDTO subtipodetalleSalida = new SubtipoClienteDTO();
     RespuestaDTO respuesta = new RespuestaDTO();     
     List<SubtipoClienteDTO> subtipoclienteList = new ArrayList<SubtipoClienteDTO>();
     try {
           respuesta = this.conectarJNDI(JNDI_ORACLE_9, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("CATALOGOS_FICHA_CLIENTE", "SUBTIPO_CLIENTE", 1, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            //statement.setInt(1, Integer.parseInt(iCodigoCliente));            
            statement.registerOutParameter(1, OracleTypes.CURSOR);            
            this.ejecuto = statement.execute();
            //String codSalida = this.statement.getBigDecimal(4).toString();

            /*if (codSalida != null || !codSalida.isEmpty() || !codSalida.equalsIgnoreCase("")) {
                respuesta = actuaciones.obtenerDescripcionSalidaSP(codSalida, codigoCanal);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) || !respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new DAOException();
                } else {*/

                    objResultSet = (ResultSet) statement.getObject(1);
                    while (objResultSet.next()) {
                        subtipocliente = new SubtipoClienteDTO();
                                               subtipocliente.setCodigoSubtipo(objResultSet.getBigDecimal("CODIGO_SUBTIPO"));
                       				subtipocliente.setDescripcion(objResultSet.getString("DESCRIPCION"));						
                    subtipoclienteList.add(subtipocliente);
                    }
                /*}
            }*/
            subtipodetalleSalida.setIbSubtipoClienteJuridicoList(subtipoclienteList);    
            //subtipocliente.setIbSubtipoClienteJuridicoList(subtipoclienteList);
            subtipodetalleSalida.setRespuesta(respuesta); 
           
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
        return subtipodetalleSalida;
        
        }
}
