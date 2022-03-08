import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DOMCreateXMLExample {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // tạo phần tử gốc có tên class
            Element rootElement = doc.createElement("class");
            // thêm thuộc tính totalStudents vào thẻ class
            doc.appendChild(rootElement);
            Attr totalEmployeesAttr = doc.createAttribute("totalEmployees");
            totalEmployeesAttr.setValue("2");
            rootElement.setAttributeNode(totalEmployeesAttr);

            // tạo phần tử student1
            Element employee1 = doc.createElement("employee");
            rootElement.appendChild(employee1);
            Attr attr1 = doc.createAttribute("rollno");
            attr1.setValue("1");
            employee1.setAttributeNode(attr1);
            // id
            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode("1"));
            employee1.appendChild(id);
            // name
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode("Vinh"));
            employee1.appendChild(name);
            // age
            Element age = doc.createElement("age");
            age.appendChild(doc.createTextNode("22"));
            employee1.appendChild(age);

            // tạo phần tử student2
            Element employee2 = doc.createElement("employee");
            rootElement.appendChild(employee2);
            Attr attr2 = doc.createAttribute("rollno");
            attr2.setValue("2");
            employee2.setAttributeNode(attr2);
            // id
            Element id2 = doc.createElement("id");
            id2.appendChild(doc.createTextNode("2"));
            employee2.appendChild(id2);
            // name
            Element name2 = doc.createElement("name");
            name2.appendChild(doc.createTextNode("Vinh"));
            employee2.appendChild(name2);
            // age
            Element age2 = doc.createElement("age");
            age2.appendChild(doc.createTextNode("23"));
            employee2.appendChild(age2);

            // ghi nội dung vào file XML
            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(
                    new File("employees.xml"));
            transformer.transform(source, result);

            // ghi kết quả ra console để kiểm tra
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
