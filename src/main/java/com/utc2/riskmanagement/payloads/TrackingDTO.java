package com.utc2.riskmanagement.payloads;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingDTO {
    private long inProgressTask;
    private long newTask;
    private long completedTask;

}
