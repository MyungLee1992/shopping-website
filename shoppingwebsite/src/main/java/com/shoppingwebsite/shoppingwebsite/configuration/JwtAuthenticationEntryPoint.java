package com.shoppingwebsite.shoppingwebsite.configuration;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException {

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		String message;
		// Check if the request as any exception that we have stored in Request
		final Exception exception = (Exception) request.getAttribute("exception");

		// If yes then use it to create the response message else use the authException
		if (exception != null) {
			message = exception.toString();
		} else {
			if (authException.getCause() != null) {
				message = authException.getCause().toString() + " " + authException.getMessage();
			} else {
				message = authException.getMessage();
			}
		}

		byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
		response.getOutputStream().write(body);
	}
}
