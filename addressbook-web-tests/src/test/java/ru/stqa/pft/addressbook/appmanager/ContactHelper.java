package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.tests.ContactModificationTests;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.List;

/**
 * Created by akuzina on 25.07.2016.
 */

public class ContactHelper extends HelperBase {
    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    private Contacts contactsCache = null;

    public void create(ContactData contact) {
        initContactCreation();
        fillInfo(contact, true);
        submitCreation();
        contactsCache = null;
    }

    public void modify(ContactData contact) {
        fillInfo(contact, false);
        submitContactModification();
        contactsCache = null;
        gotoToHomePage();
    }

    public void deleteSomeContacts() {
        deleteSelectedContacts();
        submitContactsDeletion();
        contactsCache = null;
    }

    public void gotoToHomePage() {
        click(By.linkText("home page"));
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void initNextCreation() {
        click(By.linkText("add next"));
    }

    public void submitCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void openCard(ContactData contact) {
        int id = contact.getId();
        click(By.cssSelector(String.format("a[href='view.php?id=%s'] > img[title='Details']", id)));
    }

    public void openEditForm(ContactData contact) {
        int id = contact.getId();
        click(By.cssSelector(String.format("a[href='edit.php?id=%s'] > img[title='Edit']", id)));
    }

    public ContactData infoFromCard(ContactData contact) {
        openCard(contact);
        String allDetails = wd.findElement(By.id("content")).getText();
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withAllDetails(allDetails);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        openEditForm(contact);
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getText();
        wd.navigate().back();
        return new ContactData().
                withId(contact.getId()).
                withFirstName(firstname).
                withLastName(lastname).
                withAddress(address).
                withHomePhone(home).
                withMobilePhone(mobile).
                withWorkPhone(work).
                withEmail(email).
                withEmail2(email2).
                withEmail3(email3);
    }

    public void initModification() {
        click(By.xpath("//div[@id='content']/form[1]/input[2]"));
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void fillInfo(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
        try {attach(By.name("photo"), contactData.getPhoto());}
        catch (NullPointerException npe){
            LoggerFactory.getLogger(ContactHelper.class).info("Заполнение информации о контакте без добавления фото");
        }

        if (creation) {
            if (contactData.getGroup() != null) {
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")), "Добавлен элемент new_group на форму изменения контакта");
        }
    }

    public void select(ContactData contact) {
        int id = contact.getId();
        wd.findElement(By.id(""+id+"")).click();
    }

    public void selectAll() {
        click(By.id("MassCB"));
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void submitContactsDeletion() {
        wd.switchTo().alert().accept();
    }

    public void delete() {
        click(By.xpath("//div[@id='content']/form[2]/input[2]"));
        contactsCache = null;
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public Contacts all() {
        if (contactsCache != null) {
            return new Contacts(contactsCache);
        }
        contactsCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.xpath("td"));
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            String lastName = cells.get(1).getText();
            String firstName = cells.get(2).getText();
            String address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            ContactData contact = new ContactData().
                    withId(id).
                    withFirstName(firstName).
                    withLastName(lastName).
                    withAddress(address).
                    withAllEmails(allEmails).
                    withAllPhones(allPhones);
            contactsCache.add(contact);
        }
        return contactsCache;
    }
}