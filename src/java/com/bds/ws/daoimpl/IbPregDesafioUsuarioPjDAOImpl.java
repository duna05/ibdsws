/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbPregDesafioUsuarioPjDAO;
import com.bds.ws.dto.IbParametrosDTO;
import com.bds.ws.dto.IbPregDesafioUsuarioPjDTO;
import com.bds.ws.dto.PreguntaRespuestaUsuarioDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.enumerated.EstatusAccesoEnum;
import com.bds.ws.enumerated.EstatusValidacionPositivaEnum;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbParametrosFacade;
import com.bds.ws.facade.IbPregDesafioUsuarioPjFacade;
import com.bds.ws.facade.IbUsuariosPjFacade;
import com.bds.ws.model.IbUsuariosPj;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.VALIDO;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author luis.perez
 */
@Named("IbPregDesafioUsuarioPjDAOImpl")
@Stateless
public class IbPregDesafioUsuarioPjDAOImpl implements IbPregDesafioUsuarioPjDAO {

    private static final Logger logger = Logger.getLogger(IbPregDesafioUsuarioPjDAOImpl.class.getName());

    final int RADIX = 10;

    @EJB
    IbPregDesafioUsuarioPjFacade ibPregDesafioUsuarioPjFacade;

    @EJB
    IbUsuariosPjFacade ibUsuariosPjFacade;

    @EJB
    IbParametrosFacade ibParametrosFacade;

    /**
     * Metodo que sustituye las preguntas de desafio viejas por las nuevas con
     * sus respuestas
     *
     * @param listPDDUsuarioRespuestas listado de preguntas y respuestas
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal interno en el CORE
     * @return RespuestaDTO
     */
    @Override
    public UtilDTO agregarPDDUsuarioPj(PreguntaRespuestaUsuarioDTO listPDDUsuarioRespuestas, String idCanal, String codigoCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        String idUsuario = null;
        try {
            if (!listPDDUsuarioRespuestas.getPreguntasRespuestasUsuarios().isEmpty()
                    && codigoCanal != null && !codigoCanal.isEmpty() && !codigoCanal.equalsIgnoreCase("")) {
                idUsuario = listPDDUsuarioRespuestas.getPreguntasRespuestasUsuarios().get(0).getIdUsuario().toString();
            } else {
                throw new DAOException("DAO008");
            }
            respuestaDTO = ibPregDesafioUsuarioPjFacade.borrarLotePDDUsuario(idUsuario, codigoCanal);
            if (!respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

            for (PreguntaRespuestaUsuarioDTO pregDesafioUsuario : listPDDUsuarioRespuestas.getPreguntasRespuestasUsuarios()) {
                respuestaDTO = ibPregDesafioUsuarioPjFacade.insertarPDDUsuario(pregDesafioUsuario.getIdUsuario().toString(), pregDesafioUsuario.getIdPregunta().toString(), pregDesafioUsuario.getRespuestaPDD(), codigoCanal);
                if (!respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN agregarPDDUsuarioPj: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setRespuesta(respuestaDTO);
        }
        return utilDTO;
    }

    /**
     * Metodo que devuelve un listado de preguntas de desafio de un cliente Pj
     *
     * @param idUsuario id del usuario
     * @param nombreCanal nombre del canal
     * @return retorna un listado de preguntas de desafio de un cliente
     */
    @Override
    public IbPregDesafioUsuarioPjDTO listadoPreguntasDesafioUsuarioPj(String idUsuario, String nombreCanal) {
        IbPregDesafioUsuarioPjDTO pregDesafioUsuarioDTO = new IbPregDesafioUsuarioPjDTO();
        pregDesafioUsuarioDTO.setRespuesta(new RespuestaDTO());
        try {

            if (idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                throw new DAOException("DAO008");
            }
            IbParametrosDTO ibParametrosDTO = ibParametrosFacade.consultaParametro("pjw.registro.usuario.cantidad.preguntas.validar", nombreCanal);

            if (ibParametrosDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                int cantidadPreguntasValidar = Integer.parseInt(ibParametrosDTO.getIbParametro().getValor());

                pregDesafioUsuarioDTO = ibPregDesafioUsuarioPjFacade.listaPreguntasDesafioUsuarioPj(idUsuario, nombreCanal);

                if (!pregDesafioUsuarioDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception();
                }

                int cantidadPreguntasConfiguradasParaUsuario = pregDesafioUsuarioDTO.getPreguntasDesafioUsuarioPj().size();
                Random rnd = new Random();
                //SE ELIMINAN LAS PREGUNTAS CON UN RANDON HASTA LLEGAR AL NUMERO DE 
                //PREGUNTAS CONFIGURADAS A VALIDAR
                while (pregDesafioUsuarioDTO.getPreguntasDesafioUsuarioPj().size() > cantidadPreguntasValidar) {
                    int indice = (int) (rnd.nextDouble() * (cantidadPreguntasConfiguradasParaUsuario - 1) + 0);
                    try {
                        pregDesafioUsuarioDTO.getPreguntasDesafioUsuarioPj().remove(indice);
                    } catch (IndexOutOfBoundsException e) {
                    }
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoPreguntasDesafioUsuario: ").append("IDUSR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            pregDesafioUsuarioDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return pregDesafioUsuarioDTO;
    }

    /**
     * Metodo para validar una rafaga de preguntas de desafio
     *
     * @param idUsuario id del usuario
     * @param rafaga cadena con las preguntas y respuestas a validar ej: < codigoPregunta >< separador
     * > < codigoPregunta >< separador >< codigoPregunta >< separador >
     * @param separador separador que utilizara en la rafaga, si este es null se
     * tomará guion "-" como separador
     * @param nombreCanal nombre del canal
     * @return UtilDTO -> 'true' en caso que la respuesta sea corresta 'false'
     * en caso contrario
     */
    @Override
    public UtilDTO validarPreguntaDDPj(String idUsuario, String rafaga, String separador, String nombreCanal) {
        UtilDTO utilDTO = new UtilDTO();
        utilDTO.setRespuesta(new RespuestaDTO());
        Map resultado = new HashMap();
        try {

            if (idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")
                    || rafaga == null || rafaga.isEmpty() || rafaga.equalsIgnoreCase("")
                    || separador == null || separador.isEmpty() || separador.equalsIgnoreCase("")) {
                throw new DAOException("DAO008");
            }

            UtilDTO utilDTOPreg = ibPregDesafioUsuarioPjFacade.validarPreguntaDDPj(idUsuario, rafaga, separador, nombreCanal);

            if (!utilDTOPreg.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }
            RespuestaDTO respTemp;

            if ((boolean) utilDTOPreg.getResulados().get(VALIDO)) {
                //si responde exitosamente se reinician los intentos fallidos
                respTemp = ibUsuariosPjFacade.actualizarCantIntentosFallidosPregSegPj(idUsuario, 0, nombreCanal);
                if (!respTemp.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception();
                }
                resultado.put(VALIDO, Character.forDigit(EstatusValidacionPositivaEnum.CORRECTO.getId(), RADIX));
            } else {
                //obtenemos la cant de intentos fallidos actuales}
                IbUsuariosPj usrPj = ibUsuariosPjFacade.find(idUsuario);
                int intentosActuales = usrPj.getIntentosFallidos().intValue();
                int intentosPermitidos = new Integer(ibParametrosFacade.consultaParametro("pjw.global.validacionPositiva.cantIntentosFallidos", nombreCanal).getIbParametro().getValor());
                respTemp = ibUsuariosPjFacade.actualizarCantIntentosFallidosPregSegPj(idUsuario, ++intentosActuales, nombreCanal);
                if (!respTemp.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception();
                }
                if (intentosActuales >= intentosPermitidos) {
                    //se procede a bloquear el usuario por superar los intentos fallidos
                    usrPj.setEstatusAcceso(Character.forDigit(EstatusAccesoEnum.BLOQUEADO.getId(), RADIX));
                    ibUsuariosPjFacade.modificarPj(usrPj);
                    resultado.put(VALIDO, Character.forDigit(EstatusValidacionPositivaEnum.BLOQUEADO.getId(), RADIX));
                } else {
                    //se notifica que las respuestas no son validas
                    resultado.put(VALIDO, Character.forDigit(EstatusValidacionPositivaEnum.INCORRECTO.getId(), RADIX));
                }
            }
            utilDTO.setResulados(resultado);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarPreguntaDDPj: ")//.append("USR-").append(codUsuario)
                    .append("USR-").append(idUsuario).append("-CH-").append(nombreCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarPreguntaDDPj: ")//.append("USR-").append(codUsuario)
//                    .append("USR-").append(idUsuario).append("-CH-").append(nombreCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }
}
