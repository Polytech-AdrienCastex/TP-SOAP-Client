package service;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.StringWriter;
import java.util.Iterator;

public class ServiceRequest
{
	private ServiceRequest(SOAPMessage reply)
	{
		this.reply = reply;
	}
	
	public static Builder create() throws UnsupportedOperationException, SOAPException
	{
		return new Builder();
	}
	public static class Builder
	{
		public Builder() throws UnsupportedOperationException, SOAPException
		{
			connection = SOAPConnectionFactory.newInstance()
					.createConnection();
		}
		
		private final SOAPConnection connection;
		private SOAPMessage message;
		private SOAPElement bodyElement;
		private String destination;

		public Builder addHeader(String name, String value) throws SOAPException
		{
			bodyElement.addChildElement(new QName(name)).addTextNode(value);
			message.saveChanges();
			
			return this;
		}
		
		public Builder setMessage(String operation, String destination, String prefix) throws SOAPException
		{
			message = MessageFactory.newInstance()
					.createMessage();
			
			SOAPBody body = message.getSOAPPart()
					.getEnvelope()
					.getBody();
			
			bodyElement = body.addBodyElement(new QName(destination, operation, prefix));
			
			message.saveChanges();
			
			return this;
		}
		public Builder setMessage(String operation, String destination) throws SOAPException
		{
			setMessage(operation, destination, "m");
			
			return this;
		}
		
		public Builder setServiceLocation(String destination)
		{
			this.destination = destination;
			
			return this;
		}
		
		
		public ServiceRequest build() throws SOAPException
		{
			return new ServiceRequest(connection.call(message, destination));
		}
	}

	private final SOAPMessage reply;
	
	public SOAPMessage toSOAP()
	{
		return reply;
	}

	public SOAPHeader getHeader() throws SOAPException
	{
		return reply.getSOAPPart().getEnvelope().getHeader();
	}
	public SOAPBody getBody() throws SOAPException
	{
		return reply.getSOAPPart().getEnvelope().getBody();
	}
	public Source getContent() throws SOAPException
	{
		return reply.getSOAPPart().getContent();
	}
	public NodeList getInnerBody() throws SOAPException
	{
        return ((Node)getBody().getChildElements().next())
        		.getChildNodes();
	}
	public String getInnerBodyString() throws SOAPException
	{
        return ((Node)getBody().getChildElements().next())
        		.getFirstChild()
        		.getFirstChild()
        		//.getNodeValue();
        		.getChildNodes().getLength()+"+";
	}
	
	public String getFirstObject() throws SOAPException
	{
		return toSOAP().getSOAPBody().getFirstChild().getFirstChild().getAttributes().item(1).getNodeValue();
	}
	
	@Override
	public String toString()
	{
		try
		{
			StringWriter writer = new StringWriter();
	        
	        TransformerFactory.newInstance()
			        .newTransformer()
			        .transform(getContent(), new StreamResult(writer));
	        
	        return writer.getBuffer().toString();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "Error!";
		}
	}
}
