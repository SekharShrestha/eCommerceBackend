package com.ecom.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.ecom.entity.Order;
import com.ecom.entity.Product;
import com.ecom.entity.ProductCategory;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

		HttpMethod[] unsupportedActions = { HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE };

		// disable HTTP methods for Product : PUT, POST, DELETE
		config.getExposureConfiguration().forDomainType(Product.class)
				.withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
				.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));

		// disable HTTP methods for ProductCategory : PUT, POST, DELETE
		config.getExposureConfiguration().forDomainType(ProductCategory.class)
				.withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
				.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));

		// disable HTTP methods for Orders : PUT, POST, DELETE
		config.getExposureConfiguration().forDomainType(Order.class)
				.withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
				.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));

		// call an internal helper method
		exposeIds(config);

		// configure cors mapping - no need to add crossorigin in every repo class
		cors.addMapping("/api/**").allowedOrigins("http://localhost:4200");
	}

	private void exposeIds(RepositoryRestConfiguration config) {
		// TODO Auto-generated method stub

		// get a list of all entity classes frm entityManager
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

		// create an arr of the entity types
		List<Class> entityClasses = new ArrayList<>();

		// get the entity types for the entities
		for (EntityType e : entities) {
			entityClasses.add(e.getJavaType());
		}

		// expose the entity ids for the arr of entity/domain types
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);

	}

}
