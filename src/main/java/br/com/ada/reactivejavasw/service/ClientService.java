package br.com.ada.reactivejavasw.service;

import br.com.ada.reactivejavasw.converter.ClientConverter;
import br.com.ada.reactivejavasw.dto.ClientDTO;
import br.com.ada.reactivejavasw.dto.ResponseDTO;
import br.com.ada.reactivejavasw.model.Client;
import br.com.ada.reactivejavasw.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ClientService {


    @Autowired
    private ClientConverter clientConverter;


    @Autowired
    private ClientRepository clientRepository;



    public Mono<ResponseDTO> create(ClientDTO clientDTO) {

        //converter dto em model
        Client client = this.clientConverter.toClient(clientDTO);
        //salvar na base de dados o model
        Mono<Client> clientMono = this.clientRepository.save(client);
        //retornar o dado salvo como dto
        return clientMono
                .map((productDocument) -> new ResponseDTO("Cliente cadastrado com sucesso!",
                        this.clientConverter.toClientDTO(productDocument),
                        LocalDateTime.now()))
                .onErrorReturn(new ResponseDTO("Erro ao cadastrar cliente",
                        new ClientDTO(),
                        LocalDateTime.now()));


    }

    public Flux<ResponseDTO<ClientDTO>> getAll() {
        Flux<Client> productFlux = this.clientRepository.findAll();
        return productFlux
                .map(client -> new ResponseDTO("Listagem de Clientes retornada com sucesso!",
                        this.clientConverter.toClientDTO(client),
                        LocalDateTime.now()
                ));
    }

    public Mono<ResponseDTO<ClientDTO>> findByEmail(String email) {
        Mono<Client> productMono = this.clientRepository.findByEmail(email);
        return productMono
                .map(product -> new ResponseDTO("Busca por code retornada com sucesso!",
                        this.clientConverter.toClientDTO(product),
                        LocalDateTime.now()
                ));

    }

    public Mono<ResponseDTO> update(ClientDTO clientDTO) {

        Mono<Client> clientMono = this.clientRepository.findByEmail(clientDTO.getEmail());

        return clientMono.flatMap((existingClient) -> {
            existingClient.setAge(clientDTO.getAge());
            existingClient.setName(clientDTO.getName());
            existingClient.setEmail(clientDTO.getEmail());

            return this.clientRepository.save(existingClient);
        }).map(client -> new ResponseDTO<>("Cliente alterado com sucesso!",
                this.clientConverter.toClientDTO(client),
                LocalDateTime.now()));
    }

    public Mono<ResponseDTO> delete(String email) {
        return this.clientRepository
                .deleteByEmail(email).map((client) -> new ResponseDTO<>("Client removido com sucesso!",
                        null,
                        LocalDateTime.now()));
    }
}
