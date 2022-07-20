package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
//JPARepository estão os metodos para fazer o crud
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Optional<Funcionario> findByEmail(String email);


    @Query(value = "SELECT * FROM funcionario WHERE id_cargo =:idCargo", nativeQuery = true)
    List<Funcionario> findByCargo(Integer idCargo);



    //findbyEmail, buscando atributo, sendo pelo email

    //Optional<Funcionario> findByNomeAndEmail(String nome, String email)

}
