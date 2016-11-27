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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author IPalacios
 */
@Entity
@Table(name = "inventario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventario.findAll", query = "SELECT i FROM Inventario i"),
    @NamedQuery(name = "Inventario.findMaxId", query = "SELECT MAX(i.idInventario) FROM Inventario i"),
    @NamedQuery(name = "Inventario.findMinIdDisp", query = "SELECT MIN(i.idInventario) FROM Inventario i  where i.idRevista.idRevista = :idRevista AND i.existenciai = '1' and i.idEstado.idEstado = '1'"),
    @NamedQuery(name = "Inventario.findMinIdDisp2", query = "SELECT i2 FROM Inventario i2 WHERE i2.idInventario = (SELECT MIN(i.idInventario) FROM Inventario i  where i.idRevista.idRevista = :idRevista AND i.existenciai = '1' and i.idEstado.idEstado = '1')"),
    @NamedQuery(name = "Inventario.findByIdInventario", query = "SELECT i FROM Inventario i WHERE i.idInventario = :idInventario"),
    @NamedQuery(name = "Inventario.findByExistenciai", query = "SELECT i FROM Inventario i WHERE i.existenciai = :existenciai")})
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_inventario")
    private Integer idInventario;
    @Column(name = "existenciai")
    private Integer existenciai;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInventario")
    private List<Prestamo> prestamoList;
    @JoinColumn(name = "id_revista", referencedColumnName = "id_revista")
    @ManyToOne(optional = false)
    private Revista idRevista;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne
    private Estado idEstado;

    public Inventario() {
    }

    public Inventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getExistenciai() {
        return existenciai;
    }

    public void setExistenciai(Integer existenciai) {
        this.existenciai = existenciai;
    }

    @XmlTransient
    public List<Prestamo> getPrestamoList() {
        return prestamoList;
    }

    public void setPrestamoList(List<Prestamo> prestamoList) {
        this.prestamoList = prestamoList;
    }

    public Revista getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(Revista idRevista) {
        this.idRevista = idRevista;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInventario != null ? idInventario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventario)) {
            return false;
        }
        Inventario other = (Inventario) object;
        if ((this.idInventario == null && other.idInventario != null) || (this.idInventario != null && !this.idInventario.equals(other.idInventario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Inventario[ idInventario=" + idInventario + " ]";
    }
    
}
