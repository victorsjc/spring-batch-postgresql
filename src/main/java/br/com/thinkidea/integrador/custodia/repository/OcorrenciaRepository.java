package br.com.thinkidea.integrador.custodia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.thinkidea.integrador.custodia.entity.Ocorrencia;

@Repository
public interface OcorrenciaRepository extends CrudRepository<Ocorrencia, String> {

}
