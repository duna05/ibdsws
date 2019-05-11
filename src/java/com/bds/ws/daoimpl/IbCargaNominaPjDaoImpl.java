/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.DelSurDAO;
import com.bds.ws.dao.EMailDAO;
import com.bds.ws.dao.IbCargaNominaDetallePjDAO;
import com.bds.ws.dao.IbCargaNominaPjDAO;
import com.bds.ws.dao.IbEmpresasUsuariosDAO;
import com.bds.ws.dao.NominaPjDAO;
import com.bds.ws.dto.IbAprobacionesServiciosPjDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaNominaDetallePjDTO;
import com.bds.ws.dto.IbCargaNominaPjDTO;
import com.bds.ws.dto.IbErroresCargaPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusPlantillasEmail;
import com.bds.ws.enumerated.ServiciosAprobadoresEnum;
import com.bds.ws.facade.IbAprobacionesServiciosPjFacade;
import com.bds.ws.facade.IbCargaNominaDetallePjFacade;
import com.bds.ws.facade.IbCargaNominaPjFacade;
import com.bds.ws.facade.IbParametrosFacade;
import com.bds.ws.model.IbCargaNominaDetallePj;
import com.bds.ws.model.IbCargaNominaPj;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO_SP;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.validator.Parametro;
import com.bds.ws.validator.Validator;
import com.bds.ws.validator.ValidatorCargaNomina;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.vasquez
 */
@Named("IbCargaNominaPjDAO")
@Stateless

public class IbCargaNominaPjDaoImpl extends BDSUtil implements IbCargaNominaPjDAO {

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbCargaNominaPjDaoImpl.class.getName());

    @EJB
    private IbCargaNominaPjFacade ibCargaNominaPjFacade;
    @EJB
    private IbCargaNominaDetallePjFacade ibCargaNominaDetallePjFacade;

    @EJB
    private IbCargaNominaDetallePjDAO ibCargaNominaDetallePjDAO;

    @EJB
    private IbEmpresasUsuariosDAO ibEmpresasUsuariosDAO;

    @EJB
    private EMailDAO emailDAO;

    @EJB
    private IbAprobacionesServiciosPjFacade ibAprobacionesServiciosPjFacade;

    @EJB
    private DelSurDAO delsurDAO;

    @EJB
    private NominaPjDAO ibNominaPjDAO;

    @EJB
    private IbParametrosFacade ibParametrosFacade;

    public final static String COD_HORA_MININA = "pjw.nomina.minimaHoraEjecucion";
    public final static String COD_HORA_MAXIMA = "pjw.nomina.maximaHoraEjecucion";
    public final static String COD_VALIDAR_FERIADOS = "pjw.nomina.validarDiaFeriado";
    public final static String COD_MINIMO_HORAS_ANTICIPACION = "pjw.nominaCargaMasiva.error.archivo.minAnticip";

    public final static String VALIDACION_ACTIVA = "1";
    public final static String VALIDACION_INACTIVA = "0";

    public final static int NUMERO_LINEAS_MINIMO = 2;

    public final static String ERR_KEY_CLIENTE_EXCLUIDO = "pjw.nominaCargaMasiva.error.archivo.clienteExcluido";
    public final static String ERR_KEY_MINIMO_ANTICIPACION = "pjw.nominaCargaMasiva.error.archivo.minimoAnticipacion";
    public final static String ERR_KEY_MINIMO_DOS_LINEAS = "pjw.nominaCargaMasiva.error.archivo.2Lineas";
    public final static String ERR_KEY_DIAS_FERIADOS = "pjw.nominaCargaMasiva.error.archivo.diasFeriados";
    public final static String ERR_KEY_MISMO_NOMBRE = "pjw.nominaCargaMasiva.error.archivo.mismoNombre";
    public final static String ERR_KEY_TOTAL_REGISTRO_NUMERO_LINEAS = "pjw.nominaCargaMasiva.error.archivo.totalRegNumeroLineas";
    public final static String ERR_KEY_CONVENIO_CORRESPONDA_CLIENTE = "pjw.nominaCargaMasiva.error.archivo.convenioCliente";
    public final static String ERR_KEY_CUENTA_PERTENEZCA_CONVENIO = "pjw.nominaCargaMasiva.error.archivo.cuentaConvenio";
    public final static String ERR_KEY_LINEA_CABECERA_TAMANO = "pjw.nominaCargaMasiva.error.archivo.lineaCabeceraTamano";
    public final static String ERR_KEY_NUMERO_CONVENIO_NUMEROS = "pjw.nominaCargaMasiva.error.archivo.numeroConvenioNumeros";
    public final static String ERR_KEY_FORMATO_FECHA_PAGO = "pjw.nominaCargaMasiva.error.archivo.formatoFechaHoraPago";
    public final static String ERR_KEY_FECHA_PAGO_FUTURA = "pjw.nominaCargaMasiva.error.archivo.fechaHoraPagoFutura";
    public final static String ERR_KEY_CUENTA_CLIENTE_NUMEROS = "pjw.nominaCargaMasiva.error.archivo.cuentaClienteNumeros";
    public final static String ERR_KEY_MONTO_NUMEROS = "pjw.nominaCargaMasiva.error.archivo.MontoNumeros";
    public final static String ERR_KEY_FORMA_PAGO_D = "pjw.nominaCargaMasiva.error.archivo.formaPagoD";
    public final static String ERR_KEY_REFERENCIA = "pjw.nominaCargaMasiva.error.archivo.referencia";
    public final static String ERR_KEY_MOTIVO_PAGO = "pjw.nominaCargaMasiva.error.archivo.motivoPago";
    public final static String ERR_KEY_TOTAL_REGISTROS = "pjw.nominaCargaMasiva.error.archivo.totalRegistros";
    public final static String ERR_KEY_CONFIDENCIAL = "pjw.nominaCargaMasiva.error.archivo.confidencial";
    public final static String ERR_KEY_LINEA_DETALLE_TAMANO = "pjw.nominaCargaMasiva.error.archivo.lineaDetalleTamano";
    public final static String ERR_KEY_CODIGO_EMPRESA = "pjw.nominaCargaMasiva.error.archivo.codigoEmpresa";
    public final static String ERR_KEY_CEDULA_SOLO_NUMERO = "pjw.nominaCargaMasiva.error.archivo.cedulaSoloNumeros";
    public final static String ERR_KEY_CUENTA_SOLO_NUMERO = "pjw.nominaCargaMasiva.error.archivo.cuentaSoloNumeros";
    public final static String ERR_KEY_MONTO_SOLO_NUMERO = "pjw.nominaCargaMasiva.error.archivo.montoSoloNumeros";
    public final static String ERR_KEY_TRANSACCION_C = "pjw.nominaCargaMasiva.error.archivo.transaccionC";
    public final static String ERR_KEY_CONVENIO_SOLO_NUMEROS = "pjw.nominaCargaMasiva.error.archivo.convenioSoloNumeros";
    public final static String ERR_KEY_BENEFICIARIO_ALFANUMERICO = "pjw.nominaCargaMasiva.error.archivo.beneficiarioAlfaNum";
    public final static String ERR_KEY_FERIADO = "pjw.nominaCargaMasiva.error.archivo.feriado";
    public final static String ERR_KEY_HORARIO_RESTRINGIDO = "pjw.nominaCargaMasiva.error.archivo.horarioRestringido";
    public final static String ERR_KEY_ALMENOS_UN_DETALLE_CARGADO_PARA_PERSISTENCIA = "pjw.nominaCargaMasiva.error.archivo.min1DetallePersistencia";
    public final static String ERR_KEY_ALMENOS_UN_APROBADOR = "pjw.nominaCargaMasiva.error.archivo.min1aprobador";

    @Override
    public UtilDTO insertarIbCargaNominaPj(IbCargaNominaPjDTO ibCargaNominaPjDTO, IbCargaNominaDetallePjDTO nominaDetalleDTO, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            IbCargaNominaPj cargaNomina = ibCargaNominaPjDTO.getIbCargaNominaPj();
            if (cargaNomina != null) {
                respuesta = this.ibCargaNominaPjFacade.crearPJ(cargaNomina);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL INSERTAR IbCargaNominaPj");
                }
                for (int i = 0; i < nominaDetalleDTO.getIbCargaNominaDetallesPjList().size(); i++) {
                    IbCargaNominaDetallePj detalle = nominaDetalleDTO.getIbCargaNominaDetallesPjList().get(i);
                    detalle.setIdPago(cargaNomina);
                    detalle.setCodigoClienteAbanks(cargaNomina.getCodigoClienteAbanks());
                    if (detalle.getNroLineaArchivo() == null) {
                        detalle.setNroLineaArchivo(new BigDecimal(i + 1));
                    }
                    UtilDTO utilDetalle = ibCargaNominaDetallePjDAO.insertarIbCargaNominaDetallePj(detalle, codigoCanal);
                    if (utilDetalle.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)
                            && utilDetalle.getRespuesta().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO_SP)) {
                        nominaDetalleDTO.getIbCargaNominaDetallesPjList().get(i)
                                .setIdPagoDetalle(((IbCargaNominaDetallePj) utilDetalle.getResulados().get("ibCargaNominaDetallePj")).getIdPagoDetalle());
                    }
                }
                cargaNomina.setIbCargaNominaDetallePjCollection(nominaDetalleDTO.getIbCargaNominaDetallesPjList());
                ibCargaNominaPjDTO.setIbCargaNominaPj(cargaNomina);
                resultado.put("IbCargaNominaPj", ibCargaNominaPjDTO);
                resultado.put("IbCargaNominaDetallePjList", nominaDetalleDTO);
                respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
                utilDTO.setRespuesta(respuesta);
                utilDTO.setResulados(resultado);
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarIbCargaNominaPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarIbCargaNominaPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return utilDTO;
    }

    @Override
    public UtilDTO buscarEstatusNomina(String idEstatus) {
        RespuestaDTO respuesta = new RespuestaDTO();

        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();

        try {
            IbEstatusPagosPj estatusNomina = ibCargaNominaPjFacade.buscarIbEstatusPagosPj(idEstatus);
            if (estatusNomina != null) {
                resultado.put("IbEstatusPagosPj", estatusNomina);
                respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);

            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR IbCargaNominaPj: ")
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaNominaPj: ")
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        utilDTO.setRespuesta(respuesta);
        utilDTO.setResulados(resultado);
        return utilDTO;
    }

    @Override
    public UtilDTO modificarEstatusIbCargaNominaPj(BigDecimal idPago, IbEstatusPagosPjDTO ibEstatusPagosPjDTO,
            BigDecimal idEmpresa, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        try {
            IbCargaNominaPj cargaNomina = ibCargaNominaPjFacade.buscarIbCargaNominaPj(idPago);
            if (cargaNomina != null) {
                cargaNomina.setEstatus(ibEstatusPagosPjDTO.getIbEstatusPagosPj());
                respuesta = this.ibCargaNominaPjFacade.modificarPj(cargaNomina);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaNominaPj");
                }
                UtilDTO utilDTODetalle = this.ibCargaNominaDetallePjDAO.modificarEstatusIbCargaNominaDetallePj(idPago, ibEstatusPagosPjDTO, codigoCanal);
                if (!utilDTODetalle.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaNominaDetallePj");
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
                        String parametrosCorreo = usuario.getIdEmpresa().getNombre() + "~" + idPago + "~" + cargaNomina.getMontoTotalAplicar() + "~" + usuario.getIdUsuarioPj().getNombre();
                        //ENVIAR EMAIL 
                        emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, usuario.getEmail(), parametrosCorreo, idCanal, codigoCanal);
                    }
                }
                utilDTO.setRespuesta(respuesta);
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR IbCargaNominaPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaNominaPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;
    }

    @Override
    public IbCargaNominaPjDTO consultaIbCargaNominaPj() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IbCargaNominaDetallePjDTO consultaIbCargaNominaDetallePj() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IbCargaNominaPjDTO listarIbCargaNominaPj(BigDecimal cdClienteAbanks, Short estatusCarga, String idCanal, String codigoCanal) {
        IbCargaNominaPjDTO ibCargaNominaPjDTO = new IbCargaNominaPjDTO();
        try {
            ibCargaNominaPjDTO = this.ibCargaNominaPjFacade.listarCargaNomina(cdClienteAbanks, estatusCarga);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO en listarIbCargaNominaPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaNominaPjDTO == null || ibCargaNominaPjDTO.getRespuestaDTO() == null) {
                ibCargaNominaPjDTO = new IbCargaNominaPjDTO();
                ibCargaNominaPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaNominaPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibCargaNominaPjDTO;
    }

    public Predicate<IbCargaNominaPjDTO> validarCuentaCliente() {
        Predicate<IbCargaNominaPjDTO> predicate = t -> {
            /* UtilDTO util = cuentaDAO.validarCuentaCliente(t.getIdEmpresa().getNroCta(),
                    t.getIdEmpresa().getTipoRif().charAt(0),
                    t.getIdEmpresa().getNroRif(),
                    getIdCanal(),
                    getNombreCanal()
            );*/

 /*if (!util.getRespuestaDTO().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) || (!util.getRespuestaDTO().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO) || !(boolean) util.getResuladosDTO().get(BDSUtil.VALIDO))) {
                return true;
            } else {
                return false;
            }*/
            return false;
        };
        return predicate;
    }

    @Override
    public IbCargaNominaPjDTO listarIbCargaNominaAutorizarPj(BigDecimal cdClienteAbanks, Long idUsuarioAurorizado, Long idServicio, String idCanal, String codigoCanal) {
        IbCargaNominaPjDTO ibCargaNominaPjDTO = new IbCargaNominaPjDTO();
        Collection<IbCargaNominaDetallePj> ibCargaNominaDetallePjCollection = new ArrayList<>();
        IbCargaNominaDetallePjDTO ibCargaNominaDetallePjDTO = new IbCargaNominaDetallePjDTO();
        try {
              ibCargaNominaPjDTO = this.ibCargaNominaPjFacade.listarCargaNominaAutorizacion(cdClienteAbanks, idUsuarioAurorizado,idServicio, USUARIO_CONEXION);
           

        } catch (Exception e) {

            logger.error( new StringBuilder("ERROR DAO en listarIbCargaNominaPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaNominaPjDTO == null || ibCargaNominaPjDTO.getRespuestaDTO() == null) {
                ibCargaNominaPjDTO = new IbCargaNominaPjDTO();
                ibCargaNominaPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaNominaPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibCargaNominaPjDTO;
    }

    @Override
    public UtilDTO procesarArchivoNominaPj(IbCargaArchivoDTO ibCargaArchivoDto) {
        UtilDTO utilDTO = new UtilDTO();

        byte[] data = ibCargaArchivoDto.getDataFile();

        IbCargaNominaPjDTO ibCargaNominaPjDTO = new IbCargaNominaPjDTO();
        IbErroresCargaPjDTO ibErroresCargaPjDTO;
        IbCargaNominaDetallePjDTO ibCargaNominaDetallePjDTO = new IbCargaNominaDetallePjDTO();
        ibCargaNominaDetallePjDTO.setIbCargaNominaDetallesPjList(new ArrayList());
        BigDecimal montoTotalAplicarCalculado = BigDecimal.ZERO;
        String line = "";

        /**
         * iDetallesOK contador de lineas de archivo exitosas
         */
        int iDetallesOK = 0;

        /**
         * nroLinea contador de linea fisica en el archivo
         */
        int nroLinea = 0;
        Integer idpago = null;
        Date fechaHoraPago = null;
        Date fechaHoraCarga = null;
        String cdCuentaCliente = "";
        BigDecimal cdConvenioCliente = null;
        String horaDesde, horaHasta;
        int minimoHorasAnticipacion;
        int cantidadLineasDetalleSegunCabecera = 0;
        boolean paramValidarDiasFeriado = true;

        try {
            Map<String, String> mapErrorres = this.ibCargaNominaPjFacade.listarErrorresNominaCargaMasiva();
            Map param = new HashMap();

            ValidatorCargaNomina validatorCargaNomina = new ValidatorCargaNomina(ibCargaArchivoDto);
            validatorCargaNomina.clearErrors();
            validatorCargaNomina.setMapErrorres(mapErrorres);

            //Temporal, pruebas
            //data = validatorCargaNomina.readfile("C:/desarrollo/nomina/pruebas/"+ibCargaArchivoDto.getFileName());
            horaDesde = parametro(COD_HORA_MININA, ibCargaArchivoDto.getCodigoCanal());
            horaHasta = parametro(COD_HORA_MAXIMA, ibCargaArchivoDto.getCodigoCanal());
            minimoHorasAnticipacion = Integer.valueOf(parametro(COD_MINIMO_HORAS_ANTICIPACION, ibCargaArchivoDto.getCodigoCanal())).intValue();

            paramValidarDiasFeriado = (parametro(COD_VALIDAR_FERIADOS, ibCargaArchivoDto.getCodigoCanal()).equals(VALIDACION_ACTIVA));

            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));

            try {
                line = br.readLine();
                param.put("linea", line);

                //DTO cabecera
                ibCargaNominaPjDTO = validatorCargaNomina.leerCabecera(param);

                //Cantidad de lineas presentes en el detalle, segun la cabecera
                cantidadLineasDetalleSegunCabecera = ibCargaNominaPjDTO.getIbCargaNominaPj().getCantidadCreditosAplicar().intValue();

                //cantidad de aprobadores
                int caAprobadores = 0;
                IbAprobacionesServiciosPjDTO ibAprobacionesServiciosPjDTO
                        = ibAprobacionesServiciosPjFacade
                                .consultarByServicioEmpresa(
                                        ibCargaArchivoDto.getCdClienteAbanks(),
                                        ServiciosAprobadoresEnum.NOMINA.getDescripcion(),
                                        ibCargaArchivoDto.getIdCanal());

                if (ibAprobacionesServiciosPjDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    caAprobadores = ibAprobacionesServiciosPjDTO.getIbAprobacionServicioPj().getCantAprobadores().intValue();
                    ibCargaNominaPjDTO.getIbCargaNominaPj().setCantidadAprobadoresServicio(new BigDecimal(caAprobadores));
                }
                if (caAprobadores == 0) {
                    validatorCargaNomina.addError(line, nroLinea, mapErrorres.get(ERR_KEY_ALMENOS_UN_APROBADOR));
                }

                fechaHoraCarga = ibCargaNominaPjDTO.getIbCargaNominaPj().getFechaHoraCarga();
                fechaHoraPago = ibCargaNominaPjDTO.getIbCargaNominaPj().getFechaHoraPago();
                cdCuentaCliente = ibCargaNominaPjDTO.getIbCargaNominaPj().getNroCuentaDebito();
                cdConvenioCliente = ibCargaNominaPjDTO.getIbCargaNominaPj().getNroConvenio();

            } catch (Exception e) {
                //DTO error en la Cabecera
                validatorCargaNomina.addError(line, nroLinea, e.getMessage());
            }

            if (validatorCargaNomina.countErrors() == 0) {

                if (paramValidarDiasFeriado) {
                    if (!reglaNoCargarEnDiasFeriados(fechaHoraPago, ibCargaArchivoDto.getCodigoCanal())) {
                        validatorCargaNomina.addError(line, nroLinea, mapErrorres.get(IbCargaNominaPjDaoImpl.ERR_KEY_FERIADO));
                    }
                }

                if (!reglaNoExisteOtroArchivoConMismoNombre(ibCargaNominaPjDTO)) {

                    validatorCargaNomina.addError(line, nroLinea, ERR_KEY_MISMO_NOMBRE,
                            (new Parametro()).add("$nombre", ibCargaNominaPjDTO.getIbCargaNominaPj().getNombreArchivo())
                    );
                }

                if (!this.reglaConvenioCuentaCliente(ibCargaArchivoDto.getCdClienteAbanks(), cdConvenioCliente, cdCuentaCliente, ibCargaArchivoDto.getCodigoCanal())) {
                    String err = mapErrorres.get(IbCargaNominaPjDaoImpl.ERR_KEY_CONVENIO_CORRESPONDA_CLIENTE) + " y "
                            + mapErrorres.get(IbCargaNominaPjDaoImpl.ERR_KEY_CUENTA_PERTENEZCA_CONVENIO).toLowerCase();
                    validatorCargaNomina.addError(line, nroLinea, err);
                }

                if (!isClienteExcluido(ibCargaArchivoDto.getCodigoCanal().toString(), cdConvenioCliente.intValue(), ibCargaArchivoDto.getCodigoCanal())) {
                    //La carga de la información debe ser con un mínimo de anticipación de "X" horas 
                    if (!this.reglaCargaConHorasAnticipacion(fechaHoraPago, minimoHorasAnticipacion)) {
                        validatorCargaNomina.addError(line, nroLinea, ERR_KEY_MINIMO_ANTICIPACION,
                                (new Parametro()).add("$horas", String.valueOf(minimoHorasAnticipacion))
                        );
                    }
                }

                if (!reglaCargaEnHorarioPermitido(horaDesde, horaHasta, fechaHoraPago)) {
                    validatorCargaNomina.addError(line, nroLinea, ERR_KEY_HORARIO_RESTRINGIDO,
                            (new Parametro()).add("$desde", horaDesde)
                                    .add("$hasta", horaHasta)
                    );
                }
            }

            if (validatorCargaNomina.countErrors() == 0) {

                for (nroLinea = 0; (line = br.readLine()) != null;) {
                    nroLinea++;
                    //DTO detalle
                    IbCargaNominaDetallePj ibCargaNominaDetallePj;

                    try {
                        param.put("linea", line);
                        param.put("nroLinea", nroLinea);
                        param.put("fechaHoraCarga", fechaHoraPago);

                        ibCargaNominaDetallePj = (IbCargaNominaDetallePj) validatorCargaNomina.leerDetalle(param);
                        ibCargaNominaDetallePj.setNroLineaArchivo(new BigDecimal(++iDetallesOK));
                        ibCargaNominaDetallePjDTO.getIbCargaNominaDetallesPjList().add(ibCargaNominaDetallePj);
                        montoTotalAplicarCalculado = montoTotalAplicarCalculado.add(ibCargaNominaDetallePj.getMontoPago());

                    } catch (Exception e) {
                        //DTO error en la detalle
                        validatorCargaNomina.addError(line, nroLinea, e.getMessage());
                    }

                }

                //Validacion minimo dos lineas  en el archivo
                if (!this.reglaArchivoTieneMinimoNLineas(nroLinea + 1, NUMERO_LINEAS_MINIMO)) {
                    validatorCargaNomina.addError(line, nroLinea, mapErrorres.get(IbCargaNominaPjDaoImpl.ERR_KEY_MINIMO_DOS_LINEAS));
                }

                //validacion  cabecera registra el numero de lineas presentes en el detalle
                if (!this.reglaCabeceraRegistraMismaCantidadLineasDetalle(nroLinea, cantidadLineasDetalleSegunCabecera)) {
                    validatorCargaNomina.addError(line, nroLinea, mapErrorres.get(IbCargaNominaPjDaoImpl.ERR_KEY_TOTAL_REGISTRO_NUMERO_LINEAS));
                }

                /**
                 * Numero de lineas validas, por aplicar en procesamiento
                 */
                ibCargaNominaPjDTO.getIbCargaNominaPj().setCantidadCreditosAplicar(new BigDecimal(iDetallesOK));

                /**
                 * Monto aplicar es la suma de los montos cuyos detalles estan
                 * OK en la validacion de carga email CAMBIOS A NIVEL DE NÓMINA
                 * y PAGOS ESPECIALES: miércoles 17-05-2017 10:33 a.m
                 */
                ibCargaNominaPjDTO.getIbCargaNominaPj().setMontoTotalAplicar(montoTotalAplicarCalculado);

                if (this.reglaDebeProcesarAlmenosUnDetalleParaPersistencia(ibCargaNominaDetallePjDTO)) {
                    utilDTO = this.insertarIbCargaNominaPj(
                            ibCargaNominaPjDTO,
                            ibCargaNominaDetallePjDTO,
                            ibCargaArchivoDto.getIdCanal(),
                            ibCargaArchivoDto.getCodigoCanal());
                } else {
                    validatorCargaNomina.addError(line, nroLinea, mapErrorres.get(IbCargaNominaPjDaoImpl.ERR_KEY_ALMENOS_UN_DETALLE_CARGADO_PARA_PERSISTENCIA));
                }

            }

            //si hay un error el insert retorna nulls en la respuesta y el resultado 
            if (utilDTO.getRespuesta() == null) {
                utilDTO.setRespuesta(new RespuestaDTO());
            }
            utilDTO.getRespuesta().setCodigoSP(CODIGO_RESPUESTA_EXITOSO);

            //se lee el idpago, en caso de exitir
            try {
                idpago = ibCargaNominaPjDTO.getIbCargaNominaPj().getIdPago().intValue();
            } catch (Exception e) {
            }

            //Nos interesa enviar solo los errores 
            utilDTO.setResulados(new HashMap());
            utilDTO.getResulados().put(ID_PAGO, idpago);
            utilDTO.getResulados().put(LISTA_ERRORES_CARGA_NOMINA_MASIVA, validatorCargaNomina.getIbErroresCargaPjDTO());

            return utilDTO;

        } catch (IOException ioex) {
            logger.error( new StringBuilder("ERROR IO : ")
                    .append("-CH- ").append(ibCargaArchivoDto.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ioex.getCause()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        } catch (Exception ex) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaNominaPj: ")
                    .append("-CH- ").append(ibCargaArchivoDto.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ex.getCause()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        }
        return utilDTO;
    }

    public boolean isDiaFeriado(Date feCarga, String codigoCanal) {
        boolean feriado = delsurDAO.isDiaFeriado(BDSUtil.formatearFecha(feCarga, "dd/MM/yyyy"), codigoCanal);
        return feriado;
    }

    public boolean isClienteExcluido(String cdClienteAbanks,
            int cdConvenioCliente,
            String codigoCanal) {

        UtilDTO util = this.ibNominaPjDAO.validarClienteExcluido(
                cdClienteAbanks, cdConvenioCliente, codigoCanal);

        if ((util != null && util.getRespuesta().getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)
                && util.getRespuesta().getCodigoSP().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO_SP))) {
            if (!util.getResulados().containsValue("N")) {
                return Boolean.TRUE;
            }
        }
        return false;
    }

    /**
     * Valida en un rango de horas
     *
     * @param horaDesde, formato HH:mm (24horas)
     * @param horaHasta, formato HH:mm (24horas)
     * @param fechaHoraCarga
     * @return
     */
    public boolean reglaCargaEnHorarioPermitido(String horaDesde, String horaHasta, Date fechaHoraCarga) {
        String horaCarga = BDSUtil.formatearFecha(fechaHoraCarga, "HHmm");
        int iCarga = Integer.valueOf(horaCarga);
        int iDesde = Integer.valueOf(horaDesde) * 100;
        int iHasta = Integer.valueOf(horaHasta) * 100;

        //en este caso fecha hasta es ya del dia siguiente, por eso 24horas mas
        if (iDesde > iHasta) {
            iHasta += 2400;
        }

        // iCarga Between iDesde AND iHasta
        return Validator.between(iCarga, iDesde, iHasta);

    }

    public boolean reglaConvenioCuentaCliente(BigDecimal cdClienteAbanks, BigDecimal cdConvenioCliente, String cdCuentaCliente, String codigoCanal) {
        return this.ibNominaPjDAO.validarConvenio(cdClienteAbanks.toString(), cdConvenioCliente, cdCuentaCliente, codigoCanal);
    }

    public boolean reglaNoCargarEnDiasFeriados(Date feCarga, String codigoCanal) {
        return !delsurDAO.isDiaFeriado(BDSUtil.formatearFecha(feCarga, "dd/MM/yyyy"), codigoCanal);
    }

    public boolean reglaNoExisteOtroArchivoConMismoNombre(IbCargaNominaPjDTO ibCargaNominaPjDTO) throws Exception {
        return !ibCargaNominaPjFacade.archivoNominaCargaMasivaExiste(
                ibCargaNominaPjDTO.getIbCargaNominaPj().getCodigoClienteAbanks().toString(),
                ibCargaNominaPjDTO.getIbCargaNominaPj().getNombreArchivo());
    }

    public boolean reglaArchivoTieneMinimoNLineas(int nroLinea, int minimo) {
        return (nroLinea >= minimo);
    }

    public boolean reglaCabeceraRegistraMismaCantidadLineasDetalle(int nroLineasArchivo, int lineasDetalleEnCabecera) {
        /**
         * resta 1, la linea de la cabecera
         */
        return (nroLineasArchivo == lineasDetalleEnCabecera);
    }

    public boolean reglaCargaConHorasAnticipacion(Date fechaHoraPago, long minimoHorasAnticipacion) {
        return (Validator.diferenciaDeHorasEntre(fechaHoraPago, Validator.ahora()) >= minimoHorasAnticipacion);
    }

    public boolean reglaDebeProcesarAlmenosUnDetalleParaPersistencia(IbCargaNominaDetallePjDTO ibCargaNominaDetallePjDTO) {
        return ibCargaNominaDetallePjDTO.getIbCargaNominaDetallesPjList().size() > 0;
    }

    public String parametro(String codigo, String codigoCanal) {
        return ibParametrosFacade
                .consultaParametro(codigo, codigoCanal)
                .getIbParametro().getValor();
    }

    @Override
    public UtilDTO eliminarCargaMasivaEstatusCeroPj(BigDecimal idPago, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        utilDTO.setRespuesta(new RespuestaDTO());
        try {
            IbCargaNominaPj cargaNomina = ibCargaNominaPjFacade.buscarIbCargaNominaPj(idPago);
            if (cargaNomina != null) {
                if (cargaNomina.getEstatus().getId() == BigDecimal.ZERO) {
                    respuesta = this.ibCargaNominaPjFacade.eliminar(cargaNomina);
                } else {
                    respuesta.setCodigo(CODIGO_ERROR_VALIDACIONES);
                    respuesta.setDescripcion("Nomina no esta en estatus en precargada");
                }
            } else {
                respuesta.setCodigo(CODIGO_ERROR_VALIDACIONES);
                respuesta.setDescripcion("Nomina no encontrada");
            }
            utilDTO.setRespuesta(respuesta);
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR IbCargaNominaPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaNominaPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;
    }
    
    
     @Override
    public IbCargaNominaPjDTO listarCargaNominaPjAdmin(BigDecimal clienteAbanks,Long idPago,Date fechaInicio, Date fechaFin) {
        IbCargaNominaPjDTO ibCargaNominaPjDTO = new IbCargaNominaPjDTO();
        Collection<IbCargaNominaDetallePj> ibCargaNominaDetallePjCollection = new ArrayList<>();
        IbCargaNominaDetallePjDTO ibCargaNominaDetallePjDTO = new IbCargaNominaDetallePjDTO();
        try {
              ibCargaNominaPjDTO = this.ibCargaNominaPjFacade.listarCargaNominaPjAdmin(clienteAbanks, idPago, fechaInicio, fechaFin);
           

        } catch (Exception e) {

            logger.error( new StringBuilder("ERROR DAO en listarIbCargaNominaPj: ")
                    .append("-CH- ").append(idPago)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaNominaPjDTO == null || ibCargaNominaPjDTO.getRespuestaDTO() == null) {
                ibCargaNominaPjDTO = new IbCargaNominaPjDTO();
                ibCargaNominaPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaNominaPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibCargaNominaPjDTO;
    }

    // ibCargaNominaPjFacade.buscarIbCargaNominaPj(idPago);
}
