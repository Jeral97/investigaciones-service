package com.iguana.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "investigacion")
public class Investigacion extends PanacheEntity {

    public String titulo;
    public String estado;
    public String fecha_inicio;
}
