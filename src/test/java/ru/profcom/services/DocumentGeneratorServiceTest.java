package ru.profcom.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.profcom.models.Document;

@SpringBootTest
class DocumentGeneratorServiceTest {
    @Autowired private DocumentGeneratorService documentGeneratorService;

    @Test
    public void testGenerateDocument() throws IOException {
        Document document = new Document();
        document.setFirstName("TestName");
        document.setInn("123123123123");

        byte[] standartByDOCX = documentGeneratorService.createStandartByDOCX(document);
        FileOutputStream testDocx = new FileOutputStream("TestDocx.docx");
        testDocx.write(standartByDOCX);
        testDocx.close();
    }
}