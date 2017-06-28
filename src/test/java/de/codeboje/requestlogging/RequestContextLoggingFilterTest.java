package de.codeboje.requestlogging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.slf4j.MDC;

public class RequestContextLoggingFilterTest {

	
	@Test
	public void whenNoValueInHeader_thenCreateUUIDAndAddToMDC() throws Exception{
		HttpServletRequest request = mock(HttpServletRequest.class);       
		HttpServletResponse response = mock(HttpServletResponse.class); 
		MyFilterChain chain =  new MyFilterChain();

        when(request.getHeader("X-REQUEST-ID")).thenReturn("");
       
        RequestContextLoggingFilter filter = new RequestContextLoggingFilter(null,  null);

		filter.doFilter(request, response,chain);

        verify(request, atLeast(1)).getHeader("X-REQUEST-ID");
        assertNotNull(chain.id);
	}
	
	@Test
	public void whenValueInHeader_thenCreateUUIDAndAddToMDC() throws Exception{
		HttpServletRequest request = mock(HttpServletRequest.class);       
		HttpServletResponse response = mock(HttpServletResponse.class); 
		MyFilterChain chain =  new MyFilterChain();

        when(request.getHeader("X-REQUEST-ID")).thenReturn("1234d");
       
        RequestContextLoggingFilter filter = new RequestContextLoggingFilter(null,  null);

		filter.doFilter(request, response,chain);

        verify(request, atLeast(1)).getHeader("X-REQUEST-ID");
        assertNotNull(chain.id);
        assertEquals("1234d", chain.id);
	}

}

class MyFilterChain implements FilterChain {

	public String id;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		id = MDC.get("requestId");
		
	}
	
}
