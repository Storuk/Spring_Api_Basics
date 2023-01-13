package com.epam.esm.taggiftcertificate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TagGiftCertificate {
    private Long id;
    private Long gift_certificate_id;
    private Long tag_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGift_certificate_id() {
        return gift_certificate_id;
    }

    public TagGiftCertificate setGift_certificate_id(Long gift_certificate_id) {
        this.gift_certificate_id = gift_certificate_id;
        return this;
    }

    public Long getTag_id() {
        return tag_id;
    }

    public TagGiftCertificate setTag_id(Long tag_id) {
        this.tag_id = tag_id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TagGiftCertificate that = (TagGiftCertificate) o;

        return new EqualsBuilder().append(id, that.id).append(gift_certificate_id, that.gift_certificate_id).append(tag_id, that.tag_id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(gift_certificate_id).append(tag_id).toHashCode();
    }
}
