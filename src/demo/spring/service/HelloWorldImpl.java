package demo.spring.service;

import javax.jws.WebService;

@WebService(endpointInterface = "demo.spring.service.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

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

	private String userName;
	private String password;
	
	public String sayHi(String text) {
		System.out.println("sayHi called");
		return "Hello " + text;
	}
	
	public void showUserInfo(){
		System.out.println("User Name:" +this.userName);
		System.out.println("Password:" +this.password);
	}
}
