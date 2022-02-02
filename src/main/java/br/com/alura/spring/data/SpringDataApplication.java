package br.com.alura.spring.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

//CommandLineRunner gera um método run que roda após o início da aplicação
@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner{

	private final CargoRepository repository;
	
	//Injetando a dependência
	public SpringDataApplication(CargoRepository repository) {
		this.repository = repository;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Cargo cargo = new Cargo();
		cargo.setDescricao("DESENVOLVEDOR DE SOFTWARE");
		
		repository.save(cargo);
	}

}