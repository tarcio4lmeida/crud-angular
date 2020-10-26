package com.angular.crud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angular.crud.dto.ProductDTO;
import com.angular.crud.dto.repositories.ProductRepository;
import com.angular.crud.entities.Product;
import com.angular.crud.services.exceptions.DataBaseException;
import com.angular.crud.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly=true)
	public List<ProductDTO> findAll(){
		List<Product> list =  productRepository.findAll();
		return  list.stream().map(x-> new ProductDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly=true)
	public ProductDTO findById(long id){
		Optional<Product> obj = productRepository.findById(id);
		Product entity  = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		// GetOne -> ele nao vai ao banco, somente instancia um objeto de uma determinada entity com o valor passado 
		try {
			Product entity = productRepository.getOne(id);
			copyDtoToEntity(dto, entity);
			
			productRepository.save(entity);
			return new ProductDTO(entity);
		}
		catch(EntityNotFoundException e ) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(long id) {
		try {
			productRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e ) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch(DataIntegrityViolationException e ) {
			throw new DataBaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
	}
	
}
