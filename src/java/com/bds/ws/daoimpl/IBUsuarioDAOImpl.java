/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbUsuarioDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbUsuarioDTO;
import com.bds.ws.dto.IbUsuariosCanalesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbUsuariosCanalesFacade;
import com.bds.ws.facade.IbUsuariosFacade;
import com.bds.ws.model.IbPerfiles;
import com.bds.ws.model.IbUsuarios;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz IbUsuarioDAO
 *
 * @author juan.faneite
 */
@Named("IbUsuarioDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IBUsuarioDAOImpl extends DAOUtil implements IbUsuarioDAO {

    @EJB
    IbUsuariosCanalesFacade usuariosCanalesFacade;

    @EJB
    IbUsuariosFacade usuariosFacade;

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IBUsuarioDAOImpl.class.getName());

    /**
     * Metodo que obtiene un Usuario por canal
     *
     * @param idUsuario String id del usuario.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbUsuariosCanalesDTO Obtiene los usuarios por canal
     */
    @Override
    public IbUsuariosCanalesDTO obtenerIbUsuarioPorCanal(String idUsuario, String idCanal, String nombreCanal) {
        IbUsuariosCanalesDTO usuariosCanalesDTO = new IbUsuariosCanalesDTO();
        try {
            usuariosCanalesDTO = usuariosCanalesFacade.consultaUsuarioCanal(idUsuario, idCanal);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerIbUsuarioPorCanal: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            usuariosCanalesDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (usuariosCanalesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerIbUsuarioPorCanal: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return usuariosCanalesDTO;
    }

    /**
     * Metodo que se encarga de registrar los datos de un cliente en la BD
     * Oracle 11
     *
     * @param codigoCliente codigo de cliente en DelSur
     * @param nroTDD numero de TDD afiliada
     * @param tipoDoc tipo de documento V,E,J
     * @param nroDoc numero de documento de indentidad
     * @param email email del cliente
     * @param nombreCompleto nombres y apellidos
     * @param telfCelularCompleto codigo y numero de telefono
     * @param idPerfil identificador del perfil
     * @param clave clave de acceso a la aplicacion
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @Override
    public RespuestaDTO insertarDatosIbUsuario(String codigoCliente, String nroTDD, char tipoDoc, String nroDoc,
            String email, String nombreCompleto, String telfCelularCompleto, BigDecimal idPerfil, String clave, String idCanal, String nombreCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            IbUsuarios ibUsuario = new IbUsuarios();
            ibUsuario.setId(BigDecimal.ZERO);//valor por defecto para las secuencias
            IbPerfiles perfil = new IbPerfiles();
            perfil.setId(idPerfil);
            ibUsuario.setIdPerfil(perfil);
            ibUsuario.setCodUsuario(codigoCliente);
            ibUsuario.setTdd(nroTDD);
            ibUsuario.setDocumento(nroDoc);//para que no tome el tipo de documento
            ibUsuario.setTipoDoc(tipoDoc);//en este caso solo se toma el tipo de documento
            ibUsuario.setEmail(email);
            ibUsuario.setNombre(nombreCompleto);
            ibUsuario.setCelular(telfCelularCompleto);
            ibUsuario.setFechaHoraCreacion(new Date());
            ibUsuario.setFechaHoraModificacion(new Date());
            ibUsuario.setClave(clave);
            usuariosFacade.create(ibUsuario);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarDatosIbUsuario: ")
                    .append("USR-").append(codigoCliente)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN insertarDatosIbUsuario: ")
//                    .append("USR-").append(codigoCliente)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;
    }

    /**
     * Metodo que obtiene un Usuario por codigo de cliente
     *
     * @param codUsuario String Codigo del usuario.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbUsuarioDTO Los datos de un usuario especifico
     */
    @Override
    public IbUsuarioDTO obtenerIbUsuarioPorCodusuario(String codUsuario, String idCanal, String nombreCanal) {
        IbUsuarioDTO ibUsuarioDTO = new IbUsuarioDTO();
        try {
            ibUsuarioDTO = usuariosFacade.ibUsuarioPorCodUsuario(codUsuario, nombreCanal);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerIbUsuarioPorCanal: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            ibUsuarioDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (ibUsuarioDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerIbUsuarioPorCanal: ")
//                    .append("USR-").append(codUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return ibUsuarioDTO;
    }

    /**
     * Metodo que obtiene un Usuario por codigo de cliente
     *
     * @param tdd numero de TDD
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbUsuarioDTO Los datos de un usuario por su TDD
     */
    @Override
    public IbUsuarioDTO obtenerIbUsuarioPorTDD(String tdd, String idCanal, String nombreCanal) {
        IbUsuarioDTO ibUsuarioDTO = new IbUsuarioDTO();
        try {
            ibUsuarioDTO = usuariosFacade.ibUsuarioPorTDD(tdd, nombreCanal);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerIbUsuarioPorTDD: ")
                    .append("TDD-").append(this.numeroCuentaFormato(tdd))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            ibUsuarioDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (ibUsuarioDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerIbUsuarioPorTDD: ")
//                    .append("TDD-").append(this.numeroCuentaFormato(tdd))
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return ibUsuarioDTO;
    }

    /**
     * Metodo para actualizar los datos del usuario del core a la bd del IB
     *
     * @param codigoCliente codigo de cliente de oracle 9
     * @param nroTDD numero de TDD
     * @param tipoDoc tipo de Doc P,E,V etc
     * @param nroDoc numero de documento
     * @param email correo del usuario
     * @param nombreCompleto nombres del usuario
     * @param telfCelularCompleto telefono con codigo completo ej. 04141234567
     * @param clave clave de acceso a la aplicacion
     * @param nombreCanal codigo ext del canal
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o
     * no
     */
    @Override
    public RespuestaDTO actualizarDatosUsuario(String codigoCliente, String nroTDD, char tipoDoc, String nroDoc,
            String email, String nombreCompleto, String telfCelularCompleto, String clave, String nombreCanal) {
        IbUsuarios modificado = new IbUsuarios();
        IbUsuarios usuario;
        IbUsuarioDTO usuarioDTO;
        RespuestaDTO respuesta = new RespuestaDTO();
        try {

            if (codigoCliente == null || codigoCliente.isEmpty() || codigoCliente.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                throw new DAOException("DAO008");
            }

            usuarioDTO = usuariosFacade.ibUsuarioPorCodUsuario(codigoCliente, nombreCanal);

            if (usuarioDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                usuario = usuarioDTO.getUsuario();

                if (telfCelularCompleto != null && !telfCelularCompleto.isEmpty()) {
                    usuario.setCelular(telfCelularCompleto);
                }
                if (nroDoc != null && !nroDoc.isEmpty()) {
                    usuario.setDocumento(nroDoc);
                }
                if (email != null && !email.isEmpty()) {
                    usuario.setEmail(email);
                }
                if (nombreCompleto != null && !nombreCompleto.isEmpty()) {
                    usuario.setNombre(nombreCompleto);
                }
                if (nroTDD != null && !nroTDD.isEmpty()) {
                    usuario.setTdd(nroTDD);
                }
                if (String.valueOf(tipoDoc) != null && !String.valueOf(tipoDoc).trim().isEmpty()) {
                    usuario.setTipoDoc(tipoDoc);
                }
                if (clave != null && !clave.isEmpty()) {
                    usuario.setClave(clave);
                }

                modificado = usuariosFacade.modificar(usuario);
            } else {
                respuesta = usuarioDTO.getRespuesta();
            }

        } catch (DAOException e) {
            logger.error( new StringBuilder("ERROR DAO EN actualizarDatosUsuario: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo("DAO008");//revisar el log
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN actualizarDatosUsuario: ")
                    .append("USR-").append(codigoCliente)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN actualizarDatosUsuario: ")
//                    .append("USR-").append(codigoCliente)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;
    }

    /**
     * Metodo para consultar la cantidad de intentos fallidos de preguntas de
     * seguridad
     *
     * @param idUsuario identificador del usuario
     * @param nroTDD nro de TDD del usuario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @Override
    public UtilDTO cantidadPregSegFallidas(String idUsuario, String nroTDD, String nombreCanal) {
        UtilDTO util = new UtilDTO();
        try {
            util = usuariosFacade.cantPreguntasSeguridadFallidas(idUsuario, nroTDD, nombreCanal);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN cantPreguntasSeguridadFallidas: ")
                    .append("USR-").append(idUsuario)
                    .append("TDD-").append(this.numeroCuentaFormato(nroTDD))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            util.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (util.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN cantPreguntasSeguridadFallidas: ")
//                    .append("USR-").append(idUsuario)
//                    .append("TDD-").append(this.numeroCuentaFormato(nroTDD))
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return util;
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
    @Override
    public RespuestaDTO actualizarPregSegFallidas(String idUsuario, int cantIntentos, String nombreCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            respuesta = usuariosFacade.actualizarCantIntentosFallidosPregSeg(idUsuario, cantIntentos, nombreCanal);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN actualizarCantIntentosFallidosPregSeg: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN actualizarCantIntentosFallidosPregSeg: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;
    }
    
    /**
     * Metodo para consultar la la TDD de un usuario por su Documento de Identidad
     *
     * @param tipoDoc tipo de documento
     * @param nroDoc nro de TDD del usuario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @Override
    public UtilDTO obtenerTDDPorDoc(String tipoDoc, String nroDoc, String nombreCanal){
        UtilDTO util = new UtilDTO();
        try {
            util = usuariosFacade.obtenerTDDPorDoc(tipoDoc, nroDoc, nombreCanal);

        } catch (Exception e) { 
            logger.error( new StringBuilder("ERROR DAO EN obtenerTDDPorDoc: ")
                    .append("TDOC-").append(tipoDoc)
                    .append("-NDOC-").append(nroDoc)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            util.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (util.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerTDDPorDoc: ")
//                    .append("TDOC-").append(tipoDoc)
//                    .append("-NDOC-").append(nroDoc)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return util;
    }

}
