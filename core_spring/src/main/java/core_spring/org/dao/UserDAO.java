package core_spring.org.dao;



import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import core_spring.org.entities.TaskEntity;
import core_spring.org.entities.UserEntity;

@Repository(value = "userDAO")
@Transactional(rollbackFor = Exception.class)
public class UserDAO {

	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

	public UserDAO() {
		super();
	}

	public Long save(UserEntity user) {
		Session session = sessionFactory.openSession();
		Long id = null;

		// bắt đầu 1 giao dịch
		session.beginTransaction();

		// thực thi câu query dạng hql
		id = (Long) session.save(user);

		// kết thúc 1 giao dịch
		session.getTransaction().commit();

		session.close();

		return id;
	}

	public UserEntity findById(Long id) {
		// Session session = this.sessionFactory.getCurrentSession();
		Session session = sessionFactory.openSession();
		// bắt đầu 1 giao dịch
		session.beginTransaction();

		UserEntity user = session.get(UserEntity.class, id);

		// kết thúc 1 giao dịch
		session.getTransaction().commit();
		
		if(user != null) Hibernate.initialize(user.getTask());

		session.close();
		
		return user;
	}
	
	public void updateUser(UserEntity user) {
		// Session session = this.sessionFactory.getCurrentSession();
		Session session = sessionFactory.openSession();
		// bắt đầu 1 giao dịch
		session.beginTransaction();
		
		//laay user cu
		UserEntity oldUser = session.get(UserEntity.class, user.getId());
		// cap nhat
		oldUser.setPassword(user.getPassword());
		oldUser.setUserName(user.getUserName());
		session.update(oldUser);

		// kết thúc 1 giao dịch
		session.getTransaction().commit();

		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public boolean isUserNameExist(String userName) {
		
		String hql = "FROM UserEntity WHERE userName = :userName";
		
		Session session = sessionFactory.openSession();		

		// bắt đầu 1 giao dịch
		session.beginTransaction();		
		Query<UserEntity> query = session.createQuery(hql);
		query.setParameter("userName", userName);
		List<UserEntity> results = query.list();
		
		// kết thúc 1 giao dịch
		session.getTransaction().commit();

		session.close();

		
		if(results.size() != 0 ) return true; else return false;
		
	}
	
	@SuppressWarnings("unchecked")
	public UserEntity findUserByUserName(String userName) {
		
		String hql = "FROM UserEntity WHERE userName = :userName";
		
		Session session = sessionFactory.openSession();		

		// bắt đầu 1 giao dịch
		session.beginTransaction();		
		Query<UserEntity> query = session.createQuery(hql);
		query.setParameter("userName", userName);
		List<UserEntity> results = query.list();
		
		// kết thúc 1 giao dịch
		session.getTransaction().commit();

		session.close();

		
		if(results.size() == 0 ) return null; else return results.get(0);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskEntity> getAllTask(UserEntity user) {
		String hql = "FROM TaskEntity WHERE user = :user";
		Session session = sessionFactory.openSession();
		
		// bắt đầu 1 giao dịch
		session.beginTransaction();		
		Query<TaskEntity> query = session.createQuery(hql);
		query.setParameter("user", user);
		List<TaskEntity> results = query.list();
				
		// kết thúc 1 giao dịch
		session.getTransaction().commit();


		session.close();		
		
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserEntity> getAllUser() {
		String hql = "FROM UserEntity";
		Session session = sessionFactory.openSession();
		
		// bắt đầu 1 giao dịch
		session.beginTransaction();		
		Query<UserEntity> query = session.createQuery(hql);
		List<UserEntity> results = query.list();
				
		// kết thúc 1 giao dịch
		session.getTransaction().commit();


		session.close();		
		
		return results;
	}
	
	

}
