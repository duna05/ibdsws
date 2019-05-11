/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.EMailDAO;
import com.bds.ws.dao.IbEmpresasUsuariosDAO;
import com.bds.ws.dao.IbParametrosDAO;
import com.bds.ws.dao.IbUsuariosPjDAO;
import com.bds.ws.dao.OtpDAO;
import com.bds.ws.dao.SMSDAO;
import com.bds.ws.dto.IbConexionUsuarioPjDTO;
import com.bds.ws.dto.IbEmpresasUsuariosPjDTO;
import com.bds.ws.dto.IbParametrosDTO;
import com.bds.ws.dto.IbPerfilesPjDTO;
import com.bds.ws.dto.IbUsuariosPjDTO;
import com.bds.ws.dto.OtpDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusAccesoEnum;
import com.bds.ws.enumerated.EstatusPlantillasEmail;
import com.bds.ws.enumerated.EstatusRegistroUsuarioEnum;
import com.bds.ws.enumerated.EstatusSession;
import com.bds.ws.facade.IbConexionUsuarioPjFacade;
import com.bds.ws.facade.IbEmpresasUsuariosPjFacade;
import com.bds.ws.facade.IbHistoricoClavesPjFacade;
import com.bds.ws.facade.IbParametrosFacade;
import com.bds.ws.facade.IbPerfilesPjFacade;
import com.bds.ws.facade.IbTextosFacade;
import com.bds.ws.facade.IbUsuariosPjFacade;
import com.bds.ws.model.IbCanal;
import com.bds.ws.model.IbConexionUsuarioPj;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import com.bds.ws.model.IbHistoricoClavesPj;
import com.bds.ws.model.IbPerfilesPj;
import com.bds.ws.model.IbUsuariosPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
@Named("IbUsuariosPjDAO")
@Stateless
public class IbUsuariosPjDAOImpl extends BDSUtil implements IbUsuariosPjDAO {

    @EJB
    private IbPerfilesPjFacade ibPerfilesPjFacade;
    @EJB
    private IbUsuariosPjFacade ibUsuariosPjFacade;
    @EJB
    private IbParametrosFacade ibParametrosFacade;
    @EJB
    private IbEmpresasUsuariosPjFacade ibEmpresasUsuariosPjFacade;
    @EJB
    private SMSDAO smsDAO;
    @EJB
    IbTextosFacade textosFacade;
    @EJB
    private EMailDAO emailDAO;
    @EJB
    private IbParametrosDAO ibParametrosDAO;
    @EJB
    private IbHistoricoClavesPjFacade ibHistoricoClavesPjFacade;
    @EJB
    private IbConexionUsuarioPjFacade ibConexionUsuarioPjFacade;
    @EJB
    private OtpDAO otpDAO;
    @EJB
    private IbEmpresasUsuariosDAO ibEmpresasUsuariosDAO;

    final int RADIX = 10;

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbUsuariosPjDAOImpl.class.getName());

    /**
     * Realiza la validacion de la empresa
     *
     * @param login login del usuario
     * @param idCanal id de canal interno
     * @param codigoCanal codigo de canal en el CORE
     * @return UtilDTO
     */
    @Override
    public UtilDTO validarUsuarioLogin(String login, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        Map resultado = new HashMap();
        try {
            IbUsuariosPj usuario = ibUsuariosPjFacade.validarUsuarioLogin(login);
            if (usuario == null) {
                resultado.put(EXISTE, false);
            } else {
                resultado.put(EXISTE, true);
            }
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN validarUsuarioLogin: ")
                    //.append("USR-").append(idUsuario)
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

    @Override
    public UtilDTO validarLogin(String password, String login, String idCanal, String codigoCanal, String idSession) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbUsuariosPjDTO ibUsuariosPjDTO = new IbUsuariosPjDTO();
        Map resultado = new HashMap();
        try {
            IbUsuariosPj usuario = ibUsuariosPjFacade.validarLogin(password, login, idCanal, codigoCanal);
            if (usuario == null) {
                //SE VERIFICA SI EL LOGIN DEL USUARIO EXISTE PARA ACTUALIZAR EL NUMERO INTENTOS FALLIDOS
                IbUsuariosPj usuarioLogin = ibUsuariosPjFacade.validarUsuarioLogin(login);
                //EL LOGIN EXISTE
                if (usuarioLogin != null) {
                    ibUsuariosPjDTO.setIbUsuarioPj(usuarioLogin);
                    //SE ACTUALIZA EL NUMERO DE INTENTOS FALLIDOS
                    usuarioLogin.setIntentosFallidos(usuarioLogin.getIntentosFallidos().add(BigInteger.ONE));
                    int intentosFallidosPermitidos = Integer.parseInt(ibParametrosFacade.consultaParametro("pjw.global.login.intentosFallidos", codigoCanal).getIbParametro().getValor());
                    int intentosFallidosActuales = Integer.parseInt(usuarioLogin.getIntentosFallidos().toString());
                    if (intentosFallidosActuales >= intentosFallidosPermitidos) {
                        usuarioLogin.setEstatusAcceso(Character.forDigit(EstatusAccesoEnum.BLOQUEADO.getId(), RADIX));
                        //SE CONSULTAN LAS EMPRESAS ASOCIADAS A ESE USUARIO PARA REALIZARLE EL BLOQUEO
                        List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = ibEmpresasUsuariosPjFacade.consultarDatosEmpresaUsuario(usuarioLogin.getId());
                        if (!ibEmpresasUsuariosPj.isEmpty()) {
                            //SIEMPRE SE ENVIA EL CODIGO DE EMPRESA 1 AL MOMENTO DE ENVIAR EL CORREO
                            String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
                            //CODIGO DE LA PLANTILLA PARA EL CORREO DE BLOQUEO DE USUARIO
                            String codigoPlantilla = EstatusPlantillasEmail.BLOQUEO_USUARIO_TEXT.getDescripcion();

                            for (IbEmpresasUsuariosPj ibEmpresaUsuarioPj : ibEmpresasUsuariosPj) {
                                String parametrosCorreo = ibEmpresaUsuarioPj.getIdUsuarioPj().getNombre();
                                //SE MARCA EL USUARIO Y LA EMPRESA COMO BLOQUEADO
                                if (ibEmpresaUsuarioPj.getEstatusAcceso() != Character.forDigit(EstatusAccesoEnum.BLOQUEADO.getId(), RADIX)) {
                                    ibEmpresaUsuarioPj.setEstatusAcceso(Character.forDigit(EstatusAccesoEnum.BLOQUEADO.getId(), RADIX));
                                    ibEmpresasUsuariosPjFacade.modificarPj(ibEmpresaUsuarioPj);
                                    String textoSMS = textosFacade.textoParametro("pjw.global.sms.cuerpo.bloqueo", idCanal);
                                    textoSMS = textoSMS.replace("$PFEC", new Date().toString());
                                    smsDAO.enviarSMS(ibEmpresaUsuarioPj.getTelfCelular(), textoSMS, codigoCanal);
                                    emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, ibEmpresaUsuarioPj.getEmail(), parametrosCorreo, idCanal, codigoCanal);
                                }
                            }
                        }
                        //SE HA BLOQUEADO EL USUARIO
                        resultado.put(USUARIO, ibUsuariosPjDTO);
                        resultado.put(USUARIO_BLOQUEADO, true);
                        resultado.put(CLAVE_VENCIDA, false);
                        resultado.put(EXISTE, true);
                        resultado.put(CONEXION_SIMULTANEA, false);
                        return utilDTO;
                    } else {
                        //SE ACTUALIZA EL NUMERO DE INTENTOS FALLIDOS
                        ibUsuariosPjFacade.actualizar(usuarioLogin);
                        resultado.put(USUARIO_BLOQUEADO, false);
                        resultado.put(CLAVE_VENCIDA, false);
                        //SE ENVIA EXISTE FALSE PARA PODER VALIDAR EN LA VISTA QUE EL LOGIN 
                        //Y EL PASSWORD NO ES VALIDO
                        resultado.put(EXISTE, false);
                        resultado.put(USUARIO, ibUsuariosPjDTO);
                        resultado.put(CONEXION_SIMULTANEA, false);
                        return utilDTO;
                    }
                } else {
                    //EL LOGIN NO EXISTE EN LA BASE DE DATOS
                    resultado.put(USUARIO_BLOQUEADO, false);
                    resultado.put(CLAVE_VENCIDA, false);
                    resultado.put(EXISTE, false);
                    resultado.put(USUARIO, false);
                    resultado.put(CONEXION_SIMULTANEA, false);
                    return utilDTO;
                }
            }

            //EL LOGIN Y LA CONTRASEÑA FUERON CORRECTOS SE DEVUELVEN LOS DATOS DEL USUARIO
            //SIN EMBARGO EL USUARIO SE ENCUENTRA BLOQUEADO
            ibUsuariosPjDTO.setIbUsuarioPj(usuario);
            if (usuario.getEstatusAcceso().equals(Character.forDigit(EstatusAccesoEnum.BLOQUEADO.getId(), RADIX))) {
                //EL USUARIO YA ESTABA BLOQUEADO
                resultado.put(USUARIO, ibUsuariosPjDTO);
                resultado.put(EXISTE, true);
                resultado.put(USUARIO_BLOQUEADO, true);
                resultado.put(CLAVE_VENCIDA, false);
                resultado.put(CONEXION_SIMULTANEA, false);
                return utilDTO;
            }

            //EL USUARIO NO ESTABA LOQUEADO, SE VERIFICA SI LA CLAVE ESTA VENCIDA
            List<IbHistoricoClavesPj> ibHistoricoClavesPj = (List<IbHistoricoClavesPj>) usuario.getIbHistoricoClavesPjCollection();
            Date fechaVencimiento = ibHistoricoClavesPj.get(0).getFechaVencimiento();
            Date fechaActual = new Date();

            //SE VERIFICA SI LA CLAVE ESTA VENCIDA
            //CLAVE VENCIDA SI FECHA DE VENCIMIENTO ES ANTERIOR A LA FECHA ACTUAL
            if (fechaVencimiento.before(fechaActual)) {
                resultado.put(USUARIO, ibUsuariosPjDTO);
                resultado.put(EXISTE, true);
                resultado.put(USUARIO_BLOQUEADO, false);
                resultado.put(CLAVE_VENCIDA, true);
                resultado.put(CONEXION_SIMULTANEA, false);
                return utilDTO;
            }

            //SE VERIFICA SI EXISTE UNA CONEXION SIMULTANEA
            //SE BUSCA LA ULTIMA INTERACCION CON LA BD PARA VALIDAR CONEXION SIMULTANEA
            List<IbConexionUsuarioPj> ibConexionesUsuarioesPj = (List<IbConexionUsuarioPj>) usuario.getIbConexionUsuarioPjCollection();
            if (!ibConexionesUsuarioesPj.isEmpty()) {
                IbConexionUsuarioPj ibConexionUsuarioPj = ibConexionesUsuarioesPj.get(0);
                BigInteger conectado = new BigInteger(EstatusSession.CONECTADO.getDescripcion());
                BigInteger conexionSimulatanea = new BigInteger(EstatusSession.SIMULTANEA.getDescripcion());
                //SE VERIFICA SI EL USUARIO ESTA CONECTADO A LA SESSION O SI ES CONEXION SIMULTANEA
                if (ibConexionUsuarioPj.getInicioSesion().compareTo(conectado) == 0 || ibConexionUsuarioPj.getInicioSesion().compareTo(conexionSimulatanea) == 0) {
                    //EL USUARIO YA ESTA CONECTADO A LA SESSION O FUE MARCADA LA SESSION COMO SIMULTANEA
                    Date fechaHoraUltimaInteraccion = ibConexionUsuarioPj.getFechaHoraUltimaInteraccion();
                    long timeOut;
                    long minutos = calculaMinutosEntreFechas(fechaActual, fechaHoraUltimaInteraccion);
                    IbParametrosDTO ibParametrosDTO = ibParametrosDAO.consultaParametro(CODIGO_TIME_OUT_PJ, codigoCanal);
                    if (ibParametrosDTO.getRespuesta().getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                        timeOut = Long.parseLong(ibParametrosDTO.getIbParametro().getValor());
                        //SI SE CIERRA EL EXPLORADOR HACIENDO CLIC Y ME ENCUENTRO DENTRO DEL PERIODO DE EXPIRACION
                        //DE LA SESSION
                        if (minutos < timeOut) {
                            //LA SESSION HABIA SIDO MARCADA COMO SIMULTANEA PREVIAMENTE
                            if (ibConexionUsuarioPj.getInicioSesion().compareTo(conexionSimulatanea) == 0) {
                                //SI LA SESSION HABIA SIDO MARCADA COMO SIMULTANEA ENTONCES DEVUELVO CONEXION_SIMULATENA EN FALSE
                                //PARA PERMITIR A UN TERCER USUARIO INICIAR LA SESSION
                                resultado.put(CONEXION_SIMULTANEA, false);
                                ibConexionUsuarioPj.setInicioSesion(new BigInteger(EstatusSession.CONECTADO.getDescripcion()));
                            } else {
                                ibConexionUsuarioPj.setInicioSesion(new BigInteger(EstatusSession.SIMULTANEA.getDescripcion()));
                                resultado.put(CONEXION_SIMULTANEA, true);
                            }

                            //ACTUALIZO EN LA TABLA CONEXION USUARIO PJ Y COLOCO LA SESSION COMO SIMULTANEA
                            ibConexionUsuarioPj.setIdSesion(idSession);
                            ibConexionUsuarioPjFacade.modificar(ibConexionUsuarioPj);
                            resultado.put(USUARIO, ibUsuariosPjDTO);
                            resultado.put(EXISTE, true);
                            resultado.put(USUARIO_BLOQUEADO, false);
                            resultado.put(CLAVE_VENCIDA, false);
                            return utilDTO;
                        }
                    }
                }
            } else {
                IbCanal ibCanal = new IbCanal();
                ibCanal.setId(new BigDecimal(idCanal));
                //PRIMERA VEZ QUE EL USUARIO ESTA HACIENDO LOGIN EN LA APLICACION
                //SE CREA EL PRIMER Y UNICO REGISTRO POR USUARIO EN ESA TABLA
                IbConexionUsuarioPj ibConexionUsuarioPj = new IbConexionUsuarioPj();
                ibConexionUsuarioPj.setId(BigDecimal.ZERO);
                ibConexionUsuarioPj.setFechaHoraUltimaInteraccion(new Date());
                ibConexionUsuarioPj.setIdCanal(ibCanal);
                ibConexionUsuarioPj.setIdSesion(idSession);
                ibConexionUsuarioPj.setIdUsuarioPj(usuario);
                ibConexionUsuarioPj.setInicioSesion(new BigInteger(EstatusSession.CONECTADO.getDescripcion()));
                ibConexionUsuarioPjFacade.crear(ibConexionUsuarioPj);
                ibConexionesUsuarioesPj.add(ibConexionUsuarioPj);
                usuario.setIbConexionUsuarioPjCollection(ibConexionesUsuarioesPj);
            }

            //EL PROCESO DE LOGIN FUE EXITOSO Y SE PASARON TODAS LAS VALIDACIONES ANTERIORES
            //ACTUALIZAR EL NUMERO DE INTENTOS FALLIDOS A 0
            usuario.setIntentosFallidos(BigInteger.ZERO);
            //ACTUALIZAR LA ULTIMA INTERACCION, EL INICIO DE SESSION PASA AL VALOR 1 Y SE ACTUALIZA EL ID DE SESSION
            List<IbConexionUsuarioPj> conexionesUsuarioPj = (List<IbConexionUsuarioPj>) usuario.getIbConexionUsuarioPjCollection();
            Date fechaUltimaInteraccion = conexionesUsuarioPj.get(0).getFechaHoraUltimaInteraccion();
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaUltimaInteraccionStr = formateador.format(fechaUltimaInteraccion);

            conexionesUsuarioPj.get(0).setFechaHoraUltimaInteraccion(new Date());
            conexionesUsuarioPj.get(0).setInicioSesion(new BigInteger(EstatusSession.CONECTADO.getDescripcion()));
            conexionesUsuarioPj.get(0).setIdSesion(idSession);
            usuario.setIbConexionUsuarioPjCollection(conexionesUsuarioPj);
            ibUsuariosPjFacade.actualizar(usuario);
            resultado.put(USUARIO, ibUsuariosPjDTO);
            resultado.put(EXISTE, true);
            //EL USUARIO EXISTE Y NO ESTA BLOQUEADO
            resultado.put(USUARIO_BLOQUEADO, false);
            resultado.put(CLAVE_VENCIDA, false);
            resultado.put(CONEXION_SIMULTANEA, false);
            resultado.put(FECHA_ULTIMA_CONEXION, fechaUltimaInteraccionStr);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN validarUsuarioLogin: ")
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
     * Metodo que se encarga de registrar los datos de un cliente pj en la BD
     * Oracle 11
     *
     *
     * @param nombreCompleto nombre completo del usuario
     * @param nroDoc numero de documento de indentificacion
     * @param tipoDoc tipo de documento del usuario
     * @param clave clave del usuario
     * @param login login del suaurio
     * @param estatusRegistro 1:Activo,3:PorCompletarAfiliacion
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @Override
    public UtilDTO insertarDatosIbUsuarioPj(String nombreCompleto, String nroDoc, Character tipoDoc, String clave, String login, Character estatusAcceso, Character estatusRegistro, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            IbUsuariosPj ibUsuarioPj = new IbUsuariosPj();
            ibUsuarioPj.setId(BigDecimal.ZERO);
            ibUsuarioPj.setNombre(nombreCompleto);
            ibUsuarioPj.setNroDoc(nroDoc);
            ibUsuarioPj.setTipoDoc(tipoDoc);
            ibUsuarioPj.setClave(clave);
            ibUsuarioPj.setLogin(login);
            ibUsuarioPj.setEstatusAcceso(estatusAcceso);
            ibUsuarioPj.setEstatusRegistro(estatusRegistro);
            ibUsuariosPjFacade.crearPJ(ibUsuarioPj);
            IbUsuariosPj usuario = ibUsuariosPjFacade.consultarUsuarioPjPorCedula(tipoDoc, nroDoc);
            if (usuario == null) {
                resultado.put(ID_USUARIO, false);
            } else {
                resultado.put(ID_USUARIO, usuario.getId().toString());
                resultado.put(USUARIO, usuario);
            }
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN insertarDatosIbUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;
    }

    /**
     * Metodo que se +encarga de consultar los UsuariosPj Oracle 11++
     *
     *
     *
     * @param idEmpresa codigo de la empreas en IB
     * @param idUsuario codigo del usuario en IB
     * @param nombreCompleto nombre completo del usuario
     * @param nroDoc numero de documento del usuario
     * @param tipoDoc tipo de documento del usuario
     * @param clave clave del usuario
     * @param login login del usuario
     * @param email clave del usario
     * @param telefonoOficina
     * @param telefonoCelular
     * @param representanteLegal
     * @param perfilAcceso
     * @param estatusAcceso
     * 1:Activo,2:Bloqueado,3:Inactivo(Vacaciones),4:Eliminado
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return the com.bds.ws.dto.UtilDTO
     */
    @Override
    public UtilDTO modificarDatosIbUsuarioPj(String idEmpresa, String idUsuario, String nombreCompleto, String nroDoc, Character tipoDoc, String clave, String login, String email, String telefonoOficina, String telefonoCelular, Character representanteLegal, String perfilAcceso, Character estatusAcceso, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        IbUsuariosPj ibUsuarioPj;
        try {
            IbEmpresasUsuariosPj ibEmpresaUsuarioPj = ibEmpresasUsuariosPjFacade.consultarEmpresaUsuario(new BigDecimal(idUsuario), new BigDecimal(idEmpresa));
            if (ibEmpresaUsuarioPj != null) {
                IbPerfilesPj ibPerfilPj = new IbPerfilesPj();
                ibPerfilPj.setId(!perfilAcceso.isEmpty() ? new BigDecimal(perfilAcceso) : ibEmpresaUsuarioPj.getPerfilAcceso().getId());
                ibUsuarioPj = ibEmpresaUsuarioPj.getIdUsuarioPj();
                ibUsuarioPj.setNombre(!nombreCompleto.isEmpty() ? nombreCompleto : ibUsuarioPj.getNombre());
                ibUsuarioPj.setNroDoc(!nroDoc.isEmpty() ? nroDoc : ibUsuarioPj.getNroDoc());
                ibUsuarioPj.setTipoDoc(!tipoDoc.toString().trim().isEmpty() ? tipoDoc : ibUsuarioPj.getTipoDoc());
                ibUsuarioPj.setClave(!clave.isEmpty() ? clave : ibUsuarioPj.getClave());
                ibUsuarioPj.setLogin(!login.isEmpty() ? login : ibUsuarioPj.getLogin());
                ibUsuarioPj.setEstatusAcceso(!estatusAcceso.toString().trim().isEmpty() ? estatusAcceso : ibUsuarioPj.getEstatusAcceso());

                ibEmpresaUsuarioPj.setEmail(!email.isEmpty() ? email : ibEmpresaUsuarioPj.getEmail());
                ibEmpresaUsuarioPj.setEsRepresentanteLegal(!representanteLegal.toString().trim().isEmpty() ? representanteLegal : ibEmpresaUsuarioPj.getEsRepresentanteLegal());
                ibEmpresaUsuarioPj.setEstatusAcceso(!estatusAcceso.toString().trim().isEmpty() ? estatusAcceso : ibEmpresaUsuarioPj.getEstatusAcceso());
                ibEmpresaUsuarioPj.setPerfilAcceso(ibPerfilPj);
                ibEmpresaUsuarioPj.setTelfCelular(!telefonoCelular.isEmpty() ? telefonoCelular : ibEmpresaUsuarioPj.getTelfCelular());
                ibEmpresaUsuarioPj.setTelfOficina(!telefonoOficina.isEmpty() ? telefonoOficina : ibEmpresaUsuarioPj.getTelfOficina());

                ibEmpresaUsuarioPj.setIdUsuarioPj(ibUsuarioPj);
                respuesta = ibEmpresasUsuariosPjFacade.modificarPj(ibEmpresaUsuarioPj);
            } else {
                throw new NoResultException();
            }
        } catch (NoResultException e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarEmpresasUsuarios: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN modificarDatosIbUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }

    /**
     * Metodo que se +encarga de modificar los datos de un usuario pj en la BD
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param clave clave del usuario
     * @param login login del suaurio
     * @param estatisRegistro the value of estatisRegistro
     * @param estatusAcceso the value of estatusAcceso
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return the com.bds.ws.dto.UtilDTO
     */
    @Override
    public UtilDTO modificarLoginClaveUsuarioPj(String idUsuario, String clave, String periodoVigencia, String login, Character estatisRegistro, Character estatusAcceso, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        try {
            IbUsuariosPj ibUsuarioPj = ibUsuariosPjFacade.find(new BigDecimal(idUsuario));
            if (ibUsuarioPj != null) {
                ibUsuarioPj.setId(new BigDecimal(idUsuario));
                ibUsuarioPj.setClave(clave);
                ibUsuarioPj.setLogin(login);
                ibUsuarioPj.setEstatusAcceso(estatusAcceso);
                ibUsuarioPj.setEstatusRegistro(estatisRegistro);
                respuesta = ibUsuariosPjFacade.modificarPj(ibUsuarioPj);

                IbUsuariosPj ibUsuariosPj = new IbUsuariosPj(new BigDecimal(idUsuario));
                //SE PROCEDE A INSERTAR EN LA TABLA HISTORICO CLAVES
                IbHistoricoClavesPj ibHistoricoClavesPj = new IbHistoricoClavesPj();
                ibHistoricoClavesPj.setId(BigDecimal.ZERO);
                ibHistoricoClavesPj.setClave(clave);
                ibHistoricoClavesPj.setIdUsuarioPj(ibUsuariosPj);

                //SE SUMAN DIAS A LA FECHA ACTUAL
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(periodoVigencia));

                //SE SETEA EL CALCULO DE LA FECHA AL OBJETO HISTORICO CLAVES
                ibHistoricoClavesPj.setFechaVencimiento(calendar.getTime());

                respuesta = ibHistoricoClavesPjFacade.crear(ibHistoricoClavesPj);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    //OCURRIO UN ERROR AL MOMENTO DE INTENTAR EL REGISTRO EN HISTORICO CLAVES PJ
                    return utilDTO;
                }
            } else {
                respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
            }
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN insertarDatosIbUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }

    /**
     * Metodo que se +encarga de modificar los datos de un usuario pj en la BD
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return IbUsuariosPjDTO
     */
    @Override
    public IbUsuariosPjDTO actualizarIntentosPDD(String idUsuario, String idCanal, String codigoCanal) {
        IbUsuariosPj ibUsuarioPj = ibUsuariosPjFacade.find(new BigDecimal(idUsuario));
        RespuestaDTO respuesta = new RespuestaDTO();
        IbUsuariosPjDTO ibUsuariosPjDTO = new IbUsuariosPjDTO();
        if (ibUsuarioPj != null) {
            String fechaActual = formatearFecha(new Date(), "dd/MM/yyyy");
            String fechaActualizacionBD = formatearFecha(ibUsuarioPj.getFechaModificacion(), "dd/MM/yyyy");
            int intentosFallidosActuales, intentosFallidosPermitidos;

            //SI NO ES LA PRIMERA VEZ EN EL DIA QUE SE REALIZA EL INTENTO
            if (fechaActual.equals(fechaActualizacionBD)) {
                intentosFallidosActuales = ibUsuarioPj.getIntentosFallidosPreguntas().add(BigInteger.ONE).intValue();
                intentosFallidosPermitidos = Integer.parseInt(ibParametrosFacade.consultaParametro("pjw.global.login.intentosFallidosPreguntas", codigoCanal).getIbParametro().getValor());

                if (intentosFallidosActuales >= intentosFallidosPermitidos) {
                    ibUsuarioPj.setEstatusAcceso(String.valueOf(EstatusAccesoEnum.BLOQUEADO.getId()).charAt(0));
                    //SE CONSULTAN LAS EMPRESAS ASOCIADAS A ESE USUARIO PARA REALIZARLE EL BLOQUEO
                    List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = ibEmpresasUsuariosPjFacade.consultarDatosEmpresaUsuario(ibUsuarioPj.getId());
                    if (!ibEmpresasUsuariosPj.isEmpty()) {
                        //SIEMPRE SE ENVIA EL CODIGO DE EMPRESA 1 AL MOMENTO DE ENVIAR EL CORREO
                        String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
                        //CODIGO DE LA PLANTILLA PARA EL CORREO DE BLOQUEO DE USUARIO
                        String codigoPlantilla = EstatusPlantillasEmail.BLOQUEO_USUARIO_TEXT.getDescripcion();

                        for (IbEmpresasUsuariosPj ibEmpresaUsuarioPj : ibEmpresasUsuariosPj) {
                            String parametrosCorreo = ibEmpresaUsuarioPj.getIdUsuarioPj().getNombre();
                            //SE MARCA EL USUARIO Y LA EMPRESA COMO BLOQUEADO
                            if (ibEmpresaUsuarioPj.getEstatusAcceso() != Character.forDigit(EstatusAccesoEnum.BLOQUEADO.getId(), RADIX)) {
                                ibEmpresaUsuarioPj.setEstatusAcceso(Character.forDigit(EstatusAccesoEnum.BLOQUEADO.getId(), RADIX));
                                ibEmpresasUsuariosPjFacade.modificarPj(ibEmpresaUsuarioPj);
                                //POR LOS MOMENTOS NO SE ENVIARA EL MENSAJE DE TEXTO
                                //String textoSMS = textosFacade.textoParametro("pjw.global.sms.cuerpo.bloqueo", idCanal);
                                //textoSMS = textoSMS.replace("$PFEC", new Date().toString());
                                //smsDAO.enviarSMS(ibEmpresaUsuarioPj.getTelfCelular(), textoSMS, codigoCanal);
                                emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, ibEmpresaUsuarioPj.getEmail(), parametrosCorreo, idCanal, codigoCanal);
                            }
                        }
                    }
                }
                //SI ES LA PRIMERA VEZ EN EL DIA QUE SE RELIAZA EL INTENTO
            } else {
                intentosFallidosActuales = BigInteger.ONE.intValue();
            }
            ibUsuarioPj.setIntentosFallidosPreguntas(new BigInteger(String.valueOf(intentosFallidosActuales)));
            ibUsuariosPjDTO.setIbUsuarioPj(ibUsuarioPj);
        } else {
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        }
        ibUsuariosPjDTO.setRespuesta(respuesta);
        return ibUsuariosPjDTO;
    }

    /**
     * Metodo que se +encarga de consultar los UsuariosPj Oracle 11++
     *
     * @param tipoDocumento V,E,P
     * @param nroDocumento numero de cedula
     * @param codigoCanal codigo de canal en IB
     * @param idCanal codigo de canal en el CORE bancario
     * @return the com.bds.ws.dto.UtilDTO
     */
    @Override
    public UtilDTO consultarUsuarioPjPorCedula(Character tipoDocumento, String nroDocumento, String codigoCanal, String idCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        IbUsuariosPjDTO ibUsuariosPjDTO = new IbUsuariosPjDTO();
        Map resultado = new HashMap();
        try {
            IbUsuariosPj usuario = ibUsuariosPjFacade.consultarUsuarioPjPorCedula(tipoDocumento, nroDocumento);
            if (usuario == null) {
                resultado.put(EXISTE, false);
            } else {
                ibUsuariosPjDTO.setIbUsuarioPj(usuario);
                resultado.put(EXISTE, true);
                resultado.put(ID_USUARIO, usuario.getId());
                resultado.put(USUARIO, ibUsuariosPjDTO);
            }
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarUsuarioPjPorRif: ")
                    //.append("USR-").append(idUsuario)
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log 
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }
    
    
    
     /**
     * Metodo que se +encarga de consultar los UsuariosPj Oracle 11++
     *
     * @param tipoDocumento V,E,P
     * @param nroDocumento numero de cedula
     * @param codigoCanal codigo de canal en IB
     * @param idCanal codigo de canal en el CORE bancario
     * @return the com.bds.ws.dto.UtilDTO
     */
    @Override
    public UtilDTO consultarUsuarioPjPorId( BigDecimal idUsuario, String codigoCanal, String idCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        IbUsuariosPjDTO ibUsuariosPjDTO = new IbUsuariosPjDTO();
        Map resultado = new HashMap();
        try {
            IbUsuariosPj usuario = ibUsuariosPjFacade.consultarUsuarioPjPorId(idUsuario);
            if (usuario == null) {
                resultado.put(EXISTE, false);
            } else {
                ibUsuariosPjDTO.setIbUsuarioPj(usuario);
                resultado.put(EXISTE, true);
                resultado.put(ID_USUARIO, usuario.getId());
                resultado.put(USUARIO, ibUsuariosPjDTO);
            }
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarUsuarioPjPorRif: ")
                    //.append("USR-").append(idUsuario)
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log 
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }

    /**
     * Metodo que consultar los usuarios juridicos Oracle 11++
     *
     *
     * @param perfilAcceso
     * 1:Master,2:Admin,3:Autorizador,4:Ingresador,5:Consultor
     * @param estatusRegistro []
     * 1:Activo,2:EnProcesoAfiliacion,3:PorCompletarAfiliacion
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return the com.bds.ws.dto.UtilDTO
     */
    @Override
    public UtilDTO consultarUsuariosSinReglasAproPj(String perfilAcceso, int[] estatusRegistro, String idEmpresa, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO = new IbEmpresasUsuariosPjDTO();
        Map resultado = new HashMap();
        try {
            List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = ibUsuariosPjFacade.consultarUsuariosSinReglasAproPj(new BigDecimal(perfilAcceso), estatusRegistro, new BigDecimal(idEmpresa));
            if (!ibEmpresasUsuariosPj.isEmpty()) {
                ibEmpresasUsuariosPjDTO.setIbEmpresasUsuariosPj(ibEmpresasUsuariosPj);
                resultado.put(EXISTE, true);
                resultado.put(USUARIOS, ibEmpresasUsuariosPjDTO);
            } else {
                resultado.put(EXISTE, false);
            }
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN consultarUsuariosSinReglasAproPj: ")
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
     * Metodo que valida el Historico de Claves del usuario PJ
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param clave cantidad de claves anteriores a validar
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    @Override
    public UtilDTO validarHistoricoClavesUsuarioPj(String idUsuario, String clave, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        Map resultado = new HashMap();
        try {
            int cantClaves = Integer.parseInt((ibParametrosDAO.consultaParametro("pjw.global.validacion.cantClaves", codigoCanal)).getIbParametro().getValor());
            List<IbHistoricoClavesPj> ibHistoricoClavesPj = ibHistoricoClavesPjFacade.consultarClavesPorIdUsuario(new BigDecimal(idUsuario), cantClaves);
            if (!ibHistoricoClavesPj.isEmpty()) {
                for (IbHistoricoClavesPj ibHistoricoClavePj : ibHistoricoClavesPj) {
                    if (ibHistoricoClavePj.getClave().equals(clave)) {
                        resultado.put(VALIDO, false);
                        return utilDTO;
                    }
                }
                resultado.put(VALIDO, true);
            } else {
                resultado.put(VALIDO, true);
            }
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR JPA EN validarHistoricoClavesUsuarioPj: ")
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
     * Metodo que consulta las conexiones de un usuario
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    @Override
    public IbConexionUsuarioPjDTO consultarConexionUsuarioPj(String idUsuario, String idCanal, String codigoCanal, String idSession) {
        RespuestaDTO respuesta = new RespuestaDTO();
        IbConexionUsuarioPjDTO ibConexionUsuarioPjDTO = new IbConexionUsuarioPjDTO();
        try {
            IbConexionUsuarioPj ibConexionUsuarioPj  = new IbConexionUsuarioPj();
            IbConexionUsuarioPj ibConexionUsuarioPjAmodificar = ibConexionUsuarioPjFacade.consultarConexionPorUsuario(new BigDecimal(idUsuario), idSession);
            if (ibConexionUsuarioPjAmodificar != null) {
                //SE DEVULEVE EL USUARIO SIN LA MODIFICACION DE LA FECHA DE LA ULTIMA ITERACCION
                ibConexionUsuarioPj.setFechaHoraUltimaInteraccion(ibConexionUsuarioPjAmodificar.getFechaHoraUltimaInteraccion());
                ibConexionUsuarioPj.setIdSesion(ibConexionUsuarioPjAmodificar.getIdSesion());
                ibConexionUsuarioPj.setInicioSesion(ibConexionUsuarioPjAmodificar.getInicioSesion());
                ibConexionUsuarioPj.setId(ibConexionUsuarioPjAmodificar.getId());
                ibConexionUsuarioPjDTO.setIbConexionUsuarioPj(ibConexionUsuarioPj);
                //SE MODIFICA LA FECHA DE LA ULTIMA ITERACION CON LA BD
                ibConexionUsuarioPjAmodificar.setFechaHoraUltimaInteraccion(new Date());
                ibConexionUsuarioPjFacade.modificarPj(ibConexionUsuarioPjAmodificar);
            }
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN consultarConexionUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            ibConexionUsuarioPjDTO.setRespuesta(respuesta);
        }
        return ibConexionUsuarioPjDTO;
    }

    /**
     * Metodo que cierra la conexion de un usuario
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    @Override
    public UtilDTO cerrarConexionUsuarioPj(String idUsuario, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            IbConexionUsuarioPj ibConexionesUsuariosPj = ibConexionUsuarioPjFacade.consultarConexionPorUsuario(new BigDecimal(idUsuario));
            if (ibConexionesUsuariosPj != null) {
                ibConexionesUsuariosPj.setFechaHoraUltimaInteraccion(new Date());
                ibConexionesUsuariosPj.setInicioSesion(new BigInteger(EstatusSession.DESCONECTADO.getDescripcion()));
                //SE LIMPIA EL ID DE LA SESSION
                ibConexionesUsuariosPj.setIdSesion(EstatusSession.DESCONECTADO.getDescripcion());
                ibConexionUsuarioPjFacade.modificarPj(ibConexionesUsuariosPj);
            } else {
                throw new NoResultException();
            }
        } catch (NoResultException e) {
            logger.error(new StringBuilder("ERROR JPA EN cerrarConexionUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN cerrarConexionUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }

    @Override
    public IbPerfilesPjDTO consultarPerfilesPj(String estatus, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        IbPerfilesPjDTO ibPerfilesPjDTO = new IbPerfilesPjDTO();
        ibPerfilesPjDTO.setRespuesta(respuesta);
        try {
            List<IbPerfilesPj> ibPerfilesPj;
            ibPerfilesPj = ibPerfilesPjFacade.consultarPerfilesPj(estatus.charAt(0));
            if (!ibPerfilesPj.isEmpty()) {
                ibPerfilesPjDTO.setIbPerfilesPj(ibPerfilesPj);
            } else {
                ibPerfilesPjDTO.setIbPerfilesPj(new ArrayList<IbPerfilesPj>());
            }
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN consultarConexionUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            ibPerfilesPjDTO.setRespuesta(respuesta);
        }
        return ibPerfilesPjDTO;
    }

    /**
     * Metodo que resetea el login y el password de un IbUsuarioPj
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    @Override
    public UtilDTO resetearIbUsuarioPj(String idUsuario, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = ibEmpresasUsuariosPjFacade.consultarDatosEmpresaUsuario(new BigDecimal(idUsuario));
            if (ibEmpresasUsuariosPj != null) {
                for (IbEmpresasUsuariosPj ibEmpresaUsuariosPj : ibEmpresasUsuariosPj) {
                    //SE ACTUALIZAN LOS ESTATUS DEL USUARIO DEL OBJETO USUARIO
                    ibEmpresaUsuariosPj.getIdUsuarioPj().setEstatusAcceso(String.valueOf(EstatusAccesoEnum.INACTIVO.getId()).charAt(0));
                    ibEmpresaUsuariosPj.getIdUsuarioPj().setEstatusRegistro(String.valueOf(EstatusRegistroUsuarioEnum.POR_COMPLETAR_AFILIACION.getId()).charAt(0));
                    //SE LIMPIA LA CLAVE Y EL PASSWORD PARA PERMITIR QUE SE REGISTRE NUEVAMENTE
                    ibEmpresaUsuariosPj.getIdUsuarioPj().setLogin(null);
                    ibEmpresaUsuariosPj.getIdUsuarioPj().setClave(null);
                    //SE ACTUALIZAN LOS ESTATUS DEL USUARIO EN LAS EMPRESAS A LAS QUE ESTA ASOCIADA
                    ibEmpresaUsuariosPj.setEstatusAcceso(String.valueOf(EstatusAccesoEnum.INACTIVO.getId()).charAt(0));
                    ibEmpresaUsuariosPj.setEstatusRegistro(String.valueOf(EstatusRegistroUsuarioEnum.POR_COMPLETAR_AFILIACION.getId()).charAt(0));
                    ibEmpresasUsuariosPjFacade.modificarPj(ibEmpresaUsuariosPj);

                    //DATOS PARA EL ENVIO DE CORREO Y SMS
                    //SIEMPRE SE ENVIA EL CODIGO 1 AL SP
                    //String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
                    Integer codigoPlantilla = EstatusPlantillasEmail.REGISTRO_EMPRESAUSUARIO_TEXT.getId();
                    String parametrosCorreo = ibEmpresaUsuariosPj.getIdEmpresa().getNombre() + "~" + ibEmpresaUsuariosPj.getIdUsuarioPj().getNombre();
                    
                    OtpDTO otpDTO = otpDAO.generarOTPEnviarCorreoPj(ibEmpresaUsuariosPj.getIdUsuarioPj().getId().toString(), codigoPlantilla, ibEmpresaUsuariosPj.getEmail(), parametrosCorreo, codigoCanal);
                    if (otpDTO != null && otpDTO.getOvOTP() != null) {
                        String textoSMSOTP = textosFacade.textoParametro("pjw.global.sms.otp.registrousuario", codigoCanal);
                        textoSMSOTP = textoSMSOTP.replace("$POTP", otpDTO.getOvOTP());
                        //SMS PARA OTP
                        smsDAO.enviarSMS(ibEmpresaUsuariosPj.getTelfCelular(), textoSMSOTP, codigoCanal);
                    }
                    
                    /*
                    // envio de correo a representantes legales de la empresa
                    IbEmpresasUsuariosPjDTO result = ibEmpresasUsuariosDAO.consultarEmpresasUsuariosPjRepresentanteLegal(ibEmpresaUsuariosPj.getIdEmpresa().getId().toString(), idCanal, codigoCanal);
                    //SI EXISTEN REPRESENTANTES LEGALES ASOCIADOS A LA EMPRESA
                    if (result != null && result.getIbEmpresasUsuariosPj() != null) {
                        for (IbEmpresasUsuariosPj ibEmUsu : result.getIbEmpresasUsuariosPj()) {
                            emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla.toString(), ibEmUsu.getEmail(), parametrosCorreo, idCanal, codigoCanal);
                        }
                    }
                    */
                }
            } else {
                throw new NoResultException();
            }
        } catch (NoResultException e) {
            logger.error(new StringBuilder("ERROR JPA EN cerrarConexionUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN cerrarConexionUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }
    
    /**
     * Metodo que desbloquea a un usuario de la base de datos
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    @Override
    public UtilDTO desbloquearIbUsuarioPj(String idUsuario, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = ibEmpresasUsuariosPjFacade.consultarDatosEmpresaUsuario(new BigDecimal(idUsuario));
            if (ibEmpresasUsuariosPj != null) {
                for (IbEmpresasUsuariosPj ibEmpresaUsuariosPj : ibEmpresasUsuariosPj) {
                    //SE ACTUALIZAN LOS ESTATUS DEL USUARIO DEL OBJETO USUARIO
                    ibEmpresaUsuariosPj.getIdUsuarioPj().setEstatusAcceso(String.valueOf(EstatusAccesoEnum.ACTIVO.getId()).charAt(0));
                    ibEmpresaUsuariosPj.getIdUsuarioPj().setIntentosFallidos(BigInteger.ZERO);
                    ibEmpresaUsuariosPj.getIdUsuarioPj().setIntentosFallidosPreguntas(BigInteger.ZERO);
                    ibEmpresaUsuariosPj.setEstatusAcceso(String.valueOf(EstatusAccesoEnum.ACTIVO.getId()).charAt(0));
                    ibEmpresasUsuariosPjFacade.modificarPj(ibEmpresaUsuariosPj);                    
                }
            } else {
                throw new NoResultException();
            }
        } catch (NoResultException e) {
            logger.error(new StringBuilder("ERROR JPA EN cerrarConexionUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN cerrarConexionUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }
}
