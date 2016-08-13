package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().createContact(new ContactData("firstName", null, "lastName", null, null, null, "address", null, "mobileTel", null, null, "email", null, null, null, "test1", null, null, null));
    app.getNavigationHelper().returnToHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before + 1);
  }

  @Test
  public void testNextContactCreation() {
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().createContact(new ContactData("firstName", null, "lastName", null, null, null, null, "homeTel", "mobileTel", null, null, "email", null, "email3", null, "test1", null, "secondaryHome", null));
    app.getContactHelper().initNextContactCreation();
    app.getContactHelper().fillContactInfo(new ContactData("firstName", null, "lastName", null, null, null, "address", "homeTel", "mobileTel", "workTel", null, "email", "email2", "email3", null, "[none]", null, "secondaryHome", null), true);
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().returnToHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before + 2);
  }
}