package com.example;
import lombok.Data;
@Data
public class Aluno {
 private int id;
 private String nome;
 private double nota;
 public Aluno() {
}
public Aluno(int id, String nome, double nota) {
 this.id = id;
 this.nome = nome;
 this.nota = nota;
  }
}
