/*

 */
package bean;

import controller.BitacoraJPA;
import controller.DonantesJPA;
import entity.Bitacora;
import entity.Donate;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/*
 */
@Named(value = "bitacoraBean")
@SessionScoped
public class bitacoraBean implements Serializable {

    private List<Bitacora> lBitacora = new ArrayList<>();
    private BitacoraJPA bitacoraJPA;
    private BigInteger idbitacora;
    private String tablaafectada;
    private Date fecha;
    private String accionrealizada;

    private Bitacora bitacora;
    private Bitacora editarbita;

    //private Bitacora prueva;
    public List<Bitacora> getlBitacora() {
        return lBitacora;
    }

    public void setlBitacora(List<Bitacora> lBitacora) {
        this.lBitacora = lBitacora;
    }

    public BigInteger getIdbitacora() {
        return idbitacora;
    }

    public void setIdbitacora(BigInteger idbitacora) {
        this.idbitacora = idbitacora;
    }

    public String getTablaafectada() {
        return tablaafectada;
    }

    public void setTablaafectada(String tablaafectada) {
        this.tablaafectada = tablaafectada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAccionrealizada() {
        return accionrealizada;
    }

    public void setAccionrealizada(String accionrealizada) {
        this.accionrealizada = accionrealizada;
    }

    public Bitacora getBitacora() {
        return bitacora;
    }

    public void setBitacora(Bitacora bitacora) {
        this.bitacora = bitacora;
    }

    public Bitacora getEditarbita() {
        return editarbita;
    }

    public void setEditarbita(Bitacora editarbita) {
        this.editarbita = editarbita;
    }

    public bitacoraBean() {
    }

    public List<Bitacora> Getir() {
        bitacoraJPA = new BitacoraJPA();
        lBitacora = bitacoraJPA.getBitacora();
        return lBitacora;
    }

    public void guardarbitacora(String tabla, String accion) {

        Date fecha= new Date();
        bitacora = new Bitacora();
        bitacoraJPA = new BitacoraJPA();
        BigInteger idNuevo = new BigInteger("0");
       // Calendar calendario = GregorianCalendar.getInstance();
        
        idNuevo = bitacoraJPA.getClave();
       // fecha = calendario.getTime();
      
        bitacora.setIdBitacora(idNuevo);
        bitacora.setFechabitacora(fecha);
        bitacora.setTabla(tabla);
        bitacora.setAccion(accion);
        
        bitacoraJPA.insertarbitacoraJPA(bitacora);

        this.Getir();
        this.setIdbitacora(null);
    }

    
}
