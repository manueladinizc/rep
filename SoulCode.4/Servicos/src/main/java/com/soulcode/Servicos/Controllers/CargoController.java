package com.soulcode.Servicos.Controllers;


import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")
public class CargoController {

    @Autowired
    CargoService cargoService;

    @GetMapping("/cargos")
    public List<Cargo> mostrarTodosCargos() {
        List<Cargo> cargo = cargoService.mostrarTodosCargos();
        return cargo;
    }


    @GetMapping("/cargos/{idCargo}")
    public ResponseEntity<Cargo> mostrarUmCargoPelaId(@PathVariable Integer idCargo){
        Cargo cargo = cargoService.mostrarUmCargoPeloId(idCargo);
        return ResponseEntity.ok().body(cargo);
    }



    @PostMapping("/cargos")
    public ResponseEntity<Cargo> cadastrarCargo(@RequestBody Cargo cargo){
        //o funcionario será salvo na tabela database

        cargo = cargoService.cadastrarCargo(cargo);
        URI novaUri =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cargo.getIdCargo()).toUri();
        return ResponseEntity.created(novaUri).body(cargo);
    }

    //vamos      mapear o serviço de excluir um chamado
    @DeleteMapping ("/cargos/{idCargo}")
    public ResponseEntity<Void> excluirCargo (@PathVariable Integer idCargo){
        cargoService.excluirCargo(idCargo);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/cargos/{idCargo}")
    public ResponseEntity<Cargo> editarCargo (@PathVariable Integer idCargo,
                                                         @RequestBody Cargo cargo){
        cargo.setIdCargo(idCargo);
        cargoService.editarCargo(cargo);
        return ResponseEntity.ok().body(cargo);

    }

}



