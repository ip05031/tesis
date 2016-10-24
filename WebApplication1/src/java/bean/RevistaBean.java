package bean;

import controller.ArticulosJPA;
import controller.PalabrasClavesJPA;
import controller.RevistasJPA;
import controller.TituloJPA;
import entity.Articulo;
import entity.Autor;
import entity.Categoria;
import entity.Donaciones;
import entity.PalabraClave;
import entity.Revista;
import entity.Titulo;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javaUtil.Paises;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@Named(value = "revistaBean")
@SessionScoped
public class RevistaBean implements Serializable {

    private List<Revista> listadoRevista = new ArrayList<>();
    private Revista revista;
    private Revista modiRevista;
    private Integer idDonacion;
    private String donanteFecha;
    private Integer idDonante;
    private Categoria categoria;
    private Articulo articulo;
    private Articulo clone;
    private String nombrepalabra;
    private Donaciones donacion;
    private Titulo idTitulo;
    private List<Articulo> listaArticulos = new ArrayList<>();
    private List<Articulo> listaArtiModificar = new ArrayList<>();
    private List<PalabraClave> listaPalabraClave = new ArrayList<>();
    private List<PalabraClave> listaPalabraClaveModificar = new ArrayList<>();
    private List<PalabraClave> listaPalabraClaveDestino = new ArrayList<>();
    private RevistasJPA jpaRevista;
    private Integer idAutor;

    public RevistaBean() {
        this.idTitulo = new Titulo();
        this.articulo = new Articulo();
        this.categoria = new Categoria();
        this.revista = new Revista();
        this.modiRevista = new Revista();
        this.donacion = new Donaciones();
        this.donanteFecha = "";
        this.idDonante = -1;

    }

    public Titulo getIdTitulo() {
        return idTitulo;
    }

    public void setIdTitulo(Titulo idTitulo) {
        this.idTitulo = idTitulo;
    }

    public Integer getIdDonacion() {
        return idDonacion;
    }

    public void setIdDonacion(Integer idDonacion) {
        this.idDonacion = idDonacion;
    }

    public String getDonanteFecha() {
        return donanteFecha;
    }

    public void setDonanteFecha(String donanteFecha) {
        this.donanteFecha = donanteFecha;
    }

    public Integer getIdDonante() {
        return idDonante;
    }

    public void setIdDonante(Integer idDonante) {
        this.idDonante = idDonante;
    }

    public Donaciones getDonacion() {
        return donacion;
    }

    public void setDonacion(Donaciones donacion) {
        this.donacion = donacion;
    }

    public String getNombrepalabra() {
        return nombrepalabra;
    }

    public void setNombrepalabra(String nombrepalabra) {
        this.nombrepalabra = nombrepalabra;
    }

    public List<PalabraClave> getListaPalabraClaveModificar() {
        return listaPalabraClaveModificar;
    }

    public void setListaPalabraClaveModificar(List<PalabraClave> listaPalabraClaveModificar) {
        this.listaPalabraClaveModificar = listaPalabraClaveModificar;
    }

    public List<PalabraClave> getListaPalabraClaveDestino() {
        return listaPalabraClaveDestino;
    }

    public void setListaPalabraClaveDestino(List<PalabraClave> listaPalabraClaveDestino) {
        this.listaPalabraClaveDestino = listaPalabraClaveDestino;
    }

    public List<PalabraClave> getListaPalabraClave() {
        return listaPalabraClave;
    }

    public void setListaPalabraClave(List<PalabraClave> listaPalabraClave) {
        this.listaPalabraClave = listaPalabraClave;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public List<Revista> getListadoRevista() {
        return listadoRevista;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setListadoRevista(List<Revista> listadoRevista) {
        this.listadoRevista = listadoRevista;
    }

    public Revista getRevista() {
        return revista;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }

    public Revista getModiRevista() {
        return modiRevista;
    }

    public void setModiRevista(Revista modiRevista) {
        this.modiRevista = modiRevista;
    }

    public List<Articulo> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public List<Articulo> getListaArtiModificar() {
        return listaArtiModificar;
    }

    public void setListaArtiModificar(List<Articulo> listaArtiModificar) {
        this.listaArtiModificar = listaArtiModificar;
    }

    public List<Revista> obtener() {
        jpaRevista = new RevistasJPA();
        return jpaRevista.getRevistas();
    }

    public List<Titulo> listaTitulos() {
        TituloJPA ti = new TituloJPA();
        return ti.getTitulos();
    }

    public void modificar(Revista revis) {
        this.donanteFecha = "Seleccione";
        this.idDonante = -1;
        this.idDonacion = -1;
        this.idTitulo.setIdTitulo(1);
        revista = revis;
        
        String accion = "Modificacion de datos de Revista" ;
        String tabla = "Revista" ;
        new bitacoraBean().guardarbitacora(tabla, accion);
        
        this.categoria = revis.getCategoriaList().get(0);
        this.listaArticulos = revis.getArticuloList();
        try {
            if (revis.getIdTitulo() != null) {
                this.idTitulo = revis.getIdTitulo();
            }
        } catch (Exception e) {

        }
        try {
            if (revis.getIdDonacion() != null) {
                //this.idDonante = revis.getIdDonacion().getIdDonacion();
                this.idDonacion = revis.getIdDonacion().getIdDonacion();

            }

        } catch (Exception e) {

        }

    }

    public void limpiar() {
        revista = new Revista();
        idTitulo = new Titulo();
        this.listaArticulos = new ArrayList<>();
        this.categoria = new Categoria();
        this.idDonacion = -1;

    }

    public void crear() {
        ArticulosJPA artiJAP = new ArticulosJPA();
        Integer inicialA = artiJAP.getClave() + 1;
        jpaRevista = new RevistasJPA();
        Integer inicialR = jpaRevista.getClave() + 1;
        revista.setIdRevista(inicialR);
        revista.setIdTitulo(idTitulo);

        //   revista.setArticuloList(listaArticulos);
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoria);
        revista.setCategoriaList(categorias);

        //revista.setIdDonacion(donacion);
        Calendar calendar = GregorianCalendar.getInstance();
        revista.setFechaingresor(calendar.getTime());
        jpaRevista = new RevistasJPA();
        //  jpaRevista.savePantalla(revista);

        for (Articulo ar : listaArticulos) {
            ar.setIdArticulo(inicialA);
            ar.setIdRevista(revista);
            // artiJAP = new ArticulosJPA();
            // artiJAP.savePantalla(ar);
            ++inicialA;
        }
        try {
            if (this.idDonacion != -1) {
                this.donacion = new Donaciones();
                this.donacion.setIdDonacion(idDonacion);
                revista.setIdDonacion(donacion);
            }
        } catch (Exception e) {

        }

        revista.setArticuloList(listaArticulos);
        
        String accion = "Registro de nueva Revista" ;
        String tabla = "Revista" ;
        new bitacoraBean().guardarbitacora(tabla, accion);

        jpaRevista.savePantalla(revista);

        this.revista = new Revista();
        this.donacion = new Donaciones();
        this.categoria = new Categoria();
        this.idTitulo = new Titulo();
        RequestContext.getCurrentInstance().execute("PF('nuevaPantalla').hide();");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Almacenada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void clearArticulo() {
        this.articulo = new Articulo();
    }

    public void crearArticulo() {
        articulo.setPalabraClaveList(listaPalabraClaveDestino);
        Autor autor = new Autor();
        autor.setIdAutor(idAutor);
        articulo.setIdAutor(autor);
        this.listaArticulos.add(articulo);
        this.articulo = new Articulo();
        listaPalabraClaveDestino = new ArrayList<>();
        listaPalabraClave = new ArrayList<>();
        idAutor=1;
        RequestContext.getCurrentInstance().execute("PF('articuloCrear').hide();");

    }

    public void articuloaModificar(Articulo arti) {
        this.articulo = arti;
        clone = arti;        
        PalabrasClavesJPA jpaPalabra = new PalabrasClavesJPA();
        listaPalabraClave = jpaPalabra.getPalabraClave();
        listaPalabraClaveDestino = new ArrayList<>();
        listaPalabraClaveDestino = arti.getPalabraClaveList();
        this.listaPalabraClave.removeAll(listaPalabraClaveDestino);
        this.listaPalabraClaveModificar = new ArrayList<>();

    }

    public void modificarArticulo() {
        Autor autor = new Autor();
        autor.setIdAutor(idAutor);
        articulo.setIdAutor(autor);
        articulo.setPalabraClaveList(listaPalabraClaveDestino);
        for (Articulo ar : listaArticulos) {
            if (ar.getTituloa().equalsIgnoreCase(clone.getTituloa())) {
                ar = articulo;
                break;
            }

        }
        /*  int lugar = this.listaArticulos.indexOf(articulo);        
        
        this.listaArticulos.remove(lugar);
        this.listaArticulos.add(articulo);*/
        articulo = new Articulo();
        RequestContext.getCurrentInstance().execute("PF('articuloModificar').hide();");

    }

    public void eliminarArticulo(Articulo arti) {
        int lugar = this.listaArticulos.indexOf(arti);
        this.listaArticulos.remove(lugar);
    }

    public void crearArticuloBoton() {
        articulo = new Articulo();
        listaPalabraClave.clear();
        listaPalabraClaveDestino.clear();
        listaPalabraClaveModificar.clear();
        PalabrasClavesJPA jpaPalabra = new PalabrasClavesJPA();
        listaPalabraClave = jpaPalabra.getPalabraClave();
        //RequestContext.getCurrentInstance().execute("PF('nuevaPantalla').show();");
    }

    public void modificarPalabraClave() {

    }

    public void guardarpc() {
        PalabraClave pc = new PalabraClave();
        PalabrasClavesJPA palabraJPA = new PalabrasClavesJPA();
        pc.setIdPalabra(palabraJPA.aumentarIdpalabra() + 1);
        pc.setNombrep(nombrepalabra);
        palabraJPA.guardarpcJPA(pc);
        nombrepalabra = "";
        listaPalabraClave.clear();
        PalabrasClavesJPA jpaPalabra = new PalabrasClavesJPA();
        listaPalabraClave = jpaPalabra.getPalabraClave();
        this.listaPalabraClave.removeAll(listaPalabraClaveDestino);
        this.listaPalabraClaveModificar = new ArrayList<>();
        RequestContext.getCurrentInstance().execute("PF('ingresarPalabra').hide();");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Almacenada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void trasferirLista() {
        this.listaPalabraClaveDestino.addAll(this.listaPalabraClaveModificar);
        this.listaPalabraClave.removeAll(this.listaPalabraClaveModificar);
        this.listaPalabraClaveModificar = new ArrayList<>();
    }

    public void trasferirEliminar() {
        this.listaPalabraClaveDestino.removeAll(listaPalabraClaveModificar);
        this.listaPalabraClave.addAll(listaPalabraClaveModificar);
        this.listaPalabraClaveModificar = new ArrayList<>();
    }

    public void modificarRevista() {
        ArticulosJPA artiJAP = new ArticulosJPA();
        Integer inicialA = artiJAP.getClave() + 1;
        revista.setIdTitulo(idTitulo);
        for (Articulo ar : listaArticulos) {
            if (ar.getIdArticulo() == null) {
                ar.setIdArticulo(inicialA);
                ar.setIdRevista(revista);
                artiJAP = new ArticulosJPA();
                artiJAP.savePantalla(ar);
                ++inicialA;
            } else {
                artiJAP = new ArticulosJPA();
                artiJAP.mergeTipoUsuario(articulo);
            }
        }

        if (this.idDonacion != -1) {
            donacion = new Donaciones();
            donacion.setIdDonacion(idDonacion);
            revista.setIdDonacion(donacion);
        }
        jpaRevista = new RevistasJPA();

        revista.setArticuloList(listaArticulos);
        jpaRevista.mergeTipoUsuario(revista);
        RequestContext.getCurrentInstance().execute("PF('dlg2').hide()");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Almacenada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void guardarpc2() {
        TituloJPA ti = new TituloJPA();
        idTitulo.setIdTitulo(ti.getClave() + 1);
        ti = new TituloJPA();
        ti.saveTitulo(idTitulo);
        idTitulo = new Titulo();
        RequestContext.getCurrentInstance().execute("PF('ingresarTitulos').hide()");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Almacenada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void eliminar(Revista revis) {
        jpaRevista = new RevistasJPA();
        String accion = "Datos de Revista Borrados" ;
        String tabla = "Revista" ;
        new bitacoraBean().guardarbitacora(tabla, accion);
        jpaRevista.deleteTipoUsuario(revis);

    }

    public void verRevista(Revista revis) {

    }

    public List<String> listaPaises() {
        return Paises.paises;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

}
