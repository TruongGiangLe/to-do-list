package core_spring.org.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core_spring.org.dao.TaskDAO;
import core_spring.org.entities.TaskEntity;

@Service
@Transactional
public class TaskService {
	 @Autowired
	  private TaskDAO taskDAO;
	 
	 public Long addTask(TaskEntity task) {
		 return taskDAO.addTask(task);
	 }
	
}
