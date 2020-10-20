package core_spring.org.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String userName;
    
    @Column
    private String password;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("id")
    @JsonIgnore
    private Set<TaskEntity> task;

	public UserEntity(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
    
	public UserEntity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<TaskEntity> getTask() {
		return task;
	}

	public void setTask(Set<TaskEntity> task) {
		this.task = task;
	}
	
	
    
    
}