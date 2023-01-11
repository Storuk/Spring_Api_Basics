package com.epam.esm.giftcertificatetag;

import com.epam.esm.giftcertficate.GiftCertificate;

import java.util.List;

public interface TagGiftCertificateRepo {
    List<GiftCertificate> getGiftCertificateWithTagsByTagName(String tagName);

    List<GiftCertificate> getGiftCertificateWithTagsByPartOfDescription(String partOfDescription);

    List<GiftCertificate> sortByNameAscDesc(String name);

    List<GiftCertificate> sortByNameByDateAscDesc(String name, String date);

    boolean createGiftCertificateTag(long certificateId, long tagId);
    void deleteGiftCertificateTag(long certificateId);
}
