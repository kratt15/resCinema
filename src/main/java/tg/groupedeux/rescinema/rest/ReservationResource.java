package tg.groupedeux.rescinema.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tg.groupedeux.rescinema.model.Reservation;
import tg.groupedeux.rescinema.rest.dto.ReservationRequest;
import tg.groupedeux.rescinema.service.ReservationService;

import java.util.List;

@Path("/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationResource {

    @Inject
    private ReservationService reservationService;

    @POST
    public Response create(ReservationRequest req) {
        if (req == null || req.userId == null || req.seanceId == null || req.seats == null || req.seats.isEmpty()) {
            throw new BadRequestException("Champs requis: userId, seanceId, seats");
        }
        List<int[]> seats = req.seats.stream().map(s -> new int[]{s.row, s.seat}).toList();
        Reservation r = reservationService.createReservation(req.userId, req.seanceId, seats);
        return Response.status(Response.Status.CREATED).entity(r).build();
    }

    @DELETE
    @Path("/{id}")
    public Response cancel(@PathParam("id") Long id) {
        boolean ok = reservationService.cancelReservation(id);
        if (!ok) throw new NotFoundException();
        return Response.noContent().build();
    }
}
