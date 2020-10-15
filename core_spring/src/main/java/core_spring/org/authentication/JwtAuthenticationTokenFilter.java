package core_spring.org.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import core_spring.org.entities.UserEntity;
import core_spring.org.services.JwtService;
import core_spring.org.services.UserService;

@Component
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

	private final static String TOKEN_HEADER = "authorization";

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;
	
	public JwtAuthenticationTokenFilter(UsernamePasswordAuthManager authenticationManager) {
		super(authenticationManager);
	}
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String authToken = request.getHeader(TOKEN_HEADER);
		
		if (authToken == null || authToken.equals("")) {
			chain.doFilter(request, response);
			return;
		}
		
		if (!jwtService.validateTokenLogin(authToken)) {
			throw new RuntimeException();
		}
		
		String username = jwtService.getUsernameFromToken(authToken);
		UserEntity user = userService.findUserByUserName(username);
		if (user == null) {
			throw new RuntimeException();
		}
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, authToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		chain.doFilter(request, response);
	}



	
}