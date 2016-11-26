/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.ArticuloJPA;
import controller.DescargarJPA;
import controller.ImportarJPA;
import controller.InicioJPA;
import controller.PantallaJPA;
import controller.RevistaJPA;
import entity.Articulo;
import entity.Descarga;
import entity.Pantalla;
import entity.Revista;
import entity.Usuario;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author IPalacios
 */
@Named(value = "inicioBean")
@SessionScoped
public class InicioBean implements Serializable {

    private String usuarioNombre;
    private String usuarioContra;
    private List<String> miLista = Arrays.asList("evento_1", "evento_2", "evento_3", "evento_4");
    private RevistaJPA jpaRevista;
    private InicioJPA inicioJPA;
    private List<Revista> listaRevista = new ArrayList<>();
    private List<Revista> listaRevista2 = new ArrayList<>();
    private Revista revistaAbrir = new Revista();
    private Revista revistaAbrir2 = new Revista();
    private List<Articulo> listArticulo = new ArrayList<>();
    private Articulo articuloAbrir = new Articulo();
    private String productId;
    private boolean inicioSesion;
    private boolean archivoDownload;
    private String logueado;
    private String menu;
    private List<Pantalla> listaPantallas = new ArrayList<>();
    private Usuario userLogueado = new Usuario();
    private Usuario perfilUsuario = new Usuario();
    FacesContext context;
    private DescargarJPA descargarJPA;


    /*---------------------------------------------------------- Constructor -----------------------------------------------------------------------*/
    public InicioBean() {
        System.out.println("Iniciando el construnctor Inicio-Bean");
        listaRevista2 = getListadoRevistas();
        this.setArchivoDownload(false);

    }

    /*---------------------------------------------------------- Funciones Propias -----------------------------------------------------------------*/
    public void descargaArchivo(int tipo) {

        Descarga desc = new Descarga();
        Revista rev = new Revista();
        Articulo art = new Articulo();
        Usuario user = new Usuario();
        Date fecha = null;
        Date hora = null;
        int idDescarga = 0;
        descargarJPA = new DescargarJPA();

        rev = this.revistaAbrir;
        context = FacesContext.getCurrentInstance();
        user = (Usuario) context.getExternalContext().getSessionMap().get("logueado");

        idDescarga = descargarJPA.getClave();
        Calendar calendario = GregorianCalendar.getInstance();
        fecha = calendario.getTime();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, calendario.HOUR_OF_DAY);
        cal.set(Calendar.MINUTE, calendario.MINUTE);
        cal.set(Calendar.SECOND, calendario.SECOND);
        cal.set(Calendar.MILLISECOND, calendario.MILLISECOND);
        hora = calendario.getTime();

        desc.setIdDescarga(idDescarga);
        desc.setIdRevista(rev);
        desc.setIdUsuario(user);
        desc.setFechad(fecha);
        desc.setHorad(hora);

        if (tipo == 2) {
            art = this.articuloAbrir;
            desc.setIdArticulo(art);
        }
        try {
            descargarJPA.saveDescarga(desc);
            this.setArchivoDownload(true);
        } catch (Exception e) {
            System.out.println("solicitar descarga");
        }

        // id revista
        // id articulo
        // id usuario
        // fecha
        // hora
    }

    public List<Descarga> obtenerHoras() {
        return new DescargarJPA().getHoras();
    }

    public void inhabilitar() {
        this.setArchivoDownload(false);
    }

    public String setearRedirigir(Revista revista) {
        System.out.println("seteando Revista..");
        this.revistaAbrir = revista;
        inhabilitar();
        listArticulo = new ImportarJPA().getListaUnArticulo(revista.getIdRevista());
        return "PortadaRevista?faces-redirect=true";
    }

    public String setearArticuloRedirigir(Articulo art) {
        System.out.println("se va para ver Articulo");
        inhabilitar();
        this.articuloAbrir = art;
        return "VerArticulo?faces-redirect=true";
    }

    public void listarArticulos() {
        listArticulo = new ImportarJPA().getListaUnArticulo(revistaAbrir.getIdRevista());
    }

    public void obtenersingleRevista() {

    }

    public List<Revista> getListadoRevistas() {
        System.out.println("getlistadoRevistas");
        inicioJPA = new InicioJPA();
        listaRevista = inicioJPA.obtnerRevistas2();
        return listaRevista;
    }

    public String login() {
        System.out.println("Inicio sesion");
        List<Usuario> user = new ArrayList<>();
        List<Pantalla> listaDePantallas = new ArrayList<>();
        Usuario userLogged = new Usuario();
        String hashPass = new usuarioBean().sha256(this.usuarioContra);
        System.out.println("Contra:");
        System.out.println(hashPass);
        System.out.println("ok");
        try {
            user = inicioJPA.existeUsuario(this.usuarioNombre, hashPass);
            if (!user.isEmpty()) {
                this.setInicioSesion(true);
                context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("logueado", user.get(0));
                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("logueado", user.get(0));
                userLogged = user.get(0);
                this.setUserLogueado(user.get(0));
                System.out.println("this is the nick: ----------------------");
                System.out.println(userLogged.getNickname());
                this.setLogueado("" + userLogged.getNickname());
                listaDePantallas = userLogged.getIdTusuario().getPantallaList();
                definirMenu(userLogged);
                this.setUsuarioContra("");
                this.setUsuarioNombre("");
                RequestContext.getCurrentInstance().update("form-login");
                redirigir();
                return "index?facesRedirect=true";
            } else {
                System.out.println("error ...");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Usuario y/o Contraseña Incorrectos!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                this.setInicioSesion(false);
                //redirigir();
                return "Login?facesRedirect=true";
            }

        } catch (Exception e) {
            return "Login?facesRedirect=true";
        }

        //return "index?faces-redirect=true";
    }

    public String reDirrecion() {
        listArticulo = new ImportarJPA().getListaUnArticulo(revistaAbrir.getIdRevista());
        return "PortadaRevista";
    }

    public void definirMenu(Usuario user) {
        System.out.println("usuario:" + user.getNombreu());
        int idUsuario = user.getIdUsuario();
        this.setListaPantallas(menuList(user));
    }

    public String Salir() {
        this.setInicioSesion(false);
        inhabilitar();
        context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("logueado");
        redirigir();
        return "index?facesRedirect=true";
    }

    public boolean verificarPagina(String nombre) {
        System.out.println(nombre);
        System.out.println("verificarusuario");
        String a = "a";
        if (this.inicioSesion) {
            Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("logueado");
            List<Pantalla> pantallaList = user.getIdTusuario().getPantallaList();
            int contador = 0;
            for (Pantalla pantalla : pantallaList) {
                if (pantalla.getAccesopa().compareToIgnoreCase(nombre) == 0) {
                    contador++;
                }
            }
            if (contador > 0) {
                System.out.println("si");
                return true;
            } else {
                System.out.println("no");
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(InicioBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                return false;
            }
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(InicioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
    }

    public boolean correcto() {
        boolean valor = true;

        return valor;
    }

    public void urls() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        System.out.println("url");
        System.out.println(url);
        System.out.println("uri");
        System.out.println(uri);
    }

    public void redirigir() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String url = request.getRequestURL().toString();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("" + url);
        } catch (Exception e) {
            System.out.println("error en /iniciobean/redirigir ");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }

    }

    public void redirigir2() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(InicioBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error redirigir 2");
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
        }
    }

    public boolean pantallasMenu(int tipo) {
        boolean value = false;

        if (this.listaPantallas.size() > 9) {
            value = true;
        } else {
            value = false;
        }
        if (tipo == 1) {
            value = !value;
        }
        return value;
    }

    public List<Pantalla> menuList(Usuario user) {

        List<Pantalla> menuList = new ArrayList();
        List<Pantalla> pantallaList = user.getIdTusuario().getPantallaList();
        for (Pantalla pantalla : pantallaList) {
            if (pantalla.getPermiso()) {
                menuList.add(pantalla);
            }
        }
        return menuList;
    }

    /*---------------------------------------------------------- Setter & Getter -------------------------------------------------------------------*/
    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioContra() {
        return usuarioContra;
    }

    public void setUsuarioContra(String usuarioContra) {
        this.usuarioContra = usuarioContra;
    }

    public List<Revista> getListaRevista2() {
        return listaRevista2;
    }

    public void setListaRevista2(List<Revista> listaRevista2) {
        this.listaRevista2 = listaRevista2;
    }

    public Revista getRevistaAbrir() {
        return revistaAbrir;
    }

    public void setRevistaAbrir(Revista revistaAbrir) {
        this.revistaAbrir = revistaAbrir;
    }

    public List<Articulo> getListArticulo() {
        return listArticulo;
    }

    public void setListArticulo(List<Articulo> listArticulo) {
        this.listArticulo = listArticulo;
    }

    public Articulo getArticuloAbrir() {
        return articuloAbrir;
    }

    public void setArticuloAbrir(Articulo articuloAbrir) {
        this.articuloAbrir = articuloAbrir;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<Revista> getListaRevista() {
        return listaRevista;
    }

    public void setListaRevista(List<Revista> listaRevista) {
        this.listaRevista = listaRevista;
    }

    public List<String> getMiLista() {
        return miLista;
    }

    public void setMiLista(List<String> miLista) {
        this.miLista = miLista;
    }

    public boolean isInicioSesion() {
        return inicioSesion;
    }

    public void setInicioSesion(boolean inicioSesion) {
        this.inicioSesion = inicioSesion;
    }

    public boolean isArchivoDownload() {
        return archivoDownload;
    }

    public void setArchivoDownload(boolean archivoDownload) {
        this.archivoDownload = archivoDownload;
    }

    public String getLogueado() {
        return logueado;
    }

    public void setLogueado(String logueado) {
        this.logueado = logueado;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public List<Pantalla> getListaPantallas() {
        return listaPantallas;
    }

    public void setListaPantallas(List<Pantalla> listaPantallas) {
        this.listaPantallas = listaPantallas;
    }

    public Usuario getUserLogueado() {
        return userLogueado;
    }

    public void setUserLogueado(Usuario userLogueado) {
        this.userLogueado = userLogueado;
    }

    public Usuario getPerfilUsuario() {
        return perfilUsuario;
    }

    public void setPerfilUsuario(Usuario perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }
}
