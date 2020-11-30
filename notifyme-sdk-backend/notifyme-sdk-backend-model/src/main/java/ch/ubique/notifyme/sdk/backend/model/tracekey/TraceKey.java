/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.ubique.notifyme.sdk.backend.model.tracekey;

import ch.ubique.notifyme.sdk.backend.model.util.UrlBase64StringDeserializer;
import ch.ubique.notifyme.sdk.backend.model.util.UrlBase64StringSerializer;
import ch.ubique.openapi.docannotations.Documentation;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Instant;
import javax.validation.constraints.NotNull;

public class TraceKey {
    private Integer id;

    @JsonSerialize(using = UrlBase64StringSerializer.class)
    @JsonDeserialize(using = UrlBase64StringDeserializer.class)
    @NotNull
    @Documentation(description = "base64 url encoded bytes")
    private byte[] secretKey;

    @NotNull private Instant startTime;

    @NotNull private Instant endTime;

    private Instant createdAt;

    @JsonSerialize(using = UrlBase64StringSerializer.class)
    @JsonDeserialize(using = UrlBase64StringDeserializer.class)
    @Documentation(description = "base64 url encoded bytes")
    private byte[] message;

    @JsonSerialize(using = UrlBase64StringSerializer.class)
    @JsonDeserialize(using = UrlBase64StringDeserializer.class)
    @Documentation(description = "base64 url encoded bytes")
    private byte[] nonce;

    @JsonSerialize(using = UrlBase64StringSerializer.class)
    @JsonDeserialize(using = UrlBase64StringDeserializer.class)
    @Documentation(description = "base64 url encoded bytes")
    private byte[] r2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(byte[] secretKey) {
        this.secretKey = secretKey;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public byte[] getNonce() {
        return nonce;
    }

    public void setNonce(byte[] nonce) {
        this.nonce = nonce;
    }

    public byte[] getR2() {
        return r2;
    }

    public void setR2(byte[] r2) {
        this.r2 = r2;
    }
}
