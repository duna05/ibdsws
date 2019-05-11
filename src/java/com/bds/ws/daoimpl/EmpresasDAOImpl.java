/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.EmpresasDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 *
 * @author luis.perez
 */
@Named("EmpresasDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class EmpresasDAOImpl extends DAOUtil implements EmpresasDAO  {
    
    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(EmpresasDAOImpl.class.getName());
    
    /**
     * Metodo que se encarga de buscar el nombre y el codigo de la empresa por un RIF en el
     * CORE del banco
     *
     * @param nroCuenta numero de cuenta correspondiente a la empresa
     * @param tipoRif tipo de rif J, G, etc.
     * @param rif rif de la empresa
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE bancario
     * @return UtilDTO
     */
    
    @Override
    public UtilDTO buscarNombreCodEmpresaByRif(String nroCuenta,Character tipoRif, String rif, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        String rifCompleto = tipoRif + rif;
        Map resultado = new HashMap();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_AUTOGESTION", "IB_P_VALIDA_CTA_RIF", 7, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida
            statement.setString(1, rifCompleto);
            statement.setString(2, nroCuenta);
            statement.setString(3, codigoCanal); 
            statement.registerOutParameter(4, OracleTypes.INTEGER); //CodCliente
            statement.registerOutParameter(5, OracleTypes.VARCHAR); //RazonSocial
            statement.registerOutParameter(6, OracleTypes.INTEGER); //CodSalida
            statement.registerOutParameter(7, OracleTypes.CHAR); //v_Salida
            this.ejecuto = statement.execute();
 
            //RETORNA 1 SI LA CUENTA PERTENECE AL CLIENTE
            Integer codSalida = (Integer) statement.getObject(6);
            //RETORNA V SI LA CUENTA ESTA ACTIVA EN CASO CONTRARIO RETORNA F
            String vSalida = (String) statement.getObject(7);
                       
            if (!codSalida.equals(1) || vSalida.trim().equalsIgnoreCase("F")) {
                resultado.put(EXISTE, false);
            } else {
                //CODIGO DEL CLIENTE (CODIGO DE LA EMPRESA EN EL CORE)
                Integer codCliente = (Integer) statement.getObject(4);
                //RAZON SOCIAL (NOMBRE DE LA EMPRESA EN EL CORE)
                String nombreEmpresa = new String(this.statement.getBytes(5), CHARSET_ORACLE_9);
                resultado.put(EXISTE, true);
                resultado.put(NOMBRE_EMPRESA, nombreEmpresa);
                resultado.put(CODIGO_EMPRESA, codCliente);
            }       
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN buscarNombreCodEmpresaByRif: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            
            logger.error( new StringBuilder("ERROR EN SP: IB_K_AUTOGESTION.IB_P_VALIDA_CTA_RIF ")
                    .append("Parametros SP: rif: ").append(rif).append(" nroCuenta: ").append(nroCuenta)
                    .append(" codigoCanal: ").append(codigoCanal)
                    .toString());
            
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            utilDTO.setRespuesta(respuesta);
        } finally {
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
        return utilDTO;
    }
}
