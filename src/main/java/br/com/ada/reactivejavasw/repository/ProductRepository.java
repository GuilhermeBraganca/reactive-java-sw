package br.com.ada.reactivejavasw.repository;

import br.com.ada.reactivejavasw.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    public Mono<Product> findByCode(String code);

    public Mono<Product> deleteByCode(String code);



}
