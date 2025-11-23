
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
import modelo.Sesion;
import persistencia.exceptions.NonexistentEntityException;


public class SesionJpaController implements Serializable {

    public SesionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
     public SesionJpaController() {
        emf = Persistence.createEntityManagerFactory("tpiPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sesion sesion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(sesion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sesion sesion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            sesion = em.merge(sesion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = sesion.getIdSesion();
                if (findSesion(id) == null) {
                    throw new NonexistentEntityException("The sesion with id " + id + " no longer exists.");
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
            Sesion sesion;
            try {
                sesion = em.getReference(Sesion.class, id);
                sesion.getIdSesion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sesion with id " + id + " no longer exists.", enfe);
            }
            em.remove(sesion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sesion> findSesionEntities() {
        return findSesionEntities(true, -1, -1);
    }

    public List<Sesion> findSesionEntities(int maxResults, int firstResult) {
        return findSesionEntities(false, maxResults, firstResult);
    }

    private List<Sesion> findSesionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sesion.class));
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

    public Sesion findSesion(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sesion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSesionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sesion> rt = cq.from(Sesion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
