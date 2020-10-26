package com.apicrudds.backend.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apicrudds.backend.dto.ClientDTO;
import com.apicrudds.backend.entities.Client;
import com.apicrudds.backend.resources.ClientRepository;
import com.apicrudds.backend.services.exceptions.DataBaseException;
import com.apicrudds.backend.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	ClientRepository clientRepository;
	
	@Transactional(readOnly=true)
	public Page<ClientDTO> findAll(PageRequest pageRequest){
		Page<Client> list =  clientRepository.findAll(pageRequest);
		return  list.map(x-> new ClientDTO(x));
	
	}
	
	@Transactional(readOnly=true)
	public ClientDTO findById(long id){
		Optional<Client> obj = clientRepository.findById(id);
		Client entity  = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return  new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		
		copyDtoToEntity(dto, entity);
		
		entity = clientRepository.save(entity);
		return  new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(long id, ClientDTO dto) {
		try {
			Client entity = clientRepository.getOne(id);
			
			copyDtoToEntity(dto, entity);
			
			clientRepository.save(entity);
			return new ClientDTO(entity);
		}
		catch(EntityNotFoundException e ) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(long id) {
		try {
			clientRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e ) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch(DataIntegrityViolationException e ) {
			throw new DataBaseException("Integrity violation");
		}
		
	}
	
	private void copyDtoToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());		
		entity.setIncome(dto.getIncome());		
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}
}
