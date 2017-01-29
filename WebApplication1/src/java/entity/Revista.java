/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "revista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Revista.findByLetterA", query = "SELECT r from Revista r where r.idTitulo.tituloRevista like 'A%' order by r.idRevista"),
    @NamedQuery(name = "Revista.findByLetter", query =  "SELECT r from Revista r where r.idTitulo.tituloRevista like :letra order by r.idRevista"),
    @NamedQuery(name = "Revista.findAll", query = "SELECT r FROM Revista r ORDER BY r.idRevista"),
    @NamedQuery(name = "Revista.clave", query = "SELECT MAX(a.idRevista) FROM Revista a"),
    @NamedQuery(name = "Revista.reporteDonacion", query = "SELECT r FROM Revista r Where r.idDonacion is not null  "),
    @NamedQuery(name = "Revista.findByIdRevista", query = "SELECT r FROM Revista r WHERE r.idRevista = :idRevista"),
    @NamedQuery(name = "Revista.findByTitulor", query = "SELECT r FROM Revista r WHERE r.titulor = :titulor"),
    @NamedQuery(name = "Revista.findByFechapr", query = "SELECT r FROM Revista r WHERE r.fechapr = :fechapr"),
    @NamedQuery(name = "Revista.findByIssn", query = "SELECT r FROM Revista r WHERE r.issn = :issn"),
    @NamedQuery(name = "Revista.findByImagenpr", query = "SELECT r FROM Revista r WHERE r.imagenpr = :imagenpr"),
    @NamedQuery(name = "Revista.findByArchivopr", query = "SELECT r FROM Revista r WHERE r.archivopr = :archivopr"),
    @NamedQuery(name = "Revista.findByDescripcionr", query = "SELECT r FROM Revista r WHERE r.descripcionr = :descripcionr"),
    @NamedQuery(name = "Revista.findByFechaingresor", query = "SELECT r FROM Revista r WHERE r.fechaingresor = :fechaingresor"),
    @NamedQuery(name = "Revista.findByPaisr", query = "SELECT r FROM Revista r WHERE r.paisr = :paisr"),
    @NamedQuery(name = "Revista.findByFechaprf", query = "SELECT r FROM Revista r WHERE r.fechaprf = :fechaprf")})
public class Revista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_revista")
    private Integer idRevista;
    @Size(max = 150)
    @Column(name = "titulor")
    private String titulor;
    @Column(name = "fechapr")
    @Temporal(TemporalType.DATE)
    private Date fechapr;
    @Size(max = 16)
    @Column(name = "issn")
    private String issn;
    @Size(max = 250)
    @Column(name = "imagenpr")
    private String imagenpr;
    @Size(max = 250)
    @Column(name = "archivopr")
    private String archivopr;
    @Size(max = 1200)
    @Column(name = "descripcionr")
    private String descripcionr;
    @Column(name = "fechaingresor")
    @Temporal(TemporalType.DATE)
    private Date fechaingresor;
    @Size(max = 30)
    @Column(name = "paisr")
    private String paisr;
    @Column(name = "fechaprf")
    @Temporal(TemporalType.DATE)
    private Date fechaprf;
    @JoinTable(name = "posee", joinColumns = {
        @JoinColumn(name = "id_revista", referencedColumnName = "id_revista")}, inverseJoinColumns = {
        @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")})
    @ManyToMany
    private List<Categoria> categoriaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRevista")
    private List<Articulo> articuloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRevista")
    private List<Inventario> inventarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRevista")
    private List<Descarga> descargaList;
    @JoinColumn(name = "id_titulo", referencedColumnName = "id_titulo")
    @ManyToOne
    private Titulo idTitulo;
    @JoinColumn(name = "id_donacion", referencedColumnName = "id_donacion")
    @ManyToOne
    private Donaciones idDonacion;

    public Revista() {
    }

    public Revista(Integer idRevista) {
        this.idRevista = idRevista;
    }

    public Integer getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(Integer idRevista) {
        this.idRevista = idRevista;
    }

    public String getTitulor() {
        return titulor;
    }

    public void setTitulor(String titulor) {
        this.titulor = titulor;
    }

    public Date getFechapr() {
        return fechapr;
    }

    public void setFechapr(Date fechapr) {
        this.fechapr = fechapr;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getImagenpr() {
        return imagenpr;
    }

    public void setImagenpr(String imagenpr) {
        this.imagenpr = imagenpr;
    }

    public String getArchivopr() {
        return archivopr;
    }

    public void setArchivopr(String archivopr) {
        this.archivopr = archivopr;
    }

    public String getDescripcionr() {
        return descripcionr;
    }

    public void setDescripcionr(String descripcionr) {
        this.descripcionr = descripcionr;
    }

    public Date getFechaingresor() {
        return fechaingresor;
    }

    public void setFechaingresor(Date fechaingresor) {
        this.fechaingresor = fechaingresor;
    }

    public String getPaisr() {
        return paisr;
    }

    public void setPaisr(String paisr) {
        this.paisr = paisr;
    }

    public Date getFechaprf() {
        return fechaprf;
    }

    public void setFechaprf(Date fechaprf) {
        this.fechaprf = fechaprf;
    }

    @XmlTransient
    public List<Categoria> getCategoriaList() {
        return categoriaList;
    }

    public void setCategoriaList(List<Categoria> categoriaList) {
        this.categoriaList = categoriaList;
    }

    @XmlTransient
    public List<Articulo> getArticuloList() {
        return articuloList;
    }

    public void setArticuloList(List<Articulo> articuloList) {
        this.articuloList = articuloList;
    }

    @XmlTransient
    public List<Inventario> getInventarioList() {
        return inventarioList;
    }

    public void setInventarioList(List<Inventario> inventarioList) {
        this.inventarioList = inventarioList;
    }

    @XmlTransient
    public List<Descarga> getDescargaList() {
        return descargaList;
    }

    public void setDescargaList(List<Descarga> descargaList) {
        this.descargaList = descargaList;
    }
    
    public Titulo getIdTitulo() {
        return idTitulo;
    }

    public void setIdTitulo(Titulo idTitulo) {
        this.idTitulo = idTitulo;
    }

    public Donaciones getIdDonacion() {
        return idDonacion;
    }

    public void setIdDonacion(Donaciones idDonacion) {
        this.idDonacion = idDonacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRevista != null ? idRevista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Revista)) {
            return false;
        }
        Revista other = (Revista) object;
        if ((this.idRevista == null && other.idRevista != null) || (this.idRevista != null && !this.idRevista.equals(other.idRevista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Revista[ idRevista=" + idRevista + " ]";
    }

}
