import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import java.net.URISyntaxException;

/**
 * Utility class to load the listings of properties for rental in Airbnb.
 * 
 * @author Fahim Ahmed K1921959,
 * Issa Kabir K19014844,
 * Jehan Bhuyan K19017993,
 * Ork Hossain Muntaqin  K19016476
 */
public class AirbnbDataLoader {
 
	private List<AirbnbListing> properties;
	
	private StatisticCalculator priceStatistics; // Statistics derived from the prices of all properties.
	
	public AirbnbDataLoader() {
		this.properties = new ArrayList<>();
		priceStatistics = new StatisticCalculator();
	}
	
    /** 
     * Return an ArrayList containing the rows in the AirBnB London data set csv file.
     * @param filePath CSV file to load data from
     */
    public void load(String filePath) { // airbnb-london.csv
        System.out.print("Begin loading Airbnb london dataset...");
        try{
            URL url = getClass().getResource(filePath);
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String id = line[0];
                String name = line[1];
                String host_id = line[2];
                String host_name = line[3];   
                String neighbourhood = line[4];      
                double latitude = convertDouble(line[5]);
                double longitude = convertDouble(line[6]);
                String room_type = line[7];
                int price = convertInt(line[8]);
                int minimumNights = convertInt(line[9]);
                int numberOfReviews = convertInt(line[10]);
                String lastReview = line[11];
                double reviewsPerMonth = convertDouble(line[12]);
                int calculatedHostListingsCount = convertInt(line[13]);
                int availability365 = convertInt(line[14]);

                AirbnbListing property = new AirbnbListing(id, name, host_id,
                        host_name, neighbourhood, latitude, longitude, room_type,
                        price, minimumNights, numberOfReviews, lastReview,
                        reviewsPerMonth, calculatedHostListingsCount, availability365
                    );
                properties.add(property);
                priceStatistics.addValue(price);
            }
            
        } catch(IOException | URISyntaxException e) {
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        System.out.println("Success! Number of loaded records: " + properties.size());
    }

    /**
     * Converts a String into a Double.
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is 
     * either empty or just whitespace
     */
    private Double convertDouble(String doubleString){
        if(doubleString != null && !doubleString.trim().equals("")){
            return Double.parseDouble(doubleString);
        }
        return -1.0;
    }

    /**
     * Converts a String into an Integer.
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is 
     * either empty or just whitespace
     */
    private Integer convertInt(String intString){
        if(intString != null && !intString.trim().equals("")){
            return Integer.parseInt(intString);
        }
        return -1;
    }

    /**
     * Returns all properties for rental in Airbnb.
     * @return properties for rental in Airbnb
     */
    public List<AirbnbListing> getProperties() {
		return properties;
	}
    
    /**
     * Returns the statistics derived from the prices of all properties.
     * @return the statistics derived from the prices of all properties
     */
    public StatisticCalculator getPriceStatistics() {
		return priceStatistics;
	}
    
}
