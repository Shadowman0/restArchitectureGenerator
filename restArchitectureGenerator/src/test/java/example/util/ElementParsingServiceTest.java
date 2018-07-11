package example.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.google.testing.compile.CompilationRule;

import example.domain.DtoInput;
import flarestar.mirror.mock.MockProcessingEnvironment;

public class ElementParsingServiceTest {
	@Rule
	public CompilationRule rule = new CompilationRule();

	private Elements elements;

	@Before
	public void init() {
		elements = rule.getElements();
	}

	@Test
	@Ignore
	public void getFieldNames() throws Exception {
		TypeElement classElement = elements.getTypeElement("example.util.DummyEntity");
		MockProcessingEnvironment mockedEnvironment = new MockProcessingEnvironment("test");
		ElementParsingService classUnderTest = new ElementParsingService(mockedEnvironment);
		List<? extends Element> enclosedElements = classElement.getEnclosedElements();
		List<String> collect = enclosedElements.stream().filter(e -> e.getKind().isField()).map(e -> {
			return extractTypeName(e);
		}).collect(Collectors.toList());

		DtoInput actualParsedClazz = classUnderTest.createDtoInput(classElement);
		assertThat(actualParsedClazz).isEqualTo("");
	}

	@Test
	public void getFieldNames2() throws Exception {
		TypeElement classElement = elements.getTypeElement("example.util.DummyEntity");
		MockProcessingEnvironment mockedEnvironment = new MockProcessingEnvironment("test");
		ElementParsingService classUnderTest = new ElementParsingService(mockedEnvironment);
		List<? extends Element> enclosedElements = classElement.getEnclosedElements();
		List<VariableElement> fieldsIn = ElementFilter.fieldsIn(enclosedElements);
		assertThat(fieldsIn).isEqualTo("");
	}

	private String extractTypeName(Element e) {
		String type = e.asType().toString();
		String[] split = type.split("\\.");
		return split[split.length - 1];
	}
}
