/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.CuentaDAO;
import com.bds.ws.dao.EMailDAO;
import com.bds.ws.dao.IbBeneficiariosPjDAO;
import com.bds.ws.dao.IbCargaBeneficiariosPjDAO;
import com.bds.ws.dao.IbEmpresasUsuariosDAO;
import com.bds.ws.dto.IbAprobacionesServiciosPjDTO;
import com.bds.ws.dto.IbBeneficiariosPjDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbCargaBeneficiarioManualDTO;
import com.bds.ws.dto.IbCargaBeneficiarioManualModificarDTO;
import com.bds.ws.dto.IbCargaBeneficiariosPjDTO;
import com.bds.ws.dto.IbCtasXBeneficiariosPjDTO;
import com.bds.ws.dto.IbErroresCargaPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.CuentaDelSurEnum;
import com.bds.ws.enumerated.CuentaPrincipalEnum;
import com.bds.ws.enumerated.CuentaPropiaEnum;
import com.bds.ws.enumerated.EstatusBeneficiarioEnum;
import com.bds.ws.enumerated.EstatusCuentaEnum;
import com.bds.ws.enumerated.EstatusPagosEnum;
import com.bds.ws.enumerated.EstatusPlantillasEmail;
import com.bds.ws.enumerated.FormaDePagoEnum;
import com.bds.ws.enumerated.ProductoEnum;
import com.bds.ws.enumerated.ServiciosAprobadoresEnum;
import com.bds.ws.exception.IbErroresCargaPjException;
import com.bds.ws.facade.IbAprobacionesServiciosPjFacade;
import com.bds.ws.facade.IbBeneficiariosPjFacade;
import com.bds.ws.facade.IbCargaBeneficiariosPjFacade;
import com.bds.ws.facade.IbCtasXBeneficiariosPjFacade;
import com.bds.ws.facade.IbEmpresasFacade;
import com.bds.ws.facade.IbParametrosFacade;
import com.bds.ws.model.IbBeneficiariosPj;
import com.bds.ws.model.IbCargaBeneficiariosPj;
import com.bds.ws.model.IbCtasXBeneficiariosPj;
import com.bds.ws.model.IbEmpresas;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.model.IbUsuariosPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_ERROR_VALIDACIONES;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.validator.ValidatorCargaBeneficiarios;
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
@Named("IbCargaBeneficiariosPjDAO")
@Stateless
public class IbCargaBeneficiariosPjDAOImpl extends BDSUtil implements IbCargaBeneficiariosPjDAO {

    private static final Logger logger = Logger.getLogger(IbCargaBeneficiariosPjDAOImpl.class.getName());

    @EJB
    private IbCargaBeneficiariosPjFacade ibCargaBeneficiariosPjFacade;

    @EJB
    private IbParametrosFacade ibParametrosFacade;

    @EJB
    private IbCtasXBeneficiariosPjFacade ibCtasXBeneficiariosPjFacade;

    @EJB
    private IbBeneficiariosPjFacade ibBeneficiariosPjFacade;

    @EJB
    private IbAprobacionesServiciosPjFacade ibAprobacionesServiciosPjFacade;

    @EJB
    private IbEmpresasFacade ibEmpresasFacade;

    @EJB
    private IbBeneficiariosPjDAO ibBeneficiariosPjDAO;

    @EJB
    private EMailDAO emailDAO;

    @EJB
    private IbEmpresasUsuariosDAO ibEmpresasUsuariosDAO;

    @EJB
    public CuentaDAO cuentaDAO;

    private final IbErroresCargaPjDTO ibErroresCargaPjDTOLista = new IbErroresCargaPjDTO();

    public final static String ERR_KEY_ALMENOS_UN_DETALLE_CARGADO_PARA_PERSISTENCIA = "pjw.cargaMasBeneficiarios.error.archivo.minDetPersistencia";
    public final static String ERR_KEY_MISMO_NOMBRE = "pjw.cargaMasBeneficiarios.error.archivo.mismoNombre";
    public final static String ERR_KEY_ID_BENEFICIARIO_NULL = "pjw.cargaMasBeneficiarios.error.archivo.idBeneficiarioNull";
    public final static String ERR_KEY_MINIMO_UNA_LINEA = "pjw.cargaMasBeneficiarios.error.archivo.1Lineas";
    public final static String ERR_KEY_TOTAL_REGISTROS = "pjw.cargaMasBeneficiarios.error.archivo.totalRegistros";
    public final static String ERR_KEY_CUENTA_SOLO_NUMERO = "pjw.cargaMasBeneficiarios.error.archivo.cuentaSoloNumeros";
    public final static String ERR_KEY_ALFANUMERICO = "pjw.cargaMasBeneficiarios.error.archivo.alfNum";
    public final static String ERR_KEY_CORREO = "pjw.cargaMasBeneficiarios.error.archivo.correo";
    public final static String ERR_KEY_ALFABETICO = "pjw.cargaMasBeneficiarios.error.archivo.alfabetico";
    public final static String ERR_KEY_INDICE_RIF_INVALIDO = "pjw.cargaMasBeneficiarios.error.archivo.indiceInvalido";
    public final static String ERR_KEY_NUMERADOR_RIF_INVALIDO = "pjw.cargaMasBeneficiarios.error.archivo.indNumInvalido";
    public final static String ERR_KEY_LINEA_DETALLE_TAMANO = "pjw.cargaMasBeneficiarios.error.archivo.lineaDetalleTamano";
    public final static String ERR_KEY_ALMENOS_UN_APROBADOR = "pjw.cargaMasBeneficiarios.error.archivo.min1aprobador";
    public final static String ERR_KEY_REFERENCIA = "pjw.cargaMasBeneficiarios.error.archivo.refInvalida";
    public final static String ERR_KEY_CUENTA_NO_ACTIVA = "pjw.cargaMasBeneficiarios.error.archivo.cuentaNoActi";
    public final static String ERR_KEY_CUENTA_EXISTENTE = "pjw.cargaMasBeneficiarios.error.archivo.benefExistente";

    @Override
    public IbCargaBeneficiariosPjDTO listarIbCargaBeneficiariosPj(BigDecimal idEmpresa, Short estatusCarga, String idCanal, String codigoCanal) {
        IbCargaBeneficiariosPjDTO ibCargaBeneficiariosPjDTO = new IbCargaBeneficiariosPjDTO();
        try {
            ibCargaBeneficiariosPjDTO.setIbCargaBeneficiariosPjsList(this.ibCargaBeneficiariosPjFacade.listarIbCargaBeneficiariosPj(idEmpresa, estatusCarga));
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO en listarIbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaBeneficiariosPjDTO == null || ibCargaBeneficiariosPjDTO.getRespuestaDTO() == null) {
                ibCargaBeneficiariosPjDTO = new IbCargaBeneficiariosPjDTO();
                ibCargaBeneficiariosPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaBeneficiariosPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibCargaBeneficiariosPjDTO;
    }

    @Override
    public UtilDTO insertarIbCargaBeneficiariosPj(IbCargaBeneficiariosPjDTO ibCargaBeneficiariosPjDTO, IbBeneficiariosPjDTO cargaBeneficiariosDetPjDTO, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            //Creo la cabecera para la carga
            //Siempre se crea no importando el caso
            IbCargaBeneficiariosPj cargaBeneficiarios = ibCargaBeneficiariosPjDTO.getIbCargaBeneficiariosPj();
            respuesta = this.ibCargaBeneficiariosPjFacade.crearPJ(cargaBeneficiarios);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception("ERROR AL INSERTAR IbCargaBeneficiariosPj");
            }
            //Inicio el ciclo para la lectura de los beneficiarios asociados a la carga
            int i = 1;
            IbBeneficiariosPj srchBenePorRif;
            IbBeneficiariosPj srchBenePorRef;
            for (IbBeneficiariosPj detalle : cargaBeneficiariosDetPjDTO.getIbBeneficiariosPjsList()) {
                //Validamos si el beneficiario existe por el rif y la referencia
                srchBenePorRif = ibBeneficiariosPjFacade.existeBeneficiarioPorRif(cargaBeneficiarios.getIdEmpresa(), detalle.getNroIdentificacionCliente());
                srchBenePorRef = ibBeneficiariosPjFacade.existeBeneficiarioPorRef(cargaBeneficiarios.getIdEmpresa(), detalle.getReferenciaBeneficiario());
                if ((srchBenePorRif == null) && (srchBenePorRef == null)) {
                    //Si no el beneficiario existe lo creamos con sus cuentas
                    detalle.setIdCargaBeneficiario(cargaBeneficiarios);
                    detalle.setIdEmpresa(cargaBeneficiarios.getIdEmpresa());
                    detalle.setNroLineaArchivo(i);
                    detalle.setEstatusAutorizacion(new IbEstatusPagosPj(new BigDecimal(EstatusPagosEnum.PRECARGADO.getId())));
                    detalle.setEstatusBeneficiario(Short.parseShort(EstatusBeneficiarioEnum.INACTIVO.getId()));
                    IbBeneficiariosPj auxCuentaList = new IbBeneficiariosPj();
                    auxCuentaList.setIbCtasXBeneficiariosPjList(detalle.getIbCtasXBeneficiariosPjList());
                    detalle.setIbCtasXBeneficiariosPjList(new ArrayList<>());
                    respuesta = ibBeneficiariosPjFacade.crearPJ(detalle);
                    if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                        throw new Exception("ERROR AL INSERTAR IbBeneficiariosPj");
                    }

                    for (IbCtasXBeneficiariosPj cuenta : auxCuentaList.getIbCtasXBeneficiariosPjList()) {
                        //Lectura de las cuentas por beneficiarios
                        cuenta.setIdBeneficiario(detalle);
                        cuenta.setIdEmpresa(cargaBeneficiarios.getIdEmpresa());
                        cuenta.setEstatusCuenta(Short.parseShort(EstatusCuentaEnum.INACTIVO.getId()));
                        cuenta.setEstatusAutorizacion(new IbEstatusPagosPj(new BigDecimal(EstatusPagosEnum.PRECARGADO.getId())));
                        //Consulto si existe alguna cuenta para el beneficiario
                        //Si no existe la registro como principal sino la registro como secundaria
                        boolean isCuentaRegistrada = this.ibCtasXBeneficiariosPjFacade.esCuentaRegistrada(detalle.getNroIdentificacionCliente(), cargaBeneficiarios.getIdEmpresa());
                        if (!isCuentaRegistrada) {
                            cuenta.setPrincipal(Short.parseShort(CuentaPrincipalEnum.PRINCIPAL.getId()));
                        } else {
                            cuenta.setPrincipal(Short.parseShort(CuentaPrincipalEnum.SECUNDARIA.getId()));
                        }
                        respuesta = this.ibCtasXBeneficiariosPjFacade.crearPJ(cuenta);
                        if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                            throw new Exception("ERROR AL INSERTAR IbCtasXBeneficiariosPj");
                        }
                    }
                } else {
                    //Modifico el idCargaBeneficiario por estar actualizando el registro
                    if (srchBenePorRif.getEstatusAutorizacion().equals(new Short("1"))) {
                        srchBenePorRif.setIdCargaBeneficiario(cargaBeneficiarios);
                        respuesta = ibBeneficiariosPjFacade.modificarPj(srchBenePorRif);
                        if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                            throw new Exception("ERROR AL INSERTAR IbBeneficiariosPj");
                        }
                    }
                    //Validamos si la cuenta existe en base de datos
                    for (IbCtasXBeneficiariosPj cuenta : detalle.getIbCtasXBeneficiariosPjList()) {
                        IbCtasXBeneficiariosPj ibCtasXBeneficiariosPjResult = this.ibCtasXBeneficiariosPjFacade.existeCuenta(cargaBeneficiarios.getIdEmpresa(), cuenta.getNroCuentaBeneficiario(), detalle.getNroIdentificacionCliente());
                        if (ibCtasXBeneficiariosPjResult != null) {
                            //Se actualiza el estatus de la cuenta en base de datos
                            ibCtasXBeneficiariosPjResult.setIdEmpresa(srchBenePorRif.getIdEmpresa());
                            //SE INACTIVA LA CUENTA Y SE CARGA MARCA COMO PRECARGADA
                            ibCtasXBeneficiariosPjResult.setEstatusCuenta(Short.parseShort(EstatusCuentaEnum.INACTIVO.getId()));
//                            ibCtasXBeneficiariosPjResult.setEstatusAutorizacion(new IbEstatusPagosPj(new BigDecimal(EstatusPagosEnum.PRECARGADO.getId())));
                            RespuestaDTO rspDTO = ibCtasXBeneficiariosPjFacade.modificarPj(ibCtasXBeneficiariosPjResult);
                            if (!rspDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                                throw new Exception("ERROR AL MODIFICAR IbCtasXBeneficiariosPj");
                            }
                        } else {
                            //Se crea la cuenta si no existe
                            cuenta.setIdBeneficiario(srchBenePorRif);
                            cuenta.setIdEmpresa(srchBenePorRif.getIdEmpresa());
                            cuenta.setEstatusCuenta(Short.parseShort(EstatusCuentaEnum.INACTIVO.getId()));
                            //Consulto si existe alguna cuenta para el beneficiario
                            //Si no existe la registro como principal sino la registro como secundaria
                            boolean isCuentaRegistrada = this.ibCtasXBeneficiariosPjFacade.esCuentaRegistrada(detalle.getNroIdentificacionCliente(), cargaBeneficiarios.getIdEmpresa());
                            if (!isCuentaRegistrada) {
                                cuenta.setPrincipal(Short.parseShort(CuentaPrincipalEnum.PRINCIPAL.getId()));
                            } else {
                                cuenta.setPrincipal(Short.parseShort(CuentaPrincipalEnum.SECUNDARIA.getId()));
                            }
                            cuenta.setEstatusAutorizacion(new IbEstatusPagosPj(new BigDecimal(EstatusPagosEnum.PRECARGADO.getId())));
                            respuesta = this.ibCtasXBeneficiariosPjFacade.crearPJ(cuenta);
                            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                                throw new Exception("ERROR AL INSERTAR IbCtasXBeneficiariosPj");
                            }
                        }
                    }
                }
                i++;
            }
            ibCargaBeneficiariosPjDTO.setIbCargaBeneficiariosPj(cargaBeneficiarios);
            resultado.put("IbCargaBeneficiariosPj", ibCargaBeneficiariosPjDTO);
            resultado.put("IbCargaBeneficiariosPjList", cargaBeneficiariosDetPjDTO);
            utilDTO.setRespuesta(respuesta);
            utilDTO.setResulados(resultado);
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarIbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarIbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;
    }

    @Override
    public UtilDTO procesarRegistroManualIbCargaBeneficiariosPj(IbCargaBeneficiarioManualDTO ibCargaBeneficiarioManualDTO) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        String codigoCanal = ibCargaBeneficiarioManualDTO.getCodigoCanal();
        int cantReg = 0;
        Short secCumpli = 0;

        try {

            IbCtasXBeneficiariosPj ibCtasXBeneficiariosPjResult = this.ibCtasXBeneficiariosPjFacade.existeCuenta(new IbEmpresas(ibCargaBeneficiarioManualDTO.getIdEmpresa()), ibCargaBeneficiarioManualDTO.getCuentaBeneficiario(), ibCargaBeneficiarioManualDTO.getRifBeneficiario());
            if (ibCtasXBeneficiariosPjResult == null) {
                cantReg++;
                //Cabecera armanda a partir de la información recibida.
                IbCargaBeneficiariosPj ibCargaBeneficiariosPj = new IbCargaBeneficiariosPj();
                ibCargaBeneficiariosPj.setIdEmpresa(new IbEmpresas(ibCargaBeneficiarioManualDTO.getIdEmpresa()));
                ibCargaBeneficiariosPj.setCantidadRegistros(cantReg);
                ibCargaBeneficiariosPj.setEstatusAutorizacion(new IbEstatusPagosPj(new BigDecimal(EstatusPagosEnum.CARGADO.getId())));
                ibCargaBeneficiariosPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaBeneficiarioManualDTO.getCodigoUsuario()));
                ibCargaBeneficiariosPj.setFechaHoraCarga(new Date());

                //Registro de la cabecera en BD
                respuesta = this.ibCargaBeneficiariosPjFacade.crearPJ(ibCargaBeneficiariosPj);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL INSERTAR IbCargaBeneficiariosPj");
                }

                IbBeneficiariosPj ibBeneficiariosPj = new IbBeneficiariosPj();
                IbBeneficiariosPj srchBenePorRif = new IbBeneficiariosPj();

                srchBenePorRif = this.ibBeneficiariosPjFacade.existeBeneficiarioPorRif(new IbEmpresas(ibCargaBeneficiarioManualDTO.getIdEmpresa()), ibCargaBeneficiarioManualDTO.getRifBeneficiario());

                if (srchBenePorRif == null) {

                    //Armado del detalle a partir de los datos recibidos
                    ibBeneficiariosPj.setIdCargaBeneficiario(ibCargaBeneficiariosPj);
                    ibBeneficiariosPj.setIdEmpresa(ibCargaBeneficiariosPj.getIdEmpresa());
                    ibBeneficiariosPj.setNroLineaArchivo(cantReg);
                    ibBeneficiariosPj.setNroIdentificacionCliente(ibCargaBeneficiarioManualDTO.getRifBeneficiario());
                    ibBeneficiariosPj.setReferenciaBeneficiario(ibCargaBeneficiarioManualDTO.getRifBeneficiario());
                    ibBeneficiariosPj.setEstatusAutorizacion(new IbEstatusPagosPj(new BigDecimal(EstatusPagosEnum.CARGADO.getId())));
                    ibBeneficiariosPj.setEstatusBeneficiario(Short.parseShort(EstatusBeneficiarioEnum.INACTIVO.getId()));
                    ibBeneficiariosPj.setNombreBeneficiario(ibCargaBeneficiarioManualDTO.getNombreBeneficiario());
                    ibBeneficiariosPj.setEmailBeneficiario(ibCargaBeneficiarioManualDTO.getEmailBeneficiario());
                    ibBeneficiariosPj.setSecuenciaCumplida(secCumpli);
                    ibBeneficiariosPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaBeneficiarioManualDTO.getCodigoUsuario()));
                    ibBeneficiariosPj.setIbCtasXBeneficiariosPjList(new ArrayList<>());
                    ibBeneficiariosPj.setFechaHoraCarga(new Date());

                    //Registro del detalle en BD
                    respuesta = ibBeneficiariosPjFacade.crearPJ(ibBeneficiariosPj);
                    if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                        throw new Exception("ERROR AL INSERTAR IbBeneficiariosPj");
                    }

                    //Se crean las variables necesarias para la validación del rif de la empresa
                    String auxChart = ibBeneficiariosPj.getNroIdentificacionCliente();
                    auxChart = auxChart.substring(0, 1);
                    Character tipoRif = new Character(auxChart.charAt(0));
                    String rif = ibBeneficiariosPj.getNroIdentificacionCliente();
                    rif = rif.substring(1, rif.length());
                    Long auxInt = Long.parseLong(rif);
                    rif = String.valueOf(auxInt);
                    IbEmpresas rifEmpresa = this.ibEmpresasFacade.findByRif(tipoRif, rif);
                    IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = new IbCtasXBeneficiariosPj();

                    //Lectura de las cuentas por beneficiarios
                    ibCtasXBeneficiariosPj.setIdBeneficiario(ibBeneficiariosPj);

                    ibCtasXBeneficiariosPj.setIdEmpresa(ibCargaBeneficiariosPj.getIdEmpresa());
                    ibCtasXBeneficiariosPj.setEstatusCuenta(Short.parseShort(EstatusCuentaEnum.INACTIVO.getId()));
                    ibCtasXBeneficiariosPj.setEstatusAutorizacion(new IbEstatusPagosPj(new BigDecimal(EstatusPagosEnum.CARGADO.getId())));
                    ibCtasXBeneficiariosPj.setFormaPago(FormaDePagoEnum.DELSUR.getId());

                    //Consulto si existe alguna cuenta para el beneficiario
                    //Si no existe la registro como principal sino la registro como secundaria
                    boolean isCuentaRegistrada = this.ibCtasXBeneficiariosPjFacade.esCuentaRegistrada(ibBeneficiariosPj.getNroIdentificacionCliente(), ibCargaBeneficiariosPj.getIdEmpresa());
                    if (!isCuentaRegistrada) {
                        ibCtasXBeneficiariosPj.setPrincipal(Short.parseShort(CuentaPrincipalEnum.PRINCIPAL.getId()));
                    } else {
                        ibCtasXBeneficiariosPj.setPrincipal(Short.parseShort(CuentaPrincipalEnum.SECUNDARIA.getId()));
                    }

                    //Pregunto si es una cuenta del sur
                    if (this.isCuentaDelSur(ibCargaBeneficiarioManualDTO.getCuentaBeneficiario().trim())) {
                        ibCtasXBeneficiariosPj.setCuentaDelSur(Short.parseShort(CuentaDelSurEnum.DELSUR.getId()));
                        ibCtasXBeneficiariosPj.setNroCuentaBeneficiario(ibCargaBeneficiarioManualDTO.getCuentaBeneficiario().trim());
                    } else {
                        ibCtasXBeneficiariosPj.setCuentaDelSur(Short.parseShort(CuentaDelSurEnum.OTROSBANCOS.getId()));
                        ibCtasXBeneficiariosPj.setNroCuentaBeneficiario(ibCargaBeneficiarioManualDTO.getCuentaBeneficiario().trim());
                    }

                    //Validamos si es una cuenta propia o de otro banco
                    if (rifEmpresa == null) {
                        ibCtasXBeneficiariosPj.setCuentaPropia(Short.parseShort(CuentaPropiaEnum.OTROSBANCOS.getId()));
                    } else {
                        ibCtasXBeneficiariosPj.setCuentaPropia(Short.parseShort(CuentaPropiaEnum.PROPIA.getId()));
                    }

                    ibCtasXBeneficiariosPj.setProducto(Short.parseShort(ProductoEnum.CUENTA.getId()));
                    ibCtasXBeneficiariosPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaBeneficiarioManualDTO.getCodigoUsuario()));
                    ibCtasXBeneficiariosPj.setFechaHoraCarga(new Date());

                    respuesta = this.ibCtasXBeneficiariosPjFacade.crearPJ(ibCtasXBeneficiariosPj);
                    if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                        throw new Exception("ERROR AL INSERTAR IbCtasXBeneficiariosPj");
                    }

                } else {
                    srchBenePorRif.setIdCargaBeneficiario(ibCargaBeneficiariosPj);
                    srchBenePorRif.setEstatusBeneficiario(Short.parseShort(EstatusBeneficiarioEnum.INACTIVO.getId()));
                    respuesta = ibBeneficiariosPjFacade.modificarPj(srchBenePorRif);
                    if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                        throw new Exception("ERROR AL INSERTAR IbBeneficiariosPj");
                    }

                    //Se crean las variables necesarias para la validación del rif de la empresa
                    String auxChart = srchBenePorRif.getNroIdentificacionCliente();
                    auxChart = auxChart.substring(0, 1);
                    Character tipoRif = new Character(auxChart.charAt(0));
                    String rif = srchBenePorRif.getNroIdentificacionCliente();
                    rif = rif.substring(1, rif.length());
                    Long auxInt = Long.parseLong(rif);
                    rif = String.valueOf(auxInt);
                    IbEmpresas rifEmpresa = this.ibEmpresasFacade.findByRif(tipoRif, rif);
                    IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = new IbCtasXBeneficiariosPj();

                    //Lectura de las cuentas por beneficiarios
                    ibCtasXBeneficiariosPj.setIdBeneficiario(srchBenePorRif);

                    ibCtasXBeneficiariosPj.setIdEmpresa(ibCargaBeneficiariosPj.getIdEmpresa());
                    ibCtasXBeneficiariosPj.setEstatusCuenta(Short.parseShort(EstatusCuentaEnum.INACTIVO.getId()));
                    ibCtasXBeneficiariosPj.setEstatusAutorizacion(new IbEstatusPagosPj(new BigDecimal(EstatusPagosEnum.CARGADO.getId())));
                    ibCtasXBeneficiariosPj.setFormaPago(FormaDePagoEnum.DELSUR.getId());

                    //Consulto si existe alguna cuenta para el beneficiario
                    //Si no existe la registro como principal sino la registro como secundaria
                    boolean isCuentaRegistrada = this.ibCtasXBeneficiariosPjFacade.esCuentaRegistrada(ibBeneficiariosPj.getNroIdentificacionCliente(), ibCargaBeneficiariosPj.getIdEmpresa());
                    if (!isCuentaRegistrada) {
                        ibCtasXBeneficiariosPj.setPrincipal(Short.parseShort(CuentaPrincipalEnum.PRINCIPAL.getId()));
                    } else {
                        ibCtasXBeneficiariosPj.setPrincipal(Short.parseShort(CuentaPrincipalEnum.SECUNDARIA.getId()));
                    }

                    //Pregunto si es una cuenta del sur
                    if (this.isCuentaDelSur(ibCargaBeneficiarioManualDTO.getCuentaBeneficiario().trim())) {
                        ibCtasXBeneficiariosPj.setCuentaDelSur(Short.parseShort(CuentaDelSurEnum.DELSUR.getId()));
                        ibCtasXBeneficiariosPj.setNroCuentaBeneficiario(ibCargaBeneficiarioManualDTO.getCuentaBeneficiario().trim());
                    } else {
                        ibCtasXBeneficiariosPj.setCuentaDelSur(Short.parseShort(CuentaDelSurEnum.OTROSBANCOS.getId()));
                        ibCtasXBeneficiariosPj.setNroCuentaBeneficiario(ibCargaBeneficiarioManualDTO.getCuentaBeneficiario().trim());
                    }

                    //Validamos si es una cuenta propia o de otro banco
                    if (rifEmpresa == null) {
                        ibCtasXBeneficiariosPj.setCuentaPropia(Short.parseShort(CuentaPropiaEnum.OTROSBANCOS.getId()));
                    } else {
                        ibCtasXBeneficiariosPj.setCuentaPropia(Short.parseShort(CuentaPropiaEnum.PROPIA.getId()));
                    }

                    ibCtasXBeneficiariosPj.setProducto(Short.parseShort(ProductoEnum.CUENTA.getId()));
                    ibCtasXBeneficiariosPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaBeneficiarioManualDTO.getCodigoUsuario()));
                    ibCtasXBeneficiariosPj.setFechaHoraCarga(new Date());

                    respuesta = this.ibCtasXBeneficiariosPjFacade.crearPJ(ibCtasXBeneficiariosPj);
                    if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                        throw new Exception("ERROR AL INSERTAR IbCtasXBeneficiariosPj");
                    }
                }

                //SIEMPRE SE ENVIA EL CODIGO 1 AL SP
                String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
                //ajustar codigo de la plantilla a usar
                String codigoPlantilla = EstatusPlantillasEmail.NOMINA_PENDIENTE_AUTORIZAR.getDescripcion();
                UtilDTO utilUsuarios = this.ibEmpresasUsuariosDAO.consultarEmpresasUsuariosAprobadores(ibCargaBeneficiarioManualDTO.getIdEmpresa());
                if (utilUsuarios == null || !utilUsuarios.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaBeneficiariosPj ENVIO DE CORREO");
                }
                List<IbEmpresasUsuariosPj> listaUsuarios = (List<IbEmpresasUsuariosPj>) utilUsuarios.getResulados().get("ListaAprobadores");

                //SI NO EXISTEN REPRESENTANTES LEGALES NO SE ENVIA CORREO
                if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                    for (IbEmpresasUsuariosPj usuario : listaUsuarios) {
                        String parametrosCorreo = usuario.getIdEmpresa().getNombre() + usuario.getIdUsuarioPj().getNombre();
                        //ENVIAR EMAIL 
                        emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, usuario.getEmail(), parametrosCorreo, ibCargaBeneficiarioManualDTO.getIdCanal(), codigoCanal);
                    }
                }
                utilDTO.setRespuesta(respuesta);
            }

        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarIbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarIbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return utilDTO;
    }

    @Override
    public UtilDTO modificarEstatusIbCargaBeneficiariosPj(Long idCargaBeneficiario, IbEstatusPagosPjDTO ibEstatusPagosPjDTO,
            BigDecimal idEmpresa, String idCanal,
            String codigoCanal
    ) {
        RespuestaDTO respuesta = new RespuestaDTO();
        int respuestaCuenta = 0;
        UtilDTO utilDTO = new UtilDTO();
        try {
            IbCargaBeneficiariosPj cargaBeneficiarios = ibCargaBeneficiariosPjFacade.find(idCargaBeneficiario);
            if (cargaBeneficiarios != null) {
                cargaBeneficiarios.setEstatusAutorizacion(ibEstatusPagosPjDTO.getIbEstatusPagosPj());
                respuesta = ibCargaBeneficiariosPjFacade.modificarPj(cargaBeneficiarios);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaBeneficiariosPj");
                }
                UtilDTO utilDTODetalle = this.ibBeneficiariosPjDAO.modificarEstatusIbBeneficiariosPj(idCargaBeneficiario, ibEstatusPagosPjDTO, codigoCanal);
                if (!utilDTODetalle.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbBeneficiariosPj");
                }
                respuestaCuenta = this.ibCtasXBeneficiariosPjFacade.modificarEstatusCuentas(idCargaBeneficiario, EstatusCuentaEnum.ACTIVO.getId());
                if (respuestaCuenta == 0) {
                    throw new Exception("ERROR AL MODIFICAR IbCtasXBeneficiariosPj");
                }
                //SIEMPRE SE ENVIA EL CODIGO 1 AL SP
                String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
                //ajustar codigo de la plantilla a usar
                String codigoPlantilla = EstatusPlantillasEmail.NOMINA_PENDIENTE_AUTORIZAR.getDescripcion();
                UtilDTO utilUsuarios = this.ibEmpresasUsuariosDAO.consultarEmpresasUsuariosAprobadores(idEmpresa);
                if (utilUsuarios == null || !utilUsuarios.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaBeneficiariosPj ENVIO DE CORREO");
                }
                List<IbEmpresasUsuariosPj> listaUsuarios = (List<IbEmpresasUsuariosPj>) utilUsuarios.getResulados().get("ListaAprobadores");

                //SI NO EXISTEN REPRESENTANTES LEGALES NO SE ENVIA CORREO
                if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                    for (IbEmpresasUsuariosPj usuario : listaUsuarios) {
                        String parametrosCorreo = "";
                        //Se necesitan los parametros para el caso
                        //Los mismos van a ser consultados para llenar la plantilla
                        parametrosCorreo = usuario.getIdEmpresa().getNombre() + usuario.getIdUsuarioPj().getNombre();
                        //ENVIAR EMAIL
                        emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, usuario.getEmail(), parametrosCorreo, idCanal, codigoCanal);
                    }
                }
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR IbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaBeneficiariosPj: ")
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
    public UtilDTO procesarArchivoIbCargaBeneficiariosPj(IbCargaArchivoDTO ibCargaArchivoDTO
    ) {
        UtilDTO utilDTO = new UtilDTO();

        byte[] data = ibCargaArchivoDTO.getDataFile();

        IbCargaBeneficiariosPjDTO ibCargaBeneficiariosPjDTO = new IbCargaBeneficiariosPjDTO();

        ibErroresCargaPjDTOLista.setIbErroresCargaPjDTO(new ArrayList<IbErroresCargaPjDTO>());

        IbBeneficiariosPjDTO ibBeneficiariosPjDTO = new IbBeneficiariosPjDTO();
        ibBeneficiariosPjDTO.setIbBeneficiariosPjsList(new ArrayList());

        IbCtasXBeneficiariosPjDTO ibCtasXBeneficiariosPjDTO = new IbCtasXBeneficiariosPjDTO();
        ibCtasXBeneficiariosPjDTO.setIbCtasXBeneficiariosPjsList(new ArrayList());

        Integer idCargaBeneficiario = null;
        String line = "";
        String detLinea = "";
        String lnArchivo133 = "133";
        String ctaDelSur = "";
        int caAprobadores = 0;
        int nroLinea = 0;
        int detNroLinea = 0;

        try {
            //Mensajes de errores creados para el caso
            Map<String, String> mapErrorres = ibCargaBeneficiariosPjFacade.listarErrorresCargaBeneficiariosMasiva();
            Map param = new HashMap();

            //Buffer de lectura del archivo
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));

            //Valido el archivo cargado
            ValidatorCargaBeneficiarios validatorCargaBeneficiarios = new ValidatorCargaBeneficiarios(ibCargaArchivoDTO);
            validatorCargaBeneficiarios.clearErrors();
            validatorCargaBeneficiarios.setMapErrorres(mapErrorres);

//            Validación de aprobadores para el caso
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
                    throw new IbErroresCargaPjException(mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_ALMENOS_UN_APROBADOR));
                }
            } catch (Exception e) {
                validatorCargaBeneficiarios.addError(line, nroLinea, mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_ALMENOS_UN_APROBADOR));
            }

            //Validación para archivos con nombres repetidos
            if (validatorCargaBeneficiarios.countErrors() == 0) {
                if (!reglaNoExisteOtroArchivoConMismoNombre(ibCargaArchivoDTO)) {
                    validatorCargaBeneficiarios.addError(line, nroLinea, mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_MISMO_NOMBRE));
                }
            }

            //Carga del detalle beneficiario
            if (validatorCargaBeneficiarios.countErrors() == 0) {
                for (nroLinea = 0; (line = br.readLine()) != null;) {
                    nroLinea++;

                    //Detalle beneficiario
                    IbBeneficiariosPj ibBeneficiariosPj;

                    try {

                        param.put("linea", line);
                        param.put("nroLinea", nroLinea);

                        ibBeneficiariosPj = (IbBeneficiariosPj) validatorCargaBeneficiarios.leerDetalle(param);
                        ibBeneficiariosPj.setIdEmpresa(new IbEmpresas(ibCargaArchivoDTO.getCodigoUsuario()));
                        ibBeneficiariosPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaArchivoDTO.getCodigoUsuario()));
                        ibBeneficiariosPj.setIbCtasXBeneficiariosPjList(new ArrayList<>());

                        //Se crean las variables necesarias para la validación del rif de la empresa
                        String auxChart = ibBeneficiariosPj.getNroIdentificacionCliente();
                        auxChart = auxChart.substring(0, 1);
                        Character tipoRif = new Character(auxChart.charAt(0));
                        String rif = ibBeneficiariosPj.getNroIdentificacionCliente();
                        rif = rif.substring(1, rif.length());
                        Long auxInt = Long.parseLong(rif);
                        rif = String.valueOf(auxInt);
                        IbEmpresas rifEmpresa = this.ibEmpresasFacade.findByRif(tipoRif, rif);

                        //Valido el origen de la cuenta
                        //Si proviene de un archivo de 133 caracteres o de 398
                        //Para luego setearlo al objeto
                        if (validatorCargaBeneficiarios.getFntOrigen().equalsIgnoreCase(lnArchivo133)) {
                            int countCuentas = 0;
                            //Valido si existe una cuenta ya registrada con las caracteristicas descritas en el metodo existe cuenta
                            IbCtasXBeneficiariosPj ibCtasXBeneficiariosPjResult = this.ibCtasXBeneficiariosPjFacade.existeCuenta(new IbEmpresas(ibCargaArchivoDTO.getIdEmpresa()), validatorCargaBeneficiarios.getCtaBeneficiario133(), ibBeneficiariosPj.getNroIdentificacionCliente(), ibBeneficiariosPj.getReferenciaBeneficiario());
                            if (ibCtasXBeneficiariosPjResult == null) {
                                //Detalle cuentas beneficiarios
                                IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = new IbCtasXBeneficiariosPj();
                                ibCtasXBeneficiariosPj.setIdEmpresa(new IbEmpresas(ibCargaArchivoDTO.getCodigoUsuario()));
                                ibCtasXBeneficiariosPj.setEstatusCuenta(Short.parseShort(EstatusCuentaEnum.INACTIVO.getId()));
                                ibCtasXBeneficiariosPj.setFormaPago(FormaDePagoEnum.DELSUR.getId());

                                //Validamos si es una cuenta propia o de otro banco
                                if (rifEmpresa == null) {
                                    ibCtasXBeneficiariosPj.setCuentaPropia(Short.parseShort(CuentaPropiaEnum.OTROSBANCOS.getId()));
                                } else {
                                    ibCtasXBeneficiariosPj.setCuentaPropia(Short.parseShort(CuentaPropiaEnum.PROPIA.getId()));
                                }

                                //Pregunto si es una cuenta del sur
                                if (this.isCuentaDelSur(validatorCargaBeneficiarios.getCtaBeneficiario133())) {
                                    ibCtasXBeneficiariosPj.setCuentaDelSur(Short.parseShort(CuentaDelSurEnum.DELSUR.getId()));
                                    ibCtasXBeneficiariosPj.setNroCuentaBeneficiario(validatorCargaBeneficiarios.getCtaBeneficiario133());
                                } else {
                                    ibCtasXBeneficiariosPj.setCuentaDelSur(Short.parseShort(CuentaDelSurEnum.OTROSBANCOS.getId()));
                                    ibCtasXBeneficiariosPj.setNroCuentaBeneficiario(validatorCargaBeneficiarios.getCtaBeneficiario133());
                                }
                                ibCtasXBeneficiariosPj.setProducto(Short.parseShort(ProductoEnum.CUENTA.getId()));
                                ibCtasXBeneficiariosPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaArchivoDTO.getCodigoUsuario()));
                                ibCtasXBeneficiariosPj.setFechaHoraCarga(new Date());
                                ibBeneficiariosPj.getIbCtasXBeneficiariosPjList().add(ibCtasXBeneficiariosPj);
                            } else {
                                validatorCargaBeneficiarios.addError(line, nroLinea, mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_CUENTA_EXISTENTE));
                                countCuentas++;
                            }
                            if (countCuentas == 0) {
                                ibBeneficiariosPjDTO.getIbBeneficiariosPjsList().add(ibBeneficiariosPj);
                            }

                        } else {
                            int countCuentas = 0;
                            //Recibo la cuenta de doce caracteres
                            //Creo una nueva variable con 10 caracteres para el envio de la misma al SP
                            //Dos cuentas por registro
                            String aux = validatorCargaBeneficiarios.getCtaBeneficiarioDelSur398().substring(2);
                            //Creo la cuenta a partir de los datos leidos del registro
                            ctaDelSur = cuentaDAO.obtenerCtaDelSur20DigitosSP(aux, ibCargaArchivoDTO.getCodigoCanal());
                            //Valido si existe la cuenta
                            UtilDTO auxCuentaUtilDTO = cuentaDAO.validarCuentaEmpresaCliente(ctaDelSur, tipoRif, rif, ibCargaArchivoDTO.getIdCanal(), ibCargaArchivoDTO.getCodigoCanal());
                            if (auxCuentaUtilDTO != null && auxCuentaUtilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && auxCuentaUtilDTO.getResulados().equals(BDSUtil.VALIDO)) {
                                if (ctaDelSur != "") {
                                    //Valido si existe una cuenta ya registrada con las caracteristicas descritas en el metodo existe cuenta
                                    IbCtasXBeneficiariosPj ibCtasXBeneficiariosPjResult = this.ibCtasXBeneficiariosPjFacade.existeCuenta(new IbEmpresas(ibCargaArchivoDTO.getIdEmpresa()), ctaDelSur, ibBeneficiariosPj.getNroIdentificacionCliente(), ibBeneficiariosPj.getReferenciaBeneficiario());
                                    if (ibCtasXBeneficiariosPjResult == null) {
                                        //Detalle cuentas beneficiarios
                                        IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = new IbCtasXBeneficiariosPj();

                                        //Validamos si es una cuenta propia o de otro banco
                                        if (rifEmpresa == null) {
                                            ibCtasXBeneficiariosPj.setCuentaPropia(Short.parseShort(CuentaPropiaEnum.OTROSBANCOS.getId()));
                                        } else {
                                            ibCtasXBeneficiariosPj.setCuentaPropia(Short.parseShort(CuentaPropiaEnum.PROPIA.getId()));
                                        }

                                        ibCtasXBeneficiariosPj.setIdEmpresa(new IbEmpresas(ibCargaArchivoDTO.getCodigoUsuario()));
                                        ibCtasXBeneficiariosPj.setEstatusCuenta(Short.parseShort(EstatusCuentaEnum.INACTIVO.getId()));
                                        ibCtasXBeneficiariosPj.setFormaPago(FormaDePagoEnum.DELSUR.getId());
                                        //Pregunto si es una cuenta del sur
                                        if (this.isCuentaDelSur(ctaDelSur)) {
                                            ibCtasXBeneficiariosPj.setCuentaDelSur(Short.parseShort(CuentaDelSurEnum.DELSUR.getId()));
                                        } else {
                                            ibCtasXBeneficiariosPj.setCuentaDelSur(Short.parseShort(CuentaDelSurEnum.OTROSBANCOS.getId()));
                                        }
                                        ibCtasXBeneficiariosPj.setNroCuentaBeneficiario(ctaDelSur);
                                        ibCtasXBeneficiariosPj.setProducto(Short.parseShort(ProductoEnum.CUENTA.getId()));
                                        ibCtasXBeneficiariosPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaArchivoDTO.getCodigoUsuario()));
                                        ibCtasXBeneficiariosPj.setFechaHoraCarga(new Date());
                                        //La agrego al list para despues agregarla si existe
                                        ibBeneficiariosPj.getIbCtasXBeneficiariosPjList().add(ibCtasXBeneficiariosPj);
                                    }
                                } else {
                                    validatorCargaBeneficiarios.addError(line, nroLinea, mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_CUENTA_EXISTENTE));
                                    countCuentas++;
                                }
                            } else {
                                validatorCargaBeneficiarios.addError(ctaDelSur, detNroLinea, mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_CUENTA_NO_ACTIVA));
                                countCuentas++;
                            }
                            //Valido si existe una cuenta ya registrada con las caracteristicas descritas en el metodo existe cuenta
                            IbCtasXBeneficiariosPj ibCtasXBeneficiariosPjResult = this.ibCtasXBeneficiariosPjFacade.existeCuenta(new IbEmpresas(ibCargaArchivoDTO.getIdEmpresa()), validatorCargaBeneficiarios.getCtaBeneficiarioOtrosBancos398(), ibBeneficiariosPj.getNroIdentificacionCliente(), ibBeneficiariosPj.getReferenciaBeneficiario());
                            if (ibCtasXBeneficiariosPjResult == null) {
                                //Detalle cuentas beneficiarios
                                IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = new IbCtasXBeneficiariosPj();
                                //Validamos si es una cuenta propia o de otro banco
                                if (rifEmpresa == null) {
                                    ibCtasXBeneficiariosPj.setCuentaPropia(Short.parseShort(CuentaPropiaEnum.OTROSBANCOS.getId()));
                                } else {
                                    ibCtasXBeneficiariosPj.setCuentaPropia(Short.parseShort(CuentaPropiaEnum.PROPIA.getId()));
                                }

                                ibCtasXBeneficiariosPj.setIdEmpresa(new IbEmpresas(ibCargaArchivoDTO.getCodigoUsuario()));
                                ibCtasXBeneficiariosPj.setEstatusCuenta(Short.parseShort(EstatusCuentaEnum.INACTIVO.getId()));
                                ibCtasXBeneficiariosPj.setFormaPago(FormaDePagoEnum.DELSUR.getId());
                                if (this.isCuentaDelSur(validatorCargaBeneficiarios.getCtaBeneficiarioOtrosBancos398())) {
                                    ibCtasXBeneficiariosPj.setCuentaDelSur(Short.parseShort(CuentaDelSurEnum.DELSUR.getId()));
                                } else {
                                    ibCtasXBeneficiariosPj.setCuentaDelSur(Short.parseShort(CuentaDelSurEnum.OTROSBANCOS.getId()));
                                }
                                ibCtasXBeneficiariosPj.setNroCuentaBeneficiario(validatorCargaBeneficiarios.getCtaBeneficiarioOtrosBancos398());
                                ibCtasXBeneficiariosPj.setProducto(Short.parseShort(ProductoEnum.CUENTA.getId()));
                                ibCtasXBeneficiariosPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaArchivoDTO.getCodigoUsuario()));
                                ibCtasXBeneficiariosPj.setFechaHoraCarga(new Date());
                                ibBeneficiariosPj.getIbCtasXBeneficiariosPjList().add(ibCtasXBeneficiariosPj);

                            } else {
                                validatorCargaBeneficiarios.addError(line, nroLinea, mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_CUENTA_EXISTENTE));
                                countCuentas++;
                            }

                            if (countCuentas != 2) {
                                ibBeneficiariosPjDTO.getIbBeneficiariosPjsList().add(ibBeneficiariosPj);
                            }
                        }

                    } catch (Exception e) {
                        detLinea = line;
                        detNroLinea = nroLinea;
                        validatorCargaBeneficiarios.addError(line, nroLinea, e.getMessage());
                    }
                }

                //DTO encabezado
                IbCargaBeneficiariosPj ibCargaBeneficiariosPj;
                ibCargaBeneficiariosPj = new IbCargaBeneficiariosPj();
                ibCargaBeneficiariosPj.setIdEmpresa(new IbEmpresas(ibCargaArchivoDTO.getIdEmpresa()));
                ibCargaBeneficiariosPj.setNombreArchivo(ibCargaArchivoDTO.getFileName());
                ibCargaBeneficiariosPj.setCantidadRegistros(nroLinea);
                ibCargaBeneficiariosPj.setEstatusAutorizacion(new IbEstatusPagosPj(new BigDecimal(EstatusPagosEnum.PRECARGADO.getId())));
                ibCargaBeneficiariosPj.setCodigoUsuarioCarga(new IbUsuariosPj(ibCargaArchivoDTO.getCodigoUsuario()));

                ibCargaBeneficiariosPjDTO.setIbCargaBeneficiariosPj(ibCargaBeneficiariosPj);

                //Validación minimo una linea a registrar
                if (this.reglaDebeProcesarAlmenosUnDetalleParaPersistencia(ibBeneficiariosPjDTO)) {
                    utilDTO = insertarIbCargaBeneficiariosPj(ibCargaBeneficiariosPjDTO, ibBeneficiariosPjDTO, ibCargaArchivoDTO.getIdCanal(), ibCargaArchivoDTO.getCodigoCanal());
                } else {
                    validatorCargaBeneficiarios.addError(detLinea, detNroLinea, mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_ALMENOS_UN_DETALLE_CARGADO_PARA_PERSISTENCIA));
                }
            }

            //Si hay un error el insert retorna nulls en la respuesta y el resultado
            if (utilDTO.getRespuesta() == null) {
                utilDTO.setRespuesta(new RespuestaDTO());
            }
            utilDTO.getRespuesta().setCodigoSP(CODIGO_RESPUESTA_EXITOSO);

            //Se lee el idBeneficiario, en caso de exitir
            try {
                idCargaBeneficiario = ibCargaBeneficiariosPjDTO.getIbCargaBeneficiariosPj().getIdCargaBeneficiario().intValue();
            } catch (Exception e) {
                validatorCargaBeneficiarios.addError(String.valueOf(idCargaBeneficiario), nroLinea, mapErrorres.get(IbCargaBeneficiariosPjDAOImpl.ERR_KEY_ID_BENEFICIARIO_NULL));
            }

            //Nos interesa enviar solo los errores
            utilDTO.setResulados(new HashMap());
            utilDTO.getResulados().put(ID_PAGO, idCargaBeneficiario);
            utilDTO.getResulados().put(LISTA_ERRORES_CARGA_ARCHIVO, validatorCargaBeneficiarios.getIbErroresCargaPjDTO());

            return utilDTO;

        } catch (IOException ioex) {
            logger.error( new StringBuilder("ERROR IO : ")
                    .append("-CH- ").append(ibCargaArchivoDTO.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ioex.getCause()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        } catch (Exception ex) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(ibCargaArchivoDTO.getCodigoCanal())
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(ex.getCause()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        }

        return utilDTO;

    }

    @Override
    public UtilDTO eliminarIbCargaBeneficiariosPjEstatusCero(Long idCargaBeneficiario, String codigoCanal
    ) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        utilDTO.setRespuesta(new RespuestaDTO());
        try {
            IbCargaBeneficiariosPj cargaBeneficiario = ibCargaBeneficiariosPjFacade.buscarIbCargaBeneficiariosPj(idCargaBeneficiario);
            if (cargaBeneficiario != null) {
                if (cargaBeneficiario.getEstatusAutorizacion().getId() == BigDecimal.ZERO) {
                    respuesta = this.ibCargaBeneficiariosPjFacade.eliminar(cargaBeneficiario);
                } else {
                    respuesta.setCodigo(CODIGO_ERROR_VALIDACIONES);
                    respuesta.setDescripcion("El beneficiario no esta en estatus en precargado");
                }
            } else {
                respuesta.setCodigo(CODIGO_ERROR_VALIDACIONES);
                respuesta.setDescripcion("Beneficiario no encontrado");
            }
            utilDTO.setRespuesta(respuesta);
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR IbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;
    }

    public String parametro(String codigo, String codigoCanal) {
        return ibParametrosFacade
                .consultaParametro(codigo, codigoCanal)
                .getIbParametro().getValor();
    }

    public boolean reglaNoExisteOtroArchivoConMismoNombre(IbCargaArchivoDTO ibCargaArchivoDto) throws Exception {
        return !ibCargaBeneficiariosPjFacade.archivoCargaBeneficiariosMasivaExiste(
                new IbEmpresas(ibCargaArchivoDto.getIdEmpresa()),
                ibCargaArchivoDto.getFileName());
    }

    public boolean reglaDebeProcesarAlmenosUnDetalleParaPersistencia(IbBeneficiariosPjDTO ibBeneficiariosPjDTO) {
        return ibBeneficiariosPjDTO.getIbBeneficiariosPjsList().size() > 0;
    }

    @Override
    public UtilDTO procesarRegistroManualModificarIbCargaBeneficiariosPj(IbCargaBeneficiarioManualModificarDTO ibCargaBeneficiarioManualModificarDTO, String canal, String codigoCanal) {

        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaBeneficiario = new RespuestaDTO();
        RespuestaDTO respuestaCuentaEliminar = new RespuestaDTO();
        RespuestaDTO respuestaCuentaModificar = new RespuestaDTO();

        try {
            //Consulto si existe el beneficiario
            IbBeneficiariosPj beneficiario = ibBeneficiariosPjFacade.buscarIbBeneficiariosPj(ibCargaBeneficiarioManualModificarDTO.getBeneficiario().getIbBeneficiariosPj().getIdBeneficiario());
            IbEmpresas empresa = new IbEmpresas(beneficiario.getIdEmpresa().getId());
            if (beneficiario != null) {
                beneficiario.setNombreBeneficiario(ibCargaBeneficiarioManualModificarDTO.getBeneficiario().getIbBeneficiariosPj().getNombreBeneficiario());
                beneficiario.setEmailBeneficiario(ibCargaBeneficiarioManualModificarDTO.getBeneficiario().getIbBeneficiariosPj().getEmailBeneficiario());
                respuestaBeneficiario = this.ibBeneficiariosPjFacade.modificarPj(beneficiario);
            } else {                
                respuestaBeneficiario.setCodigo(CODIGO_EXCEPCION_GENERICA);
            }
            int i = 0;
            //Consulto las cuentas a eliminar
            for (IbCtasXBeneficiariosPjDTO ibCtasXBeneficiariosPjDTO : ibCargaBeneficiarioManualModificarDTO.getListaCuentasEliminar()) {
                Long cuenta = ibCtasXBeneficiariosPjDTO.getIbCtasXBeneficiariosPjsList().get(i).getIdCuenta();
                IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = this.ibCtasXBeneficiariosPjFacade.find(cuenta);
                if (ibCtasXBeneficiariosPj != null) {
                    //Cambio el estatus a inactivo
                    ibCtasXBeneficiariosPj.setEstatusCuenta(new Short(EstatusCuentaEnum.INACTIVO.getId()));
                    respuestaCuentaEliminar = this.ibCtasXBeneficiariosPjFacade.modificarPj(ibCtasXBeneficiariosPj);
                } else {
                    respuestaCuentaEliminar.setCodigo(CODIGO_EXCEPCION_GENERICA);
                }
                i++;
            }
            i = 0;
            //Consulto las cuentas a modificar
            for (IbCtasXBeneficiariosPjDTO ibCtasXBeneficiariosPjDTO : ibCargaBeneficiarioManualModificarDTO.getListaCuentasModificar()) {
                Long cuenta = ibCtasXBeneficiariosPjDTO.getIbCtasXBeneficiariosPjsList().get(i).getIdCuenta();
                IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = this.ibCtasXBeneficiariosPjFacade.find(cuenta);
                if (ibCtasXBeneficiariosPj != null) {
                    IbCtasXBeneficiariosPj cuentaAux = this.ibCtasXBeneficiariosPjFacade.existeCuenta(ibCtasXBeneficiariosPj.getIdEmpresa(), ibCtasXBeneficiariosPjDTO.getIbCtasXBeneficiariosPjsList().get(i).getNroCuentaBeneficiario(), ibCtasXBeneficiariosPj.getIdBeneficiario().getNroIdentificacionCliente());
                    if (cuentaAux == null) {
                        if(this.isCuentaDelSur(ibCtasXBeneficiariosPjDTO.getIbCtasXBeneficiariosPjsList().get(i).getNroCuentaBeneficiario())){
                            ibCtasXBeneficiariosPj.setCuentaDelSur(Short.parseShort(CuentaDelSurEnum.DELSUR.getId()));
                        }else{
                            ibCtasXBeneficiariosPj.setCuentaDelSur(Short.parseShort(CuentaDelSurEnum.OTROSBANCOS.getId()));
                        }
                        ibCtasXBeneficiariosPj.setNroCuentaBeneficiario(ibCtasXBeneficiariosPjDTO.getIbCtasXBeneficiariosPjsList().get(i).getNroCuentaBeneficiario());
                        respuestaCuentaModificar = this.ibCtasXBeneficiariosPjFacade.modificarPj(ibCtasXBeneficiariosPj);
                    } else {
                       respuestaCuentaModificar.setCodigo(CODIGO_EXCEPCION_GENERICA);
                    }
                } else {
                    respuestaCuentaModificar.setCodigo(CODIGO_EXCEPCION_GENERICA);
                }
                i++;
            }

            //SIEMPRE SE ENVIA EL CODIGO 1 AL SP
            String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
            //ajustar codigo de la plantilla a usar
            String codigoPlantilla = EstatusPlantillasEmail.NOMINA_PENDIENTE_AUTORIZAR.getDescripcion();
            UtilDTO utilUsuarios = this.ibEmpresasUsuariosDAO.consultarEmpresasUsuariosAprobadores(empresa.getId());
            if (utilUsuarios == null || !utilUsuarios.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception("ERROR AL MODIFICAR IbCargaBeneficiariosPj");
            }
            List<IbEmpresasUsuariosPj> listaUsuarios = (List<IbEmpresasUsuariosPj>) utilUsuarios.getResulados().get("ListaAprobadores");

            //SI NO EXISTEN REPRESENTANTES LEGALES NO SE ENVIA CORREO
            if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                for (IbEmpresasUsuariosPj usuario : listaUsuarios) {
                    String parametrosCorreo = "";
                    //Se necesitan los parametros para el caso
                    //Los mismos van a ser consultados para llenar la plantilla
                    parametrosCorreo = usuario.getIdEmpresa().getNombre() + usuario.getIdUsuarioPj().getNombre();
                    //ENVIAR EMAIL
                    emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, usuario.getEmail(), parametrosCorreo, canal, codigoCanal);
                }
            }

        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR IbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuestaBeneficiario.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuestaBeneficiario.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        
        utilDTO.setRespuesta(respuestaBeneficiario);
        
        if ((!respuestaBeneficiario.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) || (!respuestaCuentaEliminar.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) || (!respuestaCuentaModificar.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO))) {
            respuestaBeneficiario.setCodigo(CODIGO_EXCEPCION_GENERICA);
            utilDTO.setRespuesta(respuestaBeneficiario);
        } 

        return utilDTO;
    }

    @Override
    public IbCargaBeneficiariosPjDTO listarIbCargaBeneficiariosAutorizarPj(BigDecimal idEmpresa, Long idUsuarioAurorizado,Long idServicio, String StatusAConsultar, String idCanal, String codigoCanal) {
       IbCargaBeneficiariosPjDTO ibCargaBeneficiariosPjDTO = new IbCargaBeneficiariosPjDTO();
        try {
            ibCargaBeneficiariosPjDTO = this.ibCargaBeneficiariosPjFacade.listarIbCargaBeneficiariosAutorizarPj(idEmpresa,  idUsuarioAurorizado, idServicio,  StatusAConsultar);
                                 
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO en listarIbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaBeneficiariosPjDTO == null || ibCargaBeneficiariosPjDTO.getRespuestaDTO() == null) {
                ibCargaBeneficiariosPjDTO = new IbCargaBeneficiariosPjDTO();
                ibCargaBeneficiariosPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaBeneficiariosPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibCargaBeneficiariosPjDTO;
    }
      

}
