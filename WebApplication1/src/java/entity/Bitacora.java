/*
 */
package entity;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IPalacios
 */
@Entity
@Table(name = "bitacora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bitacora.findAll", query = "SELECT b FROM Bitacora b"),
    @NamedQuery(name = "Bitacora.findMaxId", query = "SELECT MAX(b.idBitacora) FROM Bitacora b"),
    @NamedQuery(name = "Bitacora.findByIdBitacora", query = "SELECT b FROM Bitacora b WHERE b.idBitacora = :idBitacora"),
    @NamedQuery(name = "Bitacora.findByTabla", query = "SELECT b FROM Bitacora b WHERE b.tabla = :tabla"),
    @NamedQuery(name = "Bitacora.findByFechabitacora", query = "SELECT b FROM Bitacora b WHERE b.fechabitacora = :fechabitacora"),
    @NamedQuery(name = "Bitacora.findByAccion", query = "SELECT b FROM Bitacora b WHERE b.accion = :accion")})
public class Bitacora implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_bitacora")
    private BigInteger idBitacora;
    @Size(max = 25)
    @Column(name = "tabla")
    private String tabla;
    @Column(name = "fechabitacora")
    @Temporal(TemporalType.DATE)
    private Date fechabitacora;
    @Size(max = 300)
    @Column(name = "accion")
    private String accion;

    public Bitacora() {
    }

    public Bitacora(BigInteger idBitacora) {
        this.idBitacora = idBitacora;
    }

    public BigInteger getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(BigInteger idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public Date getFechabitacora() {
        return fechabitacora;
    }

    public void setFechabitacora(Date fechabitacora) {
        this.fechabitacora = fechabitacora;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBitacora != null ? idBitacora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bitacora)) {
            return false;
        }
        Bitacora other = (Bitacora) object;
        if ((this.idBitacora == null && other.idBitacora != null) || (this.idBitacora != null && !this.idBitacora.equals(other.idBitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Bitacora[ idBitacora=" + idBitacora + " ]";
    }
    
}
