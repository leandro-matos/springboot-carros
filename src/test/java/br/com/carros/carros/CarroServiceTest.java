package br.com.carros.carros;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import br.com.carros.carros.api.exception.ObjectNotFoundException;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.carros.carros.domain.Carro;
import br.com.carros.carros.domain.CarroService;
import br.com.carros.carros.domain.dto.CarroDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
class CarroServiceTest {

	@Autowired
	private CarroService service;
	
	@Test
	public void testSave() {
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("esportivos");
		
		CarroDTO c = service.insert(carro);
		assertNotNull(c);
		
		Long id = c.getId();
		assertNotNull(id);
		
		// Buscar o Objeto
		c = service.getCarroById(id);
		assertNotNull(c);

		assertEquals("Ferrari", c.getNome());
		assertEquals("esportivos", c.getTipo());
		
		// Deletar o objeto
		service.delete(id);
		
		//Verificar se realmente foi deletado
		try {
			assertNull(service.getCarroById(id));
			fail("O carro não foi excluído");
		} catch (ObjectNotFoundException e) {
			
		}
	}
	
	@Test
	public void testLista() {
		List<CarroDTO> carros = service.getCarros();
		assertEquals(30, carros.size());
	}
	
	@Test
	public void testListaPorTipo() {
		assertEquals(10, service.getCarroByTipo("classicos").size());
		assertEquals(10, service.getCarroByTipo("esportivos").size());
		assertEquals(10, service.getCarroByTipo("luxo").size());
		assertEquals(0, service.getCarroByTipo("x").size());
	}
	
	@Test
	public void testGet() {
		CarroDTO c = service.getCarroById(11L);
		assertNotNull(c);
		assertEquals("Ferrari FF", c.getNome());
	}
	
		
}
