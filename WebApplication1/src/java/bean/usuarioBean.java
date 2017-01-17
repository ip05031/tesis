package bean;

import controller.PantallaJPA;
import controller.TipoUsuarioJPA;
import controller.UsuarioJPA;
import entity.Pantalla;
import entity.TipoUsuario;
import entity.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import static javax.print.attribute.Size2DSyntax.MM;
import org.apache.jasper.tagplugins.jstl.ForEach;
import org.primefaces.context.RequestContext;

@Named(value = "usuarioBean")
@SessionScoped
public class usuarioBean implements Serializable {

    private List<Usuario> listadoUsuarios = new ArrayList<>();
    private String nombreUsuario;
    private String apellidoUsuario;
    private String ocupacionUsuario;
    private Date fechaNacUsuario;
    private String correoUsuario;
    private String nickUsuario;
    private String contraUsuario;
    private int estadoUsuario;
    private Date fechaAltaUsuario;
    private String paisUsuario;
    private char sexoUsuario;
    private int idUsuario;
    private int idTipoUsuario;
    private UsuarioJPA usuarioJPA;
    private Usuario editUsuario = new Usuario();

    private Boolean validar;
    private String validarNombre;

    public usuarioBean() {

    }

    /*----------------------------------------------------------------------------------------------------------------------------*/
    // para usar en xhtml
    /*----------------------------------------------------------------------------------------------------------------------------*/
    public List<Usuario> getir(int tipo) {
        return new UsuarioJPA().getUsuarios(tipo);
    }

    public void usuarioEditar(Usuario editUsuario) {
        this.idTipoUsuario = editUsuario.getIdTusuario().getIdTusuario();
        this.validarNombre = editUsuario.getNickname();
        this.setEditUsuario(editUsuario);

    }

    public void saveUsuario() {
        usuarioJPA = new UsuarioJPA();
        Usuario user = new Usuario();

        TipoUsuario tu = new TipoUsuario();
        tu.setIdTusuario(idTipoUsuario);

        Calendar calendario = GregorianCalendar.getInstance();
        Date fechaRegistro = calendario.getTime();

        String contra1 = sha256(contraUsuario);

        user.setIdUsuario(usuarioJPA.getClave());
        user.setNombreu(this.nombreUsuario);
        user.setApellidosu(this.apellidoUsuario);
        user.setOcupacion(ocupacionUsuario);
        user.setSexo(sexoUsuario);
        user.setFechanacimientou(fechaNacUsuario);
        user.setIdTusuario(tu);
        user.setPaisu(paisUsuario);
        user.setNickname(nickUsuario);
        user.setCorreou(correoUsuario);
        user.setPassword(contra1);
        user.setFechau(fechaRegistro);
        user.setEstadoi(2);

        try {
            validarUsuarioExiste();
            if (validar) {
                usuarioJPA.saveUsuario(user);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Usuario Creado exitosamente!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('nuevoUsuario').show();");
                FacesContext context4 = FacesContext.getCurrentInstance();
                Usuario user4 = (Usuario) context4.getExternalContext().getSessionMap().get("logueado");
                String accion = "Registró de nueva Usuario por el usuario = " + user4.getIdUsuario();
                String tabla = "Usuario";
                new bitacoraBean().guardarbitacora(tabla, accion);
                limpiarDatosUsuario();
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Usuario Registrado Correctamente!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
              //  FacesContext.getCurrentInstance().addMessage("Message4", new FacesMessage(FacesMessage.SEVERITY_INFO, "!", "Usuario Registrado Correctamente"));
                 context.execute("PF('nuevoUsuario').hide();");
            } else {
                FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Nombre de usuario no disponible"));
            }

        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Ha ocurrido un error!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public void limpiarDatosUsuario() {
        this.nombreUsuario = "";
        this.apellidoUsuario = "";
        this.ocupacionUsuario = "";
        this.sexoUsuario = 'M';
        this.fechaNacUsuario = null;
        this.paisUsuario = "";
        this.nickUsuario = "";
        this.correoUsuario = "";
        this.contraUsuario = "";
    }

    public void modificarUsuario() {
        usuarioJPA = new UsuarioJPA();

        System.out.println("-" + contraUsuario.compareTo(""));
        if (contraUsuario.compareTo("") != 0) {
            this.editUsuario.setPassword(contraUsuario);
        }
        /* if (paisUsuario.compareTo(paisUsuario) != 0) {
            this.editUsuario.setPaisu(paisUsuario);
        }*/
        if (validarNombre.contentEquals(editUsuario.getNickname())) {
            this.editUsuario.setPaisu(paisUsuario);
            this.editUsuario.setIdTusuario(new TipoUsuario(idTipoUsuario));
            System.out.println("" + this.editUsuario.getEstadoi());

            usuarioJPA.updateUsuario(this.editUsuario);
            System.out.println("se envio...");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Usuario Modificado exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            RequestContext context = RequestContext.getCurrentInstance();
            FacesContext context4 = FacesContext.getCurrentInstance();
            Usuario user4 = (Usuario) context4.getExternalContext().getSessionMap().get("logueado");
            String accion = "Registró de Modificacion de Revista por el usuario = " + user4.getIdUsuario();
            String tabla = "Revista";
            new bitacoraBean().guardarbitacora(tabla, accion);
            context.execute("PF('modalPantallaUpdate').hide();");
        } else {
            validarUsuarioExiste();
            if (validar) {
                this.editUsuario.setPaisu(paisUsuario);
                this.editUsuario.setIdTusuario(new TipoUsuario(idTipoUsuario));
                System.out.println("" + this.editUsuario.getEstadoi());

                usuarioJPA.updateUsuario(this.editUsuario);
                System.out.println("se envio...");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Usuario Modificado exitosamente!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                RequestContext context = RequestContext.getCurrentInstance();
                FacesContext context4 = FacesContext.getCurrentInstance();
                Usuario user4 = (Usuario) context4.getExternalContext().getSessionMap().get("logueado");
                String accion = "Registró de Modificacion de Revista por el usuario = " + user4.getIdUsuario();
                String tabla = "Revista";
                new bitacoraBean().guardarbitacora(tabla, accion);
                context.execute("PF('modalPantallaUpdate').hide();");

            } else {

                FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Nombre de usuario no disponible"));
            }

        }
    }

    public void limpiar() {
        this.setApellidoUsuario("");
        this.setContraUsuario("");
        this.setCorreoUsuario("");
        this.setEstadoUsuario(0);
        this.setEditUsuario(null);
        this.setFechaAltaUsuario(null);
        this.setFechaNacUsuario(null);
        this.setIdTipoUsuario(1);
        this.setIdUsuario(1);
        this.setNickUsuario("");
        this.setNombreUsuario("");
        this.setOcupacionUsuario("1");
        this.setPaisUsuario("1");
    }

    public void eliminarUsuario(Usuario user) {
        System.out.println("eliminar usuario");
        try {
            System.out.println("" + user.getNombreu());
            System.out.println("" + user.getEstadoi());
            user.setEstadoi(3);
            usuarioJPA = new UsuarioJPA();
            usuarioJPA.updateUsuario(user);
            FacesContext context4 = FacesContext.getCurrentInstance();
            Usuario user4 = (Usuario) context4.getExternalContext().getSessionMap().get("logueado");
            String accion = "Registró de Eliminacion de Revista por el usuario = " + user4.getIdUsuario();
            String tabla = "Revista";
            new bitacoraBean().guardarbitacora(tabla, accion);
        } catch (Exception e) {
        }
    }

    public void validarUsuarioExiste() {
        this.validar = true;
        usuarioJPA = new UsuarioJPA();
        String nickname = this.nickUsuario;
        if (nickUsuario.length() > 3) {
            System.out.println("comineza la validacion");
            if (usuarioJPA.searchNickname(nickname)) {
                validar = false;
                FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Nombre de usuario no disponible"));
            } else {
                FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_INFO, "!Validado", "Usuario Válido"));
            }
        }
    }

    public void eliminarUsuariosInactivos() {
        usuarioJPA = new UsuarioJPA();
        Calendar calendar = GregorianCalendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR, -364);
        List<Usuario> listado = usuarioJPA.eliminarUsuario(calendar.getTime());
        String accion = "Eliminar Listados de usuario para la fecha: " + calendar.getTime();
        String tabla = "Usuario";
        new bitacoraBean().guardarbitacora(tabla, accion);

        for (Usuario u : listado) {
            System.out.println(u.getApellidosu());
            usuarioJPA = new UsuarioJPA();
            usuarioJPA.eliminarUsuarioJPA(u);
        }
    }

    public void biginteger() {
        BigInteger A = new BigInteger("1");
        BigInteger I = new BigInteger("10542516131213232");
        A = A.add(I);
        System.out.println(A);

    }

    public void probandoSha256() {
        String test1 = sha256("admin123");
        String test2 = sha256("visitante123");
        String test3 = sha256("123456789");
        String test4 = sha256("contraseña");
        System.out.println(test1);
        System.out.println(test2);
        System.out.println(test3);
        System.out.println(test4);
    }

    public String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /*----------------------------------------------------------------------------------------------------------------------------*/
    // SETTER & GETTER variables
    /*----------------------------------------------------------------------------------------------------------------------------*/
    public List<Usuario> getListadoUsuarios() {
        return listadoUsuarios;
    }

    public void setListadoUsuarios(List<Usuario> listadoUsuarios) {
        this.listadoUsuarios = listadoUsuarios;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getOcupacionUsuario() {
        return ocupacionUsuario;
    }

    public void setOcupacionUsuario(String ocupacionUsuario) {
        this.ocupacionUsuario = ocupacionUsuario;
    }

    public Date getFechaNacUsuario() {
        return fechaNacUsuario;
    }

    public void setFechaNacUsuario(Date fechaNacUsuario) {
        this.fechaNacUsuario = fechaNacUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getNickUsuario() {
        return nickUsuario;
    }

    public void setNickUsuario(String nickUsuario) {
        this.nickUsuario = nickUsuario;
    }

    public String getContraUsuario() {
        return contraUsuario;
    }

    public void setContraUsuario(String contraUsuario) {
        this.contraUsuario = contraUsuario;
    }

    public int getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(int estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public Date getFechaAltaUsuario() {
        return fechaAltaUsuario;
    }

    public void setFechaAltaUsuario(Date fechaAltaUsuario) {
        this.fechaAltaUsuario = fechaAltaUsuario;
    }

    public String getPaisUsuario() {
        return paisUsuario;
    }

    public void setPaisUsuario(String paisUsuario) {
        this.paisUsuario = paisUsuario;
    }

    public char getSexoUsuario() {
        return sexoUsuario;
    }

    public void setSexoUsuario(char sexoUsuario) {
        this.sexoUsuario = sexoUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public UsuarioJPA getUsuarioJPA() {
        return usuarioJPA;
    }

    public void setUsuarioJPA(UsuarioJPA usuarioJPA) {
        this.usuarioJPA = usuarioJPA;
    }

    public Usuario getEditUsuario() {
        return editUsuario;
    }

    public void setEditUsuario(Usuario editUsuario) {
        this.editUsuario = editUsuario;
    }

}
