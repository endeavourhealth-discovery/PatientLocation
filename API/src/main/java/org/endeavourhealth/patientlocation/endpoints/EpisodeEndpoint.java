package org.endeavourhealth.patientlocation.endpoints;
import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.patientlocation.logic.EpisodeLogic;
import org.endeavourhealth.patientlocation.models.OpenEpisode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/episode")
@Metrics(registry = "patientLocationMetricRegistry")
@Api(description = "API for all calls relating to episodes")
public class EpisodeEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(EpisodeEndpoint.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name = "PatientLocation.EpisodeEndpoint.OpenEpisodes")
    @Path("/open")
    @ApiOperation(value = "Returns a list of open patient episodes")
    public Response getOpenEpisodes(@Context SecurityContext sc, @ApiParam(value = "Mandatory Service Id list") @QueryParam("serviceIds") List<String> serviceIds) throws Exception {
        LOG.debug("Get Open Episodes Called");

        List<OpenEpisode> openEpisodes = new EpisodeLogic().getOpenEpisodes(sc, serviceIds);

        return Response
            .ok()
            .entity(openEpisodes)
            .build();
    }
}
