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
@Table(name = "TB_AGENCY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgencyEntity implements Serializable{

	private static final long serialVersionUID = 770652806259316990L;

	@PrePersist
    public void prePersist() {
		this.dtCreation = Calendar.getInstance().getTime();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PLACE_ID", length = 50, nullable = false)
	private String placeId;
	
	@Column(name = "AGENCY_NUMBER", nullable = false)
	private Integer agencyNumber;
	
	@Column(name = "DT_CREATION", nullable = false)	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtCreation;
	
}
