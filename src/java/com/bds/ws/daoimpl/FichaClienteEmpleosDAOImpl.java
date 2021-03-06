/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteEmpleosDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.FichaClienteEmpleosDTO;
import com.bds.ws.dto.RespuestaDTO;

import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
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
@Named("FichaClienteEmpleosDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FichaClienteEmpleosDAOImpl extends DAOUtil implements FichaClienteEmpleosDAO {
    
    private static final Logger logger = Logger.getLogger(FichaClienteEmpleosDAOImpl.class.getName());
    
    
    
    @Override
    public FichaClienteEmpleosDTO consultarEmpleos(String iCodigoCliente) {     
     FichaClienteEmpleosDTO empleos = null;
     FichaClienteEmpleosDTO empleosSalida = new FichaClienteEmpleosDTO();
     RespuestaDTO respuesta = new RespuestaDTO();     
     List<FichaClienteEmpleosDTO> fichaClienteEmpleosList = new ArrayList<FichaClienteEmpleosDTO>();
     try {
           //respuesta = this.conectarJNDI(JNDI_ORACLE_9, null);
           respuesta = this.conectarJNDI(JNDI_ORACLE_TEMPORAL, null); //solo para pruebas****************
           
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //respuesta = this.generarQuery("FICHA_CLIENTE", "OBT_EMPLEOS_CLIENTE", 2, null);
            respuesta = this.generarQuery("FICHA_CLIENTE_TEMP", "OBT_EMPLEOS_CLIENTE", 2, null); //solo para pruebas****************
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }            
            
            statement.setInt(1, Integer.parseInt(iCodigoCliente));            
            statement.registerOutParameter(2, OracleTypes.CURSOR); 
            this.ejecuto = statement.execute();
                    objResultSet = (ResultSet) statement.getObject(2);
                    while (objResultSet.next()) {
                       empleos = new FichaClienteEmpleosDTO();                        
                       empleos.setNombreEmpresa(objResultSet.getString("NOMBRE_EMPRESA"));
                       empleos.setCargo(objResultSet.getString("CARGO"));
                       empleos.setUltimoSueldo(objResultSet.getBigDecimal("VALOR_INGRESO"));
                       empleos.setFechaEntrada(objResultSet.getString("FECHA_ENTRADA"));
                       empleos.setRif(objResultSet.getString("RIF"));
                       empleos.setRamo(objResultSet.getString("RAMO"));
                       empleos.setDireccion(objResultSet.getString("DIRECCION"));
                       empleos.setTelefono(objResultSet.getString("TELEFONO"));
                       empleos.setSecuencia(objResultSet.getBigDecimal("SECUENCIA_EMPLEO"));
                       fichaClienteEmpleosList.add(empleos);
                    }
              
            empleosSalida.setIbEmpleoList(fichaClienteEmpleosList);
            empleosSalida.setRespuesta(respuesta); 
           
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN : ")                   
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            
        } finally {
            
            this.cerrarConexion(null);
        }
        return empleosSalida;
        
        }
}
