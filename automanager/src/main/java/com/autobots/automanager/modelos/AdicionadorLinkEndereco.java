package com.autobots.automanager.modelos;

import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.entidades.Endereco;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<Endereco> {

    @Override
    public void adicionarLink(List<Endereco> lista) {
        for (Endereco endereco : lista) {
            adicionarLink(endereco);
        }
    }

    @Override
    public void adicionarLink(Endereco objeto) {
        long id = objeto.getId();

        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .obterEndereco(id))
                .withSelfRel();
        objeto.add(linkProprio);

        Link linkColecao = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .obterEnderecos())
                .withRel("enderecos");

        objeto.add(linkColecao);
    }
}
