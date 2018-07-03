package example.domain;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class ServiceInput extends Input {
	private String template = "templates/service.ftl";
	private String nameSuffix = "Service";

	public ServiceInput(Element annotatedElement, TypeElement clazz) {
		super(annotatedElement, clazz);
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

}
