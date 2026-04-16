package com.autobots.automanager.servicos;

import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.AdicionadorLinkEndereco;
import com.autobots.automanager.modelos.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class EnderecoServico {
    @Autowired
    private EnderecoRepositorio repositorio;
    @Autowired
    private AdicionadorLinkEndereco adicionadorLinkEndereco;

    public ResponseEntity<Endereco> obterEndereco(long id) {
        return repositorio.findById(id)
                .map(endereco -> {
                    adicionadorLinkEndereco.adicionarLink(endereco);
                    return ResponseEntity.ok(endereco);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Endereco>> obterEnderecos() {
        List<Endereco> enderecos = repositorio.findAll();
        adicionadorLinkEndereco.adicionarLink(enderecos);
        return ResponseEntity.ok(enderecos);
    }

    public ResponseEntity<Endereco> cadastrarEndereco(Endereco endereco) {
        endereco.setId(null);
        Endereco salvo = repositorio.save(endereco);
        adicionadorLinkEndereco.adicionarLink(salvo);

        URI location = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .obterEndereco(salvo.getId()))
                .toUri();

        return ResponseEntity.created(location).body(salvo);
    }

    public ResponseEntity<Endereco> atualizarEndereco(long id, Endereco atualizacao) {
        return repositorio.findById(id)
                .map(endereco -> {
                    new EnderecoAtualizador().atualizar(endereco, atualizacao);
                    Endereco salvo = repositorio.save(endereco);
                    adicionadorLinkEndereco.adicionarLink(salvo);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
