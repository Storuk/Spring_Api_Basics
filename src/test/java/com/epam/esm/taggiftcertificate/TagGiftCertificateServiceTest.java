package com.epam.esm.taggiftcertificate;

import com.epam.esm.exceptions.ItemNotFoundException;
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
class TagGiftCertificateServiceTest {
    @Mock
    private TagGiftCertificateRepo tagGiftCertificateRepoMock;
    @InjectMocks
    private TagGiftCertificateService tagGiftCertificateServiceMock;

    @Test
    void getGiftCertificateWithTagsByTagName_ItemNotFoundExceptionTest() {
        List<GiftCertificate> listOfTagsAndGiftCertificates = List.of();
        when(tagGiftCertificateRepoMock.getGiftCertificateWithTagsByTagName("abc")).thenReturn(listOfTagsAndGiftCertificates);
        ItemNotFoundException itemNotFoundException = assertThrows(ItemNotFoundException.class,
                () -> tagGiftCertificateServiceMock.getGiftCertificateWithTagsByTagName("abc"));
        assertEquals("There are no certificates with tag name = abc",itemNotFoundException.getMessage());
    }

    @Test
    void getGiftCertificateWithTagsByTagName_ReturnNonEmptyListTest() {
        List<GiftCertificate> listOfTagsAndGiftCertificates = List.of(new GiftCertificate());
        when(tagGiftCertificateRepoMock.getGiftCertificateWithTagsByTagName("abc")).thenReturn(listOfTagsAndGiftCertificates);
        assertEquals(listOfTagsAndGiftCertificates,tagGiftCertificateServiceMock.getGiftCertificateWithTagsByTagName("abc"));
    }

    @Test
    void getGiftCertificateWithTagsByPartOfDescription_ItemNotFoundExceptionTest() {
        List<GiftCertificate> listOfTagsAndGiftCertificates = List.of();
        when(tagGiftCertificateRepoMock.getGiftCertificateWithTagsByPartOfDescription("abc")).thenReturn(listOfTagsAndGiftCertificates);
        ItemNotFoundException itemNotFoundException = assertThrows(ItemNotFoundException.class,
                () -> tagGiftCertificateServiceMock.getGiftCertificateWithTagsByPartOfDescription("abc"));
        assertEquals("There are no certificates with description = abc",itemNotFoundException.getMessage());
    }

    @Test
    void getGiftCertificateWithTagsByPartOfDescription_ReturnNonEmptyListTest() {
        List<GiftCertificate> listOfTagsAndGiftCertificates = List.of(new GiftCertificate());
        when(tagGiftCertificateRepoMock.getGiftCertificateWithTagsByPartOfDescription("abc")).thenReturn(listOfTagsAndGiftCertificates);
        assertEquals(listOfTagsAndGiftCertificates,tagGiftCertificateServiceMock.getGiftCertificateWithTagsByPartOfDescription("abc"));
    }

    @Test
    void sortByNameAscDesc_ThrowExceptionTest() {
        List<GiftCertificate> giftCertificateList = List.of();
        when(tagGiftCertificateRepoMock.sortByNameAscDesc("ASC")).thenReturn(giftCertificateList);
        ItemNotFoundException thrown = assertThrows(ItemNotFoundException.class,
                () -> tagGiftCertificateServiceMock.sortByNameAscDesc("ASC"));
        assertEquals("No certificates", thrown.getMessage());
    }

    @Test
    void sortByNameAscDesc_SortedListOfCertificatesTest() {
        List<GiftCertificate> giftCertificateList = List.of(new GiftCertificate());
        when(tagGiftCertificateRepoMock.sortByNameAscDesc("ASC")).thenReturn(giftCertificateList);
        assertEquals(giftCertificateList, tagGiftCertificateServiceMock.sortByNameAscDesc("ASC"));
    }

    @Test
    void sortByNameByDateAscDesc_ThrowExceptionTest() {
        List<GiftCertificate> giftCertificateList = List.of();
        when(tagGiftCertificateRepoMock.sortByNameByDateAscDesc("ASC","DESC")).thenReturn(giftCertificateList);
        ItemNotFoundException thrown = assertThrows(ItemNotFoundException.class,
                () -> tagGiftCertificateServiceMock.sortByNameByDateAscDesc("ASC","DESC"));
        assertEquals("No certificates", thrown.getMessage());
    }

    @Test
    void sortByNameByDateAscDesc_SortedListOfCertificatesTest() {
        List<GiftCertificate> giftCertificateList = List.of(new GiftCertificate());
        when(tagGiftCertificateRepoMock.sortByNameByDateAscDesc("ASC","DESC")).thenReturn(giftCertificateList);
        assertEquals(giftCertificateList, tagGiftCertificateServiceMock.sortByNameByDateAscDesc("ASC","DESC"));
    }

    @Test
    void createGiftCertificateTag() {
        when(tagGiftCertificateRepoMock.createGiftCertificateTag(1L,1L)).thenReturn(true);
        assertTrue(tagGiftCertificateServiceMock.createGiftCertificateTag(new TagGiftCertificate().setGift_certificate_id(1L).setTag_id(1L)));
    }

    @Test
    void deleteGiftCertificateTag() {
        when(tagGiftCertificateRepoMock.deleteGiftCertificateTag(1L)).thenReturn(true);
        assertTrue(tagGiftCertificateServiceMock.deleteGiftCertificateTag(1L));
    }
}