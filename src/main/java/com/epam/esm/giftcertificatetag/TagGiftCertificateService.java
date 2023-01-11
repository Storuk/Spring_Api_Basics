package com.epam.esm.giftcertificatetag;

import com.epam.esm.exceptionhandler.exception.ItemNotFoundException;
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
        if (listOfTagsAndGiftCertificates.isEmpty()) {
            throw new ItemNotFoundException("There are no certificates with tag name = " + tagName);
        }
        return listOfTagsAndGiftCertificates;
    }

    public List<GiftCertificate> getGiftCertificateWithTagsByPartOfDescription(String partOfDescription){
        List<GiftCertificate> listOfTagsAndGiftCertificates = tagGiftCertificateRepo.getGiftCertificateWithTagsByPartOfDescription(partOfDescription);
        if (listOfTagsAndGiftCertificates.isEmpty()) {
            throw new ItemNotFoundException("There are no certificates with description = " + partOfDescription);
        }
        return listOfTagsAndGiftCertificates;
    }

    public List<GiftCertificate> sortByNameAscDesc(String ascOrDesc){
        List<GiftCertificate> listOfTagsAndGiftCertificates = tagGiftCertificateRepo.sortByNameAscDesc(ascOrDesc);
        if (listOfTagsAndGiftCertificates.isEmpty()) {
            throw new ItemNotFoundException("No certificates");
        }
        return listOfTagsAndGiftCertificates;
    }

    public List<GiftCertificate> sortByNameByDateAscDesc(String name, String date){
        List<GiftCertificate> listOfTagsAndGiftCertificates = tagGiftCertificateRepo.sortByNameByDateAscDesc(name, date);
        if (listOfTagsAndGiftCertificates.isEmpty()) {
            throw new ItemNotFoundException("No certificates");
        }
        return listOfTagsAndGiftCertificates;
    }

    public boolean createGiftCertificateTag(long certificateId, long tagId) {
        return tagGiftCertificateRepo.createGiftCertificateTag(certificateId, tagId);
    }

    public void deleteGiftCertificateTag(long certificateId) {
        tagGiftCertificateRepo.deleteGiftCertificateTag(certificateId);
    }
}
