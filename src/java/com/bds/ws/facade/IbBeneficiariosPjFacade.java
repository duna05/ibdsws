/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbBeneficiariosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.enumerated.EstatusBeneficiarioEnum;
import com.bds.ws.enumerated.EstatusCuentaEnum;
import com.bds.ws.enumerated.EstatusPagosEnum;
import com.bds.ws.model.IbBeneficiariosPj;
import com.bds.ws.model.IbCtasXBeneficiariosPj;
import com.bds.ws.model.IbEmpresas;
import com.bds.ws.model.IbEstatusPagosPj;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author robinson.rodriguez
 */
@Named("ibBeneficiariosPjFacade")
@Stateless(name = "wsIbBeneficiariosPjFacade")
public class IbBeneficiariosPjFacade extends AbstractFacade<IbBeneficiariosPj> {
    
    @EJB
    private IbEstatusPagosPjFacade ibEstatusPagosPjFacade;

    public IbEstatusPagosPjFacade getIbEstatusPagosPjFacade() {
        return ibEstatusPagosPjFacade;
    }

    public void setIbEstatusPagosPjFacade(IbEstatusPagosPjFacade ibEstatusPagosPjFacade) {
        this.ibEstatusPagosPjFacade = ibEstatusPagosPjFacade;
    }
    

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {    
        return em;
    }

    public IbBeneficiariosPjFacade() {
        super(IbBeneficiariosPj.class);
    }

    public List<IbBeneficiariosPj> listarIbBeneficiariosPj(Long idCargaBeneficiario, Short estatusCarga) {
        List<IbBeneficiariosPj> ibBeneficiariosPjDTO = null;
        if ((estatusCarga == null)) {
            estatusCarga = 0;
        }
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT b FROM IbBeneficiariosPj b ");
            consulta.append(" WHERE b.idCargaBeneficiario.idCargaBeneficiario =:id");
            consulta.append(" AND b.estatusAutorizacion.id =:estatus ");

            ibBeneficiariosPjDTO = (List<IbBeneficiariosPj>) em.createQuery(consulta.toString())
                    .setParameter("id", idCargaBeneficiario)
                    .setParameter("estatus", estatusCarga.longValue())
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibBeneficiariosPjDTO;
    }

    public int modificarEstatusDetalle(Long idCargaBeneficiario, BigDecimal estatus) {
        int cantActualizados = 0;
        StringBuilder queryUpdate = new StringBuilder();
        queryUpdate.append("UPDATE IB_BENEFICIARIOS_PJ B ")
                .append(" SET B.ESTATUS_AUTORIZACION = ? ")
                .append(" WHERE B.ID_CARGA_BENEFICIARIO = ? ");
        try {
            cantActualizados = getEntityManager().createNativeQuery(queryUpdate.toString())
                    .setParameter(1, estatus)
                    .setParameter(2, idCargaBeneficiario)
                    .executeUpdate();
        } catch (Exception e) {
            throw e;
        }
        return cantActualizados;
    }
    
   
    public IbBeneficiariosPj actualizarEstatusBeneficiarioPorId(BigDecimal idBenediciario, BigDecimal idEstatus,Integer cantidadAprovadores) throws Exception {
        Boolean flag = Boolean.TRUE;
        IbBeneficiariosPj ibBeneficiariosPj = null;
        IbEstatusPagosPj estatus = null,estatusAux = null;
        
        RespuestaDTO respuesta = new RespuestaDTO();
        short estatusBene =1;

        try {
            ibBeneficiariosPj = (IbBeneficiariosPj) getEntityManager().find(IbBeneficiariosPj.class, idBenediciario.longValue());
            
            if(ibBeneficiariosPj!=null){
                estatus = this.ibEstatusPagosPjFacade.buscarIbEstatusPj(idEstatus);
                estatusAux = estatus;
                ibBeneficiariosPj.setEstatusAutorizacion(estatus);
                if(idEstatus.longValue()==4l){
                    List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjList = ibBeneficiariosPj.getIbCtasXBeneficiariosPjList();
                    ibBeneficiariosPj.setEstatusBeneficiario(estatusBene);
                    System.out.println("YA SE AOUTORIZO EL ESTATUS DEL BENEFICIARIO....");
                    for (IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj : ibCtasXBeneficiariosPjList) {
                        
                        if(ibCtasXBeneficiariosPj.getEstatusAutorizacion().getId().equals(BigDecimal.valueOf(2L)) && ibCtasXBeneficiariosPj.getEstatusCuenta()==0){
                            ibCtasXBeneficiariosPj.setEstatusCuenta(Short.valueOf(EstatusCuentaEnum.ACTIVO.getId()));
                            ibCtasXBeneficiariosPj.setEstatusAutorizacion(estatusAux); 
                        }else{
                            estatus = this.ibEstatusPagosPjFacade.buscarIbEstatusPj(BigDecimal.valueOf(EstatusPagosEnum.PENDIENTE_X_AUTORIZAR.getId()));
                            ibCtasXBeneficiariosPj.setEstatusAutorizacion(estatus); 
                        }                       
                           
                    }
                    ibBeneficiariosPj.setIbCtasXBeneficiariosPjList(ibCtasXBeneficiariosPjList);
                    
                }
                respuesta = this.modificarPj(ibBeneficiariosPj);
                if(!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                flag = Boolean.FALSE;
                }
            }
              
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        if(flag == Boolean.TRUE){
            return ibBeneficiariosPj;
        }else{
            return ibBeneficiariosPj = new IbBeneficiariosPj();
        }
        
    }

    public IbBeneficiariosPj buscarIbBeneficiariosPj(Long idBeneficiario) {
        IbBeneficiariosPj ibBeneficiariosPj = new IbBeneficiariosPj();
        try {
            ibBeneficiariosPj = (IbBeneficiariosPj) em.find(IbBeneficiariosPj.class, idBeneficiario);
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return ibBeneficiariosPj;
    }

    public boolean buscarIbBeneficiariosPj(IbEmpresas idEmpresa, String nroIdentificacionCliente) {
        try {

            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT count(b) ");
            consulta.append(" FROM IbBeneficiariosPj b ");
            consulta.append(" WHERE b.nroIdentificacionCliente = :nroIdentificacionCliente ");
            consulta.append(" AND  b.idEmpresa.id = :idEmpresa");
            consulta.append(" AND b.estatusBeneficiario > 0 ");//ya esta procesado

            long count = (long) em.createQuery(consulta.toString())
                    .setParameter("nroIdentificacionCliente", nroIdentificacionCliente)
                    .setParameter("idEmpresa", idEmpresa.getId())
                    .getSingleResult();

            return (count > 0);

        } catch (Exception ex) {
            //respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
            throw ex;
        }
    }

    public List<IbCtasXBeneficiariosPj> buscarIbBeneficiariosPjByReference(IbEmpresas idEmpresa, String nroReferencia) {
        List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT b FROM IbCtasXBeneficiariosPj b ");
            consulta.append(" WHERE  b.idBeneficiario.referenciaBeneficiario = :nroReferencia");
            consulta.append(" AND  b.idEmpresa.id = :idEmpresa");
            consulta.append(" AND b.idBeneficiario.estatusBeneficiario > 0");//ya esta procesado

            ibCtasXBeneficiariosPj = (List<IbCtasXBeneficiariosPj>) em.createQuery(consulta.toString())
                    .setParameter("nroReferencia", nroReferencia)
                    .setParameter("idEmpresa", idEmpresa.getId())
                    .getResultList();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return ibCtasXBeneficiariosPj;
    }

    public IbBeneficiariosPj existeBeneficiarioPorRif(IbEmpresas idEmpresa, String nroBeneficiario) {
        IbBeneficiariosPj ibBeneficiariosPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT b FROM IbBeneficiariosPj b ");
            consulta.append(" WHERE  b.nroIdentificacionCliente = :nroBeneficiario");
            consulta.append(" AND  b.idEmpresa.id = :idEmpresa");
            consulta.append(" AND b.estatusBeneficiario > 0 ");//ya esta procesado

            ibBeneficiariosPj = (IbBeneficiariosPj) em.createQuery(consulta.toString())
                    .setParameter("nroBeneficiario", nroBeneficiario)
                    .setParameter("idEmpresa", idEmpresa.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return ibBeneficiariosPj;
    }

    public IbBeneficiariosPj existeBeneficiarioPorRef(IbEmpresas idEmpresa, String nroReferencia) {
        IbBeneficiariosPj ibBeneficiariosPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT b FROM IbBeneficiariosPj b ");
            consulta.append(" WHERE  b.referenciaBeneficiario = :referencia");
            consulta.append(" AND  b.idEmpresa.id = :idEmpresa");
            consulta.append(" AND b.estatusBeneficiario > 0 ");//ya esta procesado

            ibBeneficiariosPj = (IbBeneficiariosPj) em.createQuery(consulta.toString())
                    .setParameter("referencia", nroReferencia)
                    .setParameter("idEmpresa", idEmpresa.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return ibBeneficiariosPj;
    }

    public List<IbBeneficiariosPj> listarIbBeneficiariosPorEmpresaPj(BigDecimal idEmpresa, Short estatusBeneficiario) {
        List<IbBeneficiariosPj> ibBeneficiariosPjDTO = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT distinct b.idBeneficiario FROM IbCtasXBeneficiariosPj b ");
            consulta.append(" WHERE b.idEmpresa.id =:id");
            consulta.append(" AND b.idBeneficiario.estatusBeneficiario =:estatus");

            ibBeneficiariosPjDTO = (List<IbBeneficiariosPj>) em.createQuery(consulta.toString())
                    .setParameter("id", idEmpresa)
                    .setParameter("estatus", estatusBeneficiario)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibBeneficiariosPjDTO;
    }

    public List<IbBeneficiariosPj> listaIbBeneficiariosPorCuentaEmpresa(BigDecimal idEmpresa, BigDecimal idCtaDelSur, BigDecimal idCtaPro, Short estatusCta, Short estatusBeneficiario) {
        List<IbBeneficiariosPj> ibBeneficiariosPjDTO = new ArrayList<>();

        //IbCtasXBeneficiariosPj
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT b.idBeneficiario FROM IbCtasXBeneficiariosPj b ");
            consulta.append(" WHERE b.idEmpresa.id =:id");
            consulta.append(" AND b.cuentaDelSur =:idCtaDelSur");
            consulta.append(" AND b.cuentaPropia =:idCtaPro");

            if (estatusCta == null) {
                estatusCta = 0;
                consulta.append(" AND b.estatusCuenta >:estatusCta");
            } else {
                consulta.append(" AND b.estatusCuenta =:estatusCta");
            }

            if (estatusBeneficiario == Short.valueOf(String.valueOf(1))) {
                consulta.append(" AND b.idBeneficiario.estatusBeneficiario =" + estatusBeneficiario);
            } else {
                consulta.append(" AND b.idBeneficiario.estatusBeneficiario = 999");
            }

            consulta.append(" GROUP BY  b.idBeneficiario ");

            ibBeneficiariosPjDTO = (List<IbBeneficiariosPj>) em.createQuery(consulta.toString())
                    .setParameter("id", idEmpresa)
                    .setParameter("estatusCta", estatusCta)
                    .setParameter("idCtaPro", idCtaPro)
                    .setParameter("idCtaDelSur", idCtaDelSur)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibBeneficiariosPjDTO;
    }

    public IbBeneficiariosPj buscarIbBeneficiariosPorEmpresasPj(BigDecimal idEmpresa, Long idBeneficiario) {
        IbBeneficiariosPj ibBeneficiariosPj = null;        
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT b FROM IbBeneficiariosPj b ");
            consulta.append(" WHERE b.idEmpresa.id =:idEmpresa");
            consulta.append(" AND b.idBeneficiario =:idBeneficiario");
            consulta.append(" AND b.estatusBeneficiario > 0 ");//ya esta procesado

            ibBeneficiariosPj = (IbBeneficiariosPj) em.createQuery(consulta.toString())
                    .setParameter("idEmpresa", idEmpresa)
                    .setParameter("idBeneficiario", idBeneficiario).getSingleResult();

        } catch (Exception e) {
            throw e;
        }
        return ibBeneficiariosPj;
    }
    
    public IbBeneficiariosPj buscarCuentasBeneficiarioPorEmpresasPj(BigDecimal idEmpresa, Long idBeneficiario) {
        IbBeneficiariosPj ibBeneficiariosPj = new IbBeneficiariosPj();
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT b FROM IbBeneficiariosPj b ");
            consulta.append(" WHERE b.idEmpresa.id =:idEmpresa");   
            consulta.append(" AND b.idBeneficiario =:idBeneficiario");
            consulta.append(" AND b.estatusBeneficiario > 0 ");//ya esta procesado

            ibBeneficiariosPj = (IbBeneficiariosPj) em.createQuery(consulta.toString())
                    .setParameter("idEmpresa", idEmpresa)
                    .setParameter("idBeneficiario", idBeneficiario).getSingleResult();

        } catch (Exception e) {
            throw e;    
        }
        return ibBeneficiariosPj;
    }
    
    
    
       public IbBeneficiariosPj buscarCuentasBeneficiarioPornroIdentificacionClientePj(BigDecimal idEmpresa,String nroIdentificacionCliente) {
         IbBeneficiariosPj ibBeneficiariosPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT b FROM IbBeneficiariosPj b ");
            consulta.append(" WHERE b.idEmpresa.id =:idEmpresa"); 
            consulta.append(" AND  b.nroIdentificacionCliente =:IdentificacionCliente");
            consulta.append(" AND b.estatusBeneficiario > 0 ");//ya esta procesado

            ibBeneficiariosPj = (IbBeneficiariosPj) em.createQuery(consulta.toString())
                    .setParameter("idEmpresa", idEmpresa)
                    .setParameter("IdentificacionCliente", nroIdentificacionCliente).getSingleResult();

        } catch (Exception e) {
            throw e;
        }
        return ibBeneficiariosPj;
    }
       
    public IbBeneficiariosPjDTO listarIbBeneficiariosCtaAutorizarPj(BigDecimal idEmpresa, Long idUsuarioAurorizado,Long idServicio, String StatusAConsultar) throws Exception {

        IbBeneficiariosPjDTO ibBeneficiariosPjDTO = new IbBeneficiariosPjDTO();
        ibBeneficiariosPjDTO.setIbBeneficiariosPjsList(new ArrayList<IbBeneficiariosPj>());
        ibBeneficiariosPjDTO.setRespuestaDTO(new RespuestaDTO());
        
        List<IbBeneficiariosPj> ibBeneficiariosPjList = null;
        
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT distinct b FROM IbBeneficiariosPj  b");
            consulta.append(" INNER JOIN FETCH b.ibCtasXBeneficiariosPjList bar");
            consulta.append(" WHERE bar.idEmpresa.id =:idEmpresa" );
            consulta.append(" and  (bar.estatusAutorizacion.id=1 or bar.estatusAutorizacion.id=2 or bar.estatusAutorizacion.id=3)" );
            consulta.append(" and  bar.estatusCuenta =:estatusCuenta" );            //Cuentas inactivas
            consulta.append(" and  b.estatusBeneficiario =:estatusBeneficiario" );  //Beneficiarios activos
            consulta.append(" and NOT EXISTS (SELECT p FROM IbSecuenciaAprobacionPj p" );
            consulta.append("  WHERE p.idTransaccion = bar.idCuenta " );  
            consulta.append("        and p.idServicioPj.id =:idServicioPj" ); 
            consulta.append("        and p.idUsuarioPj.id =:idUsuarioAurorizado)" );           
            
            ibBeneficiariosPjList = (List<IbBeneficiariosPj>) em.createQuery(consulta.toString())
                   .setParameter("idEmpresa", idEmpresa)
                   .setParameter("idUsuarioAurorizado", new BigDecimal(idUsuarioAurorizado))
                    .setParameter("idServicioPj", new BigDecimal(idServicio))
                    .setParameter("estatusCuenta", Integer.parseInt(EstatusCuentaEnum.INACTIVO.getId()))
                    .setParameter("estatusBeneficiario", Integer.parseInt(EstatusBeneficiarioEnum.ACTIVO.getId()))
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        ibBeneficiariosPjDTO.setIbBeneficiariosPjsList(ibBeneficiariosPjList);

        return ibBeneficiariosPjDTO;
    } 
    
    
}
