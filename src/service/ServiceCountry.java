package service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
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
import org.w3c.dom.Node;
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
	
	protected Node seekNode(NodeList list, String nodeLocalName) throws ServiceException
	{
		nodeLocalName = nodeLocalName.toLowerCase();
		
		for(int i = 0; i < list.getLength(); i++)
		{
			Node node = list.item(i);
			if(nodeLocalName.equals(node.getLocalName().toLowerCase()))
				return node;
		}

		throw new ServiceException();
	}
	protected Collection<Node> seekNodes(NodeList list, String nodeLocalName)
	{
		Collection<Node> nodes = new LinkedList<>();
		nodeLocalName = nodeLocalName.toLowerCase();
		
		for(int i = 0; i < list.getLength(); i++)
		{
			Node node = list.item(i);
			if(nodeLocalName.equals(node.getLocalName().toLowerCase()))
				nodes.add(node);
		}

		return nodes;
	}
	
	
	protected Country castNodeToCountry(Node node) throws ServiceException
	{
		NodeList nChildren = node.getChildNodes();

		Node nodeName = seekNode(nChildren, "nom");
		Node nodeCapital = seekNode(nChildren, "capital");
		Node nodeNbInhabitants = seekNode(nChildren, "nbHabitants");

		return new Country(
				nodeName.getTextContent(),
				nodeCapital.getTextContent(),
				(int)Double.parseDouble(nodeNbInhabitants.getTextContent()));
	}
	
	public Collection<Country> getCountryList() throws UnsupportedOperationException, SOAPException, ServiceException
	{
		NodeList nl = generateRequest("getListePays")
				.build()
				.getInnerBody();
		
		Collection<Country> countries = new LinkedList<>();
		
		for(Node n : seekNodes(nl, "return"))
		{
			try
			{
				countries.add(castNodeToCountry(n));
			}
			catch(ServiceException ex)
			{
				// Not a well formated Country
			}
		}
		
		return countries;
	}
	public Country getCountryInfo(String countryName) throws UnsupportedOperationException, SOAPException, ServiceException
	{
		NodeList nl = generateRequest("donneInfoPays")
				.addHeader("pays", countryName)
				.build()
				.getInnerBody();

		try
		{
			Node resultNode = seekNode(nl, "return");
			
			try
			{
				return castNodeToCountry(resultNode);
			}
			catch(ServiceException ex)
			{
				// Not a well formated Country
				throw new ServiceException("Can't parse the result. Please, check the version of the software.");
			}
		}
		catch(ServiceException ex)
		{
			// Result node not found
			throw new ServiceException("No result returned by the Service.");
		}
	}
}
