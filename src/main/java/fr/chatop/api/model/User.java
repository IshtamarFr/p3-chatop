package fr.chatop.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(hidden=true)
	private Long id;

	@Column(unique=true)
	@Schema(example="test@test.com", required=true)
	private String email;
	@Schema(example="Jean Dupont", required=true)
	private String name;
	@Schema(example="123456", required=true)
	private String password;
	@Schema(hidden=true)
	private Timestamp created_at;
	@Schema(hidden=true)
	private Timestamp updated_at;

	// Mandatory for UserDetails for Spring Security

	@Override public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	@Override public String getUsername() {
		return this.email;
	}
	@Override public boolean isAccountNonExpired() {
		return true;
	}
	@Override public boolean isAccountNonLocked() {
		return true;
	}
	@Override public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override public boolean isEnabled() {
		return true;
	}
}
