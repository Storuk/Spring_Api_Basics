package com.epam.esm.tag;

import com.epam.esm.exceptionhandler.exception.ItemNotFoundException;
import com.epam.esm.exceptionhandler.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepo tagRepo;

    @Autowired
    public TagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    public boolean createTag(Tag tag) {
        if (!tagExists(tag.getName())) {
            return tagRepo.createTag(tag);
        }
        throw new ServerException("Tag already exist");
    }

    public List<Tag> getAllTags() {
        List<Tag> allTags = tagRepo.getAllTags();
        if (!allTags.isEmpty()) {
            return allTags;
        }
        throw new ItemNotFoundException("No tags");
    }

    public List<Tag> getTagById(long id) {
        List<Tag> tag = tagRepo.getTagById(id);
        if (!tag.isEmpty()) {
            return tag;
        }
        throw new ItemNotFoundException("No tag with id= " + id);
    }

    public boolean deleteTag(long id) {
        if (!tagRepo.deleteTag(id)) {
            return true;
        }
        throw new ItemNotFoundException("No tag with id= " + id);
    }

    public boolean tagExists(String name) {
        return tagRepo.tagExists(name);
    }

    public long getTagId(Tag tag) {
        return tagRepo.getTagId(tag);
    }

}
