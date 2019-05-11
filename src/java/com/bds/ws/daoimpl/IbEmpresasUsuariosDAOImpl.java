/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.EMailDAO;
import com.bds.ws.dao.IbEmpresasUsuariosDAO;
import com.bds.ws.dao.OtpDAO;
import com.bds.ws.dao.SMSDAO;
import com.bds.ws.dto.IbEmpresasUsuariosPjDTO;
import com.bds.ws.dto.OtpDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusEmpresaEnum;
import com.bds.ws.enumerated.EstatusPlantillasEmail;
import com.bds.ws.facade.IbEmpresasUsuariosPjFacade;
import com.bds.ws.facade.IbTextosFacade;
import com.bds.ws.model.IbEmpresas;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import com.bds.ws.model.IbPerfilesPj;
import com.bds.ws.model.IbUsuariosPj;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 *
 * @author luis.perez
 */
@Named("IbEmpresasUsuariosDAO")
@Stateless
public class IbEmpresasUsuariosDAOImpl extends DAOUtil implements IbEmpresasUsuariosDAO {

    @EJB
    private IbEmpresasUsuariosPjFacade ibEmpresasUsuariosPjFacade;
    @EJB
    private OtpDAO otpDAO;
    @EJB
    private SMSDAO smsDAO;
    @EJB
    private EMailDAO emailDAO;
    @EJB
    private IbTextosFacade ibTextosFacade;
    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbEmpresasUsuariosDAOImpl.class.getName());

    /**
     * Metodo que se encarga de registrar los de la empresa y el usuario Oracle
     * 11
     *
     *
     * @param representanteLegal indica si es representante legal
     * @param idUsuario id del usuario en ib
     * @param idEmpresa identificador de la empresa a la cual pertenece el
     * @param estatusAcceso estatus de acceso
     * @param email email del usuario
     * @param perfilAcceso perfil de acceso del usuario
     * @param estatusRegistro estatus del registro del usuario
     * @param telfCelularCompleto numero de telefono celular completo
     * @param telfOficinaCompleto telefono completo del usuario
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @Override
    public UtilDTO insertarDatosEmpresaUsuario(String idEmpresa, String idUsuario, String email, String telfCelularCompleto, String telfOficinaCompleto, Character representanteLegal, String perfilAcceso, Character estatusAcceso, Character estatusRegistro, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            IbEmpresasUsuariosPj ibEmpresasUsuariosPj = new IbEmpresasUsuariosPj();
            IbEmpresas ibEmpresas = new IbEmpresas();
            IbPerfilesPj ibPerfilesPj = new IbPerfilesPj();

            IbUsuariosPj ibUsuarios = new IbUsuariosPj();
            ibEmpresas.setId(BigDecimal.valueOf(Long.valueOf(idEmpresa)));
            ibUsuarios.setId(BigDecimal.valueOf(Long.valueOf(idUsuario)));
            ibPerfilesPj.setId(BigDecimal.valueOf(Long.valueOf(perfilAcceso)));

            ibEmpresasUsuariosPj.setId(BigDecimal.ZERO);
            ibEmpresasUsuariosPj.setIdEmpresa(ibEmpresas);
            ibEmpresasUsuariosPj.setIdUsuarioPj(ibUsuarios);
            ibEmpresasUsuariosPj.setEmail(email);
            ibEmpresasUsuariosPj.setTelfCelular(telfCelularCompleto);
            ibEmpresasUsuariosPj.setTelfOficina(telfOficinaCompleto);
            ibEmpresasUsuariosPj.setEsRepresentanteLegal(representanteLegal);
            ibEmpresasUsuariosPj.setEstatusAcceso(estatusAcceso);
            ibEmpresasUsuariosPj.setEstatusRegistro(estatusRegistro);
            ibEmpresasUsuariosPj.setPerfilAcceso(ibPerfilesPj);
            ibEmpresasUsuariosPjFacade.crearPJ(ibEmpresasUsuariosPj);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN insertarDatosEmpresaUsuario: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setRespuesta(respuesta);
        }

//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN insertarDatosEmpresaUsuario: ")
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * Metodo que se encarga de registrar los de la empresa y el usuario Oracle
     * 11
     *
     *
     * @param ibEmpresasUsuariosPj objeto de empresausuario completo con datos
     * de empresa, usuario, monto, perfilacceso
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @Override
    public UtilDTO insertarDatosEmpresaUsuario(IbEmpresasUsuariosPj ibEmpresasUsuariosPj, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            ibEmpresasUsuariosPjFacade.crearPJ(ibEmpresasUsuariosPj);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN insertarDatosEmpresaUsuario: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setRespuesta(respuesta);
        }

//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN insertarDatosEmpresaUsuario: ")
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * Metodo que consultar los usuarios con perfil autorizador activos y
     * registrados, asociados a un empresa
     *
     * @param idEmpresa codigo interno del canal
     * @return UtilDTO
     */
    @Override
    public UtilDTO consultarEmpresasUsuariosAprobadores(BigDecimal idEmpresa) {
        UtilDTO util = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        try {
            List<IbEmpresasUsuariosPj> listaUsuarios = this.ibEmpresasUsuariosPjFacade.consultarEmpresasUsuariosAprobadores(idEmpresa);
            if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                resultados.put("ListaAprobadores", listaUsuarios);
            }
            util.setRespuesta(respuesta);
            util.setResulados(resultados);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN insertarDatosEmpresaUsuario: ")
                    .append("-CH- ").append("")
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            util.setRespuesta(respuesta);
        }

//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN insertarDatosEmpresaUsuario: ")
//                    .append("-CH-").append("")
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return util;
    }

    /**
     * Metodo que consultar los usuarios juridicos Oracle 11++
     *
     *
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @param estatusAcceso
     * 1:Activo,2:Bloqueado,3:Inactivo(Vacaciones),4:Eliminado
     * @return UtilDTO
     */
    @Override
    public UtilDTO consultarEmpresasUsuarios(String idEmpresa, String idCanal, String codigoCanal, char estatusAcceso) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO = new IbEmpresasUsuariosPjDTO();
        Map resultado = new HashMap();
        try {
            List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = ibEmpresasUsuariosPjFacade.consultarEmpresasUsuarios(new BigDecimal(idEmpresa), estatusAcceso);
            ibEmpresasUsuariosPjDTO.setIbEmpresasUsuariosPj(ibEmpresasUsuariosPj);
            resultado.put(USUARIOS, ibEmpresasUsuariosPjDTO);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarEmpresasUsuarios: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuestaDTO);
        }
        return utilDTO;
    }

    /**
     * Metodo que consulta un usuario de una empresa
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idEmpresa codigo de la empresa interno en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    @Override
    public UtilDTO consultarEmpresaUsuario(String idUsuario, String idEmpresa, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO = new IbEmpresasUsuariosPjDTO();
        Map resultado = new HashMap();
        try {
            IbEmpresasUsuariosPj ibEmpresaUsuarioPj = ibEmpresasUsuariosPjFacade.consultarEmpresaUsuario(new BigDecimal(idUsuario), new BigDecimal(idEmpresa));
            if (ibEmpresaUsuarioPj == null) {
                throw new NoResultException();
            }
            ibEmpresasUsuariosPjDTO.setIbEmpresaUsuarioPj(ibEmpresaUsuarioPj);
            resultado.put(USUARIO, ibEmpresasUsuariosPjDTO);
        } catch (NoResultException e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarEmpresaUsuario: NoResultException ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-ID_USU- ").append(idUsuario)
                    .append("-ID_EMPRE- ").append(idEmpresa)
                    .append("-ID_CANAL- ").append(idCanal)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuestaDTO.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarEmpresasUsuarios: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuestaDTO);
        }
        return utilDTO;
    }

    /**
     * Metodo que consulta un usuario de una empresa
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    @Override
    public UtilDTO consultarEmpresaUsuario(String idUsuario, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO = new IbEmpresasUsuariosPjDTO();
        Map resultado = new HashMap();
        try {
            //SE BUSCAN SOLO LAS EMPRESAS ACTIVAS
            String estatusAcceso = String.valueOf(EstatusEmpresaEnum.ACTIVO.getId());
            List<IbEmpresasUsuariosPj> ibEmpresaUsuarioPjList = ibEmpresasUsuariosPjFacade.consultarEmpresaUsuario(new BigDecimal(idUsuario), estatusAcceso.charAt(0));
            if (ibEmpresaUsuarioPjList == null) {
                throw new NoResultException();
            }
            ibEmpresasUsuariosPjDTO.setIbEmpresasUsuariosPj(ibEmpresaUsuarioPjList);
            resultado.put(LISTA_EMPRESA_USUARIO, ibEmpresasUsuariosPjDTO);
        } catch (NoResultException e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarEmpresasUsuarios: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuestaDTO.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarEmpresasUsuarios: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuestaDTO);
        }
        return utilDTO;
    }

    /**
     * Metodo que valida el OTP ingresado por un usuario
     *
     *
     * @param idUsuario codigo del usuario
     * @param idEmpresa codigo interno de la empresa en IB
     * @param codigoOTP codigo OTP validar
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    @Override
    public UtilDTO validarOTPRegistroUsuario(String idUsuario, String idEmpresa, String codigoOTP, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        //SE ENVIA EL VALOR 1 PARA INDICAR QUE EL OTP A VALIDAR ES DE REGISTRO DE USUARIO
        String registroUsuario = "1";
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        OtpDTO otpDTO = otpDAO.validarOTPPj(idUsuario, codigoOTP, codigoCanal, registroUsuario);
        Map resultado = new HashMap();
        try {
            //CODIGO DE RESPUESTA EXITOSO
            if (otpDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && otpDTO.getRespuesta().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                //SE ENVIA CORREO Y SMS CUANDO LE QUEDA UNA OPORTUNDIAD PARA REALIZAR LA VALIDACION DEL OTP
                if ((otpDTO.getIntentosRealizados() + 1) == otpDTO.getIntentosMaximosPermitidos()) {
                    IbEmpresasUsuariosPj empresaUsuario = ibEmpresasUsuariosPjFacade.consultarEmpresaUsuario(new BigDecimal(idUsuario), new BigDecimal(idEmpresa));
                    if (empresaUsuario != null) {
                        //SIEMPRE SE ENVIA EL CODIGO 1 AL SP
                        String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
                        String textoSMSPrebloqueo = ibTextosFacade.textoParametro("pjw.global.sms.prebloqueo.otp", idCanal);
                        //ajustar codigo de la plantilla a usar
                        String codigoPlantilla = EstatusPlantillasEmail.REGISTRO_USUARIO_TEXT.getDescripcion();
                        String parametrosCorreo = empresaUsuario.getIdUsuarioPj().getNombre() + "~" + empresaUsuario.getIdEmpresa().getNombre() + "~" + codigoOTP;

                        //SMS PARA REGISTRO DE EMPRESA
                        smsDAO.enviarSMS(empresaUsuario.getTelfCelular(), textoSMSPrebloqueo, codigoCanal);
                        //ENVIAR EMAIL 
                        emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, empresaUsuario.getEmail(), parametrosCorreo, idCanal, codigoCanal);
                    } else {
                        throw new NoResultException();
                    }
                } else if (otpDTO.getIntentosRealizados() >= otpDTO.getIntentosMaximosPermitidos()) {
                    //EL NUMERO DE INTENTOS ES PERMITIDOS HA SIDO ALCANZADO, SE PROCEDE A
                    IbEmpresasUsuariosPj empresaUsuario = ibEmpresasUsuariosPjFacade.consultarEmpresaUsuario(new BigDecimal(idUsuario), new BigDecimal(idEmpresa));
                    if (empresaUsuario != null) {
                        String codigoOTPNew = "";
                        Integer codigoPlantilla = EstatusPlantillasEmail.REGISTRO_USUARIO_TEXT.getId();
                        String parametrosCorreo = empresaUsuario.getIdUsuarioPj().getNombre() + "~" + empresaUsuario.getIdEmpresa().getNombre() + "~" + codigoOTPNew;

                        //SE GENERA EL OTP Y AUTOMATICAMENTE SE ENVIA EL CORREO
                        otpDTO = otpDAO.generarOTPEnviarCorreoPj(idUsuario, codigoPlantilla, empresaUsuario.getEmail(), parametrosCorreo, codigoCanal);
                        if (otpDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                            codigoOTPNew = otpDTO.getOvOTP();
                        }

                        String textoSMSOTP = ibTextosFacade.textoParametro("pjw.global.sms.otp.general", idCanal);
                        textoSMSOTP = textoSMSOTP.replace("$POTP", codigoOTPNew);

                        //SE ENVIE MAIL y SMS CON EL NUEVO OTP
                        smsDAO.enviarSMS(empresaUsuario.getTelfCelular(), textoSMSOTP, codigoCanal);
                    } else {
                        throw new NoResultException();
                    }
                }
            } else {
                respuestaDTO.setCodigo(otpDTO.getRespuesta().getCodigo());
                respuestaDTO.setCodigoSP(otpDTO.getRespuesta().getCodigoSP());
            }
            resultado.put(OTP, otpDTO);
        } catch (NoResultException e) {
            logger.error(new StringBuilder("ERROR JPA EN validarOTPRegistroUsuario: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuestaDTO.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN validarOTPRegistroUsuario: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setRespuesta(respuestaDTO);
            utilDTO.setResulados(resultado);
        }
        return utilDTO;
    }

    /**
     * Metodo que consultar los usuarios juridicos Oracle 11++ por perfil
     *
     *
     * @param perfilAcceso
     * 1:Master,2:Admin,3:Autorizador,4:Ingresador,5:Consultor
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    @Override
    public UtilDTO consultarEmpresasUsuariosPjPerfil(String idEmpresa, String idCanal, String codigoCanal, String perfilAcceso) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO = new IbEmpresasUsuariosPjDTO();
        Map resultado = new HashMap();
        try {
            List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = ibEmpresasUsuariosPjFacade.consultarEmpresasUsuariosPjPerfil(new BigDecimal(idEmpresa), perfilAcceso);
            ibEmpresasUsuariosPjDTO.setIbEmpresasUsuariosPj(ibEmpresasUsuariosPj);
            resultado.put(USUARIOS, ibEmpresasUsuariosPjDTO);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarEmpresasUsuarios: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuestaDTO);
        }
        return utilDTO;
    }

    /**
     * Metodo que consultar los usuarios juridicos Oracle 11++ por perfil que
     * cuenten con montos autorizados
     *
     * @param perfilAcceso
     * 1:Master,2:Admin,3:Autorizador,4:Ingresador,5:Consultor
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    @Override
    public UtilDTO consultarEmpresasMontosUsuariosPjPerfil(String idEmpresa, String idCanal, String codigoCanal, String perfilAcceso) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO = new IbEmpresasUsuariosPjDTO();
        Map resultado = new HashMap();
        try {
            List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = ibEmpresasUsuariosPjFacade.consultarEmpresasMontosUsuariosPjPerfil(new BigDecimal(idEmpresa), perfilAcceso);
            ibEmpresasUsuariosPjDTO.setIbEmpresasUsuariosPj(ibEmpresasUsuariosPj);
            resultado.put(USUARIOS, ibEmpresasUsuariosPjDTO);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarEmpresasUsuarios: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuestaDTO);
        }
        return utilDTO;
    }

    /**
     * Metodo que consultar los usuarios juridicos que son representantes
     * legales de una empresa
     *
     *
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    @Override
    public IbEmpresasUsuariosPjDTO consultarEmpresasUsuariosPjRepresentanteLegal(String idEmpresa, String idCanal, String codigoCanal) {
        IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO = new IbEmpresasUsuariosPjDTO();
        try {
            List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = ibEmpresasUsuariosPjFacade.consultarEmpresasUsuariosPjRepresentanteLegal(new BigDecimal(idEmpresa));
            if (!ibEmpresasUsuariosPj.isEmpty()) {
                ibEmpresasUsuariosPjDTO.setIbEmpresasUsuariosPj(ibEmpresasUsuariosPj);
            }
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarEmpresasUsuarios: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            ibEmpresasUsuariosPjDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return ibEmpresasUsuariosPjDTO;
    }

    /**
     * Metodo que se encarga de registrar las modificaciones los de la empresa y
     * el usuario Oracle 11
     *
     *
     * @param ibEmpresasUsuariosPj objeto completo de EmpresaUsuario
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    @Override
    public UtilDTO modificarDatosEmpresaUsuario(IbEmpresasUsuariosPj ibEmpresasUsuariosPj, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            respuesta = ibEmpresasUsuariosPjFacade.modificarPj(ibEmpresasUsuariosPj);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN modificarDatosEmpresaUsuario: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setRespuesta(respuesta);
        }

//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN modificarDatosEmpresaUsuario: ")
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    @Override
    public List<IbEmpresasUsuariosPj> consultarEmpresaxEmpresa(String ibEmpresa, String codigoCanal) {
        List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = new ArrayList<>();
        try {
            ibEmpresasUsuariosPj = ibEmpresasUsuariosPjFacade.consultarEmpresaxEmpresa(ibEmpresa);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarEmpresaxEmpresa: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
        }
        return ibEmpresasUsuariosPj;
    }
}
