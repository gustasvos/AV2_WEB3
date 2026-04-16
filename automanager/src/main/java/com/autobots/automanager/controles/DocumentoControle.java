package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.servicos.DocumentoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoControle {

    @Autowired
    private DocumentoServico servico;

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
        return servico.obterDocumento(id);
//
    }

    @GetMapping
    public ResponseEntity<List<Documento>> obterDocumentos() {
        return servico.obterDocumentos();
//
    }

    @PostMapping
    public ResponseEntity<Documento> cadastrarDocumento(@RequestBody Documento documento) {
        return servico.cadastrarDocumento(documento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> atualizarDocumento(
            @PathVariable long id,
            @RequestBody Documento atualizacao) {
        return servico.atualizarDocumento(id, atualizacao);
    }
}
