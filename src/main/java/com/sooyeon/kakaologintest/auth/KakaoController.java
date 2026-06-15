package com.sooyeon.kakaologintest.auth;

import com.sooyeon.kakaologintest.auth.dto.KakaoTokenResponse;
import com.sooyeon.kakaologintest.auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sooyeon.kakaologintest.auth.dto.KakaoUserResponse;
import com.sooyeon.kakaologintest.user.domain.User;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @GetMapping("/kakao/login")
    public String kakaoLogin() {
        String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);

        String kakaoLoginUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + encodedRedirectUri;

        return "redirect:" + kakaoLoginUrl;
    }

    @GetMapping("/callback")
    @ResponseBody
    public String callback(@RequestParam String code) {
        User user = kakaoService.loginOrCreateUser(code);

        return "로그인 성공! DB 저장 완료<br>"
                + "우리 서비스 user id: " + user.getId() + "<br>"
                + "카카오 id: " + user.getKakaoId() + "<br>"
                + "닉네임: " + user.getNickname();
    }
}