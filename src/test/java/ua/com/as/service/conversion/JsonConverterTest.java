package ua.com.as.service.conversion;

import org.junit.Test;
import ua.com.as.util.exception.ConversionException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonConverterTest {
    @Test
    public void convert() throws Exception {
        NormalMock normalMock = new NormalMock("Ivan", 20);
        String result = ConversionManager.getJson(normalMock);
        assertEquals("{\"name\":\"Ivan\",\"age\":20}", result);
    }

    @Test(expected = ConversionException.class)
    public void convertWithException() throws Exception {
        ErrorMock errorMock = new ErrorMock("Ivan", 20);
        ConversionManager.getJson(errorMock);
    }

    @Test
    public void convertWithList() throws Exception {
        ListMock listMock = new ListMock("Ivan", "Anna");
        String result = ConversionManager.getJson(listMock);
        assertEquals("{\"children\":[\"Ivan\",\"Anna\"]}", result);
    }

    private class NormalMock {
        private String name;
        private int age;

        NormalMock(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    private class ErrorMock {
        private String name;
        private int age;

        ErrorMock(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    private class ListMock {
        private List<String> children;

        ListMock(String... children) {
            this.children = Arrays.asList(children);
        }

        public List<String> getChildren() {
            return children;
        }
    }
}