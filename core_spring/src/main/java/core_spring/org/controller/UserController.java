package core_spring.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import core_spring.org.entities.UserEntity;
import core_spring.org.services.JwtService;
import core_spring.org.services.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping("/welcome")
	public ModelAndView helloWorld() {

		String message = "<br><div style='text-align:center;'>" + "<h3>********************</div><br><br>";
		return new ModelAndView("welcome", "message", message);
	}

	/* ---------------- CREATE NEW USER ------------------------ */
	@RequestMapping(value = "/create_user", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String createUser(@RequestBody UserEntity user) {
		UserEntity newUser = new UserEntity(user.getUserName(), user.getPassword());
		if (!userService.isUserNameExist(newUser.getUserName())) {
			userService.save(newUser);
			return "created successfully!";
		} else
			return "your user name has been used!";

	}

	/* ---------------- UPDATE USER ------------------------ */
	@RequestMapping(value = "/update_user", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody String updateUser(@RequestBody UserEntity user) {
		if (!userService.isUserNameExist(user.getUserName())) {
			userService.updateUser(user);
			return "updated successfully";
		} else return "your user name has been used!";
	}

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
		//SessionUtils.getInstance().putValue(request, key, value);
		return token;
	}

}
