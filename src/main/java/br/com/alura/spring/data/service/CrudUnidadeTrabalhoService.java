package br.com.alura.spring.data.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudUnidadeTrabalhoService {

	private Boolean system = true;
	
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
	
	public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
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
		System.out.println("Descrição da unidade");
		String descricao = scanner.next();
		
		System.out.println("Endereco da unidade");
		String endereco = scanner.next();
		
		UnidadeTrabalho unidade = new UnidadeTrabalho();
		unidade.setDescricao(descricao);
		unidade.setEndereco(endereco);
		
		unidadeTrabalhoRepository.save(unidade);
		System.out.println("Salvo!");
	}
	
	private void atualizar(Scanner scanner) {
		System.out.println("Id da unidade");
		Integer id = scanner.nextInt();
		System.out.println("Nova descrição");
		String descricao = scanner.next();
		System.out.println("Novo endereço");
		String endereco = scanner.next();
		
		Optional<UnidadeTrabalho> unidadeOptional = unidadeTrabalhoRepository.findById(id);
		
		if (unidadeOptional.isPresent()) {
			UnidadeTrabalho unidade = unidadeOptional.get();
			unidade.setDescricao(descricao);
			unidade.setEndereco(endereco);
			unidadeTrabalhoRepository.save(unidade);
			System.out.println("Atualizado!");
		} else {
			System.out.println("Registro não existente!");
		}
	}
	
	private void visualizar() {
		Iterable<UnidadeTrabalho> unidades = unidadeTrabalhoRepository.findAll();
		unidades.forEach(System.out::println);
	}
	
	private void deletar(Scanner scanner) {
		System.out.println("Id do regitro para deletar:");
		Integer id = scanner.nextInt();
		
		Optional<UnidadeTrabalho> unidadeOptional = unidadeTrabalhoRepository.findById(id);
		
		if (unidadeOptional.isPresent()) {
			unidadeTrabalhoRepository.deleteById(id);
		} else {
			System.out.println("Registro não existente!");
		}
	}
}
