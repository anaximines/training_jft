package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

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
        app.goTo().homePage();

        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().
                    withFirstName("firstName").
                    withLastName("lastName").
                    withAddress("address").
                    withMobileTel("mobileTel").
                    withGroup("test1"));
        }

        app.timeout(5);
    }

    @Test
    public void testContactModificationFromContactsList() {
        Set<ContactData> before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();

        ContactData contact = new ContactData().
                withId(modifiedContact.getId()).
                withFirstName("firstName1").
                withAddress("address1").
                withMobileTel("mobileTel1");
        checkTestDataHasNull(contact, modifiedContact);


        app.contact().openEditForm(modifiedContact);
        app.contact().modify(contact);

        Set<ContactData> after = app.contact().all();

        before.remove(modifiedContact);
        before.add(contact);

        Assert.assertEquals(after, before);
    }

    @Test
    public void testContactModificationFromContactCard() {
        Set<ContactData> before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();

        ContactData contact = new ContactData().
                withId(modifiedContact.getId()).
                withLastName("lastName1").
                withAddress("address");
        checkTestDataHasNull(contact, modifiedContact);

        app.contact().openCard(modifiedContact);
        app.contact().initModification();
        app.contact().modify(contact);

        Set<ContactData> after = app.contact().all();

        before.remove(modifiedContact);
        before.add(contact);

        Assert.assertEquals(after, before);
    }
}
