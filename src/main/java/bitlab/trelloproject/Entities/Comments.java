package bitlab.trelloproject.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "d_comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comments extends idModel{
    private String comment;
    @ManyToOne
    private Tasks task;
}
