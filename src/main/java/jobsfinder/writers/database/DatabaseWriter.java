package jobsfinder.writers.database;

import jobsfinder.Job;
import jobsfinder.writers.JobsWriter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseWriter implements JobsWriter {
    private volatile static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        if(sessionFactory==null){
            createSessionFactory();
        }
        return sessionFactory;
    }

    private synchronized static void createSessionFactory() {
        if(sessionFactory!=null){return;}

        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public String toString()  {
        return "DatabaseWriter";
    }

    @Override
    public void write(Job job)  {
        System.err.println("Do you really want to drop the whole table?");
        append(job);
    }

    public void append(List<Job> jobs)  {
        for (Job job: jobs)  {
            append(job);
        }
    }

    @Override
    public void append(Job job)  {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.save(job);
            session.flush();
            session.getTransaction().commit();
            }
        catch (Exception e)  {
            e.printStackTrace();
        }
        finally  {
            session.clear();
            session.close();
        }
    }

    public Job getJobByUrl(String url)  {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Job> cr = cb.createQuery(Job.class);
        Root<Job> root = cr.from(Job.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("url"), url));
        cr.select(root).where(predicates.toArray(new Predicate[]{}));

        Query<Job> query = session.createQuery(cr);
        query.setMaxResults(1);
        List<Job> result = query.getResultList();
        session.flush();
        session.clear();
        session.close();

        return result.size() > 0 ? result.get(0) : null;
    }

    public static List<Job> getAllJobs()  {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Job> cr = cb.createQuery(Job.class);
        Root<Job> root = cr.from(Job.class);

        cr.select(root).orderBy(cb.asc(root.get("id")));

        Query<Job> query = session.createQuery(cr);
        List<Job> result = query.getResultList();
        session.flush();
        session.clear();
        session.close();

        Collections.reverse(result);

        return result;
    }
}
