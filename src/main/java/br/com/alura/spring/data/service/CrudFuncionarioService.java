package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarioRepository;
	private final CargoRepository cargoRepository;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
	
	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, 
			CargoRepository cargoRepository, 
			UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
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
				visualizar(scanner);
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
		System.out.println("Nome");
		String nome = scanner.next();
		System.out.println("CPF");
		String cpf = scanner.next();
		System.out.println("Salário");
		BigDecimal salario = scanner.nextBigDecimal();
		System.out.println("Data de contratação");
		String dataContratacao = scanner.next();
		System.out.println("Id do cargo");
		Integer cargoId = scanner.nextInt();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		Optional<Cargo> optional = cargoRepository.findById(cargoId);
		
		if (optional.isPresent()) {
			funcionario.setCargo(optional.get());
		} else {
			System.out.println("Cargo inexistente!");
		}
		
		List<UnidadeTrabalho> unidades = unidade(scanner);
		funcionario.setUnidadeTrabalho(unidades);
		
		funcionarioRepository.save(funcionario);
		System.out.println("Salvo!");
	}
	
	private List<UnidadeTrabalho> unidade(Scanner scanner){
		Boolean isTrue = true;
		List<UnidadeTrabalho> unidades = new ArrayList<>();
		
		while (isTrue) {
			System.out.println("Digite o id da unidade (para sair digite 0)");
			Integer unidadeId = scanner.nextInt();
			
			if (unidadeId != 0) {
				Optional<UnidadeTrabalho> unidadeTrabalhoOptional = unidadeTrabalhoRepository.findById(unidadeId);
				
				if (unidadeTrabalhoOptional.isPresent()) {
					unidades.add(unidadeTrabalhoOptional.get());
				} else {
					System.out.println("Unidade inexistente!");
				}
			} else {
				isTrue = false;
			}			
		}
		
		return unidades;
	}
	
	private void atualizar(Scanner scanner) {
		System.out.println("Id do funcionario");
		Integer id = scanner.nextInt();
		System.out.println("Nome");
		String nome = scanner.next();
		System.out.println("CPF");
		String cpf = scanner.next();
		System.out.println("Salário");
		BigDecimal salario = scanner.nextBigDecimal();
		System.out.println("Data de contratação");
		String dataContratacao = scanner.next();
		
		List<UnidadeTrabalho> unidades = unidade(scanner);
		
		Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
		
		if (funcionarioOptional.isPresent()) {
			Funcionario funcionario = funcionarioOptional.get();
			funcionario.setNome(nome);
			funcionario.setCpf(cpf);
			funcionario.setSalario(salario);
			funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
			funcionario.setUnidadeTrabalho(unidades);
			
			System.out.println("Id do cargo");
			Integer cargoId = scanner.nextInt();
			
			Optional<Cargo> optional = cargoRepository.findById(cargoId);
			
			if (optional.isPresent()) {
				funcionario.setCargo(optional.get());
			} else {
				System.out.println("Cargo inexistente!");
			}
			
			System.out.println("Atualizado!");
		} else {
			System.out.println("Registro não existente!");
		}
	}
	
	private void visualizar(Scanner scanner) {
		System.out.println("Qual página você deseja visualizar?");
		Integer pagina = scanner.nextInt();
		
		Pageable pageable = PageRequest.of(pagina, 5, Sort.by(Sort.Direction.ASC, "nome"));
		
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
		
		System.out.println(funcionarios);
		System.out.println("Página atual: " + funcionarios.getNumber());
		System.out.println("Total de elementos: " + funcionarios.getTotalElements());
		
		funcionarios.forEach(System.out::println);
	}
	
	private void deletar(Scanner scanner) {
		System.out.println("Id do regitro para deletar:");
		Integer id = scanner.nextInt();
		
		Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
		
		if (funcionarioOptional.isPresent()) {
			funcionarioRepository.deleteById(id);
		} else {
			System.out.println("Registro não existente!");
		}
	}
}
