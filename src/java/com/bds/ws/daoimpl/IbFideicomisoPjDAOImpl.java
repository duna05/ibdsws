package com.bds.ws.daoimpl;

import com.bds.ws.dao.EMailDAO;
import com.bds.ws.dao.IbEmpresasUsuariosDAO;
import com.bds.ws.dao.IbFideicomisoDetPjDAO;
import com.bds.ws.dao.IbFideicomisoPjDAO;
import com.bds.ws.dto.IbAprobacionesServiciosPjDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbErroresCargaPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.IbFideicomisoDetPjDTO;
import com.bds.ws.dto.IbFideicomisoPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusPlantillasEmail;
import com.bds.ws.enumerated.FideicomisoEnum;
import com.bds.ws.enumerated.ServiciosAprobadoresEnum;
import com.bds.ws.facade.IbAprobacionesServiciosPjFacade;
import com.bds.ws.facade.IbFideicomisoDetPjFacade;
import com.bds.ws.facade.IbFideicomisoPjFacade;
import com.bds.ws.facade.IbParametrosFacade;
import com.bds.ws.facade.IbTextosFacade;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.model.IbFideicomisoDetPj;
import com.bds.ws.model.IbFideicomisoPj;
import com.bds.ws.model.IbUsuariosPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.validator.Parametro;
import com.bds.ws.validator.ValidatorCargaFideicomiso;
import com.bds.ws.validator.ValidatorCargaFideicomisoFonz03Excel;
import com.bds.ws.validator.ValidatorCargaFideicomisoFonz03Txt;
import com.bds.ws.validator.ValidatorCargaFideicomisoFonz04Excel;
import com.bds.ws.validator.ValidatorCargaFideicomisoFonz04Txt;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author robinson.rodriguez
 */
@Named("IbFideicomisoPjDAO")
@Stateless
public class IbFideicomisoPjDAOImpl extends BDSUtil implements IbFideicomisoPjDAO {

    private final static String ERR_KEY_ALMENOS_UN_APROBADOR = "pjw.fideicomisoCargaMasiva.error.1aprobador";
    private final static String ERR_KEY_CODIGO_FIDEICOMISO = "pjw.fideicomisoCargaMasiva.error.codigo_fideicomiso";
    private final static String ERR_KEY_MISMO_NOMBRE = "pjw.fideicomisoCargaMasiva.error.archivo.mismoNombre";
    private final static String ERR_KEY_MISMO_NOMBRE_FONZ04 = "pjw.fideicomisoCargaMasiva4.error.archivo.mismoNombre";
    
    public final static String FORMAT_TXT = "text/plain";
    public final static String FORMAT_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public final static String FORMAT_XLS = "application/vnd.ms-excel";

    private ValidatorCargaFideicomiso validatorCargaFideicomiso;

    @EJB
    private IbFideicomisoPjFacade ibFideicomisoPjFacade;

    @EJB
    private IbFideicomisoDetPjFacade ibFideicomisoDetPjFacade;

    @EJB
    private IbFideicomisoDetPjDAO ibFideicomisoDetPjDAO;

    @EJB
    private IbTextosFacade ibTextosFacade;

    @EJB
    private IbAprobacionesServiciosPjFacade ibAprobacionesServiciosPjFacade;

    @EJB
    private IbParametrosFacade ibParametrosFacade;

    @EJB
    private IbEmpresasUsuariosDAO ibEmpresasUsuariosDAO;

    @EJB
    private EMailDAO emailDAO;

    private static final Logger logger = Logger.getLogger(IbFideicomisoPjDAOImpl.class.getName());

    private IbFideicomisoPj ibFideicomisoPj;
    private IbFideicomisoDetPj ibFideicomisoDetPj;

    @Override
    public IbFideicomisoPjDTO listarIbFideicomisoPj(BigDecimal cdClienteAbanks, String idCanal, String codigoCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbFideicomisoPjDTO ibFideicomisoPjDTO = new IbFideicomisoPjDTO();
        try {
            List<IbFideicomisoPj> fideicomiso = ibFideicomisoPjFacade.listarCabeceraFideicomiso(cdClienteAbanks);
            if (!fideicomiso.isEmpty()) {
                ibFideicomisoPjDTO.setIbFideicomisoPjsList(fideicomiso);
            } else {
                ibFideicomisoPjDTO.setIbFideicomisoPjsList(new ArrayList<IbFideicomisoPj>());
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR AL CONSULTAR listarIbFideicomisoPj: ")
                    .append("ID-CH- ").append(idCanal)
                    .append("-CH- ").append(codigoCanal)
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return ibFideicomisoPjDTO;
    }

    @Override
    public UtilDTO procesarArchivoPj(IbCargaArchivoDTO ibCargaArchivoDto) {
        ibFideicomisoPj = new IbFideicomisoPj();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbFideicomisoPjDTO ibFideicomisoPjDTO = new IbFideicomisoPjDTO();
        IbFideicomisoDetPjDTO ibFideicomisoDetPjDTO = new IbFideicomisoDetPjDTO();
        ibFideicomisoDetPjDTO.setIbFideicomisoDetPjsList(new ArrayList());
        try {
            byte[] data = ibCargaArchivoDto.getDataFile();

            validatorCargaFideicomiso = inicializar(ibCargaArchivoDto);

            //Temporal, pruebas
            //data = validatorCargaFideicomiso.readfile("C:/desarrollo/fideicomiso/pruebas/"+ibCargaArchivoDto.getFileName());
            reglaCantidadAprobadores(ibCargaArchivoDto);

            reglaCodigoFideicomiso(ibCargaArchivoDto);

            reglaNoExisteOtroArchivoConMismoNombre(ibCargaArchivoDto);

            if (validatorCargaFideicomiso.countErrors() == 0) {

                ibFideicomisoPj.setCantidadCreditosAplicar(0);
                ibFideicomisoPj.setCodigoClienteAbanks(ibCargaArchivoDto.getCdClienteAbanks().longValue());
                ibFideicomisoPj.setCodigoFideicomiso(ibCargaArchivoDto.getCodigoFideicomiso());
                ibFideicomisoPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaArchivoDto.getCodigoUsuario()));
                ibFideicomisoPj.setEstatus(new IbEstatusPagosPj(BigDecimal.ZERO));
                ibFideicomisoPj.setFechaHoraCarga(new Date());
                ibFideicomisoPj.setIndicadorTipoOperacion(ibCargaArchivoDto.getEstructura());
                ibFideicomisoPj.setMontoTotalAplicar(BigDecimal.ZERO);
                ibFideicomisoPj.setNombreArchivo(ibCargaArchivoDto.getFileName());
                ibFideicomisoPj.setSecuenciaCumplida((short) 0);

                Map param = new HashMap();
                String line;

                if (ibCargaArchivoDto.getFormatoArchivo().equals(FORMAT_TXT)) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));
                    try {
                        ibFideicomisoDetPjDTO.setIbFideicomisoDetPjsList(new ArrayList<IbFideicomisoDetPj>());
                        for (int nroLinea = 0; (line = br.readLine()) != null;) {
                            nroLinea++;
                            param.put("linea", line);
                            param.put("nroLinea", nroLinea);
                            param.put("codigoClienteAbanks", ibFideicomisoPj.getCodigoClienteAbanks());

                            try {
                                ibFideicomisoDetPj = validatorCargaFideicomiso.leerDetalle(param);
                                if (ibFideicomisoDetPj != null) {
                                    ibFideicomisoDetPjDTO.getIbFideicomisoDetPjsList().add(ibFideicomisoDetPj);
                                    if (ibFideicomisoDetPj.getMontoPago() != null) {
                                        ibFideicomisoPj.setMontoTotalAplicar(ibFideicomisoPj.getMontoTotalAplicar().add(ibFideicomisoDetPj.getMontoPago()));
                                    }
                                }
                                ibFideicomisoPj.setCantidadCreditosAplicar(nroLinea);
                            } catch (Exception e) {
                                validatorCargaFideicomiso.addError(line, nroLinea, e.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        validatorCargaFideicomiso.addError("", 0, "Error en la carga");
                    }
                } else if (ibCargaArchivoDto.getFormatoArchivo().equals(FORMAT_XLS)) {
                    HSSFWorkbook workbookXLS = new HSSFWorkbook();
                    workbookXLS = new HSSFWorkbook(new ByteArrayInputStream(data));
                    HSSFSheet sheet = workbookXLS.getSheetAt(0);
                    Iterator<Row> rowIterator = sheet.iterator();
                    recorrerRowIterator(rowIterator, ibFideicomisoDetPjDTO);
                } else if (ibCargaArchivoDto.getFormatoArchivo().equals(FORMAT_XLSX)) {
                    XSSFWorkbook workbookXLSX = new XSSFWorkbook(new ByteArrayInputStream(data));
                    XSSFSheet sheet = workbookXLSX.getSheetAt(0);
                    Iterator<Row> rowIterator = sheet.iterator();
                    recorrerRowIterator(rowIterator, ibFideicomisoDetPjDTO);
                    workbookXLSX.close();
                }
            }
            ibFideicomisoPjDTO.setIbFideicomisoPj(ibFideicomisoPj);
            UtilDTO utilDTO = new UtilDTO();
            utilDTO.setRespuesta(new RespuestaDTO());
            utilDTO.setResulados(new HashMap());
            IbErroresCargaPjDTO ibErroresCargaPjDTO = new IbErroresCargaPjDTO();
            ibErroresCargaPjDTO.setIbErroresCargaPjDTO(validatorCargaFideicomiso.getIbErroresCargaPjDTOLista());
            utilDTO.getResulados().put(BDSUtil.LISTA_ERRORES_CARGA_ARCHIVO, ibErroresCargaPjDTO);

            if (validatorCargaFideicomiso.getIbErroresCargaPjDTOLista().size() == 0) {
                utilDTO = this.insertarIbFideicomisoPj(
                        ibFideicomisoPjDTO,
                        ibFideicomisoDetPjDTO,
                        ibCargaArchivoDto.getIdCanal(),
                        ibCargaArchivoDto.getCodigoCanal());

            } else {
                //utilDTO.getRespuesta().setCodigo(CODIGO_ERROR_VALIDACIONES);
            }
            return utilDTO;
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR procesarArchivoPj: ")
                    .append("ID-CH- ").append(ibCargaArchivoDto.getIdCanal())
                    .append("-CH- ").append(ibCargaArchivoDto.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return null;
    }

    private void recorrerRowIterator(Iterator<Row> rowIterator, IbFideicomisoDetPjDTO ibFideicomisoDetPjDTO) {
        Row row;
        Integer cantidadCredaitosAplicar = 0;
        Integer nroLinea = 0;
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            row.cellIterator();
            if (nroLinea > 0) {
                ibFideicomisoDetPj = validatorCargaFideicomiso.leerDetalle(row, nroLinea);
                if (ibFideicomisoDetPj != null) {
                    ibFideicomisoDetPjDTO.getIbFideicomisoDetPjsList().add(ibFideicomisoDetPj);
                    if (ibFideicomisoDetPj.getMontoPago() != null) {
                        ibFideicomisoPj.setMontoTotalAplicar(ibFideicomisoPj.getMontoTotalAplicar().add(ibFideicomisoDetPj.getMontoPago()));
                    }
                    cantidadCredaitosAplicar++;
                }
            }
            nroLinea++;
        }
        ibFideicomisoPj.setCantidadCreditosAplicar(cantidadCredaitosAplicar);
    }

    @Override
    public UtilDTO insertarIbFideicomisoPj(IbFideicomisoPjDTO ibFideicomisoPjDTO, IbFideicomisoDetPjDTO ibFideicomisoDetPjDTO, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            IbFideicomisoPj cabecera = ibFideicomisoPjDTO.getIbFideicomisoPj();
            if (cabecera != null) {
                respuesta = this.ibFideicomisoPjFacade.crearPJ(cabecera);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL INSERTAR IbCargaNominaPj");
                }
                int i = 0;
                for (IbFideicomisoDetPj detalle : ibFideicomisoDetPjDTO.getIbFideicomisoDetPjsList()) {
                    detalle.setIdFideMovimientoPj(cabecera);
                    detalle.setNroLineaArchivo(i++);
                    UtilDTO utilDetalle = ibFideicomisoDetPjDAO.insertarIbFideicomisoDetPj(detalle, codigoCanal);
                    if (utilDetalle.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)
                            && utilDetalle.getRespuesta().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO_SP)) {
                        //ibFideicomisoDetPjDTO.getIbFideicomisoDetPjsList().get(i)
                        //        .setIdPagoDetalle(((IbFideicomisoDetPj)utilDetalle.getResulados().get("ibFideicomisoDetPj")).getIdPagoDetalle());
                    }
                }
                cabecera.setIbFideicomisoDetPjCollection(ibFideicomisoDetPjDTO.getIbFideicomisoDetPjsList());
                ibFideicomisoPjDTO.setIbFideicomisoPj(cabecera);
                resultado.put("IbFideicomisoPj", ibFideicomisoPjDTO);
                resultado.put("IbFideicomisoDetPjList", ibFideicomisoDetPjDTO);
                resultado.put(BDSUtil.ID_PAGO, cabecera.getId());
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

    private short obtenerAprobadores(IbCargaArchivoDTO ibCargaArchivoDto) {
        short caAprobadores = 0;
        try {
            IbAprobacionesServiciosPjDTO ibAprobacionesServiciosPjDTO
                    = ibAprobacionesServiciosPjFacade.consultarByServicioEmpresa(
                            ibCargaArchivoDto.getCdClienteAbanks(),
                            ServiciosAprobadoresEnum.FIDEICOMISO.getDescripcion(),
                            ibCargaArchivoDto.getIdCanal());

            if (ibAprobacionesServiciosPjDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                caAprobadores = ibAprobacionesServiciosPjDTO.getIbAprobacionServicioPj().getCantAprobadores().shortValue();
            }

        } catch (Exception e) {
            caAprobadores = 0;
        }
        return caAprobadores;
    }

    public String parametro(String codigo, String codigoCanal) {
        return ibParametrosFacade.consultaParametro(codigo, codigoCanal)
                .getIbParametro().getValor();
    }

    protected ValidatorCargaFideicomiso inicializar(IbCargaArchivoDTO ibCargaArchivoDto) throws Exception {
        ValidatorCargaFideicomiso validatorCargaFideicomiso = null;
        if (ibCargaArchivoDto.getFormatoArchivo().equals(FORMAT_TXT)) {
            if (ibCargaArchivoDto.getEstructura().equals(FideicomisoEnum.MOVIMIENTO.getDescripcion())) {
                validatorCargaFideicomiso = new ValidatorCargaFideicomisoFonz03Txt(ibCargaArchivoDto);
                validatorCargaFideicomiso.setMapErrorres(ibTextosFacade.listarLikeCodigo("pjw.fideicomisoCargaMasiva.error"));
            } else if (ibCargaArchivoDto.getEstructura().equals(FideicomisoEnum.APORTE.getDescripcion())) {
                validatorCargaFideicomiso = new ValidatorCargaFideicomisoFonz04Txt(ibCargaArchivoDto);
                validatorCargaFideicomiso.setMapErrorres(ibTextosFacade.listarLikeCodigo("pjw.fideicomisoCargaMasiva4.error"));
            }
        } else if (ibCargaArchivoDto.getFormatoArchivo().equals(FORMAT_XLS) || ibCargaArchivoDto.getFormatoArchivo().equals(FORMAT_XLSX)) {
            if (ibCargaArchivoDto.getEstructura().equals(FideicomisoEnum.MOVIMIENTO.getDescripcion())) {
                validatorCargaFideicomiso = new ValidatorCargaFideicomisoFonz03Excel(ibCargaArchivoDto);
                validatorCargaFideicomiso.setMapErrorres(ibTextosFacade.listarLikeCodigo("pjw.fideicomisoCargaMasiva.error"));
            } else if (ibCargaArchivoDto.getEstructura().equals(FideicomisoEnum.APORTE.getDescripcion())) {
                validatorCargaFideicomiso = new ValidatorCargaFideicomisoFonz04Excel(ibCargaArchivoDto);
                validatorCargaFideicomiso.setMapErrorres(ibTextosFacade.listarLikeCodigo("pjw.fideicomisoCargaMasiva4.error"));
            }
        }
        return validatorCargaFideicomiso;
    }

    private void reglaCodigoFideicomiso(IbCargaArchivoDTO ibCargaArchivoDto) {
        //validacion existencia de codigo fideicomiso
        if ((ibCargaArchivoDto.getCodigoFideicomiso() == null) || (ibCargaArchivoDto.getCodigoFideicomiso().equals(""))) {
            validatorCargaFideicomiso.addError("", 0, validatorCargaFideicomiso.getMapErrorres().get(ERR_KEY_CODIGO_FIDEICOMISO));
        }
    }

    private void reglaCantidadAprobadores(IbCargaArchivoDTO ibCargaArchivoDto) {
        //validacion de cantidad de aprobadores             
        ibFideicomisoPj.setCantidadAprobadoresServicio(obtenerAprobadores(ibCargaArchivoDto));
        if (ibFideicomisoPj.getCantidadAprobadoresServicio() == 0) {
            validatorCargaFideicomiso.addError("", 0, validatorCargaFideicomiso.getMapErrorres().get(ERR_KEY_ALMENOS_UN_APROBADOR));
        }
    }

    @Override
    public UtilDTO modificarEstatusIbFideicomisoPj(Long idFideicomisoPj, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, BigDecimal idEmpresa, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        try {
            IbFideicomisoPj ibFideicomisoPj = ibFideicomisoPjFacade.find(idFideicomisoPj);
            if (ibFideicomisoPj != null) {
                ibFideicomisoPj.setEstatus(ibEstatusPagosPjDTO.getIbEstatusPagosPj());
                respuesta = this.ibFideicomisoPjFacade.modificarPj(ibFideicomisoPj);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaNominaPj");
                }

                //SE MODIFICA EL DETALLE DE LOS FIDEICOMISOS A 
                ibFideicomisoDetPjFacade.modificarEstatusDetalle(idFideicomisoPj, ibEstatusPagosPjDTO.getIbEstatusPagosPj().getId());

                //SIEMPRE SE ENVIA EL CODIGO 1 AL SP
                String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
                //ajustar codigo de la plantilla a usar
                String codigoPlantilla = EstatusPlantillasEmail.NOMINA_PENDIENTE_AUTORIZAR.getDescripcion();
                UtilDTO utilUsuarios = this.ibEmpresasUsuariosDAO.consultarEmpresasUsuariosAprobadores(idEmpresa);
                if (utilUsuarios == null || !utilUsuarios.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR modificarEstatusIbFideicomisoPj");
                }
                List<IbEmpresasUsuariosPj> listaUsuarios = (List<IbEmpresasUsuariosPj>) utilUsuarios.getResulados().get("ListaAprobadores");

                //SI NO EXISTEN REPRESENTANTES LEGALES NO SE ENVIA CORREO
                //if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                /*
                    for (IbEmpresasUsuariosPj usuario : listaUsuarios) {
                        String parametrosCorreo = usuario.getIdEmpresa().getNombre() + "~" + idPago + "~" + cargaNomina.getMontoTotalAplicar() + "~" + usuario.getIdUsuarioPj().getNombre();
                        //ENVIAR EMAIL 
                        emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, usuario.getEmail(), parametrosCorreo, idCanal, codigoCanal);
                    }
                 */
                //}
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

    public void reglaNoExisteOtroArchivoConMismoNombre(IbCargaArchivoDTO ibCargaArchivoDTO) throws Exception {
        boolean existe = ibFideicomisoPjFacade.archivoCargaMasivaExiste(ibCargaArchivoDTO.getCdClienteAbanks().toString(), ibCargaArchivoDTO.getFileName());
        if (existe) {
            if (ibCargaArchivoDTO.getEstructura().equals(FideicomisoEnum.MOVIMIENTO.getDescripcion())) {
                validatorCargaFideicomiso.addError("", 0, ERR_KEY_MISMO_NOMBRE, (new Parametro()).add("$nombre", ibCargaArchivoDTO.getFileName()));
            } else if (ibCargaArchivoDTO.getEstructura().equals(FideicomisoEnum.APORTE.getDescripcion())) {
                validatorCargaFideicomiso.addError("", 0, ERR_KEY_MISMO_NOMBRE_FONZ04, (new Parametro()).add("$nombre", ibCargaArchivoDTO.getFileName()));
            }
        }
    }

    @Override
    public UtilDTO eliminarIbFideicomisoPjEstatusCeroPj(Long id, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        utilDTO.setRespuesta(new RespuestaDTO());
        try {
            IbFideicomisoPj ibFideicomisoPj = ibFideicomisoPjFacade.find(id);
            if (ibFideicomisoPj != null) {
                if (ibFideicomisoPj.getEstatus().getId() == BigDecimal.ZERO) {
                    respuesta = this.ibFideicomisoPjFacade.eliminarPj(ibFideicomisoPj);
                } else {
                    respuesta.setCodigo(CODIGO_ERROR_VALIDACIONES);
                    respuesta.setDescripcion("FideicomisoPj no esta en estatus en precargadO");
                }
            } else {
                respuesta.setCodigo(CODIGO_ERROR_VALIDACIONES);
                respuesta.setDescripcion("FideicomisoPj no encontrada");
            }
            utilDTO.setRespuesta(respuesta);
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN eliminarCargaMasivaEstatusCeroPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN eliminarCargaMasivaEstatusCeroPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;
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
    public IbFideicomisoPjDTO listarIbCargaFideicomisoAutorizarPj(BigDecimal cdClienteAbanks, Long idUsuarioAurorizado, Long idServicio, String idCanal, String codigoCanal) {
        IbFideicomisoPjDTO ibFideicomisoPjDTO = new IbFideicomisoPjDTO();
        try {
              ibFideicomisoPjDTO = this.ibFideicomisoPjFacade.listarIbCargaFideicomisoAutorizarPj(cdClienteAbanks, idUsuarioAurorizado,idServicio, USUARIO_CONEXION);
           

        } catch (Exception e) {

            logger.error( new StringBuilder("ERROR DAO en listarIbCargaFideicomisoAutorizarPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibFideicomisoPjDTO == null || ibFideicomisoPjDTO.getRespuestaDTO() == null) {
                ibFideicomisoPjDTO = new IbFideicomisoPjDTO();
                ibFideicomisoPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibFideicomisoPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibFideicomisoPjDTO;
    }
}
