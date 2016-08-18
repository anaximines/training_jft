package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        List<ContactData> before = app.contact().list();
        ContactData contact = new ContactData("firstName", "lastName", "address", "mobileTel", "test1");
        app.contact().create(contact);
        app.contact().gotoToHomePage();
        List<ContactData> after = app.contact().list();

        before.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }

    @Test
    public void testNextContactCreation() {
        List<ContactData> before = app.contact().list();
        ContactData contact1 = new ContactData("firstNameF", "lastNameF", null, "mobileTelF", "test1");
        ContactData contact2 = new ContactData("firstNameS", "lastNameS", "addressS", "mobileTelS", "[none]");

        app.contact().create(contact1);
        app.contact().initNextCreation();
        app.contact().fillInfo(contact2, true);
        app.contact().submitCreation();
        app.contact().gotoToHomePage();

        List<ContactData> after = app.contact().list();

        contact1.setId(contact2.getId() - 1);
        before.add(contact1);
        before.add(contact2);

        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }
}