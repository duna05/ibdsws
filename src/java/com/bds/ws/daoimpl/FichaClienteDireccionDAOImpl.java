/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteDireccionDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.FichaClienteDireccionDTO;
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
@Named("FichaClienteDireccionDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FichaClienteDireccionDAOImpl extends DAOUtil implements FichaClienteDireccionDAO {
    
     private static final Logger logger = Logger.getLogger(FichaClienteDireccionDAOImpl.class.getName());
     
    @Override
    public FichaClienteDireccionDTO consultarDatosDireccionCliente(String iCodigoCliente) {
        FichaClienteDireccionDTO fichaClienteDireccion = null;
        FichaClienteDireccionDTO fichaClienteSalida = new FichaClienteDireccionDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        List<FichaClienteDireccionDTO> fichaClienteDireccionList = new ArrayList<FichaClienteDireccionDTO>();
        try {
           respuesta = this.conectarJNDI(JNDI_ORACLE_9, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("FICHA_CLIENTE", "OBT_DIRECCION_FICHA_CLIENTE", 2, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(iCodigoCliente));            
            statement.registerOutParameter(2, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();
            objResultSet = (ResultSet) statement.getObject(2);
                    while (objResultSet.next()) {     
                     fichaClienteDireccion = new FichaClienteDireccionDTO();
                                                
                                                fichaClienteDireccion.setCodigoTipoDireccion(objResultSet.getBigDecimal("COD_TIPO_DIRECCION"));
                                                fichaClienteDireccion.setDescDireccion(objResultSet.getString("TIPO_DIRECCION"));                                                
                                                fichaClienteDireccion.setCodigoDepartamento(objResultSet.getBigDecimal("COD_ESTADO"));
                                                fichaClienteDireccion.setNombreDepartamento(objResultSet.getString("ESTADO"));
                                                fichaClienteDireccion.setCodigoSector(objResultSet.getBigDecimal("COD_SECTOR"));
                                                fichaClienteDireccion.setDescSector(objResultSet.getString("SECTOR"));
                                                fichaClienteDireccion.setUrbanizacion(objResultSet.getString("URBANIZACION"));
                                                fichaClienteDireccion.setCalle(objResultSet.getString("CALLE"));
                                                fichaClienteDireccion.setEdificio(objResultSet.getString("EDIFICIO_RESIDENCIA"));
                                                fichaClienteDireccion.setManzana(objResultSet.getString("MANZANA"));
                                                fichaClienteDireccion.setNroApartamento(objResultSet.getString("LOCAL"));
                                                fichaClienteDireccion.setTenencia(objResultSet.getString("TENENCIA"));
                                                fichaClienteDireccion.setZona(objResultSet.getBigDecimal("ZONA"));
                                                fichaClienteDireccion.setTelefonos(objResultSet.getString("TELEFONO"));
                                                 fichaClienteDireccion.setOtroTelefono(objResultSet.getBigDecimal("OTRO_TELEFONO"));
                                                //fichaClienteDireccion.setTelex(objResultSet.getString("OTRO_TELEFONO"));
                                                fichaClienteDireccion.setCodigoTenencia(objResultSet.getBigDecimal("CODIGO_TENENCIA"));
                                                fichaClienteDireccion.setEmail(objResultSet.getString("E_MAIL"));
                                                fichaClienteDireccion.setCiudad(objResultSet.getString("CIUDAD"));
                                                 fichaClienteDireccionList.add(fichaClienteDireccion);
                    }
                    
            fichaClienteSalida.setIbFichaClienteDireccionList(fichaClienteDireccionList);
            fichaClienteSalida.setRespuesta(respuesta); 
            
            
            
            
            
            
         } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN consultadireccioncliente: ")
                    //.append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            
        } finally {
            
            this.cerrarConexion(null);
        }
        return fichaClienteSalida;
        
        }
}
