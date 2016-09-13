package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Random;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by anaximines on 04/09/16.
 */
public class ContactInGroupsTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().
              withName("group4testing"));
      app.timeout(5);
    }

    if (app.db().contacts().size() <= 1) {
      app.goTo().homePage();
      app.contact().create(new ContactData().
              withFirstName("firstName").
              withLastName("lastName").
              withAddress("address").
              withMobilePhone("mobileTel"));
      app.goTo().homePage();
      app.contact().create(new ContactData().
              withFirstName("firstNameA").
              withLastName("lastNameA").
              withAddress("addressA").
              withMobilePhone("mobileTelA"));
      app.timeout(5);
    }

    Set<Integer> notEmptyGroups = app.db().groupsWithContacts();

    if (notEmptyGroups.size() == 0) {
      GroupData group = app.db().groups().iterator().next();
      ContactData contact = new ContactData().
                withFirstName("firstNameS").
                withLastName("lastNameS").
                withMobilePhone("mobileTelS").
                inGroup(group);

      app.goTo().homePage();
      app.contact().create(contact);
      app.contact().gotoToHomePage();
    }
  }

  @Test
  public void testAddingToGroupDuringContactCreation() {
    GroupData group = app.db().groups().iterator().next();
    ContactData contact = new ContactData().
            withFirstName("firstNameS").
            withLastName("lastNameS").
            withMobilePhone("mobileTelS").
            inGroup(group);

    app.goTo().homePage();
    app.contact().create(contact);
    app.contact().gotoToHomePage();

    contact = app.db().contacts().getNewContactFromDb();

    assertThat(contact.getGroups().size(), equalTo(1));
    assertTrue(contact.getGroups().hasGroupId(group.getId()));
  }

  @Test
  public void testAddingToGroupFromContactList() {
    ContactData testedContact = app.db().contacts().iterator().next();
    GroupData group = app.db().groups().iterator().next();

    group = createNewIfItHasTestedContact(group, testedContact);

    app.goTo().homePage();
    app.contact().select(testedContact);
    app.contact().addToGroup(group);

    testedContact = app.db().contacts().getContactById(testedContact.getId());

    assertTrue(testedContact.getGroups().hasGroupId(group.getId()));
  }

  @Test
  public void testAddingAllContactsToGroup() {
    GroupData group = createGroupForTesting();

    app.goTo().homePage();
    app.contact().selectAll();
    app.contact().addToGroup(group);

    group = app.db().groups().getGroupById(group.getId());

    assertThat(group.getContacts().size(), equalTo(app.db().contacts().size()));
  }

  @Test
  public void testAddingContactToSeveralGroups() {
    GroupData group1 = app.db().groups().chooseRandomGroupFromDb();
    GroupData group2 = app.db().groups().chooseRandomGroupFromDb();
    Contacts contacts = app.db().contacts();
    ContactData testedContact = contacts.iterator().next();

    group1 = createNewIfItHasTestedContact(group1, testedContact);
    group2 = createNewIfItHasTestedContact(group2, testedContact);

    app.goTo().homePage();
    app.contact().select(testedContact);
    app.contact().addToGroup(group1);
    app.goTo().homePage();
    app.contact().select(testedContact);
    app.contact().addToGroup(group2);

    testedContact = app.db().contacts().getContactById(testedContact.getId());

    assertTrue(testedContact.getGroups().hasGroupId(group1.getId()));
    assertTrue(testedContact.getGroups().hasGroupId(group2.getId()));
  }

  @Test
  public void testRemovingContactFromGroup() {
    GroupData group = chooseNotEmptyGroup();
    ContactData testedContact = group.getContacts().iterator().next();

    app.goTo().homePage();
    app.contact().openListByGroup(group);
    app.contact().select(testedContact);
    app.contact().removeFromGroup();

    testedContact = app.db().contacts().getContactById(testedContact.getId());

    assertFalse(testedContact.getGroups().hasGroupId(group.getId()));
  }

  private GroupData createNewIfItHasTestedContact(GroupData group, ContactData testedContact) {
    if (testedContact.getGroups().size() == app.db().groups().size() ||
            testedContact.getGroups().hasGroupId(group.getId())) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("testGroupWithoutContacts"));
      Groups groups = app.db().groups();
      group = groups.getGroupById(groups.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    }
    return group;
  }

  private GroupData createGroupForTesting() {
    app.goTo().groupPage();
    app.group().create(new GroupData().withName("GroupWithoutAllContacts"));
    Groups groups = app.db().groups();
    return groups.getGroupById(groups.stream().mapToInt((g) -> g.getId()).max().getAsInt());
  }

  private GroupData chooseNotEmptyGroup() {
    Set<Integer> notEmptyGroups = app.db().groupsWithContacts();

    int index = new Random().nextInt(notEmptyGroups.size());
    int notEmptyGroupId = (Integer) notEmptyGroups.toArray()[index];
    return app.db().groups().getGroupById(notEmptyGroupId);
  }
}