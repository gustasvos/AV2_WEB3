package com.autobots.automanager.controles;

import java.net.URI;
import java.util.List;

import com.autobots.automanager.modelos.AdicionadorLinkTelefone;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.TelefoneAtualizador;

@RestController
@RequestMapping("/telefones")
public class TelefoneControle {

    @Autowired
    private TelefoneRepositorio repositorio;

    @Autowired
    private AdicionadorLinkTelefone adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Telefone> obterTelefone(@PathVariable long id) {
        return repositorio.findById(id)
                .map(telefone -> {
                    adicionadorLink.adicionarLink(telefone);
                    return ResponseEntity.ok(telefone);
                })
                .orElseGet(() -> ResponseEntity.<Telefone>notFound().build());
//
    }

    @GetMapping
    public ResponseEntity<List<Telefone>> obterTelefones() {
        List<Telefone> telefones = repositorio.findAll();
        adicionadorLink.adicionarLink(telefones);
        return ResponseEntity.ok(telefones);
    }

    @PostMapping
    public ResponseEntity<Telefone> cadastrarTelefone(@RequestBody Telefone telefone) {
        telefone.setId(null);
        Telefone salvo = repositorio.save(telefone);
        adicionadorLink.adicionarLink(salvo);

        URI location = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(TelefoneControle.class)
                        .obterTelefone(salvo.getId()))
                .toUri();

        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Telefone> atualizarTelefone(
            @PathVariable long id,
            @RequestBody Telefone atualizacao) {

        return repositorio.findById(id)
                .map(telefone -> {
                    new TelefoneAtualizador().atualizar(telefone, atualizacao);
                    Telefone salvo = repositorio.save(telefone);
                    adicionadorLink.adicionarLink(salvo);
                    return ResponseEntity.ok(salvo);
                })
                .orElseGet(() -> ResponseEntity.<Telefone>notFound().build());
    }
}
