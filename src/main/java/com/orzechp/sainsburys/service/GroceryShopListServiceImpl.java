package com.orzechp.sainsburys.service;

import com.orzechp.sainsburys.dom.DocumentProcessor;
import com.orzechp.sainsburys.domain.Product;
import com.orzechp.sainsburys.domain.Result;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import static com.orzechp.sainsburys.common.DOMConstants.*;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Log4j
public class GroceryShopListServiceImpl implements GroceryShopListService {

    public static final int ZERO = 0;
    public static final String EMPTY = "";
    public static final String PRODUCT_LIST_LOAD_ERROR = "Unable to get the list of products ";

    @Autowired
    private DocumentProcessor documentProcessor;

    @Override
    public Result getSainsburysGroceryList() throws IOException {

        List<String> productList = documentProcessor.getAttributeValueFromElements(HOMEPAGE_URL, CSS_SELECTOR, HREF_ATTRIBUTE_NAME);
        List<Product> products = new ArrayList<>();

        productList.forEach(link -> {
            Product product = new Product();
            try {
                product.setTitle(getFruitTitle(link));
                product.setSize(getWebPageSize(link));
                product.setUnitPrice(getUnitPrice(link));
                product.setDescription(getDescription(link));
                products.add(product);
            } catch (IOException e) {
                log.error(String.format("%s%s", PRODUCT_LIST_LOAD_ERROR, e.getMessage()), e);
            }
        });

        return new Result(products);
    }

    private String getFruitTitle(String link) throws IOException {
        List<String> titles = documentProcessor.getElementsText(link, TITLE_CSS_SELECTOR);
        return titles.isEmpty() ? EMPTY : titles.get(ZERO);
    }

    private String getWebPageSize(String link) throws IOException {
        int size = documentProcessor.getSizeOfPage(link);
        return FileUtils.byteCountToDisplaySize(size);
    }

    private double getUnitPrice(String link) throws IOException {
        List<String> prices = documentProcessor.getElementsText(link, PRICE_PER_UNIT_CSS_SELECTOR);

        if (prices.isEmpty()) {
            return ZERO;
        }
        Matcher matcher = PRICE_PER_UNIT_PATTERN.matcher(prices.get(ZERO));
        return (matcher.find()) ?  Double.valueOf(matcher.group()) : ZERO;

    }

    private String getDescription(String link) throws IOException {
        Map<String, String> dataContent = documentProcessor.getMapOfElementTexts(link, PRODUCT_CONTENT_DATA_KEY_CSS_SELECTOR, PRODUCT_CONTENT_DATA_VALUE_CSS_SELECTOR);
        return (dataContent.get(DESCRIPTION_KEY) == null || !dataContent.containsKey(DESCRIPTION_KEY)) ? EMPTY : dataContent.get(DESCRIPTION_KEY);
    }
}
