/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class EMail1 {
    
    private String remitente;
    private List<String> destinatarios = new ArrayList<>();
    private List<String> cc = new ArrayList<>();
    private List<String> cco = new ArrayList<>();;
    private String asunto;
    private String mensaje;
   // private List<Reporte> reportes = new ArrayList<>();

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public List<String> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<String> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<String> getCco() {
        return cco;
    }

    public void setCco(List<String> cco) {
        this.cco = cco;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    //public List<Reporte> getReportes() {
    //    return reportes;
    //}

    /*public void setReportes(List<Reporte> reportes) {
        this.reportes = reportes;
    }   */      
    
}
