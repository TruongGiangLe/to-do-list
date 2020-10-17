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
	
	public TaskEntity updateTask(TaskEntity task) {
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

}
