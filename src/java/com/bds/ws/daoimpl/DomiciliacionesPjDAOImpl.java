/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.DomiciliacionesPjDAO;
import com.bds.ws.dto.DomiciliacionesCargaDTO;
import com.bds.ws.dto.DomiciliacionesConsultaDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbErroresCargaPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbEmpresasFacade;
import com.bds.ws.facade.IbTextosFacade;
import com.bds.ws.model.IbEmpresas;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import com.bds.ws.validator.ValidatorCargaDomiciliaciones;
import static com.bds.ws.validator.ValidatorCargaDomiciliaciones.ERR_KEY_FORMATO_CUENTA_INVALIDO;
import static com.bds.ws.validator.ValidatorCargaDomiciliaciones.ERR_KEY_REGISTRO_DUPLICADO;
import com.bds.ws.validator.ValidatorCargaDomiciliacionesLong179Txt;
import com.bds.ws.validator.ValidatorCargaDomiciliacionesLong399Txt;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 *
 * @author jose.perez
 */
@Named("DomiciliacionesPjDAO")
@Stateless
public class DomiciliacionesPjDAOImpl extends DAOUtil implements DomiciliacionesPjDAO {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @EJB
    private IbTextosFacade ibTextosFacade;

    @EJB
    private IbEmpresasFacade ibEmpresasFacade;
    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(DomiciliacionesPjDAOImpl.class.getName());

    private ValidatorCargaDomiciliaciones validarCargaDomiciliaciones;

    public final static String ERR_KEY_FORMATO_INVALIDO = "pjw.cargaMasDomiciliaciones.error.archivo.errorFormato";
    public final static int NUMERO_LINEAS_MINIMO = 1;
    public final static String FORMAT_CLIENTE_NORMAL = "179";
    public final static String FORMAT_TSV = "399";
    public final static String ERROR_BANCO_INCONSISTENTE = "Error en Validar Banco Destino  102Inconsistencia en Banco Receptor";
    public final static String ERROR_REGISTRO_DUPLICADO = "Registro Duplicado en MG_DOMICILIACIONES_ENVIADAS";
    public final static String ERROR_REGISTRO_DUPLICADO_PASO_1 = "Registro Duplicado en MG_DOMICILIACIONES_ENVIADAS";

    @Override
    public UtilDTO procesarArchivoDomiciliacionesPj(IbCargaArchivoDTO ibCargaArchivoDto, String idCanal, String codigoCanal) {

        UtilDTO utilDTO = new UtilDTO();

        byte[] data = ibCargaArchivoDto.getDataFile();

        String line = "";
        int nroLinea = 0;
        String nombreArchivo = "";
        DomiciliacionesCargaDTO domiciliacionesCargaDTO;
        List<DomiciliacionesCargaDTO> listaDomiciliaciones = new ArrayList();
        IbErroresCargaPjDTO ibErroresCargaPjDTO = null;
        Map param = new HashMap();
        try {
            if (ibCargaArchivoDto.getEstructura().equals(FORMAT_CLIENTE_NORMAL)) {
                validarCargaDomiciliaciones = new ValidatorCargaDomiciliacionesLong179Txt(ibCargaArchivoDto);
                validarCargaDomiciliaciones.setMapErrorres(ibTextosFacade.listarLikeCodigo("pjw.cargaMasDomiciliaciones.error"));
            } else if (ibCargaArchivoDto.getEstructura().equals(FORMAT_TSV)) {
                validarCargaDomiciliaciones = new ValidatorCargaDomiciliacionesLong399Txt(ibCargaArchivoDto);
                validarCargaDomiciliaciones.setMapErrorres(ibTextosFacade.listarLikeCodigo("pjw.cargaMasDomiciliaciones.error"));
            }

            IbEmpresas ibEmpresas = ibEmpresasFacade.find(ibCargaArchivoDto.getIdEmpresa());
            String identificacionEmpresa = ibEmpresas.getNroRif();
            param.put("rifEmpresa", identificacionEmpresa);

            //Temporal pruebas
            //data = validarCargaDomiciliaciones.readfile("C:/Domiciliaciones_archivo_pruebas/" + ibCargaArchivoDto.getFileName());
            if (validarCargaDomiciliaciones.countErrors() == 0 && ibCargaArchivoDto.getEstructura().equals(FORMAT_CLIENTE_NORMAL)) {
                //ciclo 
                Map<String, String> mapErrorres = listarErroresDomiciliacionesCargaMasiva();
                ValidatorCargaDomiciliacionesLong179Txt validatorCargaDomiciliacionesLong179Txt = new ValidatorCargaDomiciliacionesLong179Txt(ibCargaArchivoDto);
                validatorCargaDomiciliacionesLong179Txt.clearErrors();
                validatorCargaDomiciliacionesLong179Txt.setMapErrorres(mapErrorres);

                nombreArchivo = ibCargaArchivoDto.getFileName();

                BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));

                if (validatorCargaDomiciliacionesLong179Txt.countErrors() == 0) {

                    for (nroLinea = 0; (line = br.readLine()) != null;) {
                        nroLinea++;
                        try {
                            param.put("linea", line);
                            param.put("nroLinea", nroLinea);
                            param.put("fileName", nombreArchivo);
                            domiciliacionesCargaDTO = (DomiciliacionesCargaDTO) validatorCargaDomiciliacionesLong179Txt.leerDetalle(param);

                            if (domiciliacionesCargaDTO != null) {

                                utilDTO = validaDomiciliacion(domiciliacionesCargaDTO.getLineaTxt(),
                                        domiciliacionesCargaDTO.getNombreArchivo(),
                                        domiciliacionesCargaDTO.getNumeroLinea(),
                                        domiciliacionesCargaDTO.getTipoTxt(),
                                        domiciliacionesCargaDTO.getCodigoOrdenante());

                                if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)
                                        && utilDTO.getRespuesta().getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                                    listaDomiciliaciones.add(domiciliacionesCargaDTO);
                                } else {
                                    String mensajeError = "";
                                    if (utilDTO.getRespuesta().getDescripcionSP().equals(ERROR_BANCO_INCONSISTENTE)) {
                                        mensajeError = mapErrorres.get(ERR_KEY_FORMATO_CUENTA_INVALIDO);
                                    } else if (utilDTO.getRespuesta().getDescripcionSP().equals(ERROR_REGISTRO_DUPLICADO_PASO_1)) {
                                        mensajeError = mapErrorres.get(ERR_KEY_REGISTRO_DUPLICADO);
                                    }
                                    validatorCargaDomiciliacionesLong179Txt.addError(line, nroLinea, mensajeError);
                                }
                            }
                        } catch (Exception e) {
                            //validatorCargaDomiciliacionesLong179Txt.getIbErroresCargaPjDTOLista().add(ibErroresCargaPjDTO);
                        }
                    }
                }

                //Si hay un error el insert retorna nulls en la respuesta y el resultado 
                if (utilDTO.getRespuesta() == null) {
                    utilDTO.setRespuesta(new RespuestaDTO());
                }
                if (validatorCargaDomiciliacionesLong179Txt.countErrors() == 0) {
                    utilDTO.getRespuesta().setCodigoSP(CODIGO_RESPUESTA_EXITOSO);
                }

                utilDTO.setResulados(new HashMap());

                domiciliacionesCargaDTO = new DomiciliacionesCargaDTO();
                domiciliacionesCargaDTO.setListDomiciliacionesCargaDTO(listaDomiciliaciones);

                //Nos interesa enviar solo los errores 
                utilDTO.getResulados().put(LISTA_ERRORES_CARGA_ARCHIVO, validatorCargaDomiciliacionesLong179Txt.getIbErroresCargaPjDTO());

                //Nos interesa enviar las lineas exitosas 
                utilDTO.getResulados().put(LISTA_EXITOSO_CARGA_ARCHIVO, domiciliacionesCargaDTO);

                utilDTO.setRespuesta(new RespuestaDTO());
            } else if (validarCargaDomiciliaciones.countErrors() == 0 && ibCargaArchivoDto.getEstructura().equals(FORMAT_TSV)) {

                Map<String, String> mapErrorres = listarErroresDomiciliacionesCargaMasiva();
                ValidatorCargaDomiciliacionesLong399Txt validatorCargaDomiciliacionesLong399Txt = new ValidatorCargaDomiciliacionesLong399Txt(ibCargaArchivoDto);
                validatorCargaDomiciliacionesLong399Txt.clearErrors();
                validatorCargaDomiciliacionesLong399Txt.setMapErrorres(mapErrorres);

                BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));

                if (validatorCargaDomiciliacionesLong399Txt.countErrors() == 0) {

                    for (nroLinea = 0; (line = br.readLine()) != null;) {
                        nroLinea++;
                        try {
                            param.put("linea", line);
                            param.put("nroLinea", nroLinea);
                            domiciliacionesCargaDTO = (DomiciliacionesCargaDTO) validatorCargaDomiciliacionesLong399Txt.leerDetalle(param);

                            if (domiciliacionesCargaDTO != null) {

                                utilDTO = validaDomiciliacion(domiciliacionesCargaDTO.getLineaTxt(),
                                        domiciliacionesCargaDTO.getNombreArchivo(),
                                        domiciliacionesCargaDTO.getNumeroLinea(),
                                        domiciliacionesCargaDTO.getTipoTxt(),
                                        domiciliacionesCargaDTO.getCodigoOrdenante());

                                if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)
                                        && utilDTO.getRespuesta().getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                                    listaDomiciliaciones.add(domiciliacionesCargaDTO);
                                } else {
                                    String mensajeError = "";
                                    if (utilDTO.getRespuesta().getDescripcionSP().equals(ERROR_BANCO_INCONSISTENTE)) {
                                        mensajeError = mapErrorres.get(ERR_KEY_FORMATO_CUENTA_INVALIDO);
                                    } else if (utilDTO.getRespuesta().getDescripcionSP().equals(ERROR_REGISTRO_DUPLICADO_PASO_1)) {
                                        mensajeError = mapErrorres.get(ERR_KEY_REGISTRO_DUPLICADO);
                                    }
                                    validatorCargaDomiciliacionesLong399Txt.addError(line, nroLinea, mensajeError);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }

                domiciliacionesCargaDTO = new DomiciliacionesCargaDTO();
                domiciliacionesCargaDTO.setListDomiciliacionesCargaDTO(listaDomiciliaciones);

                //Si hay un error el insert retorna nulls en la respuesta y el resultado 
                if (utilDTO.getRespuesta() == null) {
                    utilDTO.setRespuesta(new RespuestaDTO());
                }

                utilDTO.setResulados(new HashMap());

                //Nos interesa enviar los errores 
                utilDTO.getResulados().put(LISTA_ERRORES_CARGA_ARCHIVO, validatorCargaDomiciliacionesLong399Txt.getIbErroresCargaPjDTO());

                //Nos interesa enviar las lineas exitosas
                utilDTO.getResulados().put(LISTA_EXITOSO_CARGA_ARCHIVO, domiciliacionesCargaDTO);

                utilDTO.setRespuesta(new RespuestaDTO());

            }

        } catch (IOException ioex) {
            logger.error( new StringBuilder("ERROR IO : ")
                    .append("-CH- ").append(ibCargaArchivoDto.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ioex.getCause()).toString(), ioex);
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        } catch (Exception ex) {
            logger.error( new StringBuilder("ERROR DAO EN procesarArchivoDomiciliacionesPj: ")
                    .append("-CH- ").append(ibCargaArchivoDto.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ex.getMessage()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        }
        return utilDTO;
    }

    public UtilDTO insertarDomiciliacion(String lineaTxt, String nombreArchivos, Integer numeroLinea, String tipoTxt, String codigoOrdenante) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        String iCodExtCanal = "";
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("PC_K_DOMICILIACION_PAGOS", "PC_P_GRABA_TXT_DOMICILIACION", 6, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, lineaTxt);
            statement.setString(2, nombreArchivos);
            statement.setInt(3, numeroLinea);
            statement.setString(4, tipoTxt);
            statement.registerOutParameter(5, OracleTypes.VARCHAR);
            statement.setString(6, codigoOrdenante);

            this.ejecuto = statement.execute();

            String respuestaSP = statement.getString(5);

            if (respuestaSP == null) {
                utilDTO.setRespuesta(respuesta);
            } else {
                logger.error( new StringBuilder("ERROR DAO EN Insertar Domiciliación: ")
                        .append("-CH-").append(iCodExtCanal)
                        .append("-DT-").append(new Date())
                        .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                        .append("-EXCP-").append(respuestaSP).toString());
                respuesta.setCodigo(CODIGO_RESPUESTA_FALLIDO_SP);
                respuesta.setDescripcionSP(respuestaSP);
            }
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN Insertar Domiciliación: ")
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            utilDTO.setRespuesta(respuesta);
        } finally {
            this.cerrarConexion(iCodExtCanal);
        }
        return utilDTO;
    }

    public Map<String, String> listarErroresDomiciliacionesCargaMasiva() throws Exception {

        Map<String, String> mapErroresDomiciliacionCargaMasiva = new HashMap();

        try {

            StringBuilder consulta = new StringBuilder();

            consulta.append("SELECT  CODIGO, MENSAJE_USUARIO ");
            consulta.append("  FROM ib_textos ");
            consulta.append(" WHERE CODIGO LIKE 'pjw.cargaMasDomiciliaciones.error.archivo%'");

            Collection listaCargaDomiciliacion = em.createNativeQuery(consulta.toString())
                    .getResultList();

            Iterator interador = listaCargaDomiciliacion.iterator();
            Object[] registro;
            while (interador.hasNext()) {
                registro = (Object[]) interador.next();
                mapErroresDomiciliacionCargaMasiva.put((String) registro[0], (String) registro[1]);
            }

        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return mapErroresDomiciliacionCargaMasiva;
    }

    /**
     *
     * @param codigoOrdenante
     * @param fechaDesde
     * @param fechaHasta
     * @param codigoCanal
     * @return
     */
    @Override
    public DomiciliacionesConsultaDTO consultarDomiciliacion(String codigoOrdenante, String fechaDesde, String fechaHasta, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        DomiciliacionesConsultaDTO domiciliacionesConsultaDTO = new DomiciliacionesConsultaDTO();
        List<DomiciliacionesConsultaDTO> listDomiciliacionesConsultaDTO = new ArrayList<>();
        try {
            listDomiciliacionesConsultaDTO = this.ConsultaConsolidada_A(codigoOrdenante, fechaDesde, fechaHasta, listDomiciliacionesConsultaDTO, codigoCanal);
            domiciliacionesConsultaDTO.setListDomiciliacionesConsultaDTO(listDomiciliacionesConsultaDTO);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarCtaAsociarBeneficiario: ")
                    .append("CTA-").append("")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            domiciliacionesConsultaDTO.setRespuesta(respuesta);
        }
        return domiciliacionesConsultaDTO;
    }

    public List<DomiciliacionesConsultaDTO> ConsultaConsolidada_A(String codigoOrdenante, String fechaDesde, String fechaHasta, List<DomiciliacionesConsultaDTO> listDomiciliacionesConsultaDTO, String codigoCanal) throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO();
        DomiciliacionesConsultaDTO domiciliacionesConsultaDTO = new DomiciliacionesConsultaDTO();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("PC_K_DOMICILIACION_PAGOS", "PC_P_CONSULTA_CONSOLIDADA_A", 6, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, codigoOrdenante);
            statement.setString(2, fechaDesde);
            statement.setString(3, fechaHasta);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            statement.registerOutParameter(5, OracleTypes.CURSOR);
            statement.registerOutParameter(6, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();
            objResultSet = (ResultSet) statement.getObject(5);

            while (objResultSet.next()) {
                domiciliacionesConsultaDTO.setFechaValida(this.objResultSet.getDate("Fecha_Valida"));
                domiciliacionesConsultaDTO.setCantidadCargado(this.objResultSet.getInt("Cant_Cargado"));
                domiciliacionesConsultaDTO.setCantidadRechazado(this.objResultSet.getInt("Cant_Rechazo"));
                domiciliacionesConsultaDTO.setCantidadPresentado(this.objResultSet.getInt("Cant_Presentado"));
                domiciliacionesConsultaDTO.setCantidadTotal(this.objResultSet.getInt("cant_T"));
                listDomiciliacionesConsultaDTO.add(domiciliacionesConsultaDTO);
                domiciliacionesConsultaDTO = new DomiciliacionesConsultaDTO();
            }
        } catch (Exception e) {
            throw e;
        }
        return listDomiciliacionesConsultaDTO;
    }

    @Override
    public UtilDTO insertarDomiciliacionPjCore(DomiciliacionesCargaDTO domiciliacionesCargaDTO, String idCanal, String codigoCanal) {

        UtilDTO utilDTO = new UtilDTO();
        List<IbErroresCargaPjDTO> listaErroresCargaInsercion = new ArrayList();
        IbErroresCargaPjDTO ibErroresCargaPjDTO = null;
        try {
            Map<String, String> mapErrorres = listarErroresDomiciliacionesCargaMasiva();
            for (DomiciliacionesCargaDTO domiciliaciones : domiciliacionesCargaDTO.getListDomiciliacionesCargaDTO()) {
                utilDTO = insertarDomiciliacion(domiciliaciones.getLineaTxt(),
                        domiciliaciones.getNombreArchivo(),
                        domiciliaciones.getNumeroLinea(),
                        domiciliaciones.getTipoTxt(),
                        domiciliaciones.getCodigoOrdenante());
                if (!utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    ibErroresCargaPjDTO = new IbErroresCargaPjDTO();
                    ibErroresCargaPjDTO.setLinea(domiciliaciones.getLineaTxt());
                    ibErroresCargaPjDTO.setNumeroLinea(domiciliaciones.getNumeroLinea());
                    ibErroresCargaPjDTO.setError(new ArrayList<>());
                    if (utilDTO.getRespuesta().getDescripcionSP().equals(ERROR_REGISTRO_DUPLICADO)) {
                        ibErroresCargaPjDTO.getError().add(mapErrorres.get(ERR_KEY_REGISTRO_DUPLICADO));
                    }
                    listaErroresCargaInsercion.add(ibErroresCargaPjDTO);
                }
            }
            ibErroresCargaPjDTO = new IbErroresCargaPjDTO();

            ibErroresCargaPjDTO.setIbErroresCargaPjDTO(listaErroresCargaInsercion);

            utilDTO.setResulados(new HashMap<String, IbErroresCargaPjDTO>());
            //Nos interesa enviar la lista de archivos no procesados en el SP
            if (!listaErroresCargaInsercion.isEmpty()) {
                utilDTO.getResulados().put(LISTA_ERRORES_CARGA_ARCHIVO_SP, ibErroresCargaPjDTO);
            }
            //Si hay un error el insert retorna nulls en la respuesta y el resultado 
            if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                utilDTO.setRespuesta(new RespuestaDTO());
                utilDTO.getRespuesta().setCodigoSP(CODIGO_RESPUESTA_EXITOSO);
            } else {
                utilDTO.getRespuesta().setCodigoSP(DESCRIPCION_RESPUESTA_FALLIDA);
            }
        } catch (Exception ex) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaPagosEspPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ex.getMessage()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        }
        return utilDTO;
    }

    public UtilDTO validaDomiciliacion(String lineaTxt, String nombreArchivos, Integer numeroLinea, String tipoTxt, String codigoOrdenante) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        String iCodExtCanal = "";
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_VALIDA_DOMICILIACION", "IB_P_VALIDA_DOMICILIACION", 7, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, lineaTxt);
            statement.setString(2, nombreArchivos);
            statement.setInt(3, numeroLinea);
            statement.setString(4, tipoTxt);
            statement.registerOutParameter(5, OracleTypes.VARCHAR);
            statement.registerOutParameter(6, OracleTypes.VARCHAR);
            statement.setString(7, codigoOrdenante);

            this.ejecuto = statement.execute();

            String respuestaSP = statement.getString(5);

            if (respuestaSP.equals("V")) {
                respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
                respuesta.setDescripcion(DESCRIPCION_RESPUESTA_EXITOSO);
            } else if (respuestaSP.equals("F")) {
                String descripcionErrorSP = statement.getString(6);
                respuesta.setCodigo(CODIGO_RESPUESTA_FALLIDO_SP);
                respuesta.setDescripcion(DESCRIPCION_RESPUESTA_FALLIDA);
                respuesta.setDescripcionSP(descripcionErrorSP);

            }
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN Consultar Domiciliación: ")
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            utilDTO.setRespuesta(respuesta);
        } finally {
            this.cerrarConexion(iCodExtCanal);
        }
        return utilDTO;
    }
}
