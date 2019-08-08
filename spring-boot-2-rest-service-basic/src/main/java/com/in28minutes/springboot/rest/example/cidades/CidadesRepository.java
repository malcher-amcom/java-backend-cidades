package com.in28minutes.springboot.rest.example.cidades;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadesRepository extends JpaRepository<Cidades, Long>{
    List<Cidade> findIfIsCapital(String capital);
}