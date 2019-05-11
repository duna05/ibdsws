/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;
import com.bds.ws.dao.ActividadEconomicaDAO;
import com.bds.ws.dao.ClasificacionDAO;
import com.bds.ws.dao.EstadoDAO;
import com.bds.ws.dao.MunicipioDAO;
import com.bds.ws.dao.SectorDAO;
import com.bds.ws.dao.SubClasificacionDAO;
import com.bds.ws.dao.SubSubClasificacionDAO;
import com.bds.ws.dao.SubtipoClienteDAO;
import com.bds.ws.dao.TipoIdentificacionDAO;
import com.bds.ws.dto.ActividadEconomicaDTO;
import com.bds.ws.dto.ClasificacionDTO;
import com.bds.ws.dto.EstadoDTO;
import com.bds.ws.dto.MunicipioDTO;
import com.bds.ws.dto.SectorDTO;
import com.bds.ws.dto.SubClasificacionDTO;
import com.bds.ws.dto.SubSubClasificacionDTO;
import com.bds.ws.dto.SubtipoClienteDTO;
import com.bds.ws.dto.TipoIdentificacionDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author humberto.rojas
 */
@WebService(serviceName = "EstadoWs")
public class EstadoWs {
     @EJB
    private EstadoDAO estadoDAO;
     
     
    @WebMethod(operationName = "consultarEstado")
    public EstadoDTO consultarEstado() {
        return estadoDAO.consultarEstado();
    }
}
