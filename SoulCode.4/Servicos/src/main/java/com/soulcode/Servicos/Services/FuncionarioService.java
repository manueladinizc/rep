package com.soulcode.Servicos.Services;


import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.CargoRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import com.soulcode.Servicos.Services.Exceptions.DataIntegrityViolationException;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// quando se fala em serviços, estamos falando dos metodos do crud da tabela
@Service
public class FuncionarioService {

    //PARA OS METODOS DA INTERFACE serem usados em outra classe, usamos o Autowired. Injeção de dependencia
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    CargoRepository cargoRepository;


    // primeiro serviço na tabela de funcionario vai ser a leitura de todos os funcionarios cadastrados.
    //findall -> método do spring Data JPA -> busca todos os registros de uma tabela

    @Cacheable(value = "funcionariosCache")
    public List<Funcionario> mostrarTodosFuncionarios(){

        return funcionarioRepository.findAll();
    }


    //vamos criar mais um serviço relacionado ao funcionário
    //criar um serviço buscar apenas um funcionário pelo seu ID(chave primária)

        @Cacheable(value = "funcionariosCache", key = "#idFuncionario")
    public Funcionario mostrarUmFuncionarioPeloId(Integer idFuncionario){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return funcionario.orElseThrow( () -> new EntityNotFoundException("Funcionario não cadastrado:" + idFuncionario));

    }

//vamos criar mais um serviço para buscar um funcionario pelo seu email
//Optional serve para evitar o travamento se o id buscado não existir, trata o not finding
    //O return ElseThrow lança uma excessão

    @Cacheable(value = "funcionariosCache", key= "idFuncionario")
    public Funcionario mostrarUmFuncionarioPeloEmail(String email){
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
        return funcionario.orElseThrow();
    }

   /* public List<Funcionario> mostrarTodosFuncionariosDeUmCargo(Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return funcionarioRepository.findByCargo(idCargo);
    }*/

    //vamos criar um serviço para cadastrar um novo funcionario

/*
    public Funcionario cadastrarFuncionario(Funcionario funcionario, Integer idCargo){
        //por precaução vamos colocar um id do funcionario como nullo
    funcionario.setIdFuncionario(null);

    Optional<Cargo> cargo = cargoRepository.findById(idCargo);
    funcionario.setCargo(cargo.get());
    return funcionarioRepository.save(funcionario);
    }
*/
    @CachePut(value = "funcionariosCache", key = "#funcionario.idFuncionario")
    public Funcionario cadastrarFuncionario(Funcionario funcionario, Integer idCargo) throws DataIntegrityViolationException {
        //só por precaução nós vamos colocar o id do funcionário como nullo
        funcionario.setIdFuncionario(null);
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        funcionario.setCargo(cargo.get());
        return funcionarioRepository.save(funcionario);
    }

/*    public Funcionario cadastrarFuncionario(Funcionario funcionario, Integer idCargo){
        try {
            Cargo cargo = cargoRepository.findById(idCargo).get();
            funcionario.setCargo(cargo);
            return funcionarioRepository.save(funcionario);
        }catch (Exception e){
            throw new DataIntegrityViolationException("Erro ao cadastrar funcionário");
        }

    }*/

    @CacheEvict(value = "funcionariosCache", key = "#idFuncionarios", allEntries = true)
    public void excluirFuncionario(Integer idFuncionario){
        //mostrarUmFuncionarioPeloId(idFuncionario);
        funcionarioRepository.deleteById(idFuncionario);
    }

    @CachePut(value = "funcionariosCache", key = "#funcionario.idFuncionarios")
    public Funcionario editarFuncionario(Funcionario funcionario){
        return funcionarioRepository.save(funcionario);

    }

    @CachePut(value = "funcionariosCache")
    public Funcionario salvarFoto(Integer idFuncionario, String caminhoFoto){
        Funcionario funcionario  = mostrarUmFuncionarioPeloId(idFuncionario);
        funcionario.setFoto(caminhoFoto);
        return funcionarioRepository.save(funcionario);
    }



    public Funcionario atribuirCargo (Funcionario funcionario, Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        funcionario.setCargo(cargo.get());
        return funcionarioRepository.save(funcionario);
    }
@Cacheable( value= "funcionariosCache", key = "#idCargo")
   public List<Funcionario> mostrarFuncionarioPeloIdCargo(Integer idCargo) {
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return funcionarioRepository.findByCargo(idCargo);
    }

}

