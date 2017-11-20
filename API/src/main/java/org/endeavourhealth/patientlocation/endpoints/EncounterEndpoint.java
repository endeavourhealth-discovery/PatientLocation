package org.endeavourhealth.patientlocation.endpoints;
import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.patientlocation.logic.EncounterLogic;
import org.endeavourhealth.patientlocation.models.OngoingEncounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("/encounters")
@Metrics(registry = "patientLocationMetricRegistry")
@Api(description = "API for all calls relating to encounters")
public class EncounterEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(EncounterEndpoint.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name = "PatientLocation.EpisodeEndpoint.OngoingEncounters")
    @Path("/ongoing")
    @ApiOperation(value = "Returns a list of ongoing patient encounters")
    public Response getOpenEpisodes(@Context SecurityContext sc, @ApiParam(value = "Mandatory Service Id list") @QueryParam("serviceIds") List<String> serviceIds) throws Exception {
        LOG.debug("Get ongoing encounters Called");

        List<OngoingEncounter> ongoingEncounters = new EncounterLogic().getOpenEncounters(sc, serviceIds);

        if (ongoingEncounters == null)
            ongoingEncounters = new ArrayList<>();

        return Response
            .ok()
            .entity(ongoingEncounters)
            .build();
    }
}
