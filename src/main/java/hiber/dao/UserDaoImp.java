package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUserOfCar(String model, String series) {
        Query<Car> query = sessionFactory.getCurrentSession().createQuery("from Car where model = :paramModel and series = :paramSeries");
        query.setParameter("paramModel", model).setParameter("paramSeries", series);
        List<Car> carList = query.list();
        List<User> userList = new ArrayList<>();
        for(Car cars : carList) {
            User user = cars.getUser();
            userList.add(user);
        }
        return userList;
    }
}
