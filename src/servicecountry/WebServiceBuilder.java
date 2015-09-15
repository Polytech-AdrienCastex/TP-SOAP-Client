package servicecountry;

public class WebServiceBuilder<T>
{
	private int port = 8080;
	private String address = "localhost";
	private String servicePath = "TPSOAPServer/services/WebServicePays";
	
	public T setPort(int port)
	{
		this.port = port;
		
		return (T)this;
	}
	public T setAddress(String address)
	{
		this.address = address;
		
		return (T)this;
	}
	public T setServicePath(String servicePath)
	{
		this.servicePath = servicePath;
		
		return (T)this;
	}
	
	public String toURI()
	{
		return "http://" + address + ":" + port + "/" + servicePath;
	}
}
