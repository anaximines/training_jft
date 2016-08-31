package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        File photo = new File("src/test/resources/geopic.png");
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().
                withFirstName("firstName").
                withLastName("lastName").
                withAddress("address").
                withMobilePhone("mobileTel").
                withPhoto(photo);

        app.contact().create(contact);
        app.contact().gotoToHomePage();

        assertThat(app.contact().count(), equalTo(before.size() + 1));

        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

    @Test
    public void testCurrentDir() {
        File currentDir = new File("."); //определение текущей директории во время выполнения теста. "." - текущая директория
        System.out.println(currentDir.getAbsolutePath()); //определение абсолютного пути к текущей директории
        // убедимся, что файл действительно существует, перед запуском основных тестов:
        File photo = new File("src/test/resources/geopic.png");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }

    @Test
    public void testNextContactCreation() {
        Contacts before = app.contact().all();
        ContactData contact1 = new ContactData().
                withFirstName("firstNameF").
                withLastName("lastNameF").
                withMobilePhone("mobileTelF").
                withGroup("test1");
        ContactData contact2 = new ContactData().
                withFirstName("firstNameS").
                withLastName("lastNameS").
                withAddress("addressS").
                withMobilePhone("mobileTelS").
                withGroup("[none]");

        app.contact().create(contact1);
        app.contact().initNextCreation();
        app.contact().fillInfo(contact2, true);
        app.contact().submitCreation();
        app.contact().gotoToHomePage();

        assertThat(app.contact().count(), equalTo(before.size() + 2));

        Contacts after = app.contact().all();
        contact2.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        contact1.withId(after.without(contact2).stream().mapToInt((g) -> g.getId()).max().getAsInt());

        assertThat(after, equalTo(before.withAdded(contact1).withAdded(contact2)));
    }

}