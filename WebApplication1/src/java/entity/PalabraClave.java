/*
 **/
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
 * @author Sara Angulo
 */
@Entity
@Table(name = "palabra_clave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PalabraClave.finlistaNombre", query = "SELECT p.nombrep FROM PalabraClave p order by p.idPalabra"),
    @NamedQuery(name = "PalabraClave.findArticulo", query = "SELECT p.nombrep FROM PalabraClave p INNER JOIN p.articuloList a  Where a.idArticulo=:idArticulo order by p.idPalabra"),
    @NamedQuery(name = "PalabraClave.findAll", query = "SELECT p FROM PalabraClave p order by p.idPalabra"),
    @NamedQuery(name = "PalabraClave.aumentarId", query = "SELECT MAX(p.idPalabra) FROM PalabraClave p"),
    @NamedQuery(name = "PalabraClave.findByIdPalabra", query = "SELECT p FROM PalabraClave p WHERE p.idPalabra = :idPalabra"),
    @NamedQuery(name = "PalabraClave.findByNombrep", query = "SELECT p FROM PalabraClave p WHERE p.nombrep = :nombrep")})

public class PalabraClave implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_palabra")
    private Integer idPalabra;
    @Size(max = 150)
    @Column(name = "nombrep")
    private String nombrep;
   
    @ManyToMany(mappedBy ="palabraClaveList")
    private List<Articulo> articuloList;
//Cambie datos de la tabla join aser cambio manual si da error
    public PalabraClave() {
    }

    public PalabraClave(Integer idPalabra) {
        this.idPalabra = idPalabra;
    }

    public Integer getIdPalabra() {
        return idPalabra;
    }

    public void setIdPalabra(Integer idPalabra) {
        this.idPalabra = idPalabra;
    }

    public String getNombrep() {
        return nombrep;
    }

    public void setNombrep(String nombrep) {
        this.nombrep = nombrep;
    }

    @XmlTransient
    public List<Articulo> getArticuloList() {
        return articuloList;
    }

    public void setArticuloList(List<Articulo> articuloList) {
        this.articuloList = articuloList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPalabra != null ? idPalabra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PalabraClave)) {
            return false;
        }
        PalabraClave other = (PalabraClave) object;
        if ((this.idPalabra == null && other.idPalabra != null) || (this.idPalabra != null && !this.idPalabra.equals(other.idPalabra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PalabraClave[ idPalabra=" + idPalabra + " ]";
    }
    
}
