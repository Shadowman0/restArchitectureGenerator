package example.util;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

import example.domain.ServiceInput;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;

public class SourceFileWritingService {

	private Configuration freemarkerCfg;
	private Filer filer;

	public SourceFileWritingService(final Filer filer) {
		this.filer = filer;
		freemarkerCfg = new Configuration(new Version(2, 3, 20));
		freemarkerCfg.setClassForTemplateLoading(this.getClass(), "/");
		freemarkerCfg.setDefaultEncoding("UTF-8");
		freemarkerCfg.setLocale(Locale.US);
		freemarkerCfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	private void createFiles(final List<ServiceInput> inputs) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		String templateName = "templates/service.ftl";
		Template template = freemarkerCfg.getTemplate(templateName);
		for (ServiceInput input : inputs) {
			// Map<String, Object> modelMap = new HashMap<>();
			// modelMap.put("packageName", clazz.getPackageName());
			// modelMap.put("sourcePackageName", clazz.getPackageName());
			// modelMap.put("targetPackageName", clazz.getTargetPackageName());
			// modelMap.put("source", clazz.getClazz());
			// modelMap.put("target", clazz.getTarget());
			// modelMap.put("generatedClazzName", input.getTargetName());
			// modelMap.put("fields", input.getFields());
			JavaFileObject jfo = filer.createSourceFile(input.getPackageName() + "." + input.getTargetName());
			Writer writer = jfo.openWriter();
			template.process(input, writer);
		}
	}

	public void createFilesSafely(List<ServiceInput> annotatedClazzes) {
		Objects.requireNonNull(annotatedClazzes);
		try {
			createFiles(annotatedClazzes);
		} catch (IOException | TemplateException e) {
			Logger.error(e.getMessage());
		}

	}

}
