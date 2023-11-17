package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.JSONResult;
import com.cafe24.shkim30.dto.fconline.BasicUserInfoDTO;
import com.cafe24.shkim30.dto.fconline.FcOnlineInfoDTO;
import com.cafe24.shkim30.dto.fconline.MaxDivisionInfoDTO;
import com.cafe24.shkim30.service.FcOnlineService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"fc온라인 api 조회"})
@RestController
@RequestMapping("/fconline")
@Slf4j
@RequiredArgsConstructor
public class FcOnlineController {
    private final FcOnlineService fcOnlineService;

    @GetMapping("info")
    public ResponseEntity<JSONResult> maxdivision(@RequestParam("nickname") String nickname) {
        BasicUserInfoDTO basicUserInfoDTO = fcOnlineService.getBasicUserInfo(nickname);
        List<MaxDivisionInfoDTO> maxDivisionInfoList = fcOnlineService.getMaxDivisionInfoMap(basicUserInfoDTO.getAccessId());

        return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(
                "조회성공",
                new FcOnlineInfoDTO(basicUserInfoDTO, maxDivisionInfoList))
        );
    }

}
