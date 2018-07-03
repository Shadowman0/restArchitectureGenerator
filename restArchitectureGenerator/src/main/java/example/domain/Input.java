package example.domain;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

public abstract class Input {

	Element annotatedElement;
	TypeElement clazz;

	public Input(Element annotatedElement, TypeElement clazz) {
		this.annotatedElement = annotatedElement;
		this.clazz = clazz;
	}

	public Element getAnnotatedElement() {
		return annotatedElement;
	}

	public void setAnnotatedElement(Element annotatedElement) {
		this.annotatedElement = annotatedElement;
	}

	public TypeElement getClazz() {
		return clazz;
	}

	public void setClazz(TypeElement clazz) {
		this.clazz = clazz;
	}

	public abstract String getTargetName();

	public String getPackageName() {
		return ((PackageElement) clazz.getEnclosingElement()).getQualifiedName().toString();
	}

}
