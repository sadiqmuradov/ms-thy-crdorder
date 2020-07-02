package az.pashabank.apl.ms.thy.model;

import az.pashabank.apl.ms.thy.entity.NetGrossIncomeEntity;
import az.pashabank.apl.ms.thy.entity.SourceOfIncomeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAnnualIncomeValuesResponse {

    private List<NetGrossIncomeEntity> netGrossIncomeList;
    private List<SourceOfIncomeEntity> sourceOfIncomeList;

}
