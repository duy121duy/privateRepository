import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonDemo {
    public static void main(String[] args) {
        String json = "{"
                + "\"id\":\"1\""
                + ",\"name\":\"Duy\""
                + ",\"age\":\"22\""
                + "}";
        // Cách 1 chuyển đổi json thành object
        Gson gson = new Gson(); // khởi tạo Gson
        Employee employee1 = gson.fromJson(json, Employee.class);
        System.out.println(employee1);
        Employee employee2 = new Employee(2, "Duy2", 23);
        Employee employee3 = new Employee(3, "Duy3", 24);
        JsonArray arr = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", employee2.getId());
        jsonObject.addProperty("name", employee2.getName());
        jsonObject.addProperty("age", employee2.getAge());
        System.out.println(jsonObject);
        arr.add(jsonObject);

        jsonObject.addProperty("id", employee1.getId());
        jsonObject.addProperty("name", employee1.getName());
        jsonObject.addProperty("age", employee1.getAge());
        System.out.println(jsonObject);
        arr.add(jsonObject);

        jsonObject.addProperty("id", employee3.getId());
        jsonObject.addProperty("name", employee3.getName());
        jsonObject.addProperty("age", employee3.getAge());
        System.out.println(jsonObject);

        arr.add(jsonObject);
        System.out.println(arr);
        System.out.println(gson.fromJson(arr.get(0), Employee.class));
    }
}
