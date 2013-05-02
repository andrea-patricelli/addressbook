package net.tirasa.test.addressbook.dao.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;
import net.tirasa.test.addressbook.controller.AddressController;

import net.tirasa.test.addressbook.data.Person;
import net.tirasa.test.addressbook.dao.PersonDAO;
import net.tirasa.test.addressbook.exceptions.DatabaseException;

public class PersonDAOLdapImpl implements PersonDAO {

    private String INITCTX;// = "com.sun.jndi.ldap.LdapCtxFactory";

    private String MY_HOST;// ="ldap://localhost:389";

    private String MGR_DN; // = "cn=Manager,o=acg-2013,dc=it";

    private String MGR_PW; // = "password";

    public PersonDAOLdapImpl(String initctx, String my_host, String manager_dn, String manager_pwd) {

        this.INITCTX = initctx;
        this.MY_HOST = my_host;
        this.MGR_DN = manager_dn;
        this.MGR_PW = manager_pwd;
    }

    @Override
    public void save(String requestParam_id, String requestParam_name, String requestParam_email,
            String requestParam_telephone) throws DatabaseException {

        try {
            // this function handles HTTP POST REQUESTS   
            Hashtable env = new Hashtable();
            //Identify service provider to use
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
            env.put(Context.PROVIDER_URL, MY_HOST);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, MGR_DN);
            env.put(Context.SECURITY_CREDENTIALS, MGR_PW);
            DirContext dctx = new InitialDirContext(env);

            //MAKE A PRELIMINAR SEARCH, EVEN IF IT NEEDS TO MODIFY AN EXISTING PERSON
            Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("cn", requestParam_id));
            NamingEnumeration<SearchResult> results = dctx.search("ou=Educatori,ou=People,o=acg-2013,dc=it", matchAttrs);

            // if ldapsearch returns results make an ldap delete and then ldap add
            if (results.hasMore()) {

                Attributes attributes = new BasicAttributes();
                Attribute objectClass = new BasicAttribute("objectClass");
                objectClass.add("organizationalPerson");
                objectClass.add("person");
                objectClass.add("inetOrgPerson");
                attributes.put(objectClass);
                Attribute sn = new BasicAttribute("sn");
                Attribute cn = new BasicAttribute("cn");
                Attribute tel = new BasicAttribute("telephoneNumber");
                sn.add(requestParam_name + ".sn");
                cn.add(requestParam_name);
                tel.add(requestParam_telephone);
                attributes.put(sn);
                attributes.put(cn);
                attributes.put("mail", requestParam_email);
                attributes.put(tel);

                // LDAP MODIFY CODE: BEGIN (DOESN'T WORK --> TO IMPROVE)

                /* ModificationItem[] mods = new ModificationItem[2];
                 * Attribute mod0 = new BasicAttribute("cn", new_name);
                 * Attribute mod1 = new BasicAttribute("mail", email); */
                // Update mail attribute 

                //mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
                //mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod1);
                //dctx.modifyAttributes("cn=" + id + ",ou=Educatori,ou=People,o=acg-2013,dc=it",mods); 

                // LDAP MODIFY CODE: END
                dctx.destroySubcontext("cn=" + requestParam_id + ",ou=Educatori,ou=People,o=acg-2013,dc=it");
                dctx.
                        createSubcontext("cn=" + requestParam_name + ",ou=Educatori,ou=People,o=acg-2013,dc=it",
                        attributes);

            } else {
                // if ldapsearch doesn't give any result --> there is a new user to add, make a simple ldap add   

                Attributes attributes = new BasicAttributes();
                Attribute objectClass = new BasicAttribute("objectClass");
                objectClass.add("organizationalPerson");
                objectClass.add("person");
                objectClass.add("inetOrgPerson");
                attributes.put(objectClass);
                Attribute sn = new BasicAttribute("sn");
                Attribute cn = new BasicAttribute("cn");
                Attribute tel = new BasicAttribute("telephoneNumber");
                sn.add(requestParam_name + ".sn");
                cn.add(requestParam_name);
                tel.add(requestParam_telephone);
                attributes.put(sn);
                attributes.put(cn);
                attributes.put("mail", requestParam_email);
                attributes.put(tel);
                dctx.
                        createSubcontext("cn=" + requestParam_name + ",ou=Educatori,ou=People,o=acg-2013,dc=it",
                        attributes);

            }
            dctx.close();
        } catch (NamingException ex) {
            throw new DatabaseException("Error on add operation" + ex.getExplanation());
        }
    }

    @Override
    public List<Person> list() throws DatabaseException{

        List<Person> collection = new ArrayList();

        try {

            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
            env.put(Context.PROVIDER_URL, MY_HOST);
            DirContext dctx;
            dctx = new InitialDirContext(env);
            Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("sn"));
            matchAttrs.put(new BasicAttribute("mail"));
            NamingEnumeration<SearchResult> results = dctx.search("ou=Educatori,ou=People,o=acg-2013,dc=it", matchAttrs);

            // loop on all results and add to arraylist Person objects
            while (results.hasMore()) {
                SearchResult sr = (SearchResult) results.next();
                Attributes attrs = sr.getAttributes();
                Person sample_person;
                sample_person = new Person(attrs.get("cn").toString(), "", attrs.get("mail").toString(), "", attrs.get(
                        "telephoneNumber").toString());
                // add Person objects to arraylist collection
                collection.add(sample_person);
            }
            dctx.close();
        } catch (NamingException ex) {
            Logger.getLogger(AddressController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return collection;
    }

    @Override
    public Person find(String requestParam_id) {
// MAKE AN LDAPSEARCH TO GET INFORMATION ABOUT PERSON IN SELECTED BY USER TO MODIFY
        Person searched = null;
        try {
            // Beginning connection to ldap directory           
            Hashtable env = new Hashtable();
            /* for authentication
             * String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
             * String MY_HOST = "ldap://Localhost:389";
             * String MGR_DN = "cn=Manager,o=acg-2013,dc=it";
             * String MGR_PW = "password"; *
             * //Identify service provider to use
             * env.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
             * env.put(Context.PROVIDER_URL, MY_HOST);
             * env.put(Context.SECURITY_AUTHENTICATION, "simple");
             * env.put(Context.SECURITY_PRINCIPAL, MGR_DN);
             * env.put(Context.SECURITY_CREDENTIALS, MGR_PW); */
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
            env.put(Context.PROVIDER_URL, MY_HOST);
            DirContext dctx = new InitialDirContext(env);
            // adding matchAttrs filter to search               
            Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("cn", requestParam_id));
            // collecting results of ldap search in a NamingEnumeration object
            NamingEnumeration<SearchResult> results = dctx.search("ou=Educatori,ou=People,o=acg-2013,dc=it", matchAttrs);

            if (results.hasMore()) {

                SearchResult sr = (SearchResult) results.next();
                Attributes attrs = sr.getAttributes();
                String nome = attrs.get("cn").toString();
                String email = attrs.get("mail").toString();
                String phone = attrs.get("telephoneNumber").toString();
                // creation of a Person object to pass to HTTP REQUEST as attribute
                searched = new Person(nome, "", email, phone, "");
            } else {
                searched = null; // it may be cut off from the code (whenever you want)
            }
            dctx.close();
            //pass searched person to editPerson.jsp
        } catch (NamingException ex) {
            Logger.getLogger(AddressController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searched;
    }

    @Override
    public void delete(String requestParam_id) { // to implement

        try {
            // beginning ldap delete
            Hashtable env = new Hashtable();
            //Identify service provider to use
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
            env.put(Context.PROVIDER_URL, MY_HOST);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, MGR_DN);
            env.put(Context.SECURITY_CREDENTIALS, MGR_PW);
            InitialDirContext dctx = new InitialDirContext(env);
            dctx.destroySubcontext("cn=" + requestParam_id + ",ou=Educatori,ou=People,o=acg-2013,dc=it");
            dctx.close();

        } catch (NamingException ex) {
            Logger.getLogger(AddressController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}