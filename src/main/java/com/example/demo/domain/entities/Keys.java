package com.example.demo.domain.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "rsa_keys")
public class Keys {

	public Keys(long id, String publicKey, String privateKey) {
		this.id=id;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT", length = 65536)
    private String publicKey;

	@Column(columnDefinition = "TEXT", length = 65536)
    private String privateKey;



	public long getId() {
		return id;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	@Override
	public String toString() {
		return "Keys{" +
				"id=" + id +
				", publicKey='" + publicKey + '\'' +
				", privateKey='" + privateKey + '\'' +
				'}';
	}
}