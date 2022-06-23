import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestMain {

    private static long suiteStartTime;
    private long testStartTime;

    private final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
    private final String csvFileName = "data.csv";
    private final String csvJsonActualFileName = "data.json";
    private final String csvJsonExpectedFileName = "dataExpected.json";

    @BeforeAll
    public static void initSuite() {
        System.out.println("Running 'EmployeeTest' for methods of class \"Employee\"");
        suiteStartTime = System.nanoTime();
    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("The 'EmployeeTest' complete: " + (System.nanoTime() - suiteStartTime));
    }

    @BeforeEach
    public void initTest() {
        System.out.println("Starting new test");
        testStartTime = System.nanoTime();
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Test complete: " + (System.nanoTime() - testStartTime));
    }

    @Test
    @DisplayName("Test \"parceCSV()\" static method")
    public void parseCSVTest() {
        List<Employee> expected = Arrays.asList(
                new Employee(1, "John", "Smith", "USA", 25),
                new Employee(2, "Ivan", "Petrov", "RU", 23)
        );
        List<Employee> actual = Main.parseCSV(columnMapping, csvFileName);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test \"listToJson()\" static method")
    public void listToJsonTest() {

        String expected = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Smith\",\n" +
                "    \"country\": \"USA\",\n" +
                "    \"age\": 25\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"firstName\": \"Ivan\",\n" +
                "    \"lastName\": \"Petrov\",\n" +
                "    \"country\": \"RU\",\n" +
                "    \"age\": 23\n" +
                "  }\n" +
                "]";
        List<Employee> listFromCSV = Main.parseCSV(columnMapping, csvFileName);
        String actual = (String) Main.listToJson(listFromCSV);
        Assertions.assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Test \"writeString()\" static method")
    public void writeStringTest() {

        // CSV --> Json

        System.out.println("Starting csv -> json convertion...");

        List<Employee> listFromCSV = Main.parseCSV(columnMapping, csvFileName);
        String jsonFromCSV = (String) Main.listToJson(listFromCSV);
        Main.writeString(jsonFromCSV, csvJsonActualFileName);

        System.out.println("End csv -> json convertion");

        // read expected file csvJsonExpectedFileName

        System.out.println("Reading \"" + csvJsonExpectedFileName + "\" file...");

        String s;
        StringBuilder sbExpectedString = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(csvJsonExpectedFileName))) {
            while ((s = br.readLine()) != null) {
                sbExpectedString.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String expected = sbExpectedString.toString();

        // read actual file csvJsonActualFileName

        System.out.println("Reading \"" + csvJsonActualFileName + "\" file...");

        StringBuilder sbActualString = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(csvJsonActualFileName))) {
            while ((s = br.readLine()) != null) {
                sbActualString.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String actual = sbActualString.toString();

        // run assertion

        Assertions.assertEquals(expected, actual);

    }
}