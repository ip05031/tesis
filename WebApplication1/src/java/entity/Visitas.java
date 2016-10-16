/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IPalacios
 */
@Entity
@Table(name = "visitas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visitas.findAll", query = "SELECT v FROM Visitas v"),
    @NamedQuery(name = "Visitas.findByFechav", query = "SELECT v FROM Visitas v WHERE v.fechav = :fechav"),
    @NamedQuery(name = "Visitas.findByCantidadv", query = "SELECT v FROM Visitas v WHERE v.cantidadv = :cantidadv")})
public class Visitas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechav")
    @Temporal(TemporalType.DATE)
    private Date fechav;
    @Column(name = "cantidadv")
    private Integer cantidadv;

    public Visitas() {
    }

    public Visitas(Date fechav) {
        this.fechav = fechav;
    }

    public Date getFechav() {
        return fechav;
    }

    public void setFechav(Date fechav) {
        this.fechav = fechav;
    }

    public Integer getCantidadv() {
        return cantidadv;
    }

    public void setCantidadv(Integer cantidadv) {
        this.cantidadv = cantidadv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fechav != null ? fechav.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visitas)) {
            return false;
        }
        Visitas other = (Visitas) object;
        if ((this.fechav == null && other.fechav != null) || (this.fechav != null && !this.fechav.equals(other.fechav))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Visitas[ fechav=" + fechav + " ]";
    }
    
}
