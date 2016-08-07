package com.orzechp.sainsburys.provider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Jsoup.class)
public class JsoupDocumentProviderTest {

    public static final String TEST_URL = "url";
    public static final String OLD_TEST_URL =  "old url";
    @Mock
    Connection connection;
    @Mock
    Document document;

    private JsoupDocumentProvider target = new JsoupDocumentProvider();

    @Test
    public void shouldRetrieveTheDocumentIfNull() throws Exception {
        PowerMockito.mockStatic(Jsoup.class);

        when(Jsoup.connect(TEST_URL)).thenReturn(connection);
        when(connection.get()).thenReturn(document);

        Document result = target.provideDocument(TEST_URL);

        assertNotNull("the result Document should not be null", result);
        assertEquals("the result Document should be the same as the one from the connection", document, result);
    }

    @Test
    public void shouldNotRetrieveTheDocumentIfItMatchesUrl() throws Exception {
        PowerMockito.mockStatic(Jsoup.class);

        when(Jsoup.connect(TEST_URL)).thenReturn(connection);
        when(connection.get()).thenReturn(document);
        when(document.location()).thenReturn(TEST_URL);

        target.provideDocument(TEST_URL);
        target.provideDocument(TEST_URL);

        verify(connection, times(1)).get();
    }

    @Test
    public void shouldRetrieveTheDocumentIfItDoesNotMatchUrl() throws Exception {
        PowerMockito.mockStatic(Jsoup.class);

        when(Jsoup.connect(TEST_URL)).thenReturn(connection);
        when(connection.get()).thenReturn(document);
        when(document.location()).thenReturn(OLD_TEST_URL);

        target.provideDocument(TEST_URL);
        target.provideDocument(TEST_URL);
        verify(connection, times(2)).get();
    }

    @Test(expected = IOException.class)
    public void shouldThrowIOException() throws Exception {
        PowerMockito.mockStatic(Jsoup.class);

        when(Jsoup.connect(TEST_URL)).thenReturn(connection);
        when(connection.get()).thenThrow(new IOException("Test Exception Message"));
        target.provideDocument(TEST_URL);
    }
}