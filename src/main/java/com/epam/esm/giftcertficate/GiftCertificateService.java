package com.epam.esm.giftcertficate;

import com.epam.esm.exceptionhandler.exception.ItemNotFoundException;
import com.epam.esm.exceptionhandler.exception.ServerException;
import com.epam.esm.giftcertificatetag.TagGiftCertificateService;
import com.epam.esm.tag.Tag;
import com.epam.esm.tag.TagRepo;
import com.epam.esm.tag.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@EnableTransactionManagement
public class GiftCertificateService {
    private final GiftCertificateRepo giftCertificateRepo;
    private final TagService tagService;
    private final TagGiftCertificateService tagGiftCertificateService;

    public GiftCertificateService(GiftCertificateRepo giftCertificateRepo, TagService tagService, TagGiftCertificateService tagGiftCertificateService) {
        this.giftCertificateRepo = giftCertificateRepo;
        this.tagService = tagService;
        this.tagGiftCertificateService = tagGiftCertificateService;
    }

    public List<GiftCertificate> getAllGiftCertificates() {
        List<GiftCertificate> allCertificates = giftCertificateRepo.getAllGiftCertificates();
        if (!allCertificates.isEmpty()) {
            return allCertificates;
        }
        throw new ItemNotFoundException("No Certificates");
    }

    public List<GiftCertificate> getGiftCertificateById(long id) {
        List<GiftCertificate> giftCertificate = giftCertificateRepo.getGiftCertificateById(id);
        if (!giftCertificate.isEmpty()) {
            return giftCertificate;
        }
        throw new ItemNotFoundException("There are no gift certificate with id= " + id);
    }

    public void deleteGiftCertificate(long id) {
        if (!giftCertificateRepo.deleteGiftCertificate(id)) {
            throw new ItemNotFoundException("No tag with id= " + id);
        }
    }

    @Transactional
    public boolean createGiftCertificate(GiftCertificate giftCertificate) {
        if(!giftCertificateRepo.giftCertificateExists(giftCertificate)) {
            giftCertificateRepo.createGiftCertificate(giftCertificate);
            long gift_certificate_id = giftCertificateRepo.getGiftCertificateId(giftCertificate);
            if (giftCertificate.getTags() != null && !giftCertificate.getTags().isEmpty()){
                for (Tag tag : giftCertificate.getTags()) {
                    if (!tagService.tagExists(tag.getName())) {
                        tagService.createTag(tag);
                    }
                    tagGiftCertificateService.createGiftCertificateTag(gift_certificate_id, tagService.getTagId(tag));
                }
            }
            return true;
        }else {
            throw new ServerException("Certificate already exists");
        }
    }

    @Transactional
    public List<GiftCertificate> updateGiftCertificate(long id, List<Tag> tags, Map<String, String> updatesMap) {
        if (giftCertificateRepo.updateGiftCertificate(id, updatesMap)) {
            if (tags != null) {
                tagGiftCertificateService.deleteGiftCertificateTag(id);
                if (!tags.isEmpty()) {
                    for(Tag tag: tags) {
                        if (!tagService.tagExists(tag.getName())) {
                            tagService.createTag(tag);
                        }
                        tagGiftCertificateService.createGiftCertificateTag(id, tagService.getTagId(tag));
                    }
                }
            } return getGiftCertificateById(id);
        } else {
            throw new ItemNotFoundException("There is no gift certificate to update with id= " + id);
        }
    }
}