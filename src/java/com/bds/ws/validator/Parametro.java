/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.validator;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author roger.muñoz
 */
public class Parametro {
    private Map<String,String> mapa= new HashMap<String,String>();

    public Parametro add(String key,String value){
        this.mapa.put(key, value);
        return this;
    }

    public  Map<String,String> getMapa(){
        return this.mapa;
    }
}
