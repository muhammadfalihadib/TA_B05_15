package apap.tugasakhir.siretail.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "cabang")

@JsonIgnoreProperties(value={"listItemCabang"}, allowSetters = true)
public class CabangModel implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 30)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 100)
    @Column(name = "alamat", nullable = false)
    private String alamat;

    @NotNull
    @Column(name = "ukuran", nullable = false)
    private Integer ukuran;

    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Size(max = 20)
    @Column(name = "no_telp", nullable = false)
    private String noTelp;

    // Relasi dengan UserModel
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel penanggungJawab;

    // Relasi dengan ItemCabangModel
    @OneToMany(mappedBy = "cabang", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ItemCabangModel> listItemCabang;

}
