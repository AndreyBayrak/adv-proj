package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


public class AdvDAOImpl implements AdvDAO
{
    private final String queryListString =    "SELECT a FROM Advertisement a WHERE a.deleted=false";
    private final String queryPatternString = "SELECT a FROM Advertisement a WHERE a.shortDesc LIKE :pattern";
    private final String queryTrashString =   "SELECT a FROM Advertisement a WHERE a.deleted=true";

    @Autowired
    private EntityManager entityManager;


    @Override
    public List<Advertisement> list()
    {
        Query query = entityManager.createQuery(queryListString, Advertisement.class);
        return (List<Advertisement>) query.getResultList();
    }


    @Override
    public List<Advertisement> list(String pattern)
    {
        Query query = entityManager.createQuery(queryPatternString, Advertisement.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return (List<Advertisement>) query.getResultList();
    }


    // Список удаленных объявлений
    @Override
    public List<Advertisement> trashList()
    {
        Query query = entityManager.createQuery(queryTrashString, Advertisement.class);
        return (List<Advertisement>) query.getResultList();
    }


    @Override
    public void add(Advertisement adv)
    {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(adv);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }


    @Override
    public void add(List<Advertisement> advs)
    {
        try {
            entityManager.getTransaction().begin();

            for (Advertisement adv : advs)
                entityManager.persist(adv);

            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }


    @Override
    public void delete(long id)
    {
        try {
            entityManager.getTransaction().begin();
            Advertisement adv = entityManager.find(Advertisement.class, id);
            entityManager.remove(adv);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }


    @Override
    public void delete(long[] ids)
    {
        try {
            entityManager.getTransaction().begin();

            for (long id : ids) {
                Advertisement adv = entityManager.find(Advertisement.class, id);
                entityManager.remove(adv);
            }

            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }


    @Override
    public void remove(long id)
    {
        try {
            entityManager.getTransaction().begin();
            Advertisement adv = entityManager.find(Advertisement.class, id);
            adv.setDeleted(true);
            entityManager.merge(adv);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }


    @Override
    public void remove(long[] ids)
    {
        try {
            entityManager.getTransaction().begin();

            for (long id : ids) {
                Advertisement adv = entityManager.find(Advertisement.class, id);
                adv.setDeleted(true);
                entityManager.merge(adv);
            }

            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }


    @Override
    public void restore(long id)
    {
        try {
            entityManager.getTransaction().begin();
            Advertisement adv = entityManager.find(Advertisement.class, id);
            adv.setDeleted(false);
            entityManager.merge(adv);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }


    @Override
    public void restore(long[] ids)
    {
        try {
            entityManager.getTransaction().begin();

            for (long id : ids) {
                Advertisement adv = entityManager.find(Advertisement.class, id);
                adv.setDeleted(false);
                entityManager.merge(adv);
            }

            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }


    @Override
    public byte[] getPhoto(long id)
    {
        try {
            Photo photo = entityManager.find(Photo.class, id);
            return photo.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
