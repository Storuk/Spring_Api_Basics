package com.epam.esm.tag;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TagRepoImplTest {
    private EmbeddedDatabase testDatabase;
    private TagRepo tagRepo;
    @BeforeEach
    void setup(){
        this.testDatabase = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("test_db/test-db.sql")
                .addScript("test_db/tag_repo_ins/tags-insert.sql")
                .build();
        this.tagRepo = new TagRepoImpl(new NamedParameterJdbcTemplate(testDatabase).getJdbcTemplate());
    }

    @AfterEach
    void shutdown(){
        testDatabase.shutdown();
    }

    @Test
    void createTagTest() {
        Tag tag = new Tag().setName("cheap");
        assertTrue(tagRepo.createTag(tag));
    }

    @Test
    void deleteTagTest_WhenTagExists() {
        Tag tag = new Tag().setId(1L);
        assertTrue(tagRepo.deleteTag(tag.getId()));
    }

    @Test
    void deleteTagTest_WhenTagNonExists() {
        Tag tag = new Tag().setId(4L);
        assertFalse(tagRepo.deleteTag(tag.getId()));
    }

    @Test
    void getAllTagsTest() {
        List<Tag> tagList = List.of(new Tag().setId(1L).setName("cheap"),
                new Tag().setId(2L).setName("expensive"),
                new Tag().setId(3L).setName("old"));
        assertEquals(tagList, tagRepo.getAllTags());
    }

    @Test
    void getTagByIdTest_WhenTagExists() {
        long tagId = 1L;
        assertEquals(List.of(new Tag().setId(1L).setName("cheap")), tagRepo.getTagById(tagId));
    }

    @Test
    void getTagByIdTest_WhenTagNonExists() {
        long tagId = 11L;
        assertEquals(List.of(),tagRepo.getTagById(tagId));
    }

    @Test
    void tagExistsTest_True() {
        Tag tag = new Tag().setName("cheap");
        assertTrue(tagRepo.tagExists(tag.getName()));
    }

    @Test
    void tagExistsTest_False() {
        Tag tag = new Tag().setName("abc");
        assertFalse(tagRepo.tagExists(tag.getName()));
    }

    @Test
    void getTagIdTest() {
        Tag tag = new Tag().setName("cheap");
        assertEquals(1L, tagRepo.getTagId(tag));
    }

}