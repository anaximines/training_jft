package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.*;

/**
 * Created by anaximines on 21/08/16.
 */
public class Groups extends ForwardingSet <GroupData> {
  private Set <GroupData> delegate;

  public Groups(Groups groups) {
    this.delegate = new HashSet<GroupData>(groups.delegate());
  }

  public Groups() {
    this.delegate = new HashSet<GroupData>();
  }

  public Groups(Collection<GroupData> groups) {
    this.delegate = new HashSet<GroupData>(groups);
  }

  public Groups withAdded (GroupData group) {
    Groups groups = new Groups(this);
    groups.add(group);
    return groups;
  }

  public Groups without(GroupData group) {
    Groups groups = new Groups(this);
    groups.remove(group);
    return groups;
  }

  public GroupData chooseRandomGroupFromDb() {
    Groups groups = new Groups(this);
    GroupData group = new GroupData();
    int size = groups.size();
    int item = new Random().nextInt(size);
    int i = 0;
    for(GroupData e : groups) {
      if (i == item)
        group = e;
      i = i + 1;
    }
    return group;
  }

  public Boolean hasGroupId(Integer groupId) {
    Groups groups = new Groups(this);
    boolean hasGroupId = false;

    Iterator<GroupData> iterator = groups.delegate.iterator();
    while (iterator.hasNext()) {
      if (iterator.next().getId() == groupId) {
        hasGroupId = true;
        break;
        }
    }
    return hasGroupId;
  }

  public GroupData getGroupById(int groupId) {
    Groups groups = new Groups(this);
    GroupData group = null;
    for (GroupData g : groups) {
      if (g.getId() == groupId) {
        group = g;
        break;
      }
    }
    return group;
  }

  @Override
  protected Set<GroupData> delegate() {
    return delegate;
  }
}
