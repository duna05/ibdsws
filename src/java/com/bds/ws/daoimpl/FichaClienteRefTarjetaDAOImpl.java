/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteRefTarjetaDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.FichaClienteRefTarjetaDTO;
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
@Named("FichaClienteRefTarjetaDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FichaClienteRefTarjetaDAOImpl extends DAOUtil implements FichaClienteRefTarjetaDAO {

    private static final Logger logger = Logger.getLogger(FichaClienteRefTarjetaDAOImpl.class.getName());

    @Override
    public FichaClienteRefTarjetaDTO consultarRefTarjeta(String iCodigoCliente) {
        FichaClienteRefTarjetaDTO refClienteRefTarjeta = null;
        FichaClienteRefTarjetaDTO refClienteRefTarjetaSalida = new FichaClienteRefTarjetaDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        List<FichaClienteRefTarjetaDTO> refClienteRefTarjetaList = new ArrayList<FichaClienteRefTarjetaDTO>();
    try {
           respuesta = this.conectarJNDI(JNDI_ORACLE_9, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("FICHA_CLIENTE", "OBT_REF_TARJETAS_CLIENTE", 2, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }            
            
            statement.setInt(1, Integer.parseInt(iCodigoCliente));            
            statement.registerOutParameter(2, OracleTypes.CURSOR); 
            this.ejecuto = statement.execute();
            

                    objResultSet = (ResultSet) statement.getObject(2);
                    while (objResultSet.next()) {
                        refClienteRefTarjeta = new FichaClienteRefTarjetaDTO();
                                               refClienteRefTarjeta.setCodTipoTarjeta(objResultSet.getBigDecimal("CODIGO_TIPO_TDC"));
                                               refClienteRefTarjeta.setDesTipoTarjeta(objResultSet.getString("DESC_TIPO_TDC"));
                                               refClienteRefTarjeta.setCodBancoTarjeta(objResultSet.getBigDecimal("ID_BANCO"));
                                               refClienteRefTarjeta.setNomBancoTarjeta(objResultSet.getString("NOMBRE_BANCO"));
                                               refClienteRefTarjeta.setNumeroTarjeta(objResultSet.getBigDecimal("NUMERO_TARJETA"));
                                               refClienteRefTarjeta.setFechaEmision(objResultSet.getString("FECHA_EMISION")); 
                                               refClienteRefTarjeta.setLimiteTarjeta(objResultSet.getBigDecimal("LIMITE_CREDITO"));
                    refClienteRefTarjetaList.add(refClienteRefTarjeta);
                    }
                    refClienteRefTarjetaSalida.setIbFichaClienteRefTarjetaList(refClienteRefTarjetaList);
              
                      
            refClienteRefTarjetaSalida.setRespuesta(respuesta); 
           
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
        return refClienteRefTarjetaSalida;
        
        }

}
