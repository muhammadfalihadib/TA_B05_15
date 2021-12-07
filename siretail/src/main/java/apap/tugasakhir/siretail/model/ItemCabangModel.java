package apap.tugasakhir.siretail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "item_cabang")

public class ItemCabangModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // @Id
    // @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "uuid_item", strategy = "uuid")
    private String uuidItem;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size
    @Column(name = "harga", nullable = false)
    private Integer harga;

    @NotNull
    @Column(name = "stok", nullable = false)
    private Integer stok;

    @NotNull
    @Size(max = 100)
    @Column(name = "kategori", nullable = false)
    private String kategori;

    @NotNull
    @Column(name = "id_promo", nullable = false)
    private Integer idPromo;

    // Relasi dengan CabangModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cabang_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CabangModel cabang;

}