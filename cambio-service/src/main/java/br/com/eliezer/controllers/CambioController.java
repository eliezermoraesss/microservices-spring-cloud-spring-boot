package br.com.eliezer.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eliezer.models.Cambio;
import br.com.eliezer.repositories.CambioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cambio Service API")
@RestController
@RequestMapping("cambio-service")
public class CambioController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private CambioRepository repository;

	// http://localhost:8000/cambio-service/5/USD/BRL
	@Operation(description = "Get cambio from currency!")
	@GetMapping(value = "/{amount}/{from}/{to}")
	public Cambio getCambio(@PathVariable("amount") BigDecimal amount, @PathVariable("from") String from,
			@PathVariable("to") String to) {
		
		var cambio = repository.findByFromAndTo(from, to);
		
		if (cambio == null) throw new RuntimeException("Currency Unsupported");
		
		var port = environment.getProperty("local.server.port");
		BigDecimal conversionFactor = cambio.getConversionFactor();
		BigDecimal convertedValue = conversionFactor.multiply(amount);
		cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
		cambio.setEnvironment(port);
		return cambio;
	}
}
