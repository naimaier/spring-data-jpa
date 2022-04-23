package br.com.alura.spring.data.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {

	private Boolean system = true;
	
	private final CargoRepository cargoRepository;
	
	public CrudCargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}
	
	public void inicial(Scanner scanner) {
		while (system) {
			System.out.println("Selecione a ação:");
			System.out.println("0 - Sair");
			System.out.println("1 - Salvar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Visualizar");
			System.out.println("4 - Deletar");
			
			Integer acao = scanner.nextInt();
			
			switch (acao) {
			case 1:
				salvar(scanner);
				break;
				
			case 2:
				atualizar(scanner);
				break;
				
			case 3:
				visualizar();
				break;
				
			case 4:
				deletar(scanner);
				break;
				
			default:
				system = false;
				break;
			}
			
		}
	}
	
	private void salvar(Scanner scanner) {
		System.out.println("Descrição do cargo");
		String descricao = scanner.next();
		
		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);
		
		cargoRepository.save(cargo);
		System.out.println("Salvo!");
	}
	
	private void atualizar(Scanner scanner) {
		System.out.println("Id do cargo");
		Integer id = scanner.nextInt();
		System.out.println("Nova descrição do cargo");
		String descricao = scanner.next();
		
		Optional<Cargo> cargoOptional = cargoRepository.findById(id);
		
		if (cargoOptional.isPresent()) {
			Cargo cargo = cargoOptional.get();
			cargo.setDescricao(descricao);
			cargoRepository.save(cargo);
			System.out.println("Atualizado!");
		} else {
			System.out.println("Registro não existente!");
		}
	}
	
	private void visualizar() {
		// FindAll retorna um Iterable
		Iterable<Cargo> cargos = cargoRepository.findAll();
		cargos.forEach(System.out::println);
	}
	
	private void deletar(Scanner scanner) {
		System.out.println("Id do regitro para deletar:");
		Integer id = scanner.nextInt();
		
		Optional<Cargo> cargoOptional = cargoRepository.findById(id);
		
		if (cargoOptional.isPresent()) {
			cargoRepository.deleteById(id);
		} else {
			System.out.println("Registro não existente!");
		}
	}
}
