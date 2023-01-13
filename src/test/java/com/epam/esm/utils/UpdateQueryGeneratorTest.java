package com.epam.esm.utils;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UpdateQueryGeneratorTest {
    UpdateQueryGenerator updateQueryGenerator = new UpdateQueryGenerator();

    @Test
    void generateQuery() {
        Map<String, List<Object>> result = updateQueryGenerator.generateQuery(10, Map.of("name", "abc"));
        Map.Entry<String,List<Object>> entry = result.entrySet().iterator().next();
        List<Object> list = entry.getValue();

        assertEquals("UPDATE gift_certificate SET name = ? ,last_update_date = ? WHERE id = ?",entry.getKey());
        assertEquals("abc",list.get(0));
        assertEquals(10L,list.get(2));
    }
}