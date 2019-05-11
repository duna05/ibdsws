/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IBAfiliacionDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IBAfiliacionesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbAfiliacionesFacade;
import com.bds.ws.facade.IbUsuariosFacade;
import com.bds.ws.model.IbAfiliaciones;
import com.bds.ws.model.IbUsuarios;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.TEXTO_SIN_RESULTADOS_JPA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz IbAfiliacionDAO
 *
 * @author juan.faneite
 */
@Named("IbAfiliacionDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbAfiliacionDAOImpl extends DAOUtil implements IBAfiliacionDAO {

    @EJB
    private IbAfiliacionesFacade ibAfiliacionesFacade;

    @EJB
    private IbUsuariosFacade ibUsuariosFacade;

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbAfiliacionDAOImpl.class.getName());

    /**
     * Metodo que Obtiene el listado de afiliados de un cliente por operacion.
     *
     * @param usuario String Referencia foranea al usuario dueno de la
     * afiliacion.
     * @param idTransaccion String ID de el tipo de transaccion.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IBAfiliacionesDTO Los datos de las afiliacion por una operacion
     */
    @Override
    public IBAfiliacionesDTO obtenerListadoAfiliadosPorOperacion(String usuario, String idTransaccion, String idCanal, String nombreCanal) {
        IBAfiliacionesDTO afiliacionesDTO = new IBAfiliacionesDTO();
        try {
            afiliacionesDTO = ibAfiliacionesFacade.obtenerListadoAfiliadosPorOperacion(usuario, idTransaccion, idCanal);
            if (!afiliacionesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerListadoAfiliadosPorOperacion: ")
                    .append("USR-").append(usuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            afiliacionesDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (afiliacionesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerListadoAfiliadosPorOperacion: ")
//                    .append("USR-").append(usuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return afiliacionesDTO;
    }

    /**
     * Metodo que Obtiene el listado de afiliados de un cliente por operacion.
     *
     * @param nroIdentidad String Numero de CI del usuario dueno. (Ordenante)
     * @param codUsuario String Codigo del usuario
     * @param idTransaccion String ID de el tipo de transaccion.
     * @param tipoTransf Indica el tipo de transaccion
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IBAfiliacionesDTO Las afiliaciones de un cliente
     */
    @Override
    public IBAfiliacionesDTO afiliacionesOperacionCodUsuario(String nroIdentidad, String codUsuario, String idTransaccion, String tipoTransf, String nombreCanal) {
        IBAfiliacionesDTO afiliacionesDTO = new IBAfiliacionesDTO();
        try {
            afiliacionesDTO = ibAfiliacionesFacade.afiliacionesOperacionCodUsuario(nroIdentidad, codUsuario, idTransaccion, tipoTransf, nombreCanal);
            if (!afiliacionesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN afiliacionesOperacionCodUsuario: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            afiliacionesDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (afiliacionesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN afiliacionesOperacionCodUsuario: ")
//                    .append("USR-").append(codUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return afiliacionesDTO;
    }

    /**
     * Metodo para obtener el afiliado por codigo de usuario y Id de Usuario
     *
     * @param idAfiliacion id de la afiliacion
     * @param nombreCanal String ID del canal
     * @return IBAfiliacionesDTO -> afiliacion Seleccionada
     */
    @Override
    public IBAfiliacionesDTO obtenerAfiliacionPorId(String idAfiliacion, String nombreCanal) {
        IBAfiliacionesDTO afiliacionesDTO = new IBAfiliacionesDTO();
        afiliacionesDTO.setRespuesta(new RespuestaDTO());
        IbAfiliaciones afiliaciones;
        RespuestaDTO respuesta = new RespuestaDTO();
        try {

            if (idAfiliacion == null || idAfiliacion.isEmpty() || idAfiliacion.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                afiliacionesDTO.getRespuesta().setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            afiliaciones = ibAfiliacionesFacade.find(new BigDecimal(idAfiliacion));            
             
            if (afiliaciones == null) {
                afiliacionesDTO.setIbAfiliacion(new IbAfiliaciones());
                throw new NoResultException();
            } else {
                afiliacionesDTO.setIbAfiliacion(afiliaciones);
            }
            
        } catch (NoResultException e) {            
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN obtenerAfiliacionPorId: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerAfiliacionPorId: ")
                    .append("IDAFIL-").append(idAfiliacion)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }finally{
            afiliacionesDTO.setRespuesta(respuesta);
        }
//        if (afiliacionesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerAfiliacionPorId: ")
//                    .append("IDAFIL-").append(idAfiliacion)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return afiliacionesDTO;
    }

    /**
     * Metodo para deshabilitar el afiliado por codigo de usuario y Id de
     * Usuario
     *
     * @param rafaga
     * @param nombreCanal String ID del canal
     * @return RespuestaDTO
     */
    @Override
    public RespuestaDTO deshabilitarAfiliacion(String rafaga, String separador, String nombreCanal) {
        IBAfiliacionesDTO afiliacionesDTO = new IBAfiliacionesDTO();
        try {
            if (rafaga == null || rafaga.isEmpty() || rafaga.equalsIgnoreCase("")
                    || separador == null || separador.isEmpty() || separador.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                throw new DAOException("DAO008");
            }

            String[] listIdAfiliaciones = rafaga.split(separador);

            for (int i = 0; i < listIdAfiliaciones.length; i++) {

                afiliacionesDTO = this.obtenerAfiliacionPorId(listIdAfiliaciones[i], nombreCanal);

                if (!afiliacionesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception();
                } else {
                    afiliacionesDTO.getIbAfiliacion().setEstatus('I');
                    afiliacionesDTO.setRespuesta(ibAfiliacionesFacade.actualizar(afiliacionesDTO.getIbAfiliacion()));
                }
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN deshabilitarAfiliacion: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            afiliacionesDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (afiliacionesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN deshabilitarAfiliacion: ")
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return afiliacionesDTO.getRespuesta();
    }

    /**
     * Metodo para actualizar una afiliacion
     *
     * @param idAfiliacion id de la afiliacion
     * @param alias alias de la afiliacion
     * @param email email del beneficiario
     * @param montoMax monto maximo
     * @param nombreBenef nombre del beneficiario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o
     * no
     */
    @Override
    public RespuestaDTO actualizarAfiliacion(String idAfiliacion, String alias, char tipoDoc, String documento, String email, String montoMax, String nombreBenef, String nombreBanco, String nroCtaTDC, String nombreCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        IBAfiliacionesDTO afiliacionesDTO;
        try {

            if (idAfiliacion == null || idAfiliacion.isEmpty() || idAfiliacion.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");//revisar el log
                throw new DAOException("DAO008");
            }

            afiliacionesDTO = this.obtenerAfiliacionPorId(idAfiliacion, nombreCanal);
            if (afiliacionesDTO.getRespuesta().getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                if (alias != null && !alias.isEmpty() && !alias.trim().equalsIgnoreCase("")) {
                    afiliacionesDTO.getIbAfiliacion().setAlias(alias);
                }
                if (Character.toString(tipoDoc) != null && !Character.toString(tipoDoc).isEmpty() && !Character.toString(tipoDoc).equalsIgnoreCase("")) {
                    afiliacionesDTO.getIbAfiliacion().setTipoDoc(tipoDoc);
                }
                if (documento != null && !documento.isEmpty() && !documento.trim().equalsIgnoreCase("")) {
                    afiliacionesDTO.getIbAfiliacion().setDocumento(documento);
                }
                if (email != null && !email.isEmpty() && !email.trim().equalsIgnoreCase("")) {
                    afiliacionesDTO.getIbAfiliacion().setEmail(email);
                }
                if (nombreBenef != null && !nombreBenef.isEmpty() && !nombreBenef.trim().equalsIgnoreCase("")) {
                    afiliacionesDTO.getIbAfiliacion().setNombreBeneficiario(nombreBenef);
                }
                if (nombreBanco != null && !nombreBanco.isEmpty() && !nombreBanco.trim().equalsIgnoreCase("")) {
                    afiliacionesDTO.getIbAfiliacion().setNombreBanco(nombreBanco);
                }
                if (nroCtaTDC != null && !nroCtaTDC.isEmpty() && !nroCtaTDC.trim().equalsIgnoreCase("")) {
                    afiliacionesDTO.getIbAfiliacion().setNumeroCuenta(nroCtaTDC);
                }
                if (montoMax != null && !montoMax.isEmpty() && !montoMax.trim().equalsIgnoreCase("")) {
                    afiliacionesDTO.getIbAfiliacion().setMontoMaximo(new BigDecimal(montoMax));
                }

                respuesta = ibAfiliacionesFacade.actualizar(afiliacionesDTO.getIbAfiliacion());
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception();
                }
            } else {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN actualizarAfiliacion: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA actualizarAfiliacion: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN actualizarAfiliacion: ")
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;
    }

    /**
     * Metodo para verificar que no ingrese un alias existente
     *
     * @param codUsuario codigo del usuario
     * @param alias alias del beneficiario
     * @param nombreCanal nombre del canal
     * @return UtilDTO verifica el alias de un usuarios
     */
    @Override
    public UtilDTO verificarAlias(String codUsuario, String alias, String nombreCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        try {

            if (codUsuario == null || codUsuario.isEmpty() || codUsuario.equalsIgnoreCase("")
                    || alias == null || alias.isEmpty() || alias.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            utilDTO = ibAfiliacionesFacade.verificarAlias(codUsuario, alias, nombreCanal);

            if (!utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA verificarAlias: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN verificarAlias: ")
//                    .append("USR-").append(codUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        utilDTO.setRespuesta(respuesta);
        return utilDTO;
    }

    /**
     * Metodo para insertar una afiliacion
     *
     * @param codUsuario codigo del usuario
     * @param alias alias del beneficiario
     * @param tipoDoc tipo de documento V o J
     * @param documento cedula o rif
     * @param email email del beneficiario
     * @param idTran id de la transaccion
     * @param montoMax monto maximo
     * @param nombreBanco nombre del banco
     * @param nombreBenef nombre del beneficiario
     * @param nroCtaTDC numero de cuenta o tdc
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o
     * no
     */
    @Override
    public RespuestaDTO insertarAfiliacion(String codUsuario, String alias, char tipoDoc, String documento, String email, String idTran, String montoMax, String nombreBanco, String nombreBenef, String nroCtaTDC, String nombreCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        IBAfiliacionesDTO afiliacionesDTO = new IBAfiliacionesDTO();
        afiliacionesDTO.setIbAfiliacion(new IbAfiliaciones());
        try {

            if (codUsuario == null || codUsuario.isEmpty() || codUsuario.equalsIgnoreCase("")
                    || alias == null || alias.isEmpty() || alias.equalsIgnoreCase("")
                    || Character.toString(tipoDoc) == null || Character.toString(tipoDoc).isEmpty() || Character.toString(tipoDoc).trim().equalsIgnoreCase("")
                    || documento == null || documento.isEmpty() || documento.equalsIgnoreCase("")
                    || email == null || email.isEmpty() || email.equalsIgnoreCase("")
                    || idTran == null || idTran.isEmpty() || idTran.equalsIgnoreCase("")
                    || montoMax == null || montoMax.isEmpty() || montoMax.equalsIgnoreCase("")
                    || nombreBanco == null || nombreBanco.isEmpty() || nombreBanco.equalsIgnoreCase("")
                    || nombreBenef == null || nombreBenef.isEmpty() || nombreBenef.equalsIgnoreCase("")
                    || nroCtaTDC == null || nroCtaTDC.isEmpty() || nroCtaTDC.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                throw new DAOException("DAO008");
            }

            IbUsuarios ibUsuarios = ibUsuariosFacade.ibUsuarioPorCodUsuario(codUsuario, nombreCanal).getUsuario();

            if (ibUsuarios == null ) {
                throw new Exception();
            }

            afiliacionesDTO.getIbAfiliacion().setId(BigDecimal.ZERO);
            afiliacionesDTO.getIbAfiliacion().setIdUsuario(ibUsuarios);
            afiliacionesDTO.getIbAfiliacion().setCodUsuario(codUsuario);
            afiliacionesDTO.getIbAfiliacion().setAlias(alias);
            afiliacionesDTO.getIbAfiliacion().setTipoDoc(tipoDoc);
            afiliacionesDTO.getIbAfiliacion().setDocumento(documento);
            afiliacionesDTO.getIbAfiliacion().setEmail(email);
            afiliacionesDTO.getIbAfiliacion().setEstatus('A');
            afiliacionesDTO.getIbAfiliacion().setFechaModificacion(new Date());
            afiliacionesDTO.getIbAfiliacion().setFechaRegistro(new Date());

            afiliacionesDTO.getIbAfiliacion().setIdTransaccion(new BigDecimal(idTran));
            afiliacionesDTO.getIbAfiliacion().setMontoMaximo(new BigDecimal(montoMax));
            afiliacionesDTO.getIbAfiliacion().setNombreBanco(nombreBanco);
            afiliacionesDTO.getIbAfiliacion().setNombreBeneficiario(nombreBenef);
            afiliacionesDTO.getIbAfiliacion().setNumeroCuenta(nroCtaTDC);

            respuesta = ibAfiliacionesFacade.crear(afiliacionesDTO.getIbAfiliacion());

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO insertarAfiliacion: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN insertarAfiliacion: ")
//                    .append("USR-").append(codUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return respuesta;
    }

    @Override
    public UtilDTO verificarProducto(String codUsuario, String producto, String nombreCanal) {
      RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        try {

            if (codUsuario == null || codUsuario.isEmpty() || codUsuario.equalsIgnoreCase("")
                    || producto == null || producto.isEmpty() || producto.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            utilDTO = ibAfiliacionesFacade.verificarProducto(codUsuario, producto, nombreCanal);

            if (!utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA verificarProducto: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN verificarProducto: ")
//                    .append("USR-").append(codUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        utilDTO.setRespuesta(respuesta);
        return utilDTO;  
    }

}
