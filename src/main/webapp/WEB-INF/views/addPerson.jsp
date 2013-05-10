<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="net.tirasa.test.addressbook.data.Person"%>
<%@ page language="java" import="java.util.*" %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>Person edit page</title>
  </head>
  <body>
    <form name="edit" action="addPerson" method="post">

      <input type="hidden" name="id" value=""  > 
      <table border="1">
        <tbody>
          <tr>
            <td>Name:</td>
            <td><input type="text" name="name" value= "" ></td>
          </tr><tr>
            <td>e-mail:</td>
            <td> <input type="email" name="email" value="" > </td>
          </tr>
          <tr>
            <td>Telephone:</td> 
            <td><input type="tel" pattern="^[0-9]{7,10}" name="telephone" value="" ></td>
          </tr>
          <tr><td colspan="2"><input type="submit"
                                     name="btnSave" value="Save"></td>
          </tr>
        </tbody>
      </table>
    </form>
  </body>
</html>