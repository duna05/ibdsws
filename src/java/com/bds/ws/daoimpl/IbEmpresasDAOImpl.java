/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;
import com.bds.ws.dao.IbEmpresasDAO;
import com.bds.ws.dto.IbEmpresasDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusEmpresaEnum;
import com.bds.ws.facade.IbEmpresasFacade;
import com.bds.ws.model.IbEmpresas;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
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
 * @author luis.perez
 */
@Named("IBEmpresasDAO")
@Stateless
public class IbEmpresasDAOImpl extends DAOUtil implements IbEmpresasDAO {
    @EJB
    private IbEmpresasFacade ibEmpresasFacade;
    
    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbEmpresasDAOImpl.class.getName());
    
    /**
     * Realiza la validacion de la empresa
     * 
     * @param tipoRif     tipo de persona, natural o juridica
     * @param rif         numero de rif
     * @param idCanal     id de canal interno
     * @param codigoCanal codigo de canal en el CORE
     * @return UtilDTO 
     */
    @Override
    public UtilDTO validarEmpresa(Character tipoRif, String rif, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        Map resultado = new HashMap();
        try {
            IbEmpresas empresa = ibEmpresasFacade.findByRif(tipoRif,rif);
            if (empresa == null) {
                resultado.put(EXISTE, false);
            } else {
                resultado.put(EXISTE, true);
                resultado.put(ID_EMPRESA, empresa.getId());
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA EN validarEmpresa: ")
                    //.append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());                     
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA); 
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuestaDTO);
        }
        return utilDTO;
    }
    
    /**
     * Metodo que se encarga de registrar los datos de una emprea en BD Oracle
     * 11++
     *
     *
     * @param nroDoc numero de documento de indentificacion
     * @param tipoDoc tipo de documento del usuario
     * @param nroCuenta
     * @param codigoEmpresa
     * @param nombre
     * @param tipoAcceso
     * @param estatus
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @Override
    public UtilDTO insertarDatosEmpresa(String nroDoc, Character tipoDoc, String nroCuenta, Long codigoEmpresa, String nombre, Character tipoAcceso, Character estatus, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            IbEmpresas ibEmpresas = new IbEmpresas();
            ibEmpresas.setId(BigDecimal.ZERO); //ES GENERADO AUTOMATICAMENTE EN BD
            ibEmpresas.setCodCliente(codigoEmpresa);
            ibEmpresas.setEstatus(estatus);
            ibEmpresas.setNombre(nombre);
            ibEmpresas.setNroCta(nroCuenta);
            ibEmpresas.setNroRif(nroDoc);
            ibEmpresas.setTipoAcceso(tipoAcceso);
            ibEmpresas.setTipoRif(tipoDoc); 
            ibEmpresasFacade.crearPJ(ibEmpresas);
            IbEmpresas empresa = ibEmpresasFacade.findByRif(tipoDoc, nroDoc);
            if (empresa == null) {
                resultado.put(ID_EMPRESA, false);
                throw new NoResultException();
            } else {
                resultado.put(ID_EMPRESA, empresa.getId().toString());
            }
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
        } 
        catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarDatosEmpresa: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarDatosEmpresa: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN insertarDatosEmpresa: ")
//                    .append("-CH- ").append(codigoCanal)
//                    .append("-DT- ").append(new Date())
//                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }
    
    /**
     * Metodo que se +encarga de consultar los datos asociados a una empresa
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @Override
    public UtilDTO consultarDatosEmpresaUsuario(String idUsuario, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbEmpresasDTO ibEmpresasDTO = new IbEmpresasDTO();
        Map resultado = new HashMap();
        //SE CONSULTAN SOLO LAS EMPRESAS ACTIVAS
        String estatusEmpresa = String.valueOf(EstatusEmpresaEnum.ACTIVO.getId());
        try {
            List<IbEmpresas> empresas = ibEmpresasFacade.consultarDatosEmpresaUsuario(new BigDecimal(idUsuario), estatusEmpresa.charAt(0));
            if(!empresas.isEmpty()){
                ibEmpresasDTO.setIbEmpresas(empresas);
                resultado.put(EMPRESAS, ibEmpresasDTO);
            }else {
                ibEmpresasDTO.setIbEmpresas(new ArrayList<IbEmpresas>());
                resultado.put(EMPRESAS, ibEmpresasDTO);
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA EN consultarDatosEmpresaUsuario: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuestaDTO);
        }
        return utilDTO;
    }

    @Override
    public UtilDTO consultarDatosEmpresaUsuario(String idUsuario, String estatusRegistro, String idCanal, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbEmpresasDTO ibEmpresasDTO = new IbEmpresasDTO();
        Map resultado = new HashMap();
        //SE CONSULTAN SOLO LAS EMPRESAS ACTIVAS
        String estatusEmpresa = String.valueOf(EstatusEmpresaEnum.ACTIVO.getId());
        try {
            List<IbEmpresas> empresas = ibEmpresasFacade.consultarDatosEmpresaUsuario(new BigDecimal(idUsuario), estatusRegistro.charAt(0), estatusEmpresa.charAt(0));
            if (!empresas.isEmpty()) {
                ibEmpresasDTO.setIbEmpresas(empresas);
                resultado.put(EMPRESAS, ibEmpresasDTO);
                resultado.put(EXISTE, true);
            } else {
                ibEmpresasDTO.setIbEmpresas(new ArrayList<IbEmpresas>());
                resultado.put(EMPRESAS, ibEmpresasDTO);
                resultado.put(EXISTE, false);
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA EN consultarDatosEmpresaUsuario: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuestaDTO);
        }
        return utilDTO;
    }
}
