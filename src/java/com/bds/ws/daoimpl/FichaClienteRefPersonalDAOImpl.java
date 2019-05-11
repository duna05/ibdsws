/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteRefPersonalDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.FichaClienteRefPersonalDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.util.DAOUtil;
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
 * @author alejandro.flores
 */
@Named("FichaClienteRefPersonalDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FichaClienteRefPersonalDAOImpl extends DAOUtil implements FichaClienteRefPersonalDAO {

    private static final Logger logger = Logger.getLogger(FichaClienteRefPersonalDAOImpl.class.getName());

    @Override
    public FichaClienteRefPersonalDTO consultarRefPersonal(String iCodigoCliente) {
        FichaClienteRefPersonalDTO refClienteRefPersonal = null;
        FichaClienteRefPersonalDTO refClienteRefPersonalSalida = new FichaClienteRefPersonalDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        List<FichaClienteRefPersonalDTO> refClienteRefPersonalList = new ArrayList<FichaClienteRefPersonalDTO>();
    try {
           //respuesta = this.conectarJNDI(JNDI_ORACLE_9, null);
           respuesta = this.conectarJNDI(JNDI_ORACLE_TEMPORAL, null); 
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("FICHA_CLIENTE_TEST", "OBT_REF_PERSONALES_CLIENTE", 2, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }            
            
            statement.setInt(1, Integer.parseInt(iCodigoCliente));            
            statement.registerOutParameter(2, OracleTypes.CURSOR); 
            this.ejecuto = statement.execute();
            

                    objResultSet = (ResultSet) statement.getObject(2);
                    while (objResultSet.next()) {
                        refClienteRefPersonal = new FichaClienteRefPersonalDTO();
                                               refClienteRefPersonal.setCodigoTipoIdentificacion(objResultSet.getBigDecimal("CODIGO_TIPO_IDENTIFICACION"));
                                               refClienteRefPersonal.setNumeroIdentificacion(objResultSet.getString("NUMERO_IDENTIFICACION"));
                                               refClienteRefPersonal.setNombre(objResultSet.getString("NOMBRE"));
                                               refClienteRefPersonal.setPrimerApellido(objResultSet.getString("PRIMER_APELLIDO"));
                                               refClienteRefPersonal.setSegundoApellido(objResultSet.getString("SEGUNDO_APELLIDO"));
                                               refClienteRefPersonal.setParentesco(objResultSet.getString("DESCRIPCION"));                                               
                                               refClienteRefPersonal.setTelefono(objResultSet.getString("TELEFONO"));
                                               refClienteRefPersonal.setTelefono2(objResultSet.getString("TELEFONO2"));
                                                   			
                    refClienteRefPersonalList.add(refClienteRefPersonal);
                    }
                    refClienteRefPersonalSalida.setIbFichaClienteRefPersonalList(refClienteRefPersonalList);
              
                      
            refClienteRefPersonalSalida.setRespuesta(respuesta); 
           
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
        return refClienteRefPersonalSalida;
        
        }

}
