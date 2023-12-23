package com.example.soaprojet.Entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Repositories {
    private Integer id;
    private String full_name;
    private String description;
    private String html_url;
    private String language;
    private Owner owner;
}
