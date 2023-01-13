package com.epam.esm.giftcertficate;

import com.epam.esm.tag.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateRepoImplTest {
    private EmbeddedDatabase testDatabase;
    private GiftCertificateRepo giftCertificateRepo;

    private final List<GiftCertificate> giftCertificatesList = List.of(
            new GiftCertificate().setId(1L).setName("mac")
            .setDescription("ice").setPrice(3).setDuration(3)
            .setCreateDate("2022-01-07T22:00Z").setLastUpdateDate("2022-01-07T22:00Z")
            .setTags(List.of(new Tag().setId(1L).setName("cheap"),
                    new Tag().setId(2L).setName("expensive"),
                    new Tag().setId(3L).setName("old"))),

            new GiftCertificate().setId(2L).setName("kfc")
                    .setDescription("nuggets").setPrice(4).setDuration(7)
                    .setCreateDate("2022-01-07T22:00Z")
                    .setLastUpdateDate("2022-01-07T22:00Z")
                    .setTags(List.of()));

    @BeforeEach
    void setup(){
        this.testDatabase = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("test_db/test-db.sql")
                .addScript("test_db/gift_certificate_repo_ins/gift-certificate-insert.sql")
                .build();
        this.giftCertificateRepo = new GiftCertificateRepoImpl(new NamedParameterJdbcTemplate(testDatabase).getJdbcTemplate());
    }

    @AfterEach
    void shutdown(){
        testDatabase.shutdown();
    }

    @Test
    void createGiftCertificateTest_True() {
        GiftCertificate giftCertificate = new GiftCertificate().setName("mac").setDescription("ice")
                .setPrice(3).setDuration(3).setCreateDate(Date.from(Instant.now())).setLastUpdateDate(Date.from(Instant.now()));
        assertTrue(giftCertificateRepo.createGiftCertificate(giftCertificate));
    }

    @Test
    void getAllGiftCertificatesTest_True() {
        assertEquals(giftCertificatesList,giftCertificateRepo.getAllGiftCertificates());
    }

    @Test
    void getGiftCertificateByIdTest_True() {
        assertEquals(List.of(giftCertificatesList.get(0)),giftCertificateRepo.getGiftCertificateById(giftCertificatesList.get(0).getId()));
    }

    @Test
    void deleteGiftCertificateTest_WhenGiftCertificateExists() {
        long giftCertificateId = 1L;
        assertTrue(giftCertificateRepo.deleteGiftCertificate(giftCertificateId));
    }

    @Test
    void deleteGiftCertificateTest_WhenGiftCertificateNonExists() {
        long giftCertificateId = 4L;
        assertFalse(giftCertificateRepo.deleteGiftCertificate(giftCertificateId));
    }
    @Test
    void giftCertificateExistsTest_TrueWhenExists() {
        GiftCertificate giftCertificate = new GiftCertificate().setName("mac")
                .setDescription("ice").setPrice(3).setDuration(3);
        assertTrue(giftCertificateRepo.giftCertificateExists(giftCertificate));
    }

    @Test
    void giftCertificateExistsTest_FalseWhenNonExists() {
        GiftCertificate giftCertificate = new GiftCertificate().setName("abc")
                .setDescription("ice").setPrice(3).setDuration(3);
        assertFalse(giftCertificateRepo.giftCertificateExists(giftCertificate));
    }

    @Test
    void getGiftCertificateIdTest() {
        GiftCertificate giftCertificate = new GiftCertificate().setId(1L).setName("mac")
                .setDescription("ice").setPrice(3).setDuration(3);
        assertEquals(giftCertificate.getId(),giftCertificateRepo.getGiftCertificateId(giftCertificate));
    }

    @Test
    void updateGiftCertificateTest() {
        Map<String, String> mapForUpdates = new HashMap<>();
        mapForUpdates.put("name", "abc");
        mapForUpdates.put("description", "desc");
        mapForUpdates.put("price", "5");
        assertTrue(giftCertificateRepo.updateGiftCertificate(2L,mapForUpdates));
    }
}