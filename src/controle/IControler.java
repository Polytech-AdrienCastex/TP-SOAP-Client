package controle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class IControler extends HttpServlet
{
	private static final String ACTION_TYPE = "action";

	private static final String ERROR_PAGE = "home.jsp";
	private static final String NOT_FOUND_PAGE = "404.jsp";

	@Target({ ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface PageAnnotation
	{
		public String action();
	}
	
	protected abstract String home(HttpServletRequest request) throws Exception;

	protected Date conversionChaineenDate(String unedate, String unformat) throws Exception
	{
		Date datesortie;
		// on définit un format de sortie
		SimpleDateFormat defFormat = new SimpleDateFormat(unformat);
		datesortie = defFormat.parse(unedate);
		return datesortie;
	}

	protected String toUTF8(String nonUTF8Value) throws UnsupportedEncodingException
	{
		byte[] array = nonUTF8Value.getBytes();
		return new String(array, 0, array.length, "UTF-8");
	}

	protected void process(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String actionName = request.getParameter(ACTION_TYPE);
		String destinationPage = null;

		if(actionName == null)
			actionName = "home"; // default
		actionName = actionName.trim().toLowerCase();

		request.setAttribute("home", actionName);

		try
		{
			for(Method m : this.getClass().getDeclaredMethods())
				if(m.isAnnotationPresent(PageAnnotation.class))
					if(m.getAnnotation(PageAnnotation.class).action().trim().toLowerCase().equals(actionName))
					{
						destinationPage = (String)m.invoke(this, request);
						break;
					}

			if(destinationPage == null)
				destinationPage = NOT_FOUND_PAGE;
		}
		catch(InvocationTargetException ex)
		{
			request.setAttribute("error", ex.getTargetException().getMessage());
			destinationPage = ERROR_PAGE;
		}
		catch(Exception ex)
		{
			request.setAttribute("error", ex.getMessage());
			destinationPage = ERROR_PAGE;
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/" + destinationPage);
		dispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			process(request, response);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			process(request, response);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
