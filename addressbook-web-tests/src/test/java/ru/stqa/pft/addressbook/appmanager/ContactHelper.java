package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akuzina on 25.07.2016.
 */

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void createContact(ContactData contact) {
        initContactCreation();
        fillContactInfo(contact, true);
        submitContactCreation();
    }

    public void modifyContact(ContactData contact) {
        fillContactInfo(contact, false);
        submitContactModification();
        returnToHomePage();
    }

    public void deleteSomeContacts() {
        deleteSelectedContacts();
        submitContactsDeletion();
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void initNextContactCreation() {
        click(By.linkText("add next"));
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void openContactCard() {
        click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[7]/a/img"));
    }

    public void openEditForm() {
        click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
    }

    public void initContactModification() {
        click(By.xpath("//div[@id='content']/form[1]/input[2]"));
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void fillContactInfo(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobileTel());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")), "Добавлен элемент new_group на форму изменения контакта");
        }
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectAllContacts() {
        click(By.id("MassCB"));
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void submitContactsDeletion() {
        wd.switchTo().alert().accept();
    }

    public void deleteContact() {
        click(By.xpath("//div[@id='content']/form[2]/input[2]"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            String lastName = element.findElements(By.xpath("td")).get(1).getText();
            String firstName = element.findElements(By.xpath("td")).get(2).getText();

            ContactData contact = new ContactData(id, firstName, lastName, null, null, null);
            contacts.add(contact);
        }
        return contacts;
    }
}