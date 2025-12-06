package com.example.demo.global.dto.resp.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor(staticName = "of")
public class SingleResult<T> extends CommonResult {

    @Schema(description = "단일 데이터", nullable = false)
    private T data;
}
