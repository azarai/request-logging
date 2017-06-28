package de.codeboje.requestlogging;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * A Spirng {@link ClientHttpRequestInterceptor} to add a custom request identifier to the outgoing http request using a value from the {@link MDC}.
 * @author Jens Boje
 *
 */
public class RequestContextLoggingInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestContextLoggingFilter.class);

	private String requestHeaderId = "X-REQUEST-ID";
	private String logIdentifier = "requestId";

	public RequestContextLoggingInterceptor(String requestHeaderId, String logIdentifier) {
		super();
		if (StringUtils.isNotEmpty(requestHeaderId)) {
			this.requestHeaderId = requestHeaderId;
		}

		if (StringUtils.isNotEmpty(logIdentifier)) {
			this.logIdentifier = logIdentifier;
		}
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execute)
			throws IOException {

		String requestUUID = MDC.get(logIdentifier);

		if (StringUtils.isEmpty(requestUUID)) {
			requestUUID = Util.createId();
			LOGGER.info("Performing Request {} without {} in MDC and assign new {}", request.getURI(), logIdentifier,
					requestUUID);
		}

		request.getHeaders().add(requestHeaderId, requestUUID);

		return execute.execute(request, body);
	}

}
