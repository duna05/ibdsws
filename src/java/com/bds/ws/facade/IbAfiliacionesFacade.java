/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IBAfiliacionesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbAfiliaciones;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.T_PAG_TDC_PROPOTROSBANCOS;
import static com.bds.ws.util.BDSUtil.T_TRANSF_PROPOTROSBANCOS;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Named("ibAfiliacionesFacade")
@Stateless(name = "wsIbAfiliacionesFacade")
public class IbAfiliacionesFacade extends AbstractFacade<IbAfiliaciones> {

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

    public IbAfiliacionesFacade() {
        super(IbAfiliaciones.class);
    }

    /**
     * Metodo que Obtiene el listado de afiliados de un cliente por operacion.
     *
     * @param usuario String Referencia foranea al usuario dueno de la
     * afiliacion.
     * @param idTransaccion String ID de el tipo de transaccion.
     * @param canal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IBAfiliacionesDTO
     */
    public IBAfiliacionesDTO obtenerListadoAfiliadosPorOperacion(String usuario, String idTransaccion, String canal) {
        IBAfiliacionesDTO afiliacionesDTO = new IBAfiliacionesDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {
            afiliacionesDTO.setIbAfiliaciones((List<IbAfiliaciones>) em.createQuery("select c from IbAfiliaciones c "
                    + "where c.idUsuario.id = :usuario "
                    + "and c.idTransaccion = :idTransaccion "
                    + "and c.estatus = 'A' "
                    + "ORDER BY UPPER(c.nombreBeneficiario) ASC")
                    .setParameter("usuario", new BigDecimal(usuario))
                    .setParameter("idTransaccion", new BigDecimal(idTransaccion))
                    .getResultList());
            if(afiliacionesDTO.getIbAfiliaciones().isEmpty()){
                throw new NoResultException();
            }
        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log   
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN obtenerListadoAfiliadosPorOperacion: ")
                    .append("USR-").append(usuario)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN obtenerListadoAfiliadosPorOperacion: ")
                    .append("USR-").append(usuario)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception ex) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        }
        afiliacionesDTO.setRespuesta(respuestaDTO);
        return afiliacionesDTO;
    }

    /**
     * Metodo para obtener el listado de afiliados por codigo de usuario y
     * cedula de identidad
     *
     * @param nroIdentidad String CI del Dueno de la cuenta
     * @param codUsuario String codigo del usuario
     * @param idTransaccion String id de la transaccion
     * @param nombreCanal String ID del canal
     * @param tipoTransf Indica el tipo de transferencia
     * @return IBAfiliacionesDTO -> listado de afiliaciones
     */
    public IBAfiliacionesDTO afiliacionesOperacionCodUsuario(String nroIdentidad, String codUsuario, String idTransaccion, String tipoTransf, String nombreCanal) {
        IBAfiliacionesDTO afiliacionesDTO = new IBAfiliacionesDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        String condicion = "";
        try {

            if (tipoTransf.equals(T_TRANSF_PROPOTROSBANCOS) || tipoTransf.equals(T_PAG_TDC_PROPOTROSBANCOS)) {
                condicion = " = ";
            } else {
                condicion = " <> ";
            }

            afiliacionesDTO.setIbAfiliaciones((List<IbAfiliaciones>) em.createQuery("select c from IbAfiliaciones c where c.codUsuario = :usuario and c.idTransaccion = :idTransaccion and c.documento " + condicion + " :nroIdentidad and c.estatus = 'A' ORDER BY c.nombreBeneficiario")
                    .setParameter("usuario", codUsuario)
                    .setParameter("idTransaccion", new BigDecimal(idTransaccion))
                    .setParameter("nroIdentidad", nroIdentidad)
                    .getResultList());
            
            if(afiliacionesDTO.getIbAfiliaciones().isEmpty()){
                throw new NoResultException();
            }
            
        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log   
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN afiliacionesOperacionCodUsuario: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN afiliacionesOperacionCodUsuario: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception ex) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        }
        afiliacionesDTO.setRespuesta(respuestaDTO);
        return afiliacionesDTO;
    }
    
    /**
     * Metodo para verificar que no ingrese un alias existente
     * @param codUsuario codigo del usuario
     * @param alias alias del beneficiario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public UtilDTO verificarAlias (String codUsuario, String alias, String nombreCanal){
        long countAf = 0;
        UtilDTO utilDTO = new UtilDTO();
        Map resultados = new HashMap();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try{
            
        countAf = (long) em.createQuery("select count (c) from IbAfiliaciones c where c.codUsuario = :codUsuario and c.alias = :alias and c.estatus = 'A'")
                .setParameter("codUsuario", codUsuario)
                .setParameter("alias",alias)
                .getSingleResult();
        
        if(countAf > 0){
            resultados.put("existeAlias", true);
        }else{
            resultados.put("existeAlias", false);
        }
        
        utilDTO.setResulados(resultados);
        
        }catch(IllegalArgumentException e){            
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN verificarAlias: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        }catch(Exception ex){
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        }
        
        utilDTO.setRespuesta(respuestaDTO);
                
        return utilDTO;
    }
    
     /**
     * Metodo para verificar que no ingrese un producto existente
     * @param codUsuario codigo del usuario
     * @param producto producto del beneficiario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public UtilDTO verificarProducto (String codUsuario, String producto, String nombreCanal){
    long countAf = 0;
    UtilDTO utilDTO = new UtilDTO();
    Map resultados = new HashMap();
    RespuestaDTO respuestaDTO = new RespuestaDTO();
    try{
        countAf = (long) em.createQuery("select count (c) from IbAfiliaciones c where c.codUsuario = :codUsuario and c.numeroCuenta = :numeroCuenta and c.estatus = 'A'")
                .setParameter("codUsuario", codUsuario)
                .setParameter("numeroCuenta",producto)
                .getSingleResult();
        
        if(countAf > 0){
            resultados.put("existeProducto", true);
        }else{
            resultados.put("existeProducto", false);
        }
        utilDTO.setResulados(resultados);
    }catch(IllegalArgumentException e){            
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN verificarProducto: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        }catch(Exception ex){
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        }
        
        utilDTO.setRespuesta(respuestaDTO);
                
        return utilDTO;
    }
    
    
}
