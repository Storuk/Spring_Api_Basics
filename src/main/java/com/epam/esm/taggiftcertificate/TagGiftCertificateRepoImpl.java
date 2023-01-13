package com.epam.esm.taggiftcertificate;

import com.epam.esm.giftcertficate.GiftCertificate;
import com.epam.esm.tag.Tag;
import com.epam.esm.utils.SqlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagGiftCertificateRepoImpl implements TagGiftCertificateRepo {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagGiftCertificateRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GiftCertificate> getGiftCertificateWithTagsByTagName(String tagName){
        return jdbcTemplate.query(SqlQuery.TagGiftCertificate.GET_GIFT_CERTIFICATE_TAGS_BY_TAG_NAME, new String[]{tagName}, (resultSet, i) ->
                new GiftCertificate().setId(resultSet.getLong("id")).
                        setName(resultSet.getString("name")).
                        setDescription(resultSet.getString("description")).
                        setPrice(resultSet.getInt("price")).
                        setDuration(resultSet.getInt("duration")).
                        setCreateDate(resultSet.getDate("create_date")).
                        setLastUpdateDate(resultSet.getDate("last_update_date")).
                        setTags(getAllTagsByCertificateId(resultSet.getLong("id"))));
    }

    @Override
    public List<GiftCertificate> getGiftCertificateWithTagsByPartOfDescription(String partOfDescription){
        return jdbcTemplate.query(SqlQuery.TagGiftCertificate.GET_GIFT_CERTIFICATE_TAGS_BY_PART_OF_DESCRIPTION, new String[]{partOfDescription + "%"}, (resultSet, i) ->
                new GiftCertificate().setId(resultSet.getLong("id")).
                        setName(resultSet.getString("name")).
                        setDescription(resultSet.getString("description")).
                        setPrice(resultSet.getInt("price")).
                        setDuration(resultSet.getInt("duration")).
                        setCreateDate(resultSet.getDate("create_date")).
                        setLastUpdateDate(resultSet.getDate("last_update_date")).
                        setTags(getAllTagsByCertificateId(resultSet.getLong("id"))));
    }

    @Override
    public List<GiftCertificate> sortByNameAscDesc(String nameSortType){
        return jdbcTemplate.query(SqlQuery.TagGiftCertificate.sortByName(nameSortType), (resultSet, i) ->
                new GiftCertificate().setId(resultSet.getLong("id")).
                        setName(resultSet.getString("name")).
                        setDescription(resultSet.getString("description")).
                        setPrice(resultSet.getInt("price")).
                        setDuration(resultSet.getInt("duration")).
                        setCreateDate(resultSet.getDate("create_date")).
                        setLastUpdateDate(resultSet.getDate("last_update_date")).
                        setTags(getAllTagsByCertificateId(resultSet.getLong("id"))));
    }

    @Override
    public List<GiftCertificate> sortByNameByDateAscDesc(String nameSortType, String dateSortType){
        return jdbcTemplate.query(SqlQuery.TagGiftCertificate.sortByNameAndDate(nameSortType, dateSortType),
                (resultSet, i) -> new GiftCertificate().setId(resultSet.getLong("id")).
                        setName(resultSet.getString("name")).
                        setDescription(resultSet.getString("description")).
                        setPrice(resultSet.getInt("price")).
                        setDuration(resultSet.getInt("duration")).
                        setCreateDate(resultSet.getDate("create_date")).
                        setLastUpdateDate(resultSet.getDate("last_update_date")).
                        setTags(getAllTagsByCertificateId(resultSet.getLong("id"))));
    }

    @Override
    public boolean createGiftCertificateTag(long certificateId, long tagId) {
        return jdbcTemplate.update(SqlQuery.TagGiftCertificate.CREATE_CERTIFICATE_TAG_RELATION, certificateId, tagId) == 1;
    }

    @Override
    public boolean deleteGiftCertificateTag(long certificateId) {
        jdbcTemplate.update(SqlQuery.TagGiftCertificate.DELETE_CERTIFICATE_TAG_RELATION, certificateId);
        return true;
    }

    private List<Tag> getAllTagsByCertificateId(long certificateId) {
        return jdbcTemplate.query(SqlQuery.TagGiftCertificate.GET_ALL_TAGS_BY_GIFT_CERTIFICATE_ID, new Object[]{certificateId}, (resultSet, i) ->
                new Tag().setId(resultSet.getLong("id")).setName(resultSet.getString("name")));
    }
}
