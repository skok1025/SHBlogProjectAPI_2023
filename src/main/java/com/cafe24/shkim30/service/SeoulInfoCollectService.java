package com.cafe24.shkim30.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeoulInfoCollectService {
    public String getCultureListStr() {
        try {
            ClassPathResource resource = new ClassPathResource("event_list.json");
            InputStream inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            return new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // 예외 처리
            e.printStackTrace();
            return null;
        }
    }
}
