package com.example.lms.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Value("${paystack.secret.key}")
    private String paystackSecretKey;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<String> initiatePayment(@RequestBody PaymentRequest request) {
        // Validate the request
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        // Business logic for payment
        return ResponseEntity.ok("Payment initiated successfully");
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> requestBody) {
        String reference = requestBody.get("reference");
        String userEmail = requestBody.get("email");

        UUID courseId;
        try {
            courseId = UUID.fromString(requestBody.get("courseId"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid Course ID format.");
        }

        if (reference == null || userEmail == null) {
            return ResponseEntity.badRequest().body("Reference, Course ID, and Email are required.");
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.paystack.co/transaction/verify/" + reference,
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && (Boolean) responseBody.get("status")) {
            // Payment successful, enroll user in course
            boolean enrolled = paymentService.enrollUserInCourse(userEmail, courseId);
            if (enrolled) {
                return ResponseEntity.ok(Map.of("message", "Payment verified. User enrolled successfully."));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Payment verified but enrollment failed."));        }

        return ResponseEntity.badRequest()
                .body(Map.of("error", "Payment verification failed"));    }



    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> payload) {
        String status = (String) payload.get("status");

        if ("success".equals(status)) {
            // Process the payment (e.g., enroll student in course)
            return ResponseEntity.ok("Payment successful");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed");
    }

}

