package example.util;

import example.domain.RestArchitecture;

@RestArchitecture
public class DummyEntity {
	private String erstesFeld;
	private String zweitesFeld;
	private int drittesFeld;

	public int getDrittesFeld() {
		return drittesFeld;
	}

	public void setDrittesFeld(int drittesFeld) {
		this.drittesFeld = drittesFeld;
	}

	public String getErstesFeld() {
		return erstesFeld;
	}

	public void setErstesFeld(String erstesFeld) {
		this.erstesFeld = erstesFeld;
	}

	public String getZweitesFeld() {
		return zweitesFeld;
	}

	public void setZweitesFeld(String zweitesFeld) {
		this.zweitesFeld = zweitesFeld;
	}
}
