package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.AdicionadorLinkEndereco;
import com.autobots.automanager.modelos.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.servicos.EnderecoServico;
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
    private EnderecoServico servico;

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
        return servico.obterEndereco(id);
//
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> obterEnderecos() {
        return servico.obterEnderecos();
//
    }

    @PostMapping
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody Endereco endereco) {
        return servico.cadastrarEndereco(endereco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizarEndereco(
            @PathVariable long id,
            @RequestBody Endereco atualizacao) {
        return servico.atualizarEndereco(id, atualizacao);
    }

}