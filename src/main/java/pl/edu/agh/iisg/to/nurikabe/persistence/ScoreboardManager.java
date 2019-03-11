package pl.edu.agh.iisg.to.nurikabe.persistence;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.edu.agh.iisg.to.nurikabe.model.Score;
import javax.persistence.PersistenceException;
import java.util.Iterator;


public class ScoreboardManager {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static Session session;

    private static void openSession() {
        session = sessionFactory.openSession();
    }

    public static void addScore(Score score) {
        openSession();
        session.beginTransaction();
        session.save(score);
        session.getTransaction().commit();
        session.close();
    }

    public static Integer findShorter(String time) {
        try {
            openSession();
            Query query = session.createQuery("SELECT COUNT(s) FROM Score s WHERE s.time <= :time")
                    .setParameter("time", time);
            Object sth = query.iterate().next();
            session.close();
            return ((Long)sth).intValue();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Score> getTopScores(int number) {
        try {
            openSession();
            Query q = session.createQuery("SELECT s.name, s.time FROM Score s ORDER BY s.time ASC")
                    .setMaxResults(number);
            ObservableList<Score> resultSet = FXCollections.observableArrayList();
            Iterator iterator = q.iterate();
            int index = 1;
            while (iterator.hasNext()) {
                Object[] obj = (Object[]) iterator.next();
                resultSet.add(new Score(index++, String.valueOf(obj[0]), String.valueOf(obj[1])));
            }
            return resultSet;

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
