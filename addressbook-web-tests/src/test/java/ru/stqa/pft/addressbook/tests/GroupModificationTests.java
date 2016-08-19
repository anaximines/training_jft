package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by anaximines on 26/07/16.
 */
public class GroupModificationTests extends TestBase {

    public void checkTestDataHasNull(GroupData group, List<GroupData> before, int index) {
        if (group.getName() == null) {
            group.withName(before.get(index).getName());
        }
    }

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().gotoGroupPage();

        if (app.group().list().size() == 0) {
            app.group().create(new GroupData().withName("test1").withFooter("test3"));
        }
    }

    @Test
    public void testGroupModification() {

        List<GroupData> before = app.group().list();
        int index = 0;
        GroupData group = new GroupData().withId(before.get(index).getId()).withHeader("test2").withFooter("test3");
        checkTestDataHasNull(group, before, index);

        app.group().modify(index, group);

        List<GroupData> after = app.group().list();
        before.remove(index);
        before.add(group);

        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }
}