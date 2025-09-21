package it.previsuite.repository.port;

import it.previsuite.model.Users;
import it.previsuite.repository.port.postgresql.PostgresqlRepository;

public interface UsersRepository extends PostgresqlRepository<Users> {

}