package com.epam.esm.giftcertficate;

import com.epam.esm.exceptions.ItemNotFoundException;
import com.epam.esm.exceptions.ServerException;
import com.epam.esm.taggiftcertificate.TagGiftCertificate;
import com.epam.esm.taggiftcertificate.TagGiftCertificateService;
import com.epam.esm.tag.Tag;
import com.epam.esm.tag.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
        throw new ItemNotFoundException("No gift certificate with id= " + id);
    }

    public boolean deleteGiftCertificate(long id) {
        if (giftCertificateRepo.deleteGiftCertificate(id)) {
            return true;
        }
        throw new ItemNotFoundException("No GiftCertificate with id= " + id);
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
                    tagGiftCertificateService.createGiftCertificateTag(new TagGiftCertificate().setGift_certificate_id(gift_certificate_id).setTag_id(tagService.getTagId(tag)));
                }
            }
            return true;
        }else {
            throw new ServerException("Certificate already exists");
        }
    }

    @Transactional
    public List<GiftCertificate> updateGiftCertificate(long id, GiftCertificate giftCertificate) {
        if (giftCertificateRepo.updateGiftCertificate(id, generateMapForUpdate(giftCertificate))) {
            if (giftCertificate.getTags() != null) {
                tagGiftCertificateService.deleteGiftCertificateTag(id);
                if (!giftCertificate.getTags().isEmpty()) {
                    for(Tag tag: giftCertificate.getTags()) {
                        if (!tagService.tagExists(tag.getName())) {
                            tagService.createTag(tag);
                        }
                        tagGiftCertificateService.createGiftCertificateTag(new TagGiftCertificate().setGift_certificate_id(id).setTag_id(tagService.getTagId(tag)));
                    }
                }
            } return getGiftCertificateById(id);
        } else {
            throw new ItemNotFoundException("There is no gift certificate to update with id= " + id);
        }
    }

    private Map<String, String> generateMapForUpdate(GiftCertificate giftCertificate) {
        Map<String, String> map = new HashMap<>();
        if (giftCertificate.getName() != null) {
            map.put("name", giftCertificate.getName());
        }
        if (giftCertificate.getDescription() != null) {
            map.put("description", giftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() != null && giftCertificate.getPrice() > 0) {
            map.put("price", String.valueOf(giftCertificate.getPrice()));
        }
        if (giftCertificate.getDuration() != null && giftCertificate.getDuration() > 0) {
            map.put("duration", String.valueOf(giftCertificate.getDuration()));
        }
        return map;
    }
}