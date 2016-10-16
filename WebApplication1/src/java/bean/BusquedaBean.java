/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.PalabrasClavesJPA;
import controller.RevistasJPA;
import entity.Articulo;
import entity.PalabraClave;
import entity.Revista;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;

/**
 *
 * @author Flever
 */
@Named(value = "busquedaBean")
@SessionScoped
public class BusquedaBean implements Serializable {

    private String studentId;
    private String buscar;
    private List<String> hojasRevista;

    private String busquedaN;
    private String busquedaAutor;
    private String busquedaTitulo;
    private String busquedaArticulo;
    private String busquedaCategoria;
    private String busquedadPalabraClave;
    private List<Revista> revistasEncontradas;
    private Revista revista;
    private List<String> temPalabra;
    private List<PalabraClave> listaPalabras;

    private List<Revista> listaBusqueda;

    FacesContext context = FacesContext.getCurrentInstance();

    public String buscar() {
        listaBusqueda();

        return "Busquedas?faces-redirect=true";
    }

    public String buscarLick() {
        RevistasJPA revis = new RevistasJPA();
        List<String> temPalabra = new ArrayList<>();
        temPalabra.add(busquedadPalabraClave);
        studentId ="";
        busquedaN = "";
        busquedaAutor = "";
        busquedaTitulo = "";
        busquedaArticulo = "";
        busquedaCategoria = "";
        
        this.revistasEncontradas = revis.getRevistasBusqueda(studentId, busquedaArticulo, busquedaTitulo, busquedaAutor, busquedaCategoria, temPalabra);

        return "Busquedas?faces-redirect=true";
    }

    public String buscarSimple() {
        busquedaN = "";
        busquedaAutor = "";
        busquedaTitulo = "";
        busquedaArticulo = "";
        busquedaCategoria = "";
        busquedadPalabraClave = "";
        listaBusqueda();

        return "Busquedas?faces-redirect=true";
    }

    public BusquedaBean() {

        hojasRevista = new ArrayList<>();
        hojasRevista.add("vb1.jpg");
        hojasRevista.add("vb11.jpg");
        hojasRevista.add("vb13.jpg");
        hojasRevista.add("vb14.jpg");
        hojasRevista.add("vb15.jpg");
        hojasRevista.add("vb17.jpg");
        hojasRevista.add("vb18.jpg");
        hojasRevista.add("vb19.jpg");
        //studentId = context.getExternalContext().getRequestParameterMap().get("studentId");

    }

    public Revista getRevista() {
        return revista;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }

    public List<Revista> getRevistasEncontradas() {
        return revistasEncontradas;
    }

    public void setRevistasEncontradas(List<Revista> revistasEncontradas) {
        this.revistasEncontradas = revistasEncontradas;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public List getHojasRevista() {
        return hojasRevista;
    }

    public void setHojasRevista(List hojasRevista) {
        this.hojasRevista = hojasRevista;
    }

    public String getBusquedaN() {
        return busquedaN;
    }

    public void setBusquedaN(String busquedaN) {
        this.busquedaN = busquedaN;
    }

    public String getBusquedaAutor() {
        return busquedaAutor;
    }

    public void setBusquedaAutor(String busquedaAutor) {
        this.busquedaAutor = busquedaAutor;
    }

    public String getBusquedaTitulo() {
        return busquedaTitulo;
    }

    public void setBusquedaTitulo(String busquedaTitulo) {
        this.busquedaTitulo = busquedaTitulo;
    }

    public String getBusquedaArticulo() {
        return busquedaArticulo;
    }

    public void setBusquedaArticulo(String busquedaArticulo) {
        this.busquedaArticulo = busquedaArticulo;
    }

    public String getBusquedaCategoria() {
        return busquedaCategoria;
    }

    public void setBusquedaCategoria(String busquedaCategoria) {
        this.busquedaCategoria = busquedaCategoria;
    }

    public String getBusquedadPalabraClave() {
        return busquedadPalabraClave;
    }

    public void setBusquedadPalabraClave(String busquedadPalabraClave) {
        this.busquedadPalabraClave = busquedadPalabraClave;
    }

    public List<String> getTemPalabra() {
        return temPalabra;
    }

    public void setTemPalabra(List<String> temPalabra) {
        this.temPalabra = temPalabra;
    }

    public List<PalabraClave> getListaPalabras() {
        return listaPalabras;
    }

    public void setListaPalabras(List<PalabraClave> listaPalabras) {
        this.listaPalabras = listaPalabras;
    }

    public List<String> listaLickPalabra(List<Articulo> parametro) {
        PalabrasClavesJPA palabraJPA;
        palabraJPA = new PalabrasClavesJPA();
        List<String> temporal = new ArrayList<>();
        for (Articulo ar : parametro) {
            for (PalabraClave p : ar.getPalabraClaveList()) {
                temporal.add(p.getNombrep());
            }
            //temporal.addAll(palabraJPA.getListaLicknombres(ar.getIdArticulo()));
        }

        return temporal;
    }

    public String imagen(String ima) {
        if (ima.isEmpty()) {
            return "vb1.jpg";
        } else {
            return ima;
        }
    }

    public String nombreCategori(Revista re) {
        return re.getCategoriaList().get(0).getNombrec();
    }

    public void listaBusqueda() {
        RevistasJPA revis = new RevistasJPA();

        this.revistasEncontradas = revis.getRevistasBusqueda(studentId, busquedaArticulo, busquedaTitulo, busquedaAutor, busquedaCategoria, temPalabra);

    }

    public List<Revista> getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List<Revista> listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }
}
