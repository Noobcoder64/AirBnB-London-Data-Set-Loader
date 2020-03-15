import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticCalculator {

	private List<Integer> values;
	
	public StatisticCalculator() {
		values = new ArrayList<>();
	}
	
	public StatisticCalculator(List<Integer> values) {
		this.values = values;
	}
	
	public void addValue(int value) {
		this.values.add(value);
	}
	
	public int getTotal() {
		return values.stream().reduce(0, (a,b) ->  a + b);
	}
	
	public double getAverage() {
		return getTotal() / values.size();
	}

	public int getUpperQuartile() {
		Collections.sort(values);
		int index = (values.size() + 1) * 3 / 4;
		return values.get(index);
	}
	
	public int getLowerQuartile() {
		Collections.sort(values);
		int index = (values.size() + 1) / 4;
		return values.get(index);
	}
	
	public int getMaxValue() {
		Collections.sort(values);
		int index = values.size() - 1;
		return values.get(index);
	}

	public int getMinValue() {
		Collections.sort(values);
		int index = 0;
		return values.get(index);
	}
	
}
