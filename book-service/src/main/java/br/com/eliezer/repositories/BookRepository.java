package br.com.eliezer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eliezer.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
