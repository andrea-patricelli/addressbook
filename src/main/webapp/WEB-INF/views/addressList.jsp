<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="net.tirasa.test.addressbook.data.Person"%>
<%@ page language="java" import="java.util.*" %>


<!DOCTYPE HTML>
<html>
  <head>
    <title>Person list page</title>
  </head>
  <body>
    PERSONS IN DATABASE
    <table border="1">
      <tbody>
        <tr>
          <td>Name</td>
          <td>e-mail</td>
          <td>Phone Number</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>   
        <c:forEach var="person" items="${persons}">
          <tr>
            <td>${person.name}</td> 
            <td>${person.email}</td>
            <td>${person.homePhoneNumber}</td>
            <td><a href="<c:out value="editPerson?do=edit&id=${person.name}"/>">Edit</a></td>
            <td><a href="<c:out value="deletePerson?do=delete&id=${person.name}"/>">Delete</a></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <br>
    <a href="<c:out value="addPerson"/>">Add a new person</a>

  </body>
</html>