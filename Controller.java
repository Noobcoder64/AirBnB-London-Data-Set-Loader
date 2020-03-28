import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.util.Collections;

public class Controller {

    AirbnbDataLoader airbnbDataLoader;

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

    public Controller() {
        airbnbDataLoader = new AirbnbDataLoader();
        airbnbDataLoader.load("airbnb-london.csv");
        boroughs = new HashMap<>();

        allProperties = airbnbDataLoader.getProperties();
    }

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
        int mostExpensive = 0, mostCheap = 0, cheapName = 0, expensiveName = 0;
        List<Integer> prices = new ArrayList<>();
        for (AirbnbListing property : properties) {
            reviews = reviews + property.getNumberOfReviews();
            if(property.getRoom_type().equals("Entire home/apt"))
            {
                TotNumberOfHomeOrApt++;
            }
            // if((property.getPrice() * property.getMinimumNights()) > mostExpensive){
            // mostExpensive = property.getPrice() * property.getMinimumNights();
            // mostExpensiveBorough = property.getNeighbourhood();
            // mostExpensiveName = property.getName();
            // }
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

    public StatisticCalculator getAllPriceStatistics() {
        return airbnbDataLoader.getPriceStatistics();
    }

    public StatisticCalculator getBoroughStatistics() {
        return boroughStatistics;
    }

    public int getAvailableProperties() {
        return properties.size();
    }

    public int getAverageReviews() {
        return averageReviews;
    }

    public int getHomeApartments() {
        return TotNumberOfHomeOrApt;
    }

    public String getMostExpensiveBorough() {
        return mostExpensiveBorough;
    }

    public String getCheapestBorough() {
        return cheapestBorough;
    }

    public String getMostExpensiveDescription() {
        return mostExpensiveName;
    }

    public String getCheapestBoroughDescription() {
        return cheapestBoroughDescription;
    }

    public String getMostReviewedBorough() {
        return mostReviewedBorough;
    }
    
    public String getCheapestHost() {
        return cheapHostName;
    }

    public String getExpensiveHost() {
        return expHostName;
    }

}
