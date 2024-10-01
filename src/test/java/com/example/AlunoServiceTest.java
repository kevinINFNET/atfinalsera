package com.example;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AlunoServiceTest {
@Test
public void consultarAlunoStatus200Test() throws IOException, InterruptedException {
  AlunoController controller = new AlunoController();
  controller.init();

HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
   .uri(URI.create("http://localhost:4567/alunos/1"))
   .GET()
   .build();
 HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
  assertEquals(200, response.statusCode());
 }
@Test
  public void incluirAlunoStatus201Test() throws IOException, InterruptedException {
AlunoController controller = new AlunoController();
controller.init();

String novoAlunoJson = """
 {
  "id": 5,
   "nome": "Spyke",
   "nota": 8.5
 }
  """;

HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
  .uri(URI.create("http://localhost:4567/alunos"))
  .header("Content-Type", "application/json")
  .POST(HttpRequest.BodyPublishers.ofString(novoAlunoJson))
  .build();

HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
assertEquals(201, response.statusCode());
 }
@Test
public void alunoServiceAlunoIdEncontradoTest() {
  AlunoService alunoService = new AlunoService();
  Aluno aluno = alunoService.getAlunoById(1);
  assertNotNull(aluno);
  assertEquals(1, aluno.getId());
  assertEquals("Kevin", aluno.getNome());
  assertEquals(9.0, aluno.getNota());
}
@Test
public void alunoServiceAlunoIdNaoEncontradoTest() {
  AlunoService alunoService = new AlunoService();
  Aluno aluno = alunoService.getAlunoById(99);
   assertNull(aluno);
 }
}
