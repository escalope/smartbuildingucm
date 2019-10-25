package ucm.dii;

import java.util.Date;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * This creates a service that launches periodically a method that is responsible of generating
 * notifications. It could be used to read a sensor data source and deliver it to clients.
 * 
 * The @service annotation makes this class be created during the main app execution
 * The @enablescheduling annotation makes the interpreter execute methods with @scheduled annotation
 * 
 * @author Jorge Gomez-Sanz
 *
 */
@Service
@EnableScheduling
public class SensorReaderService {

        public final ApplicationEventPublisher eventPublisher;
        
      
        public SensorReaderService(ApplicationEventPublisher eventPublisher) {
                this.eventPublisher = eventPublisher;
        }

        /**
         * A scheduled method must return void always. This method simulates the generation 
         * of events after 2 secs and then each 4 secs
         * Based on  https://blog.itkonekt.com/2018/06/27/server-sent-events-in-spring/
         * @throws InterruptedException
         */
        @Scheduled(fixedRate = 4000, initialDelay = 2000)
        public void publishJobNotifications() throws InterruptedException {
                // It simulates an Event lecture
                SensorNotification nStarted = new SensorNotification(new Float(Math.random()), new Date());
                this.eventPublisher.publishEvent(nStarted);
                Thread.sleep(2000);
                SensorNotification nFinished = new SensorNotification(new Float(Math.random()), new Date());
                this.eventPublisher.publishEvent(nFinished);
        }
}