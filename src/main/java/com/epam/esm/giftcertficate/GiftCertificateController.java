package com.epam.esm.giftcertficate;

import com.epam.esm.utils.VerificationOfData;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import com.epam.esm.exceptionhandler.exception.ServerException;

import static com.epam.esm.utils.VerificationOfData.checkIdZeroValue;

@RestController
@RequestMapping(value = "/certificate", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCertificates() {
        return ResponseEntity.ok(Map.of("All Gift Certificates", giftCertificateService.getAllGiftCertificates()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCertificateById(@PathVariable long id) {
        if (checkIdZeroValue(id)) {
            return ResponseEntity.ok(Map.of("Gift Certificate", giftCertificateService.getGiftCertificateById(id)));
        }
        throw new ServerException("No certificate with id = " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable long id) {
        if (checkIdZeroValue(id)) {
            giftCertificateService.deleteGiftCertificate(id);
            return ResponseEntity.ok(Map.of("status", HttpStatus.OK));
        }
        throw new ServerException("No certificate with id = " + id);
    }

    @PostMapping
    public ResponseEntity<?> createCertificate(@RequestBody GiftCertificate giftCertificate) {
        if(VerificationOfData.isNewCertificateValid(giftCertificate)) {
            giftCertificateService.createGiftCertificate(giftCertificate);
            return new ResponseEntity<>(Map.of("status", HttpStatus.CREATED), HttpStatus.CREATED);
        }
        throw new ServerException("Invalid data. Check your inputs");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCertificate(@PathVariable("id") long id, @RequestBody GiftCertificate giftCertificate) {
        if(checkIdZeroValue(id)){
            Map<String, String> updates = VerificationOfData.checkCertificateAndGetListOfFieldsForUpdate(giftCertificate);
            if (!updates.isEmpty() || giftCertificate.getTags() != null) {
                return ResponseEntity.ok(giftCertificateService.updateGiftCertificate(id, giftCertificate.getTags(), updates));
            }
            throw new ServerException("Nothing to update. All fields are empty");
        }
        throw new ServerException("Invalid data. Check your inputs");
    }

}
