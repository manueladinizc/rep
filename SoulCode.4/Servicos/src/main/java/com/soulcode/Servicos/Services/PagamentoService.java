package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.*;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ChamadoRepository chamadoRepository;

    @Cacheable("pagamentoCache")
    public List<Pagamento> mostrarTodosPagamentos() {

        return pagamentoRepository.findAll();
    }

    @Cacheable(value="pagamentoCache", key = "#idPagamento")
    public Pagamento buscarPagamentosPeloId(Integer idPagamento) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        return pagamento.orElseThrow();
    }

    @Cacheable(value ="pagamentoCache", key = "#status")
    public List<Pagamento> mostrarPagamentosPeloStatus(String status){
        return pagamentoRepository.findByStatus(status);
    }
    @CachePut(value = "pagamentoCache", key = "#idChamado")
    public Pagamento cadastrarUmPagamento (Pagamento pagamento, Integer idChamado) {
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        if (chamado.isPresent()){
            pagamento.setIdPagamento(idChamado);
            pagamento.setStatus(StatusPagamento.LANCADO);
            pagamentoRepository.save(pagamento);

            chamado.get().setPagamento(pagamento);
            chamadoRepository.save(chamado.get());
            return pagamento;

        }else {
            throw new RuntimeException();
        }
    }


    @CachePut(value = "pagamentoCache", key = "#pagamento.idPagamento")
    public Pagamento editarPagamento(Pagamento pagamento)
 {
     return pagamentoRepository.save(pagamento);
 }

 @CachePut(value = "pagamentoCache", key = "#idPagamento")
 public Pagamento modificarStatusPagamento(Integer idPagamento, String status)
 {
     Pagamento pagamento = buscarPagamentosPeloId(idPagamento);

     switch (status)
     {
         case "LANCADO":
             pagamento.setStatus(StatusPagamento.LANCADO);
             break;
         case "QUITADO":
             pagamento.setStatus(StatusPagamento.QUITADO);
             break;
     }
     return pagamentoRepository.save(pagamento);



 }

    @Cacheable(value="pagamentoCache", key = "#idPagamento")
    public Pagamento quitarPagamento(Integer idPagamento){
        Pagamento pagamento = buscarPagamentosPeloId(idPagamento);
        pagamento.setStatus(StatusPagamento.QUITADO);
        return pagamentoRepository.save(pagamento);
 }

    @Cacheable(value = "pagamentoCache")
    public List<List> orcamentoComServicoCliente(){
        return pagamentoRepository.orcamentoComServicoCliente();
    }




   /* public Pagamento cadastrarPagamento (Pagamento pagamento, Integer idChamado) throws Exception {
        //estamos declarando um optional de cliente e atribuindo para este os dados do cliente que receberá o novo
        // endereço
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        if (chamado.isPresent()) {
            pagamento.setIdPagamento(idChamado);
            pagamentoRepository.save(pagamento);


            chamado.get().setPagamento(pagamento);
            chamadoRepository.save(chamado.get());
            return pagamento;
        } else {
            throw new Exception();
        }
    }*/


}
