package com.epam.esm.tag;

import com.epam.esm.exceptionhandler.exception.ItemNotFoundException;
import com.epam.esm.exceptionhandler.exception.ServerException;
import com.epam.esm.giftcertficate.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    private TagRepo tagRepoMock;
    @InjectMocks
    private TagService tagServiceMock;

    @Test
    void createTagTest_WhenSuchTagAlreadyExists() {
        when(tagRepoMock.tagExists("abc")).thenReturn(true);
        ServerException thrown = assertThrows(ServerException.class,
                () -> tagServiceMock.createTag(new Tag().setName("abc")));
        assertEquals(thrown.getMessage(), "Tag already exist");
    }

    @Test
    void createTag_WhenSuchTagNonExistsTest() {
        when(tagRepoMock.tagExists("abc")).thenReturn(false);
        when(tagRepoMock.createTag(new Tag().setName("abc"))).thenReturn(true);
        assertTrue(tagServiceMock.createTag(new Tag().setName("abc")));
    }

    @Test
    void getAllTags_ReturnListOfNoneEmptyTest() {
        List<Tag> tagList = List.of(new Tag());
        when(tagRepoMock.getAllTags()).thenReturn(tagList);
        assertEquals(tagList,tagServiceMock.getAllTags());
    }

    @Test
    void getAllTags_ReturnItemNotFoundException_whenListOfTagsIsEmptyTest() {
        List<Tag> tagList = List.of();
        when(tagRepoMock.getAllTags()).thenReturn(tagList);
        ItemNotFoundException itemNotFoundException = assertThrows(ItemNotFoundException.class,() -> tagServiceMock.getAllTags());
        assertEquals("No tags", itemNotFoundException.getMessage());
    }

    @Test
    void getTagById_WhenSuchTag() {
        List<Tag> tag = List.of();
        when(tagRepoMock.getTagById(1L)).thenReturn(tag);
        ItemNotFoundException thrown = assertThrows(ItemNotFoundException.class,
                () -> tagServiceMock.getTagById(1L));
        assertEquals("No tag with id = " + 1L,thrown.getMessage());
    }

    @Test
    void getTagById_WhenTagExist() {
        List<Tag> tag = List.of(new Tag());
        when(tagRepoMock.getTagById(1L)).thenReturn(tag);
        assertEquals(tag,tagServiceMock.getTagById(1L));
    }

    @Test
    void deleteTag_WhenTagNonExist() {
        when(tagRepoMock.deleteTag(1L)).thenReturn(false);
        ItemNotFoundException thrown = assertThrows(ItemNotFoundException.class,
                () -> tagServiceMock.deleteTag(1L));
        assertEquals("No tag with id = " + 1, thrown.getMessage());
    }

    @Test
    void deleteTag_WhenTagExist() {
        when(tagRepoMock.deleteTag(1L)).thenReturn(true);
        assertTrue(tagServiceMock.deleteTag(1L));
    }

    @Test
    void tagExists_True() {
        when(tagRepoMock.tagExists("abc")).thenReturn(true);
        assertTrue(tagServiceMock.tagExists("abc"));
    }

    @Test
    void tagExists_False() {
        when(tagRepoMock.tagExists("abc")).thenReturn(false);
        assertFalse(tagServiceMock.tagExists("abc"));
    }

    @Test
    void getTagId() {
        when(tagRepoMock.getTagId(new Tag())).thenReturn(1L);
        assertEquals(1L, tagServiceMock.getTagId(new Tag()));
    }
}