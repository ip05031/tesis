/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.InventarioJPA;
import controller.PalabrasClavesJPA;
import entity.Autor;
import controller.PrestamoJPA;
import controller.RevistasJPA;
import entity.Categoria;
import entity.Inventario;
import entity.PalabraClave;
import entity.Prestamo;
import entity.Revista;
import entity.Titulo;
import entity.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.validator.internal.util.logging.Log_$logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author IPalacios
 */
@Named(value = "prestamoBean")
@SessionScoped
public class PrestamoBean implements Serializable {

    private PrestamoJPA prestamoJPA;
    private List<Autor> autores = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();
    private List<Titulo> titulos = new ArrayList<>();
    private List<Prestamo> prestamos = new ArrayList<>();

    private String resumen;
    private String autor;
    private String titulo;
    private String articulo;
    private String categoria;
    private List<String> palabras;

    private int idPrestamo;
    private int idInventario;
    private int idUsuario;
    private Date fecha;
    private Date hora;
    private int tipoPrestamo;
    private String fechaMostrar;

    private Prestamo devolucion;
    private int pags;
    private Date devFecha;

    private String algo;
    private boolean paso1;
    private boolean paso2;
    private boolean paso3;

    private List<PalabraClave> palabraSelec = new ArrayList<>();
    private List<Revista> revistasEncontradas;
    private List<Revista> revistasFiltradas;
    private List<Revista> listadoRevistas;
    private int idUltimoInventario;

    private Revista revistaSel = new Revista();
    private String numpags;
    private Inventario inventarioDevolucion;
    private Inventario inventarioPrestamo;

    /**
     * Creates a new instance of PrestamoBean
     */
    public PrestamoBean() {
        RevistasJPA revis = new RevistasJPA();
        listadoRevistas = revis.getRevistas();
        prestamoJPA = new PrestamoJPA();
        titulos = prestamoJPA.getTitulos();

    }

    public String buscaTitulo(Prestamo p) {
        String titu = "";
        if (p.getIdInventario() != null) {
            if (p.getIdInventario().getIdRevista() != null) {
                try {
                    for (Revista r : listadoRevistas) {

                        if (p.getIdInventario().getIdRevista().getIdRevista() != null) {
                            if (r.getIdRevista() == p.getIdInventario().getIdRevista().getIdRevista()) {
                                for (Titulo t : titulos) {
                                    if (r.getIdTitulo().getIdTitulo() == t.getIdTitulo()) {
                                        titu = t.getTituloRevista() + " " + r.getTitulor();
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

        }

        return titu;

    }

    public void pasos() {
        System.out.println("Algo");
        paso2 = true;
    }

    public List<Autor> listaAutores() {
        prestamoJPA = new PrestamoJPA();
        autores = prestamoJPA.getAutores();
        return autores;
    }

    public List<Categoria> listaCategorias() {
        prestamoJPA = new PrestamoJPA();
        categorias = prestamoJPA.getCategorias();
        return categorias;
    }

    public List<Prestamo> prestamoReporte() {
        prestamoJPA = new PrestamoJPA();
        // prestamos = PrestamoJPA.getPrestamoReporte();
        return prestamos;
    }

    public List<Titulo> listaTitulos() {
        prestamoJPA = new PrestamoJPA();
        titulos = prestamoJPA.getTitulos();
        return titulos;
    }
///String titulo, String resumen, String tituloArticulo, String autor, String categoria, List<String> palabras

    public String buscar() {
        RevistasJPA revis = new RevistasJPA();
        this.revistasEncontradas = revis.getRevistasBusqueda(
                titulo,
                resumen,
                articulo,
                autor,
                categoria,
                palabras);
        paso1 = true;
        return "Resultado?faces-redirect=true";
    }

    public boolean disponible() {
        int existe;
        boolean ret;
        System.out.println("buscando disponibilidad");
        existe = new InventarioJPA().minIdDisp(1);
        if (existe > 0) {
            ret = true;
        } else {
            ret = false;
        }
        return ret;
    }

    public boolean disponible2(int id) {
        int existe;
        boolean ret;
        System.out.println("buscando disponibilidad");
        existe = new InventarioJPA().minIdDisp(id);
        if (existe > 0) {
            ret = true;
        } else {
            ret = false;
        }
        return ret;
    }

    public void seleccionarRevista(Revista revista) {
        revistaSel = revista;
        Calendar calendario = GregorianCalendar.getInstance();
        Date fechaRegistro = calendario.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaMostrar = sdf.format(fechaRegistro);
        idPrestamo = new PrestamoJPA().getClave();
        //idInventario = new InventarioJPA().minIdDisp(revista.getIdRevista());
        inventarioPrestamo = new InventarioJPA().minIdDisp2(revista.getIdRevista());
        if (inventarioPrestamo == null) {
            idInventario = 0;
            FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_INFO, "!", "comuniquese con el administrador error en registro de prestamos"));
        } else {
            idInventario = inventarioPrestamo.getIdInventario();
        }
        fecha = fechaRegistro;
        hora = fechaRegistro;
        tipoPrestamo = 1;
        System.out.println("ni idea");
    }

    public void guardarPrestamo() {

        try {
            prestamoJPA = new PrestamoJPA();
            Prestamo prest = new Prestamo();
            Inventario inv = new Inventario();
            //inv.setIdInventario(idInventario);
            inv = inventarioPrestamo;
            Usuario user = new Usuario();
            user.setIdUsuario(idUsuario);

            prest.setIdPrestamo(idPrestamo);
            prest.setIdInventario(inv);
            prest.setIdUsuario(user);
            prest.setFechap(fecha);
            prest.setTipop(tipoPrestamo);
            prest.setHorap(hora);
            prestamoJPA.savePrestamo(prest);
            inv.setExistenciai(0);
            new InventarioJPA().updateInventario(inv);
            FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_INFO, "!", "Se registró un prestamo"));
        } catch (Exception e) {
            System.out.println("Error en guardarPrestamo");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }

    }

    public void selecDevolverRevista(Prestamo prest) {
        devolucion = prest;
        Calendar calendario = GregorianCalendar.getInstance();
        Date fechaRegistro = calendario.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaMostrar = sdf.format(fechaRegistro);
        devFecha = fechaRegistro;
        idPrestamo = new PrestamoJPA().getClave();
        idUsuario = devolucion.getIdUsuario().getIdUsuario();
        idInventario = devolucion.getIdInventario().getIdInventario();
        inventarioDevolucion = devolucion.getIdInventario();
        tipoPrestamo = 2;
    }

    public void guardarDevolucion() {
        try {
            prestamoJPA = new PrestamoJPA();
            Prestamo prest = new Prestamo();
            Inventario inv = new Inventario();
            inv = this.inventarioDevolucion;
            Usuario user = new Usuario();
            user.setIdUsuario(idUsuario);

            devolucion.setTipop(3);
            prestamoJPA.updatePrestamo(devolucion);

            prestamoJPA = new PrestamoJPA();

            prest.setIdPrestamo(idPrestamo);
            prest.setIdInventario(inv);
            prest.setIdUsuario(user);
            prest.setFechap(devFecha);
            prest.setTipop(tipoPrestamo);
            prest.setHorap(devFecha);
            prest.setPaginasp(numpags);

            prestamoJPA.savePrestamo(prest);

            inv.setExistenciai(1);
            new InventarioJPA().updateInventario(inv);
            this.numpags = "";
            RequestContext.getCurrentInstance().execute("PF('devolverRevista').hide();");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Devolución almacenada exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage("msgDevo", message);
        } catch (Exception e) {
            System.out.println("Error en guardar devolucion");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    public List<Prestamo> ListaPrestamos() {
        prestamoJPA = new PrestamoJPA();
        List<Prestamo> listaPrestamos = new ArrayList<>();
        listaPrestamos = prestamoJPA.listaPrestamos();

        /*   for (int i = 0; i < listaPrestamos.size(); i++) {
            String nombrerev = "";
            String titulorev = "";
            // nombrerev = listaPrestamos.get(i).getIdInventario().getIdRevista().getTitulor();
            titulorev = listaPrestamos.get(i).getIdInventario().getIdRevista().getIdTitulo().getTituloRevista();
            System.out.println(nombrerev);
            System.out.println(titulorev);
        }*/
        return listaPrestamos;
    }

    public List<Revista> obtenerRevistas() {
        return this.revistasEncontradas;
    }

    public List<PalabraClave> getPalabraSelec() {
        return palabraSelec;
    }

    public void setPalabraSelec(List<PalabraClave> palabraSelec) {
        this.palabraSelec = palabraSelec;
    }

    public String getAlgo() {
        return algo;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }

    public boolean isPaso1() {
        return paso1;
    }

    public void setPaso1(boolean paso1) {
        this.paso1 = paso1;
    }

    public boolean isPaso2() {
        return paso2;
    }

    public void setPaso2(boolean paso2) {
        this.paso2 = paso2;
    }

    public boolean isPaso3() {
        return paso3;
    }

    public void setPaso3(boolean paso3) {
        this.paso3 = paso3;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<String> getPalabras() {
        return palabras;
    }

    public void setPalabras(List<String> palabras) {
        this.palabras = palabras;
    }

    public Revista getRevistaSel() {
        return revistaSel;
    }

    public void setRevistaSel(Revista revistaSel) {
        this.revistaSel = revistaSel;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public int getIdUltimoInventario() {
        return idUltimoInventario;
    }

    public void setIdUltimoInventario(int idUltimoInventario) {
        this.idUltimoInventario = idUltimoInventario;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(int tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public String getFechaMostrar() {
        return fechaMostrar;
    }

    public void setFechaMostrar(String fechaMostrar) {
        this.fechaMostrar = fechaMostrar;
    }

    public Prestamo getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Prestamo devolucion) {
        this.devolucion = devolucion;
    }

    public int getPags() {
        return pags;
    }

    public void setPags(int pags) {
        this.pags = pags;
    }

    public Date getDevFecha() {
        return devFecha;
    }

    public void setDevFecha(Date devFecha) {
        this.devFecha = devFecha;
    }

    public List<Revista> getRevistasFiltradas() {
        return revistasFiltradas;
    }

    public void setRevistasFiltradas(List<Revista> revistasFiltradas) {
        this.revistasFiltradas = revistasFiltradas;
    }

    public String getNumpags() {
        return numpags;
    }

    public void setNumpags(String numpags) {
        this.numpags = numpags;
    }

}
