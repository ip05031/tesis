/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.VisitasJPA;
import entity.Visitas;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItemGroup;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
//import org.primefaces.context.RequestContext;

/**
 *
 * @author Admin
 */
@Named(value = "visitasBean")
@SessionScoped
public class visitasBean implements Serializable {

    private List<Visitas> lvisitas = new ArrayList<>();
    private VisitasJPA visitasJPA = new VisitasJPA();
    private Date fechaVisitas;
    private Integer cantidadVisitas;
    private Visitas visitas;
    private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;
    private Integer cantidad2 = 0;
    
    private String city;  
    private Map<String,String> cities = new HashMap<String, String>();
    private Date fechaInicio= new Date();
    private Date fechaFin= new Date();
    private Integer periodo = 9;

     
    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }
    
    

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Map<String, String> getCities() {
        return cities;
    }

    
    
    public Visitas getVisitas() {
        return visitas;
    }

    public void setVisitas(Visitas visitas) {
        this.visitas = visitas;
    }

    public Date getFechaVisitas() {
        return fechaVisitas;
    }

    public void setFechaVisitas(Date fechaVisitas) {
        this.fechaVisitas = fechaVisitas;
    }

    public Integer getCantidadVisitas() {
        return cantidadVisitas;
    }

    public void setCantidadVisitas(Integer cantidadVisitas) {
        this.cantidadVisitas = cantidadVisitas;
    }

    public List<Visitas> getLvisitas() {
        return lvisitas;
    }

    public void setLvisitas(List<Visitas> lvisitas) {
        this.lvisitas = lvisitas;
    }

    public VisitasJPA getVisitasJPA() {
        return visitasJPA;
    }

    public void setVisitasJPA(VisitasJPA visitasJPA) {
        this.visitasJPA = visitasJPA;
    }

    public List<Visitas> getir() {
        visitasJPA = new VisitasJPA();
        lvisitas = visitasJPA.getVisitas();
        return lvisitas;
    }
    
    public BarChartModel getBarModel() {
        return barModel;
    }

 public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

    
    public visitasBean() {
        
        //createBarModels();
        //opcionesVisitas();
        

    }
    
    public void opcionesVisitas ()
    { 
        createBarModels();
        //cities
        cities = new HashMap<String, String>();
        
        cities.put("Enero","1");
        cities.put("Febrero","2");
        cities.put("Marzo","3");
        cities.put("Abril","4");
        cities.put("Mayo","5");
        cities.put("Junio", "6");
        cities.put("Julio","7");
        cities.put("Agosto","8");
        cities.put("Septiembre","9");
        cities.put("Octubre","10");
        cities.put("Noviembre","11");
        cities.put("Diciembre","12");
        cities.put("Todo", "0");
    }
    
    
    public String getDatatipFormatIntegers(){  
        return "<font size=4 color=black><span>%s Visitas</span><span style=\"display:none;\">%s</span></font>";
     }  

///////////////////////////////////////////////

    private void createBarModels() {
        
        createHorizontalBarModel();
    }

    

    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();
        //////////////////////////////////////////////////
        
        
        ////////////////////////////////////////para cambiar formato a las fechas
       
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String fechaConFormato1 = sdf1.format(fechaInicio);
        String fechaConFormato2 = sdf1.format(fechaFin);
        List<Visitas> lista = new ArrayList<>();
        lista = new VisitasJPA().FechaVisitasBetwen(fechaInicio, fechaFin);
       System.out.println("iteracion fecha  :" + lista.size());
        
        
        ///////////////////////////////////////
        
        
        System.out.println("tamaño  :" + fechaConFormato1);
        System.out.println("tamaño  :" + fechaConFormato2);
        Date fechaV;
        Calendar calendario = GregorianCalendar.getInstance();
        Date fechaRegistro = calendario.getTime();
        Integer cantidad =0;
        
        Integer bandera = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        //Date fechaActual = new Date();
        //String fechaConFormato = sdf.format(fechaActual);
        String fechaBase;
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Cantidad de visitas");

      
        for (int i = 0; i < (lista.size()); i++) {

            fechaV = lista.get(i).getFechav();
            cantidad = lista.get(i).getCantidadv();
            
            if(cantidad2 < cantidad)
            {
               cantidad2 = cantidad; 
            }
            fechaBase = sdf.format(fechaV);
            boys.set(fechaBase, cantidad);
            
        }

        
        //////////////////////////////////////////////////

        

        

        horizontalBarModel.addSeries(boys);
        

        horizontalBarModel.setTitle("Visitas");
        horizontalBarModel.setLegendPosition("ne");
        //horizontalBarModel.setStacked(true);

        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Visitas Diarias");
        xAxis.setMin(0);
        xAxis.setMax(cantidad2+50);

        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Fecha");
    }

/////////////////////////////////////////////// 
    public void iterar() throws ParseException {
        List<Visitas> lista = new ArrayList<>();
        lista = new VisitasJPA().getVisitas();
        Date fechaV;
        Calendar calendario = GregorianCalendar.getInstance();
        Date fechaRegistro = calendario.getTime();
        Integer cantidad;
        Integer bandera = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaActual = new Date();
        String fechaConFormato = sdf.format(fechaActual);
        String fechaBase;

        //System.out.println("tamaño  :" + lista.size());
        for (int i = 0; i < (lista.size()); i++) {

            fechaV = lista.get(i).getFechav();
            cantidad = lista.get(i).getCantidadv();
            visitas = lista.get(i);

            fechaBase = sdf.format(fechaV);
            //System.out.println("///////////////////////////////////////////////////");
            /* System.out.println("iteracion  :" + i);
            System.out.println("fecha actual   " + fechaConFormato);
            System.out.println("fecha base   " + fechaBase);
            System.out.println("comparacion   " + fechaConFormato.compareTo(fechaBase));
            System.out.println("///////////////////////////////////////////////////");*/
            if (fechaConFormato.compareTo(fechaBase) == 0) {
                visitasJPA = new VisitasJPA();
                visitas.setCantidadv(cantidad + 1);
                visitasJPA.updateDonaciones(visitas);

                this.setFechaVisitas(null);
                this.setCantidadVisitas(0);

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡visita registrada!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);

                i = lista.size();
                bandera = 1;

            }

        }
        if (bandera == 0) {
            visitasJPA = new VisitasJPA();
            visitas = new Visitas();
            visitas.setFechav(fechaActual);
            visitas.setCantidadv(1);
            visitasJPA.saveVisitas(visitas);

            this.getir();
            visitas.setFechav(null);
            visitas.setCantidadv(0);

            System.out.println("/////termina");

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡visita registrada!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

}
