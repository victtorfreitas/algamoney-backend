package com.example.algamoney.api.config.token;

import com.example.algamoney.api.config.property.ApiProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@ControllerAdvice
public class RefreshTokenPostProcess implements ResponseBodyAdvice<OAuth2AccessToken> {
    private ApiProperty property;

    public RefreshTokenPostProcess(ApiProperty property) {
        this.property = property;
    }

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return Objects.requireNonNull(methodParameter.getMethod()).getName().equals("postAccessToken");
    }

    @Override
    @NonNull
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body,
                                             @NonNull MethodParameter methodParameter, @NonNull MediaType mediaType,
                                             @NonNull Class<? extends HttpMessageConverter<?>> aClass,
                                             @NonNull ServerHttpRequest serverHttpRequest,
                                             @NonNull ServerHttpResponse serverHttpResponse) {

        String refreshToken = body.getRefreshToken().getValue();

        HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        HttpServletResponse response = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;

        adicionaRefreshTokenNoCookie(refreshToken, request, response);
        removerRefreshTokenDoBody(token);
        return body;
    }

    private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
        token.setRefreshToken(null);
    }

    private void adicionaRefreshTokenNoCookie(String refreshToken,
                                              HttpServletRequest request, HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(property.getSeguranca().isEnableHttps());
        refreshTokenCookie.setPath(request.getContextPath() + "/oauth/token");
        refreshTokenCookie.setMaxAge(2592000);
        response.addCookie(refreshTokenCookie);
    }
}
