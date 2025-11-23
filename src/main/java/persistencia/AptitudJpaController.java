
package persistencia;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import modelo.Aptitud;
import persistencia.exceptions.NonexistentEntityException;

public class AptitudJpaController implements Serializable {

    public AptitudJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
            
     public AptitudJpaController() {
        emf = Persistence.createEntityManagerFactory("tpiPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aptitud aptitud) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(aptitud);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Aptitud aptitud) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            aptitud = em.merge(aptitud);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = aptitud.getIdCertificado();
                if (findAptitud(id) == null) {
                    throw new NonexistentEntityException("The aptitud with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aptitud aptitud;
            try {
                aptitud = em.getReference(Aptitud.class, id);
                aptitud.getIdCertificado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aptitud with id " + id + " no longer exists.", enfe);
            }
            em.remove(aptitud);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aptitud> findAptitudEntities() {
        return findAptitudEntities(true, -1, -1);
    }

    public List<Aptitud> findAptitudEntities(int maxResults, int firstResult) {
        return findAptitudEntities(false, maxResults, firstResult);
    }

    private List<Aptitud> findAptitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aptitud.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Aptitud findAptitud(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aptitud.class, id);
        } finally {
            em.close();
        }
    }

    public int getAptitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aptitud> rt = cq.from(Aptitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
