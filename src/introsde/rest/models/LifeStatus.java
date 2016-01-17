package introsde.rest.models;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LifeStatus {

	
	private String measureName;
	private String value;

	public LifeStatus() {

	}

	public LifeStatus(String measureName, String value) {
		this.measureName = measureName;
		this.value = value;
	}
	

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	public String getMeasureName() {
		return this.measureName;
	}
	
	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}
	
}


