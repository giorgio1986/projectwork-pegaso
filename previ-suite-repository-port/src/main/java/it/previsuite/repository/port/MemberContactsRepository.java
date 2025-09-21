package it.previsuite.repository.port;

import it.previsuite.model.MemberContacts;
import it.previsuite.repository.port.postgresql.PostgresqlRepository;

public interface MemberContactsRepository extends PostgresqlRepository<MemberContacts> {

}