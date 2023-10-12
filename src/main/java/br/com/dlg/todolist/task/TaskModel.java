package br.com.dlg.todolist.task;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_task")
@Table(name = "tb_task")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    @Column(length = 50)
    private String title;

    private LocalDateTime startedAt;

    private LocalDateTime endAt;

    private String priority;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createAt;
}
