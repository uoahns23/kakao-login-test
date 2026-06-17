package com.sooyeon.kakaologintest.auth.dto;

import java.io.Serializable;

public record LoginUser(
        Long id,
        Long kakaoId,
        String nickname
) implements Serializable {
}
