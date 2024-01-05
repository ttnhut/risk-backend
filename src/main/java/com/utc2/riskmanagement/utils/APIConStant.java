package com.utc2.riskmanagement.utils;

public class APIConStant {
    public static class MasterData {
        public static final String ENDPOINT = "/api/v1/masterdatas/";
        public static final String GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT = ENDPOINT + "{id}/";

        public static final String RECOMMEND_CLASS_ENDPOINT = ENDPOINT + "recommend";
        public static String getDeleteSuccessStatus(String id) {
            return "Successfully delete master data with id: " + id;
        }
    }

    public static class User {
        public static final String ENDPOINT = "/api/v1/users/";
        public static final String GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT = ENDPOINT + "{email}/";
        public static String getDeleteSuccessStatus(String email) {
            return "Successfully delete user with id: " + email;
        }
    }

    public static class Risk {
        public static final String ENDPOINT = "/api/v1/risks/";
        public static final String GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT = ENDPOINT + "{id}/";
        public static final String TRACKING_ENDPOINT = ENDPOINT + "tracking/";
        public static final String GET_RISKS_OF_CLASS_ENDPOINT = "/api/v1/masterdatas/{classID}/risks/";
        public static String getDeleteSuccessStatus(String id) {
            return "Successfully delete risk with id: " + id;
        }
    }

    public static class Role {
        public static final String ENDPOINT = "/api/v1/roles/";
        public static final String GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT = ENDPOINT + "{id}/";
        public static String getDeleteSuccessStatus(String id) {
            return "Successfully delete risk with id: " + id;
        }
    }

    public static class Auth {
        public static final String ENDPOINT = "/api/v1/auth";
        public static final String ACTIVATION_ENDPOINT = ENDPOINT + "/activation";
        public static final String LOGIN_ENDPOINT = ENDPOINT + "/login";
        public static final String ACTIVATION_SUCCESS_MESSAGE = "Người dùng đã được kích hoạt thành công";
    }
}
