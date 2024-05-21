package com.example.batchexam;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ColumnRangePartitioner implements Partitioner {

    private final JdbcTemplate jdbcTemplate;

    public ColumnRangePartitioner(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        final Integer min = jdbcTemplate.queryForObject("select min(id) from user", Integer.class);
        final Integer max = jdbcTemplate.queryForObject("select max(id) from user", Integer.class);
        int targetSize = (max - min) / gridSize + 1;

        final Map<String, ExecutionContext> result = new HashMap<>();
        int number = 0;
        int start = min;
        int end = start + targetSize -1;

        while (start <= max) {
            final ExecutionContext executionContext = new ExecutionContext();
            result.put("partition" + number, executionContext);

            if (end >= max) {
                end = max;
            }

            executionContext.putInt("minValue", start);
            executionContext.putInt("maxValue", end);

            start += targetSize;
            end += targetSize;
            number++;
        }

        return result;
    }
}
