package com.orzechp.sainsburys.output;

import com.orzechp.sainsburys.domain.Product;
import com.orzechp.sainsburys.domain.Result;
import com.orzechp.sainsburys.provider.FileWriterProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileWriter;
import java.util.Arrays;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ProductListOutputImplTest {

    @Mock
    private FileWriterProvider fileWriterProvider;
    @Mock
    FileWriter fileWriter;
    @InjectMocks
    private ProductListOutputImpl target;

    @Test
    public void testOutputProduct() throws Exception {
        Result result = new Result(Arrays.asList(
                buildProduct("Apricots", "38 KB", "Sainsbury's Apricot Ripe & Ready x5", 3.5),
                buildProduct("Avocados", "38 KB", "Sainsbury's Avocado Ripe & Ready XL Loose 300g", 1.5)
        ));

        when(fileWriterProvider.provideFileWriter(anyString())).thenReturn(fileWriter);

        String expectedOutput = "{\"results\":[{\"title\":\"Sainsbury's Apricot Ripe & Ready x5\",\"size\":\"38 KB\",\"unit_price\":3.5," +
                "\"description\":\"Apricots\"},{\"title\":\"Sainsbury's Avocado Ripe & Ready XL Loose 300g\",\"size\":\"38 KB\"," +
                "\"unit_price\":1.5,\"description\":\"Avocados\"}]," +
                "\"total\":5.0}";

        target.outputProductList(result);
        verify(fileWriter, times(1)).write(expectedOutput);
        verify(fileWriter, times(1)).close();
    }

    private Product buildProduct(String description, String size, String title, double unitPrice) {
        Product product = new Product();
        product.setDescription(description);
        product.setSize(size);
        product.setTitle(title);
        product.setUnitPrice(unitPrice);
        return product;
    }
}