package com.example.patientservice.service;

import com.google.cloud.NoCredentials;
import com.google.cloud.spanner.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.SpannerEmulatorContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class IntegrationTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public static final SpannerEmulatorContainer emulator = new SpannerEmulatorContainer(
            DockerImageName.parse("gcr.io/cloud-spanner-emulator/emulator:1.4.0")
    );

    public static final String PROJECT_ID = "test-project";
    public static final String INSTANCE_NAME = "test-instance";
    public static final String DATABASE_NAME = "patients";

    public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
        emulator.start();
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