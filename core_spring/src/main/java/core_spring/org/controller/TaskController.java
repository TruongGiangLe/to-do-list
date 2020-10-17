package core_spring.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import core_spring.org.entities.TaskEntity;
import core_spring.org.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;

	/* ---------------- CREATE NEW TASK ------------------------ */
	@RequestMapping(value = "/add_new_task", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String addNewTask(@RequestBody TaskEntity task) {
		TaskEntity newTask = new TaskEntity(task.getId(), task.getTaskContent(), task.getStatus(), task.getUser());
		taskService.addTask(newTask);
		return "added successfully!";
	}
	
	/* ---------------- UPDATE TASK ------------------------ */
	@RequestMapping(value = "/update_task", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody String updateTask(@RequestBody TaskEntity task) {
		TaskEntity newTask = new TaskEntity(task.getId(), task.getTaskContent(), task.getStatus(), task.getUser());
		taskService.addTask(newTask);
		return "added successfully!";
	}
	
	
	
	
}
