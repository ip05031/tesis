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
@Table(name = "descarga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Descarga.findAll", query = "SELECT d FROM Descarga d"),
    @NamedQuery(name = "Descarga.findMaxId", query = "SELECT MAX(d.idDescarga) FROM Descarga d"),
    @NamedQuery(name = "Descarga.findByIdDescarga", query = "SELECT d FROM Descarga d WHERE d.idDescarga = :idDescarga"),
    @NamedQuery(name = "Descarga.findByFechad", query = "SELECT d FROM Descarga d WHERE d.fechad = :fechad"),
    @NamedQuery(name = "Descarga.findByHorad", query = "SELECT d FROM Descarga d WHERE d.horad = :horad")})
public class Descarga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_descarga")
    private Integer idDescarga;
    @Column(name = "fechad")
    @Temporal(TemporalType.DATE)
    private Date fechad;
    @Column(name = "horad")
    @Temporal(TemporalType.TIME)  
    private Date horad;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;
    @JoinColumn(name = "id_revista", referencedColumnName = "id_revista")
    @ManyToOne(optional = false)
    private Revista idRevista;
    @JoinColumn(name = "id_articulo", referencedColumnName = "id_articulo")
    @ManyToOne(optional = false)
    private Articulo idArticulo;

    public Descarga() {
    }

    public Descarga(Integer idDescarga) {
        this.idDescarga = idDescarga;
    }

    public Integer getIdDescarga() {
        return idDescarga;
    }

    public void setIdDescarga(Integer idDescarga) {
        this.idDescarga = idDescarga;
    }

    public Date getFechad() {
        return fechad;
    }

    public void setFechad(Date fechad) {
        this.fechad = fechad;
    }

    public Date getHorad() {
        return horad;
    }

    public void setHorad(Date horad) {
        this.horad = horad;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Revista getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(Revista idRevista) {
        this.idRevista = idRevista;
    }

    public Articulo getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Articulo idArticulo) {
        this.idArticulo = idArticulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDescarga != null ? idDescarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Descarga)) {
            return false;
        }
        Descarga other = (Descarga) object;
        if ((this.idDescarga == null && other.idDescarga != null) || (this.idDescarga != null && !this.idDescarga.equals(other.idDescarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Descarga[ idDescarga=" + idDescarga + " ]";
    }
    
}
