/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbLogsDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbLogsDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbLogsFacade;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz IbLogsDAO
 * @author juan.faneite
 */
@Named("IbLogsDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbLogsDAOImpl extends DAOUtil implements IbLogsDAO{

    @EJB
    IbLogsFacade logsFacade;
    
    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(IbLogsDAOImpl.class.getName());
    
    /**
     * Metodo para obtener el listado de moviemientos del log del usuario
     *
     * @param idUsuario id del usuario
     * @param fechaDesde fecha desde si viene null se tomaran los ultimos registros
     * @param fechaHasta fecha hasta si viene null se tomaran los ultimos registros
     * @param nroMesesAtras cantidad de meses atras a colsultar
     * @param idTransaccion número de la transacción por la que se realizará el filtro
     * @param idCanal id del canal a filtrar
     * @param nombreCanal nombre del canal
     * @return IbLogsDTO -> List<IbLogs>
     */
    @Override
    public IbLogsDTO listadoHistoricoCliente(String idUsuario, String fechaDesde, String fechaHasta, int nroMesesAtras, String idTransaccion, String idCanal, String nombreCanal) {
        IbLogsDTO logsDTO = new IbLogsDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {

            if (idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                logsDTO.setRespuesta(respuesta);
                throw new DAOException();
            }

            Date fechaInicio;
            Date fechaFin;
            if (fechaDesde == null || fechaDesde.isEmpty() || fechaDesde.equalsIgnoreCase("")) {
                fechaInicio = null;
            } else {
                fechaInicio = formatearFechaStringADate(fechaDesde, FORMATO_FECHA_SIMPLE);
            }

            if (fechaHasta == null || fechaHasta.isEmpty() || fechaHasta.equalsIgnoreCase("")) {
                fechaFin = null;
            } else {
                fechaFin = formatearFechaStringADate(fechaHasta, FORMATO_FECHA_SIMPLE);
            }

            logsDTO = logsFacade.listaMovLogs(idUsuario, fechaInicio, fechaFin, nroMesesAtras, idTransaccion, idCanal, nombreCanal);

            if (!logsDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

            if (logsDTO.getLogs().isEmpty()) {                
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listadoHistoricoCliente: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoHistoricoCliente: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }finally{
            logsDTO.setRespuesta(respuesta);
        }
//        if (logsDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoHistoricoCliente: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return logsDTO;
    }
}
