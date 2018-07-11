package ${package}.api;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonRootName;

import de.etengo.eemweb.commons.core.api.VersionedDTO;
import de.etengo.eemweb.commons.core.builder.GenerateBuilder;

@JsonRootName("${classname?lower_case}")
public class ${classname}DTO extends VersionedDTO {
	
	<#list fields as field>
		<#if field.type?matches("String")>@Size(max = 100)</#if>
		private final ${field.type} ${field.name};
	</#list >
	
	
	@GenerateBuilder
	public ${classname}DTO( 	
	<#list fields as field>
		${field.type} ${field.name},
   	</#list >) {
   	
		super(lastModified);
	<#list fields as field>
		this.${field.name} = ${field.name};
   	</#list >
	}
	
	${classname}DTO() {
		this(
		<#list fields as field>
		<#if field.type?matches("String")>""
		<#else>
			<#if field.type?matches("Optional*")> Optional.empty() 
				<#else>null
			</#if>
		</#if>
		<#sep>,
   	</#list >);
	}
	
	<#list fields as field>
	public ${field.type} get${field.name?cap_first}() {
		return ${field.name};
	}
	</#list >
	
}
