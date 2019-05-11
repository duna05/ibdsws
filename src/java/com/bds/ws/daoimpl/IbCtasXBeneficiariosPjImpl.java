/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbCtasXBeneficiariosPjDAO;
import com.bds.ws.dto.IbCtasXBeneficiariosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusPagosEnum;
import com.bds.ws.facade.IbBeneficiariosPjFacade;
import com.bds.ws.facade.IbCtasXBeneficiariosPjFacade;
import com.bds.ws.model.IbBeneficiariosPj;
import com.bds.ws.model.IbCtasXBeneficiariosPj;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.MENSAJE;
import java.math.BigDecimal;
import java.util.ArrayList;
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
 * @author ledwin.belen
 */
@Named("IbCtasXBeneficiariosPjDAO")
@Stateless
public class IbCtasXBeneficiariosPjImpl implements IbCtasXBeneficiariosPjDAO {

    @EJB
    private IbCtasXBeneficiariosPjFacade ibCtasXBeneficiariosPjFacade;

    @EJB
    private IbBeneficiariosPjFacade ibBeneficiariosPjFacade;

    private static final Logger logger = Logger.getLogger(IbCtasXBeneficiariosPjImpl.class.getName());

    @Override
    public IbCtasXBeneficiariosPjDTO listarCtasBeneficiarioXEmpresa(BigDecimal idEmpresa, Long idBeneficiario, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        IbCtasXBeneficiariosPjDTO ibCtasXBeneficiariosPjDTO = new IbCtasXBeneficiariosPjDTO();
        List<IbCtasXBeneficiariosPj> list = new ArrayList<IbCtasXBeneficiariosPj>();

        try {
            if (idEmpresa != null && idBeneficiario != null) {
                list = ibCtasXBeneficiariosPjFacade.listarCtasBeneficiarioXEmpresa(idEmpresa, idBeneficiario);
                ibCtasXBeneficiariosPjDTO.setIbCtasXBeneficiariosPjsList(list);
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listarCtasBeneficiarioXEmpresa: ")
                    .append("CTA-").append("")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            ibCtasXBeneficiariosPjDTO.setRespuestaDTO(respuesta);
        }
        return ibCtasXBeneficiariosPjDTO;
    }

    @Override
    public UtilDTO eliminarCtasBeneficiarioXEmpresa(BigDecimal idEmpresa, Long idBeneficiario, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        UtilDTO utilDTO = new UtilDTO();
        long estatusAutorizacion;
        boolean validador = true;
        try {
            if (idEmpresa != null && idBeneficiario != null) {
                List<IbCtasXBeneficiariosPj> list = ibCtasXBeneficiariosPjFacade.listarCtasBeneficiarioXEmpresa(idEmpresa, idBeneficiario);
                IbBeneficiariosPj ibBeneficiariosPj = ibBeneficiariosPjFacade.buscarIbBeneficiariosPorEmpresasPj(idEmpresa, idBeneficiario);
                estatusAutorizacion = ibBeneficiariosPj.getEstatusAutorizacion().getId().longValue();
                if (estatusAutorizacion == EstatusPagosEnum.CARGADO.getId()
                        || estatusAutorizacion == EstatusPagosEnum.PENDIENTE_X_AUTORIZAR.getId()
                        || estatusAutorizacion == EstatusPagosEnum.PENDIENTE_FIRMA.getId()
                        || estatusAutorizacion == EstatusPagosEnum.AUTORIZADO.getId()
                        || estatusAutorizacion == EstatusPagosEnum.DETENIDO.getId()
                        || estatusAutorizacion == EstatusPagosEnum.REACTIVADA.getId()
                        || estatusAutorizacion == EstatusPagosEnum.PENDIENTE_X_PAGO.getId()) {
                    validador = false;

                } else {
                    for (IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj : list) {
                        estatusAutorizacion = ibCtasXBeneficiariosPj.getEstatusAutorizacion().getId().longValue();
                        if (estatusAutorizacion == EstatusPagosEnum.CARGADO.getId()
                                || estatusAutorizacion == EstatusPagosEnum.PENDIENTE_X_AUTORIZAR.getId()
                                || estatusAutorizacion == EstatusPagosEnum.PENDIENTE_FIRMA.getId()
                                || estatusAutorizacion == EstatusPagosEnum.AUTORIZADO.getId()
                                || estatusAutorizacion == EstatusPagosEnum.DETENIDO.getId()
                                || estatusAutorizacion == EstatusPagosEnum.REACTIVADA.getId()
                                || estatusAutorizacion == EstatusPagosEnum.PENDIENTE_X_PAGO.getId()) {
                            validador = false;
                            break;
                        }
                    }
                }
                if (validador) {
                    int i = 0;
                    ibBeneficiariosPj.setEstatusBeneficiario((short) 0);
                    ibBeneficiariosPjFacade.modificarPj(ibBeneficiariosPj);
                    for (IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj : list) {
                        list.get(i).setEstatusCuenta((short) 0);
                        ibCtasXBeneficiariosPjFacade.modificarPj(ibCtasXBeneficiariosPj);
                        i++;
                    }
                    resultados.put(MENSAJE, true);
                    utilDTO.setResulados(resultados);
                } else {
                    resultados.put(MENSAJE, false);
                    utilDTO.setResulados(resultados);
                }
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN eliminarCtasBeneficiarioXEmpresa: ")
                    .append("CTA-").append("")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }

    @Override
    public IbCtasXBeneficiariosPjDTO listarCtasBeneficiarioXEmpresaAutoriza(BigDecimal idEmpresa, Long idUsuarioAurorizado, Long idServicio, Long idBeneficiario, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        IbCtasXBeneficiariosPjDTO ibCtasXBeneficiariosPjDTO = new IbCtasXBeneficiariosPjDTO();

        try {

            ibCtasXBeneficiariosPjDTO = ibCtasXBeneficiariosPjFacade.listarCtasBeneficiarioXEmpresaAutoriza(idEmpresa, idUsuarioAurorizado, idServicio, idBeneficiario);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listarCtasBeneficiarioXEmpresa: ")
                    .append("CTA-").append("")
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            ibCtasXBeneficiariosPjDTO.setRespuestaDTO(respuesta);
        }
        return ibCtasXBeneficiariosPjDTO;
    }

}
