/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbUsuariosP2PDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbUsuariosP2PDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbBeneficiariosP2pFacade;
import com.bds.ws.facade.IbTextosFacade;
import com.bds.ws.facade.IbUsuariosFacade;
import com.bds.ws.facade.IbUsuariosP2pFacade;
import com.bds.ws.model.IbUsuarios;
import com.bds.ws.model.IbUsuariosP2p;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("IbUsuariosP2PDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbUsuariosP2PDAOImpl extends DAOUtil implements IbUsuariosP2PDAO {

    private static final Logger logger = Logger.getLogger(IbUsuariosP2PDAOImpl.class.getName());

    @EJB
    private IbUsuariosP2pFacade ibUsuariosP2PFacade;
    
    @EJB
    private IbBeneficiariosP2pFacade ibBeneficiariosP2pFacade;

    @EJB
    private IbUsuariosFacade ibUsuariosFacade;

    @EJB
    private IbTextosFacade textosFacade;

    /**
     * inserta la afiliacion de un usuario al servicio P2P
     *
     * @param idUsuario identificador del usuario en Ora11
     * @param nroTelefono numero de telefono asociado al servicio
     * @param tipoDocumento tipo de doc del usuario asociado al servicio
     * @param nroDocumento numero de doc del usuario asociado al servicio
     * @param email correo electronico del usuario asociado al servicio
     * @param nroCuenta numero de cuenta asociada al servicio
     * @param mtoMaxTransaccion monto maximo de pago por operacion
     * @param mtoMaxDiario monto maximo acumlado diarios de pagos
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o
     * no
     */
    @Override
    public RespuestaDTO insertarUsuarioP2P(String idUsuario, String nroTelefono, String tipoDocumento, String nroDocumento, String email,
            String nroCuenta, BigDecimal mtoMaxTransaccion, BigDecimal mtoMaxDiario, String idCanal, String nombreCanal) {
        //logger.log(Level.INFO, new StringBuilder("insertarUsuarioP2P idUsuario:").append(idUsuario).append(":").toString());
        
        RespuestaDTO respuesta = new RespuestaDTO();
        IbUsuariosP2PDTO ibUsuariosP2PDTO = new IbUsuariosP2PDTO();
        IbUsuariosP2p usuarioP2P = new IbUsuariosP2p();
        try {
            ibUsuariosP2PDTO = ibUsuariosP2PFacade.consultaUsuarioP2PxTelefono(nroTelefono, nombreCanal);

            if (ibUsuariosP2PDTO.getRespuesta().getCodigoSP().equals(CODIGO_SIN_RESULTADOS_JPA) || ibUsuariosP2PDTO.getUsuarioP2p().getEstatus().charValue() == 'I') {        
                usuarioP2P.setIdUsuario(new IbUsuarios(new BigDecimal(idUsuario)));
                //SE MODIFICA LA FORMA COMO SE SETEA EL USUARIO
                //usuarioP2P.setIdUsuario(ibUsuariosFacade.find(new BigDecimal(idUsuario)));
                usuarioP2P.setEmail(email);
                usuarioP2P.setMtoMaxDiario(mtoMaxDiario);
                usuarioP2P.setMtoMaxTransaccion(mtoMaxTransaccion);
                usuarioP2P.setNroCuenta(nroCuenta);
                usuarioP2P.setTipoDocumento(tipoDocumento);
                usuarioP2P.setNroDocumento(nroDocumento);
                usuarioP2P.setNroTelefono(nroTelefono);
                usuarioP2P.setFechaCreacion(new Date());
                usuarioP2P.setFechaModificacion(new Date());
                usuarioP2P.setEstatus('A');
            } else if (ibUsuariosP2PDTO.getUsuarioP2p().getEstatus().charValue() == 'A') {
                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDA_SP);
                return respuesta;
            }
            if (ibUsuariosP2PDTO.getRespuesta().getCodigoSP().equals(CODIGO_SIN_RESULTADOS_JPA)) {
                usuarioP2P.setId(BigDecimal.ZERO);//seteo de id por defcto en BD se reemplaza por la secuencia
                respuesta = ibUsuariosP2PFacade.crear(usuarioP2P);
            } else {
                usuarioP2P.setId(ibUsuariosP2PDTO.getUsuarioP2p().getId());
                respuesta = ibUsuariosP2PFacade.modificarPj(usuarioP2P);
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO insertarUsuarioP2P: ")
                    .append("USR-").append(idUsuario)
                    .append("-TLF-").append(nroTelefono)
                    .append("-CTA-").append(nroCuenta)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
        return respuesta;
    }

    /**
     * Metodo que realiza la consulta de afiliaciones Activas para el serv P2P
     * por usuario.
     *
     * @param idUsuario identificador del usuario en Ora11
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o
     * no
     */
    @Override
    public IbUsuariosP2PDTO consultarUsuarioP2P(String idUsuario, String idCanal, String nombreCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        IbUsuariosP2PDTO usuariosP2PDTO = new IbUsuariosP2PDTO();
        try {
            usuariosP2PDTO = ibUsuariosP2PFacade.consultaUsuarioP2P(idUsuario, nombreCanal);
            usuariosP2PDTO = ibBeneficiariosP2pFacade.consultarBeneficiariosP2P(usuariosP2PDTO, idUsuario, nombreCanal);
            
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultarUsuarioP2P: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (usuariosP2PDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultarUsuarioP2P: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return usuariosP2PDTO;
    }

    /**
     * *
     * Metodo que realiza la validacion de los datos a afiliar al servicio P2P
     *
     * @param nroTelf
     * @param nroCta
     * @param idUsuario id del usuario
     * @param nombreCanal codigo del canal
     * @return UtilDTO
     */
    @Override
    public UtilDTO validarAfiliacionP2P(String nroTelf, String nroCta, String idUsuario, String nombreCanal) {
        UtilDTO util = new UtilDTO();
        try {
            util = ibUsuariosP2PFacade.validarAfiliacionP2P(nroTelf, nroCta, idUsuario, nombreCanal);
            if (util != null && util.getRespuesta() != null && !util.getRespuesta().getCodigoSP().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                if (util.getRespuesta().getCodigoSP().equalsIgnoreCase(CODIGO_EXCEPCION_P2P_TELF_DUP)) {
                    util.getRespuesta().setDescripcionSP(textosFacade.textoParametro("error.p2p.telfDuplicado", nombreCanal));
                } else {
                    if (util.getRespuesta().getCodigoSP().equalsIgnoreCase(CODIGO_EXCEPCION_P2P_CTA_DUP)) {
                        util.getRespuesta().setDescripcionSP(textosFacade.textoParametro("error.p2p.ctaDuplicado", nombreCanal));
                    }
                }
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO validarAfiliacionP2P: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            util.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (util.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarAfiliacionP2P: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return util;
    }

    /**
     * *
     * Metodo que realiza la validacion de los datos a editar una afiliacion al
     * servicio P2P
     *
     * @param idAfiliacion identificador de la afiliacion a modificar
     * @param nroTelf numero de telf a afiliar
     * @param nroCta numero de cta a afiliar
     * @param idUsuario id del usuario
     * @param nombreCanal codigo del canal
     * @return UtilDTO
     */
    @Override
    public UtilDTO validarEdicionAfiliacionP2P(String idAfiliacion, String nroTelf, String nroCta, String idUsuario, String nombreCanal) {
        UtilDTO util = new UtilDTO();
        try {
            util = ibUsuariosP2PFacade.validarEdicionAfiliacionP2P(idAfiliacion, nroTelf, nroCta, idUsuario, nombreCanal);
            if (util != null && util.getRespuesta() != null && !util.getRespuesta().getCodigoSP().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                if (util.getRespuesta().getCodigoSP().equalsIgnoreCase(CODIGO_EXCEPCION_P2P_TELF_DUP)) {
                    util.getRespuesta().setDescripcionSP(textosFacade.textoParametro("error.p2p.telfDuplicado", nombreCanal));
                } else {
                    if (util.getRespuesta().getCodigoSP().equalsIgnoreCase(CODIGO_EXCEPCION_P2P_CTA_DUP)) {
                        util.getRespuesta().setDescripcionSP(textosFacade.textoParametro("error.p2p.ctaDuplicado", nombreCanal));
                    }
                }
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO validarEdicionAfiliacionP2P: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            util.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (util.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarEdicionAfiliacionP2P: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return util;
    }

    /**
     * edita la afiliacion de un usuario al servicio P2P
     *
     * @param idAfiliacion identificador de la afiliacion a actualizar
     * @param nroTelefono numero de telefono asociado al servicio
     * @param email correo electronico del usuario asociado al servicio
     * @param nroCuenta numero de cuenta asociada al servicio
     * @param mtoMaxTransaccion monto maximo de pago por operacion
     * @param mtoMaxDiario monto maximo acumlado diarios de pagos
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o
     * no
     */
    @Override
    public RespuestaDTO editarUsuarioP2P(String idAfiliacion, String nroTelefono, String email,
            String nroCuenta, BigDecimal mtoMaxTransaccion, BigDecimal mtoMaxDiario, String idCanal, String nombreCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            IbUsuariosP2p usuarioP2P = ibUsuariosP2PFacade.find(new BigDecimal(idAfiliacion));
            usuarioP2P.setEmail(email);
            usuarioP2P.setMtoMaxDiario(mtoMaxTransaccion);
            usuarioP2P.setMtoMaxTransaccion(mtoMaxTransaccion);
            usuarioP2P.setNroCuenta(nroCuenta);
            usuarioP2P.setNroTelefono(nroTelefono);
            usuarioP2P.setFechaModificacion(new Date());

            respuesta = ibUsuariosP2PFacade.actualizar(usuarioP2P);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO editarUsuarioP2P: ")
                    .append("AFI-").append(idAfiliacion)
                    .append("-TLF-").append(nroTelefono)
                    .append("-CTA-").append(nroCuenta)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN editarUsuarioP2P: ")
//                    .append("AFI-").append(idAfiliacion)
//                    .append("-TLF-").append(nroTelefono)
//                    .append("-CTA-").append(nroCuenta)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return respuesta;
    }

    /**
     * desafilia la afiliacion de un usuario al servicio P2P
     *
     * @param idAfiliacion identificador de la afiliacion a actualizar
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o
     * no
     */
    @Override
    public RespuestaDTO desafiliarUsuarioP2P(String idAfiliacion, String idCanal, String nombreCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            IbUsuariosP2p usuarioP2P = ibUsuariosP2PFacade.find(new BigDecimal(idAfiliacion));
            usuarioP2P.setEstatus('I');
            usuarioP2P.setFechaModificacion(new Date());
            respuesta = ibUsuariosP2PFacade.actualizar(usuarioP2P);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO desafiliarUsuarioP2P: ")
                    .append("AFI-").append(idAfiliacion)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN desafiliarUsuarioP2P: ")
//                    .append("AFI-").append(idAfiliacion)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;
    }

}
