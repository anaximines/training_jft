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
public class ContactModificationTests extends TestBase {

    public void checkTestDataHasNull(ContactData contact, int index, List<ContactData> before) {
        if (contact.getFirstName() == null) {
            contact.setFirstName(before.get(index).getFirstName());
        }
        if (contact.getLastName() == null) {
            contact.setLastName(before.get(index).getLastName());
        }
    }

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();

        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData("firstName", "lastName", "address", "mobileTel", "test1"));
        }

        app.timeout(5);
    }

    @Test
    public void testContactModificationFromContactsList() {
        List<ContactData> before = app.contact().list();
        int index = 0;
        ContactData contact = new ContactData(before.get(index).getId(), "firstName1", null, "address1", "mobileTel1", null);
        checkTestDataHasNull(contact, index, before);

        app.contact().openEditForm();
        app.contact().modify(contact);

        List<ContactData> after = app.contact().list();

        before.remove(index);
        before.add(contact);

        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }

    @Test
    public void testContactModificationFromContactCard() {
        List<ContactData> before = app.contact().list();
        int index = 0;
        ContactData contact = new ContactData(before.get(index).getId(), null, "lastName1", "address", null, null);
        checkTestDataHasNull(contact, index, before);

        app.contact().openCard();
        app.contact().initModification();
        app.contact().modify(contact);

        List<ContactData> after = app.contact().list();

        before.remove(index);
        before.add(contact);

        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }
}
