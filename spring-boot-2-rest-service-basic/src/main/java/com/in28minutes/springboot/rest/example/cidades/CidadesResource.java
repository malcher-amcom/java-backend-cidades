package com.in28minutes.springboot.rest.example.cidades;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class CidadesResource {

    @Autowired
    private CidadesRepository cidadesRepository;

    // retornar todas as cidades
    @GetMapping("/cidades")
    public List<Cidades> retrieveAllCidades() {
        return cidadesRepository.findAll();
    }

    // retornar uma cidade pelo id
    @GetMapping("/cidades/{ibge_id}")
    public Cidades retrieveCidades(@PathVariable Long ibge_id) throws Exception {
        Optional<Cidades> cidades = cidadesRepository.findById(ibge_id);

        if (!cidades.isPresent())
            throw new Exception(" ibge id não encontrado" + ibge_id);
        return cidades.get();
    }

    // deletar uma cidade
    @DeleteMapping("/cidades/{ibge_id}")
    public void deleteCidade(@PathVariable Long ibge_id) {
        cidadesRepository.deleteById(ibge_id);
    }

    // inserir uma nova cidade
    @PostMapping("/cidades")
    public ResponseEntity<Object> createCidade(@RequestBody Cidades cidade) {
        Cidades savedCidade = cidadesRepository.save(cidade);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{ibge_id}")
                .buildAndExpand(savedCidade.getIbge_id()).toUri();

                return ResponseEntity.created(location).build();

    }

    // alterar uma cidade    
    @PutMapping("/cidades/{ibge_id}")
    public ResponseEntity<Object> updateCidade(@RequestBody Cidades cidade, @PathVariable Long ibge_id) {
        Optional<Cidades> cidadeOptional = cidadesRepository.findById(ibge_id);

        if (!cidadeOptional.isPresent())
                return ResponseEntity.notFound().build();

                cidade.setIbge_id(ibge_id);
                cidadesRepository.save(cidade);
                return ResponseEntity.noContent().build();
    }

     // retornar cidades com capital
     @GetMapping("/cidades/{capital}")
     public List<Cidade> cidadesCapital() {
         List<Cidade> capital = cidadesRepository.findIfIsCapital(capital).sortByName;
         return List.findIfIsCapital(capital);
     }

    // retornar o total de todas as cidades
    @GetMapping("/cidades/contar")
    public long retrieveAllCidadesCount() {
        return cidadesRepository.count();
    }

}