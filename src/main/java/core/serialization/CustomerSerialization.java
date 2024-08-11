package core.serialization;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class CustomerSerialization {
    public static void main(String[] args) throws IOException {
        Customer customer = new Customer("Ivan", "Petrov", "secretPassword"
                , new Passport("1223", "187256"));
        System.out.println(customer);

        File file = new File("G:/Java/customer.json");
        ObjectMapper om = new ObjectMapper();

        // Один из вариантов заставить Jackson пропускать это поле при сериализации
        om.setVisibility(om.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        om.writeValue(file, customer);

        Customer deserializedCustomer = om.readValue(file, Customer.class);
        System.out.println(deserializedCustomer);
    }
}
