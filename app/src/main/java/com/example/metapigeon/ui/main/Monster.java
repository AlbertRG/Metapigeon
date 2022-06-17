package com.example.metapigeon.ui.main;

public class Monster {

    private int ID;
    private String Name;
    private String cr;
    private String size;
    private String type;
    private String aligned;
    private String ac;
    private String hp;
    private String speed;
    private int[] attributes;
    private String skill;
    private String senses;
    private String feature1;
    private String feature2;
    private String feature3;
    private String action1;
    private String action2;
    private String action3;

    public Monster(int ID, String name, String cr, String size, String type, String aligned, String ac, String hp, String speed, int[] attributes, String skill, String senses, String feature1, String feature2, String feature3, String action1, String action2, String action3) {
        this.ID = ID;
        Name = name;
        this.cr = cr;
        this.size = size;
        this.type = type;
        this.aligned = aligned;
        this.ac = ac;
        this.hp = hp;
        this.speed = speed;
        this.attributes = attributes;
        this.skill = skill;
        this.senses = senses;
        this.feature1 = feature1;
        this.feature2 = feature2;
        this.feature3 = feature3;
        this.action1 = action1;
        this.action2 = action2;
        this.action3 = action3;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAligned() {
        return aligned;
    }

    public void setAligned(String aligned) {
        this.aligned = aligned;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int[] getAttributes() {
        return attributes;
    }

    public void setAttributes(int[] attributes) {
        this.attributes = attributes;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getSenses() {
        return senses;
    }

    public void setSenses(String senses) {
        this.senses = senses;
    }

    public String getFeature1() {
        return feature1;
    }

    public void setFeature1(String feature1) {
        this.feature1 = feature1;
    }

    public String getFeature2() {
        return feature2;
    }

    public void setFeature2(String feature2) {
        this.feature2 = feature2;
    }

    public String getFeature3() {
        return feature3;
    }

    public void setFeature3(String feature3) {
        this.feature3 = feature3;
    }

    public String getAction1() {
        return action1;
    }

    public void setAction1(String action1) {
        this.action1 = action1;
    }

    public String getAction2() {
        return action2;
    }

    public void setAction2(String action2) {
        this.action2 = action2;
    }

    public String getAction3() {
        return action3;
    }

    public void setAction3(String action3) {
        this.action3 = action3;
    }

}
