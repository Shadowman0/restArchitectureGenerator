package example.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import example.domain.RestArchitecture;
import example.domain.ServiceInput;
import net.karneim.pojobuilder.analysis.InvalidElementException;
import net.karneim.pojobuilder.analysis.JavaModelAnalyzerUtil;

public class ElementParsingService {

	private ProcessingEnvironment processingEnv;
	private JavaModelAnalyzerUtil javaModelAnalyzerUtil;

	public ElementParsingService(ProcessingEnvironment processingEnv) {
		this.processingEnv = processingEnv;
		javaModelAnalyzerUtil = new JavaModelAnalyzerUtil(processingEnv.getElementUtils(),
				processingEnv.getTypeUtils());
	}

	public ServiceInput createAnnotatedClazz(final Element annotatedElement) {
		RestArchitecture annotation = annotatedElement.getAnnotation(RestArchitecture.class);
		if (annotatedElement.getKind() == ElementKind.CLASS) {
			TypeElement typeEl = convertIfValid(annotatedElement);
			return getInputForAnnotatedPojo(typeEl, annotation);
		} else {
			throw new InvalidElementException(String.format("Unexpected type %s!", annotatedElement), annotatedElement);
		}
	}

	private TypeElement convertIfValid(final Element annotatedElement) {
		TypeElement typeEl = (TypeElement) annotatedElement;
		if (typeEl.getModifiers().contains(Modifier.PRIVATE)) {
			throw new InvalidElementException(String.format("Pojo %s must not be private!", annotatedElement),
					annotatedElement);
		}
		if (typeEl.getEnclosingElement().getKind() == ElementKind.CLASS
				&& !typeEl.getModifiers().contains(Modifier.STATIC)) {
			throw new InvalidElementException(
					String.format("Pojo %s must not be a non-static inner class!", annotatedElement), annotatedElement);
		}
		return typeEl;
	}

	private ServiceInput getInputForAnnotatedPojo(TypeElement pojoTypeEl, RestArchitecture annotation) {
		return new ServiceInput(pojoTypeEl, pojoTypeEl);
	}

}
