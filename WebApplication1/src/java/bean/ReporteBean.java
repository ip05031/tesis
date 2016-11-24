/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.BitacoraJPA;
import controller.UsuarioJPA;
import entity.Bitacora;
import entity.Usuario;
import java.io.File;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author IPalacios
 */
@Named(value = "reporteBean")
@SessionScoped
public class ReporteBean implements Serializable {

    private List<Bitacora> lBitacora = new ArrayList<>();
    private List<Bitacora> tempBitacora = new ArrayList<>();
    private BitacoraJPA bitacoraJPA;

    private Date desde;
    private Date hasta;
    private String nombreUsuario;
    private Usuario user;
    private String estado;
    //private Bitacora guardar;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    public ReporteBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        user = (Usuario) context.getExternalContext().getSessionMap().get("logueado");
        Getir();
    }

    public void setNombreUsuario() {
        nombreUsuario = user.getNombreu() + " " + user.getApellidosu();
    }
    
    public List<Bitacora> listado() {
    
    return lBitacora;}

    public List<Bitacora> Getir() {
        bitacoraJPA = new BitacoraJPA();
        lBitacora = bitacoraJPA.getBitacora();
        return lBitacora;
    }

    public void filtro() {
        
        lBitacora.addAll(tempBitacora);
        this.tempBitacora.clear();
        for (Bitacora bi : lBitacora) {
            if ((desde.before(bi.getFechabitacora()) && hasta.after(bi.getFechabitacora()))) {
            this.tempBitacora.add(bi);
            
            }
        }
        lBitacora.removeAll(tempBitacora);

    }

    public void guardarReporte() {

        //eventosJPA = new EventosJPA();
        // guardar = new Bitacora();
        // Usuario de = new Usuario();
        //Date hora = new Date();
        //de.setIdUsuario(Usuario);
        //guardar.setD(lugarevento);
        //guardar.setFechae(fechaevento);
        //eventosJPA.guardareventoJPA(nomevento);
        this.Getir();
        this.setDesde(null);
        this.setHasta(null);

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Evento Almacenado exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    /*--------------------------REPORTE DESCARGAS-----------------------------------------------------*/
    public void exportarPDF() {
        System.out.println("exportar PDF");
        String realPath = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = "Nombre Usuario";

        parametros.put("date_desde", this.desde);
        parametros.put("date_hasta", this.hasta);
        parametros.put("usuario", usuario);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReporteDescargas.jasper");
        
        archivo = new File(realPath);

        try {
            conect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/baseMuna", "postgres", "muna2015");
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    archivo.getPath(),
                    parametros,
                    conect
            );
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=ReporteDescargas.pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");
        } catch (Exception ex) {
            System.out.println("Error : 1");
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            //Logger.getLogger(ReporteBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fin error 1");
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");
        }

    }

    /*--------------------------REPORTE DE PUBLICACIONES-----------------------------------------------------*/
    public void reportPublicaciones() {
        System.out.println("exportar PDF");
        String realPath = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = "Nombre Usuario";

        parametros.put("date_desde", this.desde);
        parametros.put("date_hasta", this.hasta);
        parametros.put("usuario", usuario);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/RevistaPMensual.jasper");

        archivo = new File(realPath);

        try {
            conect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/baseMuna", "postgres", "muna2015");
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    archivo.getPath(),
                    parametros,
                    conect
            );
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=RevistaPMensual.pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");
        } catch (Exception ex) {
            System.out.println("Error : 1");
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            Logger.getLogger(ReporteBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fin error 1");
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");
        }

    }

    /*-------------------------------------------------------------------------------------------------------*/
 /*-------------------------Reporte de Estado Revista-----------------------------------------------------*/
    public void reportEstadoRevista() {
        System.out.println("exportar PDF");
        String realPath = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = "Nombre Usuario";
        
        parametros.put("estado", this.estado);
        
        parametros.put("usuario", usuario);
         
        if(estado.compareTo("Todos")==0)   
        {
           
           realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReporteEstadoT.jasper");
      
        archivo = new File(realPath);  
        }
        else
        {
          realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReporteEstado.jasper");
        
        archivo = new File(realPath);   
        }
        
       

        try {
            conect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/baseMuna", "postgres", "muna2015");
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    archivo.getPath(),
                    parametros,
                    conect
            );
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=EstadoRevista.pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");
        } catch (Exception ex) {
            System.out.println("Error : 1");
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            Logger.getLogger(ReporteBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fin error 1");
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");
        }

    }

    /*------------------------------------------------------------------------------------------------------*/
 /*-------------------------Reporte de Bitacora-----------------------------------------------------*/
    public void reportBitacora() {
        System.out.println("exportar PDF");
        String realPath = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = "Nombre Usuario";

        parametros.put("date_desde", this.desde);
        parametros.put("date_hasta", this.hasta);
        parametros.put("usuario", usuario);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReporteBitacora.jasper");

        archivo = new File(realPath);

        try {
            conect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/baseMuna", "postgres", "muna2015");
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    archivo.getPath(),
                    parametros,
                    conect
            );

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=ReporteBitacora.pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");

        } catch (Exception ex) {
            System.out.println("Error : 1");
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            Logger.getLogger(ReporteBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fin error 1");
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");

        }

    }

    /*------------------------------------------------------------------------------------------------------*/
    
    /*-------------------------Reporte de visitas-----------------------------------------------------*/
    public void reportVisitas() {
        System.out.println("exportar PDF");
        String realPath = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = "Nombre Usuario";

        parametros.put("date_desde", this.desde);
        parametros.put("date_hasta", this.hasta);
        parametros.put("usuario", usuario);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReporteVisitas.jasper");

        archivo = new File(realPath);

        try {
            conect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/baseMuna", "postgres", "muna2015");
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    archivo.getPath(),
                    parametros,
                    conect
            );

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=ReporteVisitas.pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");

        } catch (Exception ex) {
            System.out.println("Error : 1");
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            Logger.getLogger(ReporteBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fin error 1");
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");

        }

    }

    /*------------------------------------------------------------------------------------------------------*/
    
    
 /*-------------------------Reporte de Adquisiciones-----------------------------------------------------*/
    public void reportAdquisiciones() {
        System.out.println("exportar PDF");
        String realPath = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = "Nombre Usuario";

        parametros.put("date_desde", this.desde);
        parametros.put("date_hasta", this.hasta);
        parametros.put("usuario", usuario);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReportAdquisiciones.jasper");

        archivo = new File(realPath);

        try {
            conect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/baseMuna", "postgres", "muna2015");
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    archivo.getPath(),
                    parametros,
                    conect
            );
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=ReporteAdquisiciones.pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");
        } catch (Exception ex) {
            System.out.println("Error : 1");
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            Logger.getLogger(ReporteBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fin error 1");
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");
        }

    }

    /*------------------------------------------------------------------------------------------------------*/
 /*-------------------------Reporte de Prestamos-----------------------------------------------------*/
    public void reportPrestamos() {
        System.out.println("exportar PDF");
        String realPath = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = "Nombre Usuario";

        parametros.put("date_desde", this.desde);
        parametros.put("date_hasta", this.hasta);
        parametros.put("usuario", usuario);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReportePrestamos.jasper");
       
        archivo = new File(realPath);

        try {
            conect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/baseMuna", "postgres", "muna2015");
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    archivo.getPath(),
                    parametros,
                    conect
            );
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=ReportePrestamos.pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");
        } catch (Exception ex) {
            System.out.println("Error : 1");
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            Logger.getLogger(ReporteBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fin error 1");
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");
        }

    }

    /*------------------------------------------------------------------------------------------------------*/
    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

}
