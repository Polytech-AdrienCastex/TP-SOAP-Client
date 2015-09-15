package controle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64.Decoder;
import java.util.Map.Entry;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import service.ServiceCountry;

import java.util.Date;

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