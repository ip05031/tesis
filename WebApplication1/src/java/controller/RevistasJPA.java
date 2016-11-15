package controller;

import entity.Revista;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RevistasJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Revista> listadoRevista = new ArrayList<>();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public RevistasJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Revista> getRevistas() {
        try {
            listadoRevista = em.createNamedQuery("Revista.findAll", Revista.class).getResultList();
            return listadoRevista;
        } catch (Exception e) {
            return listadoRevista;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public List<Revista> getReporteDonacion() {
        try {
            listadoRevista = em.createNamedQuery("Revista.reporteDonacion", Revista.class).getResultList();
            return listadoRevista;
        } catch (Exception e) {
            return listadoRevista;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Revista> getReportePrestamos() {
        try {
            listadoRevista = em.createNamedQuery("Revista.reportePrestamos", Revista.class).getResultList();
            return listadoRevista;
        } catch (Exception e) {
            return listadoRevista;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Integer getClave() {
        Integer claveId = 0;
        try {
            claveId = (Integer) em.createNamedQuery("Revista.clave").getSingleResult();
            if (claveId != null) {
                return claveId;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e);
            return claveId;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void savePantalla(Revista revista) {
        try {

            em.persist(revista);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void mergeTipoUsuario(Revista revista) {
        try {

            em.merge(revista);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean deleteTipoUsuario(Revista revista) {
        try {
            em.remove(em.merge(revista));
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Revista> getRevistasBusqueda(String titulo, String resumen, String tituloArticulo, String autor, String categoria, List<String> palabras) {
        try {
            String dato = "Select distinct r from Titulo t INNER JOIN t.revistaList r INNER JOIN r.categoriaList c INNER JOIN r.articuloList a INNER JOIN a.palabraClaveList p INNER JOIN a.idAutor au Where ";
            int i = 0;
            try {
                if (!titulo.isEmpty()) {
                    i = 1;
                    dato = dato + " t.tituloRevista LIKE '%" + titulo + "%'";
                }
            } catch (Exception e) {
            }
            try {
                if (!resumen.isEmpty()) {
                    if (i != 0) {
                        dato = dato + " AND ";
                    }
                    dato = dato + " a.resumena LIKE '%" + resumen + "%'";
                    i = 1;
                }
            } catch (Exception e) {
            }
            try {
                if (!tituloArticulo.isEmpty()) {
                    if (i != 0) {
                        dato = dato + " AND ";
                    }
                    dato = dato + " a.tituloa LIKE '%" + tituloArticulo + "%'";
                    i = 1;
                }
            } catch (Exception e) {
            }
            try {
                if (!autor.isEmpty()) {
                    if (i != 0) {
                        dato = dato + " AND ";
                    }
                    dato = dato + " au.nombreAutor LIKE '%" + autor + "%'";
                    i = 1;
                }
            } catch (Exception e) {
            }

            try {
                if (!categoria.isEmpty()) {
                    if (i != 0) {
                        dato = dato + " AND ";
                    }

                    dato = dato + " c.nombrec like '%" + categoria + "%'";
                    i = 1;
                }
            } catch (Exception e) {
            }

            try {
                if (!palabras.isEmpty()) {
                    if (i != 0) {
                    dato = dato + " AND ( ";
                      } 
                    else{
                    dato = dato + " ( ";
                    }
                    //busqueda de palabras clave por separacion de espacios
                    for (String nu : palabras) {
                        if (i == 2) {
                            dato = dato + " OR ";
                        } 
                        dato = dato + " p.nombrep ='" + nu + "'";
                        
                        i = 2;//Para que Sirva de bandera de cambio de estado

                    }
                    dato = dato + " ) ";
                    
                }
            } catch (Exception e) {
            }

            listadoRevista = em.createQuery(dato, Revista.class).getResultList();
            return listadoRevista;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return listadoRevista;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
