package org.jin.homework.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jin.homework.model.User;

import java.io.IOException;
import java.util.Base64;

public class Base64Util {
    private Base64Util() {
    }

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static User decodeAuthHeader(String authHeader) throws IOException {
        String decodedHeader = new String(Base64.getDecoder().decode(authHeader));
        return objectMapper.readValue(decodedHeader, User.class);
    }
}
