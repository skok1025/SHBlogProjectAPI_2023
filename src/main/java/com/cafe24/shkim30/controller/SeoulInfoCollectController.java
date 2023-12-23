package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.service.SeoulInfoCollectService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"서울 공공데이터 정보"})
@RestController
@RequestMapping("/seoul-info")
@Slf4j
@RequiredArgsConstructor
public class SeoulInfoCollectController {
    private final SeoulInfoCollectService seoulInfoCollectService;

    @GetMapping("/culture-list")
    public String getCultureList() {
        return seoulInfoCollectService.getCultureListStr();
    }
}
