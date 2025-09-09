package ru.profcom.services;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.profcom.models.Document;

@Service
@RequiredArgsConstructor
public class DocumentGeneratorService {
    @Autowired private final DocumentsTemplatesLoaderService templatesProperties;

    public byte[] createStandartByDOCX(Document document) throws IOException {
        FileInputStream templateFile = new FileInputStream(templatesProperties.getStandartScheme());
        XWPFDocument xwpfDocument = new XWPFDocument(templateFile);

        Map<String, String> data = new HashMap<>();
        // todo : собрать нормально full_name сторку, так как могут быть null значения
        data.put("{{ full_name }}",
            document.getLastName() + " " + document.getFirstName() + " "
                + (document.getMiddleName() == null ? "" : document.getMiddleName()));
        data.put("{{ inn }}", document.getInn());
        data.put("{{ birth_date }}", document.getBirthDate() != null ? document.getBirthDate().toString() : "");
        data.put("{{ group }}", document.getGroupNumber());
        data.put("{{ passport_serial }}", document.getPassportSerial());
        data.put("{{ passport_number }}", document.getPassportNumber());
        data.put("{{ study_type }}", document.getStudyType() != null ? document.getStudyType() : "");
        data.put("{{ issue_by_whom }}", document.getIssueByWhom());
        data.put("{{ address }}", document.getAddressLiving() != null ? document.getAddressLiving() : "");
        data.put("{{ issue_date }}", document.getIssueDate() != null ? document.getIssueDate().toString() : "");
        data.put("{{ contact_number }}", document.getContactNumber());

        for (XWPFTable table : xwpfDocument.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        String text = paragraph.getText();
                        if (text != null && text.contains("{{")) {
                            for (var j : data.entrySet()) {
                                if (text.contains(j.getKey())) {
                                    try {
                                        text = text.replace(j.getKey(), j.getValue());

                                    } catch (NullPointerException e) {
                                        System.out.printf("%s is null", text);
                                    }
                                }
                            }
                            changeText(paragraph, text);
                        }
                        paragraph.getText();
                    }
                }
            }
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        xwpfDocument.write(output);
        xwpfDocument.close();

        return output.toByteArray();
    }

    private void changeText(XWPFParagraph p, String newText) {
        List<XWPFRun> runs = p.getRuns();
        if (runs.isEmpty()) {
            XWPFRun run = p.createRun();
            run.setText(newText);
            return;
        }
        for (int i = runs.size() - 1; i > 0; i--) {
            p.removeRun(i);
        }
        runs.get(0).setText(newText, 0);
    }
}
