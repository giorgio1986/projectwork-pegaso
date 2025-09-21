package it.previsuite.presentation.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.response.PingResponseBean;
import it.previsuite.bean.response.PreviResponse;
import it.previsuite.bean.utils.DateUtils;
import it.previsuite.presentation.port.PublicRestService;
import it.previsuite.presentation.provider.AppConfigProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class PublicRestServiceAdapter implements PublicRestService {
    private static final Logger logger = LoggerFactory.getLogger(PublicRestServiceAdapter.class);
    private final AppConfigProvider config;

    @Inject
    public PublicRestServiceAdapter(AppConfigProvider config) {
        this.config = config;
    }

    @Override
    public Uni<PreviResponse<PingResponseBean>> ping() {
        PreviResponse<PingResponseBean> response = new PreviResponse<>();

        final String pingDate = DateUtils.convert(System.currentTimeMillis(), DateUtils.STANDARD_DATE_TIME);
        final String applicationName = config.getApplicationName();
        final String applicationVersion = config.getApplicationVersion();

        PingResponseBean pingData = new PingResponseBean();
        pingData.setCurrentDate(pingDate);
        pingData.setApplicationName(applicationName);
        pingData.setApplicationVersion(applicationVersion);

        response.setData(pingData);

        return Uni
                .createFrom()
                .item(response);
    }
}