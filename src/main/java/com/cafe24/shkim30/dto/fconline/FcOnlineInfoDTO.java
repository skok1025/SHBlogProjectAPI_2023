package com.cafe24.shkim30.dto.fconline;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FcOnlineInfoDTO {
    BasicUserInfoDTO basicUserInfoDTO; // 기본정보
    List<MaxDivisionInfoDTO> maxDivisionInfoList; // 최고등급정보
}
