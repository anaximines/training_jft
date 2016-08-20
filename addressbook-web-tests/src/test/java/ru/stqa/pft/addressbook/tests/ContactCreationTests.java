package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.Set;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        Set<ContactData> before = app.contact().all();
        ContactData contact = new ContactData().
                withFirstName("firstName").
                withLastName("lastName").
                withAddress("address").
                withMobileTel("mobileTel").
                withGroup("test1");

        app.contact().create(contact);
        app.contact().gotoToHomePage();
        Set<ContactData> after = app.contact().all();

        contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        before.add(contact);
        Assert.assertEquals(after, before);
    }

    @Test
    public void testNextContactCreation() {
        Set<ContactData> before = app.contact().all();
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

        Set<ContactData> after = app.contact().all();
        contact2.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());

        Set<ContactData> afterWithoutLastContact = removeLastContact(contact2, after);
        contact1.withId(afterWithoutLastContact.stream().mapToInt((g) -> g.getId()).max().getAsInt());

        before.add(contact1);
        before.add(contact2);

        Assert.assertEquals(after, before);
    }

    private Set<ContactData> removeLastContact(ContactData lastContact, Set<ContactData> set) {
        Set<ContactData> setCopy = new HashSet<>(set);
        setCopy.remove(lastContact);
        return setCopy;
    }
}