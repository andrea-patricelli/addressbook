package net.tirasa.test.addressbook.controller;

import net.tirasa.test.addressbook.dao.PersonDAO;
import net.tirasa.test.addressbook.dao.impl.PersonDAOJpaImpl;
import net.tirasa.test.addressbook.data.Person;
import net.tirasa.test.addressbook.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddressController {

    @Autowired
    private PersonDAO dbController;

    private final static Logger LOG = LoggerFactory.getLogger(PersonDAOJpaImpl.class);

    @RequestMapping(value = {"/", "/addressList"})
    public ModelAndView showAddressList() throws DatabaseException {
        ModelAndView modelAndView = new ModelAndView("addressList");
        modelAndView.addObject("persons", dbController.list());
        return modelAndView;
    }

    @RequestMapping(value = "/addPerson")
    public ModelAndView editPersonAdd() {
        LOG.info("Redirection from /addPerson to editPerson.jsp");
        return new ModelAndView("addPerson"); // aggiunto /add
    }

    @RequestMapping(value = "/editPerson", method = RequestMethod.GET)
    public ModelAndView editPersonModify(@RequestParam("id") long personId) throws DatabaseException {
        Person searched = dbController.find(personId);
        LOG.info("Using PersonDAO.find(), searched person NAME is: ".concat(searched.getName()));
        return new ModelAndView("editPerson").addObject("personSearched", searched);
    }

    @RequestMapping(value = "/deletePerson", method = RequestMethod.GET)
    public ModelAndView editPersonDelete(@RequestParam("id") long personId) throws DatabaseException {
        dbController.delete(personId);
        return new ModelAndView("deleteResult");
    }

    @RequestMapping(value = "/editPerson", method = RequestMethod.POST)
    public String editPerson(@RequestParam("id") long personId, @RequestParam("name") String personName,
            @RequestParam("email") String personEmail,
            @RequestParam("telephone") String personTelephone, @ModelAttribute("person") Person person) throws
            DatabaseException {
        // THIS METHOD HANDLES EDIT OPERATIONS ON DATABASE
        person.setName(personName);
        person.setEmail(personEmail);
        person.setTelephone(personTelephone);
        dbController.save(person);
        LOG.info("Redirection from /editPerson (method = POST) to editPerson.jsp");
        return "redirect:/";
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
    public String addPerson(@RequestParam("name") String personName,
            @RequestParam("email") String personEmail,
            @RequestParam("telephone") String personTelephone) throws DatabaseException {
        // THIS METHOD HANDLES ADD OPERATIONS ON DATABASE
        dbController.save(new Person(personName, personEmail, personTelephone));
        LOG.info("Redirection from /editPerson (method = POST) to editPerson.jsp");
        return "redirect:/";
    }
}
