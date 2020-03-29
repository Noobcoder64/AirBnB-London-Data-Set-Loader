
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
/**
 * The test class ControllerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ControllerTest
{
    /**
     * Default constructor for test class ControllerTest
     */
    private AirbnbDataLoader airbnbDataLoader;
    
    private List<AirbnbListing> properties;
    
    public ControllerTest()
    {
        
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        airbnbDataLoader = new AirbnbDataLoader();
        airbnbDataLoader.load("airbnb-london.csv");
        properties = new ArrayList<>();
        properties = airbnbDataLoader.getProperties();
    }
    
    

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void testGetAvailableProperties() {
        Controller controller  = new Controller(properties);
        controller.setStartPrice(100);
        controller.setEndPrice(200);
        controller.processRange();
        assertEquals(14029, controller.getAvailableProperties());
    }
    
    @Test
    public void testGetAverageReviews() {
        Controller controller  = new Controller(properties);
        controller.setStartPrice(100);
        controller.setEndPrice(200);
        controller.processRange();
        assertEquals(14029, controller.getAvailableProperties());
    }
    
    @Test
    public void testGetHomeApartments() {
        Controller controller  = new Controller(properties);
        controller.setStartPrice(100);
        controller.setEndPrice(200);
        controller.processRange();
        assertEquals(12849, controller.getHomeApartments());
    }
    
    @Test
    public void testGetMostExpensiveBorough() {
        Controller controller  = new Controller(properties);
        controller.setStartPrice(100);
        controller.setEndPrice(200);
        controller.processRange();
        assertEquals("Tower Hamlets", controller.getMostExpensiveBorough());
    }
    
    @Test
    public void testGetCheapestBorough() {
        Controller controller  = new Controller(properties);
        controller.setStartPrice(100);
        controller.setEndPrice(200);
        controller.processRange();
        assertEquals("Barking and Dagenham", controller.getCheapestBorough());
    }
    @Test
    public void testGetExpensiveHost() {
        Controller controller  = new Controller(properties);
        controller.setStartPrice(100);
        controller.setEndPrice(200);
        controller.processRange();
        assertEquals("Jennifer", controller.getExpensiveHost());
    }

    @Test
    public void testGetCheapestHost() {
        Controller controller  = new Controller(properties);
        controller.setStartPrice(100);
        controller.setEndPrice(200);
        controller.processRange();
        assertEquals("Hinesh", controller.getCheapestHost());
    }
    
    @Test
    public void testGetMostReviewedBorough() {
        Controller controller  = new Controller(properties);
        controller.setStartPrice(100);
        controller.setEndPrice(200);
        controller.processRange();
        assertEquals("Westminster", controller.getMostReviewedBorough());
    }
    
    @Test
    public void testGetCheapestBoroughDescription() {
        Controller controller  = new Controller(properties);
        controller.setStartPrice(100);
        controller.setEndPrice(200);
        controller.processRange();
        assertEquals("Modern 1 Bed Flat in East London", controller.getCheapestBoroughDescription());
    }
    
    @Test
    public void testGetMostExpensiveDescription() {
        Controller controller  = new Controller(properties);
        controller.setStartPrice(100);
        controller.setEndPrice(200);
        controller.processRange();
        assertEquals("Brick Lane area", controller.getMostExpensiveDescription());
    }
}
