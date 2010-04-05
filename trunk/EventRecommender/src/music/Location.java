package music;

public class Location {

	private String city;
	private String country;
	
	public Location(String City,String Country)
	{
		city = City;
		country = Country;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getCountry()
	{
		return country;
	}

	
	/**
	 * @param aCity - A city name to be compare with.
	 * @return true if aCity is equal to this city
	 */
	public boolean compareByCity(String aCity)
	{
		return (city.equals(aCity));
	}
	
	/**
	 * @param aCountry - A country name to be compare with.
	 * @return true if aCountry is equal to this country
	 */
	public boolean compareByCountry(String aCountry)
	{
		return (country.equals(aCountry));
	}
}
