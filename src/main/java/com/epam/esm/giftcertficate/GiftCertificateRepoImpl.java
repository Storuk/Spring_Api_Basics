package com.epam.esm.giftcertficate;

import com.epam.esm.DB.SqlQuery;
import com.epam.esm.tag.Tag;
import com.epam.esm.utils.UpdateQueryGenerator;
import com.epam.esm.utils.VerificationOfData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateRepoImpl implements GiftCertificateRepo {

    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean createGiftCertificate(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(SqlQuery.GiftCertificate.CREATE_CERTIFICATE, giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration(), Instant.now(), Instant.now()) == 1;
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        return jdbcTemplate.query(SqlQuery.GiftCertificate.GET_ALL_CERTIFICATES, (resultSet, i) ->
                new GiftCertificate().setId(resultSet.getLong("id")).
                        setName(resultSet.getString("name")).
                        setDescription(resultSet.getString("description")).
                        setPrice(resultSet.getInt("price")).
                        setDuration(resultSet.getInt("duration")).
                        setCreateDate(resultSet.getDate("create_date")).
                        setLastUpdateDate(resultSet.getDate("last_update_date")).
                        setTags(getAllTagsByCertificate(resultSet.getLong("id"))));
    }

    @Override
    public List<GiftCertificate> getGiftCertificateById(long id) {
        return jdbcTemplate.query(SqlQuery.GiftCertificate.GET_CERTIFICATE_BY_ID, new Object[]{id}, (resultSet, i) ->
                new GiftCertificate().
                        setId(resultSet.getLong("id")).
                        setName(resultSet.getString("name")).
                        setTags(getAllTagsByCertificate(resultSet.getLong("id"))).
                        setDescription(resultSet.getString("description")).
                        setPrice(resultSet.getInt("price")).
                        setPrice(resultSet.getInt("price")).
                        setDuration(resultSet.getInt("duration")).
                        setCreateDate(resultSet.getDate("create_date")).
                        setLastUpdateDate(resultSet.getDate("last_update_date")));
    }

    @Override
    public boolean deleteGiftCertificate(long id) {
        return jdbcTemplate.update(SqlQuery.GiftCertificate.DELETE_CERTIFICATE, id) == 1;
    }

    @Override
    public boolean giftCertificateExists(GiftCertificate giftCertificate) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(SqlQuery.GiftCertificate.IS_GIFT_CERTIFICATE_EXIST, Boolean.class, giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration()));
    }

    @Override
    public long getGiftCertificateId(GiftCertificate giftCertificate) {
        return jdbcTemplate.queryForObject(SqlQuery.GiftCertificate.GET_CERTIFICATE_ID, new Object[]{giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration()}, Long.class);
    }

    @Override
    public boolean updateGiftCertificate(long id, Map<String, String> updatesMap) {
        UpdateQueryGenerator updateQueryGenerator = new UpdateQueryGenerator();
        Map<String, List<Object>> stringListMap = updateQueryGenerator.generateQuery(id, updatesMap);
        Map.Entry<String,List<Object>> entry = stringListMap.entrySet().iterator().next();
        return jdbcTemplate.update(entry.getKey(), entry.getValue().toArray()) == 1;
    }

    private List<Tag> getAllTagsByCertificate(long certificateId) {
        return jdbcTemplate.query(SqlQuery.TagGiftCertificate.GET_ALL_TAGS_BY_GIFT_CERTIFICATE_ID, new Long[]{certificateId}, (resultSet, i) ->
                new Tag().setId(resultSet.getLong("id")).setName(resultSet.getString("name")));
    }
}
