package core_spring.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import core_spring.org.entities.UserEntity;
import core_spring.org.services.JwtService;
import core_spring.org.services.UserService;


@Controller
public class BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;

	/* ---------------- LOGIN USER ------------------------ */
	@RequestMapping(value = "/login", headers = "Accept=application/json", method = RequestMethod.POST)
	public @ResponseBody String userLogin(@RequestBody UserEntity user) {
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
	@RequestMapping(value = "/create_user", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String createUser(@RequestBody UserEntity user) {
		UserEntity newUser = new UserEntity(user.getUserName(), user.getPassword());
		if (!userService.isUserNameExist(newUser.getUserName())) {
			userService.save(newUser);
			return "created successfully!";
		} 
		
		return "your user name has been used!";

	}
}
