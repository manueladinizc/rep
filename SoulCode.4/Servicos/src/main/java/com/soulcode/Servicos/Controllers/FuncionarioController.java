package com.soulcode.Servicos.Controllers;

//Criação de End Points

import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Services.FuncionarioService;
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
public class FuncionarioController {

//Injeção de dependencia
    @Autowired
    FuncionarioService funcionarioService;

    @GetMapping("/funcionarios")
    public List<Funcionario> mostrarTodosFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.mostrarTodosFuncionarios();
        return funcionarios;
    }
// EndPoint para retornar pelo ID. RESPONSEENTITY, resposta completa, cabeçario, status e body(dados do funcionário)
    @GetMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Funcionario> mostrarUmFuncionarioPeloId(@PathVariable Integer idFuncionario){
        Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloId(idFuncionario);
        return ResponseEntity.ok().body(funcionario);
    }
    //EndPoint para buscar o funcionário por email
    @GetMapping("/funcionariosEmail/{email}")
    public ResponseEntity<Funcionario> mostrarUmFuncionarioPeloEmail(@PathVariable String email){
        Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloEmail(email);
        return ResponseEntity.ok().body(funcionario);

    }



//restbody, os dados vão ser passado pelo corpo da requisição
  /*  @GetMapping("funcionariosDoCargo/{idCargo}")
    public List<Funcionario> mostrarTodosFuncionariosDeUmCargo(@PathVariable Integer idCargo){
        List<Funcionario> funcionario = funcionarioService.mostrarTodosFuncionariosDeUmCargo(idCargo);
        return funcionario;
    }*/

    @PostMapping("/funcionarios/{idCargo}")
    public ResponseEntity<Funcionario> cadastrarFuncionario(@PathVariable Integer idCargo,
                                                            @RequestBody Funcionario funcionario){
        //o funcionario será salvo na tabela database

        funcionario = funcionarioService.cadastrarFuncionario(funcionario, idCargo);
        URI novaUri =
                ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(funcionario.getIdFuncionario()).toUri();
        return ResponseEntity.created(novaUri).body(funcionario);
    }

    @DeleteMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable Integer idFuncionario){
        funcionarioService.excluirFuncionario(idFuncionario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Funcionario> editarFuncionario(@PathVariable Integer idFuncionario,
                                                         @RequestBody Funcionario funcionario){
        funcionario.setIdFuncionario(idFuncionario);
        funcionarioService.editarFuncionario(funcionario);
        return ResponseEntity.ok().body(funcionario);

    }

    @PutMapping("/funcionarioAtribuirCargo/{idFuncionario}/{idCargo}")
    public ResponseEntity<Funcionario> atribuirCargo(
            @PathVariable Integer idFuncionario,
            @RequestParam ("idCargo") Integer idCargo,
            @RequestBody Funcionario funcionario){

        funcionario.setIdFuncionario(idFuncionario);
        funcionarioService.atribuirCargo(funcionario, idCargo);
        return ResponseEntity.ok().body(funcionario);

    }

    @GetMapping("/funcionarioPeloCargo/{idCargo}")
    public List<Funcionario> mostrarFuncionarioPeloIdCargo(@PathVariable Integer idCargo){
        List<Funcionario> funcionarios = funcionarioService.mostrarFuncionarioPeloIdCargo(idCargo);
        return funcionarios;

        }


    }



