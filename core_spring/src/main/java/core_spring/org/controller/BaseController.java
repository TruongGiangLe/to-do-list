package core_spring.org.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import core_spring.org.entities.UserEntity;
import core_spring.org.services.JwtService;
import core_spring.org.services.UserService;


@RestController
public class BaseController {
	
	private UserService userService;
	private JwtService jwtService;

	public BaseController(UserService userService, JwtService jwtService) {
		super();
		this.userService = userService;
		this.jwtService = jwtService;
	}

	/* ---------------- LOGIN USER ------------------------ */
	@PostMapping("/login")
	public String userLogin(@RequestBody UserEntity user) {
		String token = "";
		String userName = user.getUserName();
		String password = user.getPassword();
		UserEntity checkUser = userService.findUserByUserName(userName);
		if(checkUser == null) {
			return "account is not existed!";
		}
		
		if(!checkUser.getPassword().equals(password)) {
			return "your password isn't correct!";
		}
		
		token = jwtService.generateTokenLogin(userName);
		return token;
	}
	
	/* ---------------- CREATE NEW USER ------------------------ */
	@PostMapping("/create_user")
	public String createUser(@RequestBody UserEntity user) {
		UserEntity newUser = new UserEntity(user.getUserName(), user.getPassword());
		if (!userService.isUserNameExist(newUser.getUserName())) {
			userService.save(newUser);
			return "created successfully!";
		} 
		
		return "your user name has been used!";

	}
}
