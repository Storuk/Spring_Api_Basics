package com.epam.esm.giftcertficate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateRepo {
    boolean createGiftCertificate(GiftCertificate giftCertificate);
    List<GiftCertificate> getAllGiftCertificates();
    List<GiftCertificate> getGiftCertificateById(long id);
    boolean deleteGiftCertificate(long id);
    boolean giftCertificateExists(GiftCertificate giftCertificate);
    long getGiftCertificateId(GiftCertificate giftCertificate);
    boolean updateGiftCertificate(long id, Map<String, String> updatesMap);
}
