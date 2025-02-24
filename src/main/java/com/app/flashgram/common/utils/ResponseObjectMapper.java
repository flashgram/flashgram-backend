package com.app.flashgram.common.utils;

import com.app.flashgram.common.ui.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * Response 객체와 문자열 간의 변환을 처리하는 유틸리티 클래스
 * Jackson ObjectMapper를 사용하여 직렬화와 역직렬화 수행
 */
public class ResponseObjectMapper {

    private ResponseObjectMapper() {

    }

    private static final ObjectMapper objMapper = new ObjectMapper();

    /**
     * 문자열을 Response 객체로 변환
     *
     * @param response JSON 형식의 문자열
     * @return 변환된 Response 객체, 변환 실패 시 null
     */
    public static Response toResponseObject(String response) {
        try {

            return objMapper.readValue(response, Response.class);
        } catch (IOException e) {

            return null;
        }
    }

    /**
     * Response 객체를 JSON 문자열로 변환
     *
     * @param response 변환할 Response 객체
     * @return JSON 형식의 문자열, 변환 실패 시 null
     */
    public static String toStringResponse(Response<?> response) {
        try {
            return objMapper.writeValueAsString(response);
        } catch (IOException e) {

            return null;
        }
    }
}
