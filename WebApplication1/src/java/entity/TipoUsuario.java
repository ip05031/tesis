/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author IPalacios
 */
@Entity
@Table(name = "tipo_usuario")
@XmlRootElement
@NamedQueries({
    
    @NamedQuery(name = "TipoUsuario.tipoUsuario", query = "SELECT t.idTusuario, t.nombretp FROM TipoUsuario t order by t.idTusuario"),
    @NamedQuery(name = "TipoUsuario.clave", query = "SELECT MAX(t.idTusuario) FROM TipoUsuario t"),
    @NamedQuery(name = "TipoUsuario.findAll", query = "SELECT t FROM TipoUsuario t order by t.idTusuario"),
    @NamedQuery(name = "TipoUsuario.findByIdTusuario", query = "SELECT t FROM TipoUsuario t WHERE t.idTusuario = :idTusuario"),
    @NamedQuery(name = "TipoUsuario.findByNombretp", query = "SELECT t FROM TipoUsuario t WHERE t.nombretp = :nombretp"),
    @NamedQuery(name = "TipoUsuario.findByDescripciontp", query = "SELECT t FROM TipoUsuario t WHERE t.descripciontp = :descripciontp")})
public class TipoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tusuario")
    private Integer idTusuario;
    @Size(max = 50)
    @Column(name = "nombretp")
    private String nombretp;
    @Size(max = 100)
    @Column(name = "descripciontp")
    private String descripciontp;
    @JoinTable(name = "menu", joinColumns = {
        @JoinColumn(name = "id_tusuario", referencedColumnName = "id_tusuario")}, inverseJoinColumns = {
        @JoinColumn(name = "id_pantalla", referencedColumnName = "id_pantalla")})
    @ManyToMany
    private List<Pantalla> pantallaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTusuario")
    private List<Usuario> usuarioList;

    public TipoUsuario() {
    }

    public TipoUsuario(Integer idTusuario) {
        this.idTusuario = idTusuario;
    }

    public Integer getIdTusuario() {
        return idTusuario;
    }

    public void setIdTusuario(Integer idTusuario) {
        this.idTusuario = idTusuario;
    }

    public String getNombretp() {
        return nombretp;
    }

    public void setNombretp(String nombretp) {
        this.nombretp = nombretp;
    }

    public String getDescripciontp() {
        return descripciontp;
    }

    public void setDescripciontp(String descripciontp) {
        this.descripciontp = descripciontp;
    }

    @XmlTransient
    public List<Pantalla> getPantallaList() {
        return pantallaList;
    }

    public void setPantallaList(List<Pantalla> pantallaList) {
        this.pantallaList = pantallaList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTusuario != null ? idTusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoUsuario)) {
            return false;
        }
        TipoUsuario other = (TipoUsuario) object;
        if ((this.idTusuario == null && other.idTusuario != null) || (this.idTusuario != null && !this.idTusuario.equals(other.idTusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TipoUsuario[ idTusuario=" + idTusuario + " ]";
    }
    
}
