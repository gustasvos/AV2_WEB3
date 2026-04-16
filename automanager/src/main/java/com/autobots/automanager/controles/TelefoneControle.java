package com.autobots.automanager.controles;

import java.util.List;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.servicos.TelefoneServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telefones")
public class TelefoneControle {

    @Autowired
    private TelefoneServico servico;

    @GetMapping("/{id}")
    public ResponseEntity<Telefone> obterTelefone(@PathVariable long id) {
        return servico.obterTelefone(id);
//
    }

    @GetMapping
    public ResponseEntity<List<Telefone>> obterTelefones() {
        return servico.obterTelefones();
//
    }

    @PostMapping
    public ResponseEntity<Telefone> cadastrarTelefone(@RequestBody Telefone telefone) {
        return servico.cadastrarTelefone(telefone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Telefone> atualizarTelefone(
            @PathVariable long id,
            @RequestBody Telefone atualizacao) {
        return servico.atualizarTelefone(id, atualizacao);
    }
}
