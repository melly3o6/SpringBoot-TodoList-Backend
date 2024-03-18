package ch.bbcag.backend.todolist.utils;

import ch.bbcag.backend.todolist.item.Item;
import ch.bbcag.backend.todolist.person.Person;
import ch.bbcag.backend.todolist.tag.Tag;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestDataUtil {

    public static Person getTestUser() {
        return getTestUsers().get(0);
    }

    public static Person getInvalidTestUser() {
        Person user = new Person();
        user.setId(0);
        return user;
    }

    public static List<Person> getTestUsers() {
        List<Person> users = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Person user = new Person();
            user.setId(i);
            user.setUsername("User" + i);
            user.setPassword("Password" + i);
            users.add(user);
            Item item = new Item();
            item.setId(i);
            item.setName("Item");
            user.setItems(new HashSet<>());
            user.getItems().add(item);
        }

        return users;
    }

    public static Item getTestItem() {
        return getTestItems().get(0);
    }

    public static List<Item> getTestItems() {
        List<Item> items = new ArrayList<>();
        HashSet<Tag> tags = new HashSet<>();
        List<Person> users = getTestUsers();


        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Tag");
        tags.add(tag);
        tag.setLinkedItems(new HashSet<>());

        for (int i = 1; i <= 4; i++) {
            Item item = new Item();
            item.setId(i);
            item.setName("Item" + i);
            item.setDescription("Description" + i);
            item.setCreatedAt(Timestamp.valueOf("2020-01-01 00:00:00"));
            item.setDoneAt(Timestamp.valueOf("2020-01-01 05:00:00"));
            item.setDeletedAt(Timestamp.valueOf("2020-01-01 10:00:00"));
            item.setPerson(users.get(0));
            if (i > 3) {
                item.setPerson(users.get(1));
            }
            tag.getLinkedItems().add(item);
            item.setLinkedTags(tags);
            items.add(item);
        }


        return items;
    }

    public static Tag getTestTag() {
        return getTestTags().get(0);
    }

    public static List<Tag> getTestTags() {
        List<Tag> tags = new ArrayList<>();
        Set<Item> items = new HashSet<>();

        Item item = new Item();
        item.setId(1);
        item.setName("Item");
        item.setPerson(getTestUser());
        item.setLinkedTags(new HashSet<>());
        items.add(item);

        for (int i = 1; i <= 4; i++) {
            Tag tag = new Tag();
            tag.setId(i);
            tag.setName("Tag" + i);
            tag.setLinkedItems(items);
            item.getLinkedTags().add(tag);
            tags.add(tag);
        }

        return tags;
    }


}
