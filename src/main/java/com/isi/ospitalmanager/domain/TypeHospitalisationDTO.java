package com.isi.ospitalmanager.domain;
import com.isi.hospitalmanager.config.Constants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import javax.validation.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.time.Instant;
@Entity
@Table(name = "jhi_typehospi")
public class TypeHospitalisationDTO {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @NotNull
	    @Size(min = 1, max = 150)
	    @Column(length = 150, unique = true, nullable = false)
	    private String libelle;
	    
	    
}
