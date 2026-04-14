package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.AdicionadorLinkDocumento;
import com.autobots.automanager.modelos.DocumentoAtualizador;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoControle {

    @Autowired
    private DocumentoRepositorio repositorio;

    @Autowired
    private AdicionadorLinkDocumento adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
        return repositorio.findById(id)
                .map(documento -> {
                    adicionadorLink.adicionarLink(documento);
                    return ResponseEntity.ok(documento);
                })
                .orElseGet(() -> ResponseEntity.<Documento>notFound().build());
//        
    }

    @GetMapping
    public ResponseEntity<List<Documento>> obterDocumentos() {
        List<Documento> documentos = repositorio.findAll();
        adicionadorLink.adicionarLink(documentos);
        return ResponseEntity.ok(documentos);
    }

    @PostMapping
    public ResponseEntity<Documento> cadastrarDocumento(@RequestBody Documento documento) {
        documento.setId(null);
        Documento salvo = repositorio.save(documento);
        adicionadorLink.adicionarLink(salvo);

        URI location = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .obterDocumento(salvo.getId()))
                .toUri();

        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> atualizarDocumento(
            @PathVariable long id,
            @RequestBody Documento atualizacao) {

        return repositorio.findById(id)
                .map(documento -> {
                    new DocumentoAtualizador().atualizar(documento, atualizacao);
                    Documento salvo = repositorio.save(documento);
                    adicionadorLink.adicionarLink(salvo);
                    return ResponseEntity.ok(salvo);
                })
                .orElseGet(() -> ResponseEntity.<Documento>notFound().build());
    }
}
