package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactInfo(new ContactData("firstName", "middleName", "lastName", "nickname", "title", "company", "address", "homeTel", "mobileTel", "workTel", "faxTel", "email", "email2", "email3", "homepage", "secondaryAddress", "secondaryHome", "secondaryNotes"));
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().returnToHomePage();
    }

    @Test
    public void testNextContactCreation() {
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactInfo(new ContactData("firstName", "middleName", "lastName", "nickname", "title", "company", "address", "homeTel", "mobileTel", "workTel", "faxTel", "email", "email2", "email3", "homepage", "secondaryAddress", "secondaryHome", "secondaryNotes"));
        app.getContactHelper().submitContactCreation();
        app.getContactHelper().initNextContactCreation();
        app.getContactHelper().fillContactInfo(new ContactData("firstName", "middleName", "lastName", "nickname", "title", "company", "address", "homeTel", "mobileTel", "workTel", "faxTel", "email", "email2", "email3", "homepage", "secondaryAddress", "secondaryHome", "secondaryNotes"));
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().returnToHomePage();

    }
}