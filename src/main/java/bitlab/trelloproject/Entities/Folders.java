package bitlab.trelloproject.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "d_folder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Folders extends idModel{

    private String name;

    @ManyToMany
    private List<TaskCategories> categoriesList;
}
