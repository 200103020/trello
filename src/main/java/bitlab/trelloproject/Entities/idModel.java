package bitlab.trelloproject.Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class idModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}