/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteDireccionPrincipalDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.FichaClienteDireccionPrincipalDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.util.DAOUtil;
import java.sql.ResultSet;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 *
 * @author alejandro.flores
 */
@Named("FichaClienteDireccionPrincipalDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FichaClienteDireccionPrincipalDAOImpl extends DAOUtil implements FichaClienteDireccionPrincipalDAO{
    
    private static final Logger logger = Logger.getLogger(FichaClienteDireccionPrincipalDAOImpl.class.getName());

    @Override
    public FichaClienteDireccionPrincipalDTO consultarDatosDirPpalCliente(String iCodigoCliente) {
     FichaClienteDireccionPrincipalDTO fichaClienteDireccion = new FichaClienteDireccionPrincipalDTO();
     RespuestaDTO respuesta = new RespuestaDTO();     
     try {
           respuesta = this.conectarJNDI(JNDI_ORACLE_9, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("FICHA_CLIENTE", "OBT_DIRECCION_FICHA_CLIENTE", 3, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(iCodigoCliente)); 
            statement.setString(2, "S"); 
            statement.registerOutParameter(3, OracleTypes.CURSOR);            
            this.ejecuto = statement.execute();
            //String codSalida = this.statement.getBigDecimal(4).toString();

            /*if (codSalida != null || !codSalida.isEmpty() || !codSalida.equalsIgnoreCase("")) {
                respuesta = actuaciones.obtenerDescripcionSalidaSP(codSalida, codigoCanal);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) || !respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new DAOException();
                } else {*/

                    objResultSet = (ResultSet) statement.getObject(3);
                    while (objResultSet.next()) {
                       						
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
                                                fichaClienteDireccion.setTelex(objResultSet.getString("OTRO_TELEFONO"));
                                                fichaClienteDireccion.setEmail(objResultSet.getString("E_MAIL"));
                                                fichaClienteDireccion.setCodigoMunicipio(objResultSet.getBigDecimal("CODIGO_MUNICIPIO"));
                                                fichaClienteDireccion.setEs_de_trabajo(objResultSet.getString("ES_DE_TRABAJO"));
                                                fichaClienteDireccion.setCodigoDireccion(objResultSet.getBigDecimal("CODIGO_DIRECCION"));
                                                fichaClienteDireccion.setCodigoPostal(objResultSet.getString("CODIGO_POSTAL"));
                                                fichaClienteDireccion.setCiudadPN(objResultSet.getString("CIUDAD"));
                    }
            
            fichaClienteDireccion.setRespuesta(respuesta); 
           
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN buscarNombreCodEmpresaByRif: ")
                    //.append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            
        } finally {
            
            this.cerrarConexion(null);
        }
        return fichaClienteDireccion;
        
        }
}
