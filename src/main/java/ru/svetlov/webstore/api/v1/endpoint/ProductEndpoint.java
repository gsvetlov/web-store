package ru.svetlov.webstore.api.v1.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.svetlov.webstore.dto.soap.GetAllProductsRequest;
import ru.svetlov.webstore.dto.soap.GetAllProductsResponse;
import ru.svetlov.webstore.dto.soap.GetProductByIdRequest;
import ru.svetlov.webstore.dto.soap.GetProductByIdResponse;
import ru.svetlov.webstore.util.ProductServiceSoapAdapter;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://webstore.svetlov.ru/ws/products";

    private final ProductServiceSoapAdapter productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductByIdRequest(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productService.getById(request.getId()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        response.getProducts().addAll(productService.getAll());
        return response;
    }
}
