package link.reckruter.monolithic;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

	@RequestMapping(method = RequestMethod.GET, path="/ping")
	public String ping() {
		return "pong";
	}

}
