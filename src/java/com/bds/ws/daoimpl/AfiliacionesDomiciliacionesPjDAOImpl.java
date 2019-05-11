/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.AfiliacionesDomiciliacionesPjDAO;
import com.bds.ws.dto.AfiliacionesCargaDTO;
import com.bds.ws.dto.AfiliacionesConsultaDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaPagosEspDetPjDTO;
import com.bds.ws.dto.IbErroresCargaPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusDomiciliacionesCOREEnum;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbEmpresasFacade;
import com.bds.ws.facade.IbTextosFacade;
import com.bds.ws.model.IbEmpresas;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import com.bds.ws.validator.ValidatorCargaAfiliaciones;
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
@Named("AfiliacionesDomiciliacionesPjDAO")
@Stateless
public class AfiliacionesDomiciliacionesPjDAOImpl extends DAOUtil implements AfiliacionesDomiciliacionesPjDAO {

    @EJB
    private IbEmpresasFacade ibEmpresasFacade;
    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(AfiliacionesDomiciliacionesPjDAOImpl.class.getName());

    private final IbErroresCargaPjDTO ibErroresCargaPjDTOLista = new IbErroresCargaPjDTO();

    private ValidatorCargaAfiliaciones validatorCargaAfiliaciones;

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @EJB
    private IbTextosFacade ibTextosFacade;

    public final static String ERR_KEY_CODIGO_ORDENANTE = "pjw.cargaMasDomiciliaciones.error.archivo.codigoOrdenante";
    public final static String ERR_KEY_CODIGO_ORDENANTE_TOYOTA = "pjw.cargaMasDomiciliaciones.error.archivo.codigoOrdToyota";
    public final static String ERR_KEY_CLAVE_ORDENANTE = "pjw.cargaMasDomiciliaciones.error.archivo.claveOrdenante";
    public final static String ERR_KEY_BANCO_DESTINO = "pjw.cargaMasDomiciliaciones.error.archivo.bancoDestino";
    public final static String ERR_KEY_TIPO_OPERACION = "pjw.cargaMasDomiciliaciones.error.archivo.tipoOperacion";
    public final static String ERR_KEY_TIPO_OPERACION_AFILIACION = "pjw.cargaMasDomiciliaciones.error.archivo.tipoAfiliacion";
    public final static String ERR_KEY_TIPO_PAGADOR = "pjw.cargaMasDomiciliaciones.error.archivo.tipoPagador";
    public final static String ERR_KEY_TIPO_PAGADOR_DETALLE = "pjw.cargaMasDomiciliaciones.error.archivo.tipoPagadorDetalle";
    public final static String ERR_KEY_IDENTIFICADOR_PAGADOR = "pjw.cargaMasDomiciliaciones.error.archivo.identificaPagador";
    public final static String ERR_KEY_LINEA_CUENTA_PAGADOR = "pjw.cargaMasDomiciliaciones.error.archivo.cuentaPagador";
    public final static String ERR_KEY_NOMBRE_PAGADOR = "pjw.cargaMasDomiciliaciones.error.archivo.nombrePagador";
    public final static String ERR_KEY_CONTRATO = "pjw.cargaMasDomiciliaciones.error.archivo.referenciaContrato";
    public final static String ERR_KEY_FORMATO_CUENTA_INVALIDO = "pjw.cargaMasDomiciliaciones.error.archivo.errorBancoDestino";
    public final static String ERR_KEY_REGISTRO_DUPLICADO = "pjw.cargaMasDomiciliaciones.error.archivo.registroDuplicado";

    public final static int NUMERO_LINEAS_MINIMO = 1;
    public final static String ERROR_BANCO_INCONSISTENTE = "102Inconsistencia en Banco Receptor";
    public final static String ERROR_REGISTRO_DUPLICADO = "Inserta no 157  Clave Ordenante Duplicada ";
    public final static String ERROR_REGISTRO_DUPLICADO_PASO_1 = "Error en Duplicidad de registro.  Clave Ordenante Duplicada. ";
    public final static String RIF_TOYOTA = "308554839";
    public final static String CODIGO_ORDENANTE_TOYOTA = "0001";

//   Valida si existe un minimo de registros a insertar 
    public boolean reglaDebeProcesarAlmenosUnDetalleParaPersistencia(IbCargaPagosEspDetPjDTO ibCargaPagosEspDetPjDTO) {
        return ibCargaPagosEspDetPjDTO.getIbCargaPagosEspDetPjsList().size() > 0;
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
    public AfiliacionesConsultaDTO consolidarAfiliacionesPj(String codigoOrdenante, String fechaDesde, String fechaHasta, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        AfiliacionesConsultaDTO afiliacionesConsultaDTO = new AfiliacionesConsultaDTO();
        List<AfiliacionesConsultaDTO> listAfiliacionesConsultaDTO = new ArrayList<>();
        try {
            listAfiliacionesConsultaDTO = this.consultaAfiliacion(codigoOrdenante, fechaDesde, fechaHasta, EstatusDomiciliacionesCOREEnum.R.getCodigo(), listAfiliacionesConsultaDTO, codigoCanal);
            listAfiliacionesConsultaDTO = this.consultaAfiliacion(codigoOrdenante, fechaDesde, fechaHasta, EstatusDomiciliacionesCOREEnum.K.getCodigo(), listAfiliacionesConsultaDTO, codigoCanal);
            listAfiliacionesConsultaDTO = this.consultaAfiliacion(codigoOrdenante, fechaDesde, fechaHasta, EstatusDomiciliacionesCOREEnum.O.getCodigo(), listAfiliacionesConsultaDTO, codigoCanal);
            listAfiliacionesConsultaDTO = this.consultaAfiliacion(codigoOrdenante, fechaDesde, fechaHasta, EstatusDomiciliacionesCOREEnum.P.getCodigo(), listAfiliacionesConsultaDTO, codigoCanal);
            listAfiliacionesConsultaDTO = this.consultaAfiliacion(codigoOrdenante, fechaDesde, fechaHasta, EstatusDomiciliacionesCOREEnum.C.getCodigo(), listAfiliacionesConsultaDTO, codigoCanal);
            afiliacionesConsultaDTO.setListAfiliacionesConsultaDTO(listAfiliacionesConsultaDTO);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarCtaAsociarBeneficiario: ")
                    .append("CTA-").append("")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            afiliacionesConsultaDTO.setRespuesta(respuesta);
        }
        return afiliacionesConsultaDTO;
    }

    public List<AfiliacionesConsultaDTO> consultaAfiliacion(String codigoOrdenante, String fechaDesde, String fechaHasta, String busqueda, List<AfiliacionesConsultaDTO> listAfiliacionesConsultaDTO, String codigoCanal) throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO();
        AfiliacionesConsultaDTO afiliacionesConsulta = new AfiliacionesConsultaDTO();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("PC_K_DOMICILIACION_PAGOS", "PC_P_CONSULTA_AFILIACION", 7, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, codigoOrdenante);
            statement.setString(2, fechaDesde);
            statement.setString(3, fechaHasta);
            statement.setString(4, busqueda);
            statement.registerOutParameter(5, OracleTypes.VARCHAR);
            statement.registerOutParameter(6, OracleTypes.CURSOR);
            statement.registerOutParameter(7, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();
            objResultSet = (ResultSet) statement.getObject(6);

            while (objResultSet.next()) {
                afiliacionesConsulta.setClaveOrdenante(new String(this.getCharSetOracle9(objResultSet, "CLAVE_ORDENANTE")));
                afiliacionesConsulta.setContrato(new String(this.getCharSetOracle9(objResultSet, "CONTRATO")));
                afiliacionesConsulta.setBancoDestino(this.objResultSet.getInt("BANCO_DESTINO"));
                afiliacionesConsulta.setTipoOperacion(new String(this.getCharSetOracle9(objResultSet, "TIPO_OPERACION")));
                afiliacionesConsulta.setIdentificacionPagador(new String(this.getCharSetOracle9(objResultSet, "IDENTIFICACION_PAGADOR")));
                afiliacionesConsulta.setNombrePagador(new String(this.getCharSetOracle9(objResultSet, "NOMBRE_PAGADOR")));
                afiliacionesConsulta.setNumeroMensajes(this.objResultSet.getInt("NUMERO_MENSAJE"));
                afiliacionesConsulta.setDescripcion(new String(this.getCharSetOracle9(objResultSet, "DESCRIPCION")));
                afiliacionesConsulta.setCodigoResultados(new String(this.getCharSetOracle9(objResultSet, "CODIGO_RESULTADO")));
                listAfiliacionesConsultaDTO.add(afiliacionesConsulta);
                afiliacionesConsulta = new AfiliacionesConsultaDTO();
            }
        } catch (Exception e) {
            throw e;
        }
        return listAfiliacionesConsultaDTO;
    }

    /**
     *
     * @param ibCargaArchivoDto
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @Override
    public UtilDTO procesarArchivoAfiliacionesPj(IbCargaArchivoDTO ibCargaArchivoDto, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        byte[] data = ibCargaArchivoDto.getDataFile();
        ibErroresCargaPjDTOLista.setIbErroresCargaPjDTO(new ArrayList<IbErroresCargaPjDTO>());
        IbErroresCargaPjDTO ibErroresCargaPjDTO = null;
        String line = "";
        int nroLinea = 0;
        AfiliacionesCargaDTO afiliacionesCargaDTO;
        List<AfiliacionesCargaDTO> listaAfiliaciones = new ArrayList();
        Map param = new HashMap();
        try {
            validatorCargaAfiliaciones = new ValidatorCargaAfiliaciones(ibCargaArchivoDto);
            validatorCargaAfiliaciones.setMapErrorres(ibTextosFacade.listarLikeCodigo("pjw.cargaMasDomiciliaciones.error"));

            IbEmpresas ibEmpresas = ibEmpresasFacade.find(ibCargaArchivoDto.getIdEmpresa());
            String identificacionEmpresa = ibEmpresas.getNroRif();
            param.put("rifEmpresa", identificacionEmpresa);

            //Temporal pruebas
            //data = validatorCargaAfiliaciones.readfile("C:/Domiciliaciones_archivo_pruebas/" + ibCargaArchivoDto.getFileName());
            if (validatorCargaAfiliaciones.countErrors() == 0) {
                //ciclo 
                Map<String, String> mapErrorres = listarErroresAfiliacionesCargaMasiva();
                ValidatorCargaAfiliaciones validatorCargaAfiliaciones = new ValidatorCargaAfiliaciones(ibCargaArchivoDto);
                validatorCargaAfiliaciones.clearErrors();
                validatorCargaAfiliaciones.setMapErrorres(mapErrorres);

                BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));

                if (validatorCargaAfiliaciones.countErrors() == 0) {

                    for (nroLinea = 0; (line = br.readLine()) != null;) {
                        nroLinea++;
                        try {
                            param.put("linea", line);
                            param.put("nroLinea", nroLinea);
                            afiliacionesCargaDTO = (AfiliacionesCargaDTO) validatorCargaAfiliaciones.leerDetalle(param);

                            if (afiliacionesCargaDTO != null) {

                                utilDTO = validaAfiliacion(afiliacionesCargaDTO.getLineaTxt(),
                                        afiliacionesCargaDTO.getNombreArchivo(),
                                        afiliacionesCargaDTO.getNumeroLinea(),
                                        afiliacionesCargaDTO.getCodigoOrdenante());
                                if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)
                                        && utilDTO.getRespuesta().getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                                    listaAfiliaciones.add(afiliacionesCargaDTO);
                                } else {
                                    ibErroresCargaPjDTO = new IbErroresCargaPjDTO();
                                    ibErroresCargaPjDTO.setLinea(afiliacionesCargaDTO.getLineaTxt());
                                    ibErroresCargaPjDTO.setNumeroLinea(afiliacionesCargaDTO.getNumeroLinea());
                                    ibErroresCargaPjDTO.setError(new ArrayList<>());

                                    if (utilDTO.getRespuesta().getDescripcionSP().equals(ERROR_BANCO_INCONSISTENTE)) {
                                        ibErroresCargaPjDTO.getError().add(mapErrorres.get(ERR_KEY_FORMATO_CUENTA_INVALIDO));
                                    } else if (utilDTO.getRespuesta().getDescripcionSP().equals(ERROR_REGISTRO_DUPLICADO_PASO_1)) {
                                        ibErroresCargaPjDTO.getError().add(mapErrorres.get(ERR_KEY_REGISTRO_DUPLICADO));
                                    }
                                    validatorCargaAfiliaciones.getIbErroresCargaPjDTOLista().add(ibErroresCargaPjDTO);
                                }
                            }
                        } catch (Exception e) {
                            validatorCargaAfiliaciones.addError(line, nroLinea, e.getMessage());
                        }
                    }
                }
                afiliacionesCargaDTO = new AfiliacionesCargaDTO();
                afiliacionesCargaDTO.setListAfiliacionesCargaDTO(listaAfiliaciones);

                //Si hay un error el insert retorna nulls en la respuesta y el resultado 
                if (utilDTO.getRespuesta() == null) {
                    utilDTO.setRespuesta(new RespuestaDTO());
                }
                if (validatorCargaAfiliaciones.countErrors() == 0) {
                    utilDTO.getRespuesta().setCodigoSP(CODIGO_RESPUESTA_EXITOSO);
                }

                utilDTO.setResulados(new HashMap());

                //Nos interesa enviar solo los errores 
                utilDTO.getResulados().put(LISTA_ERRORES_CARGA_ARCHIVO, validatorCargaAfiliaciones.getIbErroresCargaPjDTO());

                //Nos interesa enviar las lineas exitosas 
                utilDTO.getResulados().put(LISTA_EXITOSO_CARGA_ARCHIVO, afiliacionesCargaDTO);
            }

        } catch (IOException ioex) {
            logger.error( new StringBuilder("ERROR IO : ")
                    .append("-CH- ").append(ibCargaArchivoDto.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ioex.getCause()).toString(), ioex);
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        } catch (Exception ex) {
            logger.error( new StringBuilder("ERROR DAO EN procesarArchivoAfiliacionesPj: ")
                    .append("-CH- ").append(ibCargaArchivoDto.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ex.getMessage()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        }
        return utilDTO;
    }

    @Override
    public UtilDTO insertarAfiliacionesPjCore(AfiliacionesCargaDTO afiliacionesCargaDTO, String idCana, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        List<IbErroresCargaPjDTO> listaErroresCargaInsercion = new ArrayList();
        IbErroresCargaPjDTO ibErroresCargaPjDTO = null;
        try {
            Map<String, String> mapErrorres = listarErroresAfiliacionesCargaMasiva();
            for (AfiliacionesCargaDTO afiliaciones : afiliacionesCargaDTO.getListAfiliacionesCargaDTO()) {
                utilDTO = insertarAfiliacion(afiliaciones.getLineaTxt(),
                        afiliaciones.getNombreArchivo(),
                        afiliaciones.getNumeroLinea(),
                        afiliaciones.getCodigoOrdenante());
                if (!utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    ibErroresCargaPjDTO = new IbErroresCargaPjDTO();
                    ibErroresCargaPjDTO.setLinea(afiliaciones.getLineaTxt());
                    ibErroresCargaPjDTO.setNumeroLinea(afiliaciones.getNumeroLinea());
                    if (utilDTO.getRespuesta().getDescripcionSP().equals(ERROR_REGISTRO_DUPLICADO)) {
                        ibErroresCargaPjDTO.getError().add(mapErrorres.get(ERR_KEY_REGISTRO_DUPLICADO));
                    }
                    listaErroresCargaInsercion.add(ibErroresCargaPjDTO);
                }
            }
            ibErroresCargaPjDTO = new IbErroresCargaPjDTO();

            ibErroresCargaPjDTO.setIbErroresCargaPjDTO(listaErroresCargaInsercion);

            utilDTO.setResulados(new HashMap<String, IbErroresCargaPjDTO>());

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

    public UtilDTO insertarAfiliacion(String lineaTxt, String nombreArchivos, Integer numeroLinea, String codigoOrdenante) {
        RespuestaDTO respuesta = new RespuestaDTO();
        //Map resultados = new HashMap();
        UtilDTO utilDTO = new UtilDTO();
        String iCodExtCanal = "";
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("PC_K_DOMICILIACION_PAGOS", "PU_P_GRABA_TXT_ORDENANTE", 6, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, lineaTxt);
            statement.setString(2, nombreArchivos);
            statement.setInt(3, numeroLinea);
            statement.registerOutParameter(4, OracleTypes.INTEGER);
            statement.registerOutParameter(5, OracleTypes.VARCHAR);
            statement.setString(6, codigoOrdenante);

            this.ejecuto = statement.execute();

            String respuestaSP = statement.getString(5);

            if (respuestaSP == null) {
                utilDTO.setRespuesta(respuesta);
            } else {
                logger.error( new StringBuilder("ERROR DAO EN Insertar Afiliacion: ")
                        .append("-CH-").append(iCodExtCanal)
                        .append("-DT-").append(new Date())
                        .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                        .append("-EXCP-").append(respuestaSP).toString());
                respuesta.setCodigo(CODIGO_RESPUESTA_FALLIDO_SP);
                respuesta.setDescripcionSP(respuestaSP);
            }
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN Insertar Afiliacion: ")
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

    public Map<String, String> listarErroresAfiliacionesCargaMasiva() throws Exception {

        Map<String, String> mapErroresAfiliacionesCargaMasiva = new HashMap();

        try {

            StringBuilder consulta = new StringBuilder();

            consulta.append("SELECT  CODIGO, MENSAJE_USUARIO ");
            consulta.append("  FROM ib_textos ");
            consulta.append(" WHERE CODIGO LIKE 'pjw.cargaMasDomiciliaciones.error.archivo%'");

            Collection listaCargaAfiliacion = em.createNativeQuery(consulta.toString())
                    .getResultList();

            Iterator interador = listaCargaAfiliacion.iterator();
            Object[] registro;
            while (interador.hasNext()) {
                registro = (Object[]) interador.next();
                mapErroresAfiliacionesCargaMasiva.put((String) registro[0], (String) registro[1]);
            }

        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return mapErroresAfiliacionesCargaMasiva;
    }

    public UtilDTO validaAfiliacion(String lineaTxt, String nombreArchivos, Integer numeroLinea, String codigoOrdenante) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        String iCodExtCanal = "";
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_VALIDA_DOMICILIACION", "IB_P_VALIDA_ORDENANTE", 6, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, lineaTxt);
            statement.setString(2, nombreArchivos);
            statement.setInt(3, numeroLinea);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            statement.registerOutParameter(5, OracleTypes.VARCHAR);
            statement.setString(6, codigoOrdenante);

            this.ejecuto = statement.execute();

            String respuestaSP = statement.getString(4);

            if (respuestaSP.equals("V")) {
                respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
                respuesta.setDescripcion(DESCRIPCION_RESPUESTA_EXITOSO);
            } else if (respuestaSP.equals("F")) {
                String descripcionErrorSP = statement.getString(5);
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
