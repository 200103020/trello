package bitlab.trelloproject.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "d_tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tasks extends idModel{
    private String title;
    private String description;
    private int status;

    @ManyToOne
    private Folders folder;
}
