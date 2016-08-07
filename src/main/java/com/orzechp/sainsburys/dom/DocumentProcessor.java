package com.orzechp.sainsburys.dom;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DocumentProcessor {
    int getSizeOfPage(String url) throws IOException;
    List<String> getElementsText(String url, String cssSelector) throws IOException;
    List<String> getAttributeValueFromElements(String url, String cssSelector, String attribute) throws IOException;
    Map<String, String> getMapOfElementTexts(String url, String keyCssSelector, String valueCssSelector) throws IOException;
}
