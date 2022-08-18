package com.cafe24.shkim30.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    private String code; // 400, 500
    private String message;
}
