package example.domain;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class DtoInput extends Input {
	private String template = "templates/dto.ftl";
	private String nameSuffix = "Dto";
	private List<EntityField> fields;

	public DtoInput(Element annotatedElement, TypeElement clazz, List<EntityField> fields) {
		super(annotatedElement, clazz);
		this.setFields(fields);
	}

	public String getTemplate() {
		return template;
	}

	public String getClassname() {
		return clazz.getSimpleName().toString();
	}

	public String getPackage() {
		return annotatedElement.getEnclosingElement().getSimpleName().toString();
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	@Override
	public String getTargetName() {
		return clazz.getSimpleName().toString() + nameSuffix;
	}

	public List<EntityField> getFields() {
		return fields;
	}

	public void setFields(List<EntityField> fields) {
		this.fields = fields;
	}

}
