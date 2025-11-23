
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
import modelo.Postulacion;
import persistencia.exceptions.NonexistentEntityException;


public class PostulacionJpaController implements Serializable {

    public PostulacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public PostulacionJpaController() {
        emf = Persistence.createEntityManagerFactory("tpiPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Postulacion postulacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(postulacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Postulacion postulacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            postulacion = em.merge(postulacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = postulacion.getIdPostulacion();
                if (findPostulacion(id) == null) {
                    throw new NonexistentEntityException("The postulacion with id " + id + " no longer exists.");
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
            Postulacion postulacion;
            try {
                postulacion = em.getReference(Postulacion.class, id);
                postulacion.getIdPostulacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The postulacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(postulacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Postulacion> findPostulacionEntities() {
        return findPostulacionEntities(true, -1, -1);
    }

    public List<Postulacion> findPostulacionEntities(int maxResults, int firstResult) {
        return findPostulacionEntities(false, maxResults, firstResult);
    }

    private List<Postulacion> findPostulacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Postulacion.class));
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

    public Postulacion findPostulacion(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Postulacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPostulacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Postulacion> rt = cq.from(Postulacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
