package tg.groupedeux.rescinema.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tg.groupedeux.rescinema.service.SeanceService;

import java.time.LocalDate;

@Path("/seances")
@Produces(MediaType.APPLICATION_JSON)
public class SeanceResource {

    @Inject
    private SeanceService seanceService;

    @GET
    public Response search(@QueryParam("filmId") Long filmId, @QueryParam("date") String dateStr) {
        LocalDate date = null;
        if (dateStr != null && !dateStr.isBlank()) {
            date = LocalDate.parse(dateStr);
        }
        return Response.ok(seanceService.search(filmId, date)).build();
    }

    @GET
    @Path("/{id}/disponibilites")
    public Response availability(@PathParam("id") Long id) {
        return Response.ok(seanceService.availability(id)).build();
    }
}
