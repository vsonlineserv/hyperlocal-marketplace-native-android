package in.vbuy.client;


 class Filte {
	private String name = "";
	private boolean checked = false;

	public Filte() {
	}

	public Filte(String name) {
		this.name = name;
	}

	public Filte(String name, boolean checked) {
		this.name = name;
		this.checked = checked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String toString() {
		return name;
	}

	public void toggleChecked() {
		checked = !checked;
	}
}