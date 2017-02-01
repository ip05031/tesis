/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.ArticuloJPA;
import controller.DescargarJPA;
import controller.EventosJPA;
import controller.ImportarJPA;
import controller.InicioJPA;
import controller.PantallaJPA;
import controller.RevistaJPA;
import controller.UsuarioJPA;
import entity.Articulo;
import entity.Descarga;
import entity.Evento;
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
    private RevistaJPA jpaRevista;
    private InicioJPA inicioJPA;
    private List<Revista> listaRevista = new ArrayList<>();
    // lista para letra A
    private List<Revista> listaA = new ArrayList<>();
    // lista para letra B
    private List<Revista> listaB = new ArrayList<>();
    // lista para letra C
    private List<Revista> listaC = new ArrayList<>();
    // lista para letra D
    private List<Revista> listaD = new ArrayList<>();
    // lista para letra E
    private List<Revista> listaE = new ArrayList<>();
    // lista para letra F
    private List<Revista> listaF = new ArrayList<>();
    // lista para letra G
    private List<Revista> listaG = new ArrayList<>();
    // lista para letra H
    private List<Revista> listaH = new ArrayList<>();
    // lista para letra I
    private List<Revista> listaI = new ArrayList<>();
    // lista para letra J
    private List<Revista> listaJ = new ArrayList<>();
    // lista para letra K
    private List<Revista> listaK = new ArrayList<>();
    // lista para letra L
    private List<Revista> listaL = new ArrayList<>();
    // lista para letra M
    private List<Revista> listaM = new ArrayList<>();
    // lista para letra N
    private List<Revista> listaN = new ArrayList<>();
    // lista para letra O
    private List<Revista> listaO = new ArrayList<>();
    // lista para letra P
    private List<Revista> listaP = new ArrayList<>();
    // lista para letra Q
    private List<Revista> listaQ = new ArrayList<>();
    // lista para letra R
    private List<Revista> listaR = new ArrayList<>();
    // lista para letra S
    private List<Revista> listaS = new ArrayList<>();
    // lista para letra T
    private List<Revista> listaT = new ArrayList<>();
    // lista para letra U
    private List<Revista> listaU = new ArrayList<>();
    // lista para letra V
    private List<Revista> listaV = new ArrayList<>();
    // lista para letra W
    private List<Revista> listaW = new ArrayList<>();
    // lista para letra X
    private List<Revista> listaX = new ArrayList<>();
    // lista para letra Y
    private List<Revista> listaY = new ArrayList<>();
    // lista para letra Z
    private List<Revista> listaZ = new ArrayList<>();
    // lista para letra #
    private List<Revista> listaNum = new ArrayList<>();
    // lista para letra todos
    private List<Revista> listaTodos = new ArrayList<>();
    
    
    
    
    
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
    private List<Evento> banner = new ArrayList<>();


    /*---------------------------------------------------------- Constructor -----------------------------------------------------------------------*/
    public InicioBean() {
        System.out.println("Iniciando el construnctor Inicio-Bean");
        this.setearRevistas();
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
    public List<Evento> listaImgBanner(){
        System.out.println("imagenes de banner ");
        return new EventosJPA().getImgBanner();
    }
    
    public void setearRevistas(){
       
        System.out.println("letra A");
        inicioJPA = new InicioJPA();
        listaA = inicioJPA.obtnerRevistas2();
        inicioJPA = null;
        
        System.out.println("letra B");
        inicioJPA = new InicioJPA();
        listaB = inicioJPA.obtenerRevistaPorLetra("B");
        inicioJPA = null;
        
        System.out.println("letra C");
        inicioJPA = new InicioJPA();
        listaC = inicioJPA.obtenerRevistaPorLetra("C");
        inicioJPA = null;
        
        System.out.println("letra D");
        inicioJPA = new InicioJPA();
        listaD = inicioJPA.obtenerRevistaPorLetra("D");
        inicioJPA = null;
        
        System.out.println("letra E");
        inicioJPA = new InicioJPA();
        listaE = inicioJPA.obtenerRevistaPorLetra("E");
        inicioJPA = null;
        
        System.out.println("letra F");
        inicioJPA = new InicioJPA();
        listaF = inicioJPA.obtenerRevistaPorLetra("F");
        inicioJPA = null;
        
        System.out.println("letra G");
        inicioJPA = new InicioJPA();
        listaG = inicioJPA.obtenerRevistaPorLetra("G");
        inicioJPA = null;
        
        System.out.println("letra H");
        inicioJPA = new InicioJPA();
        listaH = inicioJPA.obtenerRevistaPorLetra("H");
        inicioJPA = null;
        
        System.out.println("letra I");
        inicioJPA = new InicioJPA();
        listaI = inicioJPA.obtenerRevistaPorLetra("I");
        inicioJPA = null;
        
        System.out.println("letra J");
        inicioJPA = new InicioJPA();
        listaJ = inicioJPA.obtenerRevistaPorLetra("J");
        inicioJPA = null;
        
        System.out.println("letra K");
        inicioJPA = new InicioJPA();
        listaK = inicioJPA.obtenerRevistaPorLetra("K");
        inicioJPA = null;
        
        System.out.println("letra L");
        inicioJPA = new InicioJPA();
        listaL = inicioJPA.obtenerRevistaPorLetra("L");
        inicioJPA = null;
        
        System.out.println("letra M");
        inicioJPA = new InicioJPA();
        listaM = inicioJPA.obtenerRevistaPorLetra("M");
        inicioJPA = null;
        
        System.out.println("letra N");
        inicioJPA = new InicioJPA();
        listaN = inicioJPA.obtenerRevistaPorLetra("N");
        inicioJPA = null;
        
        System.out.println("letra O");
        inicioJPA = new InicioJPA();
        listaO = inicioJPA.obtenerRevistaPorLetra("O");
        inicioJPA = null;
        
        System.out.println("letra P");
        inicioJPA = new InicioJPA();
        listaP = inicioJPA.obtenerRevistaPorLetra("P");
        inicioJPA = null;
        
        System.out.println("letra Q");
        inicioJPA = new InicioJPA();
        listaQ = inicioJPA.obtenerRevistaPorLetra("Q");
        inicioJPA = null;
        
        System.out.println("letra R");
        inicioJPA = new InicioJPA();
        listaR = inicioJPA.obtenerRevistaPorLetra("R");
        inicioJPA = null;
        
        System.out.println("letra S");
        inicioJPA = new InicioJPA();
        listaS = inicioJPA.obtenerRevistaPorLetra("S");
        inicioJPA = null;
        
        System.out.println("letra T");
        inicioJPA = new InicioJPA();
        listaT = inicioJPA.obtenerRevistaPorLetra("T");
        inicioJPA = null;
        
        System.out.println("letra U");
        inicioJPA = new InicioJPA();
        listaU = inicioJPA.obtenerRevistaPorLetra("U");
        inicioJPA = null;
        
        System.out.println("letra V");
        inicioJPA = new InicioJPA();
        listaV = inicioJPA.obtenerRevistaPorLetra("V");
        inicioJPA = null;
        
        System.out.println("letra W");
        inicioJPA = new InicioJPA();
        listaW = inicioJPA.obtenerRevistaPorLetra("W");
        inicioJPA = null;
        
        System.out.println("letra X");
        inicioJPA = new InicioJPA();
        listaX = inicioJPA.obtenerRevistaPorLetra("X");
        inicioJPA = null;
        
        System.out.println("letra Y");
        inicioJPA = new InicioJPA();
        listaY = inicioJPA.obtenerRevistaPorLetra("Y");
        inicioJPA = null;
        
        System.out.println("letra Z");
        inicioJPA = new InicioJPA();
        listaZ = inicioJPA.obtenerRevistaPorLetra("Z");
        inicioJPA = null;
        

        
        System.out.println("Todas");
        inicioJPA = new InicioJPA();
        listaTodos = inicioJPA.obtenerTodasRevistas();
        inicioJPA = null;
    }
  

    
    public boolean verExistencias(List<Revista> revista){
        System.out.println("verificando existencia");
        boolean valor = false;
        valor = revista.size() > 0;
        System.out.println(valor);
        return valor;
    }
    
    public String login() {
        inicioJPA = new InicioJPA();
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
               //Usuario fecha que ingreso de visita
                UsuarioJPA usuarioJPA = new UsuarioJPA();
                Calendar calendar = GregorianCalendar.getInstance();
                userLogged.setFechavisita(calendar.getTime());
                usuarioJPA.updateUsuario(userLogged);
                
                redirigir();
                return "index?facesRedirect=true";
            } else {
                System.out.println("error ...");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Usuario y/o Contraseña Incorrectos!", null);
                FacesContext.getCurrentInstance().addMessage("Message456", message);
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

    public List<Revista> getListaA() {
        return listaA;
    }

    public void setListaA(List<Revista> listaA) {
        this.listaA = listaA;
    }

    public List<Revista> getListaB() {
        return listaB;
    }

    public void setListaB(List<Revista> listaB) {
        this.listaB = listaB;
    }

    public List<Revista> getListaC() {
        return listaC;
    }

    public void setListaC(List<Revista> listaC) {
        this.listaC = listaC;
    }

    public List<Revista> getListaD() {
        return listaD;
    }

    public void setListaD(List<Revista> listaD) {
        this.listaD = listaD;
    }

    public List<Revista> getListaE() {
        return listaE;
    }

    public void setListaE(List<Revista> listaE) {
        this.listaE = listaE;
    }

    public List<Revista> getListaF() {
        return listaF;
    }

    public void setListaF(List<Revista> listaF) {
        this.listaF = listaF;
    }

    public List<Revista> getListaG() {
        return listaG;
    }

    public void setListaG(List<Revista> listaG) {
        this.listaG = listaG;
    }

    public List<Revista> getListaH() {
        return listaH;
    }

    public void setListaH(List<Revista> listaH) {
        this.listaH = listaH;
    }

    public List<Revista> getListaI() {
        return listaI;
    }

    public void setListaI(List<Revista> listaI) {
        this.listaI = listaI;
    }

    public List<Revista> getListaJ() {
        return listaJ;
    }

    public void setListaJ(List<Revista> listaJ) {
        this.listaJ = listaJ;
    }

    public List<Revista> getListaK() {
        return listaK;
    }

    public void setListaK(List<Revista> listaK) {
        this.listaK = listaK;
    }

    public List<Revista> getListaL() {
        return listaL;
    }

    public void setListaL(List<Revista> listaL) {
        this.listaL = listaL;
    }

    public List<Revista> getListaM() {
        return listaM;
    }

    public void setListaM(List<Revista> listaM) {
        this.listaM = listaM;
    }

    public List<Revista> getListaN() {
        return listaN;
    }

    public void setListaN(List<Revista> listaN) {
        this.listaN = listaN;
    }

    public List<Revista> getListaO() {
        return listaO;
    }

    public void setListaO(List<Revista> listaO) {
        this.listaO = listaO;
    }

    public List<Revista> getListaP() {
        return listaP;
    }

    public void setListaP(List<Revista> listaP) {
        this.listaP = listaP;
    }

    public List<Revista> getListaQ() {
        return listaQ;
    }

    public void setListaQ(List<Revista> listaQ) {
        this.listaQ = listaQ;
    }

    public List<Revista> getListaR() {
        return listaR;
    }

    public void setListaR(List<Revista> listaR) {
        this.listaR = listaR;
    }

    public List<Revista> getListaS() {
        return listaS;
    }

    public void setListaS(List<Revista> listaS) {
        this.listaS = listaS;
    }

    public List<Revista> getListaT() {
        return listaT;
    }

    public void setListaT(List<Revista> listaT) {
        this.listaT = listaT;
    }

    public List<Revista> getListaU() {
        return listaU;
    }

    public void setListaU(List<Revista> listaU) {
        this.listaU = listaU;
    }

    public List<Revista> getListaV() {
        return listaV;
    }

    public void setListaV(List<Revista> listaV) {
        this.listaV = listaV;
    }

    public List<Revista> getListaW() {
        return listaW;
    }

    public void setListaW(List<Revista> listaW) {
        this.listaW = listaW;
    }

    public List<Revista> getListaX() {
        return listaX;
    }

    public void setListaX(List<Revista> listaX) {
        this.listaX = listaX;
    }

    public List<Revista> getListaY() {
        return listaY;
    }

    public void setListaY(List<Revista> listaY) {
        this.listaY = listaY;
    }

    public List<Revista> getListaZ() {
        return listaZ;
    }

    public void setListaZ(List<Revista> listaZ) {
        this.listaZ = listaZ;
    }

    public List<Revista> getListaNum() {
        return listaNum;
    }

    public void setListaNum(List<Revista> listaNum) {
        this.listaNum = listaNum;
    }

    public List<Revista> getListaTodos() {
        return listaTodos;
    }

    public void setListaTodos(List<Revista> listaTodos) {
        this.listaTodos = listaTodos;
    }

    public List<Evento> getBanner() {
        return banner;
    }

    public void setBanner(List<Evento> banner) {
        this.banner = banner;
    }
}
