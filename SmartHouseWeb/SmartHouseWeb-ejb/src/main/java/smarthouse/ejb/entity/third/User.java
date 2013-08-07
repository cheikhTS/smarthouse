package smarthouse.ejb.entity.third;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.sql.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.Length;

import smarthouse.ejb.entity.listener.PasswordListener;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@EntityListeners(PasswordListener.class)
public class User implements Serializable {

	@Id
	private Integer id;
	private String username;
	private String password;
	private Date last_connection;
	private String last_ip;
	private static final long serialVersionUID = 1L;
	
	// non entity field
	private transient String clearPassword;

	public User() {
		super();
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(length = 50)
	@Length(max = 50)
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	} 
	
	@Column(length = 200)
	@Length(max = 200)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}   
	
	public Date getLast_connection() {
		return this.last_connection;
	}
	public void setLast_connection(Date last_connection) {
		this.last_connection = last_connection;
	}   
	
	@Column(length = 40)
	@Length(max = 40)
	public String getLast_ip() {
		return this.last_ip;
	}
	public void setLast_ip(String last_ip) {
		this.last_ip = last_ip;
	}
	
	public String getClearPassword() {
		return clearPassword;
	}
	public void setClearPassword(String clearPassword) {
		this.clearPassword = clearPassword;
	}

}
