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
        app.getNavigationHelper().gotoHomePage();

        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("firstName", "lastName", "address", "mobileTel", "test1"));
            app.getContactHelper().createContact(new ContactData("firstName", "lastName", "address", "mobileTel", "[none]"));
        }

        app.timeout(5);
    }

    @Test
    public void testSelectedContactDeletion() {

        List<ContactData> before = app.getContactHelper().getContactList();
        int index = 0;

        app.getContactHelper().selectContact(index);
        app.getContactHelper().deleteSomeContacts();

        List<ContactData> after = app.getContactHelper().getContactList();

        before.remove(index);

        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }

    @Test
    public void testContactDeletion() {

        List<ContactData> before = app.getContactHelper().getContactList();
        int index = 0;

        app.getContactHelper().openEditForm();
        app.getContactHelper().deleteContact();
        app.timeout(5);

        List<ContactData> after = app.getContactHelper().getContactList();

        before.remove(index);

        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }

    @Test
    public void testAllContactsDeletion() {

        app.getContactHelper().selectAllContacts();
        app.getContactHelper().deleteSomeContacts();

        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(after.size(), 0);
    }
}