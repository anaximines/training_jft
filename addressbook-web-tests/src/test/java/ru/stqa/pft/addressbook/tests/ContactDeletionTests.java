package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by anaximines on 26/07/16.
 */
public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();

        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().
                    withFirstName("firstName").
                    withLastName("lastName").
                    withAddress("address").
                    withMobileTel("mobileTel").
                    withGroup("test1"));
            app.contact().create(new ContactData().
                    withFirstName("firstName").
                    withLastName("lastName").
                    withAddress("address").
                    withMobileTel("mobileTel").
                    withGroup("[none]"));
        }

        app.timeout(5);
    }

    @Test
    public void testSelectedContactDeletion() {

        List<ContactData> before = app.contact().list();
        int index = 0;

        app.contact().select(index);
        app.contact().deleteSomeContacts();

        List<ContactData> after = app.contact().list();

        before.remove(index);

        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }

    @Test
    public void testContactDeletion() {

        List<ContactData> before = app.contact().list();
        int index = 0;

        app.contact().openEditForm();
        app.contact().delete();
        app.timeout(5);

        List<ContactData> after = app.contact().list();

        before.remove(index);

        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }

    @Test
    public void testAllContactsDeletion() {

        app.contact().selectAll();
        app.contact().deleteSomeContacts();

        List<ContactData> after = app.contact().list();

        Assert.assertEquals(after.size(), 0);
    }
}