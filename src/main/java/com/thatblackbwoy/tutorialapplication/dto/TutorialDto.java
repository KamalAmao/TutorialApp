package com.thatblackbwoy.tutorialapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorialDto {
    private String title;
    private String description;
    private boolean published;
}
