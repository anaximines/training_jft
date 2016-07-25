package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.initContactCreation();
        app.fillContactInfo(new ContactData("firstName", "middleName", "lastName", "nickname", "title", "company", "address", "homeTel", "mobileTel", "workTel", "faxTel", "email", "email2", "email3", "homepage", "secondaryAddress", "secondaryHome", "secondaryNotes"));
        app.submitContactCreation();
        app.returnToHomePage();
    }
}