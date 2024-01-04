package com.example.soaprojet.controller;

import com.example.soaprojet.models.Fichier;
import com.example.soaprojet.models.Search;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.List;

@Controller
public class GitController {
    @Value("${github.token}")
    private String githubToken;

    @Value("http://localhost:8080")
    private String ApiUrl;

    @GetMapping("/search")
    private ModelAndView showSearchPage() {
        ModelAndView modelAndView = new ModelAndView("search");
        return modelAndView;
    }

// ...

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


    @GetMapping("/saved")
    public String listeSaved(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        // GET Request to the first project's API
        List<Fichier> fichiers = restTemplate.getForObject(ApiUrl + "/api/fichier", List.class);

        // Log or print the retrieved data
        System.out.println("Retrieved fichiers: " + fichiers);

        model.addAttribute("repositories", fichiers);  // Use "fichiers" instead of "saveds"
        return "liste";
    }


}
