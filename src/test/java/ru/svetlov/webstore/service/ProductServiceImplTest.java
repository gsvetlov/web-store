package ru.svetlov.webstore.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.svetlov.webstore.service.impl.ProductServiceImpl;


public class ProductServiceImplTest {

    @Test
    public void getById_withInvalidId_Throws() {
        Long invalidId = -1L;

        ProductService productService = new ProductServiceImpl(null, null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.getById(invalidId));
    }

    @Test
    public void create_withNullOrBlankTitle_Throws() {
        double validCost = 0.1d;

        String emptyTitle = "";
        String blankTitle = " ";

        ProductService productService = new ProductServiceImpl(null, null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.create(null, validCost));
        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.create(emptyTitle, validCost));
        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.create(blankTitle, validCost));
    }

    @Test
    public void create_withNegativeCost_Trows() {
        double negativeCost = -0.1d;

        ProductService productService = new ProductServiceImpl(null, null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.create("item", negativeCost));
    }

    @Test
    public void deleteById_withInvalidId_Throws() {
        Long invalidId = -1L;

        ProductService productService = new ProductServiceImpl(null, null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.deleteById(invalidId));
    }
}
