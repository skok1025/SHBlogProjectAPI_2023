package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.fconline.BasicUserInfoDTO;
import com.cafe24.shkim30.dto.fconline.MaxDivisionInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FcOnlineService {

    private final RestTemplate restTemplate;

    @Value("${fconline.auth}")
    public String FCONLINE_AUTH;
    public BasicUserInfoDTO getBasicUserInfo(String nickname) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", FCONLINE_AUTH);

        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BasicUserInfoDTO> basicUserInfoDTOResponseEntity = restTemplate.exchange(
                "https://api.nexon.co.kr/fifaonline4/v1.0/users?nickname=" + nickname,
                HttpMethod.GET,
                entity,
                BasicUserInfoDTO.class
        );

        return basicUserInfoDTOResponseEntity.getBody();
    }

    public List<MaxDivisionInfoDTO> getMaxDivisionInfoMap(String accessId) {
        List<MaxDivisionInfoDTO> result = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", FCONLINE_AUTH);

        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> maxdivisionResponseEntity = restTemplate.exchange(
                "https://api.nexon.co.kr/fifaonline4/v1.0/users/d31d5e1db076e5c2e92ffbc2/maxdivision?accessid=" + accessId,
                HttpMethod.GET,
                entity,
                List.class
        );

        List<Map> maxDivisionInfoMapList = maxdivisionResponseEntity.getBody();

        Map<Integer, String> matchTypeMap = getMatchTypeMap();  // 매치타입 (key: matchtype, value: desc)
        Map<Integer, String> divisionMap = getDivisionMap();    // 등급맵 (key: divisionId, value: divisionName)

        for (Map maxDivisionInfoMap : maxDivisionInfoMapList) {
            MaxDivisionInfoDTO maxDivisionInfoDTO = new MaxDivisionInfoDTO(
                    Integer.parseInt(maxDivisionInfoMap.get("matchType").toString()),
                    Integer.parseInt(maxDivisionInfoMap.get("division").toString()),
                    matchTypeMap.get(Integer.parseInt(maxDivisionInfoMap.get("matchType").toString())),
                    divisionMap.get(Integer.parseInt(maxDivisionInfoMap.get("division").toString())),
                    maxDivisionInfoMap.get("achievementDate").toString()
            );

            result.add(maxDivisionInfoDTO);
        }

        return result;
    }
    public Map<Integer, String> getMatchTypeMap() {
        Map<Integer, String> result = new HashMap<>();

        List<Map> matchTypeDTOList =
                restTemplate.getForObject(
                        "https://static.api.nexon.co.kr/fconline/latest/matchtype.json",
                        List.class
                );

        for (Map matchTypeMap : matchTypeDTOList) {
            result.put(
                    Integer.parseInt(matchTypeMap.get("matchtype").toString()),
                    matchTypeMap.get("desc").toString()
            );
        }

        return result;
    }

    public Map<Integer, String> getDivisionMap() {
        Map<Integer, String> result = new HashMap<>();

        List<Map> divisionList =
                restTemplate.getForObject(
                        "https://static.api.nexon.co.kr/fconline/latest/division.json",
                        List.class
                );

        for (Map divisionMap : divisionList) {
            result.put(
                    Integer.parseInt(divisionMap.get("divisionId").toString()),
                    divisionMap.get("divisionName").toString()
            );
        }

        return result;
    }
}
