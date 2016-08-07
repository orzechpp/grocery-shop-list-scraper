# Grocery Shop List Scraper

### Purpose of  this application
Grocery Shop List Scraper is a console application that scrapes the Sainsbury’s grocery site - Ripe Fruits page and prints out 
a product list in json representation into console and after successful invocation  writes the output to json file
product-list.json which can be found in root.

### Running the application

    cd grocery-shop-list-scraper-master
    mvn compile  

To get executable JAR file run

    mvn package

Note: Process can be invoked  using  one command mvn clean install.
After successful invocation please run:

    java -jar target/grocery-shop-list-scraper-1.0.0-SNAPSHOT.jar
 
 Note: Application can by started also running Main from IDE.

### Testing

To execute tests run:

    cd grocery-shop-list-scraper-master
    mvn test


    T E S T S
   -------------------------------------------------------
   Running com.orzechp.sainsburys.dom.DocumentProcessorImplTest
   Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.734 sec - in com.orzechp.sainsburys.dom.DocumentProcessorImplTest
   Running com.orzechp.sainsburys.output.ProductListOutputImplTest
   Json output:: {"results":[{"title":"Sainsbury's Apricot Ripe & Ready x5","size":"38 KB","unit_price":3.5,"description":"Apricots"},{"title":"Sainsbury's Avocado Ripe & Ready XL Loose 300g","size":"38 KB","unit_price":1.5,"description":"Avocados"}],"total":5.0}
   Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.078 sec - in com.orzechp.sainsburys.output.ProductListOutputImplTest
   Running com.orzechp.sainsburys.provider.JsoupDocumentProviderTest
   Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.081 sec - in com.orzechp.sainsburys.provider.JsoupDocumentProviderTest
   Running com.orzechp.sainsburys.service.GroceryShopListServiceImplTest
   Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.16 sec - in com.orzechp.sainsburys.service.GroceryShopListServiceImplTest
   
   Results :
   
   Tests run: 27, Failures: 0, Errors: 0, Skipped: 0
   
   [INFO] ------------------------------------------------------------------------
   [INFO] BUILD SUCCESS
   [INFO] ------------------------------------------------------------------------
   [INFO] Total time: 15.005 s
   [INFO] Finished at: 2016-06-28T09:41:19+01:00
   [INFO] Final Memory: 25M/214M

