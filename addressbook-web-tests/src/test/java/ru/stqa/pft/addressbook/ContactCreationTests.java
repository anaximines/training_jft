package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        initContactCreation();
        fillContactInfo(new ContactData("firstName", "middleName", "lastName", "nickname", "title", "company", "address", "homeTel", "mobileTel", "workTel", "faxTel", "email", "email2", "email3", "homepage", "secondaryAddress", "secondaryHome", "secondaryNotes"));
        submitContactCreation();
        returnToHomePage();
    }
}