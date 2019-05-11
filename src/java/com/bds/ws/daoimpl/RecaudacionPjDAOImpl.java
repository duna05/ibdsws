/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.RecaudacionPjDAO;
import com.bds.ws.dto.RecaudacionPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

@Named("RecaudacionDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class RecaudacionPjDAOImpl extends DAOUtil implements RecaudacionPjDAO {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(RecaudacionPjDAOImpl.class.getName());

    @Override
    public List<RecaudacionPjDTO> obtenerDetalleRecaudacion(String sNumeroConvenio, String dFechaDesde, String dFechaHasta, String iCodExtCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        List<RecaudacionPjDTO> listarecaudacion = new ArrayList<>();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_RECAUDACIONES", "IB_P_CONSULTA_RECAUDACIONES", 8, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida
            statement.setString(1, sNumeroConvenio);
            statement.setString(2, dFechaDesde);
            statement.setString(3, dFechaHasta);
            statement.setString(4, iCodExtCanal);
            statement.registerOutParameter(5, OracleTypes.VARCHAR);
            statement.registerOutParameter(6, OracleTypes.CURSOR);
            statement.registerOutParameter(7, OracleTypes.VARCHAR);
            statement.registerOutParameter(8, OracleTypes.NUMBER);

            this.ejecuto = statement.execute();
            objResultSet = (ResultSet) statement.getObject(6);
            
            while (objResultSet.next()) {
                //SI EL IDENTIFICADOR ES NULO NO SE AGREGA A RESULT SET
                if(objResultSet.getString("IDENTIFICADOR") != null){
                    RecaudacionPjDTO detalle = new RecaudacionPjDTO();
                    detalle.setIdentificador(objResultSet.getString("IDENTIFICADOR"));
                    detalle.setNumeroPlanilla(objResultSet.getString("NUMERO_PLANILLA"));
                    detalle.setFechaValida(objResultSet.getString("FECHA_VALOR"));
                    detalle.setLvOperacion(objResultSet.getString("OPERACION"));
                    detalle.setAgenciaOrigen(objResultSet.getString("AGENCIA_ORIGEN"));
                    detalle.setNumeroIdentificacion(objResultSet.getString("NUMERO_IDENTIFICACION"));
                    detalle.setValorLocal(objResultSet.getString("VALOR_LOCAL"));
                    detalle.setRespuesta(new RespuestaDTO());
                    detalle.getRespuesta().setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
                    listarecaudacion.add(detalle);
                }
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerDetalleRecaudacion: ")
                    .append("CTA-").append(sNumeroConvenio)
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            RecaudacionPjDTO recaudacion = new RecaudacionPjDTO();
            recaudacion.setRespuesta(respuesta);
            listarecaudacion.add(recaudacion);
        } finally {
            this.cerrarConexion(iCodExtCanal);
        }

        return listarecaudacion;
    }
}
