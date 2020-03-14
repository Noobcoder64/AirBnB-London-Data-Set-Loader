
public enum Boroughs {

	ENFI("Enfield", 3, 0),
	
	BARN("Barnet", 2, 1),
	HRGY("Haringey", 3, 1),
	WALT("Waltham Forest", 4, 1),
	
	HRRW("Harrow", 0, 2),
	BREN("Brent", 1, 2),
	CAMD("Camden", 2, 2),
	ISLI("Islington", 3, 2),
	HACK("Hackney", 4, 2),
	REDB("Redbridge", 5, 2),
	HAVE("Havering", 6, 2),
	
	HILL("Hillingdon", 0, 3),
	EALI("Ealing", 1, 3),
	KENS("Kensington and Chelsea", 2, 3),
	WSTM("Westminster", 3, 3),
	TOWH("Tower Hamlets", 4, 3),
	NEWH("Newham", 5, 3),
	BARK("Barking and Dagenham", 6, 3),
	
	HOUN("Hounslow", 0, 4),
	HAMM("Hammersmith and Fulham", 1, 4),
	WAND("Wandsworth", 2, 4),
	CITY("City of London", 3, 4),
	GWCH("Greenwich", 4, 4),
	BEXL("Bexley", 5, 4),
	
	RICH("Richmond upon Thames", 1, 5),
	MERT("Merton", 2, 5),
	LAMB("Lambeth", 3, 5),
	STHW("Southwark", 4, 5),
	LEWS("Lewisham", 5, 5),
	
	KING("Kingston upon Thames", 1, 6),
	SUTT("Sutton", 2, 6),
	CROY("Croydon", 3, 6),
	BROM("Bromley", 4, 6);
	
	
	private String name;
	
	private int column;
	
	private int row;
	
	private Boroughs(String name, int column, int row) {
		this.name = name;
		this.column = column;
		this.row = row;
	}
	
	public String getName() {
		return name;
	}
	
	public int getColumn() {
		return column;
	}
	
	public int getRow() {
		return row;
	}
	
}
