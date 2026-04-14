package com.autobots.automanager.controles;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelos.AdicionadorLinkCliente;
import com.autobots.automanager.modelos.ClienteAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/clientes")
public class ClienteControle {
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private AdicionadorLinkCliente adicionadorLink;

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> obterCliente(@PathVariable long id) {
		return repositorio.findById(id).map(cliente -> {
			adicionadorLink.adicionarLink(cliente);
			return ResponseEntity.ok(cliente);
		})
				.orElse(ResponseEntity.notFound().build());
//
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> obterClientes() {
		List<Cliente> clientes = repositorio.findAll();
		adicionadorLink.adicionarLink(clientes);
		return ResponseEntity.ok(clientes);
//		
	}

	@PostMapping
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
		cliente.setId(null);
		Cliente salvo = repositorio.save(cliente);
		adicionadorLink.adicionarLink(salvo);

		URI location = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(ClienteControle.class)
				.obterCliente(salvo.getId()))
				.toUri();

		return ResponseEntity.created(location).body(salvo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizarCliente(
			@PathVariable long id,
			@RequestBody Cliente atualizacao) {

		return repositorio.findById(id)
				.map(cliente -> {
					new ClienteAtualizador().atualizar(cliente, atualizacao);
					Cliente salvo = repositorio.save(cliente);
					adicionadorLink.adicionarLink(salvo);
					return ResponseEntity.ok(salvo);
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> excluirCliente(@PathVariable long id) {
		return repositorio.findById(id)
				.map(cliente -> {
					repositorio.delete(cliente);
					return ResponseEntity.<Void>noContent().build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}
