/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "donaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Donaciones.findAll", query = "SELECT d FROM Donaciones d"),
    @NamedQuery(name = "Donaciones.aumentarId", query = "SELECT MAX(d.idDonacion) FROM Donaciones d"),
    @NamedQuery(name = "Donaciones.findByIdDonacion", query = "SELECT d FROM Donaciones d WHERE d.idDonacion = :idDonacion"),
    @NamedQuery(name = "Donaciones.findByFechadonacion", query = "SELECT d FROM Donaciones d WHERE d.fechadonacion = :fechadonacion"),
    @NamedQuery(name = "Donaciones.findByArchivod", query = "SELECT d FROM Donaciones d WHERE d.archivod = :archivod")})
public class Donaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_donacion")
    private Integer idDonacion;
    @Column(name = "fechadonacion")
    @Temporal(TemporalType.DATE)
    private Date fechadonacion;
    @Size(max = 250)
    @Column(name = "archivod")
    private String archivod;
    @OneToMany(mappedBy = "idDonacion")
    private List<Revista> revistaList;
    @JoinColumn(name = "id_donante", referencedColumnName = "id_donante")
    @ManyToOne(optional = false)
    private Donate idDonante;

    public Donaciones() {
    }

    public Donaciones(Integer idDonacion) {
        this.idDonacion = idDonacion;
    }

    public Integer getIdDonacion() {
        return idDonacion;
    }

    public void setIdDonacion(Integer idDonacion) {
        this.idDonacion = idDonacion;
    }

    public Date getFechadonacion()  {  
        return fechadonacion;
    }

    public void setFechadonacion(Date fechadonacion) {
        this.fechadonacion = fechadonacion;
    }

    public String getArchivod() {
        return archivod;
    }

    public void setArchivod(String archivod) {
        this.archivod = archivod;
    }

    @XmlTransient
    public List<Revista> getRevistaList() {
        return revistaList;
    }

    public void setRevistaList(List<Revista> revistaList) {
        this.revistaList = revistaList;
    }

    public Donate getIdDonante() {
        return idDonante;
    }

    public void setIdDonante(Donate idDonante) {
        this.idDonante = idDonante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDonacion != null ? idDonacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Donaciones)) {
            return false;
        }
        Donaciones other = (Donaciones) object;
        if ((this.idDonacion == null && other.idDonacion != null) || (this.idDonacion != null && !this.idDonacion.equals(other.idDonacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Donaciones[ idDonacion=" + idDonacion + " ]";
    }
    
}
