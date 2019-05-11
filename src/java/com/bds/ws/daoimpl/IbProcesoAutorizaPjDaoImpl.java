/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbProcesoAutorizaPjDAO;
import com.bds.ws.dao.IbSecuenciaAprobacionPjDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusCuentaEnum;
import com.bds.ws.enumerated.EstatusPagosEnum;
import com.bds.ws.facade.IbCargaBeneficiariosPjFacade;
import com.bds.ws.facade.IbCargaNominaPjFacade;
import com.bds.ws.facade.IbCargaPagosCorpPjFacade;
import com.bds.ws.facade.IbCargaPagosEspPjFacade;
import com.bds.ws.facade.IbCtasXBeneficiariosPjFacade;
import com.bds.ws.facade.IbEstatusPagosPjFacade;
import com.bds.ws.facade.IbFideicomisoPjFacade;
import com.bds.ws.facade.IbProcesoAutorizaPjFacade;
import com.bds.ws.facade.IbSecuenciaAprobacionPjFacade;
import com.bds.ws.facade.IbServiciosPjFacade;
import com.bds.ws.model.IbAutorizaPj;
import com.bds.ws.model.IbBeneficiariosPj;
import com.bds.ws.model.IbCargaBeneficiariosPj;
import com.bds.ws.model.IbCargaNominaDetallePj;
import com.bds.ws.model.IbCargaNominaPj;
import com.bds.ws.model.IbCargaPagosCorpDetPj;
import com.bds.ws.model.IbCargaPagosCorpPj;
import com.bds.ws.model.IbCargaPagosEspDetPj;
import com.bds.ws.model.IbCargaPagosEspPj;
import com.bds.ws.model.IbCtasXBeneficiariosPj;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.model.IbFideicomisoDetPj;
import com.bds.ws.model.IbFideicomisoPj;
import com.bds.ws.model.IbServiciosPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
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
 * @author juan.vasquez
 */
@Named("IbProcesoAutorizaPjDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbProcesoAutorizaPjDaoImpl extends BDSUtil implements IbProcesoAutorizaPjDAO {

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbProcesoAutorizaPjDaoImpl.class.getName());

    @EJB
    private IbProcesoAutorizaPjFacade ibProcesoAutorizaPjFacade;
    @EJB
    private IbCargaNominaPjFacade IbCargaNominaPjFacade;
    @EJB
    private IbCargaBeneficiariosPjFacade ibCargaBeneficiariosPjFacade;
    @EJB
    private IbCargaPagosEspPjFacade ibCargaPagosEspPjFacade;
    @EJB
    private IbCargaPagosCorpPjFacade ibCargaPagosCorpPjFacade;
    @EJB
    private IbSecuenciaAprobacionPjFacade ibSecuenciaAprobacionPjFacade;
    @EJB
    private IbEstatusPagosPjFacade ibEstatusPagosPjFacade;
    @EJB
    private IbSecuenciaAprobacionPjDAO ibSecuenciaAprobacionPjDAO;
    @EJB
    private IbFideicomisoPjFacade ibFideicomisoPjFacade;
    @EJB
    private IbServiciosPjFacade ibServiciosPjFacade;
    @EJB
    private IbCtasXBeneficiariosPjFacade ibCtasXBeneficiariosPjFacade;

    public final static String COD_HORA_MININA = "pjw.nomina.minimaHoraEjecucion";
    public final static String COD_HORA_MAXIMA = "pjw.nomina.maximaHoraEjecucion";
    public final static String COD_VALIDAR_FERIADOS = "pjw.nomina.validarDiaFeriado";
    public final static String COD_MINIMO_HORAS_ANTICIPACION = "pjw.nominaCargaMasiva.error.archivo.minAnticip";

    public final static String VALIDACION_ACTIVA = "1";
    public final static String VALIDACION_INACTIVA = "0";
    public final static String SERVICIO_NOMINA = "pjw.menu.opcion.cashmanagement.nomina.autorizar";
    public final static String SERVICIO_PAGOS_ESPECIALES = "pjw.menu.opcion.cashmanagement.pagosesp.autorizar";
    public final static String SERVICIO_PAGOS_CORPORATIVOS = "pjw.menu.opcion.cashmanagement.pagoscorp.autorizar";
    public final static String SERVICIO_BENEFICIARIOS = "pjw.pagoscorporativos.beneficiarios.autorizar";
    public final static String SERVICIO_FIDEICOMISO = "pjw.menu.opcion.cashmanagement.fide.autorizar";
    public final static String SERVICIO_CTAX_X_BENEFICIARIOS = "pjw.pagoscorporativos.beneficiarios.ctas.autorizar";

    public final static int NUMERO_LINEAS_MINIMO = 2;

    @Override
    public UtilDTO autorizaNominaPj(List<IbAutorizaPj> ibAutorizaPjsList, Long idUsuario, Integer cantidadAprovadores, BigDecimal idPago, String idCanal, String codigoCanal) {
        //SIEMPRE SE VA A DEVOLVER UN REGISTRO POR ESTA RAZON SE HACE EL .get(0)
        IbServiciosPj ibServiciosPj = ibServiciosPjFacade.findByNombre(SERVICIO_NOMINA).get(0);
        RespuestaDTO respuesta = new RespuestaDTO();
        IbCargaNominaDetallePj IbCargaNominaDetallePj = null;
        Integer numAprovacionesReg = 0;
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        IbEstatusPagosPj estaAutorizado = new IbEstatusPagosPj(BigDecimal.valueOf(EstatusPagosEnum.AUTORIZADO.getId()));
        IbEstatusPagosPj estaDesAutorizado = new IbEstatusPagosPj(BigDecimal.valueOf(EstatusPagosEnum.PENDIENTE_X_AUTORIZAR.getId()));
        try {
            if (cantidadAprovadores == 1) {
                IbCargaNominaPj ibCargaNominaPj = IbCargaNominaPjFacade.find(idPago);
                ibCargaNominaPj.setEstatus(estaAutorizado);
                IbCargaNominaPjFacade.edit(ibCargaNominaPj);
                for (IbAutorizaPj ibAutorizaPj : ibAutorizaPjsList) {
                    respuesta = ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibServiciosPj.getId(), ibAutorizaPj.getId().longValue(), BigDecimal.valueOf(idUsuario)).getRespuesta();
                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                        IbCargaNominaDetallePj = ibProcesoAutorizaPjFacade.actualizarEstatusDetalleNominaPorId(ibAutorizaPj.id, BigDecimal.valueOf(EstatusPagosEnum.AUTORIZADO.getId()));
                        if (IbCargaNominaDetallePj == null) {
                            respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                            respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                        }
                    } else {
                        respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                        respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                    }
                }
            } else {
                int flag = 0;

                for (IbAutorizaPj ibAutorizaPj : ibAutorizaPjsList) {
                    flag++;
                    numAprovacionesReg = this.ibSecuenciaAprobacionPjFacade.buscarAprobacionesMultiples(ibServiciosPj.getId(), ibAutorizaPj.id.longValue()).size();
                    respuesta = ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibServiciosPj.getId(), ibAutorizaPj.getId().longValue(), BigDecimal.valueOf(idUsuario)).getRespuesta();
                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                        numAprovacionesReg = numAprovacionesReg + 1;
                        if (numAprovacionesReg == cantidadAprovadores) {
                            if (flag == 1) {
                                IbCargaNominaPj ibCargaNominaPj = IbCargaNominaPjFacade.find(idPago);
                                ibCargaNominaPj.setEstatus(estaAutorizado);
                                IbCargaNominaPjFacade.edit(ibCargaNominaPj);
                            }
                            IbCargaNominaDetallePj = ibProcesoAutorizaPjFacade.actualizarEstatusDetalleNominaPorId(ibAutorizaPj.id, BigDecimal.valueOf(EstatusPagosEnum.AUTORIZADO.getId()));
                            if (IbCargaNominaDetallePj == null) {
                                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                                respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                            }
                        } else if (cantidadAprovadores > numAprovacionesReg) {
                            if (flag == 1) {
                                IbCargaNominaPj ibCargaNominaPj = IbCargaNominaPjFacade.find(idPago);
                                ibCargaNominaPj.setEstatus(estaDesAutorizado);
                                IbCargaNominaPjFacade.edit(ibCargaNominaPj);
                            }
                            IbCargaNominaDetallePj = ibProcesoAutorizaPjFacade.actualizarEstatusDetalleNominaPorId(ibAutorizaPj.id, BigDecimal.valueOf(EstatusPagosEnum.PENDIENTE_X_AUTORIZAR.getId()));
                            if (IbCargaNominaDetallePj == null) {
                                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                                respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                            }
                        }
                    } else {
                        respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                        respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                    }
                }
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
    public UtilDTO autorizaPagosPj(List<IbAutorizaPj> ibAutorizaPjsList, Long idUsuario, Integer cantidadAprovadores, BigDecimal idPago, String idCanal, String codigoCanal) {
        //SIEMPRE SE VA A DEVOLVER UN REGISTRO POR ESTA RAZON SE HACE EL .get(0)
        IbServiciosPj ibServiciosPj = ibServiciosPjFacade.findByNombre(SERVICIO_PAGOS_CORPORATIVOS).get(0);
        IbEstatusPagosPj estaAutorizado = new IbEstatusPagosPj(BigDecimal.valueOf(EstatusPagosEnum.AUTORIZADO.getId()));
        IbEstatusPagosPj estaDesAutorizado = new IbEstatusPagosPj(BigDecimal.valueOf(EstatusPagosEnum.PENDIENTE_X_AUTORIZAR.getId()));
        RespuestaDTO respuesta = new RespuestaDTO();
        IbCargaPagosCorpDetPj ibCargaPagosCorpDetPj = null;
        Integer numAprovacionesReg = 0;
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();

        try {

            if (cantidadAprovadores == 1) {

                IbCargaPagosCorpPj ibCargaPagosCorpPj = ibCargaPagosCorpPjFacade.buscarIbCargaPagosCorpPj(idPago.longValue());
                ibCargaPagosCorpPj.setEstatusAutorizacion(estaAutorizado);
                ibCargaPagosCorpPjFacade.edit(ibCargaPagosCorpPj);

                for (IbAutorizaPj ibAutorizaPj : ibAutorizaPjsList) {
                    respuesta = ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibServiciosPj.getId(), ibAutorizaPj.getId().longValue(), BigDecimal.valueOf(idUsuario)).getRespuesta();
                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                        ibCargaPagosCorpDetPj = ibProcesoAutorizaPjFacade.actualizarEstatusDetallePagosPorId(ibAutorizaPj.id, BigDecimal.valueOf(4l));
                        if (ibCargaPagosCorpDetPj == null) {
                            respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                            respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                        }
                    } else {
                        respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                        respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                    }
                }
            } else {
                int flag = 0;
                for (IbAutorizaPj ibAutorizaPj : ibAutorizaPjsList) {
                    flag++;
                    numAprovacionesReg = this.ibSecuenciaAprobacionPjFacade.buscarAprobacionesMultiples(ibServiciosPj.getId(), ibAutorizaPj.id.longValue()).size();
                    respuesta = ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibServiciosPj.getId(), ibAutorizaPj.getId().longValue(), BigDecimal.valueOf(idUsuario)).getRespuesta();
                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                        numAprovacionesReg = numAprovacionesReg + 1;
                        if (numAprovacionesReg == cantidadAprovadores) {
                            if (flag == 1) {
                                IbCargaPagosCorpPj ibCargaPagosCorpPj = ibCargaPagosCorpPjFacade.find(idPago);
                                ibCargaPagosCorpPj.setEstatusAutorizacion(estaAutorizado);
                                ibCargaPagosCorpPjFacade.edit(ibCargaPagosCorpPj);
                            }
                            ibCargaPagosCorpDetPj = ibProcesoAutorizaPjFacade.actualizarEstatusDetallePagosPorId(ibAutorizaPj.id, BigDecimal.valueOf(4l));
                            if (ibCargaPagosCorpDetPj == null) {
                                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                                respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                            }
                        } else if (cantidadAprovadores > numAprovacionesReg) {
                            if (flag == 1) {
                                IbCargaPagosCorpPj ibCargaPagosCorpPj = ibCargaPagosCorpPjFacade.find(idPago);
                                ibCargaPagosCorpPj.setEstatusAutorizacion(estaDesAutorizado);
                                ibCargaPagosCorpPjFacade.edit(ibCargaPagosCorpPj);
                            }
                            ibCargaPagosCorpDetPj = ibProcesoAutorizaPjFacade.actualizarEstatusDetallePagosPorId(ibAutorizaPj.id, BigDecimal.valueOf(3l));
                            if (ibCargaPagosCorpDetPj == null) {
                                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                                respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                            }

                        }
                    } else {
                        respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                        respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                    }

                }

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
    public UtilDTO autorizaBeneficiariosPj(List<IbAutorizaPj> ibAutorizaPjsList, Long idUsuario, Integer cantidadAprovadores, BigDecimal idPago, String idCanal, String codigoCanal) {
        //SIEMPRE SE VA A DEVOLVER UN REGISTRO POR ESTA RAZON SE HACE EL .get(0)
        IbServiciosPj ibServiciosPj = ibServiciosPjFacade.findByNombre(SERVICIO_BENEFICIARIOS).get(0);
        IbEstatusPagosPj estaAutorizado = new IbEstatusPagosPj(BigDecimal.valueOf(EstatusPagosEnum.AUTORIZADO.getId()));
        IbEstatusPagosPj estaDesAutorizado = new IbEstatusPagosPj(BigDecimal.valueOf(EstatusPagosEnum.PENDIENTE_X_AUTORIZAR.getId()));
        RespuestaDTO respuesta = new RespuestaDTO();
        IbBeneficiariosPj ibBeneficiariosPj = null;
        Integer numAprovacionesReg = 0;
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();

        try {
            if (cantidadAprovadores == 1) {
                
                IbCargaBeneficiariosPj ibCargaBeneficiariosPj = ibCargaBeneficiariosPjFacade.buscarIbCargaBeneficiariosPj(idPago.longValue());
                ibCargaBeneficiariosPj.setEstatusAutorizacion(estaAutorizado);
                if (ibCargaBeneficiariosPjFacade.modificarPj(ibCargaBeneficiariosPj).getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    for (IbAutorizaPj ibAutorizaPj : ibAutorizaPjsList) {

                    respuesta = ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibServiciosPj.getId(), ibAutorizaPj.getId().longValue(), BigDecimal.valueOf(idUsuario)).getRespuesta();
                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                        ibBeneficiariosPj = ibProcesoAutorizaPjFacade.actualizarEstatusDetalleBeneficiarioPorId(ibAutorizaPj.id, BigDecimal.valueOf(EstatusPagosEnum.AUTORIZADO.getId()),cantidadAprovadores);                      
                            if (ibBeneficiariosPj == null) {
                                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                                respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                            }
                        } else {
                            respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                            respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                        }
                    }

                } else {
                    respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                    respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                }   

            } else {
                int flag = 0;
                for (IbAutorizaPj ibAutorizaPj : ibAutorizaPjsList) {
                    flag++;
                    numAprovacionesReg = this.ibSecuenciaAprobacionPjFacade.buscarAprobacionesMultiples(ibServiciosPj.getId(), ibAutorizaPj.id.longValue()).size();
                    respuesta = ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibServiciosPj.getId(), ibAutorizaPj.getId().longValue(), BigDecimal.valueOf(idUsuario)).getRespuesta();
                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                        numAprovacionesReg = numAprovacionesReg + 1;
                        if (numAprovacionesReg == cantidadAprovadores) {
                            if (flag == 1) {
                                IbCargaBeneficiariosPj ibCargaBeneficiariosPj = ibCargaBeneficiariosPjFacade.buscarIbCargaBeneficiariosPj(idPago.longValue());
                                ibCargaBeneficiariosPj.setEstatusAutorizacion(estaAutorizado);
                                if(ibCargaBeneficiariosPjFacade.modificarPj(ibCargaBeneficiariosPj).getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                                ibBeneficiariosPj = ibProcesoAutorizaPjFacade.actualizarEstatusDetalleBeneficiarioPorId(ibAutorizaPj.id, BigDecimal.valueOf(EstatusPagosEnum.AUTORIZADO.getId()),cantidadAprovadores);
                                    if (ibBeneficiariosPj == null) {
                                        respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                                        respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                                    }
                                } else {
                                    respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                                    respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                                }
                            }

                        } else if (cantidadAprovadores > numAprovacionesReg) {
                            if (flag == 1) {
                                IbCargaBeneficiariosPj ibCargaBeneficiariosPj = ibCargaBeneficiariosPjFacade.buscarIbCargaBeneficiariosPj(idPago.longValue());
                                ibCargaBeneficiariosPj.setEstatusAutorizacion(estaDesAutorizado);
                                ibCargaBeneficiariosPjFacade.edit(ibCargaBeneficiariosPj);
                            }

                            ibBeneficiariosPj = ibProcesoAutorizaPjFacade.actualizarEstatusDetalleBeneficiarioPorId(ibAutorizaPj.id, BigDecimal.valueOf(EstatusPagosEnum.PENDIENTE_X_AUTORIZAR.getId()),cantidadAprovadores);
                            if (ibBeneficiariosPj == null) {
                                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                                respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                            }
                        }
                    } else {
                        respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                        respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                    }
                }
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
    public UtilDTO autorizaPagosEspecialesPj(List<IbAutorizaPj> ibAutorizaPjsList, Long idUsuario, Integer cantidadAprovadores, BigDecimal idPago, String idCanal, String codigoCanal) {
        //SIEMPRE SE VA A DEVOLVER UN REGISTRO POR ESTA RAZON SE HACE EL .get(0)
        IbServiciosPj ibServiciosPj = ibServiciosPjFacade.findByNombre(SERVICIO_PAGOS_ESPECIALES).get(0);
        IbEstatusPagosPj estaAutorizado = new IbEstatusPagosPj(BigDecimal.valueOf(EstatusPagosEnum.AUTORIZADO.getId()));
        IbEstatusPagosPj estaDesAutorizado = new IbEstatusPagosPj(BigDecimal.valueOf(EstatusPagosEnum.PENDIENTE_X_AUTORIZAR.getId()));
        RespuestaDTO respuesta = new RespuestaDTO();
        IbCargaPagosEspDetPj ibCargaPagosEspDetPj = null;
        Integer numAprovacionesReg = 0;
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();

        try {

            if (cantidadAprovadores == 1) {
                for (IbAutorizaPj ibAutorizaPj : ibAutorizaPjsList) {
                    IbCargaPagosEspPj ibCargaPagosEspPj = ibCargaPagosEspPjFacade.buscarIbCargaPagosEspPj(idPago.longValue());
                    ibCargaPagosEspPj.setEstatus(estaAutorizado);
                    ibCargaPagosEspPjFacade.edit(ibCargaPagosEspPj);

                    respuesta = ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibServiciosPj.getId(), ibAutorizaPj.getId().longValue(), BigDecimal.valueOf(idUsuario)).getRespuesta();
                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                        ibCargaPagosEspDetPj = ibProcesoAutorizaPjFacade.actualizarEstatusDetallePagosEspecialesPorId(ibAutorizaPj.id, BigDecimal.valueOf(4l));
                        if (ibCargaPagosEspDetPj == null) {
                            respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                            respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                        }
                    } else {
                        respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                        respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                    }
                }
            } else {
                int flag = 0;
                for (IbAutorizaPj ibAutorizaPj : ibAutorizaPjsList) {
                    flag++;
                    numAprovacionesReg = this.ibSecuenciaAprobacionPjFacade.buscarAprobacionesMultiples(ibServiciosPj.getId(), ibAutorizaPj.id.longValue()).size();
                    respuesta = ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibServiciosPj.getId(), ibAutorizaPj.getId().longValue(), BigDecimal.valueOf(idUsuario)).getRespuesta();
                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                        numAprovacionesReg = numAprovacionesReg + 1;
                        if (numAprovacionesReg == cantidadAprovadores) {

                            if (flag == 1) {
                                IbCargaPagosEspPj ibCargaPagosEspPj = ibCargaPagosEspPjFacade.find(idPago);
                                ibCargaPagosEspPj.setEstatus(estaAutorizado);
                                ibCargaPagosEspPjFacade.edit(ibCargaPagosEspPj);
                            }

                            ibCargaPagosEspDetPj = ibProcesoAutorizaPjFacade.actualizarEstatusDetallePagosEspecialesPorId(ibAutorizaPj.id, BigDecimal.valueOf(4l));
                            if (ibCargaPagosEspDetPj == null) {
                                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                                respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                            }

                        } else if (cantidadAprovadores > numAprovacionesReg) {
                            if (flag == 1) {
                                IbCargaPagosEspPj ibCargaPagosEspPj = ibCargaPagosEspPjFacade.find(idPago);
                                ibCargaPagosEspPj.setEstatus(estaDesAutorizado);
                                ibCargaPagosEspPjFacade.edit(ibCargaPagosEspPj);
                            }

                            ibCargaPagosEspDetPj = ibProcesoAutorizaPjFacade.actualizarEstatusDetallePagosEspecialesPorId(ibAutorizaPj.id, BigDecimal.valueOf(3l));
                            if (ibCargaPagosEspDetPj == null) {
                                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                                respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                            }
                        }
                    } else {
                        respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                        respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                    }
                }
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
    public UtilDTO autorizaFideicomisoPj(List<IbAutorizaPj> ibAutorizaPjsList, Long idUsuario, Integer cantidadAprovadores, BigDecimal idPago, String idCanal, String codigoCanal) {
        //SIEMPRE SE VA A DEVOLVER UN REGISTRO POR ESTA RAZON SE HACE EL .get(0)
        IbServiciosPj ibServiciosPj = ibServiciosPjFacade.findByNombre(SERVICIO_FIDEICOMISO).get(0);
        IbEstatusPagosPj estaAutorizado = new IbEstatusPagosPj(BigDecimal.valueOf(EstatusPagosEnum.AUTORIZADO.getId()));
        IbEstatusPagosPj estaDesAutorizado = new IbEstatusPagosPj(BigDecimal.valueOf(EstatusPagosEnum.PENDIENTE_X_AUTORIZAR.getId()));
        RespuestaDTO respuesta = new RespuestaDTO();
        Integer numAprovacionesReg = 0;
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        IbFideicomisoDetPj ibFideicomisoDetPj = new IbFideicomisoDetPj();

        try {
            if (cantidadAprovadores == 1) {
                IbFideicomisoPj ibCargaFideicomisoPj = ibFideicomisoPjFacade.buscarIbFideicomisoPj(idPago.longValue());
                ibCargaFideicomisoPj.setEstatus(estaAutorizado);
                ibFideicomisoPjFacade.modificarPj(ibCargaFideicomisoPj);

                for (IbAutorizaPj ibAutorizaPj : ibAutorizaPjsList) {

                    respuesta = ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibServiciosPj.getId(), ibAutorizaPj.getId().longValue(), BigDecimal.valueOf(idUsuario)).getRespuesta();
                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                        ibFideicomisoDetPj = ibProcesoAutorizaPjFacade.actualizarFideicomisoId(ibAutorizaPj.id, BigDecimal.valueOf(4l));
                        if (ibFideicomisoDetPj == null) {
                            respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                            respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                        }
                    } else {
                        respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                        respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                    }
                }

            } else {
                int flag = 0;
                for (IbAutorizaPj ibAutorizaPj : ibAutorizaPjsList) {
                    flag++;
                    numAprovacionesReg = this.ibSecuenciaAprobacionPjFacade.buscarAprobacionesMultiples(ibServiciosPj.getId(), ibAutorizaPj.id.longValue()).size();
                    respuesta = ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibServiciosPj.getId(), ibAutorizaPj.getId().longValue(), BigDecimal.valueOf(idUsuario)).getRespuesta();
                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                        numAprovacionesReg = numAprovacionesReg + 1;
                        if (numAprovacionesReg == cantidadAprovadores) {
                            if (flag == 1) {
                                IbFideicomisoPj ibFideicomisoPjAux = ibFideicomisoPjFacade.buscarIbFideicomisoPj(idPago.longValue());
                                ibFideicomisoPjAux.setEstatus(estaAutorizado);
                                ibFideicomisoPjFacade.edit(ibFideicomisoPjAux);
                            }
                            ibFideicomisoDetPj = ibProcesoAutorizaPjFacade.actualizarFideicomisoId(ibAutorizaPj.id, BigDecimal.valueOf(4l));
                            if (ibFideicomisoDetPj == null) {
                                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                                respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                            }

                        } else if (cantidadAprovadores > numAprovacionesReg) {
                            if (flag == 1) {
                                IbFideicomisoPj ibFideicomisoPjAux = ibFideicomisoPjFacade.buscarIbFideicomisoPj(idPago.longValue());
                                ibFideicomisoPjAux.setEstatus(estaDesAutorizado);
                                ibFideicomisoPjFacade.edit(ibFideicomisoPjAux);
                            }

                            ibFideicomisoDetPj = ibProcesoAutorizaPjFacade.actualizarFideicomisoId(ibAutorizaPj.id, BigDecimal.valueOf(3l));
                            if (ibFideicomisoDetPj == null) {
                                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                                respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                            }
                        }
                    } else {
                        respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                        respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                    }
                }
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
    public UtilDTO autorizaCtasXBeneficiarioPj(List<IbAutorizaPj> ibAutorizaPjsList, Long idUsuario, Integer cantidadAprovadores, BigDecimal idPago, String idCanal, String codigoCanal) {
        //SIEMPRE SE VA A DEVOLVER UN REGISTRO POR ESTA RAZON SE HACE EL .get(0)
        IbServiciosPj ibServiciosPj = ibServiciosPjFacade.findByNombre(SERVICIO_CTAX_X_BENEFICIARIOS).get(0);
        IbEstatusPagosPj estaAutorizado = new IbEstatusPagosPj(BigDecimal.valueOf(EstatusPagosEnum.AUTORIZADO.getId()));
        IbEstatusPagosPj estaDesAutorizado = new IbEstatusPagosPj(BigDecimal.valueOf(EstatusPagosEnum.PENDIENTE_X_AUTORIZAR.getId()));
        RespuestaDTO respuesta = new RespuestaDTO();
        Integer numAprovacionesReg = 0;
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = new IbCtasXBeneficiariosPj();

        try {
            if (cantidadAprovadores == 1) {

                for (IbAutorizaPj ibAutorizaPj : ibAutorizaPjsList) {

                    respuesta = ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibServiciosPj.getId(), ibAutorizaPj.getId().longValue(), BigDecimal.valueOf(idUsuario)).getRespuesta();
                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                        ibCtasXBeneficiariosPj = ibCtasXBeneficiariosPjFacade.find(ibAutorizaPj.getId().longValue());
                        ibCtasXBeneficiariosPj.setEstatusAutorizacion(estaAutorizado);
                        ibCtasXBeneficiariosPj.setEstatusCuenta(Short.valueOf(EstatusCuentaEnum.ACTIVO.getId()));
                        respuesta = ibCtasXBeneficiariosPjFacade.modificarPj(ibCtasXBeneficiariosPj);
                        if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && !respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                            respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                            respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                        }
                    } else {
                        respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                        respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                    }
                }

            } else {

                for (IbAutorizaPj ibAutorizaPj : ibAutorizaPjsList) {

                    numAprovacionesReg = this.ibSecuenciaAprobacionPjFacade.buscarAprobacionesMultiples(ibServiciosPj.getId(), ibAutorizaPj.id.longValue()).size();
                    respuesta = ibSecuenciaAprobacionPjDAO.insertarSecuenciaDeAprobacion(ibServiciosPj.getId(), ibAutorizaPj.getId().longValue(), BigDecimal.valueOf(idUsuario)).getRespuesta();
                    if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) && respuesta.getDescripcion().equals(DESCRIPCION_RESPUESTA_EXITOSO)) {
                        numAprovacionesReg = numAprovacionesReg + 1;
                        if (numAprovacionesReg == cantidadAprovadores) {
                            ibCtasXBeneficiariosPj = ibCtasXBeneficiariosPjFacade.find(ibAutorizaPj.getId().longValue());
                            ibCtasXBeneficiariosPj.setEstatusAutorizacion(estaAutorizado);
                            ibCtasXBeneficiariosPj.setEstatusCuenta(Short.valueOf(EstatusCuentaEnum.ACTIVO.getId()));
                            respuesta = ibCtasXBeneficiariosPjFacade.modificarPj(ibCtasXBeneficiariosPj);

                        } else if (cantidadAprovadores > numAprovacionesReg) {
                            ibCtasXBeneficiariosPj = ibCtasXBeneficiariosPjFacade.find(ibAutorizaPj.getId().longValue());
                            ibCtasXBeneficiariosPj.setEstatusAutorizacion(estaDesAutorizado);
                            respuesta = ibCtasXBeneficiariosPjFacade.modificarPj(ibCtasXBeneficiariosPj);
                        }

                    } else {

                        respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                        respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA);
                    }
                }
            }

        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR IbCtasXBeneficiario: ")
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCtasXBeneficiario: ")
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        utilDTO.setRespuesta(respuesta);
        utilDTO.setResulados(resultado);
        return utilDTO;

    }

}
