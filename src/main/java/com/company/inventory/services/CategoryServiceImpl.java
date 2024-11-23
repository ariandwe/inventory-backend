package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;

@Service
public class CategoryServiceImpl  implements ICategoryService{
	
	@Autowired
	private ICategoryDao categoryDao;
	
	
    @Override
    @Transactional(readOnly= true)
    public ResponseEntity<CategoryResponseRest> search() {
	
    	CategoryResponseRest response = new CategoryResponseRest();
    	
    	try{
    		List<Category> category = (List<Category>) categoryDao.findAll();
    				
    		response.getCateogryResponse().setCategory(category);
    		response.setMetadata("Respuesta Ok", "00", "Respuesta exitosa");
    				
    	} catch (Exception e) {

    		response.setMetadata("Fail", "-1", "Respuesta error");
    		e.getStackTrace();

        	return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    		
    	}
    	
    	return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    	
}

    @Transactional(readOnly= true)
	@Override
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
      CategoryResponseRest response = new CategoryResponseRest();
      List<Category> list = new ArrayList<>();
    	
    	try{
    	 
    		Optional <Category> category = categoryDao.findById(id);
    		
    		if(category.isPresent()) {
    			 list.add(category.get());
    			 response.getCateogryResponse().setCategory(list);
    			 response.setMetadata("OK", "-1", "Categoria  encontrada");
    		}else {
    			response.setMetadata("Fail", "-1", "Categoria no encontrada");

            	return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
    		}
    		
    				
    	} catch (Exception e) {

    		response.setMetadata("Fail", "-1", "Error al consultar por id");
    		e.getStackTrace();

        	return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    		
    	}
    	
    	return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> save(Category category) {
		
		
		CategoryResponseRest response = new CategoryResponseRest();
	      List<Category> list = new ArrayList<>();
	    	
	    	try{
	    	 
	    		Category categorySaved = categoryDao.save(category);
	    		
	    		if(categorySaved != null) {
	    			list.add(categorySaved);
	    			response.getCateogryResponse().setCategory(list);
	    			response.setMetadata("OK", "-1", "Categoria nguardada");
	    			
	    		}else {
	    			
	    			response.setMetadata("Fail", "-1", "Categoria no guardada");

	            	return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
	    		}
	    		
	    		
	    				
	    	} catch (Exception e) {

	    		response.setMetadata("Fail", "-1", "Error al grabar categoria");
	    		e.getStackTrace();

	        	return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    		
	    	}
	    	
	    	return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    
}
	
}
