package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.company.inventory.Util.Util;
import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.dao.IProductDao;
import com.company.inventory.model.Category;
import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;

@Service
public class ProductServiceImpl implements IProductService {
	
	private ICategoryDao categoryDao;
	private IProductDao productDao;

	//Generamos el constructor para tener las dependencias de cateogryDao
	public ProductServiceImpl(ICategoryDao categoryDao, IProductDao productDao) {
		super();
		this.categoryDao = categoryDao;
		this.productDao = productDao;
	}


	@Override
	public ResponseEntity<ProductResponseRest> save (Product product , Long categoryId) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		try {
			
			//Buscar la categoria para settear al producto
			Optional<Category> category = categoryDao.findById(categoryId);
			
			if( category.isPresent()) {
				product.setCategory(category.get());
			}else {
				response.setMetadata("Respuesta Fail", "-1", "Categoria no encontrado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			 // Comprimir la imagen antes de guardar
	        if (product.getPicture() != null) {
	            byte[] compressedImage = Util.compressZLib(product.getPicture());
	            product.setPicture(compressedImage);
	        }

	        // Guardar el producto
	        Product productSaved = productDao.save(product);
	        if (productSaved != null) {
	            list.add(productSaved);
	            response.getProduct().setProducts(list);
	            response.setMetadata("Respuesta OK", "00", "Producto guardado");
	        } else {
	            response.setMetadata("Respuesta Fail", "-1", "Producto no guardado");
	            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setMetadata("Respuesta Fail", "-1", "ERROR AL GUARDAR PRODUCTO");
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    return new ResponseEntity<>(response, HttpStatus.OK);
		
	}

}
