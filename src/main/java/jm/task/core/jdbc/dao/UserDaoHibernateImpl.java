package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createQuery(" create table if not exists User (" +
                    " id bigint not null auto_increment," +
                    " age tinyint," +
                    " lastName varchar(50)," +
                    " name varchar(50)," +
                    " primary key (id))" +
                    " engine=InnoDB").executeUpdate();
//            session.update(User.class);
            transaction.commit();
            System.out.println("Create table");
        } catch (Exception e) {
            System.out.println("Table not create!!!");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createQuery("drop table if exists User").executeUpdate();
            transaction.commit();
            System.out.println("Table drop");
        } catch (Exception e) {
            System.out.println("Table not dropped!!!");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            transaction.commit();
            System.out.println("User with name: " + user.getName() + " add to table");
        } catch (Exception e) {
            System.out.println("User not created!!!");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("Remove by id: " + id);
        } catch (Exception e) {
            System.out.println("Remove by id not done!!!");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            users = session.createQuery("from User").list();
        } catch (){
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
            System.out.println("Таблиица очищена");
        } catch (Exception e) {
            System.out.println("Таблиица не очищена, ERROR");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
