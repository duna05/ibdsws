/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;



import com.bds.ws.dao.FichaClienteRefBancariasDAO;
import com.bds.ws.dao.FichaClienteRefComercialesDAO;

import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.NotificationServiceType.ServiceType;
import com.bds.ws.dto.FichaClienteRefBancariasDTO;




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
@Named("FichaClienteRefBancariasDAO")
@Stateless
@NotificationServiceType(ServiceType.SERVICES)
public class FichaClienteRefBancariasDAOImpl extends DAOUtil implements FichaClienteRefBancariasDAO {
    
     private static final Logger logger = Logger.getLogger(EstadoDAOImpl.class.getName());

    @Override
    public FichaClienteRefBancariasDTO consultarRefBancarias(String iCodigoCliente) {     
     FichaClienteRefBancariasDTO refBancarias = null;
     FichaClienteRefBancariasDTO refBancariasSalida = new FichaClienteRefBancariasDTO();
     RespuestaDTO respuesta = new RespuestaDTO();     
     List<FichaClienteRefBancariasDTO> fichaClienteRefBancariasList = new ArrayList<FichaClienteRefBancariasDTO>();
     try {
           respuesta = this.conectarJNDI(JNDI_ORACLE_9, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("FICHA_CLIENTE", "OBT_REF_BANCARIA_CLIENTE", 2, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }            
            
            statement.setInt(1, Integer.parseInt(iCodigoCliente));            
            statement.registerOutParameter(2, OracleTypes.CURSOR); 
            this.ejecuto = statement.execute();
            

                    objResultSet = (ResultSet) statement.getObject(2);
                    while (objResultSet.next()) {
                       refBancarias = new FichaClienteRefBancariasDTO(); 
                       refBancarias.setCodigoFinanciera(objResultSet.getBigDecimal("CODIGO_FINANCIERA"));
                       refBancarias.setTipoFinanciera(objResultSet.getBigDecimal("TIPO_FINANCIERA"));
                       refBancarias.setTipoCuentaFinanciera(objResultSet.getString("TIPO_DE_CUENTA_REFERENCIA"));
                       refBancarias.setNumeroCuentaReferencia(objResultSet.getString("NUMERO_DE_CUENTA_REFERENCIA"));
                       refBancarias.setFechaApertura(objResultSet.getString("FECHA_APERTURA"));
                       refBancarias.setNombreBanco(objResultSet.getString("NOMBRE_BANCO"));
                       refBancarias.setDescCuenta(objResultSet.getString("DESCRIPCION_CUENTA"));
                       refBancarias.setSaldoPromedio(objResultSet.getString("SALDO_PROMEDIO1"));
                       fichaClienteRefBancariasList.add(refBancarias);
                    }
              
            refBancariasSalida.setIbReferenciaList(fichaClienteRefBancariasList);            
            refBancariasSalida.setRespuesta(respuesta); 
           
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
        return refBancariasSalida;
        
        }
}
