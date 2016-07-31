package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactInfo(new ContactData("firstName", null, "lastName", null, null, null, "address", null, "mobileTel", null, null, "email", null, null, null, null, null, null));
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().returnToHomePage();
    }

    @Test
    public void testNextContactCreation() {
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactInfo(new ContactData("firstName", null, "lastName", null, null, null, null, "homeTel", "mobileTel", null, null, "email", null, "email3", null, null, "secondaryHome", null));
        app.getContactHelper().submitContactCreation();
        app.getContactHelper().initNextContactCreation();
        app.getContactHelper().fillContactInfo(new ContactData("firstName", null, "lastName", null, null, null, "address", "homeTel", "mobileTel", "workTel", null, "email", "email2", "email3", null, null, "secondaryHome", null));
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().returnToHomePage();

    }
}