package country;

public class Country
{
	public Country(
			String name,
			String capital,
			int nbInhabitants)
	{
		this.name = name;
		this.capital = capital;
		this.nbInhabitants = nbInhabitants;
	}
	
	private final String name;
	private final String capital;
	private final int nbInhabitants;
	
	public String getName()
	{
		return name;
	}
	public String getCapital()
	{
		return capital;
	}
	public int getNbInhabitants()
	{
		return nbInhabitants;
	}
}
