package com.travel.batch;

import com.travel.entity.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class UserDataExportJob {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job exportUserDataJob() {
        return new JobBuilder("exportUserDataJob", jobRepository)
                .start(exportUserDataStep())
                .build();
    }

    @Bean
    public Step exportUserDataStep() {
        return new StepBuilder("exportUserDataStep", jobRepository)
                .<User, User>chunk(100, transactionManager)
                .reader(userItemReader())
                .writer(userItemWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<User> userItemReader() {
        JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql(
                "SELECT id, username, email, full_name, phone_number, role, enabled, created_at, updated_at FROM users ORDER BY created_at DESC");
        reader.setRowMapper(new DataClassRowMapper<>(User.class));
        return reader;
    }

    @Bean
    public FlatFileItemWriter<User> userItemWriter() {
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        writer.setResource(new FileSystemResource("exports/users_" + timestamp + ".csv"));

        DelimitedLineAggregator<User> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<User> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] { "id", "username", "email", "fullName", "phoneNumber", "role", "enabled",
                "createdAt", "updatedAt" });
        lineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(lineAggregator);
        writer.setHeaderCallback(writer1 -> writer1
                .write("ID,Username,Email,Full Name,Phone Number,Role,Enabled,Created At,Updated At"));

        return writer;
    }
}