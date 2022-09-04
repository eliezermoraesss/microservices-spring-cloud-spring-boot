package br.com.eliezer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eliezer.models.Book;
import br.com.eliezer.proxy.CambioProxy;
import br.com.eliezer.repositories.BookRepository;

@RestController
@RequestMapping("book-service")
public class BookController {
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CambioProxy proxy;
		
	//http://localhost:8100/book-service/5/BRL
	@GetMapping(value = "/{id}/{currency}")
	public Book findBook(
			@PathVariable("id") Long id,
			@PathVariable("currency") String currency		
			) {
		
		var book = repository.findById(id).get();
		if (book == null) throw new RuntimeException("Book not found");
		
		var cambio = proxy.getCambio(book.getPrice(), "USD", currency);
		
		var port = environment.getProperty("local.server.port");
		book.setEnvironment(
				"Book port: " + port + "  Cambio Port: " + cambio.getEnvironment() );
		book.setPrice(cambio.getConvertedValue());	
		return book;
	}
}
