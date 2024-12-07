public import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CsvReader {
    private ArrayList<String> records;

    public void readCSV(String filePath) {
        records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getRecords() {
        return records;
    }
} {
    
}
