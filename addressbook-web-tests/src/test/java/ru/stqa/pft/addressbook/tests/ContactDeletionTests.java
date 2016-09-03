package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by anaximines on 26/07/16.
 */
public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();

        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().
                    withFirstName("firstName").
                    withLastName("lastName").
                    withAddress("address").
                    withMobilePhone("mobileTel").
                    withGroup("test1"));
            app.contact().create(new ContactData().
                    withFirstName("firstName").
                    withLastName("lastName").
                    withAddress("address").
                    withMobilePhone("mobileTel").
                    withGroup("[none]"));
        }

        app.timeout(5);
    }

    @Test
    public void testSelectedContactDeletion() {

        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();

        app.contact().select(deletedContact);
        app.contact().deleteSomeContacts();

        assertThat(app.contact().count(), equalTo(before.size() - 1));

        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(deletedContact)));
    }

    @Test
    public void testContactDeletion() {

        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();

        app.contact().openEditForm(deletedContact);
        app.contact().delete();
        app.timeout(5);

        assertThat(app.contact().count(), equalTo(before.size() - 1));

        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(deletedContact)));
    }

    @Test
    public void testAllContactsDeletion() {
        app.contact().selectAll();
        app.contact().deleteSomeContacts();
        assertThat(app.contact().count(), equalTo(0));
    }
}