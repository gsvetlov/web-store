package ru.svetlov.webstore.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.repository.ProductRepository;
import ru.svetlov.webstore.service.impl.ProductServiceImpl;

import javax.validation.Validator;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ProductServiceImpl.class)
public class ProductServiceImplTest {
    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private Validator validator;

    @Test
    public void getAllByIds_ReturnsValidList() throws Exception {
        List<Long> listOfIds = Arrays.asList(1L, 3L, 5L, 45L);
        Field idField = Product.class.getDeclaredField("id");
        List<Product> expectedList = listOfIds
                .stream()
                .map(i -> setIdField(idField, i, new Product("item" + i, BigDecimal.ONE)))
                .collect(Collectors.toUnmodifiableList());
        when(productRepository.findAllByIdIn(listOfIds))
                .thenReturn(expectedList);

        List<Product> actualList = service.getAllByIdIn(listOfIds);

        verify(productRepository.findAllByIdIn(anyCollection()), atMostOnce());
        assertIterableEquals(expectedList, actualList);

    }

    private Product setIdField(Field field, Long i, Product p) {
        try {
            field.setLong(p, i);
            return p;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("this can't happen");
    }


    @Test
    public void getById_withInvalidId_Throws() {
        Long invalidId = -1L;

        assertThrows(IllegalArgumentException.class, () -> service.getById(invalidId));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void create_withBlankTitle_Throws(String invalidTitle) {
        final double validCost = 0.1d;

        assertThrows(IllegalArgumentException.class, () -> service.create(invalidTitle, validCost));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.0001, -1, Double.MIN_VALUE})
    public void create_withNegativeCost_Trows(double negativeCost) {

       assertThrows(IllegalArgumentException.class, () -> service.create("item", negativeCost));
    }

    @Test
    public void deleteById_withInvalidId_Throws() {
        Long invalidId = -1L;

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.deleteById(invalidId));
    }
}
