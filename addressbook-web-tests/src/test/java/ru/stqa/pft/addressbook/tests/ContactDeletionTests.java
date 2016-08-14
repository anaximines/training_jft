package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Created by anaximines on 26/07/16.
 */
public class ContactDeletionTests extends TestBase {

  @Test
  public void testSelectedContactDeletion() {

    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("firstName", "lastName", "address", "mobileTel", "test1"));
    }

    app.timeout(5);
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(0);
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().submitContactsDeletion();
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();

    before.remove(0);

    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after,before);
  }

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().gotoHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("firstName", "lastName", "address", "mobileTel", "[none]"));
    }

    app.timeout(5);
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().openEditForm();
    app.getContactHelper().deleteContact();
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();

    before.remove(0);

    Comparator <? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after,before);
  }

  @Test
  public void testAllContactsDeletion() {
    app.getNavigationHelper().gotoHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("firstName", "lastName", "address", "mobileTel", "test1"));
      app.getContactHelper().createContact(new ContactData("firstName", "lastName", "address", "mobileTel", "[none]"));
    }

    app.timeout(5);
    List<ContactData> before = app.getContactHelper().getContactList();
    Assert.assertNotEquals(before.size(), 0);
    app.getContactHelper().selectAllContacts();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().submitContactsDeletion();
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), 0);
  }
}