package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by anaximines on 26/07/16.
 */
public class ContactDeletionTests extends TestBase {

  @Test
  public void testSelectedContactDeletion() {
    app.getNavigationHelper().gotoHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("firstName", null, "lastName", null, null, null, "address", null, "mobileTel", null, null, "email", null, null, null, "test1", null, null, null));
    }

    app.timeout(5);
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().submitContactsDeletion();
    app.getNavigationHelper().gotoHomePage();
  }

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().gotoHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("firstName", null, "lastName", null, null, null, "address", null, "mobileTel", null, null, "email", null, null, null, "test1", null, null, null));
    }

    app.timeout(5);
    app.getContactHelper().openEditForm();
    app.getContactHelper().deleteContact();
    app.getNavigationHelper().gotoHomePage();
  }

  @Test
  public void testAllContactsDeletion() {
    app.getNavigationHelper().gotoHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("firstName", null, "lastName", null, null, null, "address", null, "mobileTel", null, null, "email", null, null, null, "test1", null, null, null));
      app.getContactHelper().createContact(new ContactData("firstName", null, "lastName", null, null, null, "address", null, "mobileTel", null, null, "email", null, null, null, "[none]", null, null, null));
    }

    app.timeout(5);
    app.getContactHelper().selectAllContacts();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().submitContactsDeletion();
    app.getNavigationHelper().gotoHomePage();
  }
}