package com.example.patientservice.service;

import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import com.google.cloud.NoCredentials;
import com.google.cloud.spanner.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.SpannerEmulatorContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "spring.cloud.discovery.enabled=false"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional(readOnly = true)
@ContextConfiguration(
        initializers = {PatientServiceIntegrationTest.Initializer.class}
)
public class PatientServiceIntegrationTest {

    public static final String PROJECT_ID = "test-project";
    public static final String INSTANCE_NAME = "test-instance";
    public static final String DATABASE_NAME = "patients";

    @Autowired
    private PatientRepository patientRepository;

    @Container
    public static final SpannerEmulatorContainer emulator = new SpannerEmulatorContainer(
            DockerImageName.parse("gcr.io/cloud-spanner-emulator/emulator:1.4.0")
    );

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
            SpannerOptions options = SpannerOptions.newBuilder()
                    .setEmulatorHost(emulator.getEmulatorGrpcEndpoint())
                    .setCredentials(NoCredentials.getInstance())
                    .setProjectId(PROJECT_ID)
                    .build();

            Spanner spanner = options.getService();

            try {
                createInstance(spanner);

                createDatabase(spanner);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            TestPropertyValues.of(
                    "spring.datasource.url="
                            + "jdbc:cloudspanner://" + emulator.getEmulatorGrpcEndpoint()
                            + "/projects/" + PROJECT_ID
                            + "/instances/" + INSTANCE_NAME
                            + "/databases/" + DATABASE_NAME
                            + ";usePlainText=true",
                    "spring.datasource.driver-class-name=com.google.cloud.spanner.jdbc.JdbcDriver",
                    "spring.jpa.database-platform=com.google.cloud.spanner.hibernate.SpannerDialect",

                    "spring.cloud.gcp.spanner.instance-id=" + INSTANCE_NAME,
                    "spring.cloud.gcp.spanner.database=" + DATABASE_NAME,
                    "spring.jpa.hibernate.ddl-auto=none",

                    "hibernate.hbm2ddl.auto=none",
                    "hibernate.show_sql=true"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void patientsCountShouldBeCorrect() {
        patientRepository.saveAndFlush(new Patient());

        long count = patientRepository.count();
        assertEquals(1, count);
    }


    private static void createDatabase(Spanner spanner) throws InterruptedException, ExecutionException {
        DatabaseAdminClient dbAdminClient = spanner.getDatabaseAdminClient();
        dbAdminClient.createDatabase(INSTANCE_NAME, DATABASE_NAME, List.of()).get();
    }

    private static void createInstance(Spanner spanner) throws InterruptedException, ExecutionException {
        InstanceConfigId instanceConfig = InstanceConfigId.of(PROJECT_ID, "emulator-config");
        InstanceId instanceId = InstanceId.of(PROJECT_ID, INSTANCE_NAME);
        InstanceAdminClient insAdminClient = spanner.getInstanceAdminClient();
        insAdminClient.createInstance(InstanceInfo.newBuilder(instanceId).setNodeCount(1).setDisplayName("Test instance").setInstanceConfigId(instanceConfig).build()).get();
    }
}