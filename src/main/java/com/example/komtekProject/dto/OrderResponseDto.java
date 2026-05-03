package com.example.komtekProject.dto;

import com.example.komtekProject.enums.OrderStatus;
import lombok.*;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private Long patientId;
    private String patientFullName;
    private String patientSnils;
    private String patientEnp;
    private LocalDateTime createdDate;
    private OrderStatus status;
    private String comment;

}
