/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */
package ch.ubique.n2step.sdk.backend.ws.config;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Profile("test-cloud")
public class TestingCloudDevConfig extends WSCloudBaseConfig {

    @Bean
    @Override
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
    }

    @Bean
    @Override
    public Flyway flyway() {
        Flyway flyWay =
                Flyway.configure()
                        .dataSource(dataSource())
                        .locations("classpath:/db/migration/hsqldb")
                        .load();
        flyWay.migrate();
        return flyWay;
    }

    @Override
    public String getDbType() {
        return "hsqldb";
    }
}