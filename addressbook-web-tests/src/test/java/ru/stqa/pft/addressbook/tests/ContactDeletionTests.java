package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

/**
 * Created by anaximines on 26/07/16.
 */
public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();

        if (app.contact().all().size() == 0) {
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

        Set<ContactData> before = app.contact().all();
        ContactData deletedContact = before.iterator().next();

        app.contact().select(deletedContact);
        app.contact().deleteSomeContacts();

        Set<ContactData> after = app.contact().all();
        before.remove(deletedContact);

        Assert.assertEquals(after, before);
    }

    @Test
    public void testContactDeletion() {

        Set<ContactData> before = app.contact().all();
        ContactData deletedContact = before.iterator().next();

        app.contact().openEditForm(deletedContact);
        app.contact().delete();
        app.timeout(5);

        Set<ContactData> after = app.contact().all();

        before.remove(deletedContact);
        Assert.assertEquals(after, before);
    }

    @Test
    public void testAllContactsDeletion() {

        app.contact().selectAll();
        app.contact().deleteSomeContacts();

        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), 0);
    }
}