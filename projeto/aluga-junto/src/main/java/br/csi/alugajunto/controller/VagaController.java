package br.csi.alugajunto.controller;


import br.csi.alugajunto.model.usuario.Usuario;
import br.csi.alugajunto.model.vaga.Vaga;
import br.csi.alugajunto.service.UsuarioService;
import br.csi.alugajunto.service.VagaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vaga")
@Tag(name = "Vagas", description = "Path relacionado a operações de vagas")
public class VagaController {
    private VagaService service;

    public VagaController(VagaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as vagas", description = "Retorna uma lista de todas as vagas disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vagas retornada com sucesso")
    })
    ///http://localhost:8080/aluga-junto/vaga/listar
    @GetMapping("/listar")
    public List<Vaga> listar() {
        return this.service.listarVagas();
    }

    ///http://localhost:8080/aluga-junto/vaga/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Buscar vaga por ID", description = "Retorna uma vaga correspondente ao ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public Vaga vaga(@Parameter(description = "ID da vaga a ser buscada") @PathVariable Long id) {
        return this.service.getVaga(id);
    }

    ///http://localhost:8080/aluga-junto/vaga
    @PostMapping()
    @Transactional
    @Operation(summary = "Criar uma nova vaga", description = "Cria uma nova vaga e o adiciona à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vaga criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    public ResponseEntity salvar(@RequestBody @Valid Vaga vaga, UriComponentsBuilder uriBuilder) {
        this.service.salvarVaga(vaga);
        URI uri = uriBuilder.path("/uuid/{uuid}").buildAndExpand(vaga.getUuid()).toUri();
        return ResponseEntity.created(uri).body(vaga);
    }

    ///http://localhost:8080/aluga-junto/vaga
    @Operation(summary = "Atualizar vaga", description = "Atualiza as informações de uma vaga existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
    })
    @PutMapping
    public ResponseEntity atualizar(@RequestBody Vaga vaga) {
        this.service.atualizarVaga(vaga);
        return ResponseEntity.ok(vaga);
    }

    ///http://localhost:8080/aluga-junto/vaga/{id}
    @Operation(summary = "Excluir uma vaga", description = "Exclui a vaga com o ID especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vaga excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        this.service.excluirVaga(id);
        return ResponseEntity.noContent().build();
    }

    ///http://localhost:8080/aluga-junto/vaga/uuid/{uuid}
    @Operation(summary = "Obter vaga por UUID", description = "Retorna os detalhes da vaga utilizando o UUID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga encontrada"),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
    })
    @GetMapping("/uuid/{uuid}")
    public Vaga vagaUuid(@PathVariable String uuid) {
        return this.service.getVagaUUID(uuid);
    }

    ///http://localhost:8080/aluga-junto/vaga/{uuid}
    @Operation(summary = "Atualizar vaga com UUID", description = "Atualiza as informações de uma vaga utilizando o UUID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
    })
    @PutMapping("/uuid")
    public void atualizarUUID(@RequestBody Vaga vaga) {
        this.service.atualizarVagaUUID(vaga);
    }


}
