package com.iguana.resource;

import com.iguana.entity.Investigacion;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/investigaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvestigacionesResource {

    @GET
    public Uni<List<Investigacion>> listar() {
        return Investigacion.listAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Investigacion> obtener(@PathParam("id") Long id) {
        return Investigacion.findById(id);
    }

    @POST
    public Uni<Response> crear(Investigacion inv) {
        return Panache.withTransaction(inv::persist)
                .replaceWith(Response.ok(inv).status(201)::build);
    }

    @PUT
    @Path("/{id}")
    public Uni<Investigacion> actualizar(@PathParam("id") Long id, Investigacion datos) {
        return Panache.withTransaction(() ->
                Investigacion.<Investigacion>findById(id)
                        .onItem().ifNotNull().invoke(inv -> {
                            inv.titulo = datos.titulo;
                            inv.estado = datos.estado;
                            inv.fecha_inicio = datos.fecha_inicio;
                        })
        );
    }


    @DELETE
    @Path("/{id}")
    public Uni<Response> eliminar(@PathParam("id") Long id) {
        return Panache.withTransaction(() -> Investigacion.deleteById(id))
                .replaceWith(Response.noContent()::build);
    }
}
