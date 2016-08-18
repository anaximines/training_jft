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
        app.getNavigationHelper().gotoHomePage();

        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("firstName", "lastName", "address", "mobileTel", "test1"));
        }

        app.timeout(5);
    }

    @Test
    public void testContactModificationFromContactsList() {
        List<ContactData> before = app.getContactHelper().getContactList();
        int index = 0;
        ContactData contact = new ContactData(before.get(index).getId(), "firstName1", null, "address1", "mobileTel1", null);
        checkTestDataHasNull(contact, index, before);

        app.getContactHelper().openEditForm();
        app.getContactHelper().modifyContact(contact);

        List<ContactData> after = app.getContactHelper().getContactList();

        before.remove(index);
        before.add(contact);

        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }

    @Test
    public void testContactModificationFromContactCard() {
        List<ContactData> before = app.getContactHelper().getContactList();
        int index = 0;
        ContactData contact = new ContactData(before.get(index).getId(), null, "lastName1", "address", null, null);
        checkTestDataHasNull(contact, index, before);

        app.getContactHelper().openContactCard();
        app.getContactHelper().initContactModification();
        app.getContactHelper().modifyContact(contact);

        List<ContactData> after = app.getContactHelper().getContactList();

        before.remove(index);
        before.add(contact);

        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }
}
