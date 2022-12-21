package demo.stori.transactions.reader.service;

import demo.stori.transactions.reader.representation.TransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static demo.stori.transactions.reader.representation.TransactionOrigin.FILE;
import static demo.stori.transactions.reader.util.TransactionUtil.stringToDate;

@Slf4j
@Service
public class FileService {

    public List<TransactionRequest> processFile(String filePath) throws IOException, ParseException {
        System.out.printf("Reading filepath [%s] \n", filePath);
        Path path = Paths.get(filePath);

        String checksum = DigestUtils.md5DigestAsHex(Files.readAllBytes(path));

        List<TransactionRequest> requests = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                // Skip the header
                if (index == 0) {
                    index++;
                    continue;
                }

                String[] values = line.split(",");

                if (values.length > 4) {
                    throw new RuntimeException("Error getting the structure");
                }

                TransactionRequest request = TransactionRequest.builder()
                        .id(Long.valueOf(values[0].trim())) // first position means transaction id
                        .transaction(values[1].trim()) // second position means the transaction
                        .account(Long.valueOf(values[2].trim())) // first position means account id
                        .date(stringToDate(values[3].trim())) // first position means account id
                        .reference(checksum)
                        .origin(FILE)
                        .build();

                requests.add(request);
                index++;
            }
        }

        System.out.println(" \\m/. File was read successfully!!!");
        return requests;
    }

}
