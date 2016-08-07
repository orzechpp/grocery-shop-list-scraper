package com.orzechp.sainsburys.provider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsoupDocumentProvider {

    private Document currentDocument = null;

    public Document provideDocument(String url) throws IOException {
      return  (currentDocument == null || !currentDocument.location().equals(url)) ? currentDocument = Jsoup.connect(url).get() : currentDocument;
    }
}