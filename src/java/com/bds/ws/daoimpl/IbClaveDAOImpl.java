/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbClaveDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbHistoricoClavesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbHistoricoClavesFacade;
import com.bds.ws.facade.IbUsuariosFacade;
import com.bds.ws.model.IbHistoricoClaves;
import com.bds.ws.model.IbUsuarios;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.FORMATO_FECHA_SIMPLE;
import static com.bds.ws.util.BDSUtil.TEXTO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.formatearFechaDateADate;
import static java.lang.Integer.parseInt;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 *
 * @author wilmer.rondon
 */
@Named("IbClaveDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbClaveDAOImpl implements IbClaveDAO {
    
    private static final Logger logger = Logger.getLogger(IbClaveDAOImpl.class.getName());
    
    @EJB
    IbHistoricoClavesFacade historicoClavesFacade;
    
    @EJB
    IbUsuariosFacade usuariosFacade;
    

    /**
     * Método que se encarga de consultar si la nueva clave ya se encuentra entre las
     * últimas N claves que el cliente ha tenido
     * @param idUsuario
     * @param clave
     * @param cantClaves
     * @param nombreCanal
     * @return UtilDTO
     */
    @Override
    public UtilDTO existeEnUltimasNClaves(String idUsuario, String clave, String cantClaves, String nombreCanal) {
        
        UtilDTO utilDTO = new UtilDTO();                
        try {
            
            if(idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("")
                    || clave == null || clave.isEmpty() || clave.equalsIgnoreCase("")
                    || cantClaves == null || cantClaves.isEmpty() || cantClaves.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")){
                utilDTO.getRespuesta().setCodigo("DAO008");
                throw new DAOException("DAO008");
            }
            
            utilDTO = historicoClavesFacade.existeEnUltimasNClaves(idUsuario, clave, cantClaves, nombreCanal);
            
            if (!utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                throw new Exception();
            }
            
            if(utilDTO.getResulados().isEmpty()){
                
                throw new NoResultException();
            }
            
        } catch (NoResultException e) {
            utilDTO.getRespuesta().setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
            utilDTO.getRespuesta().setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN existeEnUltimasNClaves: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        }catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN existeEnUltimasNClaves: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN existeEnUltimasNClaves: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }                
        return utilDTO;
    }
    
    /**
     * Método que se encarga de actualizar la nueva clave en IB_USUARIOS y de insertar
     * en la tabla IB_HISTORICO_CLAVES la última clave actualizada por el usuario así como el
     * período de validez de la misma.
     * @param idUsuario
     * @param clave
     * @param periodoClave
     * @param nombreCanal
     * @return RespuestaDTO
     */
    @Override
    public RespuestaDTO crearClave(String idUsuario, String clave, String periodoClave, String nombreCanal) {
        
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbUsuarios ibUsuario = new IbUsuarios();
        IbHistoricoClaves ibHistoricoClaves = new IbHistoricoClaves();
                
        try {
            
            if(idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("")
                    || clave == null || clave.isEmpty() || clave.equalsIgnoreCase("")
                    || periodoClave == null || periodoClave.isEmpty() || periodoClave.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")){
                respuestaDTO.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }
            
            ibUsuario = usuariosFacade.find(new BigDecimal(idUsuario));
            
            if (ibUsuario == null){                
                throw new NoResultException();
            }
            
            
            ibUsuario.setClave(clave);
            ibUsuario.setIntentosFallidosPreguntas(0);
            
            respuestaDTO = usuariosFacade.actualizar(ibUsuario);
            
            if (!respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                throw new Exception();
            }            
            
            Calendar calendar = Calendar.getInstance();
                        
            ibHistoricoClaves.setId(BigDecimal.ZERO);
            ibHistoricoClaves.setIdUsuario(ibUsuario);
            ibHistoricoClaves.setClave(clave);
            
            //Se establece como fecha de creación la fecha actual
            ibHistoricoClaves.setFechaCreacion(calendar.getTime());
            
            //se le agrega la cantidad de días a la fecha actual para calcular el período de duración de la clave
            calendar.add(Calendar.DAY_OF_YEAR, parseInt(periodoClave));
            
            ibHistoricoClaves.setFechaVencimiento(formatearFechaDateADate(calendar.getTime(),FORMATO_FECHA_SIMPLE));
                        
            respuestaDTO = historicoClavesFacade.crear(ibHistoricoClaves);
            
            if (!respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                throw new Exception();
            }            
            
        } catch (NoResultException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_SIN_RESULTADOS_JPA);
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN crearClave: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());            
        }catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN crearClave: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN crearClave: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }                
        return respuestaDTO;
    }
    
    /**
     * Método que se encarga de retornar la última clave que tiene el cliente
     * activa     
     * @param idUsuario
     * @param nombreCanal
     * @return IbHistoricoClavesDTO
     */
    @Override
    public IbHistoricoClavesDTO obtenerUltimaClave(String idUsuario, String nombreCanal){
        
        IbHistoricoClavesDTO ibHistoricoClavesDTO = new IbHistoricoClavesDTO();
                
        try {
            
            if(idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("")                    
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")){
                ibHistoricoClavesDTO.getRespuesta().setCodigo("DAO008");
                throw new DAOException("DAO008");
            }
            
            ibHistoricoClavesDTO = historicoClavesFacade.obtenerUltimaClave(idUsuario, nombreCanal);
            
            if (!ibHistoricoClavesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                throw new Exception();
            }
            
            if(ibHistoricoClavesDTO.getIbHistoricoClave() == null){                
                throw new NoResultException();
            }
            
        } catch (NoResultException e) {
            ibHistoricoClavesDTO.getRespuesta().setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
            ibHistoricoClavesDTO.getRespuesta().setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN obtenerUltimaClave: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        }catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerUltimaClave: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            ibHistoricoClavesDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (ibHistoricoClavesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerUltimaClave: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }                
        return ibHistoricoClavesDTO;
        
    }
    
}
