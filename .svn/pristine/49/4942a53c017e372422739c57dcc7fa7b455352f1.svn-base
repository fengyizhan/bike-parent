package com.tiamaes.bike.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.web.filter.GenericFilterBean;

public class SessionTimeoutExceptionFilter extends GenericFilterBean {
	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception exception) {
			Throwable[] causeChain = throwableAnalyzer.determineCauseChain(exception);

			AccessDeniedException accessDeniedException = (AccessDeniedException) throwableAnalyzer
					.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
			String accept = ((HttpServletRequest) request).getHeader("Accept");
			if (accessDeniedException != null
					&& authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())
					&& "application/json".equals(accept)) {
				((HttpServletResponse) response).sendError(440);
				return;
			} else {
				throw exception;
			}
		}
	}

	private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
		protected void initExtractorMap() {
			super.initExtractorMap();
			registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
				public Throwable extractCause(Throwable throwable) {
					ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
					return ((ServletException) throwable).getRootCause();
				}
			});
		}
	}
}
