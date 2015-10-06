package controle;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import service.ServiceCountry;

@WebServlet("/Controler")
public class Controler extends IControler
{
	private static ServiceCountry serviceCountry = null;
	protected static ServiceCountry getServiceCountry()
	{
		if(serviceCountry == null)
		{
			serviceCountry = ServiceCountry.create()
					.setPort(8081)
					.setAddress("localhost")
					.setServicePath("TPSOAPServer/services/WebServicePays")
					.build();
		}
		return serviceCountry;
	}
	
	
	@PageAnnotation(action = "home")
	protected String home(HttpServletRequest request) throws Exception
	{
		request.setAttribute("countries", getServiceCountry().getCountryList());
		
		return "home.jsp";
	}

	@PageAnnotation(action = "info")
	protected String countryInformation(HttpServletRequest request) throws Exception
	{
		String country = request.getParameter("country");

		request.setAttribute("countries", getServiceCountry().getCountryList());
		request.setAttribute("country", getServiceCountry().getCountryInfo(country));
		
		return "info.jsp";
	}
}