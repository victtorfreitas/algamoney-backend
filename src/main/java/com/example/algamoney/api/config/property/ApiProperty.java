package com.example.algamoney.api.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("algamoney")
@Data
public class ApiProperty {
    private String originPermitida = "http://localhost:8000";
    private SegurancaProperty seguranca = new SegurancaProperty();

    @Data
    public static class SegurancaProperty{
        private boolean enableHttps;
    }

}
