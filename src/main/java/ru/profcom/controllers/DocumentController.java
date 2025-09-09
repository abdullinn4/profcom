package ru.profcom.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.profcom.dto.PersonalDataDto;
import ru.profcom.dto.UserProfileDto;
import ru.profcom.entities.PersonalDataEntity;
import ru.profcom.mappers.PersonalDataMapper;
import ru.profcom.models.Document;
import ru.profcom.repositories.DocumentRepository;
import ru.profcom.responses.AidRequestResponse;
import ru.profcom.services.DocumentGeneratorService;
import ru.profcom.validators.DocumentValidator;

@RestController
@RequestMapping("/profcom")
@Scope("session")
@RequiredArgsConstructor
@Tag(name = "Documents", description = "Document management APIs")
@SecurityRequirement(name = "oauth2")
public class DocumentController {
    private final DocumentGeneratorService documentGeneratorService;
    private final DocumentValidator documentValidator;
    private final PersonalDataMapper mapper;
    private final DocumentRepository documentRepository;

    private Document lastApprovedDocument;

    @Operation(summary = "Submit aid request", description = "Submits a new aid request for a university student",
        responses =
        {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = AidRequestResponse.class)))
            ,
                @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = AidRequestResponse.class))),
                @ApiResponse(responseCode = "401", description = "Unauthorized")
        })
    @PostMapping("/aid-request/university/{id}")
    public ResponseEntity<?>
    submitAid(@Parameter(description = "University ID") @PathVariable Long id,
        @Parameter(description = "User profile data") @RequestBody UserProfileDto dto) {
        List<String> errors = documentValidator.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new AidRequestResponse("rejected", String.join(", ", errors)));
        }
        Document document = new Document();
        document.toModel(dto);
        //    documentRepository.save(document);
        lastApprovedDocument.toModel(dto);
        return ResponseEntity.ok(new AidRequestResponse("success", "Заявка сохранена"));
    }

    @Operation(summary = "Download last document", description = "Downloads the last generated document in DOCX format",
        responses =
        {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                content =
                    @Content(mediaType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
            ,
                @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = AidRequestResponse.class))),
                @ApiResponse(responseCode = "401", description = "Unauthorized")
        })
    @PostMapping("/download-last")
    public ResponseEntity<?>
    sendLastDocument() {
        return generateDocument(lastApprovedDocument);
    }

    public ResponseEntity<?> generateDocument(Document document) {
        if (document == null) {
            return ResponseEntity.badRequest().body(new AidRequestResponse("error", "Документ не найден"));
        }

        try {
            byte[] fileContent = documentGeneratorService.createStandartByDOCX(document);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.docx")
                .header(
                    HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                .body(fileContent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AidRequestResponse("error", "Ошибка при генерации документа"));
        }
    }
}
