/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "pantalla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pantalla.findMaxId", query = "SELECT MAX(p.idPantalla) FROM Pantalla p"),
    @NamedQuery(name = "Pantalla.findAll", query = "SELECT p FROM Pantalla p order by p.idPantalla"),
    @NamedQuery(name = "Pantalla.findByIdPantalla", query = "SELECT p FROM Pantalla p WHERE p.idPantalla = :idPantalla"),
    @NamedQuery(name = "Pantalla.findByNombrepa", query = "SELECT p FROM Pantalla p WHERE p.nombrepa = :nombrepa"),
    @NamedQuery(name = "Pantalla.findByAccesopa", query = "SELECT p FROM Pantalla p WHERE p.accesopa = :accesopa")})
public class Pantalla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_pantalla")
    private Integer idPantalla;
    @Size(max = 60)
    @Column(name = "nombrepa")
    private String nombrepa;
    @Size(max = 250)
    @Column(name = "accesopa")
    private String accesopa;
    @Column(name = "permiso")
    private Boolean permiso;
    @ManyToMany(mappedBy = "pantallaList")
    private List<TipoUsuario> tipoUsuarioList;

    public Pantalla() {
    }

    public Pantalla(Integer idPantalla) {
        this.idPantalla = idPantalla;
    }

    public Integer getIdPantalla() {
        return idPantalla;
    }

    public void setIdPantalla(Integer idPantalla) {
        this.idPantalla = idPantalla;
    }

    public String getNombrepa() {
        return nombrepa;
    }

    public void setNombrepa(String nombrepa) {
        this.nombrepa = nombrepa;
    }

    public String getAccesopa() {
        return accesopa;
    }

    public void setAccesopa(String accesopa) {
        this.accesopa = accesopa;
    }
    
    public Boolean getPermiso() {
        return permiso;
    }

    public void setPermiso(Boolean permiso) {
        this.permiso = permiso;
    }

    @XmlTransient
    public List<TipoUsuario> getTipoUsuarioList() {
        return tipoUsuarioList;
    }

    public void setTipoUsuarioList(List<TipoUsuario> tipoUsuarioList) {
        this.tipoUsuarioList = tipoUsuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPantalla != null ? idPantalla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pantalla)) {
            return false;
        }
        Pantalla other = (Pantalla) object;
        if ((this.idPantalla == null && other.idPantalla != null) || (this.idPantalla != null && !this.idPantalla.equals(other.idPantalla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pantalla[ idPantalla=" + idPantalla + " ]";
    }
    
}
