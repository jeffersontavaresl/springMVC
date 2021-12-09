package br.com.mvc.mudi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mvc.mudi.model.Pedido;
import br.com.mvc.mudi.model.StatusPedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	
	// MÉTODO PARA BUSCAR TODOS OS ITENS QUE ESTÃO COM O STATUS PREDEFINIDO COM PAGINAÇÃO...
	// CACHEABLE -> FÁZ OS ITENS FICAREM NO CACHE DO CLIENTE PARA NÃO DAR SELECT TODAS AS VEZES QUE CARREGAR A PÁGINA
	@Cacheable("itens")
	List<Pedido> findByStatus(StatusPedido status, Pageable sort);

	
	// MÉTODO PARA BUSCAR TODOS OS PEDIDOS DE UM USUÁRIO
	@Query("SELECT p FROM Pedido p JOIN p.user u WHERE u.username = :username")
	List<Pedido> findAllByUsuario(@Param("username") String username);

	
	// MÉTODO MÉTODO PARA ACHAR OS PEDIDOS DO USUÁRIO PELO STATUS
	@Query("SELECT p FROM Pedido p JOIN p.user u WHERE u.username = :username and p.status = :status")
	List<Pedido> findByStatusEUsuario(@Param("status")StatusPedido status,@Param("username")String username);

}
