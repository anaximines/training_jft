package ru.stqa.pft.mantis.model;

/**
 * Created by anaximines on 17/09/16.
 */
public class MailMessage {

  public String to;
  public String text;

  public MailMessage (String to, String text) {
    this.to = to;
    this.text = text;
  }
}
