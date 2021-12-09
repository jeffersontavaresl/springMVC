package br.com.mvc.mudi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mvc.mudi.dto.RequisicaoNovoPedido;
import br.com.mvc.mudi.model.Pedido;
import br.com.mvc.mudi.model.User;
import br.com.mvc.mudi.repository.PedidoRepository;
import br.com.mvc.mudi.repository.UserRepository;

@Controller
@RequestMapping("pedido")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("formulario")
	public String formulario(RequisicaoNovoPedido requisicao) {
		return "pedido/formulario";
	}
	
	
	// MÉTODO PARA RECEBER CHAMAR A REQUISIÇÃO DE UM NOVO PEDIDO, CASO CONTENHA ERROS NO CADASTRO ELE ENTRA NO IF
	// IRÁ SALVAR O PEDIDO NO USUÁRIO QUE ESTÁ LOGADO
	@PostMapping("novo")
	public String novo(@Valid RequisicaoNovoPedido requisicao, BindingResult result) {
		if(result.hasErrors()) {
			return "pedido/formulario";
		} 
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(username);
		
		Pedido pedido = requisicao.toPedido();
		pedido.setUser(user);
		pedidoRepository.save(pedido);
		return "redirect:/home";
		
	}
}
