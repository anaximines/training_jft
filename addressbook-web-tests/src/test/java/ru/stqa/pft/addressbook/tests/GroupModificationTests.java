package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by anaximines on 26/07/16.
 */
public class GroupModificationTests extends TestBase {

    public void checkTestDataHasNull(GroupData testData, GroupData modifiedGroup) {
        if (testData.getName() == null) {
            testData.withName(modifiedGroup.getName());
        }
    }

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1").withFooter("test3"));
        }
    }

    @Test
    public void testGroupModification() {

        Groups before = app.db().groups();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().withId(modifiedGroup.getId()).withHeader("test2").withFooter("test3");
        app.goTo().groupPage();
        checkTestDataHasNull(group, modifiedGroup);
        app.group().modify(group);

        assertThat(app.group().count(), equalTo(before.size()));

        Groups after = app.db().groups();
        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    }
}