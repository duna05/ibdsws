/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbCtasEmpresaPjDAO;
import com.bds.ws.dto.CuentaDTO;
import com.bds.ws.facade.IbCtasEmpresaPjFacade;
import com.bds.ws.util.BDSUtil;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author luis.perez
 */
@Named("IbCtasEmpresaPjDAO")
@Stateless
public class IbCtasEmpresaPjDAOImpl extends BDSUtil implements IbCtasEmpresaPjDAO {
    @EJB
    IbCtasEmpresaPjFacade ibCtasEmpresaPjFacade;
    
    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbCtasEmpresaPjDAOImpl.class.getName());
    
    /**
     * Metodo que obtiene el listado de cuentas de ahorro y corrientes una
     * empresa en relacion a un usuario
     *
     * @param idUsuario id del usuario en     IB
     * @param idEmpresa id de la empresa en   IB
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado en el core bancario
     * @return ClienteDTO listado de cuentas de ahorro y corrientes que posee un
     * cliente
     */
    @Override
    public List<CuentaDTO> listarCuentasEmpresaUsuarioPj(String idUsuario, String idEmpresa, String idCanal, String codigoCanal) {
        return ibCtasEmpresaPjFacade.consultarCuentasPorEmpresaUsuario(new BigDecimal(idUsuario), new BigDecimal(idEmpresa));
    }
}
