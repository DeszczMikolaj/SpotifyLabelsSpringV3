package spotify.spotifylabelsspringv3.track;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class ArtistConverter implements AttributeConverter<Set<Artist>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Artist> attribute) {
        if (attribute == null || attribute.isEmpty()) return null;
        return attribute.stream().map(Artist::getName).collect(Collectors.joining(","));
    }

    @Override
    public Set<Artist> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return Set.of();
        return Arrays.stream(dbData.split(","))
                .map(String::trim)
                .map(string -> new Artist())
                .collect(Collectors.toSet());
    }
}
