package br.com.ada.reactivejavasw.controller;

import br.com.ada.reactivejavasw.converter.ProductConverter;
import br.com.ada.reactivejavasw.dto.ProductDTO;
import br.com.ada.reactivejavasw.dto.ResponseDTO;
import br.com.ada.reactivejavasw.repository.ProductRepository;
import br.com.ada.reactivejavasw.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductConverter productConverter;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(description = "Create a product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody())
    public Mono<ResponseDTO> create(@RequestBody ProductDTO productDTO) {
        return this.productService.create(productDTO);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(description = "Find all products")
    public Flux<ResponseDTO<ProductDTO>> getAll() {
        return this.productService.getAll();
    }

    @GetMapping("{code}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(description = "Find by code of product")
    public Mono<ResponseDTO<ProductDTO>> findByCode(@PathVariable("code") String code) {
        return this.productService.findByCode(code);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(description = "Update a product")
    public Mono<ResponseDTO> update(@RequestBody ProductDTO productDTO){
        return this.productService.update(productDTO);
    }

    @DeleteMapping("{reference}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<ResponseDTO> delete(@PathVariable("code") String code) {
        return this.productService.delete(code);
    }

}
