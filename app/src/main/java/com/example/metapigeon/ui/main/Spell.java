package com.example.metapigeon.ui.main;

public class Spell {

    private int ID;
    private String name;
    private String level;
    private String time;
    private String range;
    private String components;
    private boolean verbal;
    private boolean somatic;
    private boolean material;
    private String duration;
    private String classes;
    private String source;

    public Spell(int ID, String name, String level, String time, String range, String components, boolean verbal, boolean somatic, boolean material, String duration, String classes, String source) {
        this.ID = ID;
        this.name = name;
        this.level = level;
        this.time = time;
        this.range = range;
        this.components = components;
        this.verbal = verbal;
        this.somatic = somatic;
        this.material = material;
        this.duration = duration;
        this.classes = classes;
        this.source = source;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public boolean isVerbal() {
        return verbal;
    }

    public void setVerbal(boolean verbal) {
        this.verbal = verbal;
    }

    public boolean isSomatic() {
        return somatic;
    }

    public void setSomatic(boolean somatic) {
        this.somatic = somatic;
    }

    public boolean isMaterial() {
        return material;
    }

    public void setMaterial(boolean material) {
        this.material = material;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
