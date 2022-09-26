package com.plugsurfing.hometask.service.artistdetails;

import com.plugsurfing.hometask.utils.JsonConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtistDetails {
    private Map<String, Object> detailsData;

    @Override
    public String toString() {
        JsonConverter converter = new JsonConverter();
        return converter.convertToDatabaseColumn(detailsData);
    }

    public static class Builder {
        private Map<String, Object> detailsData;

        public Builder() {
            detailsData = new HashMap<>();
        }

        public Builder withId(String id) {
            return this;
        }

        public Builder withName(String name) {
            return this;
        }

        public Builder withGender(String gender) {
            return this;
        }

        public Builder withCountry(String country) {
            return this;
        }

        public Builder withDisambiguation(String disambiguation) {
            return this;
        }

        public Builder withAlbums(List<Map<String, String>> albums) {
            return this;
        }
    }
}
