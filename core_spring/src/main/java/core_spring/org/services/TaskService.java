package core_spring.org.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core_spring.org.dao.TaskDAO;
import core_spring.org.entities.TaskEntity;

@Service
@Transactional
public class TaskService {
	 
	private final TaskDAO taskDAO;
	 	 
	public TaskService(TaskDAO taskDAO) {
			 this.taskDAO = taskDAO;
	}
	 
	 public TaskEntity addTask(TaskEntity task) {
		 return taskDAO.addTask(task);
	 }
	 
	 public void updateTask(TaskEntity task) {
		 taskDAO.updateTask(task);
	 }
	
	 public void deleteTask(Long id) {
		 taskDAO.deleteTask(id);
	 }
}
