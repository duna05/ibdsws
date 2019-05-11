/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.enumerated.EstatusServicioEnum;
import com.bds.ws.model.IbParametros;
import com.bds.ws.model.IbServiEmpreUsuariosPj;
import com.bds.ws.model.IbServiciosPj;
import com.bds.ws.model.IbTextos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis.perez
 */
@Stateless
public class IbServiEmpreUsuariosPjFacade extends AbstractFacade<IbServiEmpreUsuariosPj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbServiEmpreUsuariosPjFacade() {
        super(IbServiEmpreUsuariosPj.class);
    }
    
    public Map<String, List<IbServiciosPj>> consultarModulosPerfiles(String idUsuario, String idCanal, String idEmpresa, Character estatus, Character visible) {
        StringBuilder consulta = new StringBuilder();        
        consulta.append(" SELECT  (SELECT T.MENSAJE_USUARIO FROM IB_TEXTOS T WHERE T.CODIGO = MO.NOMBRE) NOMBRE, ");
        consulta.append(" S.ID, ");
        consulta.append(" S.NOMBRE, ");
        consulta.append(" S.POSICION, ");
        consulta.append(" S.ESTATUS, ");
        consulta.append(" S.POSEE_BENEFICIARIO, ");
        consulta.append(" S.POSEE_OTP, ");
        consulta.append(" S.POSEE_SERVICIO, ");
        consulta.append(" S.ID_CORE, ");
        consulta.append(" CASE ");
        consulta.append("   WHEN S.CONTRATACION = 1 ");
        consulta.append("   THEN ");
        consulta.append("       (SELECT COUNT (0) ");
        consulta.append("             FROM IB_SERVICIOS_EMPRESA SE ");
        consulta.append("             WHERE SE.ID_EMPRESA = ?1");
        consulta.append("             AND S.ID = SE.ID_SERVICIO_PJ ");
        consulta.append("        ) ");
        consulta.append("   ELSE ");
        consulta.append("       1  ");
        consulta.append(" END ESTATUS_SERVICIO_EMPRESA, ");
        consulta.append(" PAR.VALOR, ");
        consulta.append(" TEX.MENSAJE_USUARIO, ");
        //NOMBRE DEL MODULO DE LA TABLA MODULOS
        consulta.append(" MO.NOMBRE, "); 
        //PARA SABER SI EL USUARIO TIENE PERMISO A ESA OPCION DEL MENU
        consulta.append(" (SELECT COUNT (0) ");
        consulta.append("    FROM IB_SERVI_EMPRE_USUARIOS_PJ SPER ");
        consulta.append("       WHERE SPER.ID_USUARIO_PJ  = ?2 ");
        consulta.append("       AND   SPER.ID_EMPRESA_PJ  = ?1 ");
        consulta.append("       AND   SPER.ID_CANAL       = ?5 ");
        consulta.append("       AND   SPER.ID_SERVICIO_PJ = S.ID ");
        consulta.append(" )  ");
        consulta.append("   ESTATUS_SERVI_EMPRESA_USUARIO  "); 
        consulta.append(" FROM  IB_SERVICIOS_PJ S , ");
        consulta.append("       IB_MODULOS_PJ  MO ,  ");
        //consulta.append("       IB_SERVI_EMPRE_USUARIOS_PJ SPER, ");
        consulta.append("       IB_PARAMETROS PAR, ");
        consulta.append("       IB_TEXTOS     TEX ");
        consulta.append(" WHERE MO.ID               = S.ID_MODULO_PJ ");
        //consulta.append(" AND   SPER.ID_USUARIO_PJ  = ?2 ");
        //consulta.append(" AND   SPER.ID_EMPRESA_PJ  = ?1 ");
        //consulta.append(" AND   SPER.ID_SERVICIO_PJ = S.ID ");
        consulta.append(" AND   PAR.CODIGO          = S.NOMBRE   ");
        consulta.append(" AND   PAR.ID_CANAL        = S.ID_CANAL ");
        consulta.append(" AND   TEX.CODIGO          = S.NOMBRE ");
        //consulta.append(" AND   MO.VISIBLE          = ?4 ");
        consulta.append(" AND   S.VISIBLE           = ?4 ");
        //consulta.append(" AND   SPER.ID_CANAL       = ?5 ");
        consulta.append(" AND   MO.ESTATUS          = ?6 ");
        consulta.append(" ORDER BY MO.POSICION, MO.NOMBRE, S.POSICION");
        
        Collection listaModulos = em.createNativeQuery(consulta.toString())
                .setParameter(1, idEmpresa)
                .setParameter(2, idUsuario)
                .setParameter(4, visible)
                .setParameter(5, idCanal)
                .setParameter(6, estatus)
                .getResultList();

        Iterator interador = listaModulos.iterator();
        IbServiciosPj ibServicioPj;
        IbParametros  ibParametros;
        IbTextos      ibTextos; 
        String nombreModulo;
        Map<String, List<IbServiciosPj>> mapaModulos = new LinkedHashMap();
        Object[] registro;
        while (interador.hasNext()) {
            registro = (Object[]) interador.next();
            nombreModulo = (String) registro[12] +"|"+(String) registro[0];
            if(!mapaModulos.containsKey(nombreModulo)){
                mapaModulos.put(nombreModulo, new ArrayList<IbServiciosPj>());
            }
            ibServicioPj = new IbServiciosPj();
            ibParametros = new IbParametros();
            ibTextos     = new IbTextos();
            
            //SE GUARDAN LAS TRANSACCIONES DE ESE MODULO
            ibServicioPj.setId((BigDecimal) registro[1]);
            ibServicioPj.setNombre((String) registro[2]);
            ibServicioPj.setPosicion(((BigDecimal) registro[3]).toBigInteger());
            ibServicioPj.setPoseeBeneficiario(((String) registro[5]).charAt(0));
            ibServicioPj.setPoseeOtp(((String) registro[6]).charAt(0));
            ibServicioPj.setPoseeServicio(((String) registro[7]).charAt(0));
            ibServicioPj.setIdCore(((String) registro[8]));
            
            /*
            SE REALIZA TRATAMIENTO ESPECIAL PARA EL ESTATUS
            0 SI EL SERVICIO REQUIERE CONTRATACION
            1 SI EL SERVICIO ESTA ACTIVO
            2 SI EL SERVICIO NO LO TIENE PERMITIDO EL USUARIO
            */
            
            Character estatusServicioUsuario      = ((BigDecimal) registro[13]).toString().charAt(0);
            Character estatusServicioContratacion = ((BigDecimal) registro[9]).toString().charAt(0);
            //EL SERVICIO LO TIENE PERMITIDO EL USUARIO
            if(estatusServicioUsuario != '1'){
                ibServicioPj.setEstatus(String.valueOf(EstatusServicioEnum.NO_PERMITIDO_USUARIO.getId()).charAt(0));
            }
            else if(estatusServicioContratacion != '1'){
                ibServicioPj.setEstatus(String.valueOf(EstatusServicioEnum.REQUIERE_CONTRATACION.getId()).charAt(0));
            }else {
                ibServicioPj.setEstatus(String.valueOf(EstatusServicioEnum.ACTIVO.getId()).charAt(0));
            }
            
            ibParametros.setNombre(((String) registro[10]));
            ibTextos.setMensajeUsuario(((String) registro[11]));
            ibServicioPj.setIdParametros(ibParametros);
            ibServicioPj.setIdTextos(ibTextos);
            mapaModulos.get(nombreModulo).add(ibServicioPj);
        }
        return mapaModulos;
    }
    
    public List<IbServiEmpreUsuariosPj> consultarIbServiEmpreUsuariosPj(BigDecimal idUsuario, BigDecimal idEmpresa, BigDecimal idCanal) {
        List<IbServiEmpreUsuariosPj> ibServiEmpreUsuariosPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbServiEmpreUsuariosPj e    ");
            consulta.append("     WHERE e.idUsuarioPj.id  = :idUsuario ");
            consulta.append("     AND   e.idEmpresas.id   = :idEmpresa");
            consulta.append("     AND   e.idCanal.id      = :idCanal");

            ibServiEmpreUsuariosPj = (List<IbServiEmpreUsuariosPj>) em.createQuery(consulta.toString())
                    .setParameter("idUsuario", idUsuario)
                    .setParameter("idEmpresa", idEmpresa)
                    .setParameter("idCanal", idCanal)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibServiEmpreUsuariosPj;
    }
}
