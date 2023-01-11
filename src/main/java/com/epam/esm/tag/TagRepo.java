package com.epam.esm.tag;

import java.util.List;

public interface TagRepo {
    boolean createTag(Tag tag);
    List<Tag> getAllTags();
    List<Tag> getTagById(long id);
    boolean deleteTag(long id);
    boolean tagExists(String name);
    long getTagId(Tag tag);
}
