package bean;

import controller.OcupacionJPA;
import entity.Ocupacion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@Named(value = "ocupacionBean")
@SessionScoped
public class OcupacionBean implements Serializable {

    private List<Ocupacion> locupacion = new ArrayList<>();
    private OcupacionJPA ocupacionJPA = new OcupacionJPA();
    private String nombreocupacion;
    private int idocupacion;
    private Ocupacion ocupacion;
    private Ocupacion modOcupacion;
    private String nombreTemOcupacion;
    private Boolean validarOcupacion;

    public String getNombreocupacion() {
        return nombreocupacion;
    }

    public void setNombreocupacion(String nombreocupacion) {
        this.nombreocupacion = nombreocupacion;
    }

    public int getIdocupacion() {
        return idocupacion;
    }

    public void setIdocupacion(int idocupacion) {
        this.idocupacion = idocupacion;
    }

    public Ocupacion getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(Ocupacion ocupacion) {
        this.ocupacion = ocupacion;
    }

    public Ocupacion getModOcupacion() {
        return modOcupacion;
    }

    public void setModOcupacion(Ocupacion modOcupacion) {
        this.modOcupacion = modOcupacion;
    }

    public OcupacionBean() {
    }

    public List<Ocupacion> getir() {
        ocupacionJPA = new OcupacionJPA();
        locupacion = ocupacionJPA.getOcupacion();
        return locupacion;
    }

    public List<Ocupacion> getLocupacion() {
        return locupacion;
    }

    public void setLocupacion(List<Ocupacion> locupacion) {
        this.locupacion = locupacion;
    }

    public void saveOcupacion() {
        ocupacionJPA = new OcupacionJPA();
        ocupacion = new Ocupacion();
        ocupacion.setIdOcupacion(ocupacionJPA.aumentarIdOcupacion() + 1);
        ocupacion.setNombreOcupacion(nombreocupacion);
        if (ocupacionJPA.searchOcupacion(nombreocupacion)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡La Ocupación ya existe!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            ocupacionJPA = new OcupacionJPA();
            ocupacionJPA.saveOcupacion(ocupacion);
            this.getir();
            this.setIdocupacion(0);
            this.setNombreocupacion(null);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Ocupación creada exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            RequestContext.getCurrentInstance().execute("PF('nuevaOcupacion').hide();");
        }
    }

    public void updOcupacion() {
        if (nombreTemOcupacion.contentEquals(modOcupacion.getNombreOcupacion())) {
            ocupacionJPA = new OcupacionJPA();

            ocupacionJPA.updateOcupacion(modOcupacion);

            this.setIdocupacion(0);
            this.setNombreocupacion(null);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Ocupación modificada exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            RequestContext.getCurrentInstance().execute("PF('modOcupacion').hide();");
        } else {
            validarOcupacion();
            if (validarOcupacion) {
                ocupacionJPA = new OcupacionJPA();

                ocupacionJPA.updateOcupacion(modOcupacion);

                this.setIdocupacion(0);
                this.setNombreocupacion(null);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Ocupación modificada exitosamente!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                RequestContext.getCurrentInstance().execute("PF('modOcupacion').hide();");
            }

        }
    }

    public void dltOcupacion(Ocupacion ocup) {
        ocupacionJPA = new OcupacionJPA();
        ocupacionJPA.deleteOcupacion(ocup);

        this.setIdocupacion(0);
        this.setNombreocupacion(null);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Ocupación eliminada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void LeerId(Ocupacion ocup) {
        OcupacionJPA jpa;
        Ocupacion temp;
        try {
            jpa = new OcupacionJPA();
            temp = jpa.LeerIdOcupacion(ocup);
            if (temp != null) {
                this.modOcupacion = temp;
                this.nombreTemOcupacion = temp.getNombreOcupacion();

            }

        } catch (Exception e) {
        }
    }

    public void validarOcupacion() {
        try {
            validarOcupacion = true;
            ocupacionJPA = new OcupacionJPA();
            String ocupacion = "";
            if (this.getNombreocupacion() == null) {
                ocupacion = this.modOcupacion.getNombreOcupacion();
            } else {
                ocupacion = this.getNombreocupacion();
            }
            if (ocupacion.length() > 0) {
                System.out.println("comineza la validacion de Ocupación");
                if (ocupacionJPA.searchOcupacion(ocupacion)) {
                    validarOcupacion = false;
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡La Ocupación ya existe!", null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
        } catch (Exception e) {
        }
    }

}
