package it.previsuite.presentation.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import it.previsuite.bean.commons.Claims;
import it.previsuite.bean.exceptions.PreviWebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractServiceAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AbstractServiceAdapter.class);
    protected final ObjectMapper mapper = new ObjectMapper();

    protected Uni<Long> getMemberId(String className, JsonWebToken jwt) {
        Long memberId = null;

        if (jwt.containsClaim(Claims.MEMBER_ID)) {
            memberId = Long.parseLong(jwt.getClaim(Claims.MEMBER_ID));
        }

        if (memberId == null) {
            logger.error("[{}] Invalid request on method getMemberAddresses(): memberId not found in jwt: {}", className, jwt);
            return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.UNAUTHORIZED, "Authentication required"));
        }

        return Uni.createFrom().item(memberId);
    }
}