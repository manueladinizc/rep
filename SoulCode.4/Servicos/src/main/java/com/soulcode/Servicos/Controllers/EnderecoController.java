package com.soulcode.Servicos.Controllers;


import com.soulcode.Servicos.Models.Endereco;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.EnderecoRepository;
import com.soulcode.Servicos.Services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin   //portas diferentes sendo acessada, erro de cross
@RestController
@RequestMapping("servicos")
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;




    @GetMapping("/enderecos")
    public List<Endereco> mostrarTodosEnderecos() {
        List<Endereco> endereco = enderecoService.mostrarTodosEnderecos();
        return endereco;
    }


    @GetMapping("/endereco/{idEndereco}")
    public ResponseEntity<Endereco> mostrarUmEnderecoPeloId(@PathVariable Integer idEndereco){
        Endereco enderecoCliente = enderecoService.mostrarUmEnderecoPeloId(idEndereco);
        return ResponseEntity.ok().body(enderecoCliente);
    }




    @PostMapping("/enderecos/{idCliente}")
    public ResponseEntity<Endereco> cadastrarEndereco(@PathVariable Integer idCliente,
                                                      @RequestBody Endereco endereco){
        //o funcionario será salvo na tabela database
        try{
            endereco = enderecoService.cadastrarEndereco(endereco, idCliente);
            URI novaUri =
                    ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(endereco.getIdEndereco()).toUri();
            return ResponseEntity.created(novaUri).body(endereco);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }


    @PutMapping("/enderecos/{idEndereco}")
    public ResponseEntity<Endereco> editarEndereco( @PathVariable Integer idEndereco,
                                                    @RequestBody Endereco endereco){
        endereco.setIdEndereco(idEndereco);
        enderecoService.editarEndereco(endereco);
        return   ResponseEntity.ok().body(endereco);
    }


}