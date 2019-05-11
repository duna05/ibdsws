/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbParametros;
import com.bds.ws.model.IbServiciosPerfilesPj;
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
public class IbServiciosPerfilesPjFacade extends AbstractFacade<IbServiciosPerfilesPj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbServiciosPerfilesPjFacade() {
        super(IbServiciosPerfilesPj.class);
    }
    
    public Map<String, List<IbServiciosPj>> consultarServiciosPerfilesPj(String idPerfil, String estatus, String idCanal, String visible) {
        StringBuilder consulta = new StringBuilder();        
        consulta.append(" SELECT  (SELECT T.MENSAJE_USUARIO FROM IB_TEXTOS T WHERE T.CODIGO = MO.NOMBRE) NOMBRE, "); //0
        consulta.append(" S.ID, ");       //1
        consulta.append(" S.NOMBRE, ");   //2
        consulta.append(" S.POSICION, "); //3
        consulta.append(" S.ESTATUS, ");  //4
        consulta.append(" S.POSEE_BENEFICIARIO, "); //5
        consulta.append(" S.POSEE_OTP, ");          //6
        consulta.append(" S.POSEE_SERVICIO, ");     //7
        consulta.append(" S.ID_CORE, ");            //8
        /*
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
        */
        consulta.append(" PAR.VALOR, ");                    //9 
        consulta.append(" TEX.MENSAJE_USUARIO, ");          //10
        consulta.append(" MO.NOMBRE "); //NOMBRE DEL MODULO DE LA TABLA MODULOS 11
        consulta.append(" FROM  IB_SERVICIOS_PJ S , ");
        consulta.append("       IB_MODULOS_PJ  MO ,  ");
        consulta.append("       IB_PERFILES_PJ PE ,  ");
        consulta.append("       IB_SERVICIOS_PERFILES_PJ SPER, ");
        consulta.append("       IB_PARAMETROS PAR, ");
        consulta.append("       IB_TEXTOS     TEX ");
        consulta.append(" WHERE MO.ID               = S.ID_MODULO_PJ ");
        consulta.append(" AND   SPER.ID_SERVICIO_PJ = S.ID ");
        consulta.append(" AND   PE.ID               = SPER.ID_PERFIL_PJ ");
        consulta.append(" AND   PAR.CODIGO          = S.NOMBRE   ");
        consulta.append(" AND   PAR.ID_CANAL        = S.ID_CANAL ");
        consulta.append(" AND   TEX.CODIGO          = S.NOMBRE ");
        consulta.append(" AND   PE.ID               = ?2 ");
        consulta.append(" AND   S.ESTATUS           = ?3 ");
        consulta.append(" AND   MO.ESTATUS          = ?3 ");
        consulta.append(" AND   MO.VISIBLE          = ?4 ");
        //consulta.append(" AND   S.VISIBLE           = ?4 ");
        //SE FILTRA SOLO POR LOS ACTIVOS. SE TRAE LOS NO VISIBLES TAMBIEN
        consulta.append(" AND   SPER.ID_CANAL       = ?5 ");
        consulta.append(" ORDER BY MO.POSICION, MO.NOMBRE, S.POSICION");
        
        Collection listaModulos = em.createNativeQuery(consulta.toString())
                //.setParameter(1, idEmpresa)
                .setParameter(2, idPerfil)
                .setParameter(3, estatus)
                .setParameter(4, visible)
                .setParameter(5, idCanal)
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
            nombreModulo = (String) registro[11] +"|"+(String) registro[0];
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
            //ibServicioPj.setEstatus(((BigDecimal) registro[9]).toString().charAt(0));
            ibParametros.setNombre(((String) registro[9]));
            ibTextos.setMensajeUsuario(((String) registro[10]));

            ibServicioPj.setIdParametros(ibParametros);
            ibServicioPj.setIdTextos(ibTextos);
            mapaModulos.get(nombreModulo).add(ibServicioPj);
        }
        return mapaModulos;
    }
    
    public List<IbServiciosPj> consultarServiciosPorModulo(BigDecimal idModulo, Character estatus) {
        List<IbServiciosPj> ibServiciosPj = null;

        StringBuilder consulta = new StringBuilder();
        consulta.append(" SELECT s FROM IbServiciosPj s ");
        consulta.append(" WHERE s.idModuloPj.id         = :idModulo ");
        consulta.append(" AND   s.estatus               = :estatus ");

        ibServiciosPj = (List<IbServiciosPj>) em.createQuery(consulta.toString())
                .setParameter("idModulo", idModulo)
                .setParameter("estatus" , estatus)
                .getResultList();

        return ibServiciosPj;
    }
    
    public List<IbServiciosPerfilesPj> consultarServiciosPerfilesPj(BigDecimal idPerfil) {
        List<IbServiciosPerfilesPj> ibServiciosPerfilesPj = null;

        StringBuilder consulta = new StringBuilder();
        consulta.append(" SELECT s FROM IbServiciosPerfilesPj s ");
        consulta.append(" WHERE  s.idPerfilPj.id = :idPerfil ");

        ibServiciosPerfilesPj = (List<IbServiciosPerfilesPj>) em.createQuery(consulta.toString())
                .setParameter("idPerfil", idPerfil)
                .getResultList();

        return ibServiciosPerfilesPj;
    }
}
