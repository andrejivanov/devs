package de.andrejivanov.models;

import java.util.Map;

public class MemberKnowledge {
    private String name;
    private String image;
    private Map<String, Long> skills;

    public MemberKnowledge(String name, String image, Map<String, Long> skills) {
        this.name = name;
        this.image = image;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, Long> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Long> skills) {
        this.skills = skills;
    }
}
