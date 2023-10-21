package bean;

public class User implements java.io.Serializable {
	private int user_id;
	private String user_name;
	private String password;
	private String last_update;
	private int number_of_friends;

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLast_update() {
		return last_update;
	}
	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
	public int getNumber_of_friends() {
		return number_of_friends;
	}
	public void setNumber_of_friends(int number_of_friends) {
		this.number_of_friends = number_of_friends;
	}
}