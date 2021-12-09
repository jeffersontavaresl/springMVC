package br.com.mvc.mudi.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mvc.mudi.model.Pedido;
import br.com.mvc.mudi.model.StatusPedido;
import br.com.mvc.mudi.repository.PedidoRepository;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	// MÉTODO PARA DIRECIONAR O USUÁRIO QUANDO DIGITAR /HOME NA URL
	// IRÁ LISTAR TODOS OS PEDIDOS QUE ESTÃO COM O STATUS ENTREGUE E IRÁ GERAR UMA PAGINAÇÃO DE 5 ITENS POR PÁGINA
	@GetMapping
	public String home(Model model, Principal principal) {
		
		Sort sort = Sort.by("dataDaEntrega").descending();
		PageRequest paginacao = PageRequest.of(0, 5, sort);
		
		List<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.ENTREGUE, paginacao);
		model.addAttribute("pedidos", pedidos);	
		return "home";
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/home";
	}
}
