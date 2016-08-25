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
public class ContactFormTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();

    if (app.contact().all().size() == 0) {
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
  public void testContactForm() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();

    String cardInfo = formatAllDetails(app.contact().infoFromCard(contact).getAllDetails());
    String infoFromEditForm = mergeAllDetails(app.contact().infoFromEditForm(contact));

    assertThat(cardInfo, equalTo(infoFromEditForm));
  }

  private String mergeName(ContactData contact) {
    return Arrays.asList(contact.getFirstName(), contact.getLastName()).
            stream().filter(s -> !s.equals("")).
            collect(Collectors.joining(" "));
  }

  private String mergePhonesAsOnCard(ContactData contact) {
    String home = !contact.getHomePhone().equals("") ? "H: " + contact.getHomePhone() : "";
    String mobile = !contact.getMobilePhone().equals("") ? "M: " + contact.getMobilePhone() : "";
    String work = !contact.getWorkPhone().equals("") ? "W: " + contact.getWorkPhone() : "";
    return Arrays.asList(home, mobile, work).
            stream().filter((s) -> !s.equals("")).
            collect(Collectors.joining("\n"));
  }

  private String mergeEmailsAsOnCard(ContactData contact) {
    String email = contact.getEmail().equals("") ? "" : (contact.getEmail() + getDomain(contact.getEmail()));
    String email2 = contact.getEmail2().equals("") ? "" : (contact.getEmail2() + getDomain(contact.getEmail2()));
    String email3 = contact.getEmail3().equals("") ? "" : (contact.getEmail3() + getDomain(contact.getEmail3()));
    return Arrays.asList(email, email2, email3).
            stream().filter(s -> !s.equals("")).
            collect(Collectors.joining("\n"));
  }

  private String getDomain(String email) {
    final int i = email.indexOf('@');
    return String.format(" (www.%s)",
            (i != -1 ? email.substring(i + 1) : email));
  }

  private String mergeAllDetails (ContactData contact) {
    return Arrays.asList(mergeName(contact), contact.getAddress(), mergePhonesAsOnCard(contact), mergeEmailsAsOnCard(contact)).
            stream().filter(s -> !s.equals("") && s != null).
            collect(Collectors.joining("\n"));

  }

  private String formatAllDetails (String allDetails) {
    return allDetails.replaceAll("\n\n", "\n");
  }
}
