/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.EMailDAO;
import com.bds.ws.dao.IbCargaPagosCorpDetPjDAO;
import com.bds.ws.dao.IbCargaPagosCorpPjDAO;
import com.bds.ws.dao.IbEmpresasUsuariosDAO;
import com.bds.ws.dto.IbAprobacionesServiciosPjDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaPagosCorpDetPjDTO;
import com.bds.ws.dto.IbCargaPagosCorpPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusPlantillasEmail;
import com.bds.ws.enumerated.ServiciosAprobadoresEnum;
import com.bds.ws.enumerated.TipoCargaPagoEnum;
import com.bds.ws.exception.IbErroresCargaPjException;
import com.bds.ws.facade.IbAprobacionesServiciosPjFacade;
import com.bds.ws.facade.IbBeneficiariosPjFacade;
import com.bds.ws.facade.IbCargaPagosCorpPjFacade;
import com.bds.ws.facade.IbCtasXBeneficiariosPjFacade;
import com.bds.ws.facade.IbEmpresasFacade;
import com.bds.ws.facade.IbParametrosFacade;
import com.bds.ws.model.IbBeneficiariosPj;
import com.bds.ws.model.IbCargaPagosCorpDetPj;
import com.bds.ws.model.IbCargaPagosCorpPj;
import com.bds.ws.model.IbCtasXBeneficiariosPj;
import com.bds.ws.model.IbEmpresas;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_ERROR_VALIDACIONES;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO_SP;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.validator.ValidatorCargaPagosCorporativos;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author christian .gutierrez
 */
@Named("IbCargaPagosCorpPjDAO")
@Stateless
public class IbCargaPagosCorpPjDAOImpl extends BDSUtil implements IbCargaPagosCorpPjDAO {

    private static final Logger logger = Logger.getLogger(IbCargaPagosEspPjDAOImpl.class.getName());

    @EJB
    private IbCargaPagosCorpPjFacade ibCargaPagosCorpPjFacade;

    @EJB
    private IbParametrosFacade ibParametrosFacade;

    @EJB
    private IbAprobacionesServiciosPjFacade ibAprobacionesServiciosPjFacade;

    @EJB
    private IbBeneficiariosPjFacade ibBeneficiariosPjFacade;

    @EJB
    private IbEmpresasFacade ibEmpresasFacade;

    @EJB
    private IbCtasXBeneficiariosPjFacade ibCtasXBeneficiariosPjFacade;

    @EJB
    private IbCargaPagosCorpDetPjDAO ibCargaPagosCorpDetPjDAO;

    @EJB
    private IbEmpresasUsuariosDAO ibEmpresasUsuariosDAO;

    @EJB
    private EMailDAO emailDAO;

    /**
     * Log de sistema.
     */
    public final static String ERR_KEY_MISMO_NOMBRE = "pjw.cargaMasPagCorp.error.archivo.mismoNombre";
    public final static String ERR_KEY_MINIMO_UNA_LINEA = "pjw.cargaMasPagCorp.error.archivo.1Lineas";
    public final static String ERR_KEY_MONTO_NUMEROS = "pjw.cargaMasPagCorp.error.archivo.MontoNumeros";
    public final static String ERR_KEY_LINEA_DETALLE_TAMANO = "pjw.cargaMasPagCorp.error.archivo.lineaDetalleTamano";
    public final static String ERR_KEY_CUENTA_SOLO_NUMERO = "pjw.cargaMasPagCorp.error.archivo.cuentaSoloNumeros";
    public final static String ERR_KEY_ALFANUMERICO = "pjw.cargaMasPagCorp.error.archivo.alfNum";
    public final static String ERR_KEY_CORREO = "pjw.cargaMasPagCorp.error.archivo.correo";
    public final static String ERR_KEY_ALFABETICO = "pjw.cargaMasPagCorp.error.archivo.alfabetico";
    public final static String ERR_KEY_ALMENOS_UN_DETALLE_CARGADO_PARA_PERSISTENCIA = "pjw.cargaMasPagCorp.error.archivo.minDetPersistencia";
    public final static String ERR_KEY_ALMENOS_UN_APROBADOR = "pjw.cargaMasPagCorp.error.archivo.min1aprobador";
    public final static String ERR_KEY_INDICE_RIF_INVALIDO = "pjw.cargaMasPagCorp.error.archivo.indiceInvalido";
    public final static String ERR_KEY_NUMERADOR_RIF_INVALIDO = "pjw.cargaMasPagCorp.error.archivo.indNumInvalido";
    public final static String ERR_KEY_ID_PAGO_NULL = "pjw.cargaMasPagCorp.error.archivo.idPagoNull";
    public final static String ERR_KEY_FORMATO_FECHA_PAGO = "pjw.cargaMasPagCorp.error.archivo.formatoFechaHoraPago";
    public final static String ERR_KEY_MOTIVO_PAGO = "pjw.cargaMasPagCorp.error.archivo.motivoPago";
    public final static String ERR_KEY_CLIENTE_NO_REGISTRADO = "pjw.cargaMasPagCorp.error.archivo.clienteNoReg";
    public final static String ERR_KEY_CUENTA_NO_REGISTRADA = "pjw.cargaMasPagCorp.error.archivo.cuentaNoReg";

    public final static int NUMERO_LINEAS_MINIMO = 1;

    @Override
    public IbCargaPagosCorpPjDTO listarIbCargaPagosCorpPj(BigDecimal cdClienteAbanks, Short estatusCarga, String idCanal, String codigoCanal) {
        IbCargaPagosCorpPjDTO ibCargaPagosCorpPjDTO = new IbCargaPagosCorpPjDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {
            List<IbCargaPagosCorpPj> lista = this.ibCargaPagosCorpPjFacade.listarIbCargaPagosCorpPj(cdClienteAbanks, estatusCarga);
            ibCargaPagosCorpPjDTO.setIbCargaPagosCorpPjList(lista);
        } catch (Exception e) {
            Logger.getLogger(IbCargaPagosCorpPjDAOImpl.class.getName()).log( null, e);

            logger.error( new StringBuilder("ERROR DAO en listarIbCargaPagosCorpPj: ")
                    .append("ID-CH- ").append(idCanal)
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaPagosCorpPjDTO == null || ibCargaPagosCorpPjDTO.getRespuestaDTO() == null) {
                ibCargaPagosCorpPjDTO = new IbCargaPagosCorpPjDTO();
                ibCargaPagosCorpPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        } finally {
            ibCargaPagosCorpPjDTO.setRespuestaDTO(respuestaDTO);
        }

        return ibCargaPagosCorpPjDTO;
    }

    @Override
    public UtilDTO insertarIbCargaPagosCorpPj(IbCargaPagosCorpPjDTO ibCargaPagosCorpPjDTO, IbCargaPagosCorpDetPjDTO ibCargaPagosEspDetPjDTO, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            IbCargaPagosCorpPj cargaCargaPagosCorp = ibCargaPagosCorpPjDTO.getIbCargaPagosCorpPj();
            if (cargaCargaPagosCorp != null) {
                //Insert de la cabecera
                respuesta = this.ibCargaPagosCorpPjFacade.crearPJ(cargaCargaPagosCorp);
                if (respuesta.getCodigo().equals(DESCRIPCION_RESPUESTA_FALLIDA_JPA)) {
                    throw new Exception("ERROR AL INSERTAR IbCargaPagosCorpPj");
                }

                int i = 1;
                //Inicio el ciclo para la lectura detalle
                for (IbCargaPagosCorpDetPj ibCargaPagosCorpDetPj : ibCargaPagosEspDetPjDTO.getIbCargaPagosCorpDetPjsList()) {
                    ibCargaPagosCorpDetPj.setCodigoClienteAbanks(cargaCargaPagosCorp.getCodigoClienteAbanks());
                    ibCargaPagosCorpDetPj.setIdPago(cargaCargaPagosCorp);
                    if (ibCargaPagosCorpDetPj.getNroLineaArchivo() == null) {
                        ibCargaPagosCorpDetPj.setNroLineaArchivo(new BigInteger(String.valueOf(i)));
                    }
                    ibCargaPagosCorpDetPj.setEstatusAutorizacion(new IbEstatusPagosPj(BigDecimal.ZERO));
                    ibCargaPagosCorpDetPj.setFechaHoraCarga(new Date());
                    //Insert del detalle
                    UtilDTO utilDetalle = this.ibCargaPagosCorpDetPjDAO.insertarIbCargaPagosCorpDetPj(ibCargaPagosCorpDetPj, codigoCanal);
                    if (utilDetalle.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)
                            && utilDetalle.getRespuesta().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO_SP)) {
                        ibCargaPagosEspDetPjDTO.getIbCargaPagosCorpDetPjsList().get(i)
                                .setIdPagoDetalle(((IbCargaPagosCorpDetPj) utilDetalle.getResulados().get("ibCargaPagosCorpDetPj")).getIdPagoDetalle());
                    }
                    i++;
                }

                ibCargaPagosCorpPjDTO.setIbCargaPagosCorpPj(cargaCargaPagosCorp);
                resultado.put("ibCargaPagosCorpPj", ibCargaPagosCorpPjDTO);
                resultado.put("ibCargaPagosCorpPjList", ibCargaPagosEspDetPjDTO);
                respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
                //utilDTO.setRespuesta(respuesta);
                utilDTO.setResulados(resultado);
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarIbCargaPagosCorpPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setDescripcion(DESCRIPCION_RESPUESTA_FALLIDA);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarIbCargaPagosCorpPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setDescripcion(DESCRIPCION_RESPUESTA_FALLIDA);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        utilDTO.setRespuesta(respuesta);
        return utilDTO;

    }

    @Override
    public UtilDTO modificarEstatusIbCargaPagosCorpPj(Long idPago, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, BigDecimal idEmpresa, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        try {
            IbCargaPagosCorpPj cargaPagosCorp = ibCargaPagosCorpPjFacade.buscarIbCargaPagosCorpPj(idPago);
            if (cargaPagosCorp != null) {
                cargaPagosCorp.setEstatusAutorizacion(ibEstatusPagosPjDTO.getIbEstatusPagosPj());
                //Update de la cabecera
                respuesta = this.ibCargaPagosCorpPjFacade.modificarPj(cargaPagosCorp);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaPagosCorpPj");
                }
                //Update del estatus del detalle
                UtilDTO utilDTODetalle = this.ibCargaPagosCorpDetPjDAO.modificarEstatusIbCargaPagosCorpDetPj(idPago, ibEstatusPagosPjDTO, codigoCanal);
                if (!utilDTODetalle.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaPagosCorpDetPj");
                }
                //SIEMPRE SE ENVIA EL CODIGO 1 AL SP
                String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
                //ajustar codigo de la plantilla a usar
                String codigoPlantilla = EstatusPlantillasEmail.NOMINA_PENDIENTE_AUTORIZAR.getDescripcion();
                UtilDTO utilUsuarios = this.ibEmpresasUsuariosDAO.consultarEmpresasUsuariosAprobadores(idEmpresa);
                if (utilUsuarios == null || !utilUsuarios.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaNominaPj ENVIO DE CORREO");
                }
                List<IbEmpresasUsuariosPj> listaUsuarios = (List<IbEmpresasUsuariosPj>) utilUsuarios.getResulados().get("ListaAprobadores");

                //SI NO EXISTEN REPRESENTANTES LEGALES NO SE ENVIA CORREO
                if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                    for (IbEmpresasUsuariosPj usuario : listaUsuarios) {
                        String parametrosCorreo = usuario.getIdEmpresa().getNombre() + "~" + idPago + "~" + cargaPagosCorp.getMontoTotalAplicar() + "~" + usuario.getIdUsuarioPj().getNombre();
                        //ENVIAR EMAIL 
                        emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, usuario.getEmail(), parametrosCorreo, idCanal, codigoCanal);
                    }
                }

            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN modificarEstatusIbCargaPagosCorpPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN modificarEstatusIbCargaPagosCorpPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        utilDTO.setRespuesta(respuesta);
        return utilDTO;

    }

    @Override
    public UtilDTO procesarArchivoPagosCorpPj(IbCargaArchivoDTO ibCargaArchivoDTO) {

        UtilDTO utilDTO = new UtilDTO();
        IbCargaPagosCorpPjDTO ibCargaPagosCorpPjDTO = new IbCargaPagosCorpPjDTO();
        IbCargaPagosCorpDetPjDTO ibCargaPagosCorpDetPjDTO = new IbCargaPagosCorpDetPjDTO();
        ibCargaPagosCorpDetPjDTO.setIbCargaPagosCorpDetPjsList(new ArrayList());
        byte[] data = ibCargaArchivoDTO.getDataFile();
        Integer idpago = null;
        String line = "";
        String detLinea = "";
        String archOrigen = "248";
        int nroLinea = 0;
        int detNroLinea = 0;
        int caAprobadores = 0;
        int cntTotCreditos = 0;
        Long mntAplicar = 0l;
        Long cntCredRech = 0l;
        IbBeneficiariosPj ibBenefOrdentante248;
        IbBeneficiariosPj ibBenefOrdentante387;

        try {
            //Mapa de errores configurados para pagos corporativos
            Map<String, String> mapErrorres = this.ibCargaPagosCorpPjFacade.listarErroresPagosCorporativasCargaMasiva();
            Map param = new HashMap();

            //Validator creado para el caso
            ValidatorCargaPagosCorporativos validatorCargaPagosCorporativos = new ValidatorCargaPagosCorporativos(ibCargaArchivoDTO);
            validatorCargaPagosCorporativos.clearErrors();
            validatorCargaPagosCorporativos.setMapErrorres(mapErrorres);

            //Buffer de lectura para el archivo a registrar
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));

            //Validación de aprobadores para el caso
            try {
                IbAprobacionesServiciosPjDTO ibAprobacionesServiciosPjDTO
                        = ibAprobacionesServiciosPjFacade
                                .consultarByServicioEmpresa(
                                        ibCargaArchivoDTO.getCdClienteAbanks(),
                                        ServiciosAprobadoresEnum.PAGOS_CORPORATIVOS.getDescripcion(),
                                        ibCargaArchivoDTO.getIdCanal());

                if (ibAprobacionesServiciosPjDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    caAprobadores = ibAprobacionesServiciosPjDTO.getIbAprobacionServicioPj().getCantAprobadores().intValue();
                }
                if (caAprobadores == 0) {
                    throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_ALMENOS_UN_APROBADOR));
                }
            } catch (IbErroresCargaPjException e) {
                validatorCargaPagosCorporativos.addError(line, nroLinea, mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_ALMENOS_UN_APROBADOR));
            }

            //Validación caso nombre repetido
            if (validatorCargaPagosCorporativos.countErrors() == 0) {
                if (!reglaNoExisteOtroArchivoConMismoNombre(ibCargaArchivoDTO)) {
                    validatorCargaPagosCorporativos.addError(line, nroLinea, mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_MISMO_NOMBRE));
                }
            }

            //Armado preliminar detalle pagos 
            if (validatorCargaPagosCorporativos.countErrors() == 0) {
                for (nroLinea = 0; (line = br.readLine()) != null;) {

                    //Detalle
                    nroLinea++;
                    IbCargaPagosCorpDetPj ibCargaPagosCorpDetPj;

                    try {

                        param.put("linea", line);
                        param.put("nroLinea", nroLinea);

                        ibCargaPagosCorpDetPj = (IbCargaPagosCorpDetPj) validatorCargaPagosCorporativos.leerDetalle(param);
                        try {
                            //Valido el origen del archivo, si es de 248 caracteres o si es de 387
                            if (validatorCargaPagosCorporativos.getFntOrigen().equalsIgnoreCase(archOrigen)) {
                                //Se crean las variables necesarias para la validación del rif de la empresa
                                String auxChart = ibCargaPagosCorpDetPj.getNroIdentificacionCliente();
                                auxChart = auxChart.substring(0, 1);
                                Character tipoRif = new Character(auxChart.charAt(0));
                                String rif = ibCargaPagosCorpDetPj.getNroIdentificacionCliente();
                                rif = rif.substring(1, rif.length());
                                Long auxInt = Long.parseLong(rif);
                                rif = String.valueOf(auxInt);
                                //Se valida si existe el beneficiario
                                boolean isBeneficiario = ibBeneficiariosPjFacade.buscarIbBeneficiariosPj(new IbEmpresas(ibCargaArchivoDTO.getIdEmpresa()), tipoRif + rif);
                                //Obtengo los valores para los nuevos campos creados: nombre ordenante, identificacion ordenante y nombre beneficiario.
                                ibBenefOrdentante248 = ibBeneficiariosPjFacade.existeBeneficiarioPorRif(new IbEmpresas(ibCargaArchivoDTO.getIdEmpresa()), tipoRif + rif);
                                //Seteo los valores
                                ibCargaPagosCorpDetPj.setNombreBeneficiario(ibBenefOrdentante248.getNombreBeneficiario());
                                if (isBeneficiario) {
                                    //Consulto el beneficiario
                                    IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = this.ibCtasXBeneficiariosPjFacade.existeCuenta(new IbEmpresas(ibCargaArchivoDTO.getIdEmpresa()), ibCargaPagosCorpDetPj.getNroCuentaBeneficiario().trim(), tipoRif + rif);
                                    if (ibCtasXBeneficiariosPj != null) {
                                        ibCargaPagosCorpDetPj.setCodigoClienteAbanks(ibCargaArchivoDTO.getCdClienteAbanks().longValue());
                                        ibCargaPagosCorpDetPj.setCodigoUsuarioCarga(ibCargaArchivoDTO.getCodigoUsuario().longValue());
                                        ibCargaPagosCorpDetPjDTO.getIbCargaPagosCorpDetPjsList().add(ibCargaPagosCorpDetPj);
                                        mntAplicar = mntAplicar + ((BigDecimal) ibCargaPagosCorpDetPj.getMontoPago()).longValue();
                                        cntTotCreditos++;
                                    } else {
                                        //Si no existe capturo el mensaje de error
                                        validatorCargaPagosCorporativos.addError(line, nroLinea, mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_CUENTA_NO_REGISTRADA));
                                    }

                                } else {
                                    //Si no existe capturo el mensaje de error
                                    validatorCargaPagosCorporativos.addError(line, nroLinea, mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_CLIENTE_NO_REGISTRADO));
                                }
                            } else {
                                //Obtengo los valores para los nuevos campos creados: nombre ordenante, identificacion ordenante y nombre beneficiario.
                                ibBenefOrdentante387 = ibBeneficiariosPjFacade.existeBeneficiarioPorRef(new IbEmpresas(ibCargaArchivoDTO.getIdEmpresa()), ibCargaPagosCorpDetPj.getReferenciaPago());
                                //Seteo los valores
                                ibCargaPagosCorpDetPj.setNombreBeneficiario(ibBenefOrdentante387.getNombreBeneficiario());
                                //Busco la cuenta por medio del beneficiario
                                List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPj = ibBeneficiariosPjFacade.buscarIbBeneficiariosPjByReference(new IbEmpresas(ibCargaArchivoDTO.getIdEmpresa()), ibCargaPagosCorpDetPj.getReferenciaPago());
                                if (ibCtasXBeneficiariosPj != null) {
                                    for (IbCtasXBeneficiariosPj aux : ibCtasXBeneficiariosPj) {
                                        if (aux.getPrincipal() != 0) {
                                            ibCargaPagosCorpDetPj.setCodigoClienteAbanks(ibCargaArchivoDTO.getCdClienteAbanks().longValue());
                                            ibCargaPagosCorpDetPj.setCodigoUsuarioCarga(ibCargaArchivoDTO.getCodigoUsuario().longValue());
                                            ibCargaPagosCorpDetPj.setNroCuentaBeneficiario(aux.getNroCuentaBeneficiario());
                                            ibCargaPagosCorpDetPjDTO.getIbCargaPagosCorpDetPjsList().add(ibCargaPagosCorpDetPj);
                                            mntAplicar = mntAplicar + ((BigDecimal) ibCargaPagosCorpDetPj.getMontoPago()).longValue();
                                            cntTotCreditos++;
                                        } else {
                                            //Si no existe capturo el mensaje de error
                                            validatorCargaPagosCorporativos.addError(line, nroLinea, mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_CUENTA_NO_REGISTRADA));
                                        }
                                    }
                                } else {
                                    //Si no existe capturo el mensaje de error
                                    validatorCargaPagosCorporativos.addError(line, nroLinea, mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_CLIENTE_NO_REGISTRADO));
                                }
                            }
                        } catch (Exception e) {
                            validatorCargaPagosCorporativos.addError(line, nroLinea, mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_CLIENTE_NO_REGISTRADO));
                        }

                    } catch (Exception e) {
                        detLinea = line;
                        detNroLinea = nroLinea;
                        cntCredRech++;
                        validatorCargaPagosCorporativos.addError(line, nroLinea, e.getMessage());
                    }
                }

                //Para obtener los datos de la empresa.
                IbEmpresas ibEmpresas = ibEmpresasFacade.find(ibCargaArchivoDTO.getIdEmpresa());

                //Encabezado
                IbCargaPagosCorpPj ibCargaPagosCorpPj = new IbCargaPagosCorpPj();
                ibCargaPagosCorpPj.setCodigoClienteAbanks(ibCargaArchivoDTO.getCdClienteAbanks().longValue());
                ibCargaPagosCorpPj.setIdentificacionOrdenante(ibEmpresas.getTipoRif() + ibEmpresas.getNroRif());
                ibCargaPagosCorpPj.setNombreOrdenante(ibEmpresas.getNombre());
                ibCargaPagosCorpPj.setNombreArchivo(ibCargaArchivoDTO.getFileName());
                ibCargaPagosCorpPj.setCodigoUsuarioCarga(ibCargaArchivoDTO.getCodigoUsuario().longValue());
                ibCargaPagosCorpPj.setMotivoDePago("-");
                ibCargaPagosCorpPj.setMontoTotalAplicar(new BigDecimal(mntAplicar));
                ibCargaPagosCorpPj.setCantidadCreditosAplicar(new BigDecimal(cntTotCreditos));
                ibCargaPagosCorpPj.setNroCuentaDebito(ibCargaArchivoDTO.getNroCtaDebito());
                ibCargaPagosCorpPj.setEstatusAutorizacion(new IbEstatusPagosPj(BigDecimal.ZERO));
                ibCargaPagosCorpPj.setMontoTotalAplicado(BigDecimal.ZERO);
                ibCargaPagosCorpPj.setCantidadCreditosAplicados(BigDecimal.ZERO);
                ibCargaPagosCorpPj.setCantidadCreditosRechazados(new BigDecimal(cntCredRech));
                ibCargaPagosCorpPj.setFechaHoraCarga(new Date());
                ibCargaPagosCorpPj.setSecuenciaCumplida(BigDecimal.ZERO);
                ibCargaPagosCorpPj.setFechaHoraPago(ibCargaArchivoDTO.getFechaPago());
                ibCargaPagosCorpPj.setTipoCargaPago(Short.parseShort(TipoCargaPagoEnum.MASIVA.getId()));

                ibCargaPagosCorpPjDTO.setIbCargaPagosCorpPj(ibCargaPagosCorpPj);

                //Validacion minimo una linea a registrar
                if (this.reglaDebeProcesarAlmenosUnDetalleParaPersistencia(ibCargaPagosCorpDetPjDTO)) {
                    utilDTO = this.insertarIbCargaPagosCorpPj(
                            ibCargaPagosCorpPjDTO,
                            ibCargaPagosCorpDetPjDTO,
                            ibCargaArchivoDTO.getIdCanal(),
                            ibCargaArchivoDTO.getCodigoCanal());
                } else {
                    validatorCargaPagosCorporativos.addError(detLinea, detNroLinea, mapErrorres.get(IbCargaPagosCorpPjDAOImpl.ERR_KEY_ALMENOS_UN_DETALLE_CARGADO_PARA_PERSISTENCIA));
                }

            }
            //Si hay un error el insert retorna nulls en la respuesta y el resultado 
            if (utilDTO.getRespuesta() == null) {
                utilDTO.setRespuesta(new RespuestaDTO());
            }
            utilDTO.getRespuesta().setCodigoSP(CODIGO_RESPUESTA_EXITOSO);

            //Se lee el idpago, en caso de exitir
            try {
                idpago = ibCargaPagosCorpPjDTO.getIbCargaPagosCorpPj().getIdPago().intValue();
            } catch (Exception e) {
            }

            //Nos interesa enviar solo los errores 
            utilDTO.setResulados(new HashMap());
            utilDTO.getResulados().put(ID_PAGO, idpago);
            utilDTO.getResulados().put(LISTA_ERRORES_CARGA_ARCHIVO, validatorCargaPagosCorporativos.getIbErroresCargaPjDTO());

        } catch (IOException ioex) {
            logger.error( new StringBuilder("ERROR IO : ")
                    .append("-CH- ").append(ibCargaArchivoDTO.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ioex.getCause()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        } catch (Exception ex) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaPagosCorpPj: ")
                    .append("-CH- ").append(ibCargaArchivoDTO.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ex.getCause()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        }
        return utilDTO;
    }

    public String parametro(String codigo, String codigoCanal) {
        return ibParametrosFacade
                .consultaParametro(codigo, codigoCanal)
                .getIbParametro().getValor();
    }

    public boolean reglaNoExisteOtroArchivoConMismoNombre(IbCargaArchivoDTO ibCargaArchivoDto) throws Exception {
        return !ibCargaPagosCorpPjFacade.archivoPagosCorporativasCargaMasivaExiste(
                ibCargaArchivoDto.getCdClienteAbanks().longValue(),
                ibCargaArchivoDto.getFileName());
    }

    public boolean reglaDebeProcesarAlmenosUnDetalleParaPersistencia(IbCargaPagosCorpDetPjDTO ibCargaPagosCorpDetPjDTO) {
        return ibCargaPagosCorpDetPjDTO.getIbCargaPagosCorpDetPjsList().size() > 0;
    }

    @Override
    public UtilDTO eliminarCargaMasivaEstatusCeroPj(BigDecimal idPago, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        utilDTO.setRespuesta(new RespuestaDTO());
        try {
            IbCargaPagosCorpPj cargaPagosCorp = ibCargaPagosCorpPjFacade.buscarIbCargaPagosCorpPj(idPago.longValue());
            if (cargaPagosCorp != null) {
                if (cargaPagosCorp.getEstatusAutorizacion().getId() == BigDecimal.ZERO) {
                    respuesta = this.ibCargaPagosCorpPjFacade.eliminar(cargaPagosCorp);
                } else {
                    respuesta.setCodigo(CODIGO_ERROR_VALIDACIONES);
                    respuesta.setDescripcion("El pago no esta en estatus en precargada");
                }
            } else {
                respuesta.setCodigo(CODIGO_ERROR_VALIDACIONES);
                respuesta.setDescripcion("Pago no encontrada");
            }
            utilDTO.setRespuesta(respuesta);
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR IbCargaPagosCorpPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaPagosCorpPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;
    }

    /**
     * Lista los pagos corporativos que el usuario puede autorizar
     *
     * @param cdClienteAbanks
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @Override
    public IbCargaPagosCorpPjDTO listarIbCargaPagosCorpAutorizarPj(BigDecimal cdClienteAbanks, Long idUsuarioAurorizado, Long idServicio, String idCanal, String codigoCanal) {
        IbCargaPagosCorpPjDTO ibCargaPagosCorpPjDTO = new IbCargaPagosCorpPjDTO();
        try {
            ibCargaPagosCorpPjDTO = this.ibCargaPagosCorpPjFacade.listarIbCargaPagosCorpAutorizarPj(cdClienteAbanks, idUsuarioAurorizado, idServicio, USUARIO_CONEXION);

        } catch (Exception e) {

            logger.error( new StringBuilder("ERROR DAO en listarIbCargaNominaPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaPagosCorpPjDTO == null || ibCargaPagosCorpPjDTO.getRespuestaDTO() == null) {
                ibCargaPagosCorpPjDTO = new IbCargaPagosCorpPjDTO();
                ibCargaPagosCorpPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaPagosCorpPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibCargaPagosCorpPjDTO;
    }

    /**
     * Lista los pagos corporativos que el usuario puede autorizar
     *
     * @param cdClienteAbanks
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @Override
    public IbCargaPagosCorpPjDTO listarIbCargaPagosCorpAdminPj(BigDecimal clienteAbanks,Long idPago,Date fechaInicio, Date fechaFin) {
        IbCargaPagosCorpPjDTO ibCargaPagosCorpPjDTO = new IbCargaPagosCorpPjDTO();
        try {
            ibCargaPagosCorpPjDTO = this.ibCargaPagosCorpPjFacade.listarIbCargaPagosCorpAdminPj(clienteAbanks, idPago, fechaInicio, fechaFin);

        } catch (Exception e) {

            logger.error( new StringBuilder("ERROR DAO en listarIbCargaNominaPj: ")
                    .append("-CH- ").append("01-03-ADMIN")
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaPagosCorpPjDTO == null || ibCargaPagosCorpPjDTO.getRespuestaDTO() == null) {
                ibCargaPagosCorpPjDTO = new IbCargaPagosCorpPjDTO();
                ibCargaPagosCorpPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaPagosCorpPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibCargaPagosCorpPjDTO;
    }
}
