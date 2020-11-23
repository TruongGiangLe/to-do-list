package core_spring.org.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import core_spring.org.entities.TaskEntity;
import core_spring.org.entities.UserEntity;
import core_spring.org.services.JwtService;
import core_spring.org.services.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {
	
	private UserService userService;
	
	
	public UserController(UserService userService, JwtService jwtService) {
		super();
		this.userService = userService;
	}


	//ObjectMapper mapper = new ObjectMapper();

	@RequestMapping("/welcome")
	public ModelAndView helloWorld() {

		String message = "<br><div style='text-align:center;'>" + "<h3>********************</div><br><br>";
		return new ModelAndView("welcome", "message", message);
	}


	/* ---------------- UPDATE USER ------------------------ */
	@PutMapping("/update_user")
	public String updateUser(
			Authentication authentication,
			@RequestBody UserEntity newUser
	) {
		UserEntity oldUser = (UserEntity) authentication.getPrincipal();
		newUser.setId(oldUser.getId());
		userService.updateUser(newUser);
		return "updated successfully";
		
	}
	
	/* ---------------- GET ALL TASKS ------------------------ */
	@GetMapping("/get_all_task")
	public List<TaskEntity> getAllTask( 
			Authentication authentication) {
		UserEntity tempUser = (UserEntity) authentication.getPrincipal();
		return userService.getAllTask(tempUser);
	}
	
	/* ---------------- GET TASKS BY STATUS ------------------------ */
	@GetMapping("/get_tasks_by_status/to_do")
	public List<TaskEntity> getToDoTask(Authentication authentication) {
		UserEntity user = (UserEntity) authentication.getPrincipal();
		return userService.getAllTask(user).stream()
				.filter(t -> t.getStatus().equals("to do"))
				.collect(Collectors.toList());
	}
	
	/* ---------------- GET TASKS BY STATUS ------------------------ */
	@GetMapping("/get_tasks_by_status/finished")
	public List<TaskEntity> getFinishedTask(Authentication authentication) {
		UserEntity user = (UserEntity) authentication.getPrincipal();
		return userService.getAllTask(user).stream()
		.filter(t -> t.getStatus().equals("finished"))
		.collect(Collectors.toList());
	}
	
	/* ---------------- GET ALL USERS ------------------------ */
	@GetMapping("/get_all_user")
	public List<UserEntity> getAllUser() {
		return userService.getAllUser();
	}
	
	/* ---------------- GET USERS PROFILE ------------------------ */
	@GetMapping("/get_user_profile") 
	public UserEntity getUserProfile(Authentication authentication) {
		return (UserEntity) authentication.getPrincipal();
	}

}
