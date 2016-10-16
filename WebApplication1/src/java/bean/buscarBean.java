/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author IPalacios
 */
@Named(value = "buscarBean")
@SessionScoped
public class buscarBean implements Serializable {

    String cadena ;
    private String textoResultado;

    public buscarBean() {
    }

    public void buscarPalabras() {
        String palabra = "sql";
        String texto = "lenguaje sql";
        cadena = "El padre Ángel se incorporó con un esfuerzo solemne. Se frotó los párpados"
            + "con los huesos de las manos, apartó el mosquitero de punto y permaneció sentado en"
            + "la estera pelada, pensativo un instante, el tiempo indispensable para darse cuenta "
            + "de que estaba vivo, y para recordar la fecha y su correspondencia en el santoral. "
            + "Martes cuatro de octubre», pensó; y dijo en voz baja: San Francisco de Asís.";
        boolean resultado = cadena.contains(palabra);

        if (resultado) {
            System.out.println("palabra encontrada");
        } else {
            System.out.println("palabra no encontrada");
        }
        
       this.setTextoResultado(textoResultado);
    }

    public String getTextoResultado() {
        return textoResultado;
    }

    public void setTextoResultado(String textoResultado) {
        this.textoResultado = textoResultado;
    }

}
