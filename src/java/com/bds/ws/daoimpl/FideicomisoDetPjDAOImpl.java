/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FideicomisoDetPjDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.FideicomisoDetPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.model.IbEstatusPagosPj;
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

/**
 *
 * @author robinson.rodriguez
 */
@Named("FideicomisoDetPjDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FideicomisoDetPjDAOImpl extends DAOUtil implements FideicomisoDetPjDAO {

    private static final Logger logger = Logger.getLogger(FideicomisoDetPjDAOImpl.class.getName());

    @Override
    public FideicomisoDetPjDTO listarFideicomisoDetPj(String cdClienteAbanks, String cdUsuario, String idFideicomiso, String fechaHora, String idCanal, String codigoCanal) {
        FideicomisoDetPjDTO fideicomisoDetalle = null;
        FideicomisoDetPjDTO fideicomisoDetalleSalida = new FideicomisoDetPjDTO();
        List<FideicomisoDetPjDTO> fideicomisoDetalleList = new ArrayList<FideicomisoDetPjDTO>();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("FW_K_PROCESOS", "FW_P_CONSULTA_DET", 6, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida
            statement.setString(1, cdClienteAbanks);
            statement.setString(2, cdUsuario);
            statement.setString(3, idFideicomiso);
            statement.setDate(4, this.getSQLDate(fechaHora, FORMATO_FECHA_24));
            statement.registerOutParameter(5, OracleTypes.CURSOR);
            statement.registerOutParameter(6, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(5);

            while (objResultSet.next()) {
                fideicomisoDetalle = new FideicomisoDetPjDTO();
                fideicomisoDetalle.setNroLinea(objResultSet.getString("NRO_LINEA"));
                fideicomisoDetalle.setLineaTxt(objResultSet.getString("LINEA_TXT"));
                fideicomisoDetalle.setEstatus(new IbEstatusPagosPj(objResultSet.getBigDecimal("STATUS")));
                fideicomisoDetalle.setDetalleError(objResultSet.getString("ERRORES"));
                fideicomisoDetalleList.add(fideicomisoDetalle);

            }

            fideicomisoDetalleSalida.setFideicomisoDetPjDTOList(fideicomisoDetalleList);
            fideicomisoDetalleSalida.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listarFideicomisoDetPj: ")
                    .append("ID-CH- ").append(idCanal)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            fideicomisoDetalleSalida.setRespuesta(respuesta);
        } finally {
            fideicomisoDetalleSalida.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }

        return fideicomisoDetalleSalida;

    }

}
