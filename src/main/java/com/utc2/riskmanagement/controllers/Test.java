package com.utc2.riskmanagement.controllers;

import com.utc2.riskmanagement.entities.User;
import com.utc2.riskmanagement.payloads.UserDTO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Test {
    // Hàm để đề xuất lớp học dựa trên số lần lỗi
    public static String recommendClass(Map<String, Integer> classErrors) {
        if (classErrors.isEmpty()) {
            // Không có dữ liệu để đề xuất
            return "Không có lớp học để đề xuất";
        }

        // Sử dụng lọc cộng tác để tìm lớp học có số lần lỗi thấp nhất
        String bestClass = null;
        int minErrors = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : classErrors.entrySet()) {
            String className = entry.getKey();
            int errors = entry.getValue();

            if (errors < minErrors) {
                minErrors = errors;
                bestClass = className;
            }
        }

        return "Đề xuất lớp học: " + bestClass;
    }

    public static void main(String[] args) {
        // Tạo một danh sách lớp học và số lần lỗi tương ứng
        Map<String, Integer> classErrors = new HashMap<>();
        classErrors.put("Lớp A", 0);
        classErrors.put("Lớp B", 0);
        classErrors.put("Lớp C", 0);
        classErrors.put("Lớp D", 0);

        // Gọi hàm đề xuất lớp học
        String recommendation = recommendClass(classErrors);

        // In kết quả đề xuất
        System.out.println(recommendation);
    }
}
