/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbClavesOperacionesEspecialesDAO;
import com.bds.ws.dto.IbClavesOperacionesEspecialesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.ClavesOperacionesEspecialesEnum;
import com.bds.ws.facade.IbClavesOpEspecialesFacade;
import com.bds.ws.facade.IbHistClavesOpEspecialesFacade;
import com.bds.ws.facade.IbParametrosFacade;
import com.bds.ws.model.IbCanal;
import com.bds.ws.model.IbClavesOpEspeciales;
import com.bds.ws.model.IbHistClavesOpEspeciales;
import com.bds.ws.model.IbUsuarios;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 *
 * @author ledwin.belen
 */
@Named("IbClavesOperacionesEspecialesDAO")
@Stateless
public class IbClavesOperacionesEspecialesDAOImpl extends DAOUtil implements IbClavesOperacionesEspecialesDAO {

    public final static String ESTATUS_CLAVE_OP_ESPECIALES = "pnw.intentos.fallidos.clave.OP";

    private static final Logger logger = Logger.getLogger(IbClavesOperacionesEspecialesDAOImpl.class.getName());

    @EJB
    IbClavesOpEspecialesFacade ibClavesOpEspecialesFacade;

    @EJB
    IbHistClavesOpEspecialesFacade ibHistClavesOpEspecialesFacade;

    @EJB
    private IbParametrosFacade ibParametrosFacade;

    /**
     * Métod para validar la clave de operaciones especiales porporcionada por
     * el usuario
     *
     * @param idUsuario
     * @param claveOP
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @Override
    public UtilDTO validarClaveOP(String idUsuario, String claveOP, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultados = new HashMap();
        IbClavesOperacionesEspecialesDTO ibClavesOperacionesEspecialesDTO = new IbClavesOperacionesEspecialesDTO();
        IbClavesOpEspeciales ibClavesOpEspeciales = new IbClavesOpEspeciales();

        try {
            ibClavesOperacionesEspecialesDTO = ibClavesOpEspecialesFacade.getClavesOperacionesEspeciales(idUsuario, claveOP, codigoCanal);
            if (!ibClavesOperacionesEspecialesDTO.getRespuesta().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new NoResultException();
            }
            //exitoso
            if (claveOP.equals(ibClavesOperacionesEspecialesDTO.getIbClavesOpEspeciales().getClaveEspecial()) && String.valueOf(ibClavesOperacionesEspecialesDTO.getIbClavesOpEspeciales().getEstatus()).equals(ClavesOperacionesEspecialesEnum.ACTIVO.getId())) {
                resultados.put(ESTADO_CLAVE_OP, ClavesOperacionesEspecialesEnum.ACTIVO.getDescripcion());
                ibClavesOpEspeciales = ibClavesOperacionesEspecialesDTO.getIbClavesOpEspeciales();
                //SE SETEA A 0 CUANDO ES VALIDADO DE MANERA CORRECTA LA CLAVE ESPECIAL
                ibClavesOpEspeciales.setIntentosFallidos((short) 0);
                ibClavesOpEspecialesFacade.modificarPj(ibClavesOpEspeciales);
            } //bloqueado
            else if (claveOP.equals(ibClavesOperacionesEspecialesDTO.getIbClavesOpEspeciales().getClaveEspecial()) && String.valueOf(ibClavesOperacionesEspecialesDTO.getIbClavesOpEspeciales().getEstatus()).equals(ClavesOperacionesEspecialesEnum.BLOQUEADO.getId())) {
                resultados.put(ESTADO_CLAVE_OP, ClavesOperacionesEspecialesEnum.BLOQUEADO.getDescripcion());
            } //la clave es incorrecta
            else if (!claveOP.equals(ibClavesOperacionesEspecialesDTO.getIbClavesOpEspeciales().getClaveEspecial())) {
                ibClavesOpEspeciales = ibClavesOperacionesEspecialesDTO.getIbClavesOpEspeciales();
                int intentos = ibClavesOpEspeciales.getIntentosFallidos() + 1;
                ibClavesOpEspeciales.setIntentosFallidos((short) intentos);
                if (intentos == Integer.parseInt(this.parametro(ESTATUS_CLAVE_OP_ESPECIALES, codigoCanal))) {
                    ibClavesOpEspeciales.setEstatus(Short.parseShort(ClavesOperacionesEspecialesEnum.BLOQUEADO.getId()));
                    ibClavesOpEspeciales.setIntentosFallidos((short) intentos);
                }
                ibClavesOpEspecialesFacade.modificarPj(ibClavesOpEspeciales);
                resultados.put(ESTADO_CLAVE_OP, ClavesOperacionesEspecialesEnum.INCORRECTO.getDescripcion());
            }
        } catch (NoResultException e) {
            logger.error(new StringBuilder("ERROR JPA EN getClavesOperacionesEspeciales: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta = ibClavesOperacionesEspecialesDTO.getRespuesta();
            resultados.put(ESTADO_CLAVE_OP, ClavesOperacionesEspecialesEnum.NO_REGISTRADO.getDescripcion());
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN validarClaveOP: ")
                    .append("CTA-").append("")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            //no tiene clave registrada

            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            utilDTO.setResulados(resultados);
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }

    /**
     * Método para crear o modificar la clave de operaciones especiales
     *
     * @param idUsuario
     * @param claveOP
     * @param idUsuarioCarga
     * @param sw
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @Override
    public UtilDTO insertarActualizarClaveOP(String idUsuario, String claveOP, String idUsuarioCarga, boolean sw, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultados = new HashMap();
        IbClavesOpEspeciales ibClavesOpEspeciales = new IbClavesOpEspeciales();
        IbClavesOperacionesEspecialesDTO ibClavesOperacionesEspecialesDTO = new IbClavesOperacionesEspecialesDTO();
        int marca = 0;
        try {
            //registro
            if (sw) {
                ibClavesOperacionesEspecialesDTO = ibClavesOpEspecialesFacade.getClavesOperacionesEspeciales(idUsuario, claveOP, codigoCanal);
                if (!ibClavesOperacionesEspecialesDTO.getRespuesta().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    ibClavesOpEspeciales.setCodigoUsuarioCarga(new IbUsuarios(new BigDecimal(idUsuarioCarga)));
                    ibClavesOpEspeciales.setClaveEspecial(claveOP);
                    ibClavesOpEspeciales.setEstatus(Short.parseShort(ClavesOperacionesEspecialesEnum.ACTIVO.getId()));
                    ibClavesOpEspeciales.setIdUsuario(new IbUsuarios(new BigDecimal(idUsuario)));
                    ibClavesOpEspeciales.setIdCanal(new IbCanal(new BigDecimal(idCanal)));
                    ibClavesOpEspecialesFacade.crearPJ(ibClavesOpEspeciales);
                    this.insertarActualizarHistoricoClaveOP(idUsuario, claveOP, idUsuarioCarga, idCanal, codigoCanal);
                    resultados.put(ESTADO_CLAVE_OP, ClavesOperacionesEspecialesEnum.EXITOSO.getDescripcion());
                } else {
                    resultados.put(ESTADO_CLAVE_OP, ClavesOperacionesEspecialesEnum.YA_REGISTRADO.getDescripcion());
                }

            } else { //modificación
                ibClavesOperacionesEspecialesDTO = ibClavesOpEspecialesFacade.getClavesOperacionesEspeciales(idUsuario, claveOP, codigoCanal);
                if (!ibClavesOperacionesEspecialesDTO.getRespuesta().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new NoResultException();
                }
                ibClavesOperacionesEspecialesDTO = ibHistClavesOpEspecialesFacade.getHistClavesOperacionesEspeciales(ibClavesOperacionesEspecialesDTO, idUsuario, claveOP, codigoCanal);
                if (!ibClavesOperacionesEspecialesDTO.getRespuesta().getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new NoResultException();
                }
                for (IbHistClavesOpEspeciales ibHistClavesOpEspeciales : ibClavesOperacionesEspecialesDTO.getIbHistClavesOpEspecialesList()) {
                    if (claveOP.equals(ibHistClavesOpEspeciales.getClaveEspecial())) {
                        marca = 1;
                        break;
                    }
                }
                if (marca != 1) {
                    ibClavesOpEspeciales = ibClavesOperacionesEspecialesDTO.getIbClavesOpEspeciales();
                    ibClavesOpEspeciales.setClaveEspecial(claveOP);
                    ibClavesOpEspeciales.setEstatus(Short.parseShort(ClavesOperacionesEspecialesEnum.ACTIVO.getId()));
                    //SE SETEA A 0 CUANDO ES VALIDADO DE MANERA CORRECTA LA CLAVE ESPECIAL
                    ibClavesOpEspeciales.setIntentosFallidos((short) 0);
                    ibClavesOpEspecialesFacade.modificarPj(ibClavesOpEspeciales);
                    this.insertarActualizarHistoricoClaveOP(idUsuario, claveOP, idUsuarioCarga, idCanal, codigoCanal);
                    resultados.put(ESTADO_CLAVE_OP, ClavesOperacionesEspecialesEnum.EXITOSO.getDescripcion());
                } else {
                    resultados.put(ESTADO_CLAVE_OP, ClavesOperacionesEspecialesEnum.YA_REGISTRADO.getDescripcion());
                }

            }

        } catch (NoResultException e) {
            logger.error(new StringBuilder("ERROR JPA EN getClavesOperacionesEspeciales: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta = ibClavesOperacionesEspecialesDTO.getRespuesta();
            resultados.put(ESTADO_CLAVE_OP, ClavesOperacionesEspecialesEnum.NO_REGISTRADO.getDescripcion());
        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN validarClaveOP: ")
                    .append("CTA-").append("")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            utilDTO.setRespuesta(respuesta);
            utilDTO.setResulados(resultados);
        }
        return utilDTO;
    }

    public UtilDTO insertarActualizarHistoricoClaveOP(String idUsuario, String claveOP, String idUsuarioCarga, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        IbHistClavesOpEspeciales ibHistClavesOpEspeciales = new IbHistClavesOpEspeciales();
        try {
            //registro
            ibHistClavesOpEspeciales.setCodigoUsuarioCarga(new IbUsuarios(new BigDecimal(idUsuarioCarga)));
            ibHistClavesOpEspeciales.setClaveEspecial(claveOP);
            ibHistClavesOpEspeciales.setIdUsuario(new IbUsuarios(new BigDecimal(idUsuario)));
            ibHistClavesOpEspeciales.setIdCanal(new IbCanal(new BigDecimal(idCanal)));
            ibHistClavesOpEspecialesFacade.crearPJ(ibHistClavesOpEspeciales);

        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN insertarActualizarHistoricoClaveOP: ")
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


    /**
     * Valida que el usuario tenga un registro de las claves de operaciones especiales
     * @param idUsuario
     * @param idCanal
     * @param codigoCanal
     * @return the boolean 
     */
    @Override
    public boolean validarExisteClaveOperacionesEspeciales(String idUsuario, String idCanal, String codigoCanal) {        
        boolean resultado = false;
        try {
            IbClavesOpEspeciales ibClavesOpEspeciales = ibClavesOpEspecialesFacade.validarExisteClaveOperacionesEspecialesUser(idUsuario);
            if (ibClavesOpEspeciales != null) {
                resultado = true;
            } 
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA EN validarExisteClaveOperacionesEspeciales: ")
                    //.append("USR-").append(idUsuario)
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
        } 
        return resultado;
    }

    public String parametro(String codigo, String codigoCanal) {
        return ibParametrosFacade
                .consultaParametro(codigo, codigoCanal)
                .getIbParametro().getValor();
    }
}
