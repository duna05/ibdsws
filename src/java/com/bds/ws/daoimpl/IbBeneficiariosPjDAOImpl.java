/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.CuentaDAO;
import com.bds.ws.dao.DelSurDAO;
import com.bds.ws.dao.IbBeneficiariosPjDAO;
import com.bds.ws.dto.DelSurDTO;
import com.bds.ws.dto.IbBeneficiariosPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.CuentaDelSurEnum;
import com.bds.ws.enumerated.CuentaPrincipalEnum;
import com.bds.ws.enumerated.CuentaPropiaEnum;
import com.bds.ws.enumerated.EstatusBeneficiarioEnum;
import com.bds.ws.enumerated.EstatusCuentaEnum;
import com.bds.ws.enumerated.EstatusPagosEnum;
import com.bds.ws.enumerated.FormaDePagoEnum;
import com.bds.ws.enumerated.ProductoEnum;
import com.bds.ws.facade.IbBeneficiariosPjFacade;
import com.bds.ws.facade.IbCargaPagosCorpDetPjFacade;
import com.bds.ws.facade.IbCtasXBeneficiariosPjFacade;
import com.bds.ws.facade.IbEmpresasFacade;
import com.bds.ws.facade.IbUsuariosPjFacade;
import com.bds.ws.model.IbBeneficiariosPj;
import com.bds.ws.model.IbCargaPagosCorpDetPj;
import com.bds.ws.model.IbCtasXBeneficiariosPj;
import com.bds.ws.model.IbEmpresas;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.model.IbUsuariosPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO_SP;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
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
 * @author christian.gutierrez
 */
@Named("IbBeneficiariosPjDAO")
@Stateless
public class IbBeneficiariosPjDAOImpl extends DAOUtil implements IbBeneficiariosPjDAO {

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbBeneficiariosPjDAOImpl.class.getName());

    @EJB
    private IbBeneficiariosPjFacade ibBeneficiariosPjFacade;

    @EJB
    private IbCtasXBeneficiariosPjFacade ibCtasXBeneficiariosPjFacade;

    @EJB
    private IbCargaPagosCorpDetPjFacade ibCargaPagosCorpDetPjFacade;

    @EJB
    private IbEmpresasFacade ibEmpresasFacade;

    @EJB
    private CuentaDAO cuentaDAO;

    @EJB
    private DelSurDAO delSurDAO;

    @EJB
    private IbUsuariosPjFacade ibUsuariosPjFacade;

    public IbEmpresasFacade getIbEmpresasFacade() {
        return ibEmpresasFacade;
    }

    public void setIbEmpresasFacade(IbEmpresasFacade ibEmpresasFacade) {
        this.ibEmpresasFacade = ibEmpresasFacade;
    }

    public IbCargaPagosCorpDetPjFacade getIbCargaPagosCorpDetPjFacade() {
        return ibCargaPagosCorpDetPjFacade;
    }

    public void setIbCargaPagosCorpDetPjFacade(IbCargaPagosCorpDetPjFacade ibCargaPagosCorpDetPjFacade) {
        this.ibCargaPagosCorpDetPjFacade = ibCargaPagosCorpDetPjFacade;
    }

    public IbBeneficiariosPjFacade getIbBeneficiariosPjFacade() {
        return ibBeneficiariosPjFacade;
    }

    public void setIbBeneficiariosPjFacade(IbBeneficiariosPjFacade ibBeneficiariosPjFacade) {
        this.ibBeneficiariosPjFacade = ibBeneficiariosPjFacade;
    }

    public IbCtasXBeneficiariosPjFacade getIbCtasXBeneficiariosPjFacade() {
        return ibCtasXBeneficiariosPjFacade;
    }

    public void setIbCtasXBeneficiariosPjFacade(IbCtasXBeneficiariosPjFacade ibCtasXBeneficiariosPjFacade) {
        this.ibCtasXBeneficiariosPjFacade = ibCtasXBeneficiariosPjFacade;
    }

    @Override
    public List<IbBeneficiariosPjDTO> listarIbBeneficiariosPjDTO(Long idCargaBeneficiario, Short estatusCarga, String idCanal, String codigoCanal) {
        List<IbBeneficiariosPjDTO> ibBeneficiariosPjDTOList = new ArrayList<>();
        IbBeneficiariosPjDTO ibBeneficiariosPjDTO;
        try {
            List<IbBeneficiariosPj> ibBeneficiariosPjList = ibBeneficiariosPjFacade.listarIbBeneficiariosPj(idCargaBeneficiario, estatusCarga);

            if (!ibBeneficiariosPjList.isEmpty()) {
                for (IbBeneficiariosPj ibBeneficiariosPj : ibBeneficiariosPjList) {
                    ibBeneficiariosPjDTO = new IbBeneficiariosPjDTO();
                    ibBeneficiariosPjDTO.setIbBeneficiariosPj(ibBeneficiariosPj);
                    ibBeneficiariosPjDTO.setIbCtasXBeneficiariosPj(ibBeneficiariosPj.getIbCtasXBeneficiariosPjList());
                    ibBeneficiariosPjDTOList.add(ibBeneficiariosPjDTO);
                }
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO en listarIbBeneficiariosPjDTO: ")
                    .append("ID-CH- ").append(idCanal)
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
        }
        return ibBeneficiariosPjDTOList;
    }

    /**
     * Metodo para insertar una nueva cabecera de proceso de carga de pagos
     * especiales Oracle 11
     *
     * @param ibBeneficiariosPj objeto completo de IbBeneficiariosPjDTO
     * @param codigoCanal String del canal a utilizar
     * @return UtilDTO
     */
    @Override
    public UtilDTO insertarIbBeneficiariosPj(IbBeneficiariosPj ibBeneficiariosPj, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();

        try {
            if (ibBeneficiariosPj != null) {

                respuesta = this.ibBeneficiariosPjFacade.crearPJ(ibBeneficiariosPj);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL INSERTAR insertarIbBeneficiariosPj");
                }
                resultado.put("IbBeneficiariosPj", ibBeneficiariosPj);
                utilDTO.setRespuesta(respuesta);
                utilDTO.setResulados(resultado);
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarIbBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarIbBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;

    }

    /**
     * Metodo para modificar una nueva cabecera de proceso de carga de pagos
     * especiales Oracle 11
     *
     * @param idBeneficiario de tipo Long, identificador del beneficiario a
     * cambiar de estatus
     * @param ibEstatusPagosPjDTO objeto completo de tipo IbEstatusPagosPjDTO
     * que establecera el nuevo estatus del pago
     * @param codigoCanal String
     * @return UtilDTO
     */
    @Override
    public UtilDTO modificarEstatusIbBeneficiariosPj(Long idBeneficiario, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        try {
            if (ibEstatusPagosPjDTO != null) {
                int resultado = ibBeneficiariosPjFacade.modificarEstatusDetalle(idBeneficiario, ibEstatusPagosPjDTO.getIbEstatusPagosPj().getId());
                if (resultado <= 0) {
                    throw new Exception("ERROR AL MODIFICAR IbBeneficiariosPj");
                }
                respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
                respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
                utilDTO.setRespuesta(respuesta);
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR modificarEstatusIbBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR modificarEstatusIbBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN MODIFICAR modificarEstatusIbBeneficiariosPj: ")
//                    .append("-CH- ").append(codigoCanal)
//                    .append("-DT- ").append(new Date())
//                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    @Override
    public List<IbBeneficiariosPjDTO> listarIbBeneficiariosPorEmpresaPjDTO(String idEmpresa, Short estatusBeneficiario, String idCanal, String codigoCanal) {
        IbBeneficiariosPjDTO ibBeneficiariosPjDTO = null;
        List<IbBeneficiariosPjDTO> ibBeneficiariosPjDTOList = new ArrayList<>();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            BigDecimal idCanalD = new BigDecimal(idCanal);
            BigDecimal idEmpresaD = new BigDecimal(idEmpresa);
            List<IbBeneficiariosPj> ibBeneficiariosPjList = ibBeneficiariosPjFacade.listarIbBeneficiariosPorEmpresaPj(idEmpresaD, estatusBeneficiario);
            if (!ibBeneficiariosPjList.isEmpty()) {
                for (IbBeneficiariosPj ibBeneficiariosPj : ibBeneficiariosPjList) {
                    ibBeneficiariosPjDTO = new IbBeneficiariosPjDTO();
                    ibBeneficiariosPjDTO.setIbBeneficiariosPj(ibBeneficiariosPj);
                    ibBeneficiariosPjDTO.setIbCtasXBeneficiariosPj(ibBeneficiariosPj.getIbCtasXBeneficiariosPjList());
                    ibBeneficiariosPjDTOList.add(ibBeneficiariosPjDTO);
                }
            }

        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN consultarIbBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultarIbBeneficiariosPj: ")
                    //.append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            ibBeneficiariosPjDTO.setRespuestaDTO(respuesta);
        }
        return ibBeneficiariosPjDTOList;
    }

    @Override
    public List<IbBeneficiariosPjDTO> listaIbBeneficiariosPorCtaEmpresaPjDTO(BigDecimal idEmpresa, BigDecimal idCtaDelSur, BigDecimal idCtaPro, Short estatusCta, Short estatusBeneficiario, String idCanal, String codigoCanal) {
        IbBeneficiariosPjDTO ibBeneficiariosPjDTO = null;
        List<IbBeneficiariosPjDTO> ibBeneficiariosPjDTOList = new ArrayList<>();

        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            List<IbBeneficiariosPj> ibBeneficiariosPjList = ibBeneficiariosPjFacade.listaIbBeneficiariosPorCuentaEmpresa(idEmpresa, idCtaDelSur, idCtaPro, estatusCta, estatusBeneficiario);

            if (!ibBeneficiariosPjList.isEmpty()) {
                for (IbBeneficiariosPj ibBeneficiariosPj : ibBeneficiariosPjList) {
                    ibBeneficiariosPjDTO = new IbBeneficiariosPjDTO();
                    ibBeneficiariosPjDTO.setIbBeneficiariosPj(ibBeneficiariosPj);
                    ibBeneficiariosPjDTO.setIbCtasXBeneficiariosPj(ibBeneficiariosPj.getIbCtasXBeneficiariosPjList());
                    ibBeneficiariosPjDTOList.add(ibBeneficiariosPjDTO);
                }
            }

        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN consultarIbBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultarIbBeneficiariosPj: ")
                    //.append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            ibBeneficiariosPjDTO.setRespuestaDTO(respuesta);
        }
        return ibBeneficiariosPjDTOList;

    }

    @Override
    public UtilDTO modificarEstatusIbBeneficiariosPorEmpresaPjDTO(String idEmpresa, Long idbCargaPagosCorpDetPjDTO, Long idBeneficiario, Short estatusBeneficiario, Short estatusAutorizacion, String idCanal, String codigoCanal) {

        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        boolean respuestaEnum = true;
        IbBeneficiariosPj ibBeneficiariosPj = new IbBeneficiariosPj();
        IbCargaPagosCorpDetPj ibCargaPagosCorpDetPj = new IbCargaPagosCorpDetPj();
        try {
            ibBeneficiariosPj = ibBeneficiariosPjFacade.find(idBeneficiario);
            ibCargaPagosCorpDetPj = ibCargaPagosCorpDetPjFacade.find(idbCargaPagosCorpDetPjDTO);
            String nroIdentificacionCliente = ibBeneficiariosPj.getNroIdentificacionCliente();
            //String codigoClienteAbanks = Objects.toString(ibCargaPagosCorpDetPj.getCodigoClienteAbanks()) ;
            long codigoClienteAbanks = ibCargaPagosCorpDetPj.getCodigoClienteAbanks();
            if (ibCargaPagosCorpDetPjFacade.consultarEstatus(nroIdentificacionCliente, codigoClienteAbanks)) {
                if (ibBeneficiariosPj != null) {

                    ibBeneficiariosPj.setEstatusBeneficiario(Short.valueOf(EstatusBeneficiarioEnum.INACTIVO.getId()));
                    ibBeneficiariosPj.setEstatusAutorizacion(new IbEstatusPagosPj(new BigDecimal(EstatusPagosEnum.CARGADO.getId())));

                    for (IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj : ibBeneficiariosPj.getIbCtasXBeneficiariosPjList()) {
                        ibCtasXBeneficiariosPj.setEstatusAutorizacion(new IbEstatusPagosPj(new BigDecimal(EstatusPagosEnum.CARGADO.getId())));
                        ibCtasXBeneficiariosPj.setEstatusCuenta(Short.valueOf(EstatusCuentaEnum.INACTIVO.getId()));
                    }
                }
            }

        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR modificarEstatusIbBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR modificarEstatusIbBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN MODIFICAR modificarEstatusIbBeneficiariosPj: ")
//                    .append("-CH- ").append(codigoCanal)
//                    .append("-DT- ").append(new Date())
//                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     *
     * @param nroCuenta
     * @param identBeneficiario
     * @param codigoCanal
     * @return
     */
    @Override
    public UtilDTO validarCtaAsociarBeneficiario(String nroCuenta, String identBeneficiario, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        UtilDTO utilDTO = new UtilDTO();
        DelSurDTO delSurDTO = new DelSurDTO();

        try {
            if (BDSUtil.isCuentaDelSur(nroCuenta)) {
                utilDTO = cuentaDAO.validarCtaBeneficiario(nroCuenta.substring(10, 20), identBeneficiario, codigoCanal);
            } else {
                delSurDTO = delSurDAO.listadoBancos(codigoCanal);
                delSurDTO.getBancos().removeIf(p -> !p.getCodigoBanco().equals(nroCuenta.substring(0, 4)));
                resultados.put(NOMBRE_BANCO, delSurDTO.getBancos().get(0).getNombreBanco());
                utilDTO.setResulados(resultados);
                utilDTO.setRespuesta(delSurDTO.getRespuesta());
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarCtaAsociarBeneficiario: ")
                    .append("CTA-").append("")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            utilDTO.setRespuesta(respuesta);
        } finally {
            this.cerrarConexion(codigoCanal);
        }

        return utilDTO;
    }

    /**
     *
     * @param idBeneficiario
     * @param idEmpresa
     * @param nroCtaBeneficiario
     * @param codigoUsuarioCarga
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @Override
    public UtilDTO insertarIbCuentasPorBeneficiariosPjDTO(Long idBeneficiario, String idEmpresa, String nroCtaBeneficiario, String codigoUsuarioCarga, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        boolean exist = false;
        List<IbCtasXBeneficiariosPj> list = new ArrayList<IbCtasXBeneficiariosPj>();

        try {
            if (idBeneficiario != null) {

                IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = new IbCtasXBeneficiariosPj();
                IbBeneficiariosPj ibBeneficiariosPj = ibBeneficiariosPjFacade.buscarIbBeneficiariosPorEmpresasPj(new BigDecimal(idEmpresa), idBeneficiario);
                list = ibBeneficiariosPj.getIbCtasXBeneficiariosPjList();

//                boolean exist = list.stream().filter(p -> p.getNroCuentaBeneficiario().equals(nroCtaBeneficiario)).findFirst().isPresent();
                for (IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj1 : list) {

                    if (ibCtasXBeneficiariosPj1.getNroCuentaBeneficiario().equals(nroCtaBeneficiario)) {
                        exist = true;
                        break;
                    }
                }

                if (exist) {
                    resultado.put(MENSAJE, false);
                    utilDTO.setResulados(resultado);
                } else {
                    IbUsuariosPj ibUsuariosPj = new IbUsuariosPj();
                    ibUsuariosPj.setId(new BigDecimal(codigoUsuarioCarga));
                    IbEstatusPagosPj ibEstatusPagosPj = new IbEstatusPagosPj();
                    ibEstatusPagosPj.setId(new BigDecimal(EstatusPagosEnum.CARGADO.getId()));
                    IbEmpresas ibEmpresas = ibEmpresasFacade.find(new BigDecimal(idEmpresa));
                    String identificacion = ibEmpresas.getTipoRif() + ibEmpresas.getNroRif();

                    if (ibBeneficiariosPj.getIbCtasXBeneficiariosPjList().isEmpty()) {
                        ibCtasXBeneficiariosPj.setPrincipal(Short.valueOf(CuentaPrincipalEnum.PRINCIPAL.getId()));
                    } else {
                        ibCtasXBeneficiariosPj.setPrincipal(Short.valueOf(CuentaPrincipalEnum.SECUNDARIA.getId()));
                    }
                    if (ibBeneficiariosPj.getNroIdentificacionCliente().equalsIgnoreCase(identificacion)) {
                        ibCtasXBeneficiariosPj.setCuentaPropia(Short.valueOf(CuentaPropiaEnum.PROPIA.getId()));
                    } else {
                        ibCtasXBeneficiariosPj.setCuentaPropia(Short.valueOf(CuentaPropiaEnum.OTROSBANCOS.getId()));
                    }

                    if (BDSUtil.isCuentaDelSur(nroCtaBeneficiario)) {
                        ibCtasXBeneficiariosPj.setCuentaDelSur(Short.valueOf(CuentaDelSurEnum.DELSUR.getId()));
                    } else {
                        ibCtasXBeneficiariosPj.setCuentaDelSur(Short.valueOf(CuentaDelSurEnum.OTROSBANCOS.getId()));
                    }
                    ibCtasXBeneficiariosPj.setCodigoUsuarioCarga(ibUsuariosPj);
                    ibCtasXBeneficiariosPj.setIdEmpresa(ibEmpresas);
                    ibCtasXBeneficiariosPj.setNroCuentaBeneficiario(nroCtaBeneficiario);
                    ibCtasXBeneficiariosPj.setEstatusCuenta(Short.valueOf(EstatusCuentaEnum.INACTIVO.getId()));
                    ibCtasXBeneficiariosPj.setEstatusAutorizacion(ibEstatusPagosPj);
                    ibCtasXBeneficiariosPj.setIdBeneficiario(ibBeneficiariosPj);
                    ibCtasXBeneficiariosPj.setProducto(Short.valueOf(ProductoEnum.CUENTA.getId()));
                    ibCtasXBeneficiariosPj.setFormaPago(FormaDePagoEnum.DELSUR.getId());
                    ibBeneficiariosPj.getIbCtasXBeneficiariosPjList().add(ibCtasXBeneficiariosPj);
                    ibBeneficiariosPjFacade.modificarPj(ibBeneficiariosPj);
                    resultado.put(MENSAJE, true);
                    utilDTO.setResulados(resultado);
                }

            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarIbCuentasPorBeneficiariosPjDTO: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarIbCuentasPorBeneficiariosPjDTO: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }

    @Override
    public IbBeneficiariosPjDTO consultarBeneficiariosPorEmpresaPj(String idEmpresa, Long ibBeneficiarios, String estatusCta, String codigoCanal) {
        IbBeneficiariosPjDTO ibBeneficiariosPjFinal = new IbBeneficiariosPjDTO();
        List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjList = new ArrayList<>();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            BigDecimal idEmpresaD = new BigDecimal(idEmpresa);
            IbBeneficiariosPj ibBeneficiariosPjConsulta = ibBeneficiariosPjFacade.buscarCuentasBeneficiarioPorEmpresasPj(idEmpresaD, ibBeneficiarios);
            if (ibBeneficiariosPjConsulta != null) {
                ibBeneficiariosPjFinal.setIbBeneficiariosPj(ibBeneficiariosPjConsulta);
                if (estatusCta == null || estatusCta == "") {
                    for (IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj : ibBeneficiariosPjConsulta.getIbCtasXBeneficiariosPjList()) {
                        Short estatusCuenta = ibCtasXBeneficiariosPj.getEstatusCuenta();
                        Short estatusCuentaEnum = new Short(EstatusCuentaEnum.ACTIVO.getId());
                        if (estatusCuenta.equals(estatusCuentaEnum)) {
                            ibCtasXBeneficiariosPjList.add(ibCtasXBeneficiariosPj);
                        }
                    }
                }else{
                    for (IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj : ibBeneficiariosPjConsulta.getIbCtasXBeneficiariosPjList()) {
                        Short estatusCuenta = ibCtasXBeneficiariosPj.getEstatusCuenta();                        
                        if (estatusCuenta.equals(Short.valueOf(estatusCta))) {
                            ibCtasXBeneficiariosPjList.add(ibCtasXBeneficiariosPj);
                        }
                    }
                }

                ibBeneficiariosPjFinal.setIbCtasXBeneficiariosPj(ibCtasXBeneficiariosPjList);
            }
            respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN consultarIbBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultarIbBeneficiariosPj: ")
                    //.append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return ibBeneficiariosPjFinal;
    }

    @Override
    public IbBeneficiariosPjDTO consultarBeneficiariosPorNroIdentificacionClientePj(BigDecimal idEmpresa, String nroIdentificacionCliente, String codigoCanal) {
        IbBeneficiariosPjDTO ibBeneficiariosPjFinal = new IbBeneficiariosPjDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {

            IbBeneficiariosPj ibBeneficiariosPjConsulta = ibBeneficiariosPjFacade.buscarCuentasBeneficiarioPornroIdentificacionClientePj(idEmpresa, nroIdentificacionCliente);
            if (ibBeneficiariosPjConsulta != null) {
                ibBeneficiariosPjFinal.setIbBeneficiariosPj(ibBeneficiariosPjConsulta);
                ibBeneficiariosPjFinal.setIbCtasXBeneficiariosPj(ibBeneficiariosPjConsulta.getIbCtasXBeneficiariosPjList());
            }
            respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN consultarIbBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultarIbBeneficiariosPj: ")
                    //.append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return ibBeneficiariosPjFinal;
    }

    @Override
    public IbBeneficiariosPjDTO listarIbBeneficiariosCtaAutorizarPj(BigDecimal idEmpresa, Long idUsuarioAurorizado, Long idServicio, String StatusAConsultar, String idCanal, String codigoCanal) {
        IbBeneficiariosPjDTO ibBeneficiariosPjDTO = new IbBeneficiariosPjDTO();
        try {
            ibBeneficiariosPjDTO = this.ibBeneficiariosPjFacade.listarIbBeneficiariosCtaAutorizarPj(idEmpresa, idUsuarioAurorizado, idServicio, StatusAConsultar);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO en listarIbCargaBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibBeneficiariosPjDTO == null || ibBeneficiariosPjDTO.getRespuestaDTO() == null) {
                ibBeneficiariosPjDTO = new IbBeneficiariosPjDTO();
                ibBeneficiariosPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibBeneficiariosPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibBeneficiariosPjDTO;
    }

}
