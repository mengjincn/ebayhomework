package org.jin.homework.service;

import org.jin.homework.entity.Account;
import org.jin.homework.entity.Endpoint;
import org.jin.homework.mapper.AccountMapper;
import org.jin.homework.mapper.EndpointMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final EndpointMapper endpointMapper;
    private final AccountMapper accountMapper;

    public UserService(EndpointMapper endpointMapper, AccountMapper accountMapper) {
        this.endpointMapper = endpointMapper;
        this.accountMapper = accountMapper;
    }


    public void addUserAccess(Long userId, Set<String> endpoints) throws IOException {
        Map<Long, Set<String>> userAccessExist = loadUserAccess();
        Map<Long, Set<String>> userAccess = new HashMap<>();
        if (userAccessExist.containsKey(userId)) {
            Set<String> existEndpoints = userAccessExist.get(userId);
            Set<String> newEndpoints = endpoints.stream()
                    .filter(endpoint -> !existEndpoints.contains(endpoint))
                    .collect(Collectors.toSet());

            userAccess.put(userId, newEndpoints);
        } else {
            userAccess.put(userId, endpoints);
        }
        saveUserAccess(userAccess);
    }

    public boolean hasAccess(Long userId, String resource) throws IOException {
        Map<Long, Set<String>> userAccess = loadUserAccess();
        Set<String> userResources = userAccess.get(userId);
        return userResources != null && userResources.contains(resource);
    }

    private Map<Long, Set<String>> loadUserAccess() {
        Map<Long, Set<String>> userAccess = new HashMap<>();
        List<Endpoint> allEndpoints = endpointMapper.getAllEndpoints();
        for (Endpoint endpoint : allEndpoints) {
            userAccess.computeIfAbsent(endpoint.getUserId(), k -> new HashSet<>()).add(endpoint.getEndpoint());
        }
        return userAccess;
    }

    private void saveUserAccess(Map<Long, Set<String>> userAccess) throws IOException {
        userAccess.forEach(
                (id, endpoints) -> {
                    for (String endpoint : endpoints) {
                        endpointMapper.insertEndpoint(id, endpoint);
                    }
                }
        );
    }

    public void addUser(Long userId, String accountName, String role) {
        Account user = accountMapper.getUserById(userId);
        if (user == null) {
            accountMapper.insertUser(new Account(userId, accountName, role));
        }
    }
}