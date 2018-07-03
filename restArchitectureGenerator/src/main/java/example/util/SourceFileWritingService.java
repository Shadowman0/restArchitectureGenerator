package example.util;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

import example.domain.Input;
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

	private void createFiles(final List<Input> inputs) throws TemplateNotFoundException, MalformedTemplateNameException,
			ParseException, IOException, TemplateException {
		for (Input input : inputs) {
			Template template = freemarkerCfg.getTemplate(input.getTemplate());
			JavaFileObject jfo = filer.createSourceFile(input.getPackageName() + "." + input.getTargetName());
			Writer writer = jfo.openWriter();
			template.process(input, writer);
		}
	}

	public void createFilesSafely(List<Input> annotatedClazzes) {
		Objects.requireNonNull(annotatedClazzes);
		try {
			createFiles(annotatedClazzes);
		} catch (IOException | TemplateException e) {
			Logger.error(e.getMessage());
		}

	}

}
