/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbCanalDTO;
import com.bds.ws.dto.IbParametrosDTO;
import com.bds.ws.dto.IbUsuariosCanalesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbCanal;
import com.bds.ws.model.IbUsuariosCanales;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.TEXTO_SIN_RESULTADOS_JPA;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("ibUsuariosCanalesFacade")
@Stateless(name = "wsIbUsuariosCanalesFacade")
public class IbUsuariosCanalesFacade extends AbstractFacade<IbUsuariosCanales> {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(IbUsuariosCanalesFacade.class.getName());
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @EJB
    IbCanalFacade ibCanalFacade;

    @EJB
    IbParametrosFacade parametroF;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbUsuariosCanalesFacade() {
        super(IbUsuariosCanales.class);
    }

    /**
     * *
     * Metodo que realiza la consulta del usuario.
     *
     * @param idUsuario id del usuario
     * @param idcanal id del canal
     * @return IbUsuariosCanalesDTO
     */
    public IbUsuariosCanalesDTO consultaUsuarioCanal(String idUsuario, String idcanal) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbUsuariosCanales usuariosCanales = new IbUsuariosCanales();
        IbUsuariosCanalesDTO ibUsuariosCanalesDto = new IbUsuariosCanalesDTO();
        try {

            usuariosCanales = (IbUsuariosCanales) em.createQuery("SELECT c FROM IbUsuariosCanales c "
                    + "WHERE c.idUsuario.id = :idUsuario "
                    + "AND c.idCanal.id = :canalId "
            )
                    .setParameter("idUsuario", BigDecimal.valueOf(Long.parseLong(idUsuario)))
                    .setParameter("canalId", BigDecimal.valueOf(Long.parseLong(idcanal)))
                    .getSingleResult();
            
            if(usuariosCanales == null || usuariosCanales.getId() == null){
                throw new NoResultException();
            }

            IbCanal canal = ibCanalFacade.consultaCanalID(idcanal);
            String nombreCanal = canal.getNombre();

            //---------------------------------------------------------------------------
            //Si los valores de los limites para las transferencias vienen en null o cero. Busca el valor por defecto
            //---------------------------------------------------------------------------
            IbParametrosDTO param = new IbParametrosDTO();

            if (BDSUtil.isBDecimalNullOrZero(usuariosCanales.getLimiteInternas())) {
                param = parametroF.consultaParametro("pnw.limite.trans.propias", nombreCanal);
                usuariosCanales.setLimiteInternas(new BigDecimal(param.getIbParametro().getValor()));
            }

            if (BDSUtil.isBDecimalNullOrZero(usuariosCanales.getLimiteInternasTerceros())) {
                param = parametroF.consultaParametro("pnw.limite.trans.tercerosdelsur", nombreCanal);
                usuariosCanales.setLimiteInternasTerceros(new BigDecimal(param.getIbParametro().getValor()));
            }

            if (BDSUtil.isBDecimalNullOrZero(usuariosCanales.getLimiteExternas())) {
                param = parametroF.consultaParametro("pnw.limite.trans.propiasotrosbancos", nombreCanal);
                usuariosCanales.setLimiteExternas(new BigDecimal(param.getIbParametro().getValor()));
            }

            if (BDSUtil.isBDecimalNullOrZero(usuariosCanales.getLimiteExternasTerceros())) {
                param = parametroF.consultaParametro("pnw.limite.trans.otrosbancos", nombreCanal);
                usuariosCanales.setLimiteExternasTerceros(new BigDecimal(param.getIbParametro().getValor()));
            }

            //---------------------------------------------------------------------------------------
            ibUsuariosCanalesDto.setIbUsuarioCanal(usuariosCanales);

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log 
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN consultaUsaurioCanal: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(idcanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN consultaUsaurioCanal: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(idcanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            ibUsuariosCanalesDto.setIbUsuarioCanal(usuariosCanales);

        }
        ibUsuariosCanalesDto.setRespuesta(respuestaDTO);
        return ibUsuariosCanalesDto;
    }
    
    /**
     * *
     * Metodo que realiza la consulta del usuario en usuario-canales
     *
     * @param codUsuario codigo del usuario
     * @param idcanal id del canal
     * @return IbUsuariosCanalesDTO
     */
    public IbUsuariosCanalesDTO consultaUsrCHPorCod(String codUsuario, String idcanal) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbUsuariosCanales usuariosCanales = new IbUsuariosCanales();
        IbUsuariosCanalesDTO ibUsuariosCanalesDto = new IbUsuariosCanalesDTO();
        try {

            usuariosCanales = (IbUsuariosCanales) em.createQuery("SELECT c FROM IbUsuariosCanales c, IbUsuarios u WHERE u.codUsuario = :codUsuario AND c.idUsuario.id = u.id AND  c.idCanal.id = :canalId ")
                    .setParameter("codUsuario", codUsuario)
                    .setParameter("canalId", BigDecimal.valueOf(Long.parseLong(idcanal)))
                    .getSingleResult();

            ibUsuariosCanalesDto.setIbUsuarioCanal(usuariosCanales);
            
            if(usuariosCanales == null || usuariosCanales.getId() == null){
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log  
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN consultaUsrCHPorCod: ")
                    .append("CUSR-").append(codUsuario)
                    .append("-CH-").append(idcanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN consultaUsrCHPorCod: ")
                    .append("CUSR-").append(codUsuario)
                    .append("-CH-").append(idcanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            ibUsuariosCanalesDto.setIbUsuarioCanal(usuariosCanales);

        }
        ibUsuariosCanalesDto.setRespuesta(respuestaDTO);
        return ibUsuariosCanalesDto;
    }

    /**
     * *
     * Metodo que realiza la consulta de los datos del usuario por cada canal
     * afiliado.
     *
     * @param idUsuario id del usuario
     * @param idcanal id del canal
     * @return IbUsuariosCanalesDTO
     */
    public IbUsuariosCanalesDTO consultaUsuarioCanales(String idUsuario, String idcanal) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        List<IbUsuariosCanales> usuariosCanales = null;
        IbUsuariosCanalesDTO ibUsuariosCanalesDto = new IbUsuariosCanalesDTO();
        try {

            usuariosCanales = (List<IbUsuariosCanales>) em.createQuery("SELECT c FROM IbUsuariosCanales c "
                    + "WHERE c.idUsuario.id = :idUsuario "
            )
                    .setParameter("idUsuario", BigDecimal.valueOf(Long.parseLong(idUsuario)))
                    .getResultList();

            IbCanal canal = ibCanalFacade.consultaCanalID(idcanal);
            String nombreCanal = canal.getNombre();

            ibUsuariosCanalesDto.setIbUsuariosCanales(usuariosCanales);
            
            if (ibUsuariosCanalesDto.getIbUsuariosCanales().isEmpty()){
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log  
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN consultaUsuarioCanales: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(idcanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN consultaUsuarioCanales: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(idcanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            ibUsuariosCanalesDto.setIbUsuariosCanales(usuariosCanales);

        }
        ibUsuariosCanalesDto.setRespuesta(respuestaDTO);
        return ibUsuariosCanalesDto;
    }

     /**
     * *
     * Metodo que realiza la culsulta del listado de canales por usuario
     *
     * @param idUsuario id del usuario
     * @param idcanal id del canal
     * @return IbCanalDTO
     */
    
    
    
        public IbCanalDTO consultaCanalesPorUsuario(String idUsuario,String idcanal) {
            
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        List<IbCanal> Canales = null;
        IbCanalDTO ibCanalDto = new IbCanalDTO();  
        try {
             Canales = (List<IbCanal>) em.createQuery("SELECT c FROM  IbUsuariosCanales u  JOIN u.idCanal c  WHERE u.idUsuario.id = :idUsuario "
              )
                    .setParameter("idUsuario", BigDecimal.valueOf(Long.parseLong(idUsuario)))
                    .getResultList();
        
                    ibCanalDto.setCanales(Canales);
                    
             if (ibCanalDto.getCanales().isEmpty()){
                throw new NoResultException();
                   }
             
             } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log  
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN consultaCanalesPorUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(idcanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN consultaCanalesPorUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(idcanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            ibCanalDto.setCanales(Canales);

        }
           ibCanalDto.setRespuesta(respuestaDTO);
           
          return ibCanalDto;
        
        }
    
    
}
