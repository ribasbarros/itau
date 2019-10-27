package br.com.itau.domain.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_COLLECT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CollectEntity implements Serializable {

	private static final long serialVersionUID = 4169300931299510604L;

	@PrePersist
    public void prePersist() {
		this.dtCreation = Calendar.getInstance().getTime();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "lat", nullable = false)
	private Double lat;
	
	@Column(name = "lng", nullable = false)
	private Double lng;
	
	@Column(name = "BROWSER_DEVICE", length = 150, nullable = false)
	private String browserDevice;
	
	@Column(name = "AGENCY", nullable = false)
	private Integer agency;
	
	@Column(name = "DT_CREATION", nullable = false)	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtCreation;
	
}
