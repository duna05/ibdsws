/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbLogsDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbLogs;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("ibLogsFacade")
@Stateless(name = "wsIbLogsFacade")
public class IbLogsFacade extends AbstractFacade<IbLogs> {

    @EJB
    IbParametrosFacade parametrosFacade;
    
    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(IbAfiliacionesFacade.class.getName());

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbLogsFacade() {
        super(IbLogs.class);
    }

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
    public IbLogsDTO listaMovLogs(String idUsuario, Date fechaDesde, Date fechaHasta, int nroMesesAtras, String idTransaccion, String idCanal, String nombreCanal) {
        IbLogsDTO logsDTO = new IbLogsDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        String queryIdTransaccion = "";
        String queryIdCanal = "";
        Query query;
        try {

            if (fechaDesde == null || fechaHasta == null) {
                Date referenceDate = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(referenceDate);
                c.add(Calendar.MONTH, nroMesesAtras);

                //se asigna un valor a la fechaDesde y fechaHasta
                fechaDesde = c.getTime();
                fechaHasta = new Date();
            }

            if (idTransaccion != null && !idTransaccion.isEmpty()) {
                queryIdTransaccion = " and l.idTransaccion = :idTransaccion ";
            }

            if (idCanal != null && !idCanal.isEmpty()) {
                queryIdCanal = " and l.idCanal.id = :idCanal "; 
            }

            query = em.createQuery("select l from IbLogs l where l.idUsuario.id = :idUsuario "
                    + queryIdTransaccion
                    + queryIdCanal
                    + " and FUNC('TRUNC',l.fechaHora) BETWEEN FUNC('TRUNC', :fechaDesde) and FUNC('TRUNC', :fechaHasta) "                    
                    + " ORDER BY L.fechaHora DESC");

            query.setParameter("fechaDesde", fechaDesde);
            query.setParameter("fechaHasta", fechaHasta);
            query.setParameter("idUsuario", new BigDecimal(idUsuario));

            if (idTransaccion != null && !idTransaccion.isEmpty()) {
                query.setParameter("idTransaccion", new BigDecimal(idTransaccion));
            }

            if (idCanal != null && !idCanal.isEmpty()) {
                query.setParameter("idCanal", new BigDecimal(idCanal));
            }

            logsDTO.setLogs((List<IbLogs>) query.getResultList());

            if (logsDTO.getLogs().isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log   
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN listaMovLogs: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN listaMovLogs: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception ex) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        }
        logsDTO.setRespuesta(respuestaDTO);
        return logsDTO;
    }
    
}
