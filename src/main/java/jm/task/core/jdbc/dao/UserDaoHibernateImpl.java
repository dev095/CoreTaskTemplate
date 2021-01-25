package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction = null;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String SQL_CREATE = "CREATE TABLE IF NOT EXISTS users (`id` BIGINT NOT NULL AUTO_INCREMENT, `name` VARCHAR(45) NOT NULL, `lastName` VARCHAR(45) NOT NULL, `age` INT(3) NOT NULL, PRIMARY KEY (`id`));";
        try {
            Session session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(SQL_CREATE).executeUpdate();
            transaction.commit();
            session.close();
            System.out.println("Создание таблицы - OK");
        } catch (Exception e) {
            System.err.println("Создание таблицы - ERROR");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        String SQL_DROP = "DROP TABLE IF EXISTS users;";
        Transaction transaction = null;
        try {
            Session session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(SQL_DROP).executeUpdate();
            transaction.commit();
            session.close();
            System.out.println("Удаление таблицы - OK");
        } catch (Exception e) {
            System.err.println("Удаление таблицы - ERROR");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try {
            Session session = Util.getSessionFactory().openSession();

            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
            System.out.println("Сохранение пользователя " + name + " - OK");
        } catch (Exception e) {
            System.err.println("Сохранение пользователя " + name + " - ERROR");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try {
            Session session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            session.close();
            System.out.println("Удаление пользователя с ID: " + id + " - OK");
        } catch (Exception e) {
            System.err.println("Удаление пользователя с ID: " + id + " - ERROR");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;
        try {
            Session session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            users = (List<User>) session.createQuery("FROM User").list();
            transaction.commit();
            session.close();
            System.out.println("Печать всех пользователей - ОК");
        } catch (Exception e) {
            System.err.println("Печать всех пользователей - ERROR");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try {
            Session session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
            session.close();
            System.out.println("Очистка таблицы - ОК");
        } catch (Exception e) {
            System.err.println("Очистка таблицы - ERROR");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}