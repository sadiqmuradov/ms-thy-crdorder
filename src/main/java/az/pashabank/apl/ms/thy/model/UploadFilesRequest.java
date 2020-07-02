package az.pashabank.apl.ms.thy.model;

import az.pashabank.apl.ms.thy.entity.UploadWrapperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFilesRequest {

    private Integer appId;
    private List<UploadWrapper> fileUploads;
    @JsonIgnore
    private List<UploadWrapperEntity> uploadWrapperEntities;

}
