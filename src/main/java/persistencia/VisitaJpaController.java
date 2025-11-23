

package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Visita;
import persistencia.exceptions.NonexistentEntityException;


public class VisitaJpaController implements Serializable {

    public VisitaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
       public VisitaJpaController() {
        emf = Persistence.createEntityManagerFactory("tpiPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Visita visita) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(visita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Visita visita) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            visita = em.merge(visita);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = visita.getIdVisita();
                if (findVisita(id) == null) {
                    throw new NonexistentEntityException("The visita with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visita visita;
            try {
                visita = em.getReference(Visita.class, id);
                visita.getIdVisita();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The visita with id " + id + " no longer exists.", enfe);
            }
            em.remove(visita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Visita> findVisitaEntities() {
        return findVisitaEntities(true, -1, -1);
    }

    public List<Visita> findVisitaEntities(int maxResults, int firstResult) {
        return findVisitaEntities(false, maxResults, firstResult);
    }

    private List<Visita> findVisitaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Visita.class));
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

    public Visita findVisita(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Visita.class, id);
        } finally {
            em.close();
        }
    }

    public int getVisitaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Visita> rt = cq.from(Visita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
