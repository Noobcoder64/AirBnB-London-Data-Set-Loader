import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A calculator which stores a collection of values and computes useful statistical values.
 *
 */
public class StatisticCalculator {

	private List<Integer> values;
	
	public StatisticCalculator() {
		values = new ArrayList<>();
	}
	
	public StatisticCalculator(List<Integer> values) {
		this.values = values;
	}
	
	/**
	 * Add a value to this statistic calculator.
	 */
	public void addValue(int value) {
		this.values.add(value);
	}
	
	/**
	 * Return the sum of the values.
	 * @return the sum of the values
	 */
	public int getTotal() {
		return values.stream().reduce(0, (a,b) ->  a + b);
	}
	
	/**
	 * Return the average of the values.
	 * @return the average of the values
	 */
	public double getAverage() {
		return getTotal() / values.size();
	}

	/**
	 * Return the upper quartile of the values.
	 * @return the upper quartile of the values
	 */
	public int getUpperQuartile() {
		Collections.sort(values);
		int index = (values.size()) * 3 / 4;
		return values.get(index);
	}
	
	/**
	 * Return the lower quartile of the values.
	 * @return the lower quartile of the values
	 */
	public int getLowerQuartile() {
		Collections.sort(values);
		int index = (values.size()) / 4;
		return values.get(index);
	}
	
	/**
	 * Return the largest value.
	 * @return the largest value.
	 */
	public int getMaxValue() {
		Collections.sort(values);
		int index = values.size() - 1;
		return values.get(index);
	}

	/**
	 * Return the smallest value.
	 * @return the smallest value
	 */
	public int getMinValue() {
		Collections.sort(values);
		int index = 0;
		return values.get(index);
	}
	
}
