package com.example.lms.payment;

import com.example.lms.courses.CourseRepository;
import com.example.lms.enrollment.EnrollmentService;
import com.example.lms.users.UserEntity;
import com.example.lms.users.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Value("${paystack.secret.key}")
    private String paystackSecretKey;

    private static final String PAYSTACK_URL = "https://api.paystack.co/transaction/initialize";

    private final UserRepository userRepository;
    private final EnrollmentService enrollmentService;

    public PaymentService(UserRepository userRepository, CourseRepository courseRepository,
                          EnrollmentService enrollmentService) {
        this.userRepository = userRepository;
        this.enrollmentService = enrollmentService;
    }

    public String initializePayment(String email, int amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("amount", amount * 100);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(PAYSTACK_URL, HttpMethod.POST, request, Map.class);

        return response.getBody().get("data").toString(); // Returns Payment URL
    }

    public boolean enrollUserInCourse(String userEmail, UUID courseId) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(userEmail);
        UUID userId = userOpt.get().getId();
        enrollmentService.enrollStudent(userId, courseId);
        return true;
    }
}
