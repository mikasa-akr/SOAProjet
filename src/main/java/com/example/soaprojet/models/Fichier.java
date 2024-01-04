package com.example.soaprojet.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Fichier {
    private String avatar_url;
    private String id;
    private String full_name;
    private String description;
    private String html_url;
    private String language;
}