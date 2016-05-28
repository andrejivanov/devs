package de.andrejivanov.models;

import java.util.Map;

public class MemberKnowledge {
    private Member member;
    private Map<String, Integer> countProjectsPerLanguage;

    public MemberKnowledge(Member member, Map<String, Integer> countProjectsPerLanguage) {
        this.member = member;
        this.countProjectsPerLanguage = countProjectsPerLanguage;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Map<String, Integer> getCountProjectsPerLanguage() {
        return countProjectsPerLanguage;
    }

    public void setCountProjectsPerLanguage(Map<String, Integer> countProjectsPerLanguage) {
        this.countProjectsPerLanguage = countProjectsPerLanguage;
    }
}
