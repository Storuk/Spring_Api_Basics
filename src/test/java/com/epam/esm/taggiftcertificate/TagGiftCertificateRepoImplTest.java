package com.epam.esm.taggiftcertificate;

import com.epam.esm.giftcertficate.GiftCertificate;
import com.epam.esm.tag.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagGiftCertificateRepoImplTest {
    private EmbeddedDatabase testDatabase;
    private TagGiftCertificateRepo tagGiftCertificateRepo;

    private final List<GiftCertificate> giftCertificatesList = List.of(
            new GiftCertificate().setId(1L).setName("mac")
                    .setDescription("ice").setPrice(3).setDuration(3)
                    .setCreateDate("2022-01-07T22:00Z").setLastUpdateDate("2022-01-07T22:00Z")
                    .setTags(List.of(new Tag().setId(1L).setName("cheap"))),
            new GiftCertificate().setId(2L).setName("kfc")
                    .setDescription("nuggets").setPrice(4).setDuration(7)
                    .setCreateDate("2022-01-01T22:00Z")
                    .setLastUpdateDate("2022-01-01T22:00Z")
                    .setTags(List.of(new Tag().setId(2L).setName("expensive"))));

    private final List<GiftCertificate> giftCertificatesListAsc = List.of(
            new GiftCertificate().setId(2L).setName("kfc")
                    .setDescription("nuggets").setPrice(4).setDuration(7)
                    .setCreateDate("2022-01-01T22:00Z")
                    .setLastUpdateDate("2022-01-01T22:00Z")
                    .setTags(List.of(new Tag().setId(2L).setName("expensive"))),
            new GiftCertificate().setId(1L).setName("mac")
                    .setDescription("ice").setPrice(3).setDuration(3)
                    .setCreateDate("2022-01-07T22:00Z").setLastUpdateDate("2022-01-07T22:00Z")
                    .setTags(List.of(new Tag().setId(1L).setName("cheap"))));

    @BeforeEach
    void setup(){
        this.testDatabase = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("test_db/test-db.sql")
                .addScript("test_db/tag_gift_certificate_repo_ins/tags-gift-certificate-insert.sql")
                .build();
        this.tagGiftCertificateRepo = new TagGiftCertificateRepoImpl(new NamedParameterJdbcTemplate(testDatabase).getJdbcTemplate());
    }

    @AfterEach
    void shutdown(){
        testDatabase.shutdown();
    }

    @Test
    void getGiftCertificateWithTagsByTagNameTest_WhenGiftCertificateExists() {
        String tagName = "cheap";
        assertEquals(List.of(giftCertificatesList.get(0)),tagGiftCertificateRepo.getGiftCertificateWithTagsByTagName(tagName));
    }

    @Test
    void getGiftCertificateWithTagsByTagNameTest_WhenGiftCertificateNonExists() {
        String tagName = "abc";
        assertEquals(List.of(),tagGiftCertificateRepo.getGiftCertificateWithTagsByTagName(tagName));
    }

    @Test
    void getGiftCertificateWithTagsByPartOfDescriptionTest_WhenGiftCertificateExists() {
        String tagName = "ice";
        assertEquals(List.of(giftCertificatesList.get(0)),tagGiftCertificateRepo.getGiftCertificateWithTagsByPartOfDescription(tagName));
    }

    @Test
    void getGiftCertificateWithTagsByPartOfDescriptionTest_WhenGiftCertificateNonExists() {
        String tagName = "abc";
        assertEquals(List.of(),tagGiftCertificateRepo.getGiftCertificateWithTagsByPartOfDescription(tagName));
    }

    @Test
    void sortByNameAscTest() {
        String asc = "asc";
        assertEquals(giftCertificatesListAsc,tagGiftCertificateRepo.sortByNameAscDesc(asc));
    }

    @Test
    void sortByNameDescTest() {
        String desc = "desc";
        assertEquals(giftCertificatesList,tagGiftCertificateRepo.sortByNameAscDesc(desc));
    }

    @Test
    void sortByNameByDateAscDescTest() {
        assertEquals(giftCertificatesListAsc, tagGiftCertificateRepo.sortByNameByDateAscDesc("Asc","Desc"));
    }

    @Test
    void sortByNameByDateDescAscTest() {
        assertEquals(giftCertificatesList, tagGiftCertificateRepo.sortByNameByDateAscDesc("Desc","Asc"));
    }

    @Test
    void sortByNameByDateAscAscTest() {
        assertEquals(giftCertificatesListAsc, tagGiftCertificateRepo.sortByNameByDateAscDesc("Asc","Asc"));
    }

    @Test
    void sortByNameByDateDescDescTest() {
        assertEquals(giftCertificatesList, tagGiftCertificateRepo.sortByNameByDateAscDesc("Desc","Desc"));
    }

    @Test
    void createGiftCertificateTagTest() {
        TagGiftCertificate tagGiftCertificate = new TagGiftCertificate().setGift_certificate_id(1L).setTag_id(2L);
        assertTrue(tagGiftCertificateRepo.createGiftCertificateTag(tagGiftCertificate.getGift_certificate_id(),tagGiftCertificate.getTag_id()));
    }

    @Test
    void deleteAllGiftCertificateTagsTest() {
        long certificateId = 1L;
        assertTrue(tagGiftCertificateRepo.deleteGiftCertificateTag(certificateId));
    }
}