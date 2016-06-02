<%--
  Created by IntelliJ IDEA.
  User: Eagle
  Date: 18.4.2016 г.
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>File Downloading Form</title>
</head>
<body>
<h3>Download file:</h3>
Select a file type to download:
<br />
<form action="DownloadServlet" method="POST">
  <div style="color: #FF0000;">${errorDownloadMessage}</div>
  <input type="radio" name="fileType" value="json">Json </br>
  <input type="radio" name="fileType" value="xls">Excel </br>
  <input type="radio" name="fileType" value="xml">XML

  <br />
  <br />
  <br />
  <input type="submit" value="Download File" />
</form>

<div style="color: green;">${successDownloadMessage}</div>

</br>
<a href="/">Go back to homepage</a>

</body>
</html>