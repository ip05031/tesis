/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.ArticulosJPA;
import controller.CategoriaJPA;
import controller.ImportarJPA;
import controller.PantallaJPA;
import controller.RevistaJPA;
import controller.RevistasJPA;
import entity.Articulo;
import entity.Categoria;
import entity.Pantalla;
import entity.Revista;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import sun.misc.IOUtils;

/**
 *
 * @author IPalacios
 */
@Named(value = "importacionBean")
@SessionScoped
public class importacionBean implements Serializable {

    ImportarJPA importarJPA;
    List<Revista> listaRevistas = new ArrayList<>();
    private List<Articulo> listaArticulo = new ArrayList<>();

    private String destination = "C:\\us\\sistema";
    private Revista revista = new Revista();
    private Articulo articulo = new Articulo();

    private Part file1;
    private Part file2;
    private int idRevista;
    private String descripcion;
    private String ruta_revista;
    private String ruta_portada;
    private String ruta_articulo;

    private String resumen;

    private InputStream streamPortada;
    private InputStream streamRevista;
    private InputStream streamArticulo;

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public String getRuta_articulo() {
        return ruta_articulo;
    }

    public void setRuta_articulo(String ruta_articulo) {
        this.ruta_articulo = ruta_articulo;
    }

    public String getRuta_revista() {
        return ruta_revista;
    }

    public void setRuta_revista(String ruta_revista) {
        this.ruta_revista = ruta_revista;
    }

    public String getRuta_portada() {
        return ruta_portada;
    }

    public void setRuta_portada(String ruta_portada) {
        this.ruta_portada = ruta_portada;
    }

    private String nombreArchivo = "";

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Part getFile1() {
        return file1;
    }

    public void setFile1(Part file1) {
        this.file1 = file1;
    }

    public Part getFile2() {
        return file2;
    }

    public void setFile2(Part file2) {
        this.file2 = file2;
    }

    public List<Articulo> getListaArticulo() {
        return listaArticulo;
    }

    public void setListaArticulo(List<Articulo> listaArticulo) {
        this.listaArticulo = listaArticulo;
    }

    public int getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(int idRevista) {
        this.idRevista = idRevista;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    /**
     * Creates a new instance of importacionBean
     */
    public importacionBean() {

    }

    public void actualizarTable() {
        System.out.println("***************************************************************");
    }

    public void imprimirMensajePrueba() {
        System.out.println("Esto con el fin de obtener un mensaje");
    }

    public Revista getRevista() {
        return revista;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }

    public List<Articulo> getir() {
        int idRevista = this.idRevista; //this.idRevista;

        if (idRevista == 0) {
            idRevista = 1;
        }
        System.out.println("get IR - idRevista:" + idRevista);
        importarJPA = new ImportarJPA();
        listaArticulo = importarJPA.getListaUnArticulo(idRevista);
        return listaArticulo;
    }

    public void getir2() {

        int idNuevo = this.revista.getIdRevista();
        System.out.println("ID ENCONTRADO:");
        System.out.println(idNuevo);
        //istaArticulo = importarJPA.getArticulo();

        //importarJPA = new ImportarJPA();
        //listaArticulo = importarJPA.getListaUnArticulo(0);
    }

    private static String GetFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public void upload2() {
        String nombre2 = "";
        nombre2 = "/resources/img/" + obtenerNombredeArchivo(file2);
        try {
            file2.write(nombre2);
        } catch (Exception e) {
            System.out.println("Error en upload2");
            Logger.getLogger(importacionBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void uploadSimple() {
        String nombre1 = "../../../../../../../../../../" + obtenerNombredeArchivo(file1);
        try {
            file1.write(nombre1);
            FacesMessage message = new FacesMessage("Succesful", obtenerNombredeArchivo(file1) + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException ex) {
            System.out.println("Error nuevo .............");

            Logger.getLogger(importacionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void upload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream(), 1);

            this.setRuta_portada("" + event.getFile().getFileName());
            System.out.println("se fueeeeeeeeeeeeee");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void upload11(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream(), 2);

            this.setRuta_revista("" + event.getFile().getFileName());
            System.out.println("se fueeeeeeeeeeeeee");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void upload5(FileUploadEvent event) {
        try {
            this.streamPortada = event.getFile().getInputstream();
            this.setRuta_portada(event.getFile().getFileName());
        } catch (IOException ex) {
            Logger.getLogger(importacionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void upload6(FileUploadEvent event) {
        try {
            this.streamRevista = event.getFile().getInputstream();
            this.setRuta_revista(event.getFile().getFileName());
        } catch (IOException ex) {
            Logger.getLogger(importacionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void upload7(FileUploadEvent event) {
        try {
            this.streamArticulo = event.getFile().getInputstream();
            this.setRuta_articulo(event.getFile().getFileName());
        } catch (IOException ex) {
            Logger.getLogger(importacionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarYenviar() {
        /* Copiando Archivos */
        String nombreInventado = "Manual.pdf";
        obtenerRevista(this.idRevista);
        String corte[];
        if (streamPortada.equals(null) || streamRevista.equals(null)) {
            System.out.println("stream portada null");
            return;
        }
        corte = ruta_portada.split(".");
        String nombrePortadaRevista = "Revista-" + this.idRevista + ".jpg";
        String nombreRevista = "Revista-" + this.idRevista + ".pdf";

        copyFile(nombrePortadaRevista, streamPortada, 1);

        copyFile(nombreRevista, streamRevista, 2);

        //copyFile(this.ruta_revista, streamRevista, 2);
        System.out.println("llegamos a guardar y enviar");
        System.out.println(this.descripcion);
        System.out.println(this.idRevista);
        System.out.println(this.ruta_portada);
        System.out.println(this.ruta_revista);

        if (this.descripcion.compareToIgnoreCase("") != 0) {
            revista.setDescripcionr(this.descripcion);
        }

        revista.setImagenpr(nombrePortadaRevista);
        revista.setArchivopr(nombreRevista);
        new RevistasJPA().mergeTipoUsuario(revista);
        limpiarTodo();

    }

    public void guardarArticulo() {
        String nombreArticulo = "";
        System.out.println("Ejemplo");
        
        if( this.ruta_articulo.compareTo("") == 0 ){
            return;
        }
        String numeroArticulo = "" + this.articulo.getIdArticulo();
        String numeroRevista = "" + this.articulo.getIdRevista().getIdRevista();
        nombreArticulo = "Articulo-" + numeroRevista + "-" + numeroArticulo + ".pdf";
        System.out.println("nombre:" + nombreArticulo);
        copyFile(nombreArticulo, streamArticulo, 3);
        if (this.resumen.compareToIgnoreCase("") != 0) {
            this.articulo.setResumena(this.resumen);
        }
        
        this.articulo.setArchivoa(nombreArticulo);
        new ArticulosJPA().mergeTipoUsuario(this.articulo);
        limpiar2();
    }

    public void copyFile(String fileName, InputStream in, int tipo) {
        try {

            // write the inputStream to a FileOutputStream
            String nombreCompleto = "";

            switch (tipo) {
                case 1:
                    nombreCompleto = destination + "\\portadas\\" + fileName;
                    break;
                case 2:
                    nombreCompleto = destination + "\\revistas\\" + fileName;
                    break;
                case 3:
                    nombreCompleto = destination + "\\articulos\\" + fileName;
                    break;
                default:
                    nombreCompleto = destination + "\\perdidos\\" + fileName;
                    break;

            }

            OutputStream out = new FileOutputStream(new File(nombreCompleto));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void subirArchivoSupuestamente(FileUploadEvent event) {
        String nombre = "../../../../../../../../../../" + event.getFile().getFileName();

        try {
            file1.write("" + nombre);
        } catch (IOException ex) {
            Logger.getLogger(importacionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        FacesMessage message = new FacesMessage("Succesful", nombre + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void upload3() {
        System.out.println("Datos para la Base");
        System.out.println("id Revista:");
        System.out.println(this.idRevista);
        System.out.println("Descripcion");
        System.out.println(this.descripcion);
        System.out.println("-----------------------------------------------");

        String nombre1 = "";
        String nombre2 = "";
        String nombremodificado = "";
        String nombremodificado2 = "";
        System.out.println(nombre1);
        System.out.println(nombre2);
        nombre1 = obtenerNombredeArchivo(file1);
        nombre2 = obtenerNombredeArchivo(file2);
        System.out.println("(" + nombre1 + ")");
        System.out.println("(" + nombre2 + ")");
        nombremodificado = "../../../../../../../../../../" + nombre1;
        nombremodificado2 = "../../../../../../../../../../" + nombre2;
        System.out.println("(" + nombremodificado + ")");
        System.out.println("(" + nombremodificado2 + ")");
    }

    public void upload() {
        System.out.println("Datos para la Base");
        System.out.println("id Revista:");
        System.out.println(this.idRevista);
        System.out.println("Descripcion");
        System.out.println(this.descripcion);
        System.out.println("-----------------------------------------------");

        String nombre1 = "";
        String nombre2 = "";
        String nombremodificado = "";
        String nombremodificado2 = "";
        System.out.println(nombre1);
        System.out.println(nombre2);
        nombre1 = obtenerNombredeArchivo(file1);
        nombre2 = obtenerNombredeArchivo(file2);
        System.out.println("(" + nombre1 + ")");
        System.out.println("(" + nombre2 + ")");
        nombremodificado = "../../../../../../../../../../" + nombre1;
        nombremodificado2 = "../../../../../../../../../../" + nombre2;
        System.out.println("(" + nombremodificado + ")");
        System.out.println("(" + nombremodificado2 + ")");

        try {
            file1.write(nombremodificado);
            file2.write(nombremodificado2);
        } catch (IOException ex) {
            System.out.println("Error nuevo .............");
            Logger.getLogger(importacionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        try {
            file1.write("D:\\temp\\" + getFilename(file1));
            file2.write("D:\\temp\\" + getFilename(file2));

        } catch (Exception e) {
            System.out.println("Mensaje de error upload");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getLocalizedMessage());
        }

        return "index"; //el nombre de la pagina .xhtml donde retorna
         */

    }

    public void subirFichero() throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String ruta = "";
        ruta = "C:\\us\\sistema\\revistas\\";

        try {
            inputStream = file1.getInputStream();  //leemos el fichero local
            // write the inputStream to a FileOutputStream
            outputStream = new FileOutputStream(new File(ruta));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private String obtenerNombredeArchivo(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return "";
    }

    public void obtenerRevista(int idrevista) {
        //System.out.println("id : " + origenPantalla.getIdPantalla()+"");
        importarJPA = new ImportarJPA();
        Revista temporal = new Revista();

        try {
            temporal = importarJPA.leerId(idrevista);
            if (temporal != null) {
                this.revista = temporal;
            }
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.getMessage());
        }

    }

    public void guardarRevista() {
        System.out.println("llegamos a guardar");
        System.out.println(this.descripcion);
        System.out.println(this.idRevista);
        System.out.println(this.ruta_portada);
        System.out.println(this.ruta_revista);

        obtenerRevista(this.idRevista);
        revista.setDescripcionr(this.descripcion);
        revista.setImagenpr(this.ruta_portada);
        revista.setArchivopr(this.ruta_revista);
        new RevistasJPA().mergeTipoUsuario(revista);
        limpiarTodo();

    }

    public void limpiarTodo() {
        this.descripcion = "";
        this.idRevista = 1;
        this.ruta_portada = "";
        this.ruta_revista = "";
    }

    public void limpiar2() {
        this.setRuta_articulo("");
        this.setResumen("");

    }

}
