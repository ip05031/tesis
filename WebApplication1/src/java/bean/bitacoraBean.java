/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.bitacoraJPA;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author IPalacios
 */
@Named(value = "bitacoraBean")
@SessionScoped
public class bitacoraBean implements Serializable {
    bitacoraJPA bitacoraJPA = new bitacoraJPA();
    /**
     * Creates a new instance of bitacoraBean
     */
    public bitacoraBean() {
    }
    
    public void obtenerIdAumentado(){
        bitacoraJPA = new bitacoraJPA();
        System.out.println("Buscando id ...");
        BigInteger idNuevo = new BigInteger("0");
        idNuevo = bitacoraJPA.getClave();
        System.out.println("nuevo id");
        System.out.println(idNuevo);
        System.out.println("Todo salió bien");
    }
    
}
