package example.util;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import example.domain.DtoInput;
import example.domain.EntityField;
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

	public ServiceInput createServiceInput(final Element annotatedElement) {
		RestArchitecture annotation = annotatedElement.getAnnotation(RestArchitecture.class);
		if (annotatedElement.getKind() == ElementKind.CLASS) {
			TypeElement typeEl = convertIfValid(annotatedElement);
			return new ServiceInput(typeEl, typeEl);
		} else {
			throw new InvalidElementException(String.format("Unexpected type %s!", annotatedElement), annotatedElement);
		}
	}

	public DtoInput createDtoInput(final Element annotatedElement) {
		RestArchitecture annotation = annotatedElement.getAnnotation(RestArchitecture.class);
		if (annotatedElement.getKind() == ElementKind.CLASS) {
			TypeElement typeEl = convertIfValid(annotatedElement);
			List<? extends Element> enclosedElements = typeEl.getEnclosedElements();
			List<EntityField> fields = enclosedElements.stream()//
					.filter(e -> e.getKind().isField())//
					// .map(e -> new
					// EntityField(asTypeElement(e.asType()).getSimpleName().toString(), ""))//
					.map(e -> new EntityField(extractTypeName(e), e.toString()))//
					.collect(Collectors.toList());//
			return new DtoInput(typeEl, typeEl, fields);
		} else {
			throw new InvalidElementException(String.format("Unexpected type %s!", annotatedElement), annotatedElement);
		}
	}

	private String extractTypeName(Element e) {
		String type = e.asType().toString();
		String[] split = type.split("\\.");
		return split[split.length - 1];
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

	private TypeElement asTypeElement(TypeMirror typeMirror) {
		Types TypeUtils = processingEnv.getTypeUtils();
		return (TypeElement) TypeUtils.asElement(typeMirror);
	}

}
