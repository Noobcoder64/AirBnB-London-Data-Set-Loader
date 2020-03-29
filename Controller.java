import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Collections;

public class Controller {

    private List<AirbnbListing> allProperties;

    private List<AirbnbListing> properties;

    private Map<String,Borough> boroughs;

    private StatisticCalculator boroughStatistics;

    private int startPrice;
    private int endPrice;

    private int averageReviews;
    private int totalNumberOfHomeOrApt;
    private String mostExpensiveBorough;
    private String cheapestBorough;
    private String mostReviewedBorough;
    private String cheapestPropertyDescription;
    private String mostExpensiveName;
    private String mostExpensiveHostName;
    private String cheapestHostName;
    
    private double reviewedNumber;

    public Controller(List<AirbnbListing> properties) {
    	allProperties = properties;
        boroughs = new HashMap<>();
    }

    /**
     * This method's use is to do the price range selection.
     */
    public void processRange() {
    	
        this.properties = allProperties.stream().filter(property -> property.getPrice() >= startPrice && property.getPrice() <= endPrice).collect(Collectors.toList());

        properties.stream().forEach(property -> {
                String neighbourhood = property.getNeighbourhood();

                if (!boroughs.containsKey(neighbourhood)) {
                    boroughs.put(neighbourhood, new Borough(neighbourhood));
                }
                boroughs.get(neighbourhood).addProperty(property);

        });
        
        int reviews = 0;
        List<Integer> prices = new ArrayList<>();
        for (AirbnbListing property : properties) {
            reviews = reviews + property.getNumberOfReviews();
            if(property.getRoom_type().equals("Entire home/apt"))
            {
                totalNumberOfHomeOrApt++;
            }
            if(property.getReviewsPerMonth() > reviewedNumber){
                reviewedNumber = property.getReviewsPerMonth();
                mostReviewedBorough = property.getNeighbourhood();
            }
            prices.add(property.getPrice() * property.getMinimumNights());
        }

        List<Integer> sortedlist = new ArrayList<>(prices);
        Collections.sort(sortedlist);

        for (AirbnbListing property : properties) 
        {
            if(sortedlist.get(0) == (property.getPrice() * property.getMinimumNights()))
            {
                cheapestBorough = property.getNeighbourhood();
                cheapestPropertyDescription = property.getName();
                cheapestHostName = property.getHost_name();
            }
            if(sortedlist.get(sortedlist.size() - 1) == (property.getPrice() * property.getMinimumNights()))
            {
                mostExpensiveBorough = property.getNeighbourhood();
                mostExpensiveName = property.getName();
                mostExpensiveHostName = property.getHost_name();
            }
        }

        averageReviews = reviews/properties.size();
        boroughStatistics = new StatisticCalculator();
        for (Borough borough : boroughs.values()) {
            boroughStatistics.addValue(borough.getNumberOfProperties());
        }

    }

    /**
     * Sets the starting price.
     * @param startPrice
     */
    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    /**
     * Sets the ending price.
     * @param startPrice
     */
    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    /**
     * Returns the boroughs.
     * @return the boroughs
     */
    public Map<String, Borough> getBoroughs() {
        return boroughs;
    }

    /**
     * Returns the statistics about a bourough.
     * @return the statistics about a bourough.
     */
    public StatisticCalculator getBoroughStatistics() {
        return boroughStatistics;
    }

    /**
     * Accessor Method to get number of properties available in selected price range
     */
    public int getAvailableProperties() {
        return properties.size();
    }

    /**
     * Accessor Method to get average reviews in the selected price range
     */
    public int getAverageReviews() {
        return averageReviews;
    }

    /**
     * Accessor Method to get total number of Home/Appartment type
     */
    public int getHomeApartments() {
        return totalNumberOfHomeOrApt;
    }

    /**
     * Accessor Method to get Borough with most expensive price
     */
    public String getMostExpensiveBorough() {
        return mostExpensiveBorough;
    }

    /**
     * Accessor Method to get Borough with cheapest price
     */
    public String getCheapestBorough() {
        return cheapestBorough;
    }

    /**
     * Accessor method to get Name/Property Description of the most expensive price
     */
    public String getMostExpensiveDescription() {
        return mostExpensiveName;
    }

    /**
     * Accessor method to get Name/Property Description of the cheapest price
     */
    public String getCheapestBoroughDescription() {
        return cheapestPropertyDescription;
    }

    /**
     * Accessor Method to get Borough with most reviewed
     */
    public String getMostReviewedBorough() {
        return mostReviewedBorough;
    }
    
    /**
     * Accessor Method to get Host Name with cheapest price
     */
    public String getCheapestHost() {
        return cheapestHostName;
    }

    /**
     * Accessor Method to get Host Name with most expensive price
     */
    public String getExpensiveHost() {
        return mostExpensiveHostName;
    }

}
