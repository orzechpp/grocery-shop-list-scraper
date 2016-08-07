package com.orzechp.sainsburys.output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orzechp.sainsburys.domain.Result;
import com.orzechp.sainsburys.exceptions.ProductListException;
import com.orzechp.sainsburys.provider.FileWriterProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class ProductListOutputImpl implements ProductListOutput {

    private static final String OUTPUT_FILENAME = "product-list.json";
    public static final String THERE_IS_A_ISSUE_TO_PRINT_PRODUCT_LIST = "There is issue to print  product list to the json file: ";

    @Autowired
    private FileWriterProvider fileWriterProvider;

    @Override
    public void outputProductList(Result result) throws ProductListException {

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(result);

        try {
            FileWriter writer = fileWriterProvider.provideFileWriter(OUTPUT_FILENAME);
            System.out.println("Json output:: "+ json);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new ProductListException(THERE_IS_A_ISSUE_TO_PRINT_PRODUCT_LIST
                    + e.getMessage(), e);
        }
    }
}
