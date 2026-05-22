package com.example.demo.domain.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "rsa_keys")
public class Keys {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT", length = 65536)
    private String publicKey;

	@Column(columnDefinition = "TEXT", length = 65536)
    private String privateKey;

	public Keys(long id, String publicKey, String privateKey) {
		this.id=id;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	public Keys() {}

	public long getId() {
		return id;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
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