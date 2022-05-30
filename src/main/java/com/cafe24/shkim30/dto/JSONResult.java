package com.cafe24.shkim30.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JSONResult {
	@ApiModelProperty(example = "success OR fail")
    private String result;  // success, fail
	
	@ApiModelProperty(example = "if fail, set message")
    private String message; // if fail, set message
	
	@ApiModelProperty(example = "if success, set Object")
    private Object data;    // if success, set Object

    /**
     * �꽦怨� �떆, Object �꽭�똿
     * - �깮�꽦�옄�뒗 private 濡� 吏곸젒�젒洹� 遺덇��븯怨� �빐�떦 �븿�닔濡� JSONResult �꽭�똿
     * @param message 硫붿꽭吏�
     * @param data �뜲�씠�꽣
     * @return JSONResult
     */
    public static JSONResult success(String message, Object data) {
        return new JSONResult("success", message, data);
    }

    /**
     * �떎�뙣 �떆, �떎�뙣 message �꽭�똿
     * - �깮�꽦�옄�뒗 private 濡� 吏곸젒�젒洹� 遺덇��븯怨� �빐�떦 �븿�닔濡� JSONResult �꽭�똿
     * @param message 硫붿꽭吏�
     * @return JSONResult
     */
    public static JSONResult fail(String message) {
        return new JSONResult("fail", message, null);
    }
}
