package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by anaximines on 21/08/16.
 */
public class Contacts extends ForwardingSet<ContactData> {
  private Set<ContactData> delegate;

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

    Iterator<ContactData> iterator = contacts.delegate.iterator();
    while (iterator.hasNext()) {
      if (contact.getId() == iterator.next().getId()) {
        iterator.remove();
      }
    }

    return contacts;
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
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  public ContactData getNewContactFromDb() {
    Contacts contacts = new Contacts(this);
    ContactData contact = new ContactData();
    for (ContactData c : contacts) {
      if (c.getId() == contacts.stream().mapToInt((g) -> g.getId()).max().getAsInt()) {
        contact = c;
        break;
      }
    }
    return contact;
  }

  public ContactData getContactById(Integer contactId) {
    Contacts contacts = new Contacts(this);
    ContactData contact = new ContactData();
    for (ContactData c : contacts) {
      if (c.getId() == contactId) {
        contact = c;
        break;
      }
    }
    return contact;
  }

  @Override
  protected Set<ContactData> delegate() {
    return delegate;
  }
}
