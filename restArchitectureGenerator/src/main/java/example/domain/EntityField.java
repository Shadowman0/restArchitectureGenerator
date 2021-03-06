package example.domain;

public class EntityField {

	private String type;
	private String name;

	@Override
	public String toString() {
		return "EntityField [" + type + " " + name + "]";
	}

	public EntityField(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String string) {
		this.name = string;
	}

}
