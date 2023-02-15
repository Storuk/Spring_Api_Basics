package com.epam.esm.tag;

import com.epam.esm.exceptions.ServerException;
import com.epam.esm.utils.VerificationOfData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.epam.esm.utils.VerificationOfData.checkIdZeroValue;

/**
 * Class TagController which contain method related with Tag
 * @author Vlad Storoshchuk
 * */
@RestController
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * A controller post method for creating a new tag
     * @param tag the Tag object that will be created in database
     * @see TagService#createTag(Tag)
     * */
    @PostMapping()
    public ResponseEntity<?> createTag(@RequestBody Tag tag) {
        if (VerificationOfData.isValidTag(tag)) {
            tagService.createTag(tag);
            return new ResponseEntity<>(Map.of("status", HttpStatus.CREATED), HttpStatus.CREATED);
        }
        throw new ServerException("Invalid values");
    }

    /**
     * A controller get method for getting all tags
     * @return List of Tags
     * @see TagService#getAllTags()
     * */
    @GetMapping()
    public ResponseEntity<?> getAllTags() {
        return new ResponseEntity<>(Map.of("tags", tagService.getAllTags()), HttpStatus.OK);
    }

    /**
     * A controller get method for getting tag by id
     * @param id - tag id (min value 0)
     * @see TagService#getTagById(long)
     * */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTagById(@PathVariable long id) {
        if (checkIdZeroValue(id)) {
            return new ResponseEntity<>(Map.of("tag", tagService.getTagById(id)), HttpStatus.OK);
        }
        throw new ServerException("incorrect tag id =" + id);
    }

    /**
     * A controller delete method for deleting tag by id
     * @param id - tag id (min value 0)
     * @see TagService#deleteTag(long)
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable long id) {
        if (checkIdZeroValue(id)) {
            tagService.deleteTag(id);
            return ResponseEntity.ok(Map.of("status", HttpStatus.OK));
        }
        throw new ServerException("incorrect tag id =" + id);
    }
}
