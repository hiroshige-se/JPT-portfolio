package bean;

public class UserFriends implements java.io.Serializable {
	private int user_id;
	private String user_name;
	private int friends_id;
	private String friends_name;
	private int apply_number;
	private int apply_status;
	private String last_update;
	private int reject_counter;

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
	public int getFriends_id() {
		return friends_id;
	}
	public void setFriends_id(int friends_id) {
		this.friends_id = friends_id;
	}
	public String getFriends_name() {
		return friends_name;
	}
	public void setFriends_name(String friends_name) {
		this.friends_name = friends_name;
	}
	public int getApply_number() {
		return apply_number;
	}
	public void setApply_number(int apply_number) {
		this.apply_number = apply_number;
	}
	public int getApply_status() {
		return apply_status;
	}
	public void setApply_status(int apply_status) {
		this.apply_status = apply_status;
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
	public int getReject_counter() {
		return reject_counter;
	}
	public void setReject_counter(int reject_counter) {
		this.reject_counter = reject_counter;
	}
}