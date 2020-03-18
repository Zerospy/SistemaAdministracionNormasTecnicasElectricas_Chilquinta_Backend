package cl.desagen.chilquinta.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LdapService {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Value("${ldap.baseDN}")
    private String ldapBaseDN;

    @Value("${ldap.baseFilter}")
    private String ldaBaseFilter;

    @Value("${ldap.cn}")
    private String cn;

    public Boolean userExists(String username) {
        ldapTemplate.setIgnorePartialResultException(true);
        List<String> result = ldapTemplate.search(ldapBaseDN, String.format(ldaBaseFilter, username), (AttributesMapper<String>) attrs -> (String) attrs.get(cn).get());
        return result != null && result.size() > 0;
    }

}
