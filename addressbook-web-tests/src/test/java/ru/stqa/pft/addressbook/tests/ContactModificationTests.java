package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by anaximines on 26/07/16.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModificationFromContactsList(){
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().openEditForm();
    app.getContactHelper().fillContactInfo(new ContactData("firstName", null, "lastName", null, null, null, "address", "homeTel", "mobileTel", "workTel", null, "email", null, null, null, null, null, null));
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
  }

  @Test
  public void testContactModificationFromContactCard(){
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().openContactCard();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactInfo(new ContactData("firstName", null, "lastName", null, null, null, "address", "homeTel", null, null, null, "email", "email2", "email3", null, null, null, null));
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
  }
}
