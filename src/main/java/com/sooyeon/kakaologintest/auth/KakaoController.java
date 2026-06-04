package com.sooyeon.kakaologintest.auth;

import com.sooyeon.kakaologintest.auth.dto.KakaoTokenResponse;
import com.sooyeon.kakaologintest.auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @GetMapping("/callback")
    public String kakaoCallback(String code) {
        KakaoTokenResponse tokenResponse = kakaoService.getAccessToken(code);

        return "access token: " + tokenResponse.getAccessToken()
                + "\nrefresh token: " + tokenResponse.getRefreshToken()
                + "\nexpires in: " + tokenResponse.getExpiresIn();
    }
}