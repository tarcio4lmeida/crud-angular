package com.angular.crud.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.angular.crud.dto.ProductDTO;
import com.angular.crud.services.ProductService;

@Controller
@RequestMapping(value="/products")
@CrossOrigin(origins = "*")
public class ProductResource {
	
	@Autowired
	ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAll(){
		List<ProductDTO> list = productService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable long id){
		ProductDTO dto = productService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping	
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto){
		dto = productService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable long id, @RequestBody ProductDTO dto){
		dto = productService.update(id,dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value="/{id}")
	public ResponseEntity<ProductDTO> delete(@PathVariable long id){
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
