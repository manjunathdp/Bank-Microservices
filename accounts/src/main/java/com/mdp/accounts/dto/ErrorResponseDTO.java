package com.mdp.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    String apiPath;
    HttpStatus errorCode;
    String errorMessage;
    LocalDateTime errorTime;
}
