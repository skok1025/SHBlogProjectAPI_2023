package com.cafe24.shkim30.dto.fconline;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MaxDivisionInfoDTO {
    private Integer matchType;
    private Integer division;
    private String matchTypeName;
    private String divisionName;
    private String achievementDate;
}
