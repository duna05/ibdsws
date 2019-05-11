/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.EMailDAO;
import com.bds.ws.dao.IbEmpresasDAO;
import com.bds.ws.dao.IbEmpresasUsuariosDAO;
import com.bds.ws.dao.IbManejadorTransaccionesDAO;
import com.bds.ws.dao.IbPregDesafioUsuarioPjDAO;
import com.bds.ws.dao.IbUsuariosPjDAO;
import com.bds.ws.dao.OtpDAO;
import com.bds.ws.dao.SMSDAO;
import com.bds.ws.dto.IbEmpresasUsuariosPjDTO;
import com.bds.ws.dto.IbUsuariosPjDTO;
import com.bds.ws.dto.OtpDTO;
import com.bds.ws.dto.PreguntaRespuestaUsuarioDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusAccesoEnum;
import com.bds.ws.enumerated.EstatusPlantillasEmail;
import com.bds.ws.enumerated.EstatusRegistroUsuarioEnum;
import com.bds.ws.enumerated.PerfilAccesoEnum;
import com.bds.ws.facade.IbCtasEmpresaPjFacade;
import com.bds.ws.facade.IbCtasEmpresaUsuarioPjFacade;
import com.bds.ws.facade.IbEmpresasUsuariosPjFacade;
import com.bds.ws.facade.IbHistoricoClavesPjFacade;
import com.bds.ws.facade.IbMontosUsuariosPjFacade;
import com.bds.ws.facade.IbServiEmpreUsuariosPjFacade;
import com.bds.ws.facade.IbServiciosPerfilesPjFacade;
import com.bds.ws.facade.IbTextosFacade;
import com.bds.ws.facade.IbUsuariosPjFacade;
import com.bds.ws.model.IbCanal;
import com.bds.ws.model.IbCtasEmpresaPj;
import com.bds.ws.model.IbCtasEmpresaUsuarioPj;
import com.bds.ws.model.IbEmpresas;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import com.bds.ws.model.IbMontosUsuariosPj;
import com.bds.ws.model.IbServiEmpreUsuariosPj;
import com.bds.ws.model.IbServiciosPerfilesPj;
import com.bds.ws.model.IbUsuariosPj;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author luis.perez
 */
@Named("IbManejadorTransaccionesDAO")
@Stateless
public class IbManejadorTransaccionesDAOImpl extends DAOUtil implements IbManejadorTransaccionesDAO {

    @EJB
    private IbEmpresasDAO ibEmpresasDAO;
    @EJB
    private IbEmpresasUsuariosDAO ibEmpresasUsuariosDAO;
    @EJB
    private IbUsuariosPjDAO ibUsuariosPjDAO;
    @EJB
    private IbMontosUsuariosPjFacade ibMontosUsuariosPjFacade;
    @EJB
    private SMSDAO smsDAO;
    @EJB
    IbTextosFacade textosFacade;
    @EJB
    private EMailDAO emailDAO;
    @EJB
    private IbPregDesafioUsuarioPjDAO ibPregDesafioUsuarioPjDAO;
    @EJB
    private OtpDAO otpDAO;
    @EJB
    private IbEmpresasUsuariosPjFacade ibEmpresasUsuariosPjFacade;
    @EJB
    private IbHistoricoClavesPjFacade ibHistoricoClavesPjFacade;
    @EJB
    private IbUsuariosPjFacade ibUsuariosPjFacade;
    @EJB
    private IbServiEmpreUsuariosPjFacade ibServiEmpreUsuariosPjFacade;
    @EJB
    private IbServiciosPerfilesPjFacade ibServiciosPerfilesPjFacade;
    @EJB
    private IbCtasEmpresaUsuarioPjFacade ibCtasEmpresaUsuarioPjFacade;
    @EJB
    private IbCtasEmpresaPjFacade ibCtasEmpresaPjFacade;
    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbManejadorTransaccionesDAOImpl.class.getName());

    final int RADIX = 10;

    /**
     * La creacion del usuario y la empresa con todos sus datos asociados
     *
     * @param ibEmpresasUsuariosPjDTO
     * @param idCanal
     * @param codigoCanal
     *
     * @return UtilDTO
     */
    @Override
    public UtilDTO insertarDatosUsuarioEmpresa(IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultado = new HashMap();
        try {
            String idEmpresa;
            IbUsuariosPjDTO ibUsuariosPjDTO;
            for (IbEmpresasUsuariosPj ibEmpresaUsuariosPj : ibEmpresasUsuariosPjDTO.getIbEmpresasUsuariosPj()) {
                IbUsuariosPj ibUsuarioPj = ibEmpresaUsuariosPj.getIdUsuarioPj();
                IbEmpresas ibEmpresa = ibEmpresaUsuariosPj.getIdEmpresa();

                //SE VERIFICA SI LA EMPRESA YA EXISTE EN LA BASE DE DATOS
                utilDTO = ibEmpresasDAO.validarEmpresa(ibEmpresa.getTipoRif(), ibEmpresa.getNroRif(), idCanal, codigoCanal);
                //resultadoEmpresa = datosValidacionEmpresa.getResulados();

                if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    //LA EMPRESA YA EXISTE EN LA BASE DE DATOS
                    if ((Boolean) utilDTO.getResulados().get(EXISTE) == true) {
                        idEmpresa = ((BigDecimal) utilDTO.getResulados().get(ID_EMPRESA)).toString();
                    } else {
                        //LA EMPRESA NO EXISTE EN LA BASE DE DATOS
                        //SE REGISTRAN LOS DATOS DE LA EMPRESA
                        utilDTO = ibEmpresasDAO.insertarDatosEmpresa(ibEmpresa.getNroRif(), ibEmpresa.getTipoRif(), ibEmpresa.getNroCta(), ibEmpresa.getCodCliente(), ibEmpresa.getNombre(), ibEmpresa.getTipoAcceso(), ibEmpresa.getEstatus(), idCanal, codigoCanal);
                        if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                            //resultado = utilDTO.getResulados();
                            idEmpresa = (String) utilDTO.getResulados().get(ID_EMPRESA);
                        } else {
                            respuesta = utilDTO.getRespuesta();
                            resultado = utilDTO.getResulados();
                            return utilDTO;
                        }
                    }
                } else {
                    respuesta = utilDTO.getRespuesta();
                    resultado = utilDTO.getResulados();
                    return utilDTO;
                }

                //SE VALIDA QUE EL USUARIO EXISTA EN LA BASE DE DATOS
                utilDTO = ibUsuariosPjDAO.consultarUsuarioPjPorCedula(ibUsuarioPj.getTipoDoc(), ibUsuarioPj.getNroDoc(), codigoCanal, idCanal);

                if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    //USUARIO YA EXISTE EN LA BD
                    if ((Boolean) utilDTO.getResulados().get(EXISTE) == true) {
                        ibUsuariosPjDTO = ((IbUsuariosPjDTO) utilDTO.getResulados().get(USUARIO));
                        ibEmpresaUsuariosPj.setIdUsuarioPj(ibUsuariosPjDTO.getIbUsuarioPj());
                    } else {
                        utilDTO = ibUsuariosPjDAO.insertarDatosIbUsuarioPj(ibUsuarioPj.getNombre(), ibUsuarioPj.getNroDoc(), ibUsuarioPj.getTipoDoc(), ibUsuarioPj.getClave(), ibUsuarioPj.getLogin(), ibUsuarioPj.getEstatusAcceso(), ibUsuarioPj.getEstatusRegistro(), idCanal, codigoCanal);
                        if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                            ibEmpresaUsuariosPj.setIdUsuarioPj(((IbUsuariosPj) utilDTO.getResulados().get(USUARIO)));
                        }
                    }
                } else {
                    respuesta = utilDTO.getRespuesta();
                    resultado = utilDTO.getResulados();
                    return utilDTO;
                }

                ibEmpresaUsuariosPj.setId(BigDecimal.ZERO);
                //SE SETEA EL ID DE LA EMPRESA
                ibEmpresaUsuariosPj.getIdEmpresa().setId(new BigDecimal(idEmpresa));

                utilDTO = ibEmpresasUsuariosDAO.insertarDatosEmpresaUsuario(ibEmpresaUsuariosPj, idCanal, codigoCanal);

                //SE CONSULTAN TODOS LOS SERVICIOS DISPONIBLES PARA EL USUARIO CON EL PERFIL DEL USUARIO
                List<IbServiciosPerfilesPj> listIbServiciosPerfilesPj = ibServiciosPerfilesPjFacade.consultarServiciosPerfilesPj(ibEmpresaUsuariosPj.getPerfilAcceso().getId());

                IbServiEmpreUsuariosPj ibServiEmpreUsuariosPj;
                //SE SETEA EL USUARIO CONSULTADO AL REGISTRO A INSERTAR
                for (IbServiciosPerfilesPj ibServicioPerfilPj : listIbServiciosPerfilesPj) {
                    ibServiEmpreUsuariosPj = new IbServiEmpreUsuariosPj();
                    ibServiEmpreUsuariosPj.setIdEmpresas(ibEmpresaUsuariosPj.getIdEmpresa());
                    ibServiEmpreUsuariosPj.setIdUsuarioPj(ibEmpresaUsuariosPj.getIdUsuarioPj());
                    ibServiEmpreUsuariosPj.setIdCanal(new IbCanal(new BigDecimal(idCanal)));
                    ibServiEmpreUsuariosPj.setIdServiciosPj(ibServicioPerfilPj.getIdServicioPj());
                    ibServiEmpreUsuariosPjFacade.crearPJ(ibServiEmpreUsuariosPj);
                }

                if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    String textoSMS = textosFacade.textoParametro("pjw.global.sms.cuerpo.registrousuarios", idCanal);
                    //SE REEPLAZA EL NOMBRE DE LA EMPRESA
                    textoSMS = textoSMS.replace("$EMPRE", ibEmpresaUsuariosPj.getIdEmpresa().getNombre());
                    smsDAO.enviarSMS(ibEmpresaUsuariosPj.getTelfCelular(), textoSMS, codigoCanal);
                } else {
                    respuesta = utilDTO.getRespuesta();
                    resultado = utilDTO.getResulados();
                    return utilDTO;
                }
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarDatosUsuarioEmpresa: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            utilDTO.setRespuesta(respuesta);
        } finally {
            utilDTO.setRespuesta(respuesta);
            utilDTO.setResulados(resultado);
        }
        return utilDTO;
    }

    /**
     * Metodo utilizado para completar el perfil del usuario en el proceso de
     * registro
     *
     * Actualiza el login, la clave y las PDD
     *
     * Parametros para el usuario
     *
     * @param idUsuario id del usuario en IB
     * @param idEmpresa codigo interno de la empresa en IB
     * @param clave clave del usuario
     * @param login login del usuario
     * @param periodoVigencia indica el periodo de vigencia de la clave en dias
     * @param preguntaRespuestaUsuarioDTO objeto que contiene una lista de
     * preguntas son sus respuestas
     * @param idCanal id de canal en el IB
     * @param codigoCanal codigo de canal en el CORE del banco
     *
     * @return UtilDTO
     */
    @Override
    public UtilDTO completarAfiliacionUsuarioPj(String idUsuario, String idEmpresa, String clave, String login, String periodoVigencia, PreguntaRespuestaUsuarioDTO preguntaRespuestaUsuarioDTO, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        try {
            char estatusAcceso = Character.forDigit(EstatusAccesoEnum.ACTIVO.getId(), RADIX);
            char estatusRegistro = Character.forDigit(EstatusRegistroUsuarioEnum.ACTIVO.getId(), RADIX);
            //SE ACTUALIZAN EL LOGIN Y LA CLAVE DEL USUARIO
            utilDTO = ibUsuariosPjDAO.modificarLoginClaveUsuarioPj(idUsuario, clave, periodoVigencia, login, estatusRegistro, estatusAcceso, idCanal, codigoCanal);

            //SE ACTUALIZAN LAS PREGUNTAS DE DESAFIO
            if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                utilDTO = ibPregDesafioUsuarioPjDAO.agregarPDDUsuarioPj(preguntaRespuestaUsuarioDTO, idCanal, codigoCanal);
            } else {
                //NO SE MODIFICARON LOS DATOS DEL USUARIO
                resultados = utilDTO.getResulados();
                respuesta = utilDTO.getRespuesta();
                return utilDTO;
            }

            //SI NO SE AGREGARON LAS PREGUNTAS DE SEGURIDAD DE MANERA EXITOSA
            if (!utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                //NO FUERON ENCONTRADOS REGISTROS U OCURRIO OTRO ERROR AL MOMENTO DE CONSULTAR
                resultados = utilDTO.getResulados();
                respuesta = utilDTO.getRespuesta();
                return utilDTO;
            }

            utilDTO = ibEmpresasUsuariosDAO.consultarEmpresaUsuario(idUsuario, idEmpresa, idCanal, codigoCanal);
            //CONSULTO LOS DATOS DE EMPRESA_USUARIO
            IbEmpresasUsuariosPj ibEmpresaUsuarioPj;
            if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO = (IbEmpresasUsuariosPjDTO) utilDTO.getResulados().get(USUARIO);
                ibEmpresaUsuarioPj = ibEmpresasUsuariosPjDTO.getIbEmpresaUsuarioPj();
                ibEmpresaUsuarioPj.setEstatusAcceso(estatusAcceso);
                ibEmpresaUsuarioPj.setEstatusRegistro(estatusRegistro);
                ibEmpresasUsuariosPjFacade.modificarPj(ibEmpresaUsuarioPj);
            } else {
                //NO FUERON ENCONTRADOS REGISTROS U OCURRIO OTRO ERROR AL MOMENTO DE CONSULTAR
                resultados = utilDTO.getResulados();
                respuesta = utilDTO.getRespuesta();
                return utilDTO;
            }

            /* ESTA LOGICA SE TRASLADA AL METODO modificarLoginClaveUsuarioPj
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
            if(!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                //OCURRIO UN ERROR AL MOMENTO DE INTENTAR EL REGISTRO EN HISTORICO CLAVES PJ
                return utilDTO;
            }
             */
            //SIEMPRE SE ENVIA EL CODIGO 1 AL SP
            String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
            String textoSMS = textosFacade.textoParametro("pjw.global.sms.cuerpo.completarperfilusuario", idCanal);
            String codigoPlantilla = EstatusPlantillasEmail.COMPLETAR_PERFIL_USUARIO_TEXT.getDescripcion();
            String parametrosCorreo = ibEmpresaUsuarioPj.getIdUsuarioPj().getNombre();
            //SMS PARA REGISTRO DE USUARIO
            smsDAO.enviarSMS(ibEmpresaUsuarioPj.getTelfCelular(), textoSMS, codigoCanal);
            //EMAIL PARA REGISTRO DE USUARIO
            emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, ibEmpresaUsuarioPj.getEmail(), parametrosCorreo, idCanal, codigoCanal);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA EN completarAfiliacionUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            utilDTO.setResulados(resultados);
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }

    /**
     * Metodo utilizado para crear o modificar los datos de la empresa, usuario,
     * y monto del usuario
     *
     * Actualiza el login, la clave, las PDD, y el monto del usuario
     *
     * Parametros para el usuario
     *
     * @param ibEmpresasUsuariosPjDTO objeto completo con los datos de la
     * modificacion
     * @param idCanal id de canal en el IB
     * @param codigoCanal codigo de canal en el CORE del banco
     *
     * @return UtilDTO
     */
    @Override
    public UtilDTO modificarEmpresaUsuarioMontoPj(IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        boolean usuarioExisteActivo = false;
        try {
            //SE VERIFICA SI EL USUARIO YA ESTA REGISTRADO Y QUE SE ENCUENTRE ACTIVO PARA OTRA EMPRESA

            utilDTO = ibEmpresasDAO.consultarDatosEmpresaUsuario(ibEmpresasUsuariosPjDTO.getIbEmpresaUsuarioPj().getIdUsuarioPj().getId().toString(), String.valueOf(EstatusRegistroUsuarioEnum.ACTIVO.getId()), idCanal, codigoCanal);
            if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                usuarioExisteActivo = (boolean) utilDTO.getResulados().get(EXISTE);
            }

            //SE ACTUALIZAN DATOS DE EMPRESA Y USUARIO CON RESPECTIVO MONTO
            //BUSQUEDA DE LA EMPRESA USUARIO
            utilDTO = ibEmpresasUsuariosDAO.consultarEmpresaUsuario(ibEmpresasUsuariosPjDTO.getIbEmpresaUsuarioPj().getIdUsuarioPj().getId().toString(),
                    ibEmpresasUsuariosPjDTO.getIbEmpresaUsuarioPj().getIdEmpresa().getId().toString(),
                    idCanal, codigoCanal);

            //SE ARMA OBJETO PARA COMPARAR CAMPO A CAMPO
            if ((!utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && utilDTO.getRespuesta().getCodigo().equals(CODIGO_SIN_RESULTADOS_JPA))
                    || (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO))) {

                IbEmpresasUsuariosPj empresaUsuarioASalvar;
                boolean esModificacion = true;
                if (utilDTO.getResulados().get(USUARIO) == null) {
                    empresaUsuarioASalvar = new IbEmpresasUsuariosPj(BigDecimal.ZERO);
                    esModificacion = false;
                } else {
                    empresaUsuarioASalvar = ((IbEmpresasUsuariosPjDTO) utilDTO.getResulados().get(USUARIO)).getIbEmpresaUsuarioPj();
                }

                IbEmpresasUsuariosPj ibEmpresasUsuariosPjModificado = ibEmpresasUsuariosPjDTO.getIbEmpresaUsuarioPj();
                empresaUsuarioASalvar.setCargo(!ibEmpresasUsuariosPjModificado.getCargo().equals(empresaUsuarioASalvar.getCargo()) ? ibEmpresasUsuariosPjModificado.getCargo() : empresaUsuarioASalvar.getCargo());
                empresaUsuarioASalvar.setEmail(!ibEmpresasUsuariosPjModificado.getEmail().equals(empresaUsuarioASalvar.getEmail()) ? ibEmpresasUsuariosPjModificado.getEmail() : empresaUsuarioASalvar.getEmail());
                empresaUsuarioASalvar.setEsRepresentanteLegal(!ibEmpresasUsuariosPjModificado.getEsRepresentanteLegal().equals(empresaUsuarioASalvar.getEsRepresentanteLegal()) ? ibEmpresasUsuariosPjModificado.getEsRepresentanteLegal() : empresaUsuarioASalvar.getEsRepresentanteLegal());
                empresaUsuarioASalvar.setEstatusAcceso(!ibEmpresasUsuariosPjModificado.getEstatusAcceso().equals(empresaUsuarioASalvar.getEstatusAcceso()) ? ibEmpresasUsuariosPjModificado.getEstatusAcceso() : empresaUsuarioASalvar.getEstatusAcceso());
                if (!esModificacion && !usuarioExisteActivo) {
                    empresaUsuarioASalvar.setEstatusRegistro(String.valueOf(EstatusRegistroUsuarioEnum.POR_COMPLETAR_AFILIACION.getId()).charAt(0));
                } else if (usuarioExisteActivo) {
                    //SI EL USUARIO YA EXISTE PREVIAMENTE ENTONCES SE REGISTRA EN EMPRESA USUARIO CON ESTATUS DE REGISTRO ACTIVO
                    empresaUsuarioASalvar.setEstatusRegistro(String.valueOf(EstatusRegistroUsuarioEnum.ACTIVO.getId()).charAt(0));
                }
                empresaUsuarioASalvar.setPerfilAcceso(!ibEmpresasUsuariosPjModificado.getPerfilAcceso().equals(empresaUsuarioASalvar.getPerfilAcceso()) ? ibEmpresasUsuariosPjModificado.getPerfilAcceso() : empresaUsuarioASalvar.getPerfilAcceso());
                empresaUsuarioASalvar.setTelfCelular(!ibEmpresasUsuariosPjModificado.getTelfCelular().equals(empresaUsuarioASalvar.getTelfCelular()) ? ibEmpresasUsuariosPjModificado.getTelfCelular() : empresaUsuarioASalvar.getTelfCelular());
                empresaUsuarioASalvar.setTelfOficina(!ibEmpresasUsuariosPjModificado.getTelfOficina().equals(empresaUsuarioASalvar.getTelfOficina()) ? ibEmpresasUsuariosPjModificado.getTelfOficina() : empresaUsuarioASalvar.getTelfOficina());
                //***********************************************************************************************************************************************************************************************************************************************************************************
                // Datos de la Empresa
                IbEmpresas updateIbEmpresa = ibEmpresasUsuariosPjModificado.getIdEmpresa();
                IbEmpresas ibEmpresaConsulta = empresaUsuarioASalvar.getIdEmpresa() != null ? empresaUsuarioASalvar.getIdEmpresa() : ibEmpresasUsuariosPjModificado.getIdEmpresa();
                ibEmpresaConsulta.setCodCliente(updateIbEmpresa.getCodCliente() != ibEmpresaConsulta.getCodCliente() ? updateIbEmpresa.getCodCliente() : ibEmpresaConsulta.getCodCliente());
                ibEmpresaConsulta.setEstatus(updateIbEmpresa.getEstatus() != ibEmpresaConsulta.getEstatus() ? updateIbEmpresa.getEstatus() : ibEmpresaConsulta.getEstatus());
                ibEmpresaConsulta.setNombre(updateIbEmpresa.getNombre() != ibEmpresaConsulta.getNombre() ? updateIbEmpresa.getNombre() : ibEmpresaConsulta.getNombre());
                // Se Agrega para la Persistencia de los datos
                empresaUsuarioASalvar.setIdEmpresa(ibEmpresaConsulta);
                //***********************************************************************************************************************************************************************************************************************************************************************************
                // Datos del Usuario
                IbUsuariosPj ibUsuarioConsulta;
                IbUsuariosPj updateUsuarioPj = ibEmpresasUsuariosPjModificado.getIdUsuarioPj();
                if ((updateUsuarioPj.getId() != null) && !updateUsuarioPj.getId().equals(BigDecimal.ZERO)) {
                    ibUsuarioConsulta = updateUsuarioPj;
                } else {
                    ibUsuarioConsulta = empresaUsuarioASalvar.getIdUsuarioPj() != null ? empresaUsuarioASalvar.getIdUsuarioPj() : new IbUsuariosPj(BigDecimal.ZERO);
                    ibUsuarioConsulta.setEstatusAcceso(updateUsuarioPj.getEstatusAcceso() != ibUsuarioConsulta.getEstatusAcceso() ? updateUsuarioPj.getEstatusAcceso() : ibUsuarioConsulta.getEstatusAcceso());
                    if (!esModificacion) {
                        ibUsuarioConsulta.setEstatusRegistro(String.valueOf(EstatusRegistroUsuarioEnum.POR_COMPLETAR_AFILIACION.getId()).charAt(0));
                        ibUsuarioConsulta.setNroDoc(updateUsuarioPj.getNroDoc() != ibUsuarioConsulta.getNroDoc() ? updateUsuarioPj.getNroDoc() : ibUsuarioConsulta.getNroDoc());
                        ibUsuarioConsulta.setTipoDoc(updateUsuarioPj.getTipoDoc() != ibUsuarioConsulta.getTipoDoc() ? updateUsuarioPj.getTipoDoc() : ibUsuarioConsulta.getTipoDoc());
                        ibUsuarioConsulta.setIntentosFallidos(BigInteger.ZERO);
                        ibUsuarioConsulta.setIntentosFallidosPreguntas(BigInteger.ZERO);

                    }
                    ibUsuarioConsulta.setNombre(updateUsuarioPj.getNombre() != ibUsuarioConsulta.getNombre() ? updateUsuarioPj.getNombre() : ibUsuarioConsulta.getNombre());
                }
                // Se Agrega para la Persistencia de los datos
                empresaUsuarioASalvar.setIdUsuarioPj(ibUsuarioConsulta);
                //***********************************************************************************************************************************************************************************************************************************************************************************
                // Datos de la regla de aprobacion
                IbMontosUsuariosPj updateMonto = ibEmpresasUsuariosPjModificado.getIbMontosUsuariosPj();
                IbMontosUsuariosPj montoUsuarioConsulta = empresaUsuarioASalvar.getIbMontosUsuariosPj();
                // Se verifica que es Autorizador unico perfil al cual se le aplican reglas
                if (empresaUsuarioASalvar.getPerfilAcceso().getId().intValue() == (PerfilAccesoEnum.AUTORIZADOR.getId())) {
                    // Caso 1 - ya tiene una regla y se modifica el monto
                    if (updateMonto != null && montoUsuarioConsulta != null) {
                        montoUsuarioConsulta.setMontoHasta(updateMonto.getMontoHasta() != montoUsuarioConsulta.getMontoHasta() ? updateMonto.getMontoHasta() : montoUsuarioConsulta.getMontoHasta());
                    } // Caso 2 - no tiene regla registrada y se le agrega una nueva
                    else if (updateMonto != null && montoUsuarioConsulta == null) {
                        montoUsuarioConsulta = new IbMontosUsuariosPj();
                        montoUsuarioConsulta.setId(BigDecimal.ZERO);
                        montoUsuarioConsulta.setIdUsuarioPj(empresaUsuarioASalvar.getIdUsuarioPj());
                        montoUsuarioConsulta.setIdEmpresa(ibEmpresaConsulta);
                        montoUsuarioConsulta.setMontoHasta(updateMonto.getMontoHasta());
                    } //Caso 3 - ya exite una regla y se elimina del usuario
                    else if (updateMonto == null && montoUsuarioConsulta != null) {
                        montoUsuarioConsulta = null;
                        ibMontosUsuariosPjFacade.remove(empresaUsuarioASalvar.getIbMontosUsuariosPj());
                    }
                } else {
                    if (empresaUsuarioASalvar.getIbMontosUsuariosPj() != null) {
                        montoUsuarioConsulta = null;
                        ibMontosUsuariosPjFacade.remove(empresaUsuarioASalvar.getIbMontosUsuariosPj());
                    }
                }

                // Se Agrega para la Persistencia de los datos
                empresaUsuarioASalvar.setIbMontosUsuariosPj(montoUsuarioConsulta);
                //***********************************************************************5************************************************************************************************************************************************************************************************************

                if (ibUsuarioConsulta.getId() != null && ibUsuarioConsulta.getId().equals(BigDecimal.ZERO)) {
                    utilDTO = ibUsuariosPjDAO.insertarDatosIbUsuarioPj(ibUsuarioConsulta.getNombre(), ibUsuarioConsulta.getNroDoc(), ibUsuarioConsulta.getTipoDoc(),
                            ibUsuarioConsulta.getClave(), ibUsuarioConsulta.getLogin(), ibUsuarioConsulta.getEstatusAcceso(), ibUsuarioConsulta.getEstatusRegistro(),
                            idCanal, codigoCanal);

                    if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                        empresaUsuarioASalvar.setIdUsuarioPj((IbUsuariosPj) utilDTO.getResulados().get(USUARIO));
                    }
                } else {
                    IbUsuariosPj ibUsuarioPj = ibUsuariosPjFacade.find(ibUsuarioConsulta.getId());
                    if (ibUsuarioPj != null) {
                        empresaUsuarioASalvar.setIdUsuarioPj(ibUsuarioPj);
                    }
                    if (esModificacion) {
                        empresaUsuarioASalvar.getIdUsuarioPj().setNombre(ibUsuarioConsulta.getNombre());
                    }
                }

                // se persiste toda la informacion de empresaUsuario
                if (esModificacion) {
                    //INICIO DE PROCESOS PARA GUARDADO DE CUENTAS DEL USUARIO
                    //SI ES UNA MODIFICACION SE ELIMINAN TODAS LAS CUENTAS DEL USUARIO
                    ibCtasEmpresaUsuarioPjFacade.eliminarIbCtasEmpresaUsuarioPjPorUsuario(empresaUsuarioASalvar.getIdUsuarioPj().getId());
                    //SI SE PROCEDE A CREAR LAS CUENTAS DEL USUARIO    
                    IbCtasEmpresaPj ibCtasEmpresaPj;
                    //RespuestaDTO respuestaDTO = null;
                    for (IbCtasEmpresaUsuarioPj ibCtasEmpresaUsuarioPj : ibEmpresasUsuariosPjDTO.getIbCtasEmpresaUsuarioPj()) {
                        //SE VERIFICA SI LA CUENTA EXISTE EN LA BASE DE DATOS
                        ibCtasEmpresaPj = ibCtasEmpresaPjFacade.consultarCuentasPorNumero(ibCtasEmpresaUsuarioPj.getIdCtaEmpresa().getNroCuenta());
                        //CUENTA YA EXISTE EN LA BD
                        if (ibCtasEmpresaPj != null) {
                            ibCtasEmpresaUsuarioPj.setIdCtaEmpresa(ibCtasEmpresaPj);
                        } else {
                            ibCtasEmpresaPjFacade.crear(ibCtasEmpresaUsuarioPj.getIdCtaEmpresa());
                        }
                        ibCtasEmpresaUsuarioPjFacade.crear(ibCtasEmpresaUsuarioPj);
                    }
                    //FIN DE PROCESOS PARA GUARDADO DE CUENTAS DEL USUARIO

                    //SE ELIMIANAN TODOS LOS SERVICIOS ANTERIORES DEL USUARIO
                    for (IbServiEmpreUsuariosPj ibServiEmpreUsuariosPj : empresaUsuarioASalvar.getIdUsuarioPj().getIbServiEmpreUsuariosPjList()) {
                        ibServiEmpreUsuariosPjFacade.eliminarPj(ibServiEmpreUsuariosPj);
                    }

                    //SE SETEAN TODOS LO SERVICIOS MARCADOS POR EL USUARIO
                    empresaUsuarioASalvar.getIdUsuarioPj().setIbServiEmpreUsuariosPjList(ibEmpresasUsuariosPjDTO.getIbServiEmpreUsuariosPj());
                    utilDTO = ibEmpresasUsuariosDAO.modificarDatosEmpresaUsuario(empresaUsuarioASalvar, codigoCanal);

                    //DATOS PARA EL ENVIO DE CORREO Y SMS
                    //SIEMPRE SE ENVIA EL CODIGO 1 AL SP
                    String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
                    Integer codigoPlantilla = EstatusPlantillasEmail.REGISTRO_EMPRESAUSUARIO_TEXT.getId();
                    String parametrosCorreo = empresaUsuarioASalvar.getIdEmpresa().getNombre() + "~" + empresaUsuarioASalvar.getIdUsuarioPj().getNombre();
                    if (!usuarioExisteActivo) {
                        OtpDTO otpDTO = otpDAO.generarOTPEnviarCorreoPj(empresaUsuarioASalvar.getIdUsuarioPj().getId().toString(), codigoPlantilla, empresaUsuarioASalvar.getEmail(), parametrosCorreo, codigoCanal);
                        if (otpDTO != null && otpDTO.getOvOTP() != null) {
                            String textoSMSOTP = textosFacade.textoParametro("pjw.global.sms.otp.registrousuario", codigoCanal);
                            textoSMSOTP = textoSMSOTP.replace("$POTP", otpDTO.getOvOTP());
                            //SMS PARA OTP
                            smsDAO.enviarSMS(empresaUsuarioASalvar.getTelfCelular(), textoSMSOTP, codigoCanal);
                        }
                    }

                    // envio de correo a representantes legales de la empresa
                    IbEmpresasUsuariosPjDTO result = ibEmpresasUsuariosDAO.consultarEmpresasUsuariosPjRepresentanteLegal(empresaUsuarioASalvar.getIdEmpresa().getId().toString(), idCanal, codigoCanal);
                    //SI EXISTEN REPRESENTANTES LEGALES ASOCIADOS A LA EMPRESA
                    if (result != null && result.getIbEmpresasUsuariosPj() != null) {
                        for (IbEmpresasUsuariosPj ibEmUsu : result.getIbEmpresasUsuariosPj()) {
                            emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla.toString(), ibEmUsu.getEmail(), parametrosCorreo, idCanal, codigoCanal);
                        }
                    }

                } else {
                    if (empresaUsuarioASalvar.getPerfilAcceso().getId().intValue() == (PerfilAccesoEnum.AUTORIZADOR.getId())) {
                        montoUsuarioConsulta.setIdUsuarioPj(empresaUsuarioASalvar.getIdUsuarioPj());
                    }

                    utilDTO = ibEmpresasUsuariosDAO.insertarDatosEmpresaUsuario(empresaUsuarioASalvar, idCanal, codigoCanal);

                    String nroDocumento = ibEmpresasUsuariosPjDTO.getIbEmpresaUsuarioPj().getIdUsuarioPj().getNroDoc();
                    Character tipoDocumento = ibEmpresasUsuariosPjDTO.getIbEmpresaUsuarioPj().getIdUsuarioPj().getTipoDoc();

                    //SE CONSULTA EL ID DEL USUARIO
                    IbUsuariosPj ibUsuariosPj = ibUsuariosPjFacade.consultarUsuarioPjPorCedula(tipoDocumento, nroDocumento);

                    //SE SETEA EL USUARIO CONSULTADO AL REGISTRO A INSERTAR
                    for (IbServiEmpreUsuariosPj ibServiEmpreUsuariosPj : ibEmpresasUsuariosPjDTO.getIbServiEmpreUsuariosPj()) {
                        ibServiEmpreUsuariosPj.setIdUsuarioPj(ibUsuariosPj);
                        ibServiEmpreUsuariosPjFacade.crearPJ(ibServiEmpreUsuariosPj);
                    }

                    //INICIO DE PROCESOS PARA GUARDADO DE CUENTAS DEL USUARIO
                    IbCtasEmpresaPj ibCtasEmpresaPj;
                    //RespuestaDTO respuestaDTO = null;
                    for (IbCtasEmpresaUsuarioPj ibCtasEmpresaUsuarioPj : ibEmpresasUsuariosPjDTO.getIbCtasEmpresaUsuarioPj()) {
                        //SE VERIFICA SI LA CUENTA EXISTE EN LA BASE DE DATOS
                        ibCtasEmpresaPj = ibCtasEmpresaPjFacade.consultarCuentasPorNumero(ibCtasEmpresaUsuarioPj.getIdCtaEmpresa().getNroCuenta());
                        //SE SETEA EL USUARIO QUE SE GUARDO EN LA BASE DE DATOS
                        ibCtasEmpresaUsuarioPj.setIdUsuarioPj(ibUsuariosPj);
                        //CUENTA YA EXISTE EN LA BD
                        if (ibCtasEmpresaPj != null) {
                            ibCtasEmpresaUsuarioPj.setIdCtaEmpresa(ibCtasEmpresaPj);
                        } else {
                            ibCtasEmpresaPjFacade.crear(ibCtasEmpresaUsuarioPj.getIdCtaEmpresa());
                        }
                        ibCtasEmpresaUsuarioPjFacade.crear(ibCtasEmpresaUsuarioPj);
                    }
                    //FIN DE PROCESOS PARA GUARDADO DE CUENTAS DEL USUARIO

                    //DATOS PARA EL ENVIO DE CORREO Y SMS
                    //SIEMPRE SE ENVIA EL CODIGO 1 AL SP
                    String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
                    Integer codigoPlantilla = EstatusPlantillasEmail.REGISTRO_EMPRESAUSUARIO_TEXT.getId();
                    String parametrosCorreo = empresaUsuarioASalvar.getIdEmpresa().getNombre() + "~" + empresaUsuarioASalvar.getIdUsuarioPj().getNombre();
                    if (!usuarioExisteActivo) {
                        OtpDTO otpDTO = otpDAO.generarOTPEnviarCorreoPj(empresaUsuarioASalvar.getIdUsuarioPj().getId().toString(), codigoPlantilla, empresaUsuarioASalvar.getEmail(), parametrosCorreo, codigoCanal);
                        if (otpDTO != null && otpDTO.getOvOTP() != null) {
                            String textoSMSOTP = textosFacade.textoParametro("pjw.global.sms.otp.registrousuario", codigoCanal);
                            textoSMSOTP = textoSMSOTP.replace("$POTP", otpDTO.getOvOTP());
                            //SMS PARA OTP
                            smsDAO.enviarSMS(empresaUsuarioASalvar.getTelfCelular(), textoSMSOTP, codigoCanal);
                        }
                    }

                    // envio de correo a representantes legales de la empresa
                    IbEmpresasUsuariosPjDTO result = ibEmpresasUsuariosDAO.consultarEmpresasUsuariosPjRepresentanteLegal(empresaUsuarioASalvar.getIdEmpresa().getId().toString(), idCanal, codigoCanal);
                    //SI EXISTEN REPRESENTANTES LEGALES ASOCIADOS A LA EMPRESA
                    if (result != null && result.getIbEmpresasUsuariosPj() != null) {
                        for (IbEmpresasUsuariosPj ibEmUsu : result.getIbEmpresasUsuariosPj()) {
                            emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla.toString(), ibEmUsu.getEmail(), parametrosCorreo, idCanal, codigoCanal);
                        }
                    }
                }
            } else {
                //No existe empresaUsuario
                resultados = utilDTO.getResulados();
                respuesta = utilDTO.getRespuesta();
                return utilDTO;
            }

            //SI NO SE EJECUTA EL MODIFICAREMPRESAUSUARIO EXITOSAMENTE
            if (!utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                //NO FUERON ENCONTRADOS REGISTROS U OCURRIO OTRO ERROR AL MOMENTO DE CONSULTAR
                resultados = utilDTO.getResulados();
                respuesta = utilDTO.getRespuesta();
                return utilDTO;
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA EN modificarEmpresaUsuarioMontoPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            utilDTO.setResulados(resultados);
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }
}
