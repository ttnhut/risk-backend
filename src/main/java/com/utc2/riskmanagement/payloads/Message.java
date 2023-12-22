package com.utc2.riskmanagement.payloads;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    private String text;
    private String to;
    private String from;
}
