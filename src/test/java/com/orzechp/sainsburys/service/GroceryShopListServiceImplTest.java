package com.orzechp.sainsburys.service;

import com.orzechp.sainsburys.dom.DocumentProcessor;
import com.orzechp.sainsburys.domain.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;
import static com.orzechp.sainsburys.common.DOMConstants.*;
import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroceryShopListServiceImplTest {
    @Mock
    private DocumentProcessor documentProcessor;
    @InjectMocks
    private GroceryShopListServiceImpl target;


    @Test
    public void shouldGetFruitsTitles() throws Exception {
        when(documentProcessor.getAttributeValueFromElements(HOMEPAGE_URL, CSS_SELECTOR, HREF_ATTRIBUTE_NAME)).thenReturn(singletonList("link one"));
        when(documentProcessor.getElementsText("link one", TITLE_CSS_SELECTOR)).thenReturn(singletonList("fruit title"));
        Result results = target.getSainsburysGroceryList();
        assertEquals("fruit title", results.getProducts().get(0).getTitle());
    }

    @Test
    public void shouldGetBlankFruitsTitles() throws Exception {
        when(documentProcessor.getAttributeValueFromElements(HOMEPAGE_URL, CSS_SELECTOR, HREF_ATTRIBUTE_NAME)).thenReturn(singletonList("link one"));
        when(documentProcessor.getElementsText("link one", TITLE_CSS_SELECTOR)).thenReturn(Collections.<String>emptyList());
        Result results = target.getSainsburysGroceryList();
        assertNotNull("results should not be null", results);
        assertEquals(1, results.getProducts().size());
        assertEquals("", results.getProducts().get(0).getTitle());
    }

    @Test
    public void shouldGetPageSize() throws Exception {
        when(documentProcessor.getAttributeValueFromElements(HOMEPAGE_URL, CSS_SELECTOR, HREF_ATTRIBUTE_NAME)).thenReturn(Arrays.asList("link one", "link two"));
        when(documentProcessor.getSizeOfPage("link one")).thenReturn(512);
        when(documentProcessor.getSizeOfPage("link two")).thenReturn(2048);
        Result results = target.getSainsburysGroceryList();
        assertNotNull("results should not be null", results);
        assertEquals(2, results.getProducts().size());
        assertEquals("512 bytes", results.getProducts().get(0).getSize());
        assertEquals("2 KB", results.getProducts().get(1).getSize());
    }

    @Test
    public void shouldGetZeroUnitPriceWhenNoPriceElementFound() throws Exception {
        when(documentProcessor.getAttributeValueFromElements(HOMEPAGE_URL, CSS_SELECTOR, HREF_ATTRIBUTE_NAME)).thenReturn(singletonList("link one"));
        when(documentProcessor.getElementsText("link one", PRICE_PER_UNIT_CSS_SELECTOR)).thenReturn(Collections.<String>emptyList());
        Result results = target.getSainsburysGroceryList();
        assertNotNull("results should not be null", results);
        assertEquals(1, results.getProducts().size());
        assertEquals(0, results.getProducts().get(0).getUnitPrice(), 0);
    }

    @Test
    public void shouldGetZeroUnitPriceWhenPriceElementDoesntContainAPrice() throws Exception {
        when(documentProcessor.getAttributeValueFromElements(HOMEPAGE_URL, CSS_SELECTOR, HREF_ATTRIBUTE_NAME)).thenReturn(singletonList("link one"));
        when(documentProcessor.getElementsText("link one", PRICE_PER_UNIT_CSS_SELECTOR)).thenReturn(singletonList("no price here"));
        Result results = target.getSainsburysGroceryList();
        assertNotNull("results should not be null", results);
        assertEquals(1, results.getProducts().size());
        assertEquals(0, results.getProducts().get(0).getUnitPrice(), 0);
    }

    @Test
    public void shouldGetUnitPriceWhenPriceElementContainsAPrice() throws Exception {
        when(documentProcessor.getAttributeValueFromElements(HOMEPAGE_URL, CSS_SELECTOR, HREF_ATTRIBUTE_NAME)).thenReturn(singletonList("link one"));
        when(documentProcessor.getElementsText("link one", PRICE_PER_UNIT_CSS_SELECTOR)).thenReturn(singletonList("�4.59/unit"));
        Result results = target.getSainsburysGroceryList();
        assertNotNull("results should not be null", results);
        assertEquals(1, results.getProducts().size());
        assertEquals(4.59, results.getProducts().get(0).getUnitPrice(), 0);
    }

    @Test
    public void shouldTotalUnitPrices() throws Exception {
        when(documentProcessor.getAttributeValueFromElements(HOMEPAGE_URL, CSS_SELECTOR, HREF_ATTRIBUTE_NAME)).thenReturn(Arrays.asList("link one", "link two", "link three"));
        when(documentProcessor.getElementsText("link one", PRICE_PER_UNIT_CSS_SELECTOR)).thenReturn(singletonList("�4.59/unit"));
        when(documentProcessor.getElementsText("link two", PRICE_PER_UNIT_CSS_SELECTOR)).thenReturn(singletonList("�6.78/unit"));
        when(documentProcessor.getElementsText("link three", PRICE_PER_UNIT_CSS_SELECTOR)).thenReturn(singletonList("�1.44/unit"));
        Result results = target.getSainsburysGroceryList();
        assertNotNull("results should not be null", results);
        assertEquals(3, results.getProducts().size());
        assertEquals(12.81, results.getTotal(), 0);
    }

    @Test
    public void shouldGetDescriptionIfItIsInTheMap() throws Exception {
        when(documentProcessor.getAttributeValueFromElements(HOMEPAGE_URL, CSS_SELECTOR, HREF_ATTRIBUTE_NAME)).thenReturn(singletonList("link one"));
        when(documentProcessor.getMapOfElementTexts("link one", PRODUCT_CONTENT_DATA_KEY_CSS_SELECTOR, PRODUCT_CONTENT_DATA_VALUE_CSS_SELECTOR))
                .thenReturn(of(DESCRIPTION_KEY, "test description"));
        Result results = target.getSainsburysGroceryList();
        assertNotNull("results should not be null", results);
        assertEquals(1, results.getProducts().size());
        assertEquals("test description", results.getProducts().get(0).getDescription());
    }

    @Test
    public void shouldGetBlankDescriptionIfItIsInNotTheMap() throws Exception {
        when(documentProcessor.getAttributeValueFromElements(HOMEPAGE_URL, CSS_SELECTOR, HREF_ATTRIBUTE_NAME)).thenReturn(singletonList("link one"));
        when(documentProcessor.getMapOfElementTexts("link one", PRODUCT_CONTENT_DATA_KEY_CSS_SELECTOR, PRODUCT_CONTENT_DATA_VALUE_CSS_SELECTOR))
                .thenReturn(of("some other key", "test description"));
        Result results = target.getSainsburysGroceryList();
        assertNotNull("results should not be null", results);
        assertEquals(1, results.getProducts().size());
        assertEquals("", results.getProducts().get(0).getDescription());
    }

    @Test
    public void shouldGetBlankDescriptionIfItIsInTheMapWithValueOfNull() throws Exception {
        when(documentProcessor.getAttributeValueFromElements(HOMEPAGE_URL, CSS_SELECTOR, HREF_ATTRIBUTE_NAME)).thenReturn(singletonList("link one"));
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(DESCRIPTION_KEY, null);
        when(documentProcessor.getMapOfElementTexts("link one", PRODUCT_CONTENT_DATA_KEY_CSS_SELECTOR, PRODUCT_CONTENT_DATA_VALUE_CSS_SELECTOR))
                .thenReturn(resultMap);
        Result results = target.getSainsburysGroceryList();
        assertNotNull("results should not be null", results);
        assertEquals(1, results.getProducts().size());
        assertEquals("", results.getProducts().get(0).getDescription());
    }
}