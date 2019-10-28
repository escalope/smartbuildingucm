package ucm.dii;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.w3c.dom.events.EventTarget;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.management.Notification;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
		 
	 // based on the example
	 // from https://blog.itkonekt.com/2018/06/27/server-sent-events-in-spring/
	 private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

	 /**
	  * It returns a W3C SSE emitter to handle server side event generation.
	  * There is an open connection to the client that can be processed with 
	  * specific javascript
	  * @return
	  */
     @GetMapping("/building/temprt")
     public SseEmitter getNewNotification() {
    	 	 // create a server side event emitter
             SseEmitter emitter = new SseEmitter();
             this.emitters.add(emitter);

             // kill the emitter either on completion or timeout
             emitter.onCompletion(() -> this.emitters.remove(emitter));
             emitter.onTimeout(() -> {
                     emitter.complete(); 
                     this.emitters.remove(emitter);
                     System.out.println("emitter removed");
             });

             return emitter;
     }

     /**
      * This method is invoked by the SensorReaderDevice everytime to notify events.
      * Binding is done in runtime by looking methods that use the @eventlistener annotation
      * and the same object parameter type. If there are multiple methods, each one of them
      * is invoked 
      * @param notification
      */
     @EventListener
     public void onNotification(SensorNotification notification) {
    	 System.out.println("enviado");
             List<SseEmitter> deadEmitters = new ArrayList<>();
             this.emitters.forEach(emitter -> {
                     try {
                            emitter.send(notification);
                     } catch (Exception e) {
                            deadEmitters.add(emitter);
                     }
             });
             this.emitters.remove(deadEmitters);
     }
     


}
