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
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModificationFromContactsList() {
    app.getNavigationHelper().gotoHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("firstName", "lastName", "address", "mobileTel", "test1"));
    }

    app.timeout(5);
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().openEditForm();
    ContactData contact = new ContactData(before.get(0).getId(), "firstName1", null, "address1", "mobileTel1", null);
    app.getContactHelper().fillContactInfo(contact, false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();

    if (contact.getFirstName() == null) {
      contact.setFirstName(before.get(0).getFirstName());
    }
    if (contact.getLastName() == null) {
      contact.setLastName(before.get(0).getLastName());
    }
    before.remove(0);
    before.add(contact);

    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after,before);
  }

  @Test
  public void testContactModificationFromContactCard() {
    app.getNavigationHelper().gotoHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("firstName", "lastName", "address", "mobileTel", "test1"));
    }

    app.timeout(5);
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().openContactCard();
    app.getContactHelper().initContactModification();
    ContactData contact = new ContactData(before.get(0).getId(), null, "lastName1", "address", null, null);
    app.getContactHelper().fillContactInfo(contact, false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();

    if (contact.getFirstName() == null) {
      contact.setFirstName(before.get(0).getFirstName());
    }
    if (contact.getLastName() == null) {
      contact.setLastName(before.get(0).getLastName());
    }
    before.remove(0);
    before.add(contact);

    Comparator <? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after,before);
  }
}
