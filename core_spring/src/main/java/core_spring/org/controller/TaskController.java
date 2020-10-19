package core_spring.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import core_spring.org.entities.TaskEntity;
import core_spring.org.entities.UserEntity;
import core_spring.org.services.JwtService;
import core_spring.org.services.TaskService;
import core_spring.org.services.UserService;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;

	/* ---------------- CREATE NEW TASK ------------------------ */
	@RequestMapping(value = "/add_new_task", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String addNewTask(@RequestBody TaskEntity task, @RequestHeader("Authorization") String token) {
		String userName = jwtService.getUsernameFromToken(token);
		UserEntity user = userService.findUserByUserName(userName);
		
		TaskEntity newTask = new TaskEntity();
		newTask.setStatus("to do");
		newTask.setTaskContent(task.getTaskContent());
		newTask.setUser(user);
		taskService.addTask(newTask);
		return "added successfully!";
	}
	
	/* ---------------- UPDATE TASK ------------------------ */
	@RequestMapping(value = "/update_task", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody String updateTask(@RequestBody TaskEntity task) {
		taskService.updateTask(task);
		return "updated successfully!";
	}
	
	/* ---------------- DELETE TASK ------------------------ */
	@RequestMapping(value = "/delete_task", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody String deleteTask(@RequestBody TaskEntity task) {
		taskService.deleteTask(task.getId());
		return "deleted successfully!";
	}
	
	
	
	
}
