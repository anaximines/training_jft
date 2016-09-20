package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import java.util.List;

/**
 * Created by anaximines on 20/09/16.
 */
public class UserHelper extends HelperBase {

  public UserHelper(ApplicationManager app) {
    super(app);
  }

  public void resetPassword(UserData user) {
    click(By.linkText(user.getLogin().toString()));
    click(By.cssSelector("input[value='Сбросить пароль']"));
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }
}
