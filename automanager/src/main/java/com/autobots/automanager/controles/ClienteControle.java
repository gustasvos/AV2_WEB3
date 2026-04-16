package com.autobots.automanager.controles;

import java.util.List;
import com.autobots.automanager.servicos.ClienteServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entidades.Cliente;

@RestController
@RequestMapping("/clientes")
public class ClienteControle {

	@Autowired
	private ClienteServico servico;

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> obterCliente(@PathVariable long id) {
		return servico.obterCliente(id);
//
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> obterClientes() {
		return servico.obterClientes();
//
	}

	@PostMapping
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
		return servico.cadastrarCliente(cliente);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizarCliente(
			@PathVariable long id,
			@RequestBody Cliente atualizacao) {
		return servico.atualizarCliente(id, atualizacao);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> excluirCliente(@PathVariable long id) {
		return servico.excluirCliente(id);
	}
}