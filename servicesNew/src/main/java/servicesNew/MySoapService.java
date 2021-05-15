package servicesNew;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public class MySoapService {
	
	@WebMethod
	@WebResult(name = "HelloMessage")
	public String Hello1(String Name) {
		return "Hello, " + (Name!=null ? Name : "World") + "!";
 	}

}
