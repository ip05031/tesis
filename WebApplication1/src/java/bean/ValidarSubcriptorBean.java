/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.UsuarioJPA;
import entity.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Flever
 */
@Named(value = "validarSubcriptorBean")
@SessionScoped
public class ValidarSubcriptorBean implements Serializable {

    private String claveSub;
    private String texto;

    public ValidarSubcriptorBean() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map params = ec.getRequestParameterMap();
        claveSub = (String) params.get("claveSub");
        char s;
        Usuario us = new Usuario();
        if (claveSub != null) {
            if (!claveSub.isEmpty()) {
                UsuarioJPA uJpa = new UsuarioJPA();
                List<Usuario> listaUsuario = uJpa.getUsuarios(1);
                //http://localhost:8083/WebApplication1/faces/ValidarSubscriptor.xhtml?faces-redirect=true&claveSub=123312Es343a
                for (Usuario u : listaUsuario) {
                    if(u.getClaveValidar()!=null)
                    if (u.getClaveValidar().equals(claveSub)) {
                        s = u.getClaveValidar().charAt(8);
                        int cantidad = u.getApellidosu().length();
                        if (cantidad >= 4) {
                            cantidad = 4;
                        }
                        String corta = u.getApellidosu().substring(cantidad - 1, cantidad);
                        String compara = claveSub.substring(11);
                        Integer ia = Integer.parseInt(s + "");
                        String uno = u.getIdUsuario() + "";
                        uno = uno.substring(0, 1);
                        Integer dos = Integer.parseInt(uno + "");
                        if (ia == dos && compara.equals(corta)) {
                            us = u;
                        }
                    }
                }

            }
        }

        if (us.getNombreu() != null) {

            texto = "Su cuenta ha sido activada con éxito." + us.getNombreu();
            UsuarioJPA uJpa = new UsuarioJPA();
            us.setEstadoi(2);
            uJpa.updateUsuario(us);
        } else {
            texto = "Error su cuenta no puede ser activada";
        }

    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
//Generador de codigo

}
