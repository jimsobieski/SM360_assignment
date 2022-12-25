package sm360.techassignment.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm360.techassignment.enumeration.ListingState;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "listing")
public class Listing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
  
	@ManyToOne
	@JoinColumn(name = "dealerid", nullable = false)
	private Dealer dealer;
  
	@Column(name = "vehicle", nullable = false)
	private String vehicle;
  
	@Column(name = "price", nullable = false)
	private BigDecimal price;
  
	@Column(name = "createdat", nullable = false)
	private LocalDate createdAt;
  
	@Column(name = "state", nullable = false)
	@Enumerated(EnumType.STRING)
	private ListingState state;
  
}

