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
import java.text.DateFormat;
import java.text.ParseException;
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
    private List<String> añoss;
    private Date desde;
    private Date hasta;
    private String nombreUsuario;
    private Usuario user;
    private String estado;
    private int opcion;
    private Boolean fechaActiva2Tri;
    private Boolean fechaActiva3Tri;
    private Boolean fechaActiva4Tri;
    private Boolean fechaActiva2Sem;
    private String seleccionAnio;

    private Date selectedDate;

    public String getSeleccionAnio() {
        return seleccionAnio;
    }

    public void setSeleccionAnio(String seleccionAnio) {
        this.seleccionAnio = seleccionAnio;
    }

    public Boolean getFechaActiva2Tri() {
        return fechaActiva2Tri;
    }

    public void setFechaActiva2Tri(Boolean fechaActiva2Tri) {
        this.fechaActiva2Tri = fechaActiva2Tri;
    }

    public Boolean getFechaActiva3Tri() {
        return fechaActiva3Tri;
    }

    public void setFechaActiva3Tri(Boolean fechaActiva3Tri) {
        this.fechaActiva3Tri = fechaActiva3Tri;
    }

    public Boolean getFechaActiva4Tri() {
        return fechaActiva4Tri;
    }

    public void setFechaActiva4Tri(Boolean fechaActiva4Tri) {
        this.fechaActiva4Tri = fechaActiva4Tri;
    }

    public Boolean getFechaActiva2Sem() {
        return fechaActiva2Sem;
    }

    public void setFechaActiva2Sem(Boolean fechaActiva2Sem) {
        this.fechaActiva2Sem = fechaActiva2Sem;
    }

    public List<String> getAñoss() {
        return añoss;
    }

    public void setAñoss(List<String> añoss) {
        this.añoss = añoss;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ReporteBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        user = (Usuario) context.getExternalContext().getSessionMap().get("logueado");
        setNombreUsuario();
        System.out.println(this.estado);
        añoss = new ArrayList<>();
        Calendar c1 = Calendar.getInstance();
        String annio = Integer.toString(c1.get(Calendar.YEAR));
        int ayoo = Integer.parseInt(annio);
        System.out.println(ayoo);
        for (int i = 2016; i <= ayoo; i++) {
            añoss.add(i + "");
        }
        this.seleccionAnio = ayoo + "";
        this.validarOpcionesPeriodos();

    }

    public void setNombreUsuario() {
        if (user == null) {
            nombreUsuario = "Nombre Apellido";

        } else {
            nombreUsuario = user.getNombreu() + " " + user.getApellidosu();
        }
    }

    public List<Bitacora> listado() {

        return lBitacora;
    }

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

        String usuario = nombreUsuario;

        parametros.put("date_desde", this.desde);
        parametros.put("date_hasta", this.hasta);
        parametros.put("usuario", usuario);
        if (this.estado.compareTo("todas") == 0) {
            realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReporteDescargasT.jasper");

        }
        if (this.estado.compareTo("diarias") == 0) {
            realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReporteDescargas.jasper");

        }

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
        String reportePDF = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = nombreUsuario;

        parametros.put(
                "date_desde", this.desde);
        parametros.put(
                "date_hasta", this.hasta);
        System.out.println(
                this.desde);

        parametros.put(
                "usuario", usuario);

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

    /*--------------------------REPORTE DE PUBLICACIONES X MES-----------------------------------------------------*/
    public void reportPublicacionesxMes() {
        System.out.println("exportar PDF");
        String realPath = "";
        String reportePDF = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = nombreUsuario;

        DateFormat formatter = new SimpleDateFormat("MM");
        double today = Double.parseDouble(formatter.format(this.desde));

        DateFormat formatter2 = new SimpleDateFormat("yyyy");
        double today2 = Double.parseDouble(formatter2.format(this.desde));

        parametros.put(
                "date_desde", today);
        parametros.put(
                "date_hasta", today2);
        System.out.println(
                this.desde);
        System.out.println(today);

        System.out.println(today2);

        parametros.put(
                "usuario", usuario);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/RevistaPMensual_xMes.jasper");

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

    /*--------------------------REPORTE DE PUBLICACIONES X 3M-----------------------------------------------------*/
    public void reportPublicacionesx3M() throws ParseException {
        System.out.println("exportar PDF");
        String realPath = "";
        String reportePDF = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = nombreUsuario;
        int contador = 0;
        
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //int annio = 2016;
        String periodo = "";
        String fecha1 = "";
        String fecha2 = "";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
        Date date2 = null;

        if (Integer.parseInt(this.estado) == 1) {
            periodo = "primer trimestre";
            contador = Integer.parseInt(this.estado) + 2;

            fecha1 = "01/" + this.estado + "/" + seleccionAnio;
            fecha2 = "31/" + contador + "/" + seleccionAnio;
            date1 = formato.parse(fecha1);
            date2 = formato.parse(fecha2);
            date2 = cambioDeFecha(date2);

            System.out.println(seleccionAnio);
            System.out.println(date1 + "  caso1");
            System.out.println(date2 + "  caso1");
        }
        if (Integer.parseInt(this.estado) == 4) {
            periodo = "segundo trimestre";
            contador = Integer.parseInt(this.estado) + 2;
            fecha1 = "01/" + this.estado + "/" + seleccionAnio;
            fecha2 = "30/" + contador + "/" + seleccionAnio;
            date1 = formato.parse(fecha1);
            date2 = formato.parse(fecha2);
            date2 = cambioDeFecha(date2);
            System.out.println(seleccionAnio);
            System.out.println(date1 + "  caso2");
            System.out.println(date2 + "  caso2");

        }
        if (Integer.parseInt(this.estado) == 7) {
            periodo = "tercer trimestre";
            contador = Integer.parseInt(this.estado) + 2;
            fecha1 = "01/" + this.estado + "/" + seleccionAnio;
            fecha2 = "30/" + contador + "/" + seleccionAnio;
            date1 = formato.parse(fecha1);
            date2 = formato.parse(fecha2);
            date2 = cambioDeFecha(date2);
            System.out.println(seleccionAnio);
            System.out.println(date1 + "  caso3");
            System.out.println(date2 + "  caso3");

        }
        if (Integer.parseInt(this.estado) == 10) {
            periodo = "cuarto trimestre";
            contador = Integer.parseInt(this.estado) + 2;
            fecha1 = "01/" + this.estado + "/" + seleccionAnio;
            fecha2 = "31/" + contador + "/" + seleccionAnio;
            date1 = formato.parse(fecha1);
            date2 = formato.parse(fecha2);
            date2 = cambioDeFecha(date2);
            System.out.println(seleccionAnio);
            System.out.println(date1 + "  caso4");
            System.out.println(date2 + "  caso4");
        }

        parametros.put(
                "date_desde", date1);
        parametros.put(
                "date_hasta", date2);

        parametros.put(
                "usuario", usuario);
        parametros.put(
                "periodo", periodo);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/RevistaPMensual_xMes3.jasper");

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
            this.setSeleccionAnio(null);
            this.setNombreUsuario("");
        }

    }

    /*--------------------------REPORTE DE PUBLICACIONES X 6M-----------------------------------------------------*/
    public void reportPublicacionesx6M() throws ParseException {
        System.out.println("exportar PDF");
        String realPath = "";
        String reportePDF = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = nombreUsuario;
        int contador = 0;
        
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //int annio = 2016;
        String fecha1 = "";
        String fecha2 = "";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
        Date date2 = null;

        String periodo = "";

        if (Integer.parseInt(this.estado) == 1) {
            periodo = "primer semestre";
            contador = Integer.parseInt(this.estado) + 5;

            fecha1 = "01/" + this.estado + "/" + seleccionAnio;
            fecha2 = "30/" + contador + "/" + seleccionAnio;
            date1 = formato.parse(fecha1);
            date2 = formato.parse(fecha2);
            date2 = cambioDeFecha(date2);
            System.out.println(seleccionAnio);
            System.out.println(date1 + "  caso1");
            System.out.println(date2 + "  caso1");
        }
        if (Integer.parseInt(this.estado) == 7) {
            periodo = "segundo semestre";
            contador = Integer.parseInt(this.estado) + 5;
            fecha1 = "01/" + this.estado + "/" + seleccionAnio;
            fecha2 = "31/" + contador + "/" + seleccionAnio;
            date1 = formato.parse(fecha1);
            date2 = formato.parse(fecha2);
            date2 = cambioDeFecha(date2);
            System.out.println(seleccionAnio);
            System.out.println(date1 + "  caso2");
            System.out.println(date2 + "  caso2");

        }

        parametros.put(
                "date_desde", date1);
        parametros.put(
                "date_hasta", date2);

        parametros.put(
                "usuario", usuario);
        parametros.put(
                "periodo", periodo);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/RevistaPMensual_xMes3.jasper");

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
            this.setSeleccionAnio(null);
            this.setNombreUsuario("");
        }

    }

    /*-------------------------------------------------------------------------------------------------------*/
 /*--------------------------REPORTE DE PUBLICACIONES X 1anio-----------------------------------------------------*/
    public void reportPublicacionesx12M() throws ParseException {
        System.out.println("exportar PDF");
        String realPath = "";
        String reportePDF = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        

        String usuario = nombreUsuario;
        int contador = 0;
        Date fechaActual = new Date();
        
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //int annio = 2016;
        String periodo = "";
        String fecha1 = "";
        String fecha2 = "";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
        Date date2 = null;
        periodo = "periodo anual";
        fecha1 = "01/01/" + seleccionAnio;
        fecha2 = "31/12/" + seleccionAnio;
        date1 = formato.parse(fecha1);
        date2 = formato.parse(fecha2);
        date2 = cambioDeFecha(date2);
        System.out.println(seleccionAnio);
        System.out.println(date1 + "  caso1");
        System.out.println(date2 + "  caso1");

        parametros.put(
                "date_desde", date1);
        parametros.put(
                "date_hasta", date2);

        parametros.put(
                "usuario", usuario);
        parametros.put(
                "periodo", periodo);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/RevistaPMensual_xMes3.jasper");

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
            this.setSeleccionAnio(null);
            this.setNombreUsuario("");
        }

    }

    /*-------------------------------------------------------------------------------------------------------*/
 /*-------------------------------------------------------------------------------------------------------*/
 /*-------------------------Reporte de Estado Revista-----------------------------------------------------*/
    public void reportEstadoRevista() {
        System.out.println("exportar PDF");
        String realPath = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = nombreUsuario;

        parametros.put("estado", this.estado);

        parametros.put("usuario", usuario);

        if (estado.compareTo("Todos") == 0) {

            realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReporteEstadoT.jasper");

            archivo = new File(realPath);
        } else {
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
            Logger
                    .getLogger(ReporteBean.class
                            .getName()).log(Level.SEVERE, null, ex);
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

        String usuario = nombreUsuario;

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
            Logger
                    .getLogger(ReporteBean.class
                            .getName()).log(Level.SEVERE, null, ex);
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

        String usuario = nombreUsuario;

        parametros.put("date_desde", this.desde);
        parametros.put("date_hasta", this.hasta);
        parametros.put("usuario", usuario);
        System.out.println(this.estado.compareTo("todas"));
        if (this.estado.compareTo("todas") == 0) {
            realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReporteVisitasII.jasper");

        }
        if (this.estado.compareTo("diarias") == 0) {
            realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReporteVisitas.jasper");

        }
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
            Logger
                    .getLogger(ReporteBean.class
                            .getName()).log(Level.SEVERE, null, ex);
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

        String usuario = nombreUsuario;

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
            Logger
                    .getLogger(ReporteBean.class
                            .getName()).log(Level.SEVERE, null, ex);
            System.out.println("fin error 1");
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");
        }

    }

    /*-------------------------Reporte de AdquisicionesxMes-----------------------------------------------------*/
    public void reportAdquisicionesxMes() {
        System.out.println("exportar PDF");
        String realPath = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = nombreUsuario;
        DateFormat formatter = new SimpleDateFormat("MM");
        double today = Double.parseDouble(formatter.format(this.desde));

        DateFormat formatter2 = new SimpleDateFormat("yyyy");
        double today2 = Double.parseDouble(formatter2.format(this.desde));

        parametros.put(
                "date_desde", today);
        parametros.put(
                "date_hasta", today2);
        System.out.println(
                this.desde);
        System.out.println(today);

        System.out.println(today2);

        parametros.put(
                "usuario", usuario);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReportAdquisicionesxMes.jasper");

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
            Logger
                    .getLogger(ReporteBean.class
                            .getName()).log(Level.SEVERE, null, ex);
            System.out.println("fin error 1");
            this.setDesde(null);
            this.setHasta(null);
            this.setNombreUsuario("");
        }

    }

    /*-------------------------Reporte de Adquisicionesx3M-----------------------------------------------------*/
    public void reportAdquisicionesx3M() throws ParseException {
        System.out.println("exportar PDF");
        String realPath = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = nombreUsuario;
        int contador = 0;
        
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //int annio = 2016;
        String periodo = "";
        String fecha1 = "";
        String fecha2 = "";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
        Date date2 = null;

        if (Integer.parseInt(this.estado) == 1) {
            periodo = "primer trimestre";
            contador = Integer.parseInt(this.estado) + 2;

            fecha1 = "01/" + this.estado + "/" + seleccionAnio;
            fecha2 = "31/" + contador + "/" + seleccionAnio;
            date1 = formato.parse(fecha1);
            date2 = formato.parse(fecha2);
            date2 = cambioDeFecha(date2);
            System.out.println(seleccionAnio);
            System.out.println(date1 + "  caso1");
            System.out.println(date2 + "  caso1");
        }
        if (Integer.parseInt(this.estado) == 4) {
            periodo = "segundo trimestre";
            contador = Integer.parseInt(this.estado) + 2;
            fecha1 = "01/" + this.estado + "/" + seleccionAnio;
            fecha2 = "30/" + contador + "/" + seleccionAnio;
            date1 = formato.parse(fecha1);
            date2 = formato.parse(fecha2);
            date2 = cambioDeFecha(date2);
            System.out.println(seleccionAnio);
            System.out.println(date1 + "  caso2");
            System.out.println(date2 + "  caso2");

        }
        if (Integer.parseInt(this.estado) == 7) {
            periodo = "tercer trimestre";
            contador = Integer.parseInt(this.estado) + 2;
            fecha1 = "01/" + this.estado + "/" + seleccionAnio;
            fecha2 = "30/" + contador + "/" + seleccionAnio;
            date1 = formato.parse(fecha1);
            date2 = formato.parse(fecha2);
            date2 = cambioDeFecha(date2);
            System.out.println(seleccionAnio);
            System.out.println(date1 + "  caso3");
            System.out.println(date2 + "  caso3");

        }
        if (Integer.parseInt(this.estado) == 10) {
            periodo = "cuarto trimestre";
            contador = Integer.parseInt(this.estado) + 2;
            fecha1 = "01/" + this.estado + "/" + seleccionAnio;
            fecha2 = "31/" + contador + "/" + seleccionAnio;
            date1 = formato.parse(fecha1);
            date2 = formato.parse(fecha2);
            System.out.println(seleccionAnio);
            System.out.println(date1 + "  caso4");
            System.out.println(date2 + "  caso4");
        }

        parametros.put(
                "date_desde", date1);
        parametros.put(
                "date_hasta", date2);

        parametros.put(
                "usuario", usuario);
        parametros.put(
                "periodo", periodo);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReportAdquisicionesxMes3.jasper");

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
            Logger
                    .getLogger(ReporteBean.class
                            .getName()).log(Level.SEVERE, null, ex);
            System.out.println("fin error 1");
            this.setDesde(null);
            this.setHasta(null);
            this.setSeleccionAnio(null);
            this.setNombreUsuario("");
        }

    }

    /*-------------------------Reporte de Adquisicionesx6M-----------------------------------------------------*/
    public void reportAdquisicionesx6M() throws ParseException {
        System.out.println("exportar PDF");
        String realPath = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = nombreUsuario;
        int contador = 0;
       
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //int annio = 2016;
        String fecha1 = "";
        String fecha2 = "";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
        Date date2 = null;
        String periodo = "";

        if (Integer.parseInt(this.estado) == 1) {
            periodo = "primer semestre";
            contador = Integer.parseInt(this.estado) + 5;

            fecha1 = "01/" + this.estado + "/" + seleccionAnio;
            fecha2 = "30/" + contador + "/" + seleccionAnio;
            date1 = formato.parse(fecha1);
            date2 = formato.parse(fecha2);
            date2 = cambioDeFecha(date2);
            System.out.println(seleccionAnio);
            System.out.println(date1 + "  caso1");
            System.out.println(date2 + "  caso1");
        }
        if (Integer.parseInt(this.estado) == 7) {
            periodo = "segundo semestre";
            contador = Integer.parseInt(this.estado) + 5;
            fecha1 = "01/" + this.estado + "/" + seleccionAnio;
            fecha2 = "31/" + contador + "/" + seleccionAnio;
            date1 = formato.parse(fecha1);
            date2 = formato.parse(fecha2);
            date2 = cambioDeFecha(date2);
            System.out.println(seleccionAnio);
            System.out.println(date1 + "  caso2");
            System.out.println(date2 + "  caso2");

        }

        parametros.put(
                "date_desde", date1);
        parametros.put(
                "date_hasta", date2);

        parametros.put(
                "usuario", usuario);
        parametros.put(
                "periodo", periodo);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReportAdquisicionesxMes3.jasper");

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
            Logger
                    .getLogger(ReporteBean.class
                            .getName()).log(Level.SEVERE, null, ex);
            System.out.println("fin error 1");
            this.setDesde(null);
            this.setHasta(null);
            this.setSeleccionAnio(null);
            this.setNombreUsuario("");
        }

    }

    /*-------------------------Reporte de Adquisicionesx12M-----------------------------------------------------*/
    public void reportAdquisicionesx12M() throws ParseException {
        System.out.println("exportar PDF");
        String realPath = "";
        File archivo;
        Connection conect;

        Map<String, Object> parametros = new HashMap<String, Object>();
        Calendar calendario = GregorianCalendar.getInstance();

        String usuario = nombreUsuario;
        int contador = 0;
        
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //int annio = 2016;
        String periodo = "";
        String fecha1 = "";
        String fecha2 = "";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
        Date date2 = null;
        periodo = "periodo anual";
        fecha1 = "01/01/" + seleccionAnio;
        fecha2 = "31/12/" + seleccionAnio;
        date1 = formato.parse(fecha1);
        date2 = formato.parse(fecha2);
        date2 = cambioDeFecha(date2);
        System.out.println(seleccionAnio);
        System.out.println(date1 + "  caso1");
        System.out.println(date2 + "  caso1");

        parametros.put(
                "date_desde", date1);
        parametros.put(
                "date_hasta", date2);

        parametros.put(
                "usuario", usuario);
        parametros.put(
                "periodo", periodo);

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/ReportAdquisicionesxMes3.jasper");

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
            Logger
                    .getLogger(ReporteBean.class
                            .getName()).log(Level.SEVERE, null, ex);
            System.out.println("fin error 1");
            this.setDesde(null);
            this.setHasta(null);
            this.setSeleccionAnio(null);
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

        String usuario = nombreUsuario;

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
            Logger
                    .getLogger(ReporteBean.class
                            .getName()).log(Level.SEVERE, null, ex);
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

    public Date cambioDeFecha(Date comparar) {

        Date hoy = new Date();
        
        if (comparar.before(hoy)) {
            return comparar;
        } else {
            return hoy;
        }
    }

    public void validarOpcionesPeriodos() {
        Date hoy = new Date();
        Calendar c1 = Calendar.getInstance();
        String annio = Integer.toString(c1.get(Calendar.YEAR));
        this.fechaActiva2Tri = true;
        this.fechaActiva3Tri = true;
        this.fechaActiva4Tri = true;
        this.fechaActiva2Sem = true;
        int uno = Integer.parseInt(this.seleccionAnio);
        int dos = Integer.parseInt(annio);
        System.out.println(uno + " >=" + dos);
        if (uno >= dos) {
            if (hoy.getMonth() > 3 && hoy.getMonth() <= 6) {
                this.fechaActiva2Tri = false;
            }
            if (hoy.getMonth() > 6 && hoy.getMonth() <= 9) {
                this.fechaActiva3Tri = false;
                this.fechaActiva2Sem = false;
            }
            if (hoy.getMonth() > 9 && hoy.getMonth() <= 12) {
                this.fechaActiva4Tri = false;

            }

        } else {
            this.fechaActiva2Tri = false;
            this.fechaActiva3Tri = false;
            this.fechaActiva4Tri = false;
            this.fechaActiva2Sem = false;

        }

    }

}
