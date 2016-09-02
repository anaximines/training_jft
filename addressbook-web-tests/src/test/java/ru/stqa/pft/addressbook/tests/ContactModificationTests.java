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
public class ContactModificationTests extends TestBase {

    public void checkTestDataHasNull(ContactData testData, ContactData modifiedContact) {
        if (testData.getFirstName() == null) {
            testData.withFirstName(modifiedContact.getFirstName());
        }
        if (testData.getLastName() == null) {
            testData.withLastName(modifiedContact.getLastName());
        }
    }

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData().
                    withFirstName("firstName").
                    withLastName("lastName").
                    withAddress("address").
                    withMobilePhone("mobileTel").
                    withGroup("test1"));
        }
        app.timeout(5);
    }

    @Test
    public void testContactModificationFromContactsList() {
        Contacts before = app.db().contacts();

        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().
                withId(modifiedContact.getId()).
                withFirstName("firstName1").
                withAddress("address1").
                withMobilePhone("mobileTel1");
        checkTestDataHasNull(contact, modifiedContact);

        app.contact().openEditForm(modifiedContact);
        app.contact().modify(contact);

        assertThat(app.contact().count(), equalTo(before.size()));

        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }

    @Test
    public void testContactModificationFromContactCard() {
        Contacts before = app.contact().all();

        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().
                withId(modifiedContact.getId()).
                withLastName("lastName1").
                withAddress("address");
        checkTestDataHasNull(contact, modifiedContact);

        app.contact().openCard(modifiedContact);
        app.contact().initModification();
        app.contact().modify(contact);

        assertThat(app.contact().count(), equalTo(before.size()));

        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
