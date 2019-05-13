package br.edu.ifpb.chatgroup.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {

    private String id;
    private String mensagem;
    private LocalDateTime timestamp;

}
