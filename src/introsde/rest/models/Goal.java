package introsde.rest.models;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Goal {

	
	private String type;
	private int value;
	private int idGoal;

	public Goal() {

	}

	public Goal(String type, int value, int idGoal) {
		this.type = type;
		this.value = value;
		this.idGoal = idGoal;
	}
	

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public int getIdGoal() {
		return this.idGoal;
	}
	
	public void setIdGoal(int idGoal) {
		this.idGoal = idGoal;
	}
	
}


