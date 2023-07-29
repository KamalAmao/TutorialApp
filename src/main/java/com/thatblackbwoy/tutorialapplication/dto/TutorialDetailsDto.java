package com.thatblackbwoy.tutorialapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorialDetailsDto {
    private Date createdOn;
    private String createdBy;
}
