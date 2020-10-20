package core_spring.org.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import core_spring.org.entities.TaskEntity;
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


	/* ---------------- UPDATE USER ------------------------ */
	@RequestMapping(value = "/update_user", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody String updateUser(@RequestBody UserEntity user, @RequestHeader("Authorization") String token) {
		UserEntity oldUser = null;
		if (!jwtService.validateTokenLogin(token)) return "unauthorization!";
		
		String userName = jwtService.getUsernameFromToken(token);
		oldUser = userService.findUserByUserName(userName);
		
		if(oldUser == null) return "unauthorization!";
		
		user.setId(oldUser.getId());
		userService.updateUser(user);
		
		return "updated successfully";
		
	}
	
	/* ---------------- GET ALL TASKS ------------------------ */
	@RequestMapping(value = "/get_all_task", headers = "Accept=application/json", method = RequestMethod.GET)
	public @ResponseBody List<TaskEntity> getAllTask(@RequestBody UserEntity user) {
		UserEntity realUser = userService.findById(user.getId());
		return userService.getAllTask(realUser);
	}
	
	/* ---------------- GET TASKS BY STATUS ------------------------ */
	@RequestMapping(value = "/get_tasks_by_status/to_do", headers = "Accept=application/json", method = RequestMethod.GET)
	public @ResponseBody List<TaskEntity> getToDoTask(@RequestBody UserEntity user) {
		UserEntity realUser = userService.findById(user.getId());
		List<TaskEntity> allTask = userService.getAllTask(realUser);
		List<TaskEntity> toDoTask = new LinkedList<TaskEntity>();
		for(int i = 0; i < allTask.size(); i++) {
			TaskEntity task = allTask.get(i);
			if(task.getStatus().equals("to do")) toDoTask.add(task);
		}
		return toDoTask;
	}
	
	/* ---------------- GET TASKS BY STATUS ------------------------ */
	@RequestMapping(value = "/get_tasks_by_status/finished", headers = "Accept=application/json", method = RequestMethod.GET)
	public @ResponseBody List<TaskEntity> getFinishedTask(@RequestBody UserEntity user) {
		UserEntity realUser = userService.findById(user.getId());
		List<TaskEntity> allTask = userService.getAllTask(realUser);
		List<TaskEntity> finishedTask = new LinkedList<TaskEntity>();
		for(int i = 0; i < allTask.size(); i++) {
			TaskEntity task = allTask.get(i);
			if(task.getStatus().equals("finished")) finishedTask.add(task);
		}
		return finishedTask;
	}
	
	/* ---------------- GET ALL USERS ------------------------ */
	@RequestMapping(value = "/get_all_user", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<UserEntity> getAllUser() {
		return userService.getAllUser();
	}
	
	

}
