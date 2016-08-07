package com.orzechp.sainsburys.dom;

import com.orzechp.sainsburys.provider.JsoupDocumentProvider;
import com.orzechp.sainsburys.provider.UrlConnectionProvider;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(URL.class)
public class DocumentProcessorImplTest {

    private static final String TEST_URL = "www.test.url";
    public static final int OK = 200;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;

    @Mock
    private JsoupDocumentProvider jsoupDocumentProvider;
    @Mock
    private UrlConnectionProvider urlConnectionProvider;
    @Mock
    private Document document;
    @InjectMocks
    private DocumentProcessorImpl target;

    @Before
    public void setUp() throws Exception {
        when(jsoupDocumentProvider.provideDocument(TEST_URL)).thenReturn(document);
    }

    @Test
    public void shouldGetTheSizeInBytesOfTheWebsite() throws IOException {
        URLConnection urlConnection = mock(URLConnection.class);
        when(urlConnectionProvider.provideURLConnection(TEST_URL)).thenReturn(urlConnection);
        when(urlConnection.getContentLength()).thenReturn(OK);
        int output = target.getSizeOfPage(TEST_URL);
        assertEquals(OK, output);
    }


    @Test
    public void shouldReadSingleElementText() throws IOException {
        Element element = mock(Element.class);
        when(document.select("test css")).thenReturn(new Elements(element));
        when(element.text()).thenReturn("test element text");

        List<String> output = target.getElementsText(TEST_URL, "test css");
        assertNotNull("output should not be null", output);
        assertEquals("output should contain a single element", ONE, output.size());
        assertEquals("test element text", output.get(ZERO));
    }

    @Test
    public void shouldReadMultipleElementTexts() throws IOException {
        Element element1 = mock(Element.class);
        Element element2 = mock(Element.class);
        Element element3 = mock(Element.class);
        when(document.select("test css")).thenReturn(new Elements(element1, element2, element3));
        when(element1.text()).thenReturn("first test element text");
        when(element2.text()).thenReturn("second test element text");
        when(element3.text()).thenReturn("third test element text");

        List<String> output = target.getElementsText(TEST_URL, "test css");
        assertNotNull("output should not be null", output);
        assertEquals("output should contain a three elements", THREE, output.size());
        assertTrue("output should contain first element's text", output.contains("first test element text"));
        assertTrue("output should contain second element's text", output.contains("second test element text"));
        assertTrue("output should contain third element's text", output.contains("third test element text"));
    }

    @Test
    public void shouldReturnEmptyListIfNoElementsFoundText() throws IOException {
        when(document.select("test css")).thenReturn(new Elements());

        List<String> output = target.getElementsText(TEST_URL, "test css");
        assertNotNull("output should not be null", output);
        assertTrue("output should be empty", output.isEmpty());
    }

    @Test(expected = IOException.class)
    public void shouldPassDownExceptionIfThrownText() throws IOException {
        when(jsoupDocumentProvider.provideDocument(TEST_URL)).thenThrow(new IOException("test exception"));
        target.getElementsText(TEST_URL, "test css");
    }

    @Test
    public void shouldReadAttributeFromSingleElement() throws IOException {
        Element element = mock(Element.class);
        when(document.select("test css")).thenReturn(new Elements(element));
        when(element.hasAttr("attr")).thenReturn(true);
        when(element.attr("attr")).thenReturn("test attribute value");

        List<String> output = target.getAttributeValueFromElements(TEST_URL, "test css", "attr");
        assertNotNull("output should not be null", output);
        assertEquals("output should contain a single element", ONE, output.size());
        assertEquals("test attribute value", output.get(ZERO));
    }

    @Test
    public void shouldReadAttributesFromMultipleElements() throws IOException {
        Element element1 = mock(Element.class);
        Element element2 = mock(Element.class);
        Element element3 = mock(Element.class);
        when(document.select("test css")).thenReturn(new Elements(element1, element2, element3));
        when(element1.hasAttr("attr")).thenReturn(true);
        when(element1.attr("attr")).thenReturn("first test attribute value");
        when(element2.hasAttr("attr")).thenReturn(true);
        when(element2.attr("attr")).thenReturn("second test attribute value");
        when(element3.hasAttr("attr")).thenReturn(true);
        when(element3.attr("attr")).thenReturn("third test attribute value");

        List<String> output = target.getAttributeValueFromElements(TEST_URL, "test css", "attr");
        assertNotNull("output should not be null", output);
        assertEquals("output should contain a three elements", THREE, output.size());
        assertTrue("output should contain first element's attribute value", output.contains("first test attribute value"));
        assertTrue("output should contain second element's attribute value", output.contains("second test attribute value"));
        assertTrue("output should contain third element's attribute value", output.contains("third test attribute value"));
    }

    @Test
    public void shouldOnlyReadAttributesFromTheElementsThatHaveTheAttribute() throws IOException {
        Element element1 = mock(Element.class);
        Element element2 = mock(Element.class);
        Element element3 = mock(Element.class);
        when(document.select("test css")).thenReturn(new Elements(element1, element2, element3));
        when(element1.hasAttr("attr")).thenReturn(true);
        when(element1.attr("attr")).thenReturn("first test attribute value");
        when(element2.hasAttr("attr")).thenReturn(false);
        when(element2.attr("attr")).thenReturn("second test attribute value");
        when(element3.hasAttr("attr")).thenReturn(true);
        when(element3.attr("attr")).thenReturn("third test attribute value");

        List<String> output = target.getAttributeValueFromElements(TEST_URL, "test css", "attr");
        assertNotNull("output should not be null", output);
        assertEquals("output should contain a three elements", TWO, output.size());
        assertTrue("output should contain first element's attribute value", output.contains("first test attribute value"));
        assertFalse("output should contain second element's attribute value", output.contains("second test attribute value"));
        assertTrue("output should contain third element's attribute value", output.contains("third test attribute value"));
    }

    @Test
    public void shouldReturnEmptyListIfNoElementsFoundAttribute() throws IOException {
        when(document.select("test css")).thenReturn(new Elements());

        List<String> output = target.getAttributeValueFromElements(TEST_URL, "test css", "attr");
        assertNotNull("output should not be null", output);
        assertTrue("output should be empty", output.isEmpty());
    }

    @Test(expected = IOException.class)
    public void shouldPassDownExceptionIfThrownAttribute() throws IOException {
        when(jsoupDocumentProvider.provideDocument(TEST_URL)).thenThrow(new IOException("test exception"));
        target.getAttributeValueFromElements(TEST_URL, "test css", "attr");
    }

    @Test
    public void shouldBuildAMapOfElements() throws IOException {
        Element key1 = mock(Element.class);
        Element key2 = mock(Element.class);
        Element key3 = mock(Element.class);
        Element value1 = mock(Element.class);
        Element value2 = mock(Element.class);
        Element value3 = mock(Element.class);

        when(document.select("key css")).thenReturn(new Elements(key1, key2, key3));
        when(document.select("value css")).thenReturn(new Elements(value1, value2, value3));

        when(key1.text()).thenReturn("key one");
        when(key2.text()).thenReturn("key two");
        when(key3.text()).thenReturn("key three");
        when(value1.text()).thenReturn("value one");
        when(value2.text()).thenReturn("value two");
        when(value3.text()).thenReturn("value three");

        Map<String, String> output = target.getMapOfElementTexts(TEST_URL, "key css", "value css");
        assertNotNull("output should not be null", output);
        assertEquals(3, output.size());
        assertTrue("output should have key 'key one'", output.containsKey("key one"));
        assertEquals("value one", output.get("key one"));
        assertTrue("output should have key 'key two'", output.containsKey("key two"));
        assertEquals("value two", output.get("key two"));
        assertTrue("output should have key 'key three'", output.containsKey("key three"));
        assertEquals("value three", output.get("key three"));
    }

    @Test
    public void shouldInsertNullIfNotEnoughValueElements() throws IOException {
        Element key1 = mock(Element.class);
        Element key2 = mock(Element.class);
        Element key3 = mock(Element.class);
        Element key4 = mock(Element.class);
        Element value1 = mock(Element.class);
        Element value2 = mock(Element.class);

        when(document.select("key css")).thenReturn(new Elements(key1, key2, key3, key4));
        when(document.select("value css")).thenReturn(new Elements(value1, value2));

        when(key1.text()).thenReturn("key one");
        when(key2.text()).thenReturn("key two");
        when(key3.text()).thenReturn("key three");
        when(key4.text()).thenReturn("key four");
        when(value1.text()).thenReturn("value one");
        when(value2.text()).thenReturn("value two");

        Map<String, String> output = target.getMapOfElementTexts(TEST_URL, "key css", "value css");
        assertNotNull("output should not be null", output);
        assertEquals(FOUR, output.size());
        assertTrue("output should have key 'key one'", output.containsKey("key one"));
        assertEquals("value one", output.get("key one"));
        assertTrue("output should have key 'key two'", output.containsKey("key two"));
        assertEquals("value two", output.get("key two"));
        assertTrue("output should have key 'key three'", output.containsKey("key three"));
        assertNull("output three should be null", output.get("key three"));
        assertTrue("output should have key 'key four'", output.containsKey("key four"));
        assertNull("output four should be null", output.get("key four"));
    }
}
