package br.com.ada.reactivejavasw.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "products")
public class Product {

    @Id
    private String id = new ObjectId().toString();

    private String name;
    private String Description;
    private BigDecimal amount;
    @Indexed(unique = true, background = true)
    private String code;
    public Product(String name, String description, BigDecimal amount, String code) {
        this.name = name;
        this.code = code;
        Description = description;
        this.amount = amount;
    }
}
