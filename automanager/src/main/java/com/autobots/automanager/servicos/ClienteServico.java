package com.autobots.automanager.servicos;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelos.AdicionadorLinkCliente;
import com.autobots.automanager.modelos.ClienteAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class ClienteServico {

    @Autowired
    private ClienteRepositorio repositorio;
    @Autowired
    private AdicionadorLinkCliente adicionadorLink;

    public ResponseEntity<Cliente> obterCliente(long id) {
        return repositorio.findById(id)
                .map(cliente -> {
                    adicionadorLink.adicionarLink(cliente);
                    return ResponseEntity.ok(cliente);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Cliente>> obterClientes() {
        List<Cliente> clientes = repositorio.findAll();
        adicionadorLink.adicionarLink(clientes);
        return ResponseEntity.ok(clientes);
    }

    public ResponseEntity<Cliente> cadastrarCliente(Cliente cliente) {
        cliente.setId(null);
        Cliente salvo = repositorio.save(cliente);
        adicionadorLink.adicionarLink(salvo);

        URI location = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ClienteControle.class)
                        .obterCliente(salvo.getId()))
                .toUri();

        return ResponseEntity.created(location).body(salvo);
    }

    public ResponseEntity<Cliente> atualizarCliente(long id, Cliente atualizacao) {
        return repositorio.findById(id)
                .map(cliente -> {
                    new ClienteAtualizador().atualizar(cliente, atualizacao);
                    Cliente salvo = repositorio.save(cliente);
                    adicionadorLink.adicionarLink(salvo);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Object> excluirCliente(long id) {
        return repositorio.findById(id)
                .map(cliente -> {
                    repositorio.delete(cliente);
                    return ResponseEntity.<Void>noContent().build();
                })
                .orElseGet(() -> ResponseEntity.<Void>notFound().build());
    }
}
