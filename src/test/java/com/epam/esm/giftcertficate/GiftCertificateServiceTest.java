package com.epam.esm.giftcertficate;

import com.epam.esm.exceptionhandler.exception.ItemNotFoundException;
import com.epam.esm.exceptionhandler.exception.ServerException;
import com.epam.esm.taggiftcertificate.TagGiftCertificate;
import com.epam.esm.taggiftcertificate.TagGiftCertificateService;
import com.epam.esm.tag.Tag;
import com.epam.esm.tag.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {
    @Mock
    private GiftCertificateRepo giftCertificateRepoMock;

    @Mock
    private TagService tagService;

    @Mock
    private TagGiftCertificateService tagGiftCertificateService;

    @InjectMocks
    private GiftCertificateService giftCertificateServiceMock;

    @Test
    void createGiftCertificateTest_FalseCertificateAlreadyExists() {
        GiftCertificate giftCertificate = new GiftCertificate();
        when(giftCertificateRepoMock.giftCertificateExists(giftCertificate)).thenReturn(true);
        ServerException serverException = assertThrows(ServerException.class,() -> giftCertificateServiceMock.createGiftCertificate(giftCertificate));
        assertEquals("Certificate already exists",serverException.getMessage());
    }

    @Test
    void createGiftCertificateTest_TrueWhenCertificateIsNotExists() {
        GiftCertificate giftCertificate = new GiftCertificate();
        when(giftCertificateRepoMock.giftCertificateExists(giftCertificate)).thenReturn(false);
        when(giftCertificateRepoMock.createGiftCertificate(giftCertificate)).thenReturn(true);
        assertTrue(giftCertificateServiceMock.createGiftCertificate(giftCertificate));
    }

    @Test
    void createGiftCertificateTest_TrueWithListOfTagsAndTagAlreadyExists() {
        GiftCertificate giftCertificate = new GiftCertificate().setTags(List.of(new Tag()));
        when(giftCertificateRepoMock.giftCertificateExists(giftCertificate)).thenReturn(false);
        when(giftCertificateRepoMock.createGiftCertificate(giftCertificate)).thenReturn(true);
        when(giftCertificateRepoMock.getGiftCertificateId(giftCertificate)).thenReturn(1L);
        when(tagService.tagExists(giftCertificate.getTags().get(0).getName())).thenReturn(true);
        when(tagService.getTagId(new Tag())).thenReturn(1L);
        when(tagGiftCertificateService.createGiftCertificateTag(new TagGiftCertificate().setGift_certificate_id(1L).setTag_id(1L))).thenReturn(true);
        assertTrue(giftCertificateServiceMock.createGiftCertificate(giftCertificate));
    }

    @Test
    void createGiftCertificateTest_TrueWithListOfTagsAndTagIsNotExists() {
        GiftCertificate giftCertificate = new GiftCertificate().setTags(List.of(new Tag()));
        when(giftCertificateRepoMock.giftCertificateExists(giftCertificate)).thenReturn(false);
        when(giftCertificateRepoMock.createGiftCertificate(giftCertificate)).thenReturn(true);
        when(giftCertificateRepoMock.getGiftCertificateId(giftCertificate)).thenReturn(1L);
        when(tagService.tagExists(giftCertificate.getTags().get(0).getName())).thenReturn(false);
        when(tagService.createTag(new Tag())).thenReturn(true);
        when(tagService.getTagId(new Tag())).thenReturn(1L);
        when(tagGiftCertificateService.createGiftCertificateTag(new TagGiftCertificate().setGift_certificate_id(1L).setTag_id(1L))).thenReturn(true);
        assertTrue(giftCertificateServiceMock.createGiftCertificate(giftCertificate));
    }

    @Test
    void getAllGiftCertificatesTest_WhenEmptyList() {
        List<GiftCertificate> allCertificates = List.of();
        when(giftCertificateRepoMock.getAllGiftCertificates()).thenReturn(allCertificates);
        ItemNotFoundException thrown = assertThrows(ItemNotFoundException.class,
                () -> giftCertificateServiceMock.getAllGiftCertificates());
        assertEquals("No Certificates",thrown.getMessage());
    }

    @Test
    void getAllGiftCertificatesTest_WhenNonEmptyList() {
        List<GiftCertificate> allCertificates = List.of(new GiftCertificate());
        when(giftCertificateRepoMock.getAllGiftCertificates()).thenReturn(allCertificates);
        assertEquals(giftCertificateServiceMock.getAllGiftCertificates(),allCertificates);
    }

    @Test
    void getGiftCertificateById_WhenNoSuchCertificate() {
        List<GiftCertificate> allCertificates = List.of();
        when(giftCertificateRepoMock.getGiftCertificateById(1L)).thenReturn(allCertificates);
        ItemNotFoundException thrown = assertThrows(ItemNotFoundException.class,
                () -> giftCertificateServiceMock.getGiftCertificateById(1L));
        assertEquals("No gift certificate with id= " + 1L,thrown.getMessage());
    }

    @Test
    void getGiftCertificateById_WhenSuchCertificateExist() {
        List<GiftCertificate> allCertificates = List.of(new GiftCertificate());
        when(giftCertificateRepoMock.getGiftCertificateById(1L)).thenReturn(allCertificates);
        assertEquals(allCertificates,giftCertificateServiceMock.getGiftCertificateById(1L));
    }

    @Test
    void deleteGiftCertificateTest_WhenNoSuchCertificate() {
        when(giftCertificateRepoMock.deleteGiftCertificate(1L)).thenReturn(false);
        ItemNotFoundException thrown = assertThrows(ItemNotFoundException.class,
                () -> giftCertificateServiceMock.deleteGiftCertificate(1L));
        assertEquals("No GiftCertificate with id= " + 1, thrown.getMessage());
    }

    @Test
    void deleteGiftCertificateTest_WhenGiftCertificateExists() {
        when(giftCertificateRepoMock.deleteGiftCertificate(1L)).thenReturn(true);
        assertTrue(giftCertificateServiceMock.deleteGiftCertificate(1L));
    }

    @Test
    void updateGiftCertificate() {
        List<Tag> tagList = List.of();
        Map<String, String> updatesMap = new HashMap<>();
        when(giftCertificateRepoMock.updateGiftCertificate(1L,updatesMap)).thenReturn(true);
        when(giftCertificateRepoMock.getGiftCertificateById(1L)).thenReturn(List.of(new GiftCertificate()));
        assertEquals(List.of(new GiftCertificate()),giftCertificateServiceMock.updateGiftCertificate(1L, tagList, updatesMap));
    }
}