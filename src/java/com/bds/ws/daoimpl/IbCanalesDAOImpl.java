/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.IbCanalesDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbCanalDTO;
import com.bds.ws.dto.IbParametrosDTO;
import com.bds.ws.dto.IbUsuarioDTO;
import com.bds.ws.dto.IbUsuariosCanalesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.exception.JPAException;
import com.bds.ws.facade.IbCanalFacade;
import com.bds.ws.facade.IbParametrosFacade;
import com.bds.ws.facade.IbTextosFacade;
import com.bds.ws.facade.IbUsuariosCanalesFacade;
import com.bds.ws.facade.IbUsuariosFacade;
import com.bds.ws.model.IbCanal;
import com.bds.ws.model.IbUsuariosCanales;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 * Claque que implementa la interfaz IbCanalesDAO
 *
 * @author renseld.lugo
 */
@Named("IbCanalesDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbCanalesDAOImpl extends DAOUtil implements IbCanalesDAO {

    private static final Logger logger = Logger.getLogger(IbCanalesDAOImpl.class.getName());

    @EJB
    private IbUsuariosCanalesFacade ibUsuariosCanalesFacade;

    @EJB
    private IbParametrosFacade ibParametrosFacade;

    @EJB
    private IbUsuariosFacade ibUsuariosFacade;

    @EJB
    private IbCanalFacade canalFacade;

    @EJB
    public ActuacionesDAO actuaciones;

    @EJB
    private IbTextosFacade ibTextosFacade;

    /**
     * Obtiene la fecha de la ultima conexion del cliente al canal
     *
     * @param idUsuario String Numero o id del cliente.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return UtilDTO Contiene la ultima hora de conexion
     */
    @Override
    public UtilDTO consultarUltimaConexionCanal(String idUsuario, String idCanal, String nombreCanal) {
        IbUsuariosCanalesDTO usuariosCanalesDTO = null;
        UtilDTO utilDTO = new UtilDTO();
        Map resultados = new HashMap();
        try {
            usuariosCanalesDTO = ibUsuariosCanalesFacade.consultaUsuarioCanal(idUsuario, idCanal);

            if (usuariosCanalesDTO.getRespuesta().getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                //String reportDate = IbCanalesDAOImpl.formatearFecha(usuariosCanalesDTO.getIbUsuarioCanal().getFechaHoraUltimaInteraccion(), FORMATO_FECHA_COMPLETA);
                resultados.put(1, usuariosCanalesDTO.getIbUsuarioCanal().getFechaHoraUltimaInteraccion());
                utilDTO.setResulados(resultados);
                utilDTO.setRespuesta(usuariosCanalesDTO.getRespuesta());
            } else {
                utilDTO.setRespuesta(usuariosCanalesDTO.getRespuesta());
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultarUltimaConexionCanal: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultarUltimaConexionCanal: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * Metodo que realiza el bloqueo de acceso a canales
     *
     * @param idUsuario Id de usuario a bloquear
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o
     * no
     */
    @Override
    public RespuestaDTO bloquearAccesoCanal(String idUsuario, String idCanal, String nombreCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        IbUsuariosCanalesDTO ibUsuariosCanalesDTO = null;
        IbUsuariosCanales ibUsuariosCanales = null;
        try {

            ibUsuariosCanalesDTO = ibUsuariosCanalesFacade.consultaUsuarioCanal(idUsuario, idCanal);

            if (ibUsuariosCanalesDTO.getRespuesta().getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                ibUsuariosCanalesDTO.getIbUsuarioCanal().setEstatusAcceso(CODIGO_BLOQUEO_CANAL);
                ibUsuariosCanales = ibUsuariosCanalesFacade.modificar(ibUsuariosCanalesDTO.getIbUsuarioCanal());
                if (!ibUsuariosCanales.getEstatusAcceso().equalsIgnoreCase(CODIGO_BLOQUEO_CANAL)) {
                    respuesta.setCodigo("JPA001");
                    respuesta.setDescripcion(DESCRIPCION_RESPUESTA_FALLIDA);
                    throw new JPAException();
                }
            } else {
                respuesta = ibUsuariosCanalesDTO.getRespuesta();
            }
        } catch (JPAException e) {

            logger.error( new StringBuilder("ERROR JPA bloqueaAccsoAlCanal: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA IbCanalesDAOImpl: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN IbCanalesDAOImpl: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;
    }

    /**
     * Metodo que realiza la validacion para que no existan dos sessiones
     * simultaneas de un mismo usuario
     *
     * @param idUsuario id del usuario
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return UtilDTO (False: si no existe otra conexion | true: si existe otra
     * conexion)
     */
    @Override
    public UtilDTO validarConexionSimultanea(String idUsuario, String idCanal, String nombreCanal) {
        IbUsuariosCanalesDTO usuariosCanalesDTO;
        IbParametrosDTO ibParametrosDTO;
        IbCanal canal;
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Date fInicio = null;
        Date fActual = new Date();
        List<IbCanal> canales = null;               //listado de canales disponibles para IB
        //variable para almacenar la cant de minutos entre conexiones
        long minutos = 0;
        //variable para almacenar el valor del timeOut de sesion regitrado en BD
        long valor = 0;
        Map resultados = new HashMap();
        try {
            usuariosCanalesDTO = ibUsuariosCanalesFacade.consultaUsuarioCanales(idUsuario, idCanal);
            resultados.put(1, false);

            if (usuariosCanalesDTO.getRespuesta().getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                for (IbUsuariosCanales usrCanal : usuariosCanalesDTO.getIbUsuariosCanales()) {
                    if (CODIGO_SESSION_ACIVA.equalsIgnoreCase(usrCanal.getInicioSesion().toString())) {
                        fInicio = usrCanal.getFechaHoraUltimaInteraccion();
                        minutos = calculaMinutosEntreFechas(fInicio, fActual);
                        ibParametrosDTO = ibParametrosFacade.consultaParametro(CODIGO_TIME_OUT, nombreCanal);

                        if (ibParametrosDTO.getRespuesta().getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                            valor = Long.parseLong(ibParametrosDTO.getIbParametro().getValor());
                            if (minutos < valor) {
                                resultados.put(1, true);
                                break;
                            }
                            utilDTO.setRespuesta(respuesta);
                        } else {
                            utilDTO.setRespuesta(usuariosCanalesDTO.getRespuesta());
                        }
                    }
                }
            } else {
                utilDTO.setRespuesta(usuariosCanalesDTO.getRespuesta());
            }

            utilDTO.setResulados(resultados);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarConexionSimultanea: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            utilDTO.setRespuesta(respuesta);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarConexionSimultanea: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * metodo para validar el acceso a la banca movil via app mobile NOTA este
     * metodo es temporal
     *
     * @param numeroTarjeta numero de tarjeta de 20 digitos
     * @param password password de acceso al IB
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbParametrosDTO con el nombre del usuario y la fecha dela ultima
     * conexion si el acceso es correcto
     */
    @Override
    public UtilDTO loginIB(String numeroTarjeta, String password, String idCanal, String nombreCanal) {
        UtilDTO parametros = new UtilDTO();
        Map mapaParametros = new HashMap();
        RespuestaDTO respuesta = new RespuestaDTO();
        IbUsuariosCanalesDTO usuariosCanalesDTO = new IbUsuariosCanalesDTO();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_LOGIN", 5, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida
            statement.setString(1, numeroTarjeta);
            statement.setString(2, password);
            statement.setString(3, nombreCanal);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            statement.registerOutParameter(5, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            respuesta = actuaciones.obtenerDescripcionSalidaSP(new String(this.statement.getBytes(5), CHARSET_ORACLE_9), nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            } else if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                mapaParametros.put("codCliente", new String(this.statement.getBytes(4), CHARSET_ORACLE_9));
            }

            parametros.setResulados(mapaParametros);
            parametros.setRespuesta(respuesta);

        } catch (DAOException d) {
            parametros.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN loginIB: ")
                    .append("TDD-").append(this.numeroCuentaFormato(numeroTarjeta))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            parametros.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN loginIB: ")
//                    .append("TDD-").append(this.numeroCuentaFormato(numeroTarjeta))
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return parametros;
    }

    /**
     * inserta la realcion de usuario con canal
     *
     * @param codUsuario el codigo del usuario a asociar
     * @param idSesion indica el id de la sesion
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o
     * no
     */
    @Override
    public RespuestaDTO insertarUsuarioCanal(String codUsuario, String idSesion, String idCanal, String nombreCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            IbUsuariosCanales usuarioCanal = new IbUsuariosCanales();
            usuarioCanal.setId(BigDecimal.ZERO);//seteo de id por defcto en BD se reemplaza por la secuencia
            usuarioCanal.setIdUsuario(ibUsuariosFacade.ibUsuarioPorCodUsuario(codUsuario, nombreCanal).getUsuario());
            usuarioCanal.setIdCanal(canalFacade.consultaCanalNombre(nombreCanal));
            usuarioCanal.setEstatusAcceso("A");//cuando se crea el usuario se hace con el estatus 'A' = Activo
            usuarioCanal.setEstatusRegistro("A");//para es status de registro igual y automaticamente se coloca

            if (idCanal.equalsIgnoreCase(CODIGO_CANAL_WEB)) {
                usuarioCanal.setLimiteInternas(new BigDecimal(ibParametrosFacade.consultaParametro("pnw.limite.trans.propias", nombreCanal).getIbParametro().getValor()));
                usuarioCanal.setLimiteInternasTerceros(new BigDecimal(ibParametrosFacade.consultaParametro("pnw.limite.trans.tercerosdelsur", nombreCanal).getIbParametro().getValor()));
                usuarioCanal.setLimiteExternas(new BigDecimal(ibParametrosFacade.consultaParametro("pnw.limite.trans.propiasotrosbancos", nombreCanal).getIbParametro().getValor()));
                usuarioCanal.setLimiteExternasTerceros(new BigDecimal(ibParametrosFacade.consultaParametro("pnw.limite.trans.otrosbancos", nombreCanal).getIbParametro().getValor()));
            } else {
                if (idCanal.equalsIgnoreCase(CODIGO_CANAL_MOBILE)) {
                    usuarioCanal.setLimiteInternas(new BigDecimal(ibParametrosFacade.consultaParametro("limite.trans.propias", nombreCanal).getIbParametro().getValor()));
                    usuarioCanal.setLimiteInternasTerceros(new BigDecimal(ibParametrosFacade.consultaParametro("limite.trans.tercerosdelsur", nombreCanal).getIbParametro().getValor()));
                    usuarioCanal.setLimiteExternas(new BigDecimal(ibParametrosFacade.consultaParametro("limite.trans.propiasotrosbancos", nombreCanal).getIbParametro().getValor()));
                    usuarioCanal.setLimiteExternasTerceros(new BigDecimal(ibParametrosFacade.consultaParametro("limite.trans.otrosbancos", nombreCanal).getIbParametro().getValor()));
                }
            }

            usuarioCanal.setInicioSesion('0');  //el inicion de sesion en '1' = conectado
            usuarioCanal.setIntentosFallidos(0);
            usuarioCanal.setFechaHoraUltimaInteraccion(new Date());
            usuarioCanal.setIdSesion(idSesion);

            ibUsuariosCanalesFacade.create(usuarioCanal);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO insertarUsuarioCanal: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN insertarUsuarioCanal: ")
//                    .append("USR-").append(codUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return respuesta;
    }

    /**
     * Actualiza la realcion de usuario con canal
     *
     * @param idUsuario el id del usuario a asociar
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @param estatusConexion Char estatus de la conexion.
     * @param intentosFallidos cant de intentos fallidos de acceso
     * @param idSesion identificador unico de la session
     * @param estatusAcceso estatus de acceso al canal
     * @param estatusRegistro estatus de registro
     * @param limiteInternas limite de monto de operaciones propias en DELSUR
     * @param limiteExternas limite de monto de operaciones propias en otros
     * bancos
     * @param limiteInternasTerc limite de monto de operaciones terceros en
     * DELSUR
     * @param limiteExternasTerc limite de monto de operaciones terceros en
     * otros bancos
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o
     * no
     */
    @Override
    public RespuestaDTO actualizarUsuarioCanal(String idUsuario, String idCanal, String nombreCanal, char estatusConexion, int intentosFallidos, String idSesion,
            String estatusAcceso, String estatusRegistro, BigDecimal limiteInternas, BigDecimal limiteExternas, BigDecimal limiteInternasTerc, BigDecimal limiteExternasTerc) {
        RespuestaDTO respuesta = new RespuestaDTO();
        IbUsuariosCanalesDTO ibUsuarioCanalesDTO = null;
        IbUsuariosCanales ibUsuariosCanales = null;
        try {

            if (idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("")
                    || idCanal == null || idCanal.isEmpty() || idCanal.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                throw new DAOException("DAO008");
            }

            ibUsuarioCanalesDTO = ibUsuariosCanalesFacade.consultaUsuarioCanal(idUsuario, idCanal);

            if (ibUsuarioCanalesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                ibUsuariosCanales = ibUsuarioCanalesDTO.getIbUsuarioCanal();

                if (String.valueOf(estatusConexion) != null && !String.valueOf(estatusConexion).trim().isEmpty()) {
                    ibUsuariosCanales.setInicioSesion(estatusConexion);
                }
                ibUsuariosCanales.setFechaHoraUltimaInteraccion(Calendar.getInstance().getTime());
                if (String.valueOf(intentosFallidos) != null && !String.valueOf(intentosFallidos).trim().isEmpty()) {
                    ibUsuariosCanales.setIntentosFallidos(intentosFallidos);
                }
                if (idSesion != null && !idSesion.isEmpty()) {
                    ibUsuariosCanales.setIdSesion(idSesion);
                }
                if (estatusAcceso != null && !estatusAcceso.isEmpty()) {
                    ibUsuariosCanales.setEstatusAcceso(estatusAcceso);
                }
                if (estatusRegistro != null && !estatusRegistro.isEmpty()) {
                    ibUsuariosCanales.setEstatusRegistro(estatusRegistro);
                }
                if (limiteInternas != null) {
                    ibUsuariosCanales.setLimiteInternas(limiteInternas);
                }
                if (limiteExternas != null) {
                    ibUsuariosCanales.setLimiteExternas(limiteExternas);
                }
                if (limiteInternasTerc != null) {
                    ibUsuariosCanales.setLimiteInternasTerceros(limiteInternasTerc);
                }
                if (limiteExternasTerc != null) {
                    ibUsuariosCanales.setLimiteExternasTerceros(limiteExternasTerc);
                }

                ibUsuariosCanalesFacade.modificar(ibUsuariosCanales);

            } else {
                respuesta = ibUsuarioCanalesDTO.getRespuesta();
            }

        } catch (DAOException e) {
            logger.error( new StringBuilder("ERROR DAO EN actualizarUsuarioCanales: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo("DAO008");//revisar el log
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO actualizarUsuarioCanales: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN actualizarUsuarioCanales: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return respuesta;
    }

    /**
     * Permite consultar el UsuarioCanal a traves del id del usuario y id del
     * canal
     *
     * @param idUsuario id del usuario
     * @param idCanal id del canal
     * @return IbUsuariosCanalesDTO Datos referentes al usuario canal
     */
    @Override
    public IbUsuariosCanalesDTO consultarUsuarioCanalporIdUsuario(String idUsuario, String idCanal) {
        IbUsuariosCanalesDTO ibUsuariosCanalesDTO = new IbUsuariosCanalesDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        String nombreCanal = "";

        try {
            ibUsuariosCanalesDTO = ibUsuariosCanalesFacade.consultaUsuarioCanal(idUsuario, idCanal);

            IbCanal canal = canalFacade.consultaCanalID(idCanal);
            nombreCanal = canal.getNombre();

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultarUsuarioCanalporIdUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            ibUsuariosCanalesDTO.setRespuesta(respuesta);

        }
//        if (ibUsuariosCanalesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultarUsuarioCanalporIdUsuario: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return ibUsuariosCanalesDTO;

    }

    /**
     * Metodo para validar el estatus de registro y de acceso del usuario en
     * IB_USUARIOS_CANALES
     *
     * @param codUsuario codigo del usuario
     * @param idCanal id del canal
     * @param nombreCanal nombre del canal
     * @return Si esta bloqueado en alguno de los dos retorna el codigoSP y
     * textoSP
     */
    @Override
    public RespuestaDTO validarUsuarioAccesoRegistro(String codUsuario, String idCanal, String nombreCanal) {
        IbUsuariosCanalesDTO usuariosCanalesDTO = new IbUsuariosCanalesDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            usuariosCanalesDTO = ibUsuariosCanalesFacade.consultaUsrCHPorCod(codUsuario, idCanal);
            if (!usuariosCanalesDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            } else if (usuariosCanalesDTO.getIbUsuarioCanal().getEstatusAcceso().equals(CODIGO_BLOQUEO_CANAL) || usuariosCanalesDTO.getIbUsuarioCanal().getEstatusRegistro().equals(CODIGO_BLOQUEO_CANAL)) {
                respuesta.setCodigoSP("JPA006");
                respuesta.setTextoSP(ibTextosFacade.textoParametro("JPA006", nombreCanal));
            }

        } catch (DAOException e) {
            logger.error( new StringBuilder("ERROR DAO validarUsuarioAccesoRegistro: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta = usuariosCanalesDTO.getRespuesta();
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO validarUsuarioAccesoRegistro: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            respuesta.setDescripcion(DESCRIPCION_RESPUESTA_FALLIDA);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarUsuarioAccesoRegistro: ")
//                    .append("USR-").append(codUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;
    }

    /**
     * Metodo para obtener el listado de canales
     *
     * @param codUsuario codigo del usuario para efectos de registro en el log
     * @param nombreCanal nombre del canal para efectos de registro en el log
     * @return IbCanalDTO Listado de canales para un usuario
     */
    @Override
    public IbCanalDTO listadoCanales(String codUsuario, String nombreCanal) {
        IbCanalDTO canalDTO = new IbCanalDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {

            if (codUsuario == null || codUsuario.isEmpty() || codUsuario.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                canalDTO.getRespuesta().setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            canalDTO = canalFacade.listaCanales(nombreCanal);

            if (!canalDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

            if (canalDTO.getCanales().isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listadoCanales: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoCanales: ")
                    .append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            canalDTO.setRespuesta(respuesta);
        }
//        if (canalDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoCanales: ")
//                    .append("USR-").append(codUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return canalDTO;
    }

    /**
     * metodo para validar bin de una TDD
     *
     * @param numeroBin fragmento de Bin de la TDD
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return UtilDTO con el resultardo de la validacion del bin
     */
    public UtilDTO validarBin(String numeroBin, String nombreCanal) {
        UtilDTO parametros = new UtilDTO();
        Map mapaParametros = new HashMap();
        RespuestaDTO respuesta = new RespuestaDTO();
        IbUsuariosCanalesDTO usuariosCanalesDTO = new IbUsuariosCanalesDTO();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            /**
             * *********************AGREGAR SP***************************
             */
            respuesta = this.generarQuery("IB_K_XXXXXX", "XXXXXX", 3, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida
            statement.setString(1, numeroBin);
            statement.registerOutParameter(2, OracleTypes.VARCHAR);
            statement.registerOutParameter(3, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            respuesta = actuaciones.obtenerDescripcionSalidaSP(new String(this.statement.getBytes(3), CHARSET_ORACLE_9), nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            } else if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                mapaParametros.put("binValido", new String(this.statement.getBytes(2), CHARSET_ORACLE_9));
            }

            parametros.setResulados(mapaParametros);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarBin: ")
                    .append("BIN-").append(this.numeroCuentaFormato(numeroBin))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            parametros.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarBin: ")
//                    .append("BIN-").append(this.numeroCuentaFormato(numeroBin))
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return parametros;
    }

    /**
     * metodo que retorna la TDD activa para un codigo de cliente en Oracle9
     *
     * @param codCliente codigo de cliente Ora9
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return UtilDTO con el resultardo de la validacion del la TDD en Ora9
     *
     */
    @Override
    public UtilDTO validarTDDActivaCore(String codCliente, String nombreCanal) {
        UtilDTO parametros = new UtilDTO();
        Map mapaParametros = new HashMap();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_OBTENER_TARJETA_ACTIVA", 3, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(codCliente));
            statement.setString(2, nombreCanal);
            statement.registerOutParameter(3, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            mapaParametros.put("tddActiva", this.statement.getBytes(3) != null ? new String(this.statement.getBytes(3), CHARSET_ORACLE_9) : "");
            parametros.setResulados(mapaParametros);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarTDDActivaCore: ")
                    .append("CODCLIENTE-").append(codCliente)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            parametros.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarTDDActivaCore: ")
//                    .append("CODCLIENTE-").append(codCliente)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return parametros;
    }

    /**
     * metodo que retorna la TDD activa para un codigo de cliente en Oracle9
     *
     * @param codCliente codigo de cliente Ora9
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * @param numeroTDD String numero TDD transaccion.
     * @return UtilDTO con el resultardo de la validacion del la TDD en Ora9
     *
     */
    @Override
    public UtilDTO validarTDDActivaCore(String codCliente, String nombreCanal, String numeroTDD) {
        UtilDTO parametros = new UtilDTO();
        Map mapaParametros = new HashMap();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_OBTENER_TARJETA_ACTIVA", 4, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(codCliente));
            statement.setString(2, numeroTDD);
            statement.setString(3, nombreCanal);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            mapaParametros.put("tddActiva", this.statement.getBytes(4) != null ? new String(this.statement.getBytes(4), CHARSET_ORACLE_9) : "");
            parametros.setResulados(mapaParametros);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarTDDActivaCore: ")
                    .append("CODCLIENTE-").append(codCliente)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            parametros.setRespuesta(respuesta);
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarTDDActivaCore: ")
//                    .append("CODCLIENTE-").append(codCliente)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return parametros;
    }

    /**
     * metodo para validar que un TDD se encuentre activa en Oracle9
     *
     * @param numeroTDD numero de la TDD a validar
     * @param clave contrasenna de acceso encriptada
     * @param idCanal identificador unico del canal
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return UtilDTO con el resultardo de la validacion del bin
     *
     */
    @Override
    public UtilDTO validarLogin(String numeroTDD, String clave, String idCanal, String nombreCanal) {
        UtilDTO parametros = new UtilDTO();
        Map mapaParametros = new HashMap();
        RespuestaDTO respuesta = new RespuestaDTO();
        IbCanal canal = null;
        IbUsuarioDTO usuario = null;
        IbUsuariosCanalesDTO usuariosCanalesDTO = null;
        try {
            mapaParametros.put("login", "false");
            mapaParametros.put("usuario", null);
            parametros.setResulados(mapaParametros);
            //VALIDACION DE CANAL VALIDO
            if (idCanal != null && !idCanal.trim().equalsIgnoreCase("")) {
                canal = canalFacade.find(new BigDecimal(idCanal));
                if (canal == null || canal.getId() == null) {
                    respuesta.setDescripcionSP("error.login.canalInv");
                    respuesta.setCodigoSP(CODIGO_ERROR_VALIDACIONES);
                    parametros.setRespuesta(respuesta);
                    return parametros;
                }
                //VALIDACION DE CANAL ACTIVO
                if (ibParametrosFacade.consultaParametro("canal.estatus", canal.getId(), canal.getCodigo()).getIbParametro().getValor().equalsIgnoreCase("0")) {
                    respuesta.setDescripcionSP("error.login.canalInactivo");
                    respuesta.setCodigoSP(CODIGO_ERROR_VALIDACIONES);
                    parametros.setRespuesta(respuesta);
                    return parametros;
                }
            } else {
                respuesta.setDescripcionSP("error.login.canalInv");
                respuesta.setCodigoSP(CODIGO_ERROR_VALIDACIONES);
                parametros.setRespuesta(respuesta);
                return parametros;
            }
            //VALIDACION DE LONGITUD DE TDD
            if (numeroTDD == null || numeroTDD.length() != 16) {
                respuesta.setDescripcionSP("error.usuario.invalido");
                respuesta.setCodigoSP(CODIGO_ERROR_VALIDACIONES);
                parametros.setRespuesta(respuesta);
                return parametros;
            }
            //VALIDACION DE CARACTERES DISTINTOS A NUMEROS EN TDD
            if (numeroTDD.matches("[^\\d]")) {
                respuesta.setDescripcionSP("error.usuario.invalido");
                respuesta.setCodigoSP(CODIGO_ERROR_VALIDACIONES);
                parametros.setRespuesta(respuesta);
                return parametros;
            }

            //VALIDACION DE BIN BANCARIO 
            if (!numeroTDD.substring(0, 6).equalsIgnoreCase(ibParametrosFacade.consultaParametro("bin.tdd", nombreCanal).getIbParametro().getValor())) {
                respuesta.setDescripcionSP("error.usuario.invalido");
                respuesta.setCodigoSP(CODIGO_ERROR_VALIDACIONES);
                parametros.setRespuesta(respuesta);
                return parametros;
            }

            //VALIDACION DE REGISTRO DE USUARIO
            usuario = ibUsuariosFacade.ibUsuarioPorTDD(numeroTDD, nombreCanal);
            //usuario = ibUsuariosFacade.ibUsuarioPorTDDClave(numeroTDD, clave, nombreCanal);

            respuesta = usuario.getRespuesta();
            if (respuesta.getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) && !respuesta.getCodigoSP().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                respuesta.setDescripcionSP("error.usuario.noAfiliado");
                mapaParametros.put("login", "false");
                mapaParametros.put("usuario", null);
                parametros.setResulados(mapaParametros);
                respuesta.setCodigoSP(CODIGO_ERROR_VALIDACIONES);
                parametros.setRespuesta(respuesta);
                //SALIMOS YA QUE LA TDD NO EXISTE
                return parametros;
            } else {
                if (respuesta.getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                    if (usuario.getUsuario() == null || usuario.getUsuario().getId() == null) {
                        respuesta.setDescripcionSP("error.usuario.invalido");
                        mapaParametros.put("login", "false");
                        mapaParametros.put("usuario", null);
                        parametros.setResulados(mapaParametros);
                        respuesta.setCodigoSP(CODIGO_ERROR_VALIDACIONES);
                        parametros.setRespuesta(respuesta);
                        //SALIMOS YA QUE LA TDD NO EXISTE
                        return parametros;
                    } else {
                        //VALIDACION DE USUARIO Y PASSWORD
                        if (usuario.getUsuario() == null || usuario.getUsuario().getId() == null || usuario.getUsuario().getClave() == null || usuario.getUsuario().getClave().equals("")) {
                            respuesta.setDescripcionSP("error.usuario.noAfiliado");
                            respuesta.setCodigoSP(CODIGO_ERROR_VALIDACIONES);
                            parametros.setRespuesta(respuesta);
                            //SALIMOS YA QUE EL USUARIO NO ESTA AFILIADO AL CANAL
                            return parametros;
                        } else {
                            if (usuario.getUsuario() != null && usuario.getUsuario().getId() != null && usuario.getUsuario().getClave().equals(clave)) {
                                mapaParametros.put("usuario", usuario);
                                parametros.setResulados(mapaParametros);
                                parametros.setRespuesta(respuesta);
                            } else {
                                respuesta.setDescripcionSP("error.usuario.invalido");
                                parametros.setRespuesta(respuesta);
                                //SALIMOS YA QUE EL PASS NO CORRESPONDE
                                return parametros;
                            }
                        }
                    }
                } else {
                    //ERROR EN LA CONSULTA
                    throw new DAOException();
                }
            }
            //VALIDACION DE ASOCIACION DE REGISTRO DE USUARIO POR CANAL
            usuariosCanalesDTO = ibUsuariosCanalesFacade.consultaUsuarioCanal(usuario.getUsuario().getId().toString(), idCanal);
            respuesta = usuariosCanalesDTO.getRespuesta();
            if (respuesta.getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                if (usuariosCanalesDTO.getIbUsuarioCanal() == null || usuariosCanalesDTO.getIbUsuarioCanal().getId() == null) {
                    respuesta.setDescripcionSP("error.usuario.noAfiliado");
                    respuesta.setCodigoSP(CODIGO_ERROR_VALIDACIONES);
                    parametros.setRespuesta(respuesta);
                    //SALIMOS YA QUE EL USUARIO NO ESTA AFILIADO AL CANAL
                    return parametros;
                } else {
                    //VALIDACION DE ESTATUS DE ACCESOS
                    if (usuariosCanalesDTO.getIbUsuarioCanal().getEstatusAcceso() == null
                            || !usuariosCanalesDTO.getIbUsuarioCanal().getEstatusAcceso().equalsIgnoreCase("A")
                            || usuariosCanalesDTO.getIbUsuarioCanal().getEstatusRegistro() == null
                            || !usuariosCanalesDTO.getIbUsuarioCanal().getEstatusRegistro().equalsIgnoreCase("A")) {
                        respuesta.setDescripcionSP("error.usuario.bloqueado");
                        respuesta.setCodigoSP(CODIGO_ERROR_VALIDACIONES);
                        parametros.setRespuesta(respuesta);
                        //SALIMOS YA QUE EL USUARIO ESTA BLOQUEADO
                        return parametros;
                    } else {
                        //ACCESO EXITOSO
                        mapaParametros.put("usuario", usuario);
                        mapaParametros.put("login", "true");
                        parametros.setResulados(mapaParametros);
                        parametros.setRespuesta(respuesta);
                    }
                }
            } else {
                throw new DAOException();
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarLogin: ")
                    .append("TDD-").append(this.numeroCuentaFormato(numeroTDD))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            parametros.setRespuesta(respuesta);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarLogin: ")
//                    .append("TDD-").append(this.numeroCuentaFormato(numeroTDD))
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return parametros;
    }

    @Override
    public IbCanalDTO consultaCanalesPorUsuario(String idUsuario, String idcanal, String nombreCanal) {
        IbCanalDTO canalDTO = new IbCanalDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {

            if (idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("")
                    || idcanal == null || idcanal.isEmpty() || idcanal.equalsIgnoreCase("")) {
                canalDTO.getRespuesta().setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            canalDTO = ibUsuariosCanalesFacade.consultaCanalesPorUsuario(idUsuario, idcanal);

            if (!canalDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

            if (canalDTO.getCanales().isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN consultaCanalesPorUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN consultaCanalesPorUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            canalDTO.setRespuesta(respuesta);
        }
//        if (canalDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultaCanalesPorUsuario: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return canalDTO;

    }

}
