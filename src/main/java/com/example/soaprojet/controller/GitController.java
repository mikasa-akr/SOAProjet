package com.example.soaprojet.controller;

import com.example.soaprojet.Entity.Owner;
import com.example.soaprojet.Entity.Repositories;
import com.example.soaprojet.Entity.Search;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.List;

@Controller
public class GitController {
    @Value("${github.token}")
    private String githubToken;

    @GetMapping("/search")
    private ModelAndView showSearchPage() {
        ModelAndView modelAndView = new ModelAndView("search");
        return modelAndView;
    }

    @PostMapping("/search")
    private ModelAndView searchRepositories(@RequestParam String search, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Authorization", "Bearer " + githubToken);
        headers.set("X-GitHub-Api-Version", "2022-11-28");

        String uri = "https://api.github.com/search/repositories?q=" + search;
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(uri));
        RestTemplate restTemplate = new RestTemplate();
        Search searchRepositories = restTemplate.exchange(requestEntity, Search.class).getBody();

        ModelAndView modelAndView = new ModelAndView("repositories");
        modelAndView.addObject("repositoriesList", searchRepositories.getItems());
        return modelAndView;
    }
}
