package com.autobots.automanager.servicos;

import com.autobots.automanager.controles.DocumentoControle;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.AdicionadorLinkDocumento;
import com.autobots.automanager.modelos.DocumentoAtualizador;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class DocumentoServico {
    @Autowired
    private DocumentoRepositorio repositorio;
    @Autowired
    AdicionadorLinkDocumento adicionadorLinkDocumento;

    public ResponseEntity<Documento> obterDocumento(long id) {
        return repositorio.findById(id)
                .map(documento -> {
                    adicionadorLinkDocumento.adicionarLink(documento);
                    return ResponseEntity.ok(documento);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Documento>> obterDocumentos() {
        List<Documento> documentos = repositorio.findAll();
        adicionadorLinkDocumento.adicionarLink(documentos);
        return ResponseEntity.ok(documentos);
    }

    public ResponseEntity<Documento> cadastrarDocumento(Documento documento) {
        documento.setId(null);
        Documento salvo = repositorio.save(documento);
        adicionadorLinkDocumento.adicionarLink(salvo);

        URI location = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .obterDocumento(salvo.getId()))
                .toUri();

        return ResponseEntity.created(location).body(salvo);
    }

    public ResponseEntity<Documento> atualizarDocumento(long id, Documento atualizacao) {
        return repositorio.findById(id)
                .map(documento -> {
                    new DocumentoAtualizador().atualizar(documento, atualizacao);
                    Documento salvo = repositorio.save(documento);
                    adicionadorLinkDocumento.adicionarLink(salvo);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
