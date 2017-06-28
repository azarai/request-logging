package de.codeboje.requestlogging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

public class RequestContextLoggingInterceptorTest {

	@Before
	public void before() {
		MDC.clear();
	}
	
	@Test
	public void whenNoValueInMDC_thenCreateUUIDAndAddToHeader() throws Exception{
		HttpRequest request = mock(HttpRequest.class);       
		ClientHttpRequestExecution execute = mock(ClientHttpRequestExecution.class);    

		HttpHeaders headers = new HttpHeaders();
        when(request.getHeaders()).thenReturn(headers);
        
        RequestContextLoggingInterceptor rcli = new RequestContextLoggingInterceptor(null,  null);

        byte[] body = new byte[0];
		rcli.intercept(request,body, execute);

        verify(request, atLeast(1)).getHeaders();
        verify(execute, atLeast(1)).execute(request, body);
        assertTrue(StringUtils.isNotEmpty(headers.getFirst("X-REQUEST-ID")));
	}
	@Test
	public void whenValueInMDC_thenCreateUUIDAndAddToHeader() throws Exception{
		HttpRequest request = mock(HttpRequest.class);       
		ClientHttpRequestExecution execute = mock(ClientHttpRequestExecution.class);    

		HttpHeaders headers = new HttpHeaders();
        when(request.getHeaders()).thenReturn(headers);
        
        MDC.put("requestId", "1234d");
        RequestContextLoggingInterceptor rcli = new RequestContextLoggingInterceptor(null,  null);

        byte[] body = new byte[0];
		rcli.intercept(request,body, execute);

        verify(request, atLeast(1)).getHeaders();
        verify(execute, atLeast(1)).execute(request, body);
        assertTrue(StringUtils.isNotEmpty(headers.getFirst("X-REQUEST-ID")));
        assertEquals("1234d", headers.getFirst("X-REQUEST-ID"));
	}
}
