package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.AdicionadorLinkEndereco;
import com.autobots.automanager.modelos.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoControle {

    @Autowired
    private EnderecoRepositorio repositorio;
    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
        return repositorio.findById(id)
                .map(endereco -> {
                    adicionadorLink.adicionarLink(endereco);
                    return ResponseEntity.ok(endereco);
                })
                .orElseGet(() -> ResponseEntity.<Endereco>notFound().build());
//        
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> obterEnderecos() {
        List<Endereco> enderecos = repositorio.findAll();
        adicionadorLink.adicionarLink(enderecos);
        return ResponseEntity.ok(enderecos);
    }

    @PostMapping
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody Endereco endereco) {
        endereco.setId(null);
        Endereco salvo = repositorio.save(endereco);
        adicionadorLink.adicionarLink(salvo);

        URI location = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .obterEndereco(salvo.getId()))
                .toUri();

        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizarEndereco(
            @PathVariable long id,
            @RequestBody Endereco atualizacao) {

        return repositorio.findById(id)
                .map(endereco -> {
                    new EnderecoAtualizador().atualizar(endereco, atualizacao);
                    Endereco salvo = repositorio.save(endereco);
                    adicionadorLink.adicionarLink(salvo);
                    return ResponseEntity.ok(salvo);
                })
                .orElseGet(() -> ResponseEntity.<Endereco>notFound().build());
    }

}