
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
import modelo.HistoriaClinica;
import persistencia.exceptions.NonexistentEntityException;


public class HistoriaClinicaJpaController implements Serializable {

    public HistoriaClinicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public HistoriaClinicaJpaController() {
        emf = Persistence.createEntityManagerFactory("tpiPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistoriaClinica historiaClinica) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(historiaClinica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistoriaClinica historiaClinica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            historiaClinica = em.merge(historiaClinica);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = historiaClinica.getidHistoria();
                if (findHistoriaClinica(id) == null) {
                    throw new NonexistentEntityException("The historiaClinica with id " + id + " no longer exists.");
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
            HistoriaClinica historiaClinica;
            try {
                historiaClinica = em.getReference(HistoriaClinica.class, id);
                historiaClinica.getidHistoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historiaClinica with id " + id + " no longer exists.", enfe);
            }
            em.remove(historiaClinica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistoriaClinica> findHistoriaClinicaEntities() {
        return findHistoriaClinicaEntities(true, -1, -1);
    }

    public List<HistoriaClinica> findHistoriaClinicaEntities(int maxResults, int firstResult) {
        return findHistoriaClinicaEntities(false, maxResults, firstResult);
    }

    private List<HistoriaClinica> findHistoriaClinicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistoriaClinica.class));
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

    public HistoriaClinica findHistoriaClinica(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistoriaClinica.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoriaClinicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistoriaClinica> rt = cq.from(HistoriaClinica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
