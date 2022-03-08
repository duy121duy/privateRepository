package com.example;

import com.example.entities.IncomeTax;
import com.example.entities.Tax;
import com.example.utils.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.util.List;

public class HibernateExample {
    public static void main(String[] args) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();

            Query query = session.createNativeQuery("SELECT s.emp_no, s.salary*10/100 AS tax FROM salaries AS s " +
                    "WHERE s.from_date LIKE ? OR s.to_date LIKE ? " +
                    "GROUP BY s.emp_no", Tax.class);
            query.setParameter(1, "1999" + "-" + "01" + "-%");
            query.setParameter(2, "1999" + "-" + "01" + "-%");


            List<Tax> taxes = query.getResultList();

            session.beginTransaction();

            query = session.createNativeQuery("SHOW tables like 'income_tax'");
            if (query.getResultList().size() == 0) {
                query = session.createNativeQuery("CALL createTableTax()");
                query.executeUpdate();
            }

            session.getTransaction().commit();

            session.beginTransaction();
//
            for (Tax tax : taxes) {
                IncomeTax incomeTax = new IncomeTax(tax.getEmpNo(), tax.getTax(), Integer.parseInt("1999"), Integer.parseInt("1"));
                session.persist(incomeTax);
            }

//        // Count user from database
//        Long numberOfMessage = session.createQuery("SELECT COUNT(*) FROM Message", Long.class).uniqueResult();
//        System.out.println("Number of message in database: " + numberOfMessage);
//
//        // Get user by id
//        Message savedMessage = session.find(Message.class, messageId);
//        System.out.println("savedUser: " + savedMessage);
//
//        // Update user
//        savedMessage.setMessage("Hello Java new");
//        session.update(savedMessage);
//
//        // Get users
//        List<Message> messages = session.createQuery("FROM Message", Message.class).list();
//        messages.forEach(System.out::println);
//
//        // Delete user
////        session.delete(savedMessage);
//
//        // Commit the current resource transaction, writing any unflushed changes to the database.
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}
