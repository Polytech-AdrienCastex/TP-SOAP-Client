package service.exception;

import org.w3c.dom.Node;

public class ServiceException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ServiceException(Node xmlNode)
	{
		super(xmlNode.getTextContent());
	}
	public ServiceException(String message)
	{
		super(message);
	}
	public ServiceException()
	{
		super();
	}
}
