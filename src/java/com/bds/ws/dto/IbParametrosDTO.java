/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbParametros;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IbParametrosDTO
 * @author renseld.lugo
 */
public class IbParametrosDTO implements Serializable {

    private IbParametros ibParametro;   //Objeto de tipo IbParametros para retornar un solo parametro
    private List<IbParametros> ibParametros;  //Lisa de objetos IbParametros para retornar varios parametros 
    private RespuestaDTO respuesta;  //objeto de tipo RespuestaDTO para retornar el resultado de la transaccion

    /**
     * retorna Objeto de tipo IbParametros para retornar un solo parametro.
     *
     * @return IbParametros.
     */
    public IbParametros getIbParametro() {
        return ibParametro;
    }

    /**
     * asigna Objeto de tipo IbParametros
     * @param ibParametro IbParametros.
     */
    public void setIbParametro(IbParametros ibParametro) {
        this.ibParametro = ibParametro;
    }

    /**
     * retorna Lisa de objetos IbParametros para retornar varios parametros.
     * @return List < IbParametros >.
     */
    public List<IbParametros> getIbParametros() {
        return ibParametros;
    }

    /**
     * asigna Lisa de objetos IbParametros.
     * @param ibParametros IbParametros.
     */
    public void setIbParametros(List<IbParametros> ibParametros) {
        this.ibParametros = ibParametros;
    }

    /**
     * retorna la respuesta de la operacion que se realiza.
     * @return RespuestaDTO
     */
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    /**
     * asigna objeto para almacenar la respuesta de la transaccion
     * @param respuesta objeto para almacenar la respuesta de la transaccion
     */
    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

}
