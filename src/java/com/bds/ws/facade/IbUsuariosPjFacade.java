/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusAccesoEnum;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import com.bds.ws.model.IbPerfilesPj;
import com.bds.ws.model.IbUsuariosPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis.perez
 */
@Named("ibUsuariosPjFacade")
@Stateless(name = "wsIbUsuariosPjFacade")
public class IbUsuariosPjFacade extends AbstractFacade<IbUsuariosPj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbUsuariosPjFacade() {
        super(IbUsuariosPj.class);
    }
    
    public IbUsuariosPj validarUsuarioLogin(String login) {
        IbUsuariosPj usuario = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbUsuariosPj e");
            consulta.append("     WHERE UPPER(e.login)  = UPPER(:login)");
            
            usuario = (IbUsuariosPj) em.createQuery(consulta.toString())
                    .setParameter("login", login)
                    .getSingleResult();
            
        } catch (NoResultException e){
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return usuario;
    }
    
    public IbUsuariosPj validarLogin(String password, String login, String idCanal, String codigoCanal) {
        IbUsuariosPj usuario = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbUsuariosPj e, ");
            consulta.append("     IbHistoricoClavesPj    c          ");
            consulta.append("     WHERE e.id     = c.idUsuarioPj.id ");
            consulta.append("     AND   e.login  = :login");
            consulta.append("     AND   e.clave  = :clave");
            consulta.append("     AND   c.clave  = :clave");
            
            usuario = (IbUsuariosPj) em.createQuery(consulta.toString())
                    .setParameter("login", login)
                    .setParameter("clave", password)
                    .getSingleResult();
            
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return usuario;
    }
   
    public IbUsuariosPj consultarUsuarioPjPorCedula(Character tipoDocumento, String nroDocumento) {
        IbUsuariosPj ibUsuarioPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbUsuariosPj e");
            consulta.append("     WHERE e.tipoDoc  = :tipoDoc");
            consulta.append("     AND   e.nroDoc   = :nroDoc");
            
            ibUsuarioPj = (IbUsuariosPj) em.createQuery(consulta.toString())
                    .setParameter("tipoDoc", tipoDocumento)
                    .setParameter("nroDoc", nroDocumento)
                    .getSingleResult();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return ibUsuarioPj;
    }
    
    public IbUsuariosPj consultarUsuarioPjPorId(BigDecimal idUsuario) {
        IbUsuariosPj ibUsuarioPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbUsuariosPj e");
            consulta.append("     WHERE e.id  = :idUsuario");
            ibUsuarioPj = (IbUsuariosPj) em.createQuery(consulta.toString())
                    .setParameter("idUsuario", idUsuario)
                    
                    .getSingleResult();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return ibUsuarioPj;
    }
    
    public List<IbEmpresasUsuariosPj> consultarUsuariosSinReglasAproPj(BigDecimal perfilAcceso, int[] estatusRegistro, BigDecimal idEmpresa) {
        List<IbEmpresasUsuariosPj> listaIbEmpresasUsuariosPj = new ArrayList<>();
        IbEmpresasUsuariosPj ibEmpresasUsuariosPj;
        IbUsuariosPj ibUsuariosPj;
        IbPerfilesPj ibPerfilesPj;
        StringBuilder consulta = new StringBuilder();
        int sizeArray = estatusRegistro.length;
        StringBuilder condicion = new StringBuilder();
        int i = 1;
        for (int estatusRegistroi : estatusRegistro) {
            if (sizeArray > 1) {
                condicion.append("'").append(estatusRegistroi).append("'");
                //SI NO ES EL ULTIMO REGISTRO
                if (i < sizeArray) {
                    condicion.append(",");
                }
            } else {
                condicion.append("'").append(estatusRegistroi).append("'");
            }
            i++;
        }
        
        consulta.append(" SELECT U.ID, EU.ID,     U.NOMBRE,        U.TIPO_DOC, U.NRO_DOC, EU.CARGO, ");
        consulta.append("        EU.TELF_OFICINA, EU.TELF_CELULAR, EU.EMAIL,   EU.PERFIL_ACCESO   ");
        consulta.append("     FROM IB_USUARIOS_PJ U, ");
        consulta.append("          IB_EMPRESAS_USUARIOS_PJ EU, ");
        consulta.append("          IB_MONTOS_USUARIOS_PJ   MONTOS ");
        consulta.append("     WHERE EU.ID_EMPRESA    = MONTOS.ID_EMPRESA    (+) ");
        consulta.append("     AND   EU.ID_USUARIO_PJ = MONTOS.ID_USUARIO_PJ (+) ");
        consulta.append("     AND U.ID             = EU.ID_USUARIO_PJ ");
        consulta.append("     AND MONTOS.ID_EMPRESA    IS NULL ");
        consulta.append("     AND MONTOS.ID_USUARIO_PJ IS NULL ");
        consulta.append("     AND EU.PERFIL_ACCESO  = ").append(perfilAcceso);
        consulta.append("     AND EU.ID_EMPRESA    = ").append(idEmpresa);
        consulta.append("     AND EU.ESTATUS_REGISTRO IN (").append(condicion).append(")");
        consulta.append("     AND EU.ESTATUS_ACCESO NOT IN (").append(EstatusAccesoEnum.ELIMINADO.getId()).append(")");
        consulta.append("     AND U.ESTATUS_ACCESO  NOT IN (").append(EstatusAccesoEnum.ELIMINADO.getId()).append(")");
        consulta.append("     ORDER BY NOMBRE ");
        
        Collection listaUsuarios = em.createNativeQuery(consulta.toString())
        .getResultList();
        
        Iterator interador = listaUsuarios.iterator();
        while(interador.hasNext()){
            Object[] registro = (Object[]) interador.next();
            ibEmpresasUsuariosPj = new IbEmpresasUsuariosPj();
            ibUsuariosPj         = new IbUsuariosPj();
            ibPerfilesPj         = new IbPerfilesPj();
            ibUsuariosPj.setId((BigDecimal) registro[0]);
            ibEmpresasUsuariosPj.setId((BigDecimal) registro[1]);
            ibUsuariosPj.setNombre((String) registro[2]);
            ibUsuariosPj.setTipoDoc(((String) registro[3]).charAt(0));
            ibUsuariosPj.setNroDoc((String) registro[4]);
            ibEmpresasUsuariosPj.setCargo((String) registro[5]);
            ibEmpresasUsuariosPj.setTelfOficina((String) registro[6]);
            ibEmpresasUsuariosPj.setTelfCelular((String) registro[7]);
            ibEmpresasUsuariosPj.setEmail((String) registro[8]);
            
            ibPerfilesPj.setId((BigDecimal) registro[9]);
            ibEmpresasUsuariosPj.setPerfilAcceso(ibPerfilesPj);
            ibEmpresasUsuariosPj.setIdUsuarioPj(ibUsuariosPj);
            listaIbEmpresasUsuariosPj.add(ibEmpresasUsuariosPj);
        }
        return listaIbEmpresasUsuariosPj;
    }
    
    /**
     * Metodo actualiza la cantidad de intentos fallidos de las preguntas de
     * seguridad
     *
     * @param idUsuario identificador del usuario
     * @param cantIntentos cantidad de intentos fallidos a actualizar
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO actualizarCantIntentosFallidosPregSegPj(String idUsuario, int cantIntentos, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbUsuariosPj usuario;
        try {
            usuario = (IbUsuariosPj) em.createNamedQuery("IbUsuariosPj.findById")
                    .setParameter("id", new BigDecimal(idUsuario))
                    .getSingleResult();

            if (usuario == null) {
                throw new NoResultException();
            }

            usuario.setIntentosFallidosPreguntas(new BigInteger(String.valueOf(cantIntentos)));
            respuestaDTO = this.actualizar(usuario);

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN actualizarCantIntentosFallidosPregSeg: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
        }

        return respuestaDTO;
    }
    
    /**
     * Metodo para consultar la cantidad de intentos fallidos de preguntas de
     * seguridad
     *
     * @param idUsuario identificador del usuario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public UtilDTO cantPreguntasSeguridadFallidas(String idUsuario, String nombreCanal) {
        int cantIntentosFallidos = 0;
        UtilDTO utilDTO = new UtilDTO();
        Map resultados = new HashMap();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {

            cantIntentosFallidos = (int) em.createQuery("select c.intentosFallidosPreguntas from IbUsuarios c where c.id = :idUsuario")
                    .setParameter("idUsuario", new BigDecimal(idUsuario))
                    .getSingleResult();
            resultados.put("intentosFallidos", cantIntentosFallidos);

            utilDTO.setResulados(resultados);

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN cantPreguntasSeguridadFallidas: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception ex) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        }

        utilDTO.setRespuesta(respuestaDTO);

        return utilDTO;
    }
}
