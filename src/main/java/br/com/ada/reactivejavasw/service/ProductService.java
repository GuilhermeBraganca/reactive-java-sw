package br.com.ada.reactivejavasw.service;

import br.com.ada.reactivejavasw.converter.ProductConverter;
import br.com.ada.reactivejavasw.dto.ProductDTO;
import br.com.ada.reactivejavasw.dto.ResponseDTO;
import br.com.ada.reactivejavasw.model.Product;
import br.com.ada.reactivejavasw.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ProductService {


    @Autowired
    private ProductConverter productConverter;


    @Autowired
    private ProductRepository productRepository;



    public Mono<ResponseDTO> create(ProductDTO productDTO) {

        //converter dto em model
        Product product = this.productConverter.toProduct(productDTO);
        //salvar na base de dados o model
        Mono<Product> productMono = this.productRepository.save(product);
        //retornar o dado salvo como dto
        return productMono
                .map((productDocument) -> new ResponseDTO("Produto cadastrado com sucesso!",
                        this.productConverter.toProductDTO(productDocument),
                        LocalDateTime.now()))
                .onErrorReturn(new ResponseDTO("Erro ao cadastrar produto",
                        new ProductDTO(),
                        LocalDateTime.now()));


    }

    public Flux<ResponseDTO<ProductDTO>> getAll() {
        Flux<Product> productFlux = this.productRepository.findAll();
        return productFlux
                .map(product -> new ResponseDTO("Listagem de produtos retornada com sucesso!",
                        this.productConverter.toProductDTO(product),
                        LocalDateTime.now()
                ));
    }

    public Mono<ResponseDTO<ProductDTO>> findByCode(String code) {
        Mono<Product> productMono = this.productRepository.findByCode(code);
        return productMono
                .map(product -> new ResponseDTO("Busca por code retornada com sucesso!",
                        this.productConverter.toProductDTO(product),
                        LocalDateTime.now()
                ));

    }

    public Mono<ResponseDTO> update(ProductDTO productDTO) {

        Mono<Product> productMono = this.productRepository.findByCode(productDTO.getCode());

        return productMono.flatMap((existingProduct) -> {
            existingProduct.setCode(productDTO.getCode());
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setAmount(productDTO.getAmount());
            return this.productRepository.save(existingProduct);
        }).map(product -> new ResponseDTO<>("Produto alterado com sucesso!",
                this.productConverter.toProductDTO(product),
                LocalDateTime.now()));
    }

    public Mono<ResponseDTO> delete(String code) {
        return this.productRepository
                .deleteByCode(code).map((product) -> new ResponseDTO<>("Produto removido com sucesso!",
                        null,
                        LocalDateTime.now()));
    }
}
