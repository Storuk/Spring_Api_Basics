package com.epam.esm.utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateQueryGenerator {
    public Map<String, List<Object>> generateQuery(long id, Map<String, String> updatesMap){
        List<Object> updatesParam = new ArrayList<>();
        Instant lastUpdateTime = Instant.now();

        StringBuilder generatedQuery = new StringBuilder("UPDATE gift_certificate SET ");
        updatesMap.forEach((key, value) -> {
            generatedQuery.append(key).append(" = ? ,");
            updatesParam.add(value);
        });
        generatedQuery.append("last_update_date = ? WHERE id = ?");
        updatesParam.addAll(List.of(lastUpdateTime, id));
        return Map.of(generatedQuery.toString(), updatesParam);
    }
}
