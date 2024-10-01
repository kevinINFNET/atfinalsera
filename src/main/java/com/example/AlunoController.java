package com.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ResponseTransformer;
import java.util.List;
import static spark.Spark.*;

public class AlunoController {
 private final AlunoService alunoService;
 private final ObjectMapper objectMapper;
public AlunoController() {
 this.alunoService = new AlunoService();
 this.objectMapper = new ObjectMapper();
}
public void init() {
  port(4567);
 get("/alunos", "application/json", (req, res) -> {
  List<Aluno> alunos = alunoService.getAllAlunos();
   res.status(200);
return alunos;
}, json());
 get("/alunos/:id", "application/json", (req, res) -> {
   int id = Integer.parseInt(req.params(":id"));
    Aluno aluno = alunoService.getAlunoById(id);
   if (aluno == null) {
    res.status(404);
   return new ErrorResponse("Aluno não encontrado");
 }
   res.status(200);
   return aluno;
   }, json());
post("/alunos", "application/json", (req, res) -> {
   Aluno novoAluno = objectMapper.readValue(req.body(), Aluno.class);
     alunoService.addAluno(novoAluno);
      res.status(201);
        return novoAluno;
}, json());
 put("/alunos/:id", "application/json", (req, res) -> {
  int id = Integer.parseInt(req.params(":id"));
     Aluno alunoExistente = alunoService.getAlunoById(id);
       if (alunoExistente == null) {
         res.status(404);
          return new ErrorResponse("Aluno não encontrado");
}
  Aluno alunoAtualizado = objectMapper.readValue(req.body(), Aluno.class);
    alunoService.updateAluno(id, alunoAtualizado.getNome(), alunoAtualizado.getNota());
    res.status(200);
     return alunoAtualizado;
        }, json());

 delete("/alunos/:id", "application/json", (req, res) -> {
   int id = Integer.parseInt(req.params(":id"));
    Aluno alunoExistente = alunoService.getAlunoById(id);
     if (alunoExistente == null) {
       res.status(404);
       return new ErrorResponse("Aluno não encontrado");
 }
alunoService.deleteAluno(id);
   res.status(200);
   return new ErrorResponse("Aluno excluído com sucesso");
}, json());
}
private ResponseTransformer json() {
  return object -> {
    try {
return objectMapper.writeValueAsString(object);
} catch (Exception e) {
     throw new RuntimeException("Erro ao converter objeto para JSON", e);
     }
   };
 }
}
