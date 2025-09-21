package it.previsuite.service.port;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.UserBean;
import it.previsuite.bean.request.LoginRequestBean;
import it.previsuite.bean.response.LoginResponseBean;

public interface UsersManager {
    Uni<LoginResponseBean> login(LoginRequestBean request);
    Uni<Boolean> logout();
    Uni<UserBean> getUser(Long userId);
}