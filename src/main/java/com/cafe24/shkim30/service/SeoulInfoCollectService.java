package com.cafe24.shkim30.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeoulInfoCollectService {
    @Value("${resource-dir}")
    public String RESOURCE_DIR;
    public String getCultureListStr() {
        try {
            String filePath = RESOURCE_DIR + "/event_list.json";

            File file = new File(filePath);
            FileSystemResource resource = new FileSystemResource(file);

            byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String jsonData = new String(bdata, StandardCharsets.UTF_8);

            // jsonData에 파일 내용이 담겨있습니다.
            return jsonData;
        } catch (IOException e) {
            // 예외 처리
            e.printStackTrace();
            return null;
        }
    }
}
