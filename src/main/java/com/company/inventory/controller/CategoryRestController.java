package com.company.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;

@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {

	@Autowired
	private ICategoryService service;
	
	/*
	 * obtiene todas las categoricas
	 * @return
	 */
	
	@GetMapping("/categories")
	public ResponseEntity<CategoryResponseRest> searchCategory(){
		
		ResponseEntity<CategoryResponseRest> response = service.search();
		return response;
	}	
	
	@GetMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> searchCategoryById(@PathVariable Long id){
		
		ResponseEntity<CategoryResponseRest> response = service.searchById(id);
		return response;
	}
	

	/*
	 * guardar registro de categorias
	 * @param category
	 * @return
	 */
	
	@PostMapping("/categories")
	public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category){
		
		ResponseEntity<CategoryResponseRest> response = service.save(category);
		return response;
	}
	
	/*
	 * actualizar actualizar de categorias
	 * @param category
	 * @return
	 */

	
	@PutMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> update(@RequestBody Category category , @PathVariable Long id){
		
		ResponseEntity<CategoryResponseRest> response = service.update(category , id);
		return response;
	}
	/*
	 * eliminar id de categorias
	 * @param category
	 * @return
	 */
	
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> delete( @PathVariable Long id){
		
		ResponseEntity<CategoryResponseRest> response = service.deleteById(id);
		return response;
	}
	
	
	
	
	
}
