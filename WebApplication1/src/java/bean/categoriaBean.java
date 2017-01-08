/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.CategoriaJPA;
import controller.PantallaJPA;
import entity.Categoria;
import entity.Pantalla;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@Named(value = "categoriaBean")
@SessionScoped
public class categoriaBean implements Serializable {

    private List<Categoria> lcategoria = new ArrayList<>();
    private CategoriaJPA categoriaJPA = new CategoriaJPA();
    private String nombreCategoria;
    private int idCategoria;
    private Categoria categoria;
    private Categoria modCategoria;

    public Categoria getModCategoria() {
        return modCategoria;
    }

    public void setModCategoria(Categoria modCategoria) {
        this.modCategoria = modCategoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public categoriaBean() {

    }

    public List<Categoria> getir() {
        categoriaJPA = new CategoriaJPA();
        lcategoria = categoriaJPA.getCategoria();
        return lcategoria;
    }

    public List<Categoria> getLcategoria() {
        return lcategoria;
    }

    public void setLcategoria(List<Categoria> lcategoria) {
        this.lcategoria = lcategoria;
    }

    public void saveCategoria() {
        categoriaJPA = new CategoriaJPA();
        categoria = new Categoria();
        categoria.setIdCategoria(categoriaJPA.aumentarIdCategoria() + 1);
        categoria.setNombrec(nombreCategoria);
        if (categoriaJPA.searchCategora(nombreCategoria)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Esa categoría ya existe!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            categoriaJPA = new CategoriaJPA();
            categoriaJPA.saveCategoria(categoria);
            this.getir();
            this.setIdCategoria(0);
            this.setNombreCategoria(null);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Categoria creada exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            RequestContext.getCurrentInstance().execute("PF('nuevaCategoria').hide();");
        }

    }

    public void updCategoria() {
        categoriaJPA = new CategoriaJPA();

        categoriaJPA.updateCategoria(modCategoria);

        this.setIdCategoria(0);
        this.setNombreCategoria(null);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Categoria modificada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
         RequestContext.getCurrentInstance().execute("PF('modCategoria').hide();");
    }

    public void dltCategoria(Categoria cat) {
        categoriaJPA = new CategoriaJPA();
        categoriaJPA.deleteCategoria(cat);

        this.setIdCategoria(0);
        this.setNombreCategoria(null);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Categoria eliminada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void LeerId(Categoria cat) {
        CategoriaJPA jpa;
        Categoria temp;
        try {
            jpa = new CategoriaJPA();
            temp = jpa.LeerIdCategoria(cat);
            if (temp != null) {
                this.modCategoria = temp;

            }

        } catch (Exception e) {
        }
    }

    public void validarCategoria() {
        categoriaJPA = new CategoriaJPA();
        String categoria = this.getNombreCategoria();
        if (categoria.length() > 0) {
            System.out.println("comineza la validacion de categoria");
            if (categoriaJPA.searchCategora(categoria)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Esa categoría ya existe!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

}
