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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mario Sanchez
 */
@Entity
@Table(name = "evento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e"),
    @NamedQuery(name = "Evento.aumentarId", query = "SELECT MAX(e.idEvento) FROM Evento e"),
    @NamedQuery(name = "Evento.findByIdEvento", query = "SELECT e FROM Evento e WHERE e.idEvento = :idEvento"),
    @NamedQuery(name = "Evento.findByFechae", query = "SELECT e FROM Evento e WHERE e.fechae = :fechae"),
    @NamedQuery(name = "Evento.findByLugare", query = "SELECT e FROM Evento e WHERE e.lugare = :lugare"),
    @NamedQuery(name = "Evento.findByImagene", query = "SELECT e FROM Evento e WHERE e.imagene = :imagene"),
    @NamedQuery(name = "Evento.findByNombree", query = "SELECT e FROM Evento e WHERE e.nombree = :nombree"),
    @NamedQuery(name = "Evento.findByImaBanner", query = "SELECT e FROM Evento e WHERE e.imgbanner = :imgbanner"),
    @NamedQuery(name = "Evento.findByHorae", query = "SELECT e FROM Evento e WHERE e.horae = :horae")})
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_evento")
    private Integer idEvento;
    @Column(name = "fechae")
    @Temporal(TemporalType.DATE)
    private Date fechae;
    @Size(max = 150)
    @Column(name = "lugare")
    private String lugare;
    @Size(max = 250)
    @Column(name = "imagene")
    private String imagene;
    @Size(max = 150)
    @Column(name = "nombree")
    private String nombree;
    @Column(name = "horae")
    @Temporal(TemporalType.TIME)
    private Date horae;
    @Size(max = 50)
    @Column(name = "imgbanner")
    private String imgbanner;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public Evento() {
    }

    public Evento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Date getFechae() {
        return fechae;
    }

    public void setFechae(Date fechae) {
        this.fechae = fechae;
    }

    public String getLugare() {
        return lugare;
    }

    public void setLugare(String lugare) {
        this.lugare = lugare;
    }

    public String getImagene() {
        return imagene;
    }

    public void setImagene(String imagene) {
        this.imagene = imagene;
    }

    public String getNombree() {
        return nombree;
    }

    public void setNombree(String nombree) {
        this.nombree = nombree;
    }

    public Date getHorae() {
        return horae;
    }

    public void setHorae(Date horae) {
        this.horae = horae;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }
    
     public String getImgbanner() {
        return imgbanner;
    }

    public void setImgbanner(String imgbanner) {
        this.imgbanner = imgbanner;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }
 
    @Override
    public String toString() {
        return "entity.Evento[ idEvento=" + idEvento + " ]";
    }
    
}
