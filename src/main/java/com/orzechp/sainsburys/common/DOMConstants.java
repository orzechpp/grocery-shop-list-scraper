package com.orzechp.sainsburys.common;

import java.util.regex.Pattern;

public class DOMConstants {

    public static final String HOMEPAGE_URL = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
    public static final String CSS_SELECTOR = "ul.productLister a";
    public static final String HREF_ATTRIBUTE_NAME = "href";
    public static final String TITLE_CSS_SELECTOR = "div.productTitleDescriptionContainer h1";
    public static final String PRICE_PER_UNIT_CSS_SELECTOR = "p.pricePerUnit";
    public static final String PRODUCT_CONTENT_DATA_KEY_CSS_SELECTOR = "productcontent htmlcontent h3.productDataItemHeader";
    public static final String PRODUCT_CONTENT_DATA_VALUE_CSS_SELECTOR = "productcontent htmlcontent div.productText";
    public static final Pattern PRICE_PER_UNIT_PATTERN = Pattern.compile("[0-9\\.]+");
    public static final String DESCRIPTION_KEY = "Description";

}
