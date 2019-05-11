/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.DivisasDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.SicadIDTO;
import com.bds.ws.dto.SicadIIDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.sql.ResultSet;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz DivisasDAO
 * @author juan.faneite
 */
@Named("DivisasDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class DivisasDAOImpl extends DAOUtil implements DivisasDAO {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(DivisasDAOImpl.class.getName());

    /**
     * Obtiene los datos para consultar el listado de las subastas historicas en
     * las que ha participado el cliente.
     *
     * @param iIdentificacion String Numero de cedula del cliente.
     * @param canal String canal por el cual se realiza la consulta.
     * @return SicadIDTO
     */
    @Override
    public SicadIDTO consultaAdjSicadI(String iIdentificacion, String canal) {
        SicadIDTO sicadI = new SicadIDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_SICAD", "IB_P_CONSULTA_ADJ_SICADI", 2, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, iIdentificacion);
            statement.registerOutParameter(2, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(2);
            while (objResultSet.next()) {
                sicadI.setNombreBono(this.getCharSetOracle9(objResultSet, "nombreBono"));
                sicadI.setCodigoAdjudicacion(this.getCharSetOracle9(objResultSet, "codigoAdjudicacion"));
                sicadI.setEstatus(this.getCharSetOracle9(objResultSet, "estatus"));
                sicadI.setMontoCompra(objResultSet.getBigDecimal(this.getCharSetOracle9(objResultSet, "montoCompra")));
                sicadI.setPrecio(objResultSet.getBigDecimal(this.getCharSetOracle9(objResultSet, "precio")));
                sicadI.setMtoAsignadoDolar(objResultSet.getBigDecimal(this.getCharSetOracle9(objResultSet, "mtoAsignadoDolar")));
                sicadI.setMtoAsignadoBS(objResultSet.getBigDecimal(this.getCharSetOracle9(objResultSet, "mtoAsignadoBS")));
                sicadI.setTipoCambio(objResultSet.getBigDecimal(this.getCharSetOracle9(objResultSet, "tipoCambio")));
                sicadI.setFechaSolicitud(this.getUtilDate(objResultSet, "fechaSolicitud"));
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN consultaAdjSicadI: ")
                    .append("CI-").append(iIdentificacion)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            sicadI.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("c consultaAdjSicadI: ")
//                    .append("CI-").append(iIdentificacion)
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return sicadI;
    }

    /**
     * Obtiene los datos para consultar el listado de las subastas historicas en
     * las que ha participado el cliente.
     *
     * @param iIdentificacion String Numero de cedula del cliente.
     * @param canal String canal por el cual se realiza la consulta.
     * @return SicadIIDTO
     */
    @Override
    public SicadIIDTO consultaAdjSicadII(String iIdentificacion, String canal) {
        SicadIIDTO sicadII = new SicadIIDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_SICAD", "IB_P_CONSULTA_ADJ_SICADII", 2, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, iIdentificacion);
            statement.registerOutParameter(2, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(2);
            while (objResultSet.next()) {
                sicadII.setSecuenciaEmision(this.getCharSetOracle9(objResultSet, "mtoAsignadoBS"));
                sicadII.setFechaAdicion(this.getUtilDate(objResultSet, "fechaAdicion"));
                sicadII.setMontoCompra(objResultSet.getBigDecimal(this.getCharSetOracle9(objResultSet, "montoCompra")));
                sicadII.setMontoOperacion(objResultSet.getBigDecimal(this.getCharSetOracle9(objResultSet, "montoOperacion")));
                sicadII.setMontoComision(objResultSet.getBigDecimal(this.getCharSetOracle9(objResultSet, "montoComision")));
                sicadII.setPorcentajeFinanc(objResultSet.getBigDecimal(this.getCharSetOracle9(objResultSet, "porcentajeFinanc")));
                sicadII.setNumeroCuenta(this.getCharSetOracle9(objResultSet, "numeroCuenta"));
                sicadII.setNumeroCuentaDolar2(this.getCharSetOracle9(objResultSet, "numeroCuentaDolar2"));
                sicadII.setMontoAdjudicado(objResultSet.getBigDecimal(this.getCharSetOracle9(objResultSet, "montoAdjudicado")));
                sicadII.setTipoCambio(this.getCharSetOracle9(objResultSet, "tipoCambio"));
                sicadII.setInstrumento(this.getCharSetOracle9(objResultSet, "instrumento"));
                sicadII.setEstatus(this.getCharSetOracle9(objResultSet, "estatus"));
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN consultaAdjSicadII: ")
                    .append("CI-").append(iIdentificacion)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            sicadII.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultaAdjSicadII: ")
//                    .append("CI-").append(iIdentificacion)
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return sicadII;
    }

}
