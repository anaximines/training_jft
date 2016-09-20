package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

import java.util.Properties;

/**
 * Created by anaximines on 20/09/16.
 */
public class SessionHelper extends HelperBase {

  public SessionHelper(ApplicationManager app) {
    super(app);
  }

  public void login(String username, String password) {
    type(By.name("username"), username);
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Войти']"));
  }

  public void loginAsAdmin() {
    String adminLogin = app.getProperty("web.adminLogin");
    String adminPassword = app.getProperty("web.adminPassword");
    type(By.name("username"), adminLogin);
    type(By.name("password"), adminPassword);
    click(By.cssSelector("input[value='Войти']"));
  }
}
