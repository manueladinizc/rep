package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Services.ChamadoService;
import com.soulcode.Servicos.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;



@CrossOrigin   //portas diferentes sendo acessada, erro de cross
@RestController
@RequestMapping("servicos") //mapeamento das requisições começam em serviços, a rota base começa em serviços,
// Segue o tipo Rest
public class ChamadoController {


    //Injeção de dependencia
    @Autowired
    ChamadoService chamadoService;

    @GetMapping("/chamados")
    public List<Chamado> mostrarTodosChamados() {
        List<Chamado> chamados = chamadoService.mostrarTodosChamados();
        return chamados;
    }
    // EndPoint para retornar pelo ID. RESPONSEENTITY, resposta completa, cabeçario, status e body(dados do funcionário)
    @GetMapping("/chamados/{idChamado}")
    public ResponseEntity<Chamado> mostrarUmChamadoPeloId(@PathVariable Integer idChamado){
        Chamado chamado = chamadoService.mostrarUmChamadoPeloId(idChamado);
        return ResponseEntity.ok().body(chamado);
    }



    @GetMapping ("/chamadosPeloCliente/{idCliente}")
    public List<Chamado> buscarChamadosPeloCliente(@PathVariable Integer idCliente){
        List<Chamado> chamados = chamadoService.buscarChamadosPeloCliente(idCliente);
        return chamados;
    }

    @GetMapping ("/chamadosPeloFuncionario/{idCliente}")
    public List<Chamado> buscarChamadosPeloFuncionario(@PathVariable Integer idFuncionario){
        List<Chamado> chamados = chamadoService.buscarChamadosPeloFuncionario(idFuncionario);
        return chamados;
    }

    @GetMapping("/chamadosPeloStatus")
    public List<Chamado> buscarChamadosPeloStatus(@RequestParam("status") String status){
        List<Chamado> chamados = chamadoService.buscarChamadosPeloStatus(status);
        return chamados;
    }

    @GetMapping("/chamadosPorIntervaloData")
    public List<Chamado> buscarPorIntervaloData(@RequestParam("data1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data1,
                                                @RequestParam("data2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data2){
        List<Chamado> chamados = chamadoService.buscarPorIntervaloData(data1,data2);
        return chamados;
    }


    @PostMapping("/chamados/{idCliente}")
    public ResponseEntity<Chamado> cadastrarCahamado(@PathVariable Integer idCliente, @RequestBody Chamado chamado ){
        chamado = chamadoService.cadastrarChamado(chamado, idCliente);
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(chamado.getIdChamado()).toUri();
        return ResponseEntity.created(novaUri).body(chamado);

    }

    //vamos mapear o serviço de excluir um chamado
    @DeleteMapping ("/chamados/{idChamado}")
    public ResponseEntity<Void> excluirChamado (@PathVariable Integer idChamado){
        chamadoService.excluirChamado(idChamado);
        return ResponseEntity.noContent().build();

    }

    //vamos mapear o serviço de editar um chamado
    //para edição precisamos do metodo http do tipo put

    @PutMapping("/chamados/{idChamado}")
    public ResponseEntity<Chamado> editarChamado(@PathVariable Integer idChamado,
                                                 @RequestBody Chamado chamado){
        chamado.setIdChamado(idChamado);
        chamadoService.editarChamado(chamado, idChamado);
        return ResponseEntity.ok().build();
    }

    //vamos fazer o mapeamento do método de atribuir um funcionario a um determidado chamado

    @PutMapping("/chamadosAtribuirFuncionario/{idChamado}/{idFuncionario}")
    public ResponseEntity<Chamado> atribuirFuncionario(@PathVariable Integer idChamado,
                                                       @PathVariable Integer idFuncionario){
        chamadoService.atribuirFuncionario(idChamado, idFuncionario);
        return ResponseEntity.noContent().build();
    }


    // vamos contruir o mapeameto do mátodo para modificar o  status de um chamado
    @PutMapping("/chamadosModificarStatus/{idChamado}")
    public ResponseEntity<Chamado> modificarStatus(@PathVariable Integer idChamado,
                                                   @RequestParam("status") String status){
        chamadoService.modificarStatus(idChamado, status);
        return ResponseEntity.noContent().build();
    }
}



