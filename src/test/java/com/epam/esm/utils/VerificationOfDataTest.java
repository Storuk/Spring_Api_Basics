package com.epam.esm.utils;

import com.epam.esm.giftcertficate.GiftCertificate;
import com.epam.esm.tag.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerificationOfDataTest {

    @Test
    void checkIdZeroValue_True() {
        List<Long> testObjs = List.of(165L, 70L, 3L);
        for (long id : testObjs) {
            assertTrue(VerificationOfData.checkIdZeroValue(id));
        }
    }

    @Test
    void checkIdZeroValue_False() {
        List<Long> testObjs = List.of(-20L, -8L, 0L);
        for (long id : testObjs) {
            assertFalse(VerificationOfData.checkIdZeroValue(id));
        }
    }

    @Test
    void isValidTag_True() {
        List<Tag> testObj = List.of(new Tag().setName("2ha2rd1"), new Tag().setName("exp12"), new Tag().setName("yep37"));
        for (Tag tag : testObj) {
            assertTrue(VerificationOfData.isValidTag(tag));
        }
    }

    @Test
    void isValidTag_FalseEmptyNullOrNumeric() {
        List<Tag> testObj = List.of(new Tag().setName(" "), new Tag().setName(null), new Tag().setName("356"));
        for (Tag tag : testObj) {
            assertFalse(VerificationOfData.isValidTag(tag));
        }
    }

    @Test
    void isStringValid_True() {
        List<String> strings = List.of("cheap", "expensive3", "old54");
        for (String string : strings) {
            assertTrue(VerificationOfData.isStringValid(string));
        }
    }

    @Test
    void isStringValid_FalseNullValue() {
        String string = null;
        assertFalse(VerificationOfData.isStringValid(string));
    }

    @Test
    void isStringValid_FalseValue() {
        String string = " ";
        assertFalse(VerificationOfData.isStringValid(string));
    }

    @Test
    void isStringValid_FalseNumericValue() {
        List<String> numerics = List.of("111", "222", "333");
        for (String string : numerics) {
            assertFalse(VerificationOfData.isStringValid(string));
        }
    }

    @Test
    void isSortingTypeCorrect_True() {
        List<String> numerics = List.of("ASC", "DESC", "asc", "desc");
        for (String string : numerics) {
            assertTrue(VerificationOfData.isSortingTypeCorrect(string));
        }
    }

    @Test
    void isSortingTypeCorrect_False() {
        List<String> numerics = List.of("ABC", "RTE", "312", "#//.");
        for (String string : numerics) {
            assertFalse(VerificationOfData.isSortingTypeCorrect(string));
        }
    }

    @Test
    void isNewCertificateValid_True() {
        List<GiftCertificate> trueCertificates = List.of(new GiftCertificate().setName("cheap").setDescription("discount").setPrice(5).setDuration(4),
                new GiftCertificate().setName("cheap").setDescription("des12").setPrice(7).setDuration(8).setTags(List.of(new Tag().setName("abc"), new Tag().setName("1a2b3c"), new Tag().setName("AbEc#"))));
        for(GiftCertificate giftCertificate: trueCertificates) {
            assertTrue(VerificationOfData.isNewCertificateValid(giftCertificate));
        }
    }

    @Test
    void isNewCertificateValid_False() {
        List<GiftCertificate> wrongCertificates = List.of(new GiftCertificate().setName("543").setDescription("discount").setPrice(5).setDuration(4),
                new GiftCertificate().setName("cheap").setDescription("35").setPrice(5).setDuration(4),
                new GiftCertificate().setName("cheap").setDescription("des12").setPrice(-5).setDuration(8),
                new GiftCertificate().setName("cheap").setDescription("des12").setPrice(5).setDuration(-8));
        for(GiftCertificate giftCertificate: wrongCertificates) {
            assertFalse(VerificationOfData.isNewCertificateValid(giftCertificate));
        }
    }

    @Test
    void isListOfTagsCorrect_True() {
        List<Tag> tags = List.of(new Tag().setName("abc"), new Tag().setName("1a2b3c"), new Tag().setName("AbEc#"));
        assertTrue(VerificationOfData.isListOfTagsCorrect(tags));
    }

    @Test
    void isListOfTagsCorrect_False() {
        List<Tag> tags = List.of(new Tag().setName(" "), new Tag().setName(null), new Tag().setName("356"));
        assertFalse(VerificationOfData.isListOfTagsCorrect(tags));
    }

    @Test
    void checkCertificateAndGetListOfFieldsForUpdate() {
    }

    @Test
    void isCertificateValidForUpdate_True() {
        List<GiftCertificate> trueCertificates = List.of(new GiftCertificate().setName("cheap").setTags(null),
                new GiftCertificate().setName(null).setTags(List.of(new Tag().setName("abc"), new Tag().setName("1a2b3c"), new Tag().setName("AbEc#"))));
        for(GiftCertificate giftCertificate: trueCertificates) {
            assertEquals(VerificationOfData.isCertificateValidForUpdate(giftCertificate),"");
        }
    }

    @Test
    void isCertificateValidForUpdate_FalseNullCertificate() {
        GiftCertificate giftCertificate = null;
        assertEquals(VerificationOfData.isCertificateValidForUpdate(giftCertificate),"Can not make update. Your GiftCertificate is empty.");
    }

    @Test
    void isCertificateValidForUpdate_FalseNumericName() {
        GiftCertificate giftCertificate = new GiftCertificate().setName("123");
        assertEquals(VerificationOfData.isCertificateValidForUpdate(giftCertificate),"Invalid input name: 123");
    }

    @Test
    void isCertificateValidForUpdate_FalseNumericNam() {
        GiftCertificate giftCertificate = new GiftCertificate().setTags(List.of(new Tag().setId(1L).setName("356")));
        System.out.println(giftCertificate.getTags().toString());
        assertEquals(VerificationOfData.isCertificateValidForUpdate(giftCertificate),"Invalid input tags: " + giftCertificate.getTags().toString());
    }

}