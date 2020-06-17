package br.com.carros.carros.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.carros.carros.api.exception.ObjectNotFoundException;
import br.com.carros.carros.domain.dto.CarroDTO;

@Service
public class CarroService {

	@Autowired
	private CarroRepository dao;
	
	public List<CarroDTO> getCarros() {
		return dao.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
	}
	
    public CarroDTO getCarroById(Long id) {
        Optional<Carro> carro = dao.findById(id);
        return carro.map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
    }

	public List<CarroDTO> getCarroByTipo(String tipo) {
		return dao.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public CarroDTO insert(Carro carro) {
		Assert.isNull(carro.getId(), "Não foi possível inserir o registro");
		return CarroDTO.create(dao.save(carro));
	}
	
	 public CarroDTO update(Carro carro, Long id) {
	        Assert.notNull(id,"Não foi possível atualizar o registro");

	        // Busca o carro no banco de dados
	        Optional<Carro> optional = dao.findById(id);
	        if(optional.isPresent()) {
	            Carro db = optional.get();
	            // Copiar as propriedades
	            db.setNome(carro.getNome());
	            db.setTipo(carro.getTipo());
	            System.out.println("Carro id " + db.getId());

	            // Atualiza o carro
	            dao.save(db);

	            return CarroDTO.create(db);
	        } else {
	            return null;
	            //throw new RuntimeException("Não foi possível atualizar o registro");
	        }
	    }

	public void delete(Long id) {
		dao.deleteById(id);
	}

}
