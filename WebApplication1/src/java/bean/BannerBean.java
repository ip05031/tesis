/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.File;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author IPalacios
 */
@Named(value = "bannerBean")
@SessionScoped
public class BannerBean implements Serializable {

    /**
     * Creates a new instance of BannerBean
     */
    public BannerBean() {
    }

    public void borrarFichero() {
        File fichero = new File("C:\\us\\sistema\\banner\\revista.pdf");
        if (fichero.exists()) {
            System.out.println("El fichero  existe");
            if (fichero.delete()) {
                System.out.println("El fichero ha sido borrado satisfactoriamente");
            } else {
                System.out.println("El fichero no puede ser borrado");
            }
        } else {
            System.out.println("Pues va a ser que no");
        }

    }

    public void leerDirectorio() {
        String sDirectorio = "C:\\us\\sistema\\banner";
        File f = new File(sDirectorio);
        if (f.exists()) {
            File[] ficheros = f.listFiles();
            for (int x = 0; x < ficheros.length; x++) {
                System.out.println(ficheros[x].getName());
            }
        } else {
            System.out.println("no existe");
        }
    }
}
