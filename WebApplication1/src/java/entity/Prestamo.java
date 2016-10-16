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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "prestamo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prestamo.findAll", query = "SELECT p FROM Prestamo p"),
    @NamedQuery(name = "Prestamo.findPrestamos", query = "SELECT p FROM Prestamo p where p.tipop = '1'"),
    @NamedQuery(name = "Prestamo.findDevolucion", query = "SELECT p FROM Prestamo p where p.tipop = '2' "),
    @NamedQuery(name = "Prestamo.findMaxId", query = "SELECT MAX(p.idPrestamo) FROM Prestamo p"),
    @NamedQuery(name = "Prestamo.findByIdPrestamo", query = "SELECT p FROM Prestamo p WHERE p.idPrestamo = :idPrestamo"),
    @NamedQuery(name = "Prestamo.findByFechap", query = "SELECT p FROM Prestamo p WHERE p.fechap = :fechap"),
    @NamedQuery(name = "Prestamo.findByHorap", query = "SELECT p FROM Prestamo p WHERE p.horap = :horap"),
    @NamedQuery(name = "Prestamo.findByTipop", query = "SELECT p FROM Prestamo p WHERE p.tipop = :tipop"),
    @NamedQuery(name = "Prestamo.findByPaginasp", query = "SELECT p FROM Prestamo p WHERE p.paginasp = :paginasp")})
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_prestamo")
    private Integer idPrestamo;
    @Column(name = "fechap")
    @Temporal(TemporalType.DATE)
    private Date fechap;
    @Column(name = "horap")
    @Temporal(TemporalType.DATE)
    private Date horap;
    @Column(name = "tipop")
    private Integer tipop;
    @Column(name = "paginasp")
    private Integer paginasp;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @JoinColumn(name = "id_inventario", referencedColumnName = "id_inventario")
    @ManyToOne(optional = false)
    private Inventario idInventario;

    public Prestamo() {
    }

    public Prestamo(Integer idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Integer getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Integer idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Date getFechap() {
        return fechap;
    }

    public void setFechap(Date fechap) {
        this.fechap = fechap;
    }

    public Date getHorap() {
        return horap;
    }

    public void setHorap(Date horap) {
        this.horap = horap;
    }

    public Integer getTipop() {
        return tipop;
    }

    public void setTipop(Integer tipop) {
        this.tipop = tipop;
    }

    public Integer getPaginasp() {
        return paginasp;
    }

    public void setPaginasp(Integer paginasp) {
        this.paginasp = paginasp;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Inventario getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Inventario idInventario) {
        this.idInventario = idInventario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrestamo != null ? idPrestamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prestamo)) {
            return false;
        }
        Prestamo other = (Prestamo) object;
        if ((this.idPrestamo == null && other.idPrestamo != null) || (this.idPrestamo != null && !this.idPrestamo.equals(other.idPrestamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Prestamo[ idPrestamo=" + idPrestamo + " ]";
    }
    
}
