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
    private int TotNumberOfHomeOrApt;
    private String mostExpensiveBorough;
    private String cheapestBorough;
    private String mostReviewedBorough;
    private String cheapestBoroughDescription;
    private String mostExpensiveName;
    private String expHostName;
    private String cheapHostName;
    
    private double reviewedNumber;

    public Controller(List<AirbnbListing> properties) {
        allProperties = properties;
        boroughs = new HashMap<>();
    }

    /**
     * This method's use is to do the price range selection.
     * It
     */
    public void processRange() {
        averageReviews = 0;
        TotNumberOfHomeOrApt = 0;
        
        this.properties = allProperties.stream().filter(property -> property.getPrice() >= startPrice && property.getPrice() <= endPrice).collect(Collectors.toList());

        properties.stream().forEach(property -> {
                String neighbourhood = property.getNeighbourhood();

                if (!boroughs.containsKey(neighbourhood)) {
                    boroughs.put(neighbourhood, new Borough(neighbourhood));
                }
                boroughs.get(neighbourhood).addProperty(property);

            });
        int reviews = 0;
        int mostExpensive = 0, mostCheap = 0, cheapName = 0, expensiveName = 0;
        List<Integer> prices = new ArrayList<>();
        for (AirbnbListing property : properties) {
            reviews = reviews + property.getNumberOfReviews();
            if(property.getRoom_type().equals("Entire home/apt"))
            {
                TotNumberOfHomeOrApt++;
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
                cheapestBoroughDescription = property.getName();
                cheapHostName = property.getHost_name();
            }
            if(sortedlist.get(sortedlist.size() - 1) == (property.getPrice() * property.getMinimumNights()))
            {
                mostExpensiveBorough = property.getNeighbourhood();
                mostExpensiveName = property.getName();
                expHostName = property.getHost_name();
            }
        }

        averageReviews = reviews/properties.size();
        boroughStatistics = new StatisticCalculator();
        for (Borough borough : boroughs.values()) {
            boroughStatistics.addValue(borough.getNumberOfProperties());
        }

    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    public Map<String, Borough> getBoroughs() {
        return boroughs;
    }

    public StatisticCalculator getBoroughStatistics() {
        return boroughStatistics;
    }

    public int getAvailableProperties() {
        return properties.size();   //Accessor Method to get number of properties available in selected price range
    }

    public int getAverageReviews() {
        return averageReviews;  //Accessor Method to get average reviews in the selected price range
    }

    public int getHomeApartments() {
        return TotNumberOfHomeOrApt;    //Accessor Method to get total number of Home/Appartment type
    }

    public String getMostExpensiveBorough() {
        return mostExpensiveBorough;    //Accessor Method to get Borough with most expensive price
    }

    public String getCheapestBorough() {
        return cheapestBorough; //Accessor Method to get Borough with cheapest price
    }

    public String getMostExpensiveDescription() {
        return mostExpensiveName;   //Accessor method to get Name/Property Description of the most expensive price
    }

    public String getCheapestBoroughDescription() {
        return cheapestBoroughDescription;  //Accessor method to get Name/Property Description of the cheapest price
    }

    public String getMostReviewedBorough() {
        return mostReviewedBorough; //Accessor Method to get Borough with most reviewed
    }
    
    public String getCheapestHost() {
        return cheapHostName;   //Accessor Method to get Host Name with cheapest price
    }

    public String getExpensiveHost() {
        return expHostName; //Accessor Method to get Host Name with most expensive price
    }

}
