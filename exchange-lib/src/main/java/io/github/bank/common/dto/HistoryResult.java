package io.github.bank.common.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Map;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResult {
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private String DateFrom;
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private String DateTo;
    private String Currency;
}
