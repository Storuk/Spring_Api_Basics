package com.epam.esm.taggiftcertificate;

import com.epam.esm.exceptions.ServerException;
import com.epam.esm.utils.VerificationOfData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Class TagGiftCertificateController which contain method related with Tag
 * @author Vlad Storoshchuk
 * */
@RestController
@RequestMapping(value = "/tags-certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagGiftCertificateController {
    private final TagGiftCertificateService tagGiftCertificateService;

    @Autowired
    public TagGiftCertificateController(TagGiftCertificateService tagGiftCertificateService) {
        this.tagGiftCertificateService = tagGiftCertificateService;
    }

    /**
     * A controller get method for getting all GiftCertificates by tag name
     * @param name - tagName for getting all gift certificates
     * Values for Pagination
     * @return List of GiftCertificates
     * @see TagGiftCertificateService#getGiftCertificateWithTagsByTagName(String)
     * */
    @GetMapping("{name}")
    public ResponseEntity<?> getGiftCertificateWithTagsByTagName(@PathVariable String name) {
        if (VerificationOfData.isStringValid(name)) {
            return new ResponseEntity<>(Map.of("CertificatesTags", tagGiftCertificateService.getGiftCertificateWithTagsByTagName(name)), HttpStatus.OK);
        }
        throw new ServerException("Invalid data");
    }

    /**
     * A controller get method for getting all GiftCertificates by part of Description
     * @param description - description values for getting all gift certificates
     * Values for Pagination
     * @return PagedModel of GiftCertificates with links
     * @see TagGiftCertificateService#getGiftCertificateWithTagsByPartOfDescription(String)
     * */
    @GetMapping("description/{description}")
    public ResponseEntity<?> getGiftCertificateWithTagsByPartOfDescription(@PathVariable String description) {
        if (VerificationOfData.isStringValid(description)) {
            return new ResponseEntity<>(Map.of("CertificatesTags", tagGiftCertificateService.getGiftCertificateWithTagsByPartOfDescription(description)), HttpStatus.OK);
        }
        throw new ServerException("Invalid data");
    }

    /**
     * A controller get method for getting all GiftCertificates sorted by name
     * @param name - sorting type for name (ASC OR DESC)
     * Values for Pagination
     * @return sorted List of GiftCertificates
     * @see TagGiftCertificateService#sortByNameAscDesc(String)
     * */
    @GetMapping("sort-name/{name}")
    public ResponseEntity<?> sortByNameAscDesc(@PathVariable String name) {
        if (VerificationOfData.isStringValid(name)) {
            if (VerificationOfData.isSortingTypeCorrect(name)){
                return new ResponseEntity<>(Map.of("CertificatesTags", tagGiftCertificateService.sortByNameAscDesc(name)), HttpStatus.OK);
            }
            throw new ServerException("ASC or DESC value allowed only");
        }
        throw new ServerException("Invalid data");
    }

    /**
     * A controller get method for getting all GiftCertificates sorted by name and by date
     * @param name - sorting type for name (ASC OR DESC)
     * @param date - sorting type for date (ASC OR DESC)
     * @return sorted List of GiftCertificates
     * @see TagGiftCertificateService#sortByNameByDateAscDesc(String, String)
     * */
    @GetMapping("sort-both/{name}/{date}")
    public ResponseEntity<?> sortByNameByDateAscDesc(@PathVariable String name, @PathVariable String date) {
        if (VerificationOfData.isStringValid(name) && VerificationOfData.isStringValid(date)) {
            if (VerificationOfData.isSortingTypeCorrect(name) && VerificationOfData.isSortingTypeCorrect(date)) {
                return new ResponseEntity<>(Map.of("CertificatesTags", tagGiftCertificateService.sortByNameByDateAscDesc(name, date)), HttpStatus.OK);
            }
            throw new ServerException("ASC or DESC value allowed only");
        }
        throw new ServerException("Invalid data");
    }
}