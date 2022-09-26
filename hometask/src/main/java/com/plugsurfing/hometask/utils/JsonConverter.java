package com.plugsurfing.hometask.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class JsonConverter implements AttributeConverter<Map<String, Object>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> contentAsMap) {
        String contentAsSerializedJson = "{}";
        try {
            contentAsSerializedJson = objectMapper.writeValueAsString(contentAsMap);
        } catch (final JsonProcessingException e) {
            log.error("JSON reading error", e);
        }
        return contentAsSerializedJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String contentAsSerializedJson) {
        Map<String, Object> contentAsMap = new HashMap<>();
        if (contentAsSerializedJson == null) {
            return contentAsMap;
        }
        try {
            contentAsMap = objectMapper.readValue(contentAsSerializedJson, Map.class);
        } catch (final IOException e) {
            log.error("JSON reading error", e);
        }
        return contentAsMap;
    }
}
