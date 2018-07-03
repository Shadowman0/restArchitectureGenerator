package ${package};


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.etengo.eemweb.commons.db.entity.Entities;
import ${package}.${classname};

@Component
public class ${classname}Service {
	
	private static final String ERROR_NOT_FOUND = "${classname} nicht gefunden";
	
	@Autowired
	private ${classname}Repository repository;
	
	
	public ${classname} get(UUID id) {
		return Entities.getOrThrow(repository.findById(id), ERROR_NOT_FOUND);
	}
	
	public ${classname} update(${classname} ${classname?lower_case}) {
		${classname?lower_case}.checkValidForUpdate(repository);
		return repository.save(${classname?lower_case});
	}
	
	public ${classname} create(${classname} ${classname?lower_case}) {
		return repository.save(${classname?lower_case});
	}
	
}