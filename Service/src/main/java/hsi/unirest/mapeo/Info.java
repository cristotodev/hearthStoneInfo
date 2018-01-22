package hsi.unirest.mapeo;

import java.util.ArrayList;
import java.util.List;

public class Info {
	
	private String patch;
	private List<String> classes;
	private List<String> sets;
	private List<String> types;
	private List<String> factions;
	private List<String> qualities;
	private List<String> races;
	private List<String> locales;
	
	public Info() {
		classes = new ArrayList<>();
		sets = new ArrayList<>();
		types = new ArrayList<>();
		factions = new ArrayList<>();
		qualities = new ArrayList<>();
		races = new ArrayList<>();
		locales = new ArrayList<>();
	}

	public String getPatch() {
		return patch;
	}

	public void setPatch(String patch) {
		this.patch = patch;
	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
	}

	public List<String> getSets() {
		return sets;
	}

	public void setSets(List<String> sets) {
		this.sets = sets;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public List<String> getFactions() {
		return factions;
	}

	public void setFactions(List<String> factions) {
		this.factions = factions;
	}

	public List<String> getQualities() {
		return qualities;
	}

	public void setQualities(List<String> qualities) {
		this.qualities = qualities;
	}

	public List<String> getRaces() {
		return races;
	}

	public void setRaces(List<String> races) {
		this.races = races;
	}

	public List<String> getLocales() {
		return locales;
	}

	public void setLocales(List<String> locales) {
		this.locales = locales;
	}

}
