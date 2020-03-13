import java.util.ArrayList;
import java.util.List;

public class Borough {

	private String name;
	
	private List<AirbnbListing> properties;
	
	public Borough(String name) {
		this.name = name;
		this.properties = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addProperty(AirbnbListing property) {
		this.properties.add(property);
	}
	
	public int getNumberOfProperties() {
		return properties.size();
	}
	
}
