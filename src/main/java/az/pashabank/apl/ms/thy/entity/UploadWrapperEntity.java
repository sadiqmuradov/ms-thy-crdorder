package az.pashabank.apl.ms.thy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.StringJoiner;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "thy_uploads")
public class UploadWrapperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_thy_uploads")
    @SequenceGenerator(sequenceName = "seq_thy_uploads", allocationSize = 1, name = "seq_thy_uploads")
    @JsonIgnore
    private int id;
    private String name;
    private String fileType;
    private long fileSize;
    private String fileLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appId", nullable = false)
    @JsonIgnore
    private ThyApplicationEntity app;

    public UploadWrapperEntity(String name, String fileType, long fileSize, String fileLocation) {
        this.name = name;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileLocation = fileLocation;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UploadWrapperEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("fileType='" + fileType + "'")
                .add("fileSize=" + fileSize)
                .add("fileLocation='" + fileLocation + "'")
                .toString();
    }
}
