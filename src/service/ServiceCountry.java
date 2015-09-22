package service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sound.midi.Soundbank;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import country.Country;
import service.exception.ServiceException;

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
	
	
	protected Document parseXML(String data)
	{
		try(InputStream dataStream = new ByteArrayInputStream(data.getBytes());)
		{
			return DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse(dataStream);
		}
		catch(ParserConfigurationException | SAXException | IOException ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public String[] getCountryList() throws UnsupportedOperationException, SOAPException, ServiceException
	{
		Document countries = parseXML("<countries>" + generateRequest("getListePays")
				.build()
				.getInnerBodyString() + "</countries>");

		NodeList errors = countries.getElementsByTagName("error");
		
		if(errors.getLength() > 0)
		{ // manage errors
			throw new ServiceException(errors.item(0));
		}
		else
		{
			NodeList list = countries
					.getDocumentElement()
					.getElementsByTagName("country");
			
			List<String> data = new LinkedList<>();
			for(int i = 0; i < list.getLength(); i++)
				data.add(list.item(i).getTextContent());
	
			return data.toArray(new String[data.size()]);
		}
	}
	public Country getCountryInfo(String countryName) throws UnsupportedOperationException, SOAPException, ServiceException
	{
		Document country = parseXML(generateRequest("donneInfoPays")
				.addHeader("pays", countryName)
				.build()
				.getInnerBodyString());
		
		NodeList errors = country.getElementsByTagName("error");
		
		if(errors.getLength() > 0)
		{ // manage errors
			throw new ServiceException(errors.item(0));
		}
		else
		{
			String name = country.getElementsByTagName("name").item(0).getTextContent();
			String capital = country.getElementsByTagName("capital").item(0).getTextContent();
			String nbinhab = country.getElementsByTagName("nbinhab").item(0).getTextContent();
			
			return new Country(name, capital, (int)Double.parseDouble(nbinhab));
		}
	}
}
