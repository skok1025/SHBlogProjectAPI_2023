package com.cafe24.shkim30.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JSONResult {
    private String result;  // success, fail
    private String message; // if fail, set message
    private Object data;    // if success, set Object

    /**
     * 성공 시, Object 세팅
     * - 생성자는 private 로 직접접근 불가하고 해당 함수로 JSONResult 세팅
     * @param message 메세지
     * @param data 데이터
     * @return JSONResult
     */
    public static JSONResult success(String message, Object data) {
        return new JSONResult("sucess", message, data);
    }

    /**
     * 실패 시, 실패 message 세팅
     * - 생성자는 private 로 직접접근 불가하고 해당 함수로 JSONResult 세팅
     * @param message 메세지
     * @return JSONResult
     */
    public static JSONResult fail(String message) {
        return new JSONResult("fail", message, null);
    }
}
