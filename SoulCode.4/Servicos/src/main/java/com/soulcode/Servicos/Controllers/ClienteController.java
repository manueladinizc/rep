package com.soulcode.Servicos.Controllers;

//Criação de End Points


import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin   //portas diferentes sendo acessada, erro de cross
@RestController
@RequestMapping("servicos") //mapeamento das requisições começam em serviços, a rota base começa em serviços,
// Segue o tipo Rest
public class ClienteController {
    //Injeção de dependencia
    @Autowired
    ClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> mostrarTodosClientes() {
        List<Cliente> cliente = clienteService.mostrarTodosClientes();
        return cliente;
    }
    // EndPoint para retornar pelo ID. RESPONSEENTITY, resposta completa, cabeçario, status e body(dados do funcionário)
    @GetMapping("/clientes/{idCliente}")
    public ResponseEntity<Cliente> mostrarUmClientePelaId(@PathVariable Integer idCliente){
        Cliente cliente = clienteService.mostrarUmClientePeloId(idCliente);
        return ResponseEntity.ok().body(cliente);
    }

    //EndPoint para buscar o cliente por email
    @GetMapping("/clientesEmail/{email}")
    public ResponseEntity<Cliente> mostrarUmClientePeloEmail(@PathVariable String email) {
        Cliente cliente = clienteService.mostrarUmClientePeloEmail(email);
        return ResponseEntity.ok().body(cliente);

    }

    //restbody, os dados vão ser passado pelo corpo da requisição
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente){
        //o funcionario será salvo na tabela database
        //
        cliente= clienteService.cadastrarCliente(cliente);
        URI novaUri =
                ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(cliente.getIdCliente()).toUri();
        return ResponseEntity.created(novaUri).body(cliente);
    }



    @PutMapping("clientes/{idCliente}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable Integer idCliente,
                                                         @RequestBody Cliente cliente){
        cliente.setIdCliente(idCliente);
        cliente = clienteService.editarCliente(cliente);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("clientes/{idCliente}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Integer idCliente){
        clienteService.excluirCliente(idCliente);
        return ResponseEntity.noContent().build();
    }




}
