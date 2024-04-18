package ru.mediasoft.warehouse.scheduling;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mediasoft.warehouse.annotation.MeasureTime;


import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Profile("!local")
@RequiredArgsConstructor
@ConditionalOnProperty(name = {"app.scheduling.optimization.enabled", "app.scheduling.enabled"})
public class OptimizedProductPriceScheduler {

    private final DataSource dataSource;

    @Value("${app.scheduling.priceIncreasePercentage}")
    private BigDecimal percentage;

    @Transactional
    @Scheduled(fixedRateString = "${app.scheduling.period}")
    @MeasureTime
    public void increasePrice() {
        System.out.println("START OPTIMIZED SCHEDULER");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE products SET price = price + (price * ?)")) {
            statement.setBigDecimal(1, percentage.divide(new BigDecimal(100)));
            statement.executeUpdate();
            save(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("END OPTIMIZED SCHEDULER");

    }

    private void save(Connection connection) throws SQLException, IOException {
        String filePath = "src/main/resources/export.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM products")) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String productId = resultSet.getString("id");
                    String sku = resultSet.getString("sku");
                    String productName = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String category = resultSet.getString("category");
                    BigDecimal productPrice = resultSet.getBigDecimal("price");
                    int quantity = resultSet.getInt("quantity");
                    String created = resultSet.getString("created");
                    String updatedQuantity = resultSet.getString("updated_quantity");

                    writer.write(productId + "," + sku + "," + productName + "," + description + "," + category + "," + productPrice + "," + quantity + "," + created + "," + updatedQuantity + "\n");
                }
            }
        }
    }

}



/*
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.scheduling.optimization.enabled")
public class OptimizedProductPriceScheduler {

    private final DataSource dataSource;

    @Value("${app.scheduling.priceIncreasePercentage}")
    private BigDecimal percentage;

    @Transactional
    @Scheduled(fixedRateString = "${app.scheduling.period}")
    @MeasureTime
    public void increasePrice() {
        System.out.println("START OPTIMIZED SCHEDULING !!!!!!!!!!!!!!!!!!!!!");
        try (Connection connection = dataSource.getConnection()) {
            List<Product> products = getAllProducts(connection);
            updateProductPrices(connection, products);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("EEEEEEEEEEEEEEEEENNNNNNNNNNNNNNNNNNNNNDDDDDDDDDDDDDDDD");
    }

    private List<Product> getAllProducts(Connection connection) throws SQLException {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM products")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(UUID.fromString(resultSet.getString("id")));
                    product.setPrice(resultSet.getBigDecimal("price"));
                    products.add(product);
                }
            }
        }
        return products;
    }

    private void updateProductPrices(Connection connection, List<Product> products) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE products SET price = ? WHERE id = ?")) {
            for (Product product : products) {
                BigDecimal newPrice = calculateNewPrice(product.getPrice());
                statement.setBigDecimal(1, newPrice);
                statement.setObject(2, product.getId(), java.sql.Types.OTHER);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private BigDecimal calculateNewPrice(BigDecimal oldPrice) {
        BigDecimal increaseAmount = oldPrice.multiply(percentage.divide(new BigDecimal(100)));
        return oldPrice.add(increaseAmount);
    }
}


 */