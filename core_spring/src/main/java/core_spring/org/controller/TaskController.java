package core_spring.org.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core_spring.org.entities.TaskEntity;
import core_spring.org.entities.UserEntity;
import core_spring.org.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
	private TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	/* ---------------- CREATE NEW TASK ------------------------ */
	@PostMapping("/add_new_task")
	public TaskEntity addNewTask(
			@RequestBody TaskEntity task, 
			Authentication authentication) {
		UserEntity user = (UserEntity) authentication.getPrincipal();
		TaskEntity newTask = new TaskEntity();
		newTask.setStatus("to do");
		newTask.setTaskContent(task.getTaskContent());
		newTask.setUser(user);
		return taskService.addTask(newTask);
	}

	/* ---------------- UPDATE TASK ------------------------ */
	@PutMapping("/update_task")
	public String updateTask(@RequestBody TaskEntity task) {
		taskService.updateTask(task);
		return "updated successfully!";
	}

	/* ---------------- DELETE TASK ------------------------ */
	@DeleteMapping("/delete_task/{taskId}")
	public String deleteTask(@PathVariable Long taskId) {
		taskService.deleteTask(taskId);
		return "deleted successfully!";
	}

}
