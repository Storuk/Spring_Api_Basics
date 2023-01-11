package com.epam.esm.giftcertificatetag;

import com.epam.esm.exceptionhandler.exception.ServerException;
import com.epam.esm.utils.VerificationOfData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/tags-certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagGiftCertificateController {
    private final TagGiftCertificateService tagGiftCertificateService;

    @Autowired
    public TagGiftCertificateController(TagGiftCertificateService tagGiftCertificateService) {
        this.tagGiftCertificateService = tagGiftCertificateService;
    }

    @GetMapping("{name}")
    public ResponseEntity<?> getGiftCertificateWithTagsByTagName(@PathVariable String name) {
        if (VerificationOfData.isStringValid(name)) {
            return new ResponseEntity<>(Map.of("CertificatesTags", tagGiftCertificateService.getGiftCertificateWithTagsByTagName(name)), HttpStatus.OK);
        }
        throw new ServerException("Invalid data");
    }

    @GetMapping("description/{description}")
    public ResponseEntity<?> getGiftCertificateWithTagsByPartOfDescription(@PathVariable String description) {
        if (VerificationOfData.isStringValid(description)) {
            return new ResponseEntity<>(Map.of("CertificatesTags", tagGiftCertificateService.getGiftCertificateWithTagsByPartOfDescription(description)), HttpStatus.OK);
        }
        throw new ServerException("Invalid data");
    }

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