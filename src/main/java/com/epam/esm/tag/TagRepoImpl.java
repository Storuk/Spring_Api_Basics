package com.epam.esm.tag;

import com.epam.esm.utils.SqlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepoImpl implements TagRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean createTag(Tag tag) {
        return jdbcTemplate.update(SqlQuery.Tag.CREATE_TAG, tag.getName()) == 1;
    }

    @Override
    public boolean deleteTag(long id) {
        return jdbcTemplate.update(SqlQuery.Tag.DELETE_TAG, id) == 1;
    }

    @Override
    public List<Tag> getAllTags() {
        return jdbcTemplate.query(SqlQuery.Tag.GET_ALL_TAGS, (resultSet, i) ->
                new Tag().setId(resultSet.getLong("id")).setName(resultSet.getString("name")));
    }

    @Override
    public List<Tag> getTagById(long id) {
        return jdbcTemplate.query(SqlQuery.Tag.GET_TAG_BY_ID, new Long[]{id}, (resultSet, i) ->
                new Tag().setId(resultSet.getLong("id")).setName(resultSet.getString("name")));
    }

    @Override
    public boolean tagExists(String name) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(SqlQuery.Tag.IS_TAG_EXISTS, Boolean.class, name));
    }

    @Override
    public long getTagId(Tag tag) {
        return jdbcTemplate.queryForObject(SqlQuery.Tag.GET_TAG_ID_BY_NAME, new Object[]{tag.getName()}, Long.class);
    }
}
