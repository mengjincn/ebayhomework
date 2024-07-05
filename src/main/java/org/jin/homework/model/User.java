package org.jin.homework.model;

import java.util.Set;

public class User {
    private Long userId;
    private String accountName;
    private String role;
    private Set<String> resources;

    public User() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<String> getResources() {
        return resources;
    }

    public void setResources(Set<String> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName())
                .append('[')
                .append("userId=").append(userId == null ? "null" : userId)
                .append(", accountName=").append(accountName == null ? "null" : "'" + accountName + "'")
                .append(", role=").append(role == null ? "null" : "'" + role + "'")
                .append(", resources=").append(resourcesToString())
                .append(']')
                .toString();
    }

    private String resourcesToString() {
        if (resources == null) {
            return "null";
        }
        if (resources.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (String resource : resources) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(resource == null ? "null" : "'" + resource + "'");
        }
        sb.append("]");
        return sb.toString();
    }
}
