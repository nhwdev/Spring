package lombokstudy;

//import jdk.jshell.JShell.Builder;

public class Ex02_User {
	private String id;
	private String pw;
	public Ex02_User(Builder builder) {
		this.id = builder.id;
		this.pw = builder.pw;
	}
	public static Builder builder() {
		return new Builder();
	}
	public String toString() {
		return "User [id=" + id + ",pw=" + pw + "]";
	}
	public static class Builder{
		private String id;
		private String pw;
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		public Builder pw(String pw) {
			this.pw = pw;
			return this;
		}
		public Ex02_User builder() {
			return new Ex02_User(this);
		}
	}
}
