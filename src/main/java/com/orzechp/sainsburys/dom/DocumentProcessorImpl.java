package com.orzechp.sainsburys.dom;

import com.orzechp.sainsburys.provider.JsoupDocumentProvider;
import com.orzechp.sainsburys.provider.UrlConnectionProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class DocumentProcessorImpl implements DocumentProcessor {
    public static final int ZERO = 0;
    @Autowired
    private JsoupDocumentProvider jsoupDocumentProvider;
    @Autowired
    private UrlConnectionProvider urlConnectionProvider;

    @Override
    public int getSizeOfPage(String url) throws IOException {
        URLConnection urlConnection = urlConnectionProvider.provideURLConnection(url);
        return urlConnection.getContentLength();
    }

    @Override
    public List<String> getElementsText(String url, String cssSelector) throws IOException {
        Elements elements = selectElements(url, cssSelector);
        List<String> texts = new ArrayList<>();
        elements.forEach(e -> {
            texts.add(e.text());
        });
        return texts;
    }

    @Override
    public List<String> getAttributeValueFromElements(String url, String cssSelector, String attribute) throws IOException {
        Elements elements = selectElements(url, cssSelector);
        List<String> attributeValues = new ArrayList<>();
        elements.forEach(e -> {
            if (e.hasAttr(attribute)) {
                attributeValues.add(e.attr(attribute));
            }
        });
        return attributeValues;
    }

    @Override
    public Map<String, String> getMapOfElementTexts(String url, String keyCssSelector, String valueCssSelector) throws IOException {
        Elements keyElements = selectElements(url, keyCssSelector);
        Elements valueElements = selectElements(url, valueCssSelector);

        Map<String, String> valueMap = new HashMap<>();
        IntStream.range(ZERO, keyElements.size()).boxed().forEach(k -> {
            valueMap.put(keyElements.get(k).text(), valueElements.size() > k ? valueElements.get(k).text() : null);
        });
        return valueMap;
    }

    private Elements selectElements(String url, String cssSelector) throws IOException {
        Document document = jsoupDocumentProvider.provideDocument(url);
        return document.select(cssSelector);
    }
}
