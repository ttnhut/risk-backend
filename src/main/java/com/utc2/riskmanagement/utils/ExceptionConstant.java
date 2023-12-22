package com.utc2.riskmanagement.utils;

public class ExceptionConstant {
    public static final String RESOURCE_NOT_FOUND = "%s not found %s with value = %s";
    public static final String RESOURCE_EXIST = "%s exist %s with value = %s";
    public static class MasterData {
        public static final String RESOURCE = "Master Data";
        public static final String ID_FIELD = "id";
    }

    public static class User {
        public static final String RESOURCE = "User";
        public static final String ID_FIELD = "email";
        public static final String CODE_FIELD = "code";
    }

    public static class Risk {
        public static final String RESOURCE = "Risk";
        public static final String ID_FIELD = "id";
    }

    public static class Role {
        public static final String RESOURCE = "Role";
        public static final String ID_FIELD = "id";
    }
}
