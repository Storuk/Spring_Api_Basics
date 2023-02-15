package com.epam.esm.taggiftcertificate;

import com.epam.esm.exceptions.ItemNotFoundException;
import com.epam.esm.giftcertficate.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Service
@EnableTransactionManagement
public class TagGiftCertificateService {
    private final TagGiftCertificateRepo tagGiftCertificateRepo;

    @Autowired
    public TagGiftCertificateService(TagGiftCertificateRepo tagGiftCertificateRepo) {
        this.tagGiftCertificateRepo = tagGiftCertificateRepo;
    }

    public List<GiftCertificate> getGiftCertificateWithTagsByTagName(String tagName){
        List<GiftCertificate> listOfTagsAndGiftCertificates = tagGiftCertificateRepo.getGiftCertificateWithTagsByTagName(tagName);
        if (!listOfTagsAndGiftCertificates.isEmpty()) {
            return listOfTagsAndGiftCertificates;
        }
        throw new ItemNotFoundException("There are no certificates with tag name = " + tagName);
    }

    public List<GiftCertificate> getGiftCertificateWithTagsByPartOfDescription(String partOfDescription){
        List<GiftCertificate> listOfTagsAndGiftCertificates = tagGiftCertificateRepo.getGiftCertificateWithTagsByPartOfDescription(partOfDescription);
        if (!listOfTagsAndGiftCertificates.isEmpty()) {
            return listOfTagsAndGiftCertificates;
        }
        throw new ItemNotFoundException("There are no certificates with description = " + partOfDescription);
    }

    public List<GiftCertificate> sortByNameAscDesc(String nameSortType){
        List<GiftCertificate> listOfTagsAndGiftCertificates = tagGiftCertificateRepo.sortByNameAscDesc(nameSortType);
        if (!listOfTagsAndGiftCertificates.isEmpty()) {
            return listOfTagsAndGiftCertificates;
        }
        throw new ItemNotFoundException("No certificates");
    }

    public List<GiftCertificate> sortByNameByDateAscDesc(String nameSortType, String dateSortType){
        List<GiftCertificate> listOfTagsAndGiftCertificates = tagGiftCertificateRepo.sortByNameByDateAscDesc(nameSortType, dateSortType);
        if (!listOfTagsAndGiftCertificates.isEmpty()) {
            return listOfTagsAndGiftCertificates;
        }
        throw new ItemNotFoundException("No certificates");
    }

    public boolean createGiftCertificateTag(TagGiftCertificate tagGiftCertificate) {
        return tagGiftCertificateRepo.createGiftCertificateTag(tagGiftCertificate.getGift_certificate_id(), tagGiftCertificate.getTag_id());
    }

    public boolean deleteGiftCertificateTag(long certificateId) {
        return tagGiftCertificateRepo.deleteGiftCertificateTag(certificateId);
    }
}
