package de.andrejivanov;

import com.google.common.collect.ImmutableMap;
import de.andrejivanov.models.Member;
import de.andrejivanov.models.MemberKnowledge;
import de.andrejivanov.models.Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DevService {

    private GithubAPI githubAPI;
    private ParallelExecutor parallelExecutor;
    private String org;

    private static final Logger LOGGER = LoggerFactory.getLogger(DevService.class);

    @Autowired
    public DevService(final GithubAPI githubAPI,
                      final ParallelExecutor parallelExecutor,
                      @Value("${github.org}") final String org) {
        this.githubAPI = githubAPI;
        this.parallelExecutor = parallelExecutor;
        this.org = org;
    }

    public List<MemberKnowledge> knowledgeList() {
        List<Member> members = githubAPI.memebers(org);
        return parallelExecutor.getInParallelAndCollect(members.stream(), this::getMememberKnowlege);
    }

    private MemberKnowledge getMememberKnowlege(Member member) {
        List<Repo> repos = githubAPI.repos(member.getLogin());
        repos.stream().forEach(repo -> repo.setOwner(member.getLogin()));
        List<Map<String, Integer>> languages = parallelExecutor.getInParallelAndCollect(repos.stream(), this::getLanguages);
        Map<String, Long> countProjectsPerLanguage =
                languages.stream()
                        .map(Map::keySet)
                        .flatMap(Collection::stream)
                        .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        return new MemberKnowledge(member.getLogin(), member.getAvatar_url(), countProjectsPerLanguage);
    }

    private Map<String, Integer> getLanguages(Repo repo) {
        return githubAPI.languages(repo.getOwner(), repo.getName());
    }


}
