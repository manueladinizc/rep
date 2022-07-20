package com.soulcode.Servicos.Services;


import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {


    @Autowired
    CargoRepository cargoRepository;

    @Cacheable("cargosCache")
    public List<Cargo> mostrarTodosCargos(){

        return cargoRepository.findAll();
    }

    @Cacheable(value="cargosCache", key = "#idCargo")
    public Cargo mostrarUmCargoPeloId(Integer idCargo){

        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return cargo.orElseThrow();

    }

    @CachePut(value = "cargosCache", key = "#cargos.idCargo")
    public Cargo cadastrarCargo(Cargo cargo){
        //por precaução vamos colocar um id do funcionario como nullo
        cargo.setIdCargo(null);
        return cargoRepository.save(cargo);
    }

    @CacheEvict(value = "cargosCache", key = "#idCargo", allEntries = true)
    public void excluirCargo(Integer idCargo){
        cargoRepository.deleteById(idCargo);

         }



    @CachePut(value = "cargosCache", key = "#cargos.idCargo")
    public Cargo editarCargo(Cargo cargo){
        return cargoRepository.save(cargo);

    }


}
