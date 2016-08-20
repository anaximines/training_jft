package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {

        app.goTo().gotoGroupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1").withFooter("test3"));
        }
    }

    @Test
    public void testGroupDeletion() {

        Set<GroupData> before = app.group().all();
        GroupData deletedGroup = before.iterator().next();

        app.group().delete(deletedGroup);

        Set<GroupData> after = app.group().all();
        before.remove(deletedGroup);

        Assert.assertEquals(after, before);
    }
}
