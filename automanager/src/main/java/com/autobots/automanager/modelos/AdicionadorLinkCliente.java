package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.entidades.Cliente;

@Component
public class AdicionadorLinkCliente implements AdicionadorLink<Cliente> {

	@Autowired
	private AdicionadorLinkDocumento adicionadorLinkDocumento;
	@Autowired
	private AdicionadorLinkTelefone adicionadorLinkTelefone;
	@Autowired
	private AdicionadorLinkEndereco adicionadorLinkEndereco;


	@Override
	public void adicionarLink(List<Cliente> lista) {
		for (Cliente cliente : lista) {
			adicionarLink(cliente);
		}
	}

	@Override
	public void adicionarLink(Cliente objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControle.class)
						.obterCliente(objeto.getId()))
				.withSelfRel();
		objeto.add(linkProprio);

		Link linkColecao = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControle.class)
						.obterClientes())
				.withRel("clientes");
		objeto.add(linkColecao);

		if (objeto.getDocumentos() != null) {
			adicionadorLinkDocumento.adicionarLink(objeto.getDocumentos());
		}
		if (objeto.getTelefones() != null) {
			adicionadorLinkTelefone.adicionarLink(objeto.getTelefones());
		}
		if (objeto.getEndereco() != null) {
			adicionadorLinkEndereco.adicionarLink(objeto.getEndereco());
		}
	}
}
