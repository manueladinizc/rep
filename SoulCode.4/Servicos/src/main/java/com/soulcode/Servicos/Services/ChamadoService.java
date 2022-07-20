package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Models.StatusChamado;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    ChamadoRepository chamadoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    public List<Chamado> mostrarTodosChamados() {

        return chamadoRepository.findAll();
    }

    //vamos criar mais um serviço relacionado ao funcionário
    //criar um serviço buscar apenas um funcionário pelo seu ID(chave primária)

    @Cacheable("chamadosCache")
    public Chamado mostrarUmChamadoPeloId(Integer idChamado) {
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        return chamado.orElseThrow();
    }
    @Cacheable(value="chamadosCache", key = "#idCliente")
    public List<Chamado> buscarChamadosPeloCliente(Integer idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return chamadoRepository.findByCliente(cliente);
    }

    @Cacheable(value="chamadosCache", key = "#idFuncionario")
    public List<Chamado> buscarChamadosPeloFuncionario(Integer idFuncionario) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return chamadoRepository.findByFuncionario(funcionario);
    }
    @Cacheable(value="chamadosCache", key = "#status")
    public List<Chamado> buscarChamadosPeloStatus(String status) {

        return chamadoRepository.findByStatus(status);
    }

    @Cacheable(value = "chamadosCache", key = "T(java.util.Objects).hash(#data1, #data2)")
    public List<Chamado> buscarPorIntervaloData(Date data1, Date data2) {
        return chamadoRepository.findByIntervaloData(data1, data2);
    }

    //cadastrar um novo chamado
    //temos 2 regras:
    //1) no momento do cadastri do chamado, já devemos informar de qual cliente é
    //2) no momento do cadastri do chamado, a princippio vamos fazer esse cadastro sem estar atribuido a um funcionario
    //3) no momento do cadastri do chamado, o ststus desse chamado deve ser RECEBIDO

    //servço para cadastro de novo chamado
    @CachePut(value = "chamadosCache", key = "#idCliente")
    public Chamado cadastrarChamado(Chamado chamado, Integer idCliente) {
        chamado.setStatus(StatusChamado.RECEBIDO);
        chamado.setFuncionario(null);
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        chamado.setCliente(cliente.get());
        return chamadoRepository.save(chamado);
    }

    // Método para exclusão de um chamado
    @CacheEvict(value = "chamadosCache", key = "#idChamados", allEntries = true)
    public void excluirChamado(Integer idChamado) {

        chamadoRepository.deleteById(idChamado);
    }

    //Metodo para editar um chamado
    //no momento da edição de um chamado, devemos preservar o cliente e o funcionario desse chamado
    //vamos editar os dados do chamado , mas continuar com os daods do cliente e os dados do funcionario

    @CachePut(value = "chamadosCache", key = "#idChamado")
    public Chamado editarChamado(Chamado chamado, Integer idChamado) {
        Chamado chamadoSemAsNovasAlteracoes = mostrarUmChamadoPeloId(idChamado);
        Funcionario funcionario = chamadoSemAsNovasAlteracoes.getFuncionario();
        Cliente cliente = chamadoSemAsNovasAlteracoes.getCliente();


        chamado.setCliente(cliente);
        chamado.setFuncionario(funcionario);
        return chamadoRepository.save(chamado);
    }


    //metaodo para atribuir um funcionario para um determinado chamado ou trocar o funcionário de determinado chamado
    //regra: no momento em que um determianado chamado é atribuido a um funcionário o status do funcionário precisa
    // ser alterado para ATRIBUIDO


    public Chamado atribuirFuncionario(Integer idChamado, Integer idFuncionario) {
        //buscar os dados do funcionárioque vai  ser atribuido a esse chamado
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        //buscar o chamado para o qual vai ser especificado o funcionário escolhido
        Chamado chamado = mostrarUmChamadoPeloId(idChamado);
        chamado.setFuncionario(funcionario.get());
        chamado.setStatus(StatusChamado.ATRIBUIDO);

        return chamadoRepository.save(chamado);
    }
    @CachePut(value = "chamadosCache", key = "#idChamado")
    //metodo para modificar o status de um chamado
    public Chamado modificarStatus(Integer idChamado, String status) {
        Chamado chamado = mostrarUmChamadoPeloId(idChamado);

        if (chamado.getFuncionario() != null) {


            switch (status) {
                case "ATRIBUIDO":
                    chamado.setStatus(StatusChamado.ATRIBUIDO);
                    break;

                case "CONCLUIDO":
                    chamado.setStatus(StatusChamado.CONCLUIDO);
                    break;

                case "ARQUIVADO":
                    chamado.setStatus(StatusChamado.ARQUIVADO);
                    break;

                }
            }
            switch (status) {
                case "RECEBIDO":
                    chamado.setStatus(StatusChamado.RECEBIDO);
                    break;

            }

            return chamadoRepository.save(chamado);
        }


    }

