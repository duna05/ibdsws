/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbMenuDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbModulosDTO;
import com.bds.ws.dto.IbTransaccionesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbModulosFacade;
import com.bds.ws.facade.IbTransaccionesFacade;
import com.bds.ws.facade.IbUsuariosPerfilPilotoFacade;
import com.bds.ws.model.IbModulos;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz IbMenuDAO
 * @author renseld.lugo
 */
@Named("IbMenuDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbMenuDAOImpl extends DAOUtil implements IbMenuDAO {

    private static final Logger logger = Logger.getLogger(IbMenuDAOImpl.class.getName());

    @EJB
    private IbUsuariosPerfilPilotoFacade ibUsuariosPerfilPilotoFacade;

    @EJB
    private IbModulosFacade ibModulosFacade;

    @EJB
    private IbTransaccionesFacade ibTransaccionesFacade;
    
    /***
     * Metodo que retorna el menu (Modulos y Transacciones) validando si el usuario es "piloto" o no
     * @param idUsuario String id del usuario
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbModulosDTO Listado de los modulos por usuario
     */
    @Override
    public IbModulosDTO consultarModulosPorUsuario(String idUsuario,  String idCanal, String nombreCanal) {

        IbModulosDTO ibModulosDTO = new IbModulosDTO();
        List<IbModulos> modulosTemp = new ArrayList<IbModulos>();
        IbTransaccionesDTO ibTransacciones = null;
        UtilDTO utilDTO = new UtilDTO();
        Character codEstatus = CODIGO_BUSQUEDA_NO_PILOTO;
        RespuestaDTO respuesta = new RespuestaDTO();
        boolean esPiloto = false;
        try {
            utilDTO = ibUsuariosPerfilPilotoFacade.consultaUsaurioperfilPiloto(idUsuario, idCanal);
            if (utilDTO.getRespuesta().getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                codEstatus = CODIGO_BUSQUEDA_PILOTO;
                esPiloto = true;
            }
            ibModulosDTO = ibModulosFacade.consultaModulosTransacciones(idUsuario, idCanal, codEstatus, esPiloto);

            if (ibModulosDTO.getRespuesta().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO) && ibModulosDTO.getIbModulos() != null && !ibModulosDTO.getIbModulos().isEmpty()) {
                //consultamos los submenus para cada modulo
                for (IbModulos modulo : ibModulosDTO.getIbModulos()) {
                    ibTransacciones = new IbTransaccionesDTO();
                    ibTransacciones = ibTransaccionesFacade.consultaModulosTransacciones(nombreCanal, codEstatus, modulo.getId());
                    if (ibTransacciones != null) {
                        modulo.setIbTransaccionesCollection(ibTransacciones.getTransaccciones());
                    }
                    modulosTemp.add(modulo);

                }
                ibModulosDTO.setIbModulos(modulosTemp);
            } else {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO consultarModulosPorUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultarModulosPorUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        } finally {
            ibModulosDTO.setRespuesta(respuesta);
        }
//        if (ibModulosDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultarModulosPorUsuario: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return ibModulosDTO;
    }

    /**
     * Metodo para obtener el modelo y la transaccion de los que tengan el posee beneficiario activo
     * @param nombreCanal String ID del canal
     * @return IbModulosDTO
     */
    @Override
    public IbModulosDTO obtenerModeloTransBeneficiario(String nombreCanal) {
        IbModulosDTO ibModulosDTO = new IbModulosDTO();
        List<IbModulos> modulosTemp = new ArrayList<>();
        RespuestaDTO respuesta = new RespuestaDTO();
        IbTransaccionesDTO ibTransacciones;
        try {
            ibModulosDTO = ibModulosFacade.obtenerModeloTransBeneficiario(nombreCanal);

            if (ibModulosDTO.getRespuesta().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO) && ibModulosDTO.getIbModulos() != null && !ibModulosDTO.getIbModulos().isEmpty()) {
                //consultamos los submenus paar cada modulo
                for (IbModulos modulo : ibModulosDTO.getIbModulos()) {
                    ibTransacciones = ibTransaccionesFacade.obtenerModeloTransBeneficiario(modulo.getId(), nombreCanal);
                    if (ibTransacciones != null) {
                        modulo.setIbTransaccionesCollection(ibTransacciones.getTransaccciones());
                    }
                    modulosTemp.add(modulo);

                }
                ibModulosDTO.setIbModulos(modulosTemp);
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO obtenerModeloTransBeneficiario: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            ibModulosDTO.setRespuesta(respuesta);
        }
//        if (ibModulosDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerModeloTransBeneficiario: ")
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return ibModulosDTO;
    }
    
    /**
     * Método para obtener todos los módulos y transacciones asociadas a un canal en específico
     * esten activos o inactivos, visibles o no visibles
     * @author wilmer.rondon
     * @param idCanal canal del cual se desea obtener los modulos y las transacciones
     * @param nombreCanal nombre del canal para efectos de los logs
     * @return IbModulosDTO
     */
    @Override
    public IbModulosDTO obtenerTodosModulosYTransacciones(String idCanal, String nombreCanal) {
        IbModulosDTO ibModulosDTO = new IbModulosDTO();
        List<IbModulos> modulosTemp = new ArrayList<IbModulos>();
        RespuestaDTO respuesta = new RespuestaDTO();
        IbTransaccionesDTO ibTransacciones = null;
        try {
            ibModulosDTO = ibModulosFacade.obtenerTodosModulos(idCanal, nombreCanal);

            if (ibModulosDTO.getRespuesta().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO) && ibModulosDTO.getIbModulos() != null && !ibModulosDTO.getIbModulos().isEmpty()) {
                //consultamos los submenus paar cada modulo
                for (IbModulos modulo : ibModulosDTO.getIbModulos()) {
                    ibTransacciones = new IbTransaccionesDTO();
                    ibTransacciones = ibTransaccionesFacade.obtenerTodasTransacciones(modulo.getId(), nombreCanal);
                    if (ibTransacciones != null) {
                        modulo.setIbTransaccionesCollection(ibTransacciones.getTransaccciones());
                    }
                    modulosTemp.add(modulo);

                }
                ibModulosDTO.setIbModulos(modulosTemp);
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO obtenerTodosModulosYTransacciones: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        } finally {
            ibModulosDTO.setRespuesta(respuesta);
        }
//        if (ibModulosDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerTodosModulosYTransacciones: ")
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return ibModulosDTO;
    }
    
                }
