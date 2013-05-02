package net.tirasa.test.addressbook.controller;

import net.tirasa.test.addressbook.dao.PersonDAO;
import net.tirasa.test.addressbook.data.Person;
import net.tirasa.test.addressbook.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddressController {

    @Autowired
    private PersonDAO dbController;

    @RequestMapping(value = {"/", "/addressList"})
    public ModelAndView showAddressList() throws Exception {

        ModelAndView modelAndView = new ModelAndView("addressList");
        modelAndView.addObject("persons", dbController.list());
        return modelAndView;
    }

    @RequestMapping(value = "/addPerson")
    public ModelAndView editPersonAdd() {
        return new ModelAndView("editPerson");
    }

    @RequestMapping(value = "/editPerson", method = RequestMethod.GET)
    public ModelAndView editPersonModify(@RequestParam("id") String personId) throws DatabaseException {

        String id = personId;
        Person searched = (Person) dbController.find(id);
        return new ModelAndView("editPerson").addObject("personSearched", searched);
    }

    @RequestMapping(value = "/deletePerson", method = RequestMethod.GET)
    public ModelAndView editPersonDelete(@RequestParam("id") String personId) throws DatabaseException {
        String id = personId;
        dbController.delete(id);
        return new ModelAndView("deleteResult");
    }

    @RequestMapping(value = "/editPerson", method = RequestMethod.POST)
    public String addPerson(@RequestParam("id") String personId, @RequestParam("name") String personName,
            @RequestParam("email") String personEmail,
            @RequestParam("telephone") String personTelephone) throws DatabaseException {
        // THIS METHOD HANDLES ADD OPERATIONS ON DATABASE
        dbController.save(personId, personName, personEmail, personTelephone);
        return "redirect:/";
    }
}
