package core_spring.org.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import core_spring.org.entities.TaskEntity;

@Repository(value = "taskDAO")
@Transactional(rollbackFor = Exception.class)
public class TaskDAO {

	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

	public Long addTask(TaskEntity task) {
		Session session = sessionFactory.openSession();
		Long id;
		// bắt đầu 1 giao dịch
		session.beginTransaction();

		// thực thi câu query dạng hql
		id = (Long) session.save(task);

		// kết thúc 1 giao dịch
		session.getTransaction().commit();

		session.close();

		return id;

	}
	
	public void updateTask(TaskEntity task) {
		Session session = sessionFactory.openSession();
		
		// bắt đầu 1 giao dịch
		session.beginTransaction();

		TaskEntity oldTask = session.get(TaskEntity.class, task.getId());
		// cap nhat
		oldTask.setStatus(task.getStatus());
		oldTask.setTaskContent(task.getTaskContent());
		session.update(oldTask);

		// kết thúc 1 giao dịch
		session.getTransaction().commit();

		session.close();

	}
	
	public TaskEntity getTaskById(Long id) {
	Session session = sessionFactory.openSession();
		
		// bắt đầu 1 giao dịch
		session.beginTransaction();
		TaskEntity task = session.get(TaskEntity.class, id);
		
		// kết thúc 1 giao dịch
		session.getTransaction().commit();

		session.close();
		return task;
	}
	
	public void deleteTask(Long id) {
	Session session = sessionFactory.openSession();
		
		// bắt đầu 1 giao dịch
		session.beginTransaction();

		TaskEntity task = session.get(TaskEntity.class, id);
		if(task != null) session.delete(task);

		// kết thúc 1 giao dịch
		session.getTransaction().commit();

		session.close();
	}
	

}
