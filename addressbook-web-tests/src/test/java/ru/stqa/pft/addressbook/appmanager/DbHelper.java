package ru.stqa.pft.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.addressbook.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by akuzina on 02.09.2016.
 */
public class DbHelper {

    private final SessionFactory sessionFactory;

    public DbHelper() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public Groups groups() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> groups = session.createQuery("from GroupData").list();
        session.getTransaction().commit();
        session.close();
        return new Groups(groups);
    }

    public Contacts contacts() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> contacts = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
        session.getTransaction().commit();
        session.close();
        return new Contacts(contacts);
    }

    public Set<Integer> groupsWithContacts() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Set<Integer> notEmptyGroups = new HashSet<Integer>(session.createNativeQuery(
                "select distinct aig.group_id " +
                "from address_in_groups aig " +
                "join addressbook a on " +
                "aig.id = a.id and " +
                "a.deprecated = '0000-00-00'").list());
        session.getTransaction().commit();
        session.close();
        return notEmptyGroups;
    }
}
