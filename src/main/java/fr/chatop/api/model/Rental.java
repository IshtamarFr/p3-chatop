package fr.chatop.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rentals")
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(hidden = true)
	private Long id;

	@Schema(example="My dream house", required=true)
	private String name;
	@Schema(example="215.0", required=true)
	private float surface;
	@Schema(example="120.0", required=true)
	private float price;
	@Schema(example="C:/test.jpg")
	private String picture;
	@Schema(example="My grandmother's house", required=true)
	private String description;
	@Schema(example="2", required=true)
	private Long owner_id;

	@Schema(hidden=true)
	private Timestamp created_at;
	@Schema(hidden=true)
	private Timestamp updated_at;
}
