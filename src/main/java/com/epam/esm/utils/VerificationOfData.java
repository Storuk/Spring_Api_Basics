package com.epam.esm.utils;

import com.epam.esm.giftcertficate.GiftCertificate;
import com.epam.esm.tag.Tag;
import org.apache.commons.lang3.StringUtils;
import com.epam.esm.exceptionhandler.exception.ServerException;
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
        return obj != null && !StringUtils.isBlank(obj) && !StringUtils.isEmpty(obj) && !StringUtils.isNumeric(obj);
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

    public static Map<String, String> checkCertificateAndGetListOfFieldsForUpdate(GiftCertificate giftCertificate) {
        Map<String, String> map = new HashMap<>();
        String dataValid = isCertificateValidForUpdate(giftCertificate);
        if(dataValid.isEmpty()) {
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
        throw new ServerException(dataValid);
    }

    public static String isCertificateValidForUpdate(GiftCertificate giftCertificate) {
        String dataValid = "";
        if (giftCertificate != null) {
            if (giftCertificate.getName() != null && StringUtils.isNumeric(giftCertificate.getName())){
                dataValid += "Invalid input name: " + giftCertificate.getName();
            }
            if (giftCertificate.getTags() != null && !isListOfTagsCorrect(giftCertificate.getTags())){
                dataValid += "Invalid input tags: " + giftCertificate.getTags().toString();
            }
            return dataValid;
        }
        return "Can not make update. Your GiftCertificate is empty.";
    }
}
