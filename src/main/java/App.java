import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        List<Employee> employees = parseCSV(columnMapping, fileName);
        String json = listToJson(employees);
        saveJson(json);
    }

    private static void saveJson(String json) {
        try(FileWriter writer = new FileWriter("data.json")){
            writer.write(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static String listToJson(List<Employee> employees) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        StringBuilder sb = new StringBuilder();
        for (Employee employee : employees)
            sb.append(gson.toJson(employee) + "\n");
        return sb.toString();
    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try(CSVReader reader = new CSVReader(new FileReader(fileName))){
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            List<Employee> employees = csv.parse();
            return employees;
        }
        catch(Exception ex){
        }
        return null;
    }
}
