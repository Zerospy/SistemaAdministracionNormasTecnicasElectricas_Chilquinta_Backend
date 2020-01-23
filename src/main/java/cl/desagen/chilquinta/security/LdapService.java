package cl.desagen.chilquinta.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class LdapService {

    @Autowired
    private LdapTemplate ldapTemplate;

    public List<String> search(String username) {
//        return ldapTemplate.search("ou=users", "cn=" + username, (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
        ldapTemplate.setIgnorePartialResultException(true);
        return ldapTemplate.search(query().where("objectclass").is(username), (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
    }

}
