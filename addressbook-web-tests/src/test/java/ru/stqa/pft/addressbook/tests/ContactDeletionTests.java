package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

/**
 * Created by anaximines on 26/07/16.
 */
public class ContactDeletionTests extends TestBase {

  @Test
  public void testSelectedContactDeletion() {
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().submitContactsDeletion();
    app.getNavigationHelper().gotoHomePage();
  }

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().openEditForm();
    app.getContactHelper().deleteContact();
    app.getNavigationHelper().gotoHomePage();
  }

  @Test
  public void testAllContactsDeletion(){
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().selectAllContacts();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().submitContactsDeletion();
    app.getNavigationHelper().gotoHomePage();
  }
}