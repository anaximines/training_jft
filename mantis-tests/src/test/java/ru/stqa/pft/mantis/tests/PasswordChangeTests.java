package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Created by anaximines on 20/09/16.
 */
public class PasswordChangeTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testPasswordChange() throws IOException, MessagingException {

    UserData user = app.db().users().iterator().next();
    String email = user.getEmail();
    String newPassword = "newPassword";

    app.authorization().loginAsAdmin();
    app.goTo().manageUserPage();
    app.user().resetPassword(user);

    List<MailMessage> mailMessages = app.mail().waitForMail(1, 5000);
    String confirmationLink = app.user().findConfirmationLink(mailMessages, email);
    app.timeout(5);
    app.registration().changePassword(confirmationLink, newPassword);
    assertTrue(app.newSession().login(user, newPassword));
  }

  @AfterMethod (alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
