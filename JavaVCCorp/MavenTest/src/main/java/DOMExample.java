import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DOMExample {
    public static void main(String[] args) {
        List<Employee> employees = DOMExample.readListEmployees();
        // hiển thị các đối tượng student ra màn hình
        for (Employee e : employees) {
            System.out.println(e.toString());
        }
    }

    public static List<Employee> readListEmployees() {
        List<Employee> listStudents = new ArrayList<>();
        Employee employee = null;

        try {
            // đọc file input.xml
            File inputFile = new File("employees.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // in phần tử gốc ra màn hình
            System.out.println("Phần tử gốc:"
                    + doc.getDocumentElement().getNodeName());

            // đọc tất cả các phần tử có tên thẻ là "employee"
            NodeList nodeListEmployee = doc.getElementsByTagName("employee");

            // duyệt các phần tử student
            for (int i = 0; i < nodeListEmployee.getLength(); i++) {
                // tạo đối tượng student
                employee = new Employee();
                // đọc các thuộc tính của student
                Node nNode = nodeListEmployee.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    employee.setId(Integer.parseInt(eElement.getElementsByTagName("id")
                            .item(0).getTextContent()));
                    employee.setName(eElement.getElementsByTagName("name")
                            .item(0).getTextContent());
                    employee.setAge(Integer.parseInt(eElement.getElementsByTagName("age")
                            .item(0).getTextContent()));
                }
                // add đối tượng student vào listStudents
                listStudents.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStudents;
    }
}
