package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by anaximines on 25/08/16.
 */
public class ContactEmailTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();

    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().
              withFirstName("firstName").
              withLastName("lastName").
              withAddress("address").
              withMobilePhone("+78001112233").
              withWorkPhone("123-45-67").
              withEmail("email@mail.ru").
              withEmail2("email@test.com"));
    }

    app.timeout(5);
  }

  @Test
  public void testContactEmails() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3()).
            stream().filter((s) -> !s.equals("")).
            collect(Collectors.joining("\n"));
  }
}
