package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().
                withFirstName("firstName").
                withLastName("lastName").
                withAddress("address").
                withMobileTel("mobileTel").
                withGroup("test1");

        app.contact().create(contact);
        app.contact().gotoToHomePage();
        Contacts after = app.contact().all();

        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

    @Test
    public void testNextContactCreation() {
        Contacts before = app.contact().all();
        ContactData contact1 = new ContactData().
                withFirstName("firstNameF").
                withLastName("lastNameF").
                withMobileTel("mobileTelF").
                withGroup("test1");
        ContactData contact2 = new ContactData().
                withFirstName("firstNameS").
                withLastName("lastNameS").
                withAddress("addressS").
                withMobileTel("mobileTelS").
                withGroup("[none]");

        app.contact().create(contact1);
        app.contact().initNextCreation();
        app.contact().fillInfo(contact2, true);
        app.contact().submitCreation();
        app.contact().gotoToHomePage();

        Contacts after = app.contact().all();
        contact2.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        contact1.withId(after.without(contact2).stream().mapToInt((g) -> g.getId()).max().getAsInt());

        assertThat(after, equalTo(before.withAdded(contact1).withAdded(contact2)));
    }

}