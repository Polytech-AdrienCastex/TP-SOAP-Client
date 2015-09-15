package servicecountry;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.soap.SOAPException;

public class ServiceCountry
{
	public ServiceCountry(String url)
	{
		this.url = url;
	}
	
	private final String url;
	
	
	public static Builder create()
	{
		return new Builder();
	}
	public static class Builder extends WebServiceBuilder<Builder>
	{
		public ServiceCountry build()
		{
			return new ServiceCountry(toURI());
		}
	}
	
	protected ServiceRequest.Builder generateRequest(String operation) throws UnsupportedOperationException, SOAPException
	{
		return ServiceRequest.create()
				.setServiceLocation(url)
				.setMessage(operation, "http://webservice");
	}
	
	
	public String[] getCountryList() throws UnsupportedOperationException, SOAPException
	{
		return generateRequest("getListePays")
				.build()
				.getInnerBodyString()
				.split("\n");
	}
	public Country getCountryInfo(String countryName) throws UnsupportedOperationException, SOAPException
	{
		Map<String, String> data = Stream.of(generateRequest("donneInfoPays")
				.addHeader("pays", countryName)
				.build()
				.getInnerBodyString()
				.split("\n"))
				.map(l -> l.contains(":") ? l.split(":") : new String[] { "?", l })
				.collect(Collectors.toMap(l -> l[0].trim().toLowerCase(), l -> l[1].trim()));
		
		return new Country(countryName, data.get("capital"), (int)Double.parseDouble(data.get("nombre d'habitants")));
	}
}
