package com.example.demo.global.util;

import com.example.demo.global.dto.resp.result.SingleResult;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseUtil {

    public static <T> SingleResult<T> getSingleResult(T data) {
        return SingleResult.of(data);
    }

}
