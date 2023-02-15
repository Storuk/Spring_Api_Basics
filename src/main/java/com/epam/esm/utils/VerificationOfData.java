package com.epam.esm.utils;

import com.epam.esm.giftcertficate.GiftCertificate;
import com.epam.esm.tag.Tag;
import org.apache.commons.lang3.StringUtils;
import com.epam.esm.exceptions.ServerException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerificationOfData {
    public static boolean checkIdZeroValue(long id) {
        return id > 0;
    }

    public static boolean isValidTag(Tag tag) {
        return tag != null && isStringValid(tag.getName());
    }

    public static boolean isStringValid(String obj) {
        return obj != null && !StringUtils.isBlank(obj) && !StringUtils.isNumeric(obj);
    }

    public static boolean isSortingTypeCorrect(String method) {
        return method.equalsIgnoreCase("ASC") || method.equalsIgnoreCase("DESC");
    }

    public static boolean isNewCertificateValid(GiftCertificate giftCertificate) {
        return giftCertificate != null && isStringValid(giftCertificate.getName())
                && isStringValid(giftCertificate.getDescription())
                && giftCertificate.getPrice() != null && giftCertificate.getPrice() > 0
                && giftCertificate.getDuration() != null && giftCertificate.getDuration() > 0;
    }

    public static boolean isListOfTagsCorrect(List<Tag> tags) {
        if(tags != null) {
            for (Tag tag : tags) {
                if (!isValidTag(tag)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isGiftCertificateValidForUpdate(GiftCertificate giftCertificate) {
        if (giftCertificate != null) {
            if (giftCertificate.getName() != null && !isStringValid(giftCertificate.getName())){
                throw new ServerException("Invalid input name: " + giftCertificate.getName());
            }
            if (giftCertificate.getDescription() != null && !isStringValid(giftCertificate.getDescription())) {
                throw new ServerException("Invalid input description: " + giftCertificate.getDescription());
            }
            if (giftCertificate.getPrice() != null && giftCertificate.getPrice() <= 0) {
                throw new ServerException("Price should be > 0. Your value " + giftCertificate.getPrice());
            }
            if (giftCertificate.getDuration() != null && giftCertificate.getDuration() <= 0) {
                throw new ServerException("Duration should be > 0. Your value " + giftCertificate.getDuration());
            }
            if (giftCertificate.getTags() != null && !isListOfTagsCorrect(giftCertificate.getTags())){
                throw new ServerException("Invalid input tags." + giftCertificate.getTags());
            }
            return true;
        }
        return false;
    }
}
