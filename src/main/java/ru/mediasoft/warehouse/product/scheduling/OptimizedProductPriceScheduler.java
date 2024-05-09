package ru.mediasoft.warehouse.product.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    public void increasePrice() throws Exception {
        log.info("START OPTIMIZED SCHEDULER!");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE products SET price = price + (price * ?)")) {
            statement.setBigDecimal(1, percentage.divide(new BigDecimal(100)));
            statement.executeUpdate();
            saveToFile(connection);
        } catch (Exception e) {
            throw new Exception(e);
        }
        log.info("END OPTIMIZED SCHEDULER!");
    }

    private void saveToFile(Connection connection) throws SQLException, IOException {
        String filePath = "src/main/resources/export.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM products");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String csvRow = buildRow(resultSet);
                writer.write(csvRow);
            }
        }
    }


    private String buildRow(ResultSet resultSet) throws SQLException {
        StringBuilder builder = new StringBuilder();
        builder.append(resultSet.getString("id")).append(",")
                .append(resultSet.getString("sku")).append(",")
                .append(resultSet.getString("name")).append(",")
                .append(resultSet.getString("description")).append(",")
                .append(resultSet.getString("category")).append(",")
                .append(resultSet.getBigDecimal("price")).append(",")
                .append(resultSet.getInt("quantity")).append(",")
                .append(resultSet.getString("created")).append(",")
                .append(resultSet.getString("updated_quantity")).append("\n");
        return builder.toString();
    }
}