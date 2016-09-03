package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by anaximines on 21/08/16.
 */
public class Contacts extends ForwardingSet <ContactData> {
  private Set <ContactData> delegate;

  public Contacts(Contacts contacts) {
    this.delegate = new HashSet<ContactData>(contacts.delegate);
  }

  public Contacts() {
    this.delegate = new HashSet<ContactData>();
  }

  public Contacts(Collection<ContactData> contacts) {
    this.delegate = new HashSet<ContactData>(contacts);
  }

  public Contacts withAdded(ContactData contact) {
    Contacts contacts = new Contacts(this);
    setNullStringsAsEmpty(contact);
    contacts.add(contact);
    return contacts;
  }

  public Contacts without(ContactData contact) {
    Contacts contacts = new Contacts(this);
    contacts.remove(contact);
    return contacts;
  }

  @Override
  protected Set<ContactData> delegate() {
    return delegate;
  }

  public static void setNullStringsAsEmpty(ContactData contact) {

    for (Field f : contact.getClass().getDeclaredFields()) {
      f.setAccessible(true);
      try {
        if (f.getType().equals(String.class) &&
                !f.getName().equals("group") &&
                !f.getName().equals("allPhones") &&
                !f.getName().equals("allEmails") &&
                !f.getName().equals("allDetails")) {
          String value = (String) f.get(contact);
          if (value == null) {
            f.set(contact, "");
          }
        }
      } catch ( Exception e ) {
        throw new RuntimeException(e);
      }
    }
  }
}
