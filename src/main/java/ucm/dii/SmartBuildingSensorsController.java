package ucm.dii;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class SmartBuildingSensorsController {

	private final String code1="micasa" ;
	private final String code2="tucasa" ;
	
	@RequestMapping("/building/temp")
	public double getBuildingTemperature() {
		return Math.random()*40;
	}

	@RequestMapping("/house/temp/{id}")
	public double getApartmentTemperature(@PathVariable("id") String id) {
		if (id.equals(code1))
			return 20f*Math.random();
		else 
			if (id.equals(code2))
				return  30f*Math.random();
			return -1;
	}


}
