package com.codingbrothers.futurimages.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Iterators;

public class RequestContextTest {

	private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";

	private static final Locale CUSTOM_DEF_LOCALE = Locale.CANADA_FRENCH;
	private static final Locale LOCALE_CZECH = new Locale("cs", "CZ");
	private static Locale origDefLocale;

	private HttpServletRequest defLocaleRequest;
	private HttpServletRequest czechLocaleRequest;

	@BeforeClass
	public static void setCustomDefaultLocale() {
		origDefLocale = Locale.getDefault();
		Locale.setDefault(CUSTOM_DEF_LOCALE);
	}

	@AfterClass
	public static void resetDefaultLocale() {
		Locale.setDefault(origDefLocale);
	}

	@Test(expected = RuntimeException.class)
	public void ctxRequiresRequest() {
		new RequestContext(null);
	}

	@Test
	public void ctxReturnsRootLocaleByDefaultIfReqDoesNotProvideOne() {
		RequestContext ctx = new RequestContext(defLocaleRequest);
		assertEquals(Locale.ROOT, ctx.getRequestLocale());
	}

	@Test
	public void ctxReturnsPassedLocaleIfReqDoesNotProvideOne() {
		RequestContext ctx = new RequestContext(defLocaleRequest);
		assertEquals(Locale.ENGLISH, ctx.getRequestLocale(Locale.ENGLISH));
	}

	@Test
	public void ctxIgnoresReqLocaleIfAcceptLanguageHeaderIsMissing() {
		RequestContext ctx = new RequestContext(defLocaleRequest);
		assertEquals(Locale.ROOT, ctx.getRequestLocale());
	}

	@Test
	public void ctxReturnsReqLocaleIfAcceptLanguageHeaderIsSet() {
		RequestContext ctx = new RequestContext(czechLocaleRequest);
		assertEquals(LOCALE_CZECH, ctx.getRequestLocale());
	}
	
	@Test
	public void ctxIgnoresPassedLocaleIfAcceptLanguageHeaderIsSet() {
		RequestContext ctx = new RequestContext(czechLocaleRequest);
		assertEquals(LOCALE_CZECH, ctx.getRequestLocale(Locale.ITALY));
	}

	@Before
	public void createDummyRequest() {
		defLocaleRequest = mock(HttpServletRequest.class);
		doReturn(Locale.getDefault()).when(defLocaleRequest).getLocale();
		doReturn(Iterators.asEnumeration(Arrays.asList(Locale.getDefault()).iterator())).when(defLocaleRequest)
				.getLocales();

		czechLocaleRequest = new HttpServletRequestWrapper(mock(HttpServletRequest.class)) {

			@Override
			public String getHeader(String name) {
				if (ACCEPT_LANGUAGE_HEADER.equals(name)) {
					return "cs_CZ";
				} else {
					return null;
				}
			}

			@Override
			public Enumeration<?> getHeaders(String name) {
				if (ACCEPT_LANGUAGE_HEADER.equals(name)) {
					return Iterators.asEnumeration(Arrays.asList("cs_CZ").iterator());
				} else {
					return Iterators.asEnumeration(Collections.emptyList().iterator());
				}
			}

			@Override
			public Enumeration<?> getHeaderNames() {
				return Iterators.asEnumeration(Arrays.asList(ACCEPT_LANGUAGE_HEADER).iterator());
			}

			@Override
			public Locale getLocale() {
				return LOCALE_CZECH;
			}

			@Override
			public Enumeration<?> getLocales() {
				return Iterators.asEnumeration(Arrays.asList(LOCALE_CZECH).iterator());
			}
		};
	}

}
