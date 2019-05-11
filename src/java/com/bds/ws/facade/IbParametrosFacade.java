/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbParametrosDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbParametros;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Date;
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
@Named("ibParametrosFacade")
@Stateless(name = "wsIbParametrosFacade") 
public class IbParametrosFacade extends AbstractFacade<IbParametros> {

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

    public IbParametrosFacade() {
        super(IbParametros.class);
    }

    /**
     * Metodo que realiza la consulta de un parametro en la tabla IB_PARAMETROS
     *
     * @param codigoParametro codigo del parametro
     * @param nombreCanal nombre del canal
     * @return IbParametrosDTO
     */
    public IbParametrosDTO consultaParametro(String codigoParametro, String nombreCanal) {

        IbParametrosDTO ibParametrosDTO = new IbParametrosDTO(); 
        IbParametros ibParametros = new IbParametros();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {

            ibParametros = (IbParametros) em.createQuery("SELECT c FROM IbParametros c "
                    + "WHERE c.codigo = :codParametro"
            )
                    .setParameter("codParametro", codigoParametro)
                    .getSingleResult();

            ibParametrosDTO.setIbParametro(ibParametros);
            
            if (ibParametrosDTO.getIbParametro() == null || ibParametrosDTO.getIbParametro().getId() == null){
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log 
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN consultaParametro: ")
                    .append("PARAMETRO-").append(codigoParametro)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN consultaParametro: ")
                    .append("PARAMETRO-").append(codigoParametro)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            ibParametrosDTO.setIbParametro(ibParametros);
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        ibParametrosDTO.setRespuesta(respuestaDTO);
        return ibParametrosDTO;
    }

    /**
     * Metodo que realiza la consulta de un parametro en la tabla IB_PARAETROS
     *
     * @param codigoParametro codigo del parametro
     * @param idCanal id del canal
     * @param nombreCanal nombre del canal
     * @return IbParametrosDTO
     */
    public IbParametrosDTO consultaParametro(String codigoParametro, BigDecimal idCanal, String nombreCanal) {

        IbParametrosDTO ibParametrosDTO = new IbParametrosDTO();
        IbParametros ibParametros = new IbParametros();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {

            ibParametros = (IbParametros) em.createQuery("SELECT c FROM IbParametros c "
                    + "WHERE c.codigo = :codParametro "
                    + "and c.idCanal.id = :codIdCanal")
                    .setParameter("codParametro", codigoParametro)
                    .setParameter("codIdCanal", idCanal)
                    .getSingleResult();

            ibParametrosDTO.setIbParametro(ibParametros);
            
            if (ibParametrosDTO.getIbParametro() == null || ibParametrosDTO.getIbParametro().getId() == null){
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log     
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN consultaParametro: ")
                    .append("PARAMETRO-").append(codigoParametro)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN consultaParametro: ")
                    .append("PARAMETRO-").append(codigoParametro)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            ibParametrosDTO.setIbParametro(ibParametros);
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        ibParametrosDTO.setRespuesta(respuestaDTO);
        return ibParametrosDTO;
    }

}
