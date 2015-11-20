package com.andaily.springoauth.web;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.andaily.springoauth.service.OauthService;
import com.andaily.springoauth.service.dto.AuthCallbackDto;
import com.andaily.springoauth.service.dto.AuthorizationCodeDto;

/**
 * 'authorization_code'验证控制器
 *
 */
@Controller
public class AuthorizationCodeController {

	private static final Logger	LOG	= LoggerFactory.getLogger(AuthorizationCodeController.class);

	@Value("#{properties['user-authorization-uri']}")
	private String				userAuthorizationUri;

	@Value("#{properties['application-host']}")
	private String				host;

	@Value("#{properties['unityUserInfoUri']}")
	private String				unityUserInfoUri;

	@Autowired
	private OauthService		oauthService;

	@RequestMapping(value = "authorization_code", method = RequestMethod.GET)
	public String authorizationCode(Model model) {
		model.addAttribute("userAuthorizationUri", userAuthorizationUri);
		model.addAttribute("host", host);
		model.addAttribute("unityUserInfoUri", unityUserInfoUri);
		model.addAttribute("state", UUID.randomUUID().toString());
		return "authorization_code";
	}

	/*
	 * Save state firstly Redirect to oauth-server login page: step-2
	 */
	@RequestMapping(value = "authorization_code", method = RequestMethod.POST)
	public String submitAuthorizationCode(AuthorizationCodeDto codeDto, HttpServletRequest request) throws Exception {
		return oauthService.submitAuthorizationCode(codeDto.getClientId(), codeDto.getScope(),
				codeDto.getClientSecret(), "authorization_code_callback");
	}

	/*
	 * Oauth callback (redirectUri): step-3
	 * 
	 * Handle 'code', go to 'access_token' ,validation oauth-server response
	 * data
	 * 
	 * authorization_code_callback
	 */
	@RequestMapping(value = "authorization_code_callback")
	public void authorizationCodeCallback(AuthCallbackDto callbackDto, HttpServletRequest request, Model model)
			throws Exception {
		Map<String, String> map = oauthService.authorizationCodeCallback(callbackDto);
		System.out.println(map.get("message"));
		System.out.println(map.get("error"));
	}

}
