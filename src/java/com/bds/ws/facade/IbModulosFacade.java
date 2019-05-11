/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbModulosDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbModulos;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
@Named("ibModulosFacade")
@Stateless(name = "wsIbModulosFacade")
public class IbModulosFacade extends AbstractFacade<IbModulos> {

    /*
     *logs del sistema
     */
    private static final Logger logger = Logger.getLogger(IbModulosFacade.class.getName());

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbModulosFacade() {
        super(IbModulos.class);
    }

    /**
     * *
     * Metodo que realiza la busqueda del menu a ser mostrado a un usuario
     *
     * @param idUsuario id del usuario
     * @param idcanal id del canal
     * @param codEstatus codigo del estatus
     * @param piloto es piloto
     * @return IbModulosDTO
     */
    public IbModulosDTO consultaModulosTransacciones(String idUsuario, String idcanal, Character codEstatus, boolean piloto) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbModulosDTO ibModulosDTO = new IbModulosDTO();
        String condicion = "";

        try {
            if (piloto) {
                condicion = " <> ";
                codEstatus = 'I';
            } else {
                condicion = " = ";
            }

            ibModulosDTO.setIbModulos((List<IbModulos>) em.createQuery("SELECT distinct c FROM IbModulos c "
                    + "JOIN FETCH IbTransacciones t on c.id = t.idModulo.id "
                    + "WHERE c.estatus " + condicion + " :codEstatus "
                    + "and t.estatus " + condicion + ":codEstatus "
                    + "and c.idCanal.id = :idcanal "
                    + "and c.visible = 1 ORDER BY c.posicion ASC"
            )
                    .setParameter("codEstatus", codEstatus)
                    .setParameter("idcanal", Long.parseLong(idcanal))
                    .getResultList()
            );
            
            if(ibModulosDTO.getIbModulos().isEmpty()){
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log    
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN consultaModulosTransacciones: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(idcanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN consultaModulosTransacciones: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(idcanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        ibModulosDTO.setRespuesta(respuestaDTO);

        return ibModulosDTO;
    }
    
    /**
     * Metodo para obtener el modelo y la transaccion de los que tengan el posee beneficiario activo
     * @param nombreCanal String ID del canal
     * @return IbModulosDTO
     */
    public IbModulosDTO obtenerModeloTransBeneficiario(String nombreCanal){
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbModulosDTO ibModulosDTO = new IbModulosDTO();

        try {
            
            ibModulosDTO.setIbModulos((List<IbModulos>) em.createQuery("SELECT DISTINCT m FROM IbModulos m, IbTransacciones t where m.idCanal.id = 2 "                     
                    + "and m.id = t.idModulo.id "
                    + "and t.poseeBeneficiario = 1 "
                    + "and t.estatus = 'A' "
                    + "and m.estatus = 'A' "
                    + "and m.visible = 1"
            )
                    .getResultList()
            );
            
            if(ibModulosDTO.getIbModulos().isEmpty()){
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log     
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN obtenerModeloTransBeneficiario: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN obtenerModeloTransBeneficiario: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        ibModulosDTO.setRespuesta(respuestaDTO);

        return ibModulosDTO;
    }
    
    /**
     * Método que obtiene todos los módulos asociados a un canal esten activos o inactivos,
     * visibles o no visibles
     * @author wilmer.rondon
     * @param idCanal canal del cual se desea obtener los modulos y las transacciones
     * @param nombreCanal nombre del canal para efectos de los logs
     * @return IbModulosDTO
     */
    public IbModulosDTO obtenerTodosModulos(String idCanal, String nombreCanal){
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbModulosDTO ibModulosDTO = new IbModulosDTO();

        try {
            
            ibModulosDTO.setIbModulos((List<IbModulos>) em.createQuery("SELECT DISTINCT m "
                    + "FROM IbModulos m, "
                    + "IbTransacciones t, "
                    + "IbTextos tt "
                    + "where m.idCanal.id = :idCanal "                     
                    + "and m.id = t.idModulo.id and tt.codigo = m.nombre ORDER BY tt.mensajeUsuario ASC"
            )
                    .setParameter("idCanal", new BigDecimal(idCanal))
                    .getResultList()
            );
            
            if(ibModulosDTO.getIbModulos().isEmpty()){
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log    
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN obtenerTodosModulos: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN obtenerTodosModulos: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        ibModulosDTO.setRespuesta(respuestaDTO);

        return ibModulosDTO;
    }
}
