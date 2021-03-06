package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType());
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }


  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().
              withName("group4testing"));
      app.timeout(5);
    }
  }

  @Test(dataProvider = "validContactsFromXml")
  public void testContactCreation(ContactData contact) {
    Groups groups = app.db().groups();
    File photo = new File("src/test/resources/geopic.png");
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    contact.withPhoto(photo).inGroup(groups.iterator().next());
    app.contact().create(contact);
    app.contact().gotoToHomePage();

    assertThat(app.contact().count(), equalTo(before.size() + 1));

    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().
            mapToInt((g) -> g.getId()).max().getAsInt()))));
    verifyContactListInUi();
  }

  @Test(dataProvider = "validContactsFromJson")
  public void testNextContactCreation(ContactData contact1) {
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    ContactData contact2 = new ContactData().
            withFirstName("firstNameS").
            withLastName("lastNameS").
            withAddress("addressS").
            withMobilePhone("mobileTelS");

    app.contact().create(contact1);
    app.contact().initNextCreation();
    app.contact().fillInfo(contact2, true);
    app.contact().submitCreation();
    app.contact().gotoToHomePage();

    assertThat(app.contact().count(), equalTo(before.size() + 2));

    Contacts after = app.db().contacts();

    contact2.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    contact1.withId(after.without(contact2).stream().mapToInt((g) -> g.getId()).max().getAsInt());
    assertThat(after, equalTo(before.withAdded(contact1).withAdded(contact2)));
    verifyContactListInUi();
  }
}