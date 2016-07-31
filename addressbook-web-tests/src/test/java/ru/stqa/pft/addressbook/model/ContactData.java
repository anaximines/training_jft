package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String nickname;
    private final String title;
    private final String company;
    private final String address;
    private final String homeTel;
    private final String mobileTel;
    private final String workTel;
    private final String faxTel;
    private final String email;
    private final String email2;
    private final String email3;
    private final String homepage;
    private String group;
    private final String address2;
    private final String homeTel2;
    private final String notes;

    public ContactData(String firstName, String middleName, String lastName, String nickname, String title, String company, String address, String homeTel, String mobileTel, String workTel, String faxTel, String email, String email2, String email3, String homepage, String group, String address2, String homeTel2, String notes) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.title = title;
        this.company = company;
        this.address = address;
        this.homeTel = homeTel;
        this.mobileTel = mobileTel;
        this.workTel = workTel;
        this.faxTel = faxTel;
        this.email = email;
        this.email2 = email2;
        this.email3 = email3;
        this.homepage = homepage;
        this.group = group;
        this.address2 = address2;
        this.homeTel2 = homeTel2;
        this.notes = notes;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getHomeTel() {
        return homeTel;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public String getWorkTel() {
        return workTel;
    }

    public String getFaxTel() {
        return faxTel;
    }

    public String getEmail() {
        return email;
    }

    public String getEmail2() {
        return email2;
    }

    public String getEmail3() {
        return email3;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getGroup() {
        return group;
    }

    public String getAddress2() {
        return address2;
    }

    public String getHomeTel2() {
        return homeTel2;
    }

    public String getNotes() {
        return notes;
    }
}
