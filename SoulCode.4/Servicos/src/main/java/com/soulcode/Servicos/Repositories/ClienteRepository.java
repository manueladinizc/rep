package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JPARepository estão os metodos para fazer o crud

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByEmail(String email);

}

