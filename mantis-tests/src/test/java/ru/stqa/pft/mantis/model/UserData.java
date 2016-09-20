package ru.stqa.pft.mantis.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by anaximines on 20/09/16.
 */
@Entity
@Table(name = "mantis_user_table")
public class UserData {
  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;

  @Column (name = "username")
  String login;

  @Column (name = "email")
  String email;


  public UserData setId(int id) {
    this.id = id;
    return this;
  }

  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserData withLogin(String login) {
    this.login = login;
    return this;
  }

  public int getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getLogin() {
    return login;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserData userData = (UserData) o;

    if (id != userData.id) return false;
    if (login != null ? !login.equals(userData.login) : userData.login != null) return false;
    return email != null ? email.equals(userData.email) : userData.email == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (login != null ? login.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "UserData{" +
            "email='" + email + '\'' +
            ", id=" + id +
            ", login='" + login + '\'' +
            '}';
  }
}
