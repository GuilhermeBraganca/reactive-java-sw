package br.com.ada.reactivejavasw.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    private String name;
    private Integer age;
    private String email;
}