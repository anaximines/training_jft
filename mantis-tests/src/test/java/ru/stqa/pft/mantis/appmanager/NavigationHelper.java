package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

/**
 * Created by anaximines on 20/09/16.
 */
public class NavigationHelper extends HelperBase {

  public NavigationHelper(ApplicationManager app) {
    super(app);
  }

  public void manageUserPage() {
    click(By.linkText("управление"));
    click(By.linkText("Управление пользователями"));
  }
}
