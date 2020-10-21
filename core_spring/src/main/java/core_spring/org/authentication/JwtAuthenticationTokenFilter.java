package core_spring.org.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import core_spring.org.entities.UserEntity;
import core_spring.org.services.JwtService;
import core_spring.org.services.UserService;

public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

	private final static String TOKEN_HEADER = "Authorization";

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomAccessDeniedHandler deniedHandler;
	
	
	
	public JwtAuthenticationTokenFilter(UsernamePasswordAuthManager authenticationManager) {
		super(authenticationManager);
	}
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String authToken = request.getHeader(TOKEN_HEADER);
		
		if (authToken == null || authToken.equals("") || !jwtService.validateTokenLogin(authToken)) {
			deniedHandler.handle(request, response, null);
			return;
		}
		
		String username = jwtService.getUsernameFromToken(authToken);
		UserEntity user = userService.findUserByUserName(username);
		if (user == null) {
			deniedHandler.handle(request, response, null);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, authToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		chain.doFilter(request, response);
	}



	
}