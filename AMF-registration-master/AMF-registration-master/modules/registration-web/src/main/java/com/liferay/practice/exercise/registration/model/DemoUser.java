package com.liferay.practice.exercise.registration.model;

public class DemoUser {
	private String first_name;
	private String last_name;
	
	public DemoUser() {
		   this.first_name = null;
		   this.last_name = null;
		}

		public DemoUser(String first_name, String last_name) {
		   setFirst_name(first_name);
		   setLast_name(last_name);
		}

		public String getFirst_name() {
			return first_name;
		}

		public void setFirst_name(String first_name) {
			this.first_name = first_name;
		}

		public String getLast_name() {
			return last_name;
		}

		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}


}
