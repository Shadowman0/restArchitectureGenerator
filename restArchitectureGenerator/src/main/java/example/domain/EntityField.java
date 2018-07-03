package example.domain;

public class EntityField {

	private Class<?> clazz;
	private String name;

	public EntityField(Class<?> clazz, String name) {
		this.clazz = clazz;
		this.name = name;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public void setName(String string) {
		this.name = string;
	}

}
