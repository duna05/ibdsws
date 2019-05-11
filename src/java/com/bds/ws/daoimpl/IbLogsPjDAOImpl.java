/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbLogsPjDAO;
import com.bds.ws.dao.IbParametrosDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbLogsPjDTO;
import com.bds.ws.dto.IbParametrosDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbLogsPjFacade;
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
@Named("IbLogsPjDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbLogsPjDAOImpl extends DAOUtil implements IbLogsPjDAO{

    @EJB
    IbLogsPjFacade ibLogsPjFacade;
    
    @EJB
    IbParametrosDAO ibParametrosDAO;
    
    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(IbLogsPjDAOImpl.class.getName());
    
    private String CODIGO_CANTIDAD_MESES_ATRAS = "pjw.global.filtrar.meses";
    
    /**
     * Metodo para obtener el listado de moviemientos del log del usuario
     *
     * @param idUsuario id del usuario
     * @param fechaDesde fecha desde si viene null se tomaran los ultimos registros
     * @param fechaHasta fecha hasta si viene null se tomaran los ultimos registros
     * @param idServicio id del serivio en IB
     * @param idCanal id del canal a filtrar
     * @param nombreCanal nombre del canal
     * @return IbLogsDTO -> List<IbLogsPj>
     */
    @Override
    public IbLogsPjDTO listadoHistoricoUsuarioEmpresaPj(String idUsuario, String idEmpresa, String fechaDesde, String fechaHasta, String idServicio, String idCanal, String nombreCanal) {
        IbLogsPjDTO logsPjDTO = new IbLogsPjDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {

            if (idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                logsPjDTO.setRespuesta(respuesta);
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
            
            IbParametrosDTO ibParametrosDTO = ibParametrosDAO.consultaParametro(CODIGO_CANTIDAD_MESES_ATRAS, idCanal);
            int nroMesesAtras = Integer.parseInt(ibParametrosDTO.getIbParametro().getValor());
            
            logsPjDTO = ibLogsPjFacade.listaMovLogs(idUsuario,idEmpresa,fechaInicio, fechaFin, nroMesesAtras, idServicio, idCanal, nombreCanal);

            if (!logsPjDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

            if (logsPjDTO.getLogsPj().isEmpty()) {                
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
            logsPjDTO.setRespuesta(respuesta);
        }
        return logsPjDTO;
    }
}
