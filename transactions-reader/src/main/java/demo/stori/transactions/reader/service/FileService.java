package demo.stori.transactions.reader.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    public void processFile(String filePath) throws IOException {
        System.out.println("Read " + filePath);

        List<List<String>> records = new ArrayList<>();
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



                System.out.print(values[0] + " - ");
                System.out.print(values[1] + " - ");
                System.out.print(values[2] + " - ");
                System.out.print(values[3] + "\n");
                //records.add(Arrays.asList(values));
                index++;
            }
        }
    }

}
