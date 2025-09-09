package ru.profcom.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class DocumentsTemplatesLoaderService {
    @Value("${document.template.docx.standartScheme}") private String standartScheme;
}
