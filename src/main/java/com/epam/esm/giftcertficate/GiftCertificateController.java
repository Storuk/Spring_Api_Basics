package com.epam.esm.giftcertficate;

import com.epam.esm.utils.VerificationOfData;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import com.epam.esm.exceptions.ServerException;

import static com.epam.esm.utils.VerificationOfData.checkIdZeroValue;

/**
 * Class GiftCertificateController which contain method related with GiftCertificate
 * @author Vlad Storoshchuk
 * */
@RestController
@RequestMapping(value = "/certificate", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * A controller get method for getting all GiftCertificates
     * @return List of GiftCertificates
     * @see GiftCertificateService#getAllGiftCertificates()
     * */
    @GetMapping
    public ResponseEntity<?> getAllCertificates() {
        return ResponseEntity.ok(Map.of("All Gift Certificates", giftCertificateService.getAllGiftCertificates()));
    }

    /**
     * A controller get method for getting GiftCertificate
     * @param id - id of GiftCertificate (min value 1)
     * @return GiftCertificate
     * @see GiftCertificateService#getGiftCertificateById(long)
     * */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCertificateById(@PathVariable long id) {
        if (checkIdZeroValue(id)) {
            return ResponseEntity.ok(Map.of("Gift Certificate", giftCertificateService.getGiftCertificateById(id)));
        }
        throw new ServerException("No certificate with id = " + id);
    }

    /**
     * A controller delete method for deleting GiftCertificate
     * @param id - id of GiftCertificate for deleting (min value 1)
     * @return HttpStatus OK
     * @see GiftCertificateService#deleteGiftCertificate(long)
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable long id) {
        if (checkIdZeroValue(id)) {
            giftCertificateService.deleteGiftCertificate(id);
            return ResponseEntity.ok(Map.of("status", HttpStatus.OK));
        }
        throw new ServerException("No certificate with id = " + id);
    }

    /**
     * A controller post method for creating GiftCertificate
     * @param giftCertificate the GiftCertificate object that will be created in database
     * @return HttpStatus OK
     * @see GiftCertificateService#createGiftCertificate(GiftCertificate)
     * */
    @PostMapping
    public ResponseEntity<?> createCertificate(@RequestBody GiftCertificate giftCertificate) {
        if(VerificationOfData.isNewCertificateValid(giftCertificate)) {
            giftCertificateService.createGiftCertificate(giftCertificate);
            return new ResponseEntity<>(Map.of("status", HttpStatus.CREATED), HttpStatus.CREATED);
        }
        throw new ServerException("Invalid data. Check your inputs");
    }

    /**
     * A controller patch method for updating GiftCertificate
     * @param id - id of gift certificate (min value 1)
     * @param giftCertificate the GiftCertificate object for updating giftCertificate
     * @return updated GiftCertificate
     * @see GiftCertificateService#updateGiftCertificate(long, GiftCertificate) 
     * */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCertificate(@PathVariable("id") long id, @RequestBody GiftCertificate giftCertificate) {
        if(checkIdZeroValue(id)){
            if (VerificationOfData.isGiftCertificateValidForUpdate(giftCertificate)) {
                return ResponseEntity.ok(giftCertificateService.updateGiftCertificate(id, giftCertificate));
            }
            throw new ServerException("Nothing to update. All fields are empty");
        }
        throw new ServerException("Invalid data. Check your inputs");
    }

}
