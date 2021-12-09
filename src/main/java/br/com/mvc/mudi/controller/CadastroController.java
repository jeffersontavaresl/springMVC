package br.com.mvc.mudi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mvc.mudi.model.User;
import br.com.mvc.mudi.repository.UserRepository;

@Controller
@RequestMapping("/cadastro")
public class CadastroController {

	@Autowired
	private UserRepository repository;
	
	// MÉTODO PARA INFORMA A PÁGINA HTML QUE O USUÁRIO SERÁ DIRECIONADO QUANDO INFORMAR NA URL /CADASTRO
	@GetMapping
	public String cadastro(){
		return "cadastro";
	}
	
	// MÉTODO PARA ADICIONAR UM NOVO USUÁRIO, CHAMANDO O REPOSITÓRIO PARA SALVAR NO BANCO DE DADOS.
	
	@PostMapping
	public String novoUsuario(User user, Model model) {
	
		model.addAttribute("username", user.getUsername());
		model.addAttribute("password", user.getPassword());
		user.setEnabled(true);
		repository.save(user);
		
		return "usuario";
		
	}
}
