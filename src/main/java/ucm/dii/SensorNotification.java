package ucm.dii;

import java.io.Serializable;
import java.util.Date;

/**
 * Event example. 
 * 
 * @author superjj
 *
 */
public class SensorNotification implements Serializable{

	public SensorNotification(Float value, Date time) {
		super();  
		this.value = value;
		this.timestamp = time;
	} 

	private Float value; 
	private Date timestamp;
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date time) {
		this.timestamp = time;
	} 

}
