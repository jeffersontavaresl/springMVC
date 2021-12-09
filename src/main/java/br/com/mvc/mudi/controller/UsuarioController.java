package br.com.mvc.mudi.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mvc.mudi.model.Pedido;
import br.com.mvc.mudi.model.StatusPedido;
import br.com.mvc.mudi.repository.PedidoRepository;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	// LISTA TODOS OS PEDIDOS POR USUÁRIO PEGANDO O NÚMERO DO USUÁRIO QUE ESTÁ LOGADO 
	@GetMapping("pedido")
	public String home(Model model, Principal principal) {
		List<Pedido> pedidos = pedidoRepository.findAllByUsuario(principal.getName());
		model.addAttribute("pedidos", pedidos);	
		return "usuario/home";
	}
	
	// FILTRA OS PEDIDOS PELO STATUS E PELO NOME DO USUÁRIO QUE ESTÁ LOGADO NO SISTEMA
	@GetMapping("pedido/{status}")
	public String aguardando(@PathVariable("status") String status, Model model, Principal principal) {
		List<Pedido> pedidos = pedidoRepository.findByStatusEUsuario(StatusPedido.valueOf(status.toUpperCase()), principal.getName());
		model.addAttribute("pedidos", pedidos);	
		model.addAttribute("status", status);
		return "usuario/home";
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/usuario/home";
	}
}
