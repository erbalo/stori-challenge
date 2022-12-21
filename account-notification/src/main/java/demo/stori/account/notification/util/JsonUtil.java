package demo.stori.account.notification.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

import java.io.File;
import java.util.List;

public final class JsonUtil {

    private JsonUtil() {
    }

    public static ObjectMapper createMapper(){
        return new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @SneakyThrows
    public static <R> String toJson(ObjectWriter writer, R representation) {
        return writer.writeValueAsString(representation);
    }

    public static <R> String toJson(R representation) {
        return toJson(createObjectMapper().writer(), representation);
    }

    public static ObjectMapper createObjectMapper() {
        return new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        ObjectMapper mapper = createObjectMapper();
        return fromJson(mapper.readerFor(clazz), json);
    }

    @SneakyThrows
    public static <T> T fromJson(ObjectReader reader, String json) {
        return reader.readValue(json);
    }

    @SneakyThrows
    public static <T> T fromFile(File file, Class<T> clazz) {
        ObjectMapper mapper = createObjectMapper();
        return mapper.readValue(file, clazz);
    }

    @SneakyThrows
    public static <T> List<T> fromJsonToList(String json, String listName, Class<T> clazz) {
        ObjectMapper mapper = createObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        JsonNode path = jsonNode.findPath(listName);
        return mapper.readValue(path.toString(), TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
    }

}
