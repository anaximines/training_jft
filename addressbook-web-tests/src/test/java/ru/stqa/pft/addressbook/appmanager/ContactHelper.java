package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by akuzina on 25.07.2016.
 */
public class ContactHelper extends HelperBase {

    public ContactHelper(FirefoxDriver wd){
        super(wd);
    }

    public void submitContactCreation() {
        wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
    }

    public void fillContactInfo(ContactData contactData) {
        type(By.name("firstname"),contactData.getFirstName());
        type(By.name("middlename"),contactData.getMiddleName());
        type(By.name("lastname"),contactData.getLastName());
        type(By.name("nickname"),contactData.getNickname());
        type(By.name("title"),contactData.getTitle());
        type(By.name("company"),contactData.getCompany());
        type(By.name("address"),contactData.getAddress());
        type(By.name("home"),contactData.getHomeTel());
        type(By.name("mobile"),contactData.getMobileTel());
        type(By.name("work"),contactData.getWorkTel());
        type(By.name("fax"),contactData.getFaxTel());
        type(By.name("email"),contactData.getEmail());
        type(By.name("email2"),contactData.getEmail2());
        type(By.name("email3"),contactData.getEmail3());
        type(By.name("homepage"),contactData.getHomepage());
        type(By.name("address2"),contactData.getAddress2());
        type(By.name("phone2"),contactData.getHomeTel2());
        type(By.name("notes"),contactData.getNotes());
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }
}
