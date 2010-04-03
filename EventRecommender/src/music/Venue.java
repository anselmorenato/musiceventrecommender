package music;

public class Venue implements MusicItem {
	final private int id;
	private String name;
	private String city;
	private String street;
	private String country;
	private String postalcode;
	private double latitude;
	private double longitude;
	
	public Venue(int id) {
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public int getID() {
		return id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param country - the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param postalcode the postalcode to set
	 */
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	/**
	 * @return the postalcode
	 */
	public String getPostalcode() {
		return postalcode;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((postalcode == null) ? 0 : postalcode.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venue other = (Venue) obj;
		if (city == null) {
			if (other.getCity() != null)
				return false;
		} else if (!city.equals(other.getCity()))
			return false;
		if (country == null) {
			if (other.getCountry() != null)
				return false;
		} else if (!country.equals(other.getCountry()))
			return false;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.getLatitude()))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.getLongitude()))
			return false;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		if (postalcode == null) {
			if (other.getPostalcode() != null)
				return false;
		} else if (!postalcode.equals(other.getPostalcode()))
			return false;
		if (street == null) {
			if (other.getStreet() != null)
				return false;
		} else if (!street.equals(other.getStreet()))
			return false;
		return true;
	}
	
}
