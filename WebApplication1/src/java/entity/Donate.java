/*
 * */

package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
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
 * @author Sara Angulo
 */
@Entity
@Table(name = "donate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Donate.findAll", query = "SELECT d FROM Donate d"),
    @NamedQuery(name = "Donate.aumentarId", query = "SELECT MAX(d.idDonante) FROM Donate d"),
    @NamedQuery(name = "Donate.findByIdDonante", query = "SELECT d FROM Donate d WHERE d.idDonante = :idDonante"),
    @NamedQuery(name = "Donate.findByNombred", query = "SELECT d FROM Donate d WHERE d.nombred = :nombred"),
    @NamedQuery(name = "Donate.findByDireccion", query = "SELECT d FROM Donate d WHERE d.direccion = :direccion"),
    @NamedQuery(name = "Donate.findByTelefono", query = "SELECT d FROM Donate d WHERE d.telefono = :telefono"),
    @NamedQuery(name = "Donate.findByCorreodonate", query = "SELECT d FROM Donate d WHERE d.correodonate = :correodonate")})
public class Donate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_donante")
    private Integer idDonante;
    @Size(max = 150)
    @Column(name = "nombred")
    private String nombred;
    @Size(max = 250)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 9)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 150)
    @Column(name = "correodonate")
    private String correodonate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDonante")
    private List<Donaciones> donacionesList;

    public Donate() {   
    }

    public Donate(Integer idDonante) {
        this.idDonante = idDonante;
    }

    public Integer getIdDonante() {
        return idDonante;
    }

    public void setIdDonante(Integer idDonante) {
        this.idDonante = idDonante;
    }

    public String getNombred() {
        return nombred;
    }

    public void setNombred(String nombred) {
        this.nombred = nombred;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreodonate() {
        return correodonate;
    }

    public void setCorreodonate(String correodonate) {
        this.correodonate = correodonate;
    }

    @XmlTransient
    public List<Donaciones> getDonacionesList() {
        return donacionesList;
    }

    public void setDonacionesList(List<Donaciones> donacionesList) {
        this.donacionesList = donacionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDonante != null ? idDonante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Donate)) {
            return false;
        }
        Donate other = (Donate) object;
        if ((this.idDonante == null && other.idDonante != null) || (this.idDonante != null && !this.idDonante.equals(other.idDonante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Donate[ idDonante=" + idDonante + " ]";
    }
    
}
