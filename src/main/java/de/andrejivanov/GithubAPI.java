package de.andrejivanov;

import de.andrejivanov.models.Member;
import de.andrejivanov.models.Repo;
import feign.Param;
import feign.RequestLine;

import java.util.HashMap;
import java.util.List;

interface GithubAPI {

    @RequestLine("GET /orgs/{org}/members")
    List<Member> memebers(@Param("org") String org);

    @RequestLine("GET /users/{member}/repos")
    List<Repo> repos(@Param("member") String member);

    @RequestLine("GET /repos/{member}/{repo}/languages")
    HashMap<String, Integer> languages(@Param("member") String member, @Param("repo") String repo);
}
