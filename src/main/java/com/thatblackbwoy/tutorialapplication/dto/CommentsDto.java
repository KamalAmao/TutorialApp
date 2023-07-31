package com.thatblackbwoy.tutorialapplication.dto;

import com.thatblackbwoy.tutorialapplication.model.Tutorial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsDto {
    private String content;
}
