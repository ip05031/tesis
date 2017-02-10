/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author IPalacios
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findNickname", query = "SELECT u FROM Usuario u where u.nickname like :nickname "),
    @NamedQuery(name = "Usuario.findEmployee", query = "SELECT u FROM Usuario u WHERE u.idTusuario.idTusuario != :idTusuario and u.estadoi = '2' "),
    @NamedQuery(name = "Usuario.findVisitor", query = "SELECT u FROM Usuario u WHERE u.idTusuario.idTusuario = :idTusuario and u.estadoi = '2'"), 
    @NamedQuery(name = "Usuario.findVisitorValidos", query = "SELECT u FROM Usuario u WHERE u.idTusuario.idTusuario = :idTusuario and u.estadoi = '2' and u.envio = TRUE"), 
    @NamedQuery(name = "Usuario.findVisitor2", query = "SELECT u FROM Usuario u WHERE u.idTusuario.idTusuario = :idTusuario and u.estadoi = '1'"),
    @NamedQuery(name = "Usuario.findMaxId", query = "SELECT MAX(u.idUsuario) FROM Usuario u"),
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByEliminar", query = "SELECT u FROM Usuario u WHERE u.fechavisita < :fechavisita"),
    @NamedQuery(name = "Usuario.findBylogin", query = "SELECT u FROM Usuario u WHERE u.nickname = :nickname AND u.password = :contra and u.estadoi ='2' "),
    @NamedQuery(name = "Usuario.findByNombreu", query = "SELECT u FROM Usuario u WHERE u.nombreu = :nombreu"),
    @NamedQuery(name = "Usuario.findByApellidosu", query = "SELECT u FROM Usuario u WHERE u.apellidosu = :apellidosu"),
    @NamedQuery(name = "Usuario.findByOcupacion", query = "SELECT u FROM Usuario u WHERE u.ocupacion = :ocupacion"),
    @NamedQuery(name = "Usuario.findByFechanacimientou", query = "SELECT u FROM Usuario u WHERE u.fechanacimientou = :fechanacimientou"),
    @NamedQuery(name = "Usuario.findByCorreou", query = "SELECT u FROM Usuario u WHERE u.correou = :correou"),
    @NamedQuery(name = "Usuario.findByNickname", query = "SELECT u FROM Usuario u WHERE u.nickname = :nickname"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByEstadoi", query = "SELECT u FROM Usuario u WHERE u.estadoi = :estadoi"),
    @NamedQuery(name = "Usuario.findByFechau", query = "SELECT u FROM Usuario u WHERE u.fechau = :fechau"),
    @NamedQuery(name = "Usuario.findByPaisu", query = "SELECT u FROM Usuario u WHERE u.paisu = :paisu")})
public class Usuario implements Serializable {

    @Column(name = "sexo")
    private Character sexo;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Size(max = 60)
    @Column(name = "nombreu")
    private String nombreu;
    @Size(max = 60)
    @Column(name = "apellidosu")
    private String apellidosu;
    @Size(max = 60)
    @Column(name = "ocupacion_")
    private String ocupacion;
    @Column(name = "fechanacimientou")
    @Temporal(TemporalType.DATE)
    private Date fechanacimientou;
    @Size(max = 150)
    @Column(name = "correou")
    private String correou;
    @Size(max = 60)
    @Column(name = "nickname")
    private String nickname;
    @Size(max = 250)
    @Column(name = "password")
    private String password;
    @Column(name = "estadoi")
    private Integer estadoi;
    @Column(name = "fechau")
    @Temporal(TemporalType.DATE)
    private Date fechau;
    @Size(max = 30)
    @Column(name = "paisu")
    private String paisu;
    @Size(max = 14)
    @Column(name = "claveValidar")
    private String claveValidar;
    @Column(name = "fechavisita")
    @Temporal(TemporalType.DATE)
    private Date fechavisita;
    @Basic(optional = false)
    @NotNull
    @Column(name = "envio")
    private boolean envio;
    @JoinColumn(name = "id_tusuario", referencedColumnName = "id_tusuario")
    @ManyToOne(optional = false)
    private TipoUsuario idTusuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Prestamo> prestamoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Evento> eventoList;
    @OneToMany(mappedBy = "idUsuario")
    private List<Descarga> descargaList;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreu() {
        return nombreu;
    }

    public void setNombreu(String nombreu) {
        this.nombreu = nombreu;
    }

    public String getApellidosu() {
        return apellidosu;
    }

    public void setApellidosu(String apellidosu) {
        this.apellidosu = apellidosu;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public Date getFechanacimientou() {
        return fechanacimientou;
    }

    public void setFechanacimientou(Date fechanacimientou) {
        this.fechanacimientou = fechanacimientou;
    }

    public String getCorreou() {
        return correou;
    }

    public void setCorreou(String correou) {
        this.correou = correou;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEstadoi() {
        return estadoi;
    }

    public void setEstadoi(Integer estadoi) {
        this.estadoi = estadoi;
    }

    public Date getFechau() {
        return fechau;
    }

    public void setFechau(Date fechau) {
        this.fechau = fechau;
    }

    public String getPaisu() {
        return paisu;
    }

    public void setPaisu(String paisu) {
        this.paisu = paisu;
    }
    
    public String getClaveValidar() {
        return claveValidar;
    }

    public void setClaveValidar(String claveValidar) {
        this.claveValidar = claveValidar;
    }

    public TipoUsuario getIdTusuario() {
        return idTusuario;
    }

    public void setIdTusuario(TipoUsuario idTusuario) {
        this.idTusuario = idTusuario;
    }
    
    public Date getFechavisita() {
        return fechavisita;
    }

    public void setFechavisita(Date fechavisita) {
        this.fechavisita = fechavisita;
    }

    @XmlTransient
    public List<Prestamo> getPrestamoList() {
        return prestamoList;
    }

    public void setPrestamoList(List<Prestamo> prestamoList) {
        this.prestamoList = prestamoList;
    }

    @XmlTransient
    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    @XmlTransient
    public List<Descarga> getDescargaList() {
        return descargaList;
    }

    public void setDescargaList(List<Descarga> descargaList) {
        this.descargaList = descargaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Usuario[ idUsuario=" + idUsuario + " ]";
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }
    
    public boolean getEnvio() {
        return envio;
    }

    public void setEnvio(boolean envio) {
        this.envio = envio;
    }
    
}
