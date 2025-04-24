package com.review.task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {

    @Id
    private String id;

    private String username;

    private Long expiryTime;

    private String link;
}
