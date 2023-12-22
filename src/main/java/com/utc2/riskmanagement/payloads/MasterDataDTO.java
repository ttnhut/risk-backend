package com.utc2.riskmanagement.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MasterDataDTO implements Serializable {
   private String id;
   private String type;
   private String value;

   public MasterDataDTO(String id) {
      this.id = id;
   }
}
