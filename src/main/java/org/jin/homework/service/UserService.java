package org.jin.homework.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jin.homework.model.User;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    private static final String USER_ACCESS_FILE = "user_access.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public User decodeAuthHeader(String authHeader) throws IOException {
        String decodedHeader = new String(Base64.getDecoder().decode(authHeader));
        return objectMapper.readValue(decodedHeader, User.class);
    }

    public void addUserAccess(Long userId, Set<String> endpoints) throws IOException {
        Map<Long, Set<String>> userAccess = loadUserAccess();
        userAccess.put(userId, endpoints);
        saveUserAccess(userAccess);
    }

    public boolean hasAccess(Long userId, String resource) throws IOException {
        Map<Long, Set<String>> userAccess = loadUserAccess();
        Set<String> userResources = userAccess.get(userId);
        return userResources != null && userResources.contains(resource);
    }

    private Map<Long, Set<String>> loadUserAccess() throws IOException {
        File file = new File(USER_ACCESS_FILE);
        if (file.exists()) {
            return objectMapper.readValue(file, new TypeReference<Map<Long, Set<String>>>() {
            });
        }
        return new HashMap<>();
    }

    private void saveUserAccess(Map<Long, Set<String>> userAccess) throws IOException {
        objectMapper.writeValue(new File(USER_ACCESS_FILE), userAccess);
    }
}