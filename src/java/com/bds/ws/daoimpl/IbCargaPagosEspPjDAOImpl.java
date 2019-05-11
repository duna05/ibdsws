/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.EMailDAO;
import com.bds.ws.dao.IbCargaPagosEspDetPjDAO;
import com.bds.ws.dao.IbCargaPagosEspPjDAO;
import com.bds.ws.dao.IbEmpresasUsuariosDAO;
import com.bds.ws.dto.IbAprobacionesServiciosPjDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaPagosEspDetPjDTO;
import com.bds.ws.dto.IbCargaPagosEspPjDTO;
import com.bds.ws.dto.IbErroresCargaPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusPlantillasEmail;
import com.bds.ws.enumerated.ServiciosAprobadoresEnum;
import com.bds.ws.exception.IbErroresCargaPjException;
import com.bds.ws.facade.IbAprobacionesServiciosPjFacade;
import com.bds.ws.facade.IbCargaPagosEspDetPjFacade;
import com.bds.ws.facade.IbCargaPagosEspPjFacade;
import com.bds.ws.facade.IbParametrosFacade;
import com.bds.ws.model.IbCargaPagosEspDetPj;
import com.bds.ws.model.IbCargaPagosEspPj;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.model.IbUsuariosPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO_SP;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.ID_PAGO;
import com.bds.ws.validator.ValidatorCargaPagosEspeciales;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * @author robinson.rodriguez
 */
@Named("IbCargaPagosEspPjDAO")
@Stateless
public class IbCargaPagosEspPjDAOImpl extends BDSUtil implements IbCargaPagosEspPjDAO {

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbCargaPagosEspPjDAOImpl.class.getName());

    @EJB
    private IbCargaPagosEspPjFacade ibCargaPagosEspPjFacade;

    @EJB
    private IbCargaPagosEspDetPjDAO ibCargaPagosEspDetPjDAO;

    @EJB
    private IbParametrosFacade ibParametrosFacade;

    @EJB
    private EMailDAO emailDAO;

    @EJB
    private IbEmpresasUsuariosDAO ibEmpresasUsuariosDAO;

    @EJB
    private IbAprobacionesServiciosPjFacade ibAprobacionesServiciosPjFacade;

    @EJB
    private IbCargaPagosEspDetPjFacade ibCargaPagosEspDetPjFacade;

    private final IbErroresCargaPjDTO ibErroresCargaPjDTOLista = new IbErroresCargaPjDTO();

    public final static String ERR_KEY_MISMO_NOMBRE = "pjw.cargaMasPagosEspeciales.error.archivo.mismoNombre";
    public final static String ERR_KEY_MINIMO_UNA_LINEA = "pjw.cargaMasPagosEspeciales.error.archivo.1Lineas";
    public final static String ERR_KEY_TOTAL_REGISTRO_NUMERO_LINEAS = "pjw.cargaMasPagosEspeciales.error.archivo.totalRegNumLineas";
    public final static String ERR_KEY_LINEA_CABECERA_TAMANO = "pjw.cargaMasPagosEspeciales.error.archivo.lineaCabTam";
    public final static String ERR_KEY_MONTO_NUMEROS = "pjw.cargaMasPagosEspeciales.error.archivo.MontoNumeros";
    public final static String ERR_KEY_TOTAL_REGISTROS = "pjw.cargaMasPagosEspeciales.error.archivo.totalRegistros";
    public final static String ERR_KEY_LINEA_DETALLE_TAMANO = "pjw.cargaMasPagosEspeciales.error.archivo.lineaDetalleTamano";
    public final static String ERR_KEY_CUENTA_SOLO_NUMERO = "pjw.cargaMasPagosEspeciales.error.archivo.cuentaSoloNumeros";
    public final static String ERR_KEY_BENEFICIARIO_ALFANUMERICO = "pjw.cargaMasPagosEspeciales.error.archivo.beneficiarioAlfNum";
    public final static String ERR_KEY_ALFANUMERICO = "pjw.cargaMasPagosEspeciales.error.archivo.alfNum";
    public final static String ERR_KEY_CORREO = "pjw.cargaMasPagosEspeciales.error.archivo.correo";
    public final static String ERR_KEY_ALFABETICO = "pjw.cargaMasPagosEspeciales.error.archivo.alfabetico";
    public final static String ERR_KEY_ALMENOS_UN_DETALLE_CARGADO_PARA_PERSISTENCIA = "pjw.cargaMasPagosEspeciales.error.archivo.minDetPersistencia";
    public final static String ERR_KEY_ALMENOS_UN_APROBADOR = "pjw.cargaMasPagosEspeciales.error.archivo.min1aprobador";
    public final static String ERR_KEY_INDICE_RIF_INVALIDO = "pjw.cargaMasPagosEspeciales.error.archivo.indiceInvalido";
    public final static String ERR_KEY_NUMERADOR_RIF_INVALIDO = "pjw.cargaMasPagosEspeciales.error.archivo.indNumInvalido";
    public final static String ERR_KEY_ID_PAGO_NULL = "pjw.cargaMasPagosEspeciales.error.archivo.idPagoNull";

    public final static int NUMERO_LINEAS_MINIMO = 1;

    @Override
    public IbCargaPagosEspPjDTO listarIbCargaPagosEspPj(BigDecimal idpago, Short estatusCarga, String idCanal, String codigoCanal) {
        IbCargaPagosEspPjDTO ibCargaPagosEspPjDTO = new IbCargaPagosEspPjDTO();
        try {
            ibCargaPagosEspPjDTO = this.ibCargaPagosEspPjFacade.listarIbCargaPagosEspPj(idpago, estatusCarga);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO en listarIbCargaPagosEspPj: ")
                    .append("ID-CH- ").append(idCanal)
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaPagosEspPjDTO == null || ibCargaPagosEspPjDTO.getRespuestaDTO() == null) {
                ibCargaPagosEspPjDTO = new IbCargaPagosEspPjDTO();
                ibCargaPagosEspPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaPagosEspPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibCargaPagosEspPjDTO;
    }

    @Override
    public UtilDTO insertarIbCargaPagosEspPj(IbCargaPagosEspPjDTO ibCargaPagosEspPjDTO, IbCargaPagosEspDetPjDTO pagosEspecialesDetalleDTO, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            IbCargaPagosEspPj cargaPagosEspeciales = ibCargaPagosEspPjDTO.getIbCargaPagosEspPj();
            if (cargaPagosEspeciales != null) {
                respuesta = ibCargaPagosEspPjFacade.crearPJ(cargaPagosEspeciales);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL INSERTAR IbCargaPagosEspPj");
                }
                for (int i = 0; i < pagosEspecialesDetalleDTO.getIbCargaPagosEspDetPjsList().size(); i++) {
                    IbCargaPagosEspDetPj detalle = pagosEspecialesDetalleDTO.getIbCargaPagosEspDetPjsList().get(i);
                    detalle.setIdPago(cargaPagosEspeciales);
                    detalle.setCodigoClienteAbanks(cargaPagosEspeciales.getCodigoClienteAbanks());
                    if (detalle.getNroLineaArchivo() == 0) {
                        detalle.setNroLineaArchivo(i + 1);
                    }
                    UtilDTO utilDetalle = ibCargaPagosEspDetPjDAO.insertarIbCargaPagosEspPj(detalle, codigoCanal);
                    if (utilDetalle.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)
                            && utilDetalle.getRespuesta().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO_SP)) {
                        pagosEspecialesDetalleDTO.getIbCargaPagosEspDetPjsList().get(i)
                                .setIdPagoDetalle(((IbCargaPagosEspDetPj) utilDetalle.getResulados().get(NOMBRE_CABECERA_DETALLE)).getIdPagoDetalle());
                    }
                }
                cargaPagosEspeciales.setIbCargaPagosEspDetPjCollection(pagosEspecialesDetalleDTO.getIbCargaPagosEspDetPjsList());
                ibCargaPagosEspPjDTO.setIbCargaPagosEspPj(cargaPagosEspeciales);
                resultado.put(NOMBRE_CABECERA_DETALLE, ibCargaPagosEspPjDTO);
                resultado.put(NOMBRE_DETALLE, pagosEspecialesDetalleDTO);
                respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
                utilDTO.setRespuesta(respuesta);
                utilDTO.setResulados(resultado);
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarIbCargaPagosEspPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarIbCargaPagosEspPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return utilDTO;

    }

    @Override
    public UtilDTO modificarEstatusIbCargaPagosEspPj(Long idPago, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, BigDecimal idEmpresa, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        try {
            IbCargaPagosEspPj cargaPagosEspeciales = ibCargaPagosEspPjFacade.buscarIbCargaPagosEspPj(idPago);
            if (cargaPagosEspeciales != null) {
                cargaPagosEspeciales.setEstatus(ibEstatusPagosPjDTO.getIbEstatusPagosPj());
                respuesta = this.ibCargaPagosEspPjFacade.modificarPj(cargaPagosEspeciales);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaPagosEspPj");
                }
                UtilDTO utilDTODetalle = this.ibCargaPagosEspDetPjDAO.modificarEstatusIbCargaPagosEspDetPj(idPago, ibEstatusPagosPjDTO, codigoCanal);
                if (!utilDTODetalle.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaPagosEspDetPj");
                }
                //SIEMPRE SE ENVIA EL CODIGO 1 AL SP
                String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
                //ajustar codigo de la plantilla a usar
                String codigoPlantilla = EstatusPlantillasEmail.REGISTRO_USUARIO_TEXT.getDescripcion();
                UtilDTO utilUsuarios = this.ibEmpresasUsuariosDAO.consultarEmpresasUsuariosAprobadores(idEmpresa);
                if (utilUsuarios == null || !utilUsuarios.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaPagosEspPj ENVIO DE CORREO");
                }
                List<IbEmpresasUsuariosPj> listaUsuarios = (List<IbEmpresasUsuariosPj>) utilUsuarios.getResulados().get(NOMBRE_APROVADOR);

                //SI NO EXISTEN REPRESENTANTES LEGALES NO SE ENVIA CORREO
                if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                    for (IbEmpresasUsuariosPj usuario : listaUsuarios) {
                        String parametrosCorreo = usuario.getIdUsuarioPj().getNombre() + "~" + usuario.getIdEmpresa().getNombre();
                        //ENVIAR EMAIL 
                        emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, usuario.getEmail(), parametrosCorreo, idCanal, codigoCanal);
                    }
                }
                utilDTO.setRespuesta(respuesta);
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR IbCargaPagosEspPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaPagosEspPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;
    }

    @Override
    public UtilDTO procesarArchivoPagosEspecialesPj(IbCargaArchivoDTO ibCargaArchivoDto) {
        UtilDTO utilDTO = new UtilDTO();

        byte[] data = ibCargaArchivoDto.getDataFile();

        IbCargaPagosEspPjDTO ibCargaPagosEspPjDTO = new IbCargaPagosEspPjDTO();

        ibErroresCargaPjDTOLista.setIbErroresCargaPjDTO(new ArrayList<IbErroresCargaPjDTO>());

        IbCargaPagosEspDetPjDTO ibCargaPagosEspDetPjDTO = new IbCargaPagosEspDetPjDTO();
        ibCargaPagosEspDetPjDTO.setIbCargaPagosEspDetPjsList(new ArrayList());

        Long totalMontoaAplicar = 0l;
        Long countCantRech = 0l;
        Integer idpago = null;
        String line = "";
        String detLinea = "";
        int nroLinea = 0;
        int detNroLinea = 0;
        int countLineaTotal = 0;

        try {

            //Temporal, pruebas
//            data = readfile("C:/Pagos_Especiales_archivo_pruebas/" + ibCargaArchivoDto.getFileName());
            Map<String, String> mapErrorres = ibCargaPagosEspPjFacade.listarErroresPagosEspecialesCargaMasiva();
            Map param = new HashMap();

            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));

            ValidatorCargaPagosEspeciales validatorCargaPagosEspeciales = new ValidatorCargaPagosEspeciales(ibCargaArchivoDto);
            validatorCargaPagosEspeciales.clearErrors();
            validatorCargaPagosEspeciales.setMapErrorres(mapErrorres);

            int caAprobadores = 0;
            try {
                //Cantidad de aprobadores
                
                IbAprobacionesServiciosPjDTO ibAprobacionesServiciosPjDTO = 
                    ibAprobacionesServiciosPjFacade.consultarByServicioEmpresa(
                        ibCargaArchivoDto.getCdClienteAbanks(), ServiciosAprobadoresEnum.PAGOS_ESPECIALES.getDescripcion(), ibCargaArchivoDto.getIdCanal());

                if(ibAprobacionesServiciosPjDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                    caAprobadores = ibAprobacionesServiciosPjDTO.getIbAprobacionServicioPj().getCantAprobadores().intValue();
                }
                if (caAprobadores == 0) {
                    throw new IbErroresCargaPjException(mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_ALMENOS_UN_APROBADOR));
                }
            } catch (Exception e) {
                validatorCargaPagosEspeciales.addError(line, nroLinea, mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_ALMENOS_UN_APROBADOR));
            }

            if (validatorCargaPagosEspeciales.countErrors() == 0) {
                if (!reglaNoExisteOtroArchivoConMismoNombre(ibCargaArchivoDto)) {
                    validatorCargaPagosEspeciales.addError(line, nroLinea, mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_MISMO_NOMBRE));
                }
            }

            if (validatorCargaPagosEspeciales.countErrors() == 0) {
                for (nroLinea = 0; (line = br.readLine()) != null;) {
                    nroLinea++;

                    //DTO detalle
                    IbCargaPagosEspDetPj ibCargaPagosEspDetPj;

                    try {
                        param.put("linea", line);
                        param.put("nroLinea", nroLinea);

                        ibCargaPagosEspDetPj = (IbCargaPagosEspDetPj) validatorCargaPagosEspeciales.leerDetalle(param);
                        ibCargaPagosEspDetPj.setCodigoClienteAbanks(((BigDecimal) ibCargaArchivoDto.getCdClienteAbanks()).intValue());
                        ibCargaPagosEspDetPjDTO.getIbCargaPagosEspDetPjsList().add(ibCargaPagosEspDetPj);
                        countLineaTotal++;
                        totalMontoaAplicar = totalMontoaAplicar + ((BigDecimal) ibCargaPagosEspDetPj.getMontoPago()).longValue();
                    } catch (Exception e) {
                        detLinea = line;
                        detNroLinea = nroLinea;
                        countCantRech++;
                        //DTO error en la detalle
                        validatorCargaPagosEspeciales.addError(line, nroLinea, e.getMessage());
                    }
                }

                //DTO encabezado
                IbCargaPagosEspPj ibCargaPagosEspPj;

                ibCargaPagosEspPj = new IbCargaPagosEspPj();
                ibCargaPagosEspPj.setCodigoClienteAbanks((ibCargaArchivoDto.getCdClienteAbanks()).longValue());
                ibCargaPagosEspPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaArchivoDto.getCodigoUsuario()));
                ibCargaPagosEspPj.setNombreArchivo(ibCargaArchivoDto.getFileName());
                ibCargaPagosEspPj.setMotivoDePago("-");
                ibCargaPagosEspPj.setMontoTotalAplicar(new BigDecimal(totalMontoaAplicar));
                ibCargaPagosEspPj.setCantidadCreditosAplicar(new BigDecimal(countLineaTotal));
                ibCargaPagosEspPj.setNroCuentaDebito(ibCargaArchivoDto.getNroCtaDebito());
                ibCargaPagosEspPj.setEstatus(new IbEstatusPagosPj(BigDecimal.ZERO));
                ibCargaPagosEspPj.setMontoTotalAplicado(BigDecimal.ZERO);
                ibCargaPagosEspPj.setCantidadCreditosAplicados(BigDecimal.ZERO);
                ibCargaPagosEspPj.setCantidadCreditosRechazados(new BigDecimal(countCantRech));
                ibCargaPagosEspPj.setCantidadAprobadoresServicio(caAprobadores);
                ibCargaPagosEspPj.setSecuenciaCumplida(BigDecimal.ZERO);
                ibCargaPagosEspPj.setFechaHoraPago(ibCargaArchivoDto.getFechaPago());

                ibCargaPagosEspPjDTO.setIbCargaPagosEspPj(ibCargaPagosEspPj);

                //Validación minimo una linea a registrar
                if (this.reglaDebeProcesarAlmenosUnDetalleParaPersistencia(ibCargaPagosEspDetPjDTO)) {
                    utilDTO = insertarIbCargaPagosEspPj(
                            ibCargaPagosEspPjDTO,
                            ibCargaPagosEspDetPjDTO,
                            ibCargaArchivoDto.getIdCanal(),
                            ibCargaArchivoDto.getCodigoCanal());
                } else {
                    validatorCargaPagosEspeciales.addError(detLinea, detNroLinea, mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_ALMENOS_UN_DETALLE_CARGADO_PARA_PERSISTENCIA));
                }

            }

            //Si hay un error el insert retorna nulls en la respuesta y el resultado 
            if (utilDTO.getRespuesta() == null) {
                utilDTO.setRespuesta(new RespuestaDTO());
            }
            utilDTO.getRespuesta().setCodigoSP(CODIGO_RESPUESTA_EXITOSO);

//            Se lee el idpago, en caso de exitir
            try {
                idpago = ibCargaPagosEspPjDTO.getIbCargaPagosEspPj().getIdPago().intValue();
            } catch (Exception e) {
                validatorCargaPagosEspeciales.addError(String.valueOf(idpago), nroLinea, mapErrorres.get(IbCargaPagosEspPjDAOImpl.ERR_KEY_ID_PAGO_NULL));
            }

            //Nos interesa enviar solo los errores 
            utilDTO.setResulados(new HashMap());
            utilDTO.getResulados().put(ID_PAGO, idpago);
            utilDTO.getResulados().put(LISTA_ERRORES_CARGA_PAGOS_ESPECIALES_MASIVA, validatorCargaPagosEspeciales.getIbErroresCargaPjDTO());

            return utilDTO;

        } catch (IOException ioex) {
            logger.error( new StringBuilder("ERROR IO : ")
                    .append("-CH- ").append(ibCargaArchivoDto.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ioex.getCause()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        } catch (Exception ex) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaPagosEspPj: ")
                    .append("-CH- ").append(ibCargaArchivoDto.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ex.getMessage()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);

        }
        return utilDTO;

    }
// Valida si existe otro archivo con el mismo nombre

    public boolean reglaNoExisteOtroArchivoConMismoNombre(IbCargaArchivoDTO ibCargaArchivoDto) throws Exception {
        return !ibCargaPagosEspPjFacade.archivoPagosEspecialesCargaMasivaExiste(
                ((BigDecimal) ibCargaArchivoDto.getCdClienteAbanks()).longValue(),
                ibCargaArchivoDto.getFileName());
    }

//   Valida si existe un minimo de registros a insertar 
    public boolean reglaDebeProcesarAlmenosUnDetalleParaPersistencia(IbCargaPagosEspDetPjDTO ibCargaPagosEspDetPjDTO) {
        return ibCargaPagosEspDetPjDTO.getIbCargaPagosEspDetPjsList().size() > 0;
    }

    /**
     * Devuelve el listado autorizado para un usuario
     * @param cdClienteAbanks
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param idCanal
     * @param codigoCanal
     * @return 
     */
    @Override
    public IbCargaPagosEspPjDTO listarIbCargaPagosEspAutorizarPj(BigDecimal cdClienteAbanks, Long idUsuarioAurorizado, Long idServicio, String idCanal, String codigoCanal) {
        IbCargaPagosEspPjDTO ibCargaPagosEspPjDTO = new IbCargaPagosEspPjDTO();
        try {
              ibCargaPagosEspPjDTO = this.ibCargaPagosEspPjFacade.listarIbCargaPagosEspAutorizarPj(cdClienteAbanks, idUsuarioAurorizado,idServicio, USUARIO_CONEXION);
           

        } catch (Exception e) {

            logger.error( new StringBuilder("ERROR DAO en listarIbCargaPagosEspAutorizarPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaPagosEspPjDTO == null || ibCargaPagosEspPjDTO.getRespuestaDTO() == null) {
                ibCargaPagosEspPjDTO = new IbCargaPagosEspPjDTO();
                ibCargaPagosEspPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaPagosEspPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibCargaPagosEspPjDTO;
    }
    
     @Override
    public IbCargaPagosEspPjDTO buscarIbCargaPorParametrosPagosEspPj(BigDecimal clienteAbanks,Long idPago,Date fechaInicio, Date fechaFin) {
        IbCargaPagosEspPjDTO ibCargaPagosEspPjDTO = new IbCargaPagosEspPjDTO();
        try {
            ibCargaPagosEspPjDTO = this.ibCargaPagosEspPjFacade.buscarIbCargaPorParametrosPagosEspPj(clienteAbanks, idPago, fechaInicio, fechaFin);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO en buscarIbCargaPorParametrosPagosEspPj: ")
                    .append("ID-CB- ").append(clienteAbanks)
                    .append("-IP- ").append(idPago)
                    .append("ID-FI- ").append(fechaInicio)
                    .append("-FF- ").append(fechaFin)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaPagosEspPjDTO == null || ibCargaPagosEspPjDTO.getRespuestaDTO() == null) {
                ibCargaPagosEspPjDTO = new IbCargaPagosEspPjDTO();
                ibCargaPagosEspPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaPagosEspPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibCargaPagosEspPjDTO;
    }
    
}
