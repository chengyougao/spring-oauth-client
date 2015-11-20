package com.andaily.springoauth.service;

import java.util.Map;

import com.andaily.springoauth.service.dto.AccessTokenDto;
import com.andaily.springoauth.service.dto.AuthAccessTokenDto;
import com.andaily.springoauth.service.dto.AuthCallbackDto;
import com.andaily.springoauth.service.dto.RefreshAccessTokenDto;
import com.andaily.springoauth.service.dto.UserDto;

/**
 * @author Shengzhao Li
 */

public interface OauthService {

	String submitAuthorizationCode(String clientId, String scope, String clientSecret,
			String authorization_code_callback);

	Map<String, String> authorizationCodeCallback(AuthCallbackDto callbackDto);

	AccessTokenDto retrieveAccessTokenDto(AuthAccessTokenDto tokenDto);

	AuthAccessTokenDto createAuthAccessTokenDto(AuthCallbackDto callbackDto);

	UserDto loadUnityUserDto(String accessToken);

	AccessTokenDto retrievePasswordAccessTokenDto(AuthAccessTokenDto authAccessTokenDto);

	AccessTokenDto refreshAccessTokenDto(RefreshAccessTokenDto refreshAccessTokenDto);

	AccessTokenDto retrieveCredentialsAccessTokenDto(AuthAccessTokenDto authAccessTokenDto);
}
