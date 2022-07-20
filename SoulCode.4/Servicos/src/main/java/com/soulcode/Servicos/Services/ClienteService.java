package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


// Quando se fala em serviços, estamos falando dos metodos do crud da tabela
@Service
public class ClienteService {

    //PARA OS METODOS DA INTERFACE serem usados em outra classe, usamos o Autowired. Injeção de dependencia
    @Autowired
    ClienteRepository clienteRepository;

    @Cacheable("clientesCache") //só chama o return se o cache expirar
    public List<Cliente> mostrarTodosClientes(){

        return clienteRepository.findAll();
    }

    //vamos criar mais um serviço relacionado ao funcionário
    //criar um serviço buscar apenas um funcionário pelo seu ID(chave primária)

    @Cacheable(value="clientesCache", key = "#idCliente") //clienteCache::1
    public Cliente mostrarUmClientePeloId(Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        /*return cliente.orElseThrow();*/
        return cliente.orElseThrow();
    }




    //vamos criar mais um serviço para buscar um funcionario pelo seu email
    //Optional serve para evitar o travamento se o id buscado não existir, trata o not finding
    //O return ElseThrow lança uma excessão
    public Cliente mostrarUmClientePeloEmail(String email){
        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        return cliente.orElseThrow();
    }


    //vamos criar um serviço para cadastrar um novo CLIENTE

    @CachePut(value = "clientesCache", key = "#cliente.idClinte")
    public Cliente cadastrarCliente(Cliente cliente){
        //por precaução vamos colocar um id do cliente como nullo
        /*mostrarUmClientePeloId(cliente.getIdCliente());*/
        cliente.setIdCliente(null);
        return clienteRepository.save(cliente);
    }

    //atualizar ou substitui a info no cache de acordo com a key
    @CachePut(value = "clientesCache", key = "#cliente.idCliente")
    public Cliente editarCliente(Cliente cliente){
        mostrarUmClientePeloId(cliente.getIdCliente());
        return clienteRepository.save(cliente);

    }

    /*@CacheEvict(value="clientesCache", key = "#idCliente", allEntries = true)
    public void excluirCliente(Integer idCliente){
        mostrarUmClientePeloId(idCliente);
        clienteRepository.deleteById(idCliente);
    }*/

    @CacheEvict(value = "clientesCache", key = "#idCliente", allEntries = true)
    public void excluirCliente(Integer idCliente) {
        mostrarUmClientePeloId(idCliente);
        clienteRepository.deleteById(idCliente);
    }












}
