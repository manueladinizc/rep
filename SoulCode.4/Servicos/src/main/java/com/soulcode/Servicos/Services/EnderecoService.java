package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Endereco;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ClienteRepository clienteRepository;



    @Cacheable("enderecoCache")
    public List<Endereco> mostrarTodosEnderecos() {

        return enderecoRepository.findAll();
    }
    @Cacheable(value="enderecoCache", key = "#idEndereco")
    public Endereco mostrarUmEnderecoPeloId(Integer idEndereco){
        Optional<Endereco> endereco = enderecoRepository.findById(idEndereco);
        return endereco.orElseThrow();
    }

    /*Regra:
   * 1º Para cadastrar um endereço, o cliente deve estar cadastrado no database
   * 2º No momento do cadastro do endereço, precisamos passar o id do cliente dono desse endereço
   * 3º O id do endereço vai ser o mesmo id do cliente
   * 4º Não permitir que um endereço seja salvo sem a existência do respectivo cliente*/
    @CachePut(value = "enderecoCache", key = "idCliente")
    public Endereco cadastrarEndereco (Endereco endereco, Integer idCliente) throws Exception {
        //estamos declarando um optional de cliente e atribuindo para este os dados do cliente que receberá o novo
        // endereço
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isPresent()) {
            endereco.setIdEndereco(idCliente);
            enderecoRepository.save(endereco);


            cliente.get().setEndereco(endereco);
            clienteRepository.save(cliente.get());
            return endereco;
        } else {
            throw new Exception();
        }
    }

    @CachePut(value = "enderecoCache", key = "#endereco.idEndereco")
    public Endereco editarEndereco(Endereco endereco){

        return enderecoRepository.save(endereco);
        }
    }




