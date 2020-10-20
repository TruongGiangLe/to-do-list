package core_spring.org.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core_spring.org.dao.UserDAO;
import core_spring.org.entities.TaskEntity;
import core_spring.org.entities.UserEntity;

@Service
@Transactional
public class UserService {
	
	 @Autowired
	  private UserDAO userDAO;
	 
	  public Long save(UserEntity user) {
		  return userDAO.save(user);
		  }
	  
	  public UserEntity findById(Long id) {
		  return userDAO.findById(id);
	  }
	  
	  public void updateUser(UserEntity user) {
		  userDAO.updateUser(user);
	  }
	  
	  public boolean isUserNameExist(String userName) {
		  return userDAO.isUserNameExist(userName);
	  }
	  
	  public UserEntity findUserByUserName(String userName) {
		  return userDAO.findUserByUserName(userName);
	  }
	  
	  public List<TaskEntity> getAllTask(UserEntity user) {
		  return userDAO.getAllTask(user);
	  }
	  
	  public List<UserEntity> getAllUser() {
		  return userDAO.getAllUser();
	  }
}
